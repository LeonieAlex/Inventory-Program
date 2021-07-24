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

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

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

        if(!Validation.isNumeric(New)){
            AlertBox.display("Error", "Data inputted must be numeric");
            taskSelected.setItemValue(Old);
        } else {
            float newValue = Float.parseFloat(New);
            DecimalFormat df = new DecimalFormat("#.##");
            df.setMinimumFractionDigits(2);
            String newString = "$" + df.format(newValue);

            taskSelected.setItemValue(newString);
        }

        tableView.refresh();
    }

    /*
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

    public ObservableList<Item> getItem(){
        item.add(new Item("$399.00", "AXB124AXY3", "XBox One"));
        item.add(new Item("$299.99", "S40AZBDE47", "Playstation"));

        return item;
    }



    @FXML
    void OpenHTML(ActionEvent event) {

    }

    /*
    OpenJSON
        call ReadJson
     */
    @FXML
    void OpenJSON(ActionEvent event) throws IOException {
        tableView.setItems(JsonFile.readJson());
    }

    @FXML
    void OpenTSV(ActionEvent event) {

    }

    @FXML
    void SaveToHTML(ActionEvent event) {

    }

    /*
    SaveToJSON
        Call ToJson
     */
    @FXML
    void SaveToJSON(ActionEvent event) throws IOException {
        JsonFile.ToJson(item);
    }

    @FXML
    void SaveToTSV(ActionEvent event) {

    }

    /*
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
}
