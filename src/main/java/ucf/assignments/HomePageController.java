package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Leonie Alexandra
 */

public class HomePageController implements Initializable {

    @FXML
    private TableView<Item> tableView;

    @FXML
    private TableColumn<Item, String> TableValue;

    @FXML
    private TableColumn<Item, String> TableSerialNum;

    @FXML
    private TableColumn<Item, String> TableName;

    @FXML
    private TextField ItemName;

    @FXML
    private TextField SerialNum;

    @FXML
    private TextField Value;

    @FXML
    private TextField SearchBox;

    @FXML
    private Button RemoveButton;

    @FXML
    private Button Add;

    @FXML
    private CheckBox ValueCheckBox;

    @FXML
    private CheckBox SerialNumberCheckbox;

    @FXML
    private CheckBox NameCheckbox;

    public final ObservableList<Item> item = FXCollections.observableArrayList();

    /*
    This function is used to add a new item to the table

    Get string name from ItemName textfield
    Get string serialNumber from SerialNum textfield
    Get string a from Value textfield

    if(a is not numeric)
        Display Alertbox
    else if (name is not the valid length)
        Display Alertbox
    else if (serialNumber is not the valid length)
        Display Alertbox
        if(serialNumber is not alphanumeric)
            Display Alertbox
    else
        if(serialNumber is a duplicate)
            create a float from a called newValue
            Create Decimal format of 2 decimal places
            Create String called newString which allows the number to have a $ in front of it
            add new Item
        else
            Display Alertbox
    set ItemName to nothing
    set SerialNum to nothing
    set Value to nothing
     */
    @FXML
    void OnButtonClickAdd(ActionEvent event) {
        String name = ItemName.getText();
        String serialNumber = SerialNum.getText();
        String a = Value.getText();

        if(!Validation.isNumeric(a)){
            AlertBox.display("Error", "Data inputted must be numeric");
        } else if(!Validation.checkItemName(name)){
            AlertBox.display("Error", "Each inventory item shall have a name between 2 and 256 characters in length (inclusive)");
        } else if(!Validation.checkSerial(serialNumber)){
            AlertBox.display("Error", "Must be XXXXXXXXXX in length");
            if(!Validation.isAlphaNumeric(serialNumber)){
                AlertBox.display("Error", "Must be Alphanumeric");
            }
        } else {
            if(CheckDuplicate(serialNumber)){
                float newValue = Float.parseFloat(a);
                DecimalFormat df = new DecimalFormat("#.##");
                df.setMinimumFractionDigits(2);
                String newString = "$" + df.format(newValue);
                item.add(new Item(newString, serialNumber, name));
            } else {
                AlertBox.display("Error", "Duplicate Serial Number");
            }
        }
        ItemName.setText("");
        SerialNum.setText("");
        Value.setText("");
    }

    /*
    This function is used to edit serial Number in the cell
    ChangeSerialNumber{
        Get taskSelected from the selected cell
        Create New string which takes the New Value
        Create Old string which takes the Old Value

        if(New String is Valid){
            if(The New String is not a duplicate)
                set cell with New String
            else
                display Alertbox
                set cell with Old String
        }
        refresh table
    }
     */
    @FXML
    void ChangeSerialNumber(TableColumn.CellEditEvent editedCell) {
        Item taskSelected =  tableView.getSelectionModel().getSelectedItem();
        String New = editedCell.getNewValue().toString();
        String Old = editedCell.getOldValue().toString();

        if(Validation.ChangeNumber(New, Old).equals(New)){
            if(CheckDuplicate(New)){
                taskSelected.setItemNumber(New);
            } else {
                AlertBox.display("Error", "Duplicate Value");
                taskSelected.setItemNumber(Old);
            }
        }
        tableView.refresh();
    }

    /*
    This is used to edit the cell for Name
    ChangeName{
        Get taskSelected from the selected cell
        Create New string which takes the New Value
        Create Old string which takes the Old Value

        if(New name is invalid){
            display Alertbox
            set cell to old string
        } else
            set cell to new string
        refresh table
    }
     */
    @FXML
    void ChangeName(TableColumn.CellEditEvent editedCell) {
        Item taskSelected =  tableView.getSelectionModel().getSelectedItem();
        String New = editedCell.getNewValue().toString();
        String Old = editedCell.getOldValue().toString();

        if(Validation.ChangeName(New, Old).equals(Old)){
            AlertBox.display("Error", "Invalid length");
            taskSelected.setItemName(Old);
        } else {
            taskSelected.setItemName(New);
        }
        tableView.refresh();
    }

