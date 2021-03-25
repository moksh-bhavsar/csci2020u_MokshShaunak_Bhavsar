package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Main extends Application {

    private float max1;
    private float max2;
    private final Canvas plot = new Canvas();
    private float maximum;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        primaryStage.setTitle("Lab 09: Stock Performance");

        InputStream stock1Input = downloadStockPrices("GOOG");
        InputStream stock2Input = downloadStockPrices("AAPL");

        BufferedReader input1 = new BufferedReader(new InputStreamReader(stock1Input));
        BufferedReader input2 = new BufferedReader(new InputStreamReader(stock2Input));

        ArrayList<Float> closingStock1 = new ArrayList<>();
        ArrayList<Float> closingStock2 = new ArrayList<>();
        String line;
        while((line = input1.readLine())!=null){
            String[] data = line.split(",");
            if (!data[0].equals("Date")){
                closingStock1.add(Float.parseFloat(data[4]));
            }
        }

        while((line = input2.readLine())!=null){
            String[] data = line.split(",");
            if (!data[0].equals("Date")){
                closingStock2.add(Float.parseFloat(data[4]));
            }
        }

        max1 = max(closingStock1);
        max2 = max(closingStock2);

        root.getChildren().add(plot);
        Scene scene = new Scene(root, 800 ,600);
        primaryStage.setScene(scene);
        primaryStage.show();

        plot.widthProperty().bind(primaryStage.widthProperty());
        plot.heightProperty().bind(primaryStage.heightProperty());

        drawLinePlot(closingStock1, closingStock2);

    }

    private InputStream downloadStockPrices(String shareTickerSymbol) throws IOException {
        String link = "https://query1.finance.yahoo.com/v7/finance/download/" + shareTickerSymbol
                + "?period1=1262322000&period2=1451538000&interval=1mo&events=history&includeAdjustedClose=true";

        URL netURL = new URL(link);

        URLConnection conn = netURL.openConnection();
        conn.setDoOutput(false);
        conn.setDoInput(true);


        return conn.getInputStream();
    }

    private void drawLinePlot(ArrayList<Float> stock1, ArrayList<Float> stock2){
        max1 = max(stock1);
        max2 = max(stock2);

        maximum = Math.max(max1, max2);

        GraphicsContext gc = plot.getGraphicsContext2D();

        gc.setStroke(Color.BLACK);

        // vertical axis
        gc.strokeLine(50,550,50,30);

        // horizontal axis
        gc.strokeLine(50,550,770, 550);

        gc.setStroke(Color.RED);
        plotLine(stock1);
        gc.setStroke(Color.BLUE);
        plotLine(stock2);

    }

    private float max(ArrayList<Float> prices){
        float max = 0;
        for (float price: prices){
            if (price >= max){
                max = price;
            }
        }

        return max;
    }

    private void plotLine(ArrayList<Float> prices){
        double x_scale = (plot.getWidth()-100)/prices.size();
        GraphicsContext gc = plot.getGraphicsContext2D();

        for (int i =1; i<prices.size(); i++){
            double y_1_coordinate = 550 - (prices.get(i-1)*500/maximum);
            double y_2_coordinate = 550 - (prices.get(i)*500/maximum);
            gc.strokeLine(50 + x_scale*(i-1),y_1_coordinate, 50 + x_scale*i,y_2_coordinate);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
