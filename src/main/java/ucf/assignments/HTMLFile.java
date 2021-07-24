package ucf.assignments;

import javafx.collections.ObservableList;

import java.io.*;

public class HTMLFile {
    public static void ToHTML(ObservableList<Item> Item){
        File myObj = new File("App.json");
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
                    "  </tr>\n" +
                    "  <tr>";
            for(int i=0; i<Item.size(); i++){
                output += "<td>" + "" + "</td>";
            }
            bw.write(output);

            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
