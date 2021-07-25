package ucf.assignments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/*
Each inventory item shall have a value representing its monetary value in US dollars
Each inventory item shall have a unique serial number in the format of XXXXXXXXXX where X can be either a letter or digit
Each inventory item shall have a name between 2 and 256 characters in length (inclusive)
The user shall be able to edit the value of an existing inventory item
The user shall be able to edit the serial number of an existing inventory item
The user shall be able to edit the name of an existing inventory item
 */
class ValidationTest {

    @Test
    void ItIsAlphanumeric(){
        String serialNumber = "AXIJD29KD";
        Boolean expected = true;
        Boolean actual = Validation.isAlphaNumeric(serialNumber);

        assertEquals(actual, expected);
    }

    @Test
    void NotAlphanumeric(){
        String serialNumber = "A(38XKJE";
        Boolean expected = false;
        Boolean actual = Validation.isAlphaNumeric(serialNumber);

        assertEquals(actual, expected);
    }

    @Test
    void SerialNumIs10(){
        String serialNumber = "LKAKSJD394";
        Boolean expected = true;
        Boolean actual = Validation.checkSerial(serialNumber);

        assertEquals(expected, actual);
    }

    @Test
    void Not10(){
        String serialNumber = "LKAKSJD4";
        Boolean expected = false;
        Boolean actual = Validation.checkSerial(serialNumber);

        assertEquals(expected, actual);
    }

    @Test
    void ValidItemName(){
        String name = "alsdkfjlakgja";
        Boolean expected = true;
        Boolean actual = Validation.checkItemName(name);

        assertEquals(expected, actual);
    }

    @Test
    void NotValidName(){
        String name = "a";
        Boolean expected = false;
        Boolean actual = Validation.checkItemName(name);

        assertEquals(expected, actual);
    }

    @Test
    void ChangeNameApproved(){
        String Old = "aadsfasdf";
        String New = "aadsfasdfasfd";
        String actual = Validation.ChangeName(New, Old);
        assertEquals(New, actual);
    }

    @Test
    void ChangeNameNotApproved(){
        String Old = "aadsfasdf";
        String New = "a";
        String actual = Validation.ChangeName(New, Old);
        assertEquals(Old, actual);
    }

    @Test
    void ChangeNumberApproved(){
        String Old = "LKAKSJD394";
        String New = "AKDEI293KD";
        String actual = Validation.ChangeNumber(New, Old);
        assertEquals(New, actual);
    }

    @Test
    void ChangeNumberNotApproved(){
        String Old = "LKAKSJD394";
        String New = "IDK293*#KD";
        String actual = Validation.ChangeNumber(New, Old);
        assertEquals(Old, actual);
    }

    @Test
    void isNumericApproved(){
        String New = "2938";
        boolean actual = Validation.isNumeric(New);
        assertEquals(true, actual);
    }

    @Test
    void NotApprovedNumeric(){
        String New = "asd";
        boolean actual = Validation.isNumeric(New);
        assertFalse(actual);
    }

    @Test
    void ChangeValueApproved(){
        String Old = "$233.00";
        String New = "23";
        String result = "$23.00";
        String actual= Validation.ChangeValue(New, Old);
        assertEquals(result, actual);
    }

    @Test
    void ChangeValueNotApproved(){
        String Old = "$233.00";
        String New = "as";
        String actual= Validation.ChangeValue(New, Old);
        assertEquals(Old, actual);
    }
}