package ucf.assignments;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Leonie Alexandra
 */

public class JsonFile {
    /*
    ToJson(Observable List that wants to be in Json){
        try{
            Create a writer which connects to a Buffered Writer
            write the Item

            flush writer
        }
    }
     */
    public static void ToJson(ObservableList<Item> Item, String filename) throws IOException {
        File myObj = new File(filename);
        try {
            FileWriter writer = new FileWriter(myObj);
            Writer bw = new BufferedWriter(writer);
            bw.write(String.valueOf(Item));

            bw.flush();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    /*
    readJson(){
        Create reader which connects to a buffered reader which reads a Json
        Create a Gson builder
        Create an array of what is read
        return TransferContents
    }
     */
    public static ObservableList<Item> readJson(String filename) throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(filename));
        Gson gson = new Gson();
        Item[] enums = gson.fromJson(reader, Item[].class);

        return transferContents(enums);
    }

    /*
    transferContents
        Create Observable List
        loop through
            add
        return Observable List
     */
    public static ObservableList<Item> transferContents(Item[] enums){
        ObservableList<Item> Item = FXCollections.observableArrayList();
        for(Item test : enums) {
            Item.add(test);
        }
        return Item;
    }

}
