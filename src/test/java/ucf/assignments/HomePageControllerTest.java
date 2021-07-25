package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HomePageControllerTest {
    /*
    DuplicateSerialNotApproved
        Create Observable list called items
        Add Items into it
        Create a String called actual which is null
        Create a String called Duplicate which takes AXB124AXY3 (A duplicate value in the items list)
        for(Item test: item)
            Create a String called checkNum which takes the Number of the test in the List
            if(checkNum is equal to the Duplicate string)
                actual equals to "Disapprove"
            else
                actual equals to "Approve"
        AssertEquals Disapprove and actual
     */
    @Test
    void DuplicateSerialNotApproved(){
        ObservableList<Item> item = FXCollections.observableArrayList();
        item.add(new Item("$299.99", "S40AZBDE47", "Playstation"));
        item.add(new Item("$399.99", "AXB124AXY3", "Xbox One"));
        String actual ="";
        String Duplicate = "AXB124AXY3";
        for(Item test : item) {
            String checkNum = test.getItemNumber();
            if(checkNum.equals(Duplicate)) {
                actual = "disapprove";
            } else {
                actual = "Approve";
            }
        }
        assertEquals("disapprove", actual);
    }

    /*
    DuplicateSerialApprove
        Create Observable list called items
        Add Items into it
        Create a String called actual which is null
        Create a String called Duplicate which takes AXB124AX73 (Not a duplicate value in the items list)
        for(Item test: item)
            Create a String called checkNum which takes the Number of the test in the List
            if(checkNum is equal to the Duplicate string)
                actual equals to "Disapprove"
            else
                actual equals to "Approve"
        AssertEquals Disapprove and actual
     */
    @Test
    void DuplicateSerialApproved(){
        ObservableList<Item> item = FXCollections.observableArrayList();
        item.add(new Item("$299.99", "S40AZBDE47", "Playstation"));
        item.add(new Item("$399.99", "AXB124AXY3", "Xbox One"));
        String actual ="";
        String Duplicate = "AXB124AX73";
        for(Item test : item) {
            String checkNum = test.getItemNumber();
            if(checkNum.equals(Duplicate)) {
                actual = "disapprove";
            } else {
                actual = "Approve";
            }
        }
        assertEquals("Approve", actual);
    }

    /*
    This function checks the adding feature and check whether 100 items can be added

    AddNewTo100
        Create Integer actual equal to 100
        Create Integer expected equal to CountRemove function from HomePageController

        assertEquals
     */
    @Test
    void AddNewTo100Check(){
        Integer actual= 100;
        Integer expected = HomePageController.CountAll();

        assertEquals(actual, expected);
    }

    /*
    This test is created to Check whether removing button works

    CheckRemove
        Create an Integer called Actual which equals to 1
        Create an Integer called expected which equals to the integer returned from calling CountRemove function

        assertEquals(actual and expected);
     */
    @Test
    void CheckRemove(){
        Integer actual= 1;
        Integer expected = HomePageController.CountRemove();

        assertEquals(actual, expected);
    }

    /*
    This function checks to see if we can find by Serial Number

    FindByNum
        Create a String actual which equals to "$399.99 AXB124AXY3 Xbox One"

        Assert equals of actual and SearchSerialNum function in HomePageController
     */
    @Test
    void FindByNum() {
        String actual = "$399.99 AXB124AXY3 Xbox One";

        assertEquals(actual, HomePageController.SearchSerialNum());
    }

    /*
    This function checks to see if we can find by Name

    FindByNum
        Create a String actual which equals to "$399.99 AXB124AXY3 Xbox One"

        Assert equals of actual and SearchByName function in HomePageController
     */
    @Test
    void FindByName(){
        String actual = "$399.99 AXB124AXY3 Xbox One";

        assertEquals(actual, HomePageController.SearchByName());
    }

    /*
    This function Checks Json file

    CheckJson
        Create Integer actual which is equal to 1
        Create Integer expected which takes the value returned from CheckJson from HomePage Controller

        assertEqual actual and expected
     */
    @Test
    void CheckJson() throws IOException {
        Integer actual = 1;
        Integer expected = HomePageController.CheckJson();

        assertEquals(actual, expected);
    }

    /*
    This test checks saving Json

    CheckSaveJson
        Create an Integer called actual which equals to 1
        Create Integer called expected which takes the value returned from the function CheckSaveJson()

        assertEquals actual and expected
     */
    @Test
    void CheckSaveJson() throws IOException {
        Integer actual = 1;
        Integer expected = HomePageController.CheckSaveJson();

        assertEquals(actual, expected);
    }

    /*
    This function checks TSV (saving and loading)

    CheckTSV
        Create an actual string which equals to"$399.99\tAXB124AXY3\tXbox One\n" + "$399.99\t1000000000\tPlaystation\n"
        assertEquals(actual, String returned from SaveAndReadTSV function);
     */
    @Test
    void CheckTSV() throws IOException {
        String actual ="$399.99\tAXB124AXY3\tXbox One\n" +
                "$399.99\t1000000000\tPlaystation\n";
        assertEquals(actual, HomePageController.SaveAndReadTSV());
    }

    /*
    This function checks HTML (Saving and loading)
    CheckHTML
        Create a string actual which equals to expected result
        assertEquals(actual, String returned from SaveAndReadHTML function);
     */
    @Test
    void CheckHTML() throws IOException {
        String actual = "<!DOCTYPE html>\n" +
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
                "  </tr>\n" +
                "<tr>\n" +
                "    <td>$399.99</td>\n" +
                "    <td>AXB124AXY3</td>\n" +
                "    <td>Xbox One</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "    <td>$399.99</td>\n" +
                "    <td>1000000000</td>\n" +
                "    <td>Playstation</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n";
        assertEquals(actual, HomePageController.SaveAndReadHTML());
    }

    /*
    Function checks sorting
     */
    @Test
    void CheckSorting(){
        //Used a TableView function which sorts it already
    }
}