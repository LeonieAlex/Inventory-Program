package ucf.assignments;

import java.text.DecimalFormat;

/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Leonie Alexandra
 */

public class Validation {
    /*
    This is used to check whether a string is alphanumeric

    isAlphaNumeric
        Create a string pattern which equals to "^[a-zA-Z0-9]*$"
        return whether s matches the pattern
     */
    public static boolean isAlphaNumeric(String s){
        String pattern= "^[a-zA-Z0-9]*$";
        return s.matches(pattern);
    }

    /*
    This function is used to check the length of Serial Number
    checkSerial
        return if s length is equal to 10
     */
    public static boolean checkSerial(String s){
        return s.length() == 10;
    }

    /*
    This function is used to check the length of Item Name
    checkItemName
        return if s length is between 2 and 256
     */
    public static boolean checkItemName(String s){
        return s.length() >= 2 && s.length() <= 256;
    }

    /*
    This function is used to check whether the New Or Old string would be returned
    ChangeName
        if New is valid
            return New
        return old if not
     */
    static String ChangeName(String New, String Old){
        if(checkItemName(New))
            return New;
        return Old;
    }

    /*
    This function is used to check whether the New or Old string would be returned for Serial Number
    ChangeNumber
        If New is alphanumeric and has a length of 10
            return new
        return old
     */
    static String ChangeNumber(String New, String Old){
        if(isAlphaNumeric(New) && checkSerial(New))
            return New;
        return Old;
    }

    /*
    this function is to check whether the input type is Numeric
    isNumeric
        Create a string pattern which equals to"^[0-9]*$" for whole numbers
        Create another string pattern which equals to  "^\\d*\\.\\d+|\\d+\\.\\d*$" for decimal numbers
        return if s matches either
     */
    public static boolean isNumeric(String s){
        String pattern = "^[0-9]*$";
        String pattern2 = "^\\d*\\.\\d+|\\d+\\.\\d*$";
        return s.matches(pattern) || s.matches(pattern2);
    }

    /*
    This function is used to check whether the New or Old string will be returned
    ChangeValue
        If the New is not Numeric
            return Old
        else
            Create a float called new Value which converts String to Float
            Create a decimal format of 2 decimal points
            Set the Minimum fraction digits as 2
            return a string of df with a $ in front of it.
     */
    public static String ChangeValue(String New, String Old){
        if(!isNumeric(New)){
            return Old;
        } else {
            float newValue = Float.parseFloat(New);
            DecimalFormat df = new DecimalFormat("#.##");
            df.setMinimumFractionDigits(2);
            return "$" + df.format(newValue);
        }
    }
}
