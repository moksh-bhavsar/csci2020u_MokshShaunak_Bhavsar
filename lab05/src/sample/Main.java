package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class Main extends Application {

    private TableView table = new TableView();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Lab 05 Solutions");
        primaryStage.setScene(new Scene(root, 300, 275));

        TableColumn SID = new TableColumn("SID");
        TableColumn assignments = new TableColumn("Assignments");
        TableColumn midterm = new TableColumn("Midterm");
        TableColumn finalExam = new TableColumn("Final Exam");
        TableColumn finalMark = new TableColumn("Final Mark");
        TableColumn letterGrade = new TableColumn("Letter Grade");

        table.getColumns().addAll(SID,assignments,midterm,finalExam,finalMark,letterGrade);

        Scene TableView = new Scene(table,300,275);
        primaryStage.setScene(TableView);

        primaryStage.show();

        SID.setCellValueFactory(new PropertyValueFactory<StudentRecord,String>("studentID"));
        assignments.setCellValueFactory(new PropertyValueFactory<StudentRecord, Float>("assignments"));
        midterm.setCellValueFactory(new PropertyValueFactory<StudentRecord, Float>("midterm"));
        finalExam.setCellValueFactory(new PropertyValueFactory<StudentRecord, Float>("finalExam"));
        finalMark.setCellValueFactory(new PropertyValueFactory<StudentRecord, Float>("finalMark"));
        letterGrade.setCellValueFactory(new PropertyValueFactory<StudentRecord, Character>("finalGrade"));

        ObservableList marks = DataSource.getAllMarks();
        table.setItems(marks);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
