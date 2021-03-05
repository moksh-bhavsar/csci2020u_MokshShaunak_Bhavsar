package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;


public class Main extends Application {

    private Canvas bar;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        Scene scene = new Scene(root, 800, 600);

        // Creating new canvas
        bar = new Canvas();
        bar.widthProperty().bind(primaryStage.widthProperty());
        bar.heightProperty().bind(primaryStage.heightProperty());


        // calculating max in both the series
        double maxSeries1 = max(avgHousingPricesByYear);
        double maxSeries2 = max(avgCommercialPricesByYear);

        // calculating max from both the series
        double max = (maxSeries1 > maxSeries2) ? maxSeries1 : maxSeries2;

        // finding maximum height possible with some padding space
        double maxHeight = scene.getHeight() - 100;

        root.getChildren().add(bar);
        primaryStage.setTitle("Lab 06");
        primaryStage.setScene(scene);
        primaryStage.show();

        GraphicsContext gc = bar.getGraphicsContext2D();

        // to create bar chart
        for (int i=0; i<avgCommercialPricesByYear.length; i++){
            gc.setFill(Color.BLUE);
            // height calculated using the formula: current value*100/highest value
            double height1 = (avgCommercialPricesByYear[i]*maxHeight)/max;
            gc.fillRect(20+i*30, 600-height1, 10, height1);
            gc.setFill(Color.RED);
            double height2 = avgHousingPricesByYear[i]*maxHeight/max;
            gc.fillRect(10+i*30, 600-height2, 10, height2);
        }

        // calculating total
        int total = 0;
        for (int j : purchasesByAgeGroup) {
            total += j;
        }


        int curr_arc = total;
        // to calculate pie chart, we create a full circle with desired color and then start creating smaller arc with
        // appropriate color.
        for (int i=purchasesByAgeGroup.length-1; i>=0;i--){
            // get color from pieColours array
            gc.setFill(pieColours[i]);

            // angle of arc calculated using the formula: current value*360/total
            gc.fillArc(400, 150, 300, 300, 0, curr_arc*360/total, ArcType.ROUND);

            // updating curr_arc to create smaller arc and thus making the pie chart
            curr_arc -= purchasesByAgeGroup[i];
        }

    }

    private static double[] avgHousingPricesByYear = {
            247381.0,264171.4,287715.3,294736.1,
            308431.4,322635.9,340253.0,363153.7
    };
    private static double[] avgCommercialPricesByYear = {
            1121585.3,1219479.5,1246354.2,1295364.8,
            1335932.6,1472362.0,1583521.9,1613246.3
    };

    private static String[] ageGroups = {
            "18-25", "26-35", "36-45", "46-55", "56-65", "65+"
    };
    private static int[] purchasesByAgeGroup = {
            648, 1021, 2453, 3173, 1868, 2247
    };
    private static Color[] pieColours = {
            Color.AQUA, Color.GOLD, Color.DARKORANGE,
            Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM
    };

    private static double max(double[] array){
        double max = array[0];
        for (int i=0; i< array.length; i++){
            if (array[i] > max){
                max = array[i];
            }
        }
        return max;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
