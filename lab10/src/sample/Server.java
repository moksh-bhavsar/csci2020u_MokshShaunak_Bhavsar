package sample;

import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;

public class Server extends Application {

    private int clientNo = 0;

    private TextArea listOfMessages = new TextArea();

    @Override
    public void start(Stage stage) throws Exception {

        ScrollPane scrollPane = new ScrollPane(listOfMessages);

        Button exit = new Button("exit");

        GridPane gridPane = new GridPane();

        gridPane.setHgap(10);
        gridPane.setVgap(20);

        gridPane.add(scrollPane,1,1,1,1);
        gridPane.add(exit,1,2,1,1);

        Scene scene = new Scene(gridPane, 450, 300);
        stage.setTitle("MultiThreadServer"); // Set the stage title
        stage.setScene(scene); // Place the scene in the stage
        stage.show(); // Display the stage

        new Thread( () -> {
            try {
                // Create a server socket
                ServerSocket serverSocket = new ServerSocket(8003);

                while (true) {
                    // Listen for a new connection request
                    Socket socket = serverSocket.accept();

                    // Increment clientNo
                    clientNo++;

                    Platform.runLater( () -> {

                        // Find the client's host name, and IP address
                        InetAddress inetAddress = socket.getInetAddress();
                    });

                    // Create and start a new thread for the connection
                    new Thread(new HandleAClient(socket)).start();
                }
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
        }).start();

        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Platform.exit();
            }
        });

    }

    // Define the thread class for handling new connection
    class HandleAClient implements Runnable {
        private Socket socket; // A connected socket

        // Construct a thread
        public HandleAClient(Socket socket) {
            this.socket = socket;
        }

        //Run thread
        public void run() {
            try {
                // Create data input stream
                DataInputStream inputFromClient = new DataInputStream(
                        socket.getInputStream());

                // Continuously serve the client
                while (true) {
                    String message = inputFromClient.readUTF();

                    Platform.runLater(() -> {
                        listOfMessages.setEditable(true);
                        listOfMessages.appendText(message+ '\n');
                        listOfMessages.setEditable(false);
                    });
                }
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