    /*
    This is used to edit the cell for Value
    ChangeValue{
        Get taskSelected from the selected cell
        Create New string which takes the New Value
        Create Old string which takes the Old Value

        if(New string is not numeric){
            display Alertbox
            set cell to old string
        } else
            Create new Value float
            Create Decimal Format of #.##
            Create String called newString such that value has $ in front of it
            set cell to newString
        refresh table
    }
     */
    @FXML
    void ChangeValue(TableColumn.CellEditEvent editedCell) {
        Item taskSelected =  tableView.getSelectionModel().getSelectedItem();
        String New = editedCell.getNewValue().toString();
        String Old = editedCell.getOldValue().toString();

        if(Validation.ChangeValue(New, Old).equals(Old)){
            AlertBox.display("Error", "Data inputted must be numeric");
            taskSelected.setItemValue(Old);
        } else {
            taskSelected.setItemValue(Validation.ChangeValue(New, Old));
        }

        tableView.refresh();
    }

    /*
    This is to remove selected item
    OnClickRemove{
        Create ObservableList called selectedRows
        let selectedRows equal to selected row in table

        for(Item : selectedRows)
            remove
    }
     */
    @FXML
    void OnClickRemove(ActionEvent event) {
        ObservableList<Item> selectedRows;

        selectedRows = tableView.getSelectionModel().getSelectedItems();

        for (Item anotherTask: selectedRows){
            item.remove(anotherTask);
        }
    }

    /*
    setCellValueFactory for TableValue
    setCellValueFactory for TableSerialNum
    setCellValueFactory for TableName

    setItems from getItem

    set table as editable
    set cells in TableValue column as editable
    set cells in TableSerialNum column as editable
    set cells in TableName column as editable

    Create a FilteredList
    Call SearchBox and addListener
        setPredicate for filteredData
            if(newValue is null or empty)
                show all data

            Create string which takes the newValue to lower case

            if(serial Number and name contains the newValue)
                show it on table
            return false
         }
         Create SortedList which takes filteredData
         set SortedList
     }
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableValue.setCellValueFactory(new PropertyValueFactory<Item, String>("ItemValue"));
        TableSerialNum.setCellValueFactory(new PropertyValueFactory<Item, String>("ItemNumber"));
        TableName.setCellValueFactory(new PropertyValueFactory<Item, String>("ItemName"));

        tableView.setItems(getItem());

        tableView.setEditable(true);
        TableValue.setCellFactory(TextFieldTableCell.forTableColumn());
        TableSerialNum.setCellFactory(TextFieldTableCell.forTableColumn());
        TableName.setCellFactory(TextFieldTableCell.forTableColumn());

        FilteredList<Item> filteredData = new FilteredList<>(item, b->true);
        SearchBox.textProperty().addListener((observable, oldValue, newValue)->{
            filteredData.setPredicate(Item -> {
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if(Item.getItemName().toLowerCase().contains(lowerCaseFilter) || Item.getItemNumber().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                return false;
            });
        });

        SortedList<Item> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }

    /*
    This is used to add items to the table such that when run, there's 2 items already in the table
    getItem()
        item add

        return item
     */
    public ObservableList<Item> getItem(){
        item.add(new Item("$399.00", "AXB124AXY3", "XBox One"));
        item.add(new Item("$299.99", "S40AZBDE47", "Playstation"));

        return item;
    }

