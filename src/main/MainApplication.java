package main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ToolBar;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javafx.geometry.*;

import javax.imageio.ImageIO;
import javax.tools.Tool;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;

public class MainApplication extends Application {

    private final String Title = "Get AwesomeShitZ";
    private final static int WIDTH = 800;
    private final static int HEIGHT = 600;

    private BorderPane layout;
    private Pane background;
//    private Canvas canvas;
    private ImageView foreground;
    private ToolBar toolBar;
    private MenuBar menu;
    private Button button;

//    pointer to the primaryStage
    private Stage stage;

    private int[] pix = new int[WIDTH * HEIGHT];

    /**
     * create the primaryStage and initialize all components in it
     * @param primaryStage
     * @throws Exception
     */
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        primaryStage.setTitle(Title);
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();

        foreground = new ImageView();

//        initialize toolbar
        toolBar = getToolBar();

//        initialize menubar
        menu = getMenu();

//        initialize components
        layout = new BorderPane();

//        layout.setPadding(new Insets(10));
        layout.setTop(menu);
        layout.setBottom(toolBar);
        layout.setCenter(foreground);

//        start the stage
        Scene scene = new Scene(layout, WIDTH, HEIGHT);
        scene.getStylesheets().add("/res/style/main_style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private MenuBar getMenu(){
        final Menu start = new Menu("Start");
        final Menu edit = new Menu("Edit");
        final Menu options = new Menu("Options");
        final Menu help = new Menu("Help");

        MenuBar tmp = new MenuBar(start, edit, options, help);

        return tmp;
    }

    private ToolBar getToolBar(){
        final Button button = new Button("Load image");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Choose an image");
//                FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("All Images", "*.png, *.jpg, *.jpeg");
//                fileChooser.getExtensionFilters().add(extensionFilter);
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("All Images", "*.*"),
                        new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                        new FileChooser.ExtensionFilter("GIF", "*.gif"),
                        new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                        new FileChooser.ExtensionFilter("PNG", "*.png")
                );
//                System.out.println(file);
                try{
//                    Image image = Toolkit.getToolkit().loadImage(fileChooser.getInitialDirectory().getPath(), WIDTH, HEIGHT, true, true);
//                    BufferedImage image = ImageIO.read(file);
                    File file = fileChooser.showOpenDialog(stage);
                    Image source = new Image(file.toURI().toString());
                    PixelReader reader = source.getPixelReader();

                    WritableImage destination = new WritableImage(WIDTH, HEIGHT);
                    PixelWriter writer = destination.getPixelWriter();

//                    TODO: scale image to size of imageview
//                    TODO: get size of imageview (to prevent iterate over full width and height
//                    reader.getPixels(0, 0, WIDTH, HEIGHT, );
                    for(int x = 0; x < WIDTH; ++x){
                        for(int y = 0; y < HEIGHT; ++y){
                            Color color = reader.getColor(x, y);
                            writer.setColor(x, y, color);
                        }
                    }
                    foreground.setImage(destination);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
//        button.getStyleClass().add("button");

        ToolBar tmp = new ToolBar(button);

        return tmp;
    }

    private void call(){
        System.out.println("Hello World!");
    }

    public static void main(String[] args){
        launch(args);
//        System.out.println("VisualBonds: " + Screen.getPrimary().getVisualBounds());
    }
}
