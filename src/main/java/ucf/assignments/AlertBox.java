package ucf.assignments;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Leonie Alexandra
 */

public class AlertBox {
    /*
    Create a new function called display in which takes the title and message
    (this is to increase flexibility of what will pop up in the alert){
        Create a new Stage
        Specify its modality
        Set the title string as a Title
        Set a min width

        Create a new label and set it as the message
        Create a button
        Set action so that when the user clicks on the button, the window closes

        Create a new scene which calls the layout (a VBox which has both the label and the button)
        set Scene
        show and Wait
    }
     */
    public static void display(String title, String message){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);

        Label label = new Label();
        label.setText(message);
        Button close = new Button("Close");
        close.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, close);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}