    /*
    This function is used to Open html document


    Get String called filename which takes the returned from OpenPopUp()
    if the filename does not contain html
        Add "html"
    Create File that opens the filename
    Create document which is null
    Create Observable Item for html
    try
        doc = the file
        Take the first table
        Take rows which are values enclosed in <tr></tr> in html file

        for(if i =0, i is less than row size, increase i by 1)
            get Element rows of i
            Get element columns which get values enclosed by <td>
            Create an array of Strings for each column
            Split the columns by " " and its limit is 3
            Add the items to the html Observable List
        setItems
     catch
        print error
     */
    @FXML
    void OpenHTML(ActionEvent event) throws IOException {
        String filename = OpenPopUP();

        if(!filename.isEmpty()) {
            if (!filename.contains("html")) {
                filename += ".html";
            }
            File fileOpen = new File(filename);
            Document doc = null;
            ObservableList<Item> html = FXCollections.observableArrayList();
            try {
                doc = Jsoup.parse(fileOpen, "UTF-8");
                Element table = doc.select("table").get(0);
                Elements rows = table.select("tr");

                for (int i = 1; i < rows.size(); i++) {
                    Element row = rows.get(i);
                    Elements cols = row.getElementsByTag("td");
                    String[] arrOfStr = cols.text().split(" ", 3);
                    html.add(new Item(arrOfStr[0], arrOfStr[1], arrOfStr[2]));
                }
                tableView.setItems(html);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    This function is used to open a json file and set the items in the json file to the table
    OpenJSON
        Get the filename which is the string returned from OpenPopUP()
        if(filename does not contain json)
            add json
        call ReadJson
        setitems
     */
    @FXML
    void OpenJSON(ActionEvent event) throws IOException {
        String filename = OpenPopUP();
        if(!filename.isEmpty()) {
            if (!filename.contains("json")) {
                filename += ".json";
            }
            tableView.setItems(JsonFile.readJson(filename));
        }
    }

    /*
    This function is used to open a TSV file which will then set the contents of the tsv file to the table

    Get String called filename which takes the returned from OpenPopUp()
    if the filename does not contain tsv
        Add "tsv"
    Create String for FieldDelimiter which is \t
    Create observable list of tsv
    try
        Create Buffered Reader br which reads the filename

        Create a string line
        while(there are lines in the file and is not null, read)
            Split by \t
            Add Item
            setItems
     catch
        print error
     */
    @FXML
    void OpenTSV(ActionEvent event) {
        String filename = OpenPopUP();

        if(!filename.isEmpty()) {
            if (!filename.contains("txt")) {
                filename += ".txt";
            }
            String FieldDelimiter = "\t";
            ObservableList<Item> tsv = FXCollections.observableArrayList();
            try {
                BufferedReader br = new BufferedReader(new FileReader(filename));

                String line;
                while ((line = br.readLine()) != null) {
                    String[] fields = line.split(FieldDelimiter, -1);

                    Item inventory = new Item(fields[0], fields[1], fields[2]);
                    tsv.add(inventory);
                    tableView.setItems(tsv);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /*
    This function will save the data in the table to a html file


    Get String called filename which takes the returned from OpenPopUp()
    if the filename does not contain html
        Add "html"

    Create new File which takes the filename
    try
        Create Filewriter which takes the file
        Create Writer which BufferWrites the writer

        String outpuut = "";
        Initialise the output with the css and <body>, <title> and whatever
        for(Item test: item)
            Add output which takes each itemvalue, number and Name in html form
        then add the last line of the html file which consists of closing the table, body
        write the output
        flush
     catch
        error
     */
    @FXML
    void SaveToHTML(ActionEvent event) {
        String filename = OpenPopUP();

        if(!filename.isEmpty()) {
            if (!filename.contains("html")) {
                filename += ".html";
            }
            File myObj = new File(filename);
            try {
                FileWriter writer = new FileWriter(myObj);
                Writer bw = new BufferedWriter(writer);

                String output = "";
                output += "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<head>\n" +
                        "<style>\n" +
                        "table {\n" +
                        "  font-family: arial, sans-serif;\n" +
                        "  border-collapse: collapse;\n" +
                        "  width: 100%;\n" +
                        "}\n" +
                        "\n" +
                        "td, th {\n" +
                        "  border: 1px solid #dddddd;\n" +
                        "  text-align: left;\n" +
                        "  padding: 8px;\n" +
                        "}\n" +
                        "</style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<table>\n" +
                        "  <tr>\n" +
                        "    <th>Value</th>\n" +
                        "    <th>Serial Number</th>\n" +
                        "    <th>Name</th>\n" +
                        "  </tr>\n";
                for (Item test : item) {
                    output += "<tr>\n" +
                            "    <td>" + test.getItemValue() + "</td>\n" +
                            "    <td>" + test.getItemNumber() + "</td>\n" +
                            "    <td>" + test.getItemName() + "</td>\n" +
                            "</tr>\n";
                }
                output += "</table>\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>\n";
                bw.write(output);

                bw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    This function saves the data of the table to a json file

    SaveToJSON
        Get String filename from the String returned by OpenPopUp
        if(the filename does not contain json)
            Add Json
        Call ToJson
     */
    @FXML
    void SaveToJSON(ActionEvent event) throws IOException {
        String filename = OpenPopUP();

        if(!filename.isEmpty()) {
            if (!filename.contains("json")) {
                filename += ".json";
            }
            JsonFile.ToJson(item, filename);
        }
    }

    /*
    This function saves the data of a table to a tsv file

    Create String filename which takes the String returned from OpenPopUP()

    if(the filename does not contain tsv)
        Add tsv to the filename
    Create a writer which is null
    try
        Create file which takes the filename
        Create a writer which is a Buffered Writer of the file
        for(item test: item)
            Create a String of text which gets their value and separated by tabs
            Write the text
     catch
        print error
     finally
        flush writer
        close writer
     */
    @FXML
    void SaveToTSV(ActionEvent event) throws IOException {
        String filename = OpenPopUP();
        if(!filename.isEmpty()) {
            if (!filename.contains("txt")) {
                filename += ".txt";
            }
            Writer writer = null;
            try {
                File file = new File(filename);
                writer = new BufferedWriter(new FileWriter(file));
                for (Item test : item) {
                    String text = test.getItemValue() + "\t" + test.getItemNumber() + "\t" + test.getItemName() + "\n";

                    writer.write(text);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                writer.flush();
                writer.close();
            }
        }
    }

    /*
    This function allows sorting of Name when the checkbox is checked

    if(the Checkbox is selected)
        set all the other checkbox as not selected
        remove TableValue and TableSerialNum column sort
        add TableName column sort
    else
        remove TableName column sort
     */
    @FXML
    void SortByName(ActionEvent event) {
        if(NameCheckbox.isSelected()){
            ValueCheckBox.setSelected(false);
            SerialNumberCheckbox.setSelected(false);
            tableView.getSortOrder().remove(TableValue);
            tableView.getSortOrder().remove(TableSerialNum);
            tableView.getSortOrder().add(TableName);
        } else {
            tableView.getSortOrder().remove(TableName);
        }
    }

    /*
    This function allows sorting the SerialNumber column when the checkbox is ticked

    if(the Checkbox is selected)
        set all the other checkbox as not selected
        remove TableValue and TableName column sort
        add TableSerialNum column sort
    else
        remove TableSerialNum column sort
     */
    @FXML
    void SortBySerialNumber(ActionEvent event) {
        if(SerialNumberCheckbox.isSelected()){
            NameCheckbox.setSelected(false);
            ValueCheckBox.setSelected(false);
            tableView.getSortOrder().remove(TableValue);
            tableView.getSortOrder().remove(TableName);
            tableView.getSortOrder().add(TableSerialNum);
        } else {
            tableView.getSortOrder().remove(TableSerialNum);
        }
    }

    /*
    This function allows the sorting of Values when the checkbox is ticked

    if(the Checkbox is selected)
        set all the other checkbox as not selected
        remove TableSerialNum and TableName column sort
        add TableValue column sort
    else
        remove TableValue column sort
     */
    @FXML
    void SortByValue(ActionEvent event) {
        if(ValueCheckBox.isSelected()){
            NameCheckbox.setSelected(false);
            SerialNumberCheckbox.setSelected(false);
            tableView.getSortOrder().remove(TableSerialNum);
            tableView.getSortOrder().remove(TableName);
            tableView.getSortOrder().add(TableValue);
        } else {
            tableView.getSortOrder().remove(TableValue);
        }
    }

    /*
    This function checks to see if there is a duplicate number for SerialNumbers

    CheckDuplicate which takes String s
        for(Item test: item)
            Create String called checkNum which gets the serialNumber
            if(checkNum is equal to string s)
                return false
        return true
     */
    public boolean CheckDuplicate(String s){
        for(Item test : item) {
            String checkNum = test.getItemNumber();
            if(checkNum.equals(s)) {
                return false;
            }
        }
        return true;
    }

    /*
    this function is used to create PopUp window

    String OpenPopUp
        Initialise filename as empty
        Create a TextInputDialog of textInput
        set its title
        set the Text
        Create an Optional String of result which shows the TextInputDialog
        Create a textfield
        filename is the text from textfield

        if(the input has a length of 0)
            Display an alert
        return filename
     */
    public String OpenPopUP(){
        String filename = "";
        TextInputDialog textInput = new TextInputDialog();
        textInput.setTitle("Enter File Name to Be Saved or Opened");
        textInput.getDialogPane().setContentText("File Name");
        Optional<String> result = textInput.showAndWait();
        TextField input = textInput.getEditor();
        filename = input.getText();

        if (input.getText().length() == 0){
            AlertBox.display("No File name entered", "Write the name of the filename to be saved or opened.");
        }

        return filename;
    }

    /*
    For JUnit
     */

    /*
    This function is made for JUnit to check whether 100 items can be added

    CountAll()
        Create an Observable List called TestForAdd
        Create an Integer count which equals to 0
        Create an int for Serial Number which equals to 1000000000

        for(int i=0; i<100; i++)
            SerialNumber increases by 1
            Create a String Number which makes SerialNumber as a String
            add Item to TestForAdd

        for(Item test: TestForAdd)
            count++
        return count;
     */
    static Integer CountAll(){
        ObservableList<Item> TestForAdd = FXCollections.observableArrayList();
        Integer count=0;
        int SerialNumber = 1000000000;

        for(int i=0; i<100; i++){
            SerialNumber++;
            String Number = String.valueOf(SerialNumber);
            TestForAdd.add(new Item("$399.99", Number, "Xbox One"));
        }

        for(Item test : TestForAdd) {
            count++;
        }
        return count;
    }

    /*
    This function is to JUnit removing items

    CountRemove()
        Create an Observable List called task
        Add task

        remove one task at index 1
        Count size
        return countsize
     */
    static Integer CountRemove(){
        ObservableList<Item> task = FXCollections.observableArrayList();

        task.add(new Item("$399.99", "AXB124AXY3", "Xbox One"));
        task.add(new Item("$399.99", "1000000000", "Xbox One"));

        task.remove(1);
        Integer CountRemove = task.size();

        return CountRemove;
    }

    /*
    This function is used to Search from Serial Num

    SearchSerialNum
        Create an Observable List called task
        Create a String called itemDetail which equals to nothing

        add tasks

        for(Item test: task)
            Create String called completion which gets the ItemNumber
            if(completion equals to AXB124AXY3)
                itemDetail = its Value, Number and Name
        return itemDetail
     */
    static String SearchSerialNum(){
        ObservableList<Item> task = FXCollections.observableArrayList();
        String itemDetail = "";

        task.add(new Item("$399.99", "AXB124AXY3", "Xbox One"));
        task.add(new Item("$399.99", "1000000000", "Xbox One"));


        for(Item test : task) {
            String completion = test.getItemNumber();
            if(completion.equals("AXB124AXY3"))
               itemDetail = test.getItemValue() + " " + test.getItemNumber() + " " + test.getItemName();
        }
        return itemDetail;
    }

    /*
    This function is used to Search from Name

    SearchSerialNum
        Create an Observable List called task
        Create a String called itemDetail which equals to nothing

        add tasks

        for(Item test: task)
            Create String called completion which gets the ItemNumber
            if(completion equals to Xbox One)
                itemDetail = its Value, Number and Name
        return itemDetail
     */
    static String SearchByName(){
        ObservableList<Item> task = FXCollections.observableArrayList();
        String itemDetail = "";

        task.add(new Item("$399.99", "AXB124AXY3", "Xbox One"));
        task.add(new Item("$399.99", "1000000000", "Playstation"));


        for(Item test : task) {
            String completion = test.getItemName();
            if(completion.equals("Xbox One"))
                itemDetail = test.getItemValue() + " " + test.getItemNumber() + " " + test.getItemName();
        }
        return itemDetail;
    }

    /*
    This function is used to CheckJson

    CheckJson()
        Create an Observable List called task
        add tasks

        Call ToJson function for file called "App.json)
        Create an Observable List called output which calls the readJson of App.json

        for(int i=0; i<size of task; i++)
            if(task equals to output)
                return 1
            else
                return 0;
     */
    static Integer CheckJson() throws IOException {
        ObservableList<Item> task = FXCollections.observableArrayList();
        task.add(new Item("$399.99", "AXB124AXY3", "Xbox One"));
        task.add(new Item("$399.99", "1000000000", "Playstation"));

        JsonFile.ToJson(task, "App.json");
        ObservableList<Item> output =  JsonFile.readJson("App.json");

        for (int i = 0; i < task.size(); i++){
            if(task.get(i).toString().equals(output.get(i).toString())){
                return 1;
            }else{
                return 0;
            }
        }
        return 0;
    }

    /*
    This function checks saved Json

    CheckSaveJson()
        Create an Observable List called task
        add tasks
        Call toJson function for "App.json"
        Createa  file
        Create a Scanner for the file

        if(file has content)
            return 1
        return 0;
     */
    static Integer CheckSaveJson() throws IOException {
        ObservableList<Item> task = FXCollections.observableArrayList();
        task.add(new Item("$399.99", "AXB124AXY3", "Xbox One"));
        task.add(new Item("$399.99", "1000000000", "Playstation"));

        JsonFile.ToJson(task, "App.json");

        File myObj = new File("App.json");
        Scanner myReader = new Scanner(myObj);
        if(myReader.hasNext()){
            return 1;
        }
        return 0;
    }

    /*
    This function is used to check whether table can be saved to TSV and read from TSV

    SaveAndReadTSV()
        Create an ObservableList called task
        task add

        Create a string for filename called Bleh
        if filename does not contain txt
            filename adds .txt
        Create a writer which is null

        try
            Createa a file which takes teh filename
            Create a writer which takes the file as BufferedWriter
            for(Item test: task)
                String text = format of tsv
                write text
        catch
            error
        finally
            flush writer
            writer close

        Create BufferedReader called br

        Create a String line
        Createa String called result which is ""
        while(there's a next line)
            Createa String[] called fields which separates contents by \t
            Add item from the String[]
            string result adds field[0] field[1] and field[2]
        return result
     */
    static String SaveAndReadTSV() throws IOException {
        ObservableList<Item> task = FXCollections.observableArrayList();
        task.add(new Item("$399.99", "AXB124AXY3", "Xbox One"));
        task.add(new Item("$399.99", "1000000000", "Playstation"));

        String filename = "Bleh";
        if (!filename.contains("txt")) {
            filename += ".txt";
        }
        Writer writer = null;
        try {
            File file = new File(filename);
            writer = new BufferedWriter(new FileWriter(file));
            for (Item test : task) {
                String text = test.getItemValue() + "\t" + test.getItemNumber() + "\t" + test.getItemName() + "\n";

                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            writer.flush();
            writer.close();
        }

        BufferedReader br = new BufferedReader(new FileReader("Bleh.txt"));

        String line;
        String result ="";
        while ((line = br.readLine()) != null) {
            String[] fields = line.split("\t", -1);

            Item inventory = new Item(fields[0], fields[1], fields[2]);
            result += fields[0] + "\t" + fields[1] + "\t" + fields[2] + "\n";
        }
        return result;
    }

    /*
    This function checks whether table can be saved to HTML and can load from HTML

    SaveAndReadHTML()
        Create an ObservableList called task
        add tasks

        Create a String called filename as Bleh
        if filename does not contain html
            Add .html to filename
        Create a writer which is ull
        try
            Create Writer
            Create a string output which has the first part of the html
            for(Item item: task)
                Add a string to output which has the test's Value, Number and name
            Add a string to output which is the end of the html file such as the </table> </body> and </html>
            write output
            flush writer
        catch
            Error
        Create a bufferedReader for Bleh.html
        Create a string called line
        Create a string called result which is equal to ""
        try
            while there is next line
            add line to result
        catch
            Error
        return result
     */
    static String SaveAndReadHTML() throws IOException {
        ObservableList<Item> task = FXCollections.observableArrayList();
        task.add(new Item("$399.99", "AXB124AXY3", "Xbox One"));
        task.add(new Item("$399.99", "1000000000", "Playstation"));

        String filename = "Bleh";
        if (!filename.contains("html")) {
            filename += ".html";
        }
        Writer writer = null;
        try {
            Writer bw = new BufferedWriter(new FileWriter("Bleh.html"));

            String output = "";
            output += "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<style>\n" +
                    "table {\n" +
                    "  font-family: arial, sans-serif;\n" +
                    "  border-collapse: collapse;\n" +
                    "  width: 100%;\n" +
                    "}\n" +
                    "\n" +
                    "td, th {\n" +
                    "  border: 1px solid #dddddd;\n" +
                    "  text-align: left;\n" +
                    "  padding: 8px;\n" +
                    "}\n" +
                    "</style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<table>\n" +
                    "  <tr>\n" +
                    "    <th>Value</th>\n" +
                    "    <th>Serial Number</th>\n" +
                    "    <th>Name</th>\n" +
                    "  </tr>\n";
            for (Item test : task) {
                output += "<tr>\n" +
                        "    <td>" + test.getItemValue() + "</td>\n" +
                        "    <td>" + test.getItemNumber() + "</td>\n" +
                        "    <td>" + test.getItemName() + "</td>\n" +
                        "</tr>\n";
            }
            output += "</table>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>\n";
            bw.write(output);

            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader br = new BufferedReader(new FileReader("Bleh.html"));
        String line;
        String result ="";
        try {
            while ((line = br.readLine()) != null) {
                result += line + "\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

}