package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

import java.util.Map;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        Scene scene = new Scene(root, 500, 400);

        Canvas pie = new Canvas();
        pie.widthProperty().bind(scene.widthProperty());
        pie.heightProperty().bind(scene.heightProperty());

        root.getChildren().add(pie);
        primaryStage.setTitle("Lab 07");
        primaryStage.setScene(scene);
        primaryStage.show();

        GraphicsContext gc = pie.getGraphicsContext2D();

        // calculate total weather warnings
        int total = 0;
        Map<String, Integer> data = dataCollector.getData();
        for (String type:data.keySet()){
            total += data.get(type);
        }

        int cur_arc = total;
        int i = 0;
        for (String type : data.keySet()){
            // drawing text as legend
            gc.setFill(Color.BLACK);
            gc.fillText(type, 110, 65+i*50);

            // changing color
            gc.setFill(pieColours[i]);

            //creating rectangle with appropriate color for legend
            gc.fillRect(75,50+i*50,25,25);

            // creating arc of appropriate angle with appropriate color
            gc.fillArc(275,100,175,175,0,cur_arc*360/total, ArcType.ROUND);

            cur_arc -= data.get(type);
            i++;

            // if all the colours in pieColours are used
            if (i == pieColours.length-1){
                break;
            }
        }
    }

    private static Color[] pieColours = {
            Color.AQUA, Color.GOLD, Color.DARKORANGE,
            Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM
    };

    public static void main(String[] args) {
        launch(args);
    }
}
