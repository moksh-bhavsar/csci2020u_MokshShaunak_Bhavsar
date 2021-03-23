package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.security.spec.ECField;

public class Main extends Application {

    private TableView table = new TableView();
    private String currentFileName = "F:\\Software_Sys_Dev_Int\\csci2020u_MokshShaunak_Bhavsar\\lab08\\lab08.csv";
    private ObservableList<StudentRecord> marks = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        primaryStage.setTitle("Lab 05 Solutions");
        primaryStage.setScene(new Scene(root, 800, 600));

        // Creating a Border Pane which will include the tableView and MenuBar and Labels
        BorderPane modified = new BorderPane();

        // Creating Table Columns
        TableColumn SID = new TableColumn("SID");
        TableColumn assignments = new TableColumn("Assignments");
        TableColumn midterm = new TableColumn("Midterm");
        TableColumn finalExam = new TableColumn("Final Exam");
        TableColumn finalMark = new TableColumn("Final Mark");
        TableColumn letterGrade = new TableColumn("Letter Grade");

        table.getColumns().addAll(SID,assignments,midterm,finalExam,finalMark,letterGrade);

        // Adding table to center of the Border Pane
        modified.setCenter(table);

        // Creating label and Text Fields
        Label sid = new Label("SID");
        TextField inSid = new TextField();

        Label assignment = new Label("Assignment");
        TextField inAssignment = new TextField();

        Label usMidterm = new Label("Midterm");
        TextField inMidterm = new TextField();

        Label usFinalExam = new Label("Final Exam");
        TextField inFinalExam = new TextField();

        Button add = new Button("Add");

        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // create new record using user provided data
                StudentRecord record = new StudentRecord(inSid.getText(), Float.parseFloat(inAssignment.getText()), Float.parseFloat(inMidterm.getText()), Float.parseFloat(inFinalExam.getText()));
                marks.add(record);
                inSid.clear();
                inAssignment.clear();
                inMidterm.clear();
                inFinalExam.clear();
            }
        });

        // Creating grid pane to add labels and text fields
        GridPane input = new GridPane();

        input.setVgap(10);
        input.setHgap(10);

        input.add(sid,1,1);
        input.add(inSid,2,1);

        input.add(assignment,4,1);
        input.add(inAssignment,5,1);

        input.add(usMidterm,1,2);
        input.add(inMidterm,2,2);

        input.add(usFinalExam,4,2);
        input.add(inFinalExam,5,2);

        input.add(add,1,3);

        // adding grid pane to border pane
        modified.setBottom(input);

        // Creating menu bar
        MenuBar menuBar = new MenuBar();
        Menu file = new Menu("File");
        MenuItem newFile = new MenuItem("New");
        MenuItem open = new MenuItem("Open");
        MenuItem save = new MenuItem("Save");
        MenuItem saveAs = new MenuItem("Save as");
        MenuItem exit = new MenuItem("Exit");

        // Handling action of Open menuItem
        open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                File selectedFile = fileChooser.showOpenDialog(primaryStage);
                if (null != selectedFile) {
                    table.getItems().clear();
                    currentFileName = selectedFile.getAbsolutePath();
                    try {
                        load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // Handling action of save
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    File file = new File(currentFileName);
                    save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // Handling action of newFile
        newFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                table.getItems().clear();
            }
        });

        // Handling action of saveAs
        saveAs.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
                File selectedFile = fileChooser.showOpenDialog(primaryStage);
                try {
                    if (selectedFile != null){
                        currentFileName = selectedFile.getAbsolutePath();
                        save(selectedFile);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // Handling action of exit
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.exit();
            }
        });

        // adding menuItems to menuBar
        file.getItems().addAll(newFile,open,save,saveAs,exit);

        menuBar.getMenus().addAll(file);

        // Adding menuBar to top of Border Pane
        modified.setTop(menuBar);

        primaryStage.show();

        SID.setCellValueFactory(new PropertyValueFactory<StudentRecord,String>("studentID"));
        assignments.setCellValueFactory(new PropertyValueFactory<StudentRecord, Float>("assignments"));
        midterm.setCellValueFactory(new PropertyValueFactory<StudentRecord, Float>("midterm"));
        finalExam.setCellValueFactory(new PropertyValueFactory<StudentRecord, Float>("finalExam"));
        finalMark.setCellValueFactory(new PropertyValueFactory<StudentRecord, Float>("finalMark"));
        letterGrade.setCellValueFactory(new PropertyValueFactory<StudentRecord, Character>("finalGrade"));

        table.setItems(marks);

        Scene TableView = new Scene(modified,800,600);
        primaryStage.setScene(TableView);

        load();
    }

    private void save(File file) throws IOException {
        PrintWriter output = new PrintWriter(file);
        for (StudentRecord record:marks){
            String text = record.getStudentID() + "," + record.getMidterm() + "," + record.getAssignments() + "," + record.getFinalExam();

            output.println(text);
        }

        output.close();
    }

    private void load() throws IOException {
     String csvFile = currentFileName;

        BufferedReader input = new BufferedReader(new FileReader(csvFile));

        String line = null;
        while((line = input.readLine()) != null){
            String[] data = line.split(",");
            StudentRecord newRecord = new StudentRecord(data[0],Float.parseFloat(data[1]),
                                                        Float.parseFloat(data[2]), Float.parseFloat(data[3]));
            marks.add(newRecord);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
