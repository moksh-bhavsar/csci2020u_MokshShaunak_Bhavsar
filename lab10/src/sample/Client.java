package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;

import java.io.*;
import java.net.Socket;

public class Client extends Application {

    //IO streams
    DataOutputStream inputToServer = null;

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Client");

        Text username = new Text("Username: ");
        TextField usernameTextField = new TextField();
        Text message = new Text("Message: ");
        TextField messageTextField = new TextField();
        Button send = new Button("Send");
        Button exit = new Button("Exit");

        GridPane gridPane = new GridPane();

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(username,1,1,1,1);
        gridPane.add(usernameTextField,2,1,1,1);
        gridPane.add(message,1,2,1,1);
        gridPane.add(messageTextField,2,2,1,1);
        gridPane.add(send,1,3,1,1);
        gridPane.add(exit,1,4,1,1);

        Scene scene = new Scene(gridPane,400,250);
        stage.setScene(scene);
        stage.show();

        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Platform.exit();
            }
        });

        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                try{
                    String name = usernameTextField.getText().trim();
                    String message = messageTextField.getText().trim();
                    String fullMessage = name + ": " + message;

                    inputToServer.writeUTF(fullMessage);
                    inputToServer.flush();

                }catch (IOException ex) {
                    System.err.println(ex);
                }

            }
        });

        try {
            //Create a socket to connect to the server
            Socket socket = new Socket("localhost" , 8003);

            //Create an output stream to send data to the server
            inputToServer = new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException ex){
            System.err.println(ex);
        }

    }
}
