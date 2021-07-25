package ucf.assignments;

import java.text.DecimalFormat;

public class Validation {
    public static boolean isAlphaNumeric(String s){
        String pattern= "^[a-zA-Z0-9]*$";
        return s.matches(pattern);
    }

    public static boolean checkSerial(String s){
        return s.length() == 10;
    }

    public static boolean checkItemName(String s){
        return s.length() > 2 && s.length() < 256;
    }

    static String ChangeName(String New, String Old){
        if(checkItemName(New))
            return New;
        return Old;
    }

    static String ChangeNumber(String New, String Old){
        if(isAlphaNumeric(New) && checkSerial(New))
            return New;
        return Old;
    }

    public static boolean isNumeric(String s){
        String pattern = "^[0-9]*$";
        return s.matches(pattern);
    }

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
