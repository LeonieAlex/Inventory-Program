package ucf.assignments;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Item {
    public String ItemName, ItemValue, ItemNumber;

    /*
    Create function Item that takes ItemValue, ItemNumber and ItemName
        this ItemValue is ItemValue
        this ItemNumber is ItemNumber
        this ItemName is ItemName
     */
    public Item(String ItemValue, String ItemNumber, String ItemName) {
        this.ItemValue = ItemValue;
        this.ItemNumber = ItemNumber;
        this.ItemName = ItemName;
    }

    /*
    getItemValue function which is a string
        return ItemValue
     */
    public String getItemValue() {
        return ItemValue;
    }

    /*
    setItemValue which takes ItemValue String
        this ItemValue is ItemValue
     */
    public void setItemValue(String ItemValue) {
        this.ItemValue = ItemValue;
    }

    /*
    getItemNumber function which is a string
        return ItemNumber
     */
    public String getItemNumber() {
        return ItemNumber;
    }

    /*
    setItemNumber which takes ItemNumber String
       this ItemNumber is ItemValue
    */
    public void setItemNumber(String ItemNumber) {
        this.ItemNumber = ItemNumber;
    }

    /*
    getItemName function which is a string
        return ItemName
     */
    public String getItemName() {
        return ItemName;
    }

    /*
    setItemName which takes ItemName String
       this ItemName is ItemName
    */
    public void setItemName(String ItemName) {
        this.ItemName = ItemName;
    }

    /*
    Override
    Create toString function
        return json formatted text
    */
    @Override
    public String toString() {
        return "{\"ItemValue\":"
                + "\"" + ItemValue + "\""
                + ", \"ItemNumber\":"
                + "\"" + ItemNumber + "\""
                + ", \"ItemName\":"
                + "\"" + ItemName + "\"" + "}";
    }
}
