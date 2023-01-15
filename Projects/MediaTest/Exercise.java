import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.web.WebView;
import javafx.scene.Group;
import javafx.scene.paint.Color; 
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination ;
import javafx.scene.image.*; 
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.scene.layout.VBox;
import java.util.*;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.*;
import javafx.scene.control.TextArea;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.geometry.Pos;
/**
 * Write a description of JavaFX class YouTubeTest here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Exercise extends Application
{
    ArrayList<ExerciseVideo> videos = new ArrayList<ExerciseVideo>();
    int index; 
    String link; 
    Color c = Color.web("#87ceeb");

    /**
     * The start method is the main entry point for every JavaFX application. 
     * It is called after the init() method has returned and after 
     * the system is ready for the application to begin running.
     *
     * @param  stage the primary stage for this application.
     */
    @Override
    public void start(Stage stage) throws Exception
    {         

        stage.setTitle("Excerise Demo"); 

        Group root = new Group();

        Scene scene = new Scene(root,c);

        addVideos();

        ComboBox comboBox = new ComboBox();
        for(int i = 0; i < videos.size();i++){
            comboBox.getItems().add(videos.get(i).title); 
        }

        VBox vbox = new VBox();
        vbox.getChildren().add(comboBox); 

        Button button = new Button("Choose Exercise"); 
        button.setLayoutX(345);
        button.setLayoutY(317);
        button.setPrefWidth(122);
        button.setPrefHeight(27);
        
        button.setOnAction(e -> {
                
               
                BorderPane border = new BorderPane(); 
                border.setPadding(new Insets(40, 10, 10, 10)); 
                
                Group root2 = new Group();
                Group right = new Group();
                Group top = new Group(); 

                index = comboBox.getSelectionModel().getSelectedIndex();

                link = videos.get(index).link; 
                WebView webview = displayVideo(link); 
                webview.setPrefSize(1300,900);
                root2.getChildren().add(webview); 

                Button back = new Button("Back");
                back.setPrefSize(122,27);
                back.setOnAction(e2 -> {
                    stage.setScene(scene); 
                });
                
                right.getChildren().add(back);
                
                Text title2 = new Text();
                title2.setText(videos.get(index).title);
                title2.setStyle("-fx-font-weight: bold");
                title2.setFont(Font.font("Calibri", 40));
                
                top.getChildren().add(title2); 
                
                border.setTop(top);
                border.setAlignment(top, Pos.TOP_CENTER); 
                
                border.setRight(right); 
                border.setAlignment(right,Pos.BOTTOM_RIGHT);

                border.setCenter(root2); 
                border.setAlignment(root2, Pos.CENTER); 
                
                border.setBackground(new Background(new BackgroundFill(c, null, null)));

                Scene scene2 = new Scene(border);
                     
                stage.setScene(scene2);
                stage.setFullScreen(true);
            });

        Button button2 = new Button("Exit Program"); 
        button2.setLayoutX(484);
        button2.setLayoutY(317);
        button2.setPrefWidth(122);
        button2.setPrefHeight(27);
        button2.setOnAction(this::exitButton);

        vbox.getChildren().add(button); 
        vbox.setPadding(new Insets(20, 0, 0, 10));


        Text title = new Text();
        Text time = new Text();
        TextArea description = new TextArea();
        description.setLayoutX(375);
        description.setLayoutY(75);
        description.setPrefHeight(200);
        description.setPrefWidth(200);

        comboBox.setOnAction(e -> {
                int option = comboBox.getSelectionModel().getSelectedIndex(); 

                title.setText(videos.get(option).title);
                title.setStyle("-fx-font-weight: bold");
                title.setX(400);
                title.setY(60); 
                title.setFont(Font.font("Calibri", 25));

                description.clear();
                description.setWrapText(true);
                description.setEditable(false);
                description.appendText(videos.get(option).description);

                int seconds = (videos.get(option).time) % 60;
                int minutes =  (((videos.get(option).time) / 60) % 60);
                
                String second = String.format("%02d", seconds); 

                time.setText("Time to complete - " + minutes + " : " + second );
                time.setX(375);
                time.setY(297); 
                time.setFont(Font.font("Ariel", 10));

                Image photo = new Image(videos.get(option).image);
                ImageView iv = new ImageView(photo); 
                iv.setLayoutX(10);
                iv.setLayoutY(65); 
                iv.setFitHeight(272);
                iv.setFitWidth(315);

                root.getChildren().add(iv); 
            }); 

        Image logo = new Image("FreeMindLogo.png"); 
        stage.getIcons().add(logo);

        ImageView imageview = new ImageView(logo); 
        imageview.setX(560);
        imageview.setY(7); 
        imageview.setFitHeight(40);
        imageview.setFitWidth(40);

        root.getChildren().add(title);
        root.getChildren().add(imageview); 
        root.getChildren().add(button);
        root.getChildren().add(vbox); 
        root.getChildren().add(description);
        root.getChildren().add(time);
        root.getChildren().add(button2);

        stage.setScene(scene);
        
        stage.setWidth(640);
        stage.setHeight(400);
        
        stage.setResizable(false);
        stage.show(); 
    }

  
    public void addVideos(){
        videos.add(new ExerciseVideo("Feeling anxious or nervous? This calming guided breathing video will allow you to stay relaxed, and into a calmed state.\n\nDeep Breaths video is guided by professional Ana Tyrkala from Nicklaus Children's Hospital ", "Deep Breaths", "https://www.youtube.com/embed/K353fkHYMPs", 82,"Deep Breaths.png"));
        videos.add(new ExerciseVideo("This video takes you on a journey within yourself and your mind. With a guided experience you won't even realize when the video has ended.\n\nTake a minute to find some peace. Tamara Levitt created this one minute meditation to help you relax.", "Body Scan", "https://www.youtube.com/embed/F7PxEy5IyV4", 59, "Body Scan.png"));
        videos.add(new ExerciseVideo("Take some time to relax with some Tai Chi, a series of gentle physical exercises and stretches connecting with the mind promoting its serenity through gentle movements.\n\nThis Tai Chi routine is guided by trained professional Tachi Genko", "Relaxing Tai Chi", "https://www.youtube.com/embed/rgsRPNHR9wk", 176, "Relaxing Tai Chi.png"));
        videos.add(new ExerciseVideo("A new type of way to calm and clear the mind, the practice of qigong cordinates slow stylized movements, deep diaphragmatic breathing, and calm mental focus, with visualization of guiding qi through the body.\n\nThis quick Qigong workout is guided my ShifuYanLei an experencied individual when it comes to Qigong.", "Qigong Calm", "https://www.youtube.com/embed/Tw4Cbo3ZR-U", 243, "Qigong Calm.png"));
        videos.add(new ExerciseVideo("A simple mind and body practice. Yoga combines physical poses, breathing techniques, and meditation or relaxation. The combination of all these facts results in mental and physical well-being.\n\nThis yoga routine is guided by popular YouTube channel, Yoga With Bird.", "Yoga", "https://www.youtube.com/embed/2WE-L8iyu0U", 336, "Yoga.jpg"));
    }

    public WebView displayVideo(String link){
        WebView webview = new WebView();
        webview.getEngine().load(link);

        return webview; 
    }

    private void exitButton(ActionEvent event)
    {
        System.exit(0); 
    }
    
}
