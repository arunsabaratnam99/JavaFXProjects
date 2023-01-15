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
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.scene.media.Media;  
import javafx.scene.media.MediaPlayer;  
import javafx.scene.media.MediaView;  
import java.io.File; 
import javafx.util.Duration;
import javafx.scene.control.Slider;
import javafx.beans.value. *;
/**
 * Write a description of JavaFX class Sounds here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Sounds extends Application
{
    //Creating all the nodes I will be using within the program such as the combo boxes, sliders, progressbar, arraylist etc.
    private ComboBox<String> speedBox = new ComboBox(); 
    private ComboBox comboBox = new ComboBox();

    private Slider volumeSlider = new Slider();

    private ProgressBar songProgressBar = new ProgressBar();

    public ArrayList<Songs> info = new ArrayList<Songs>();

    private int[] speeds = {25, 50, 75, 100, 125, 150, 175, 200}; 

    private Timer timer; 
    private TimerTask task; 
    private boolean running; 

    private Media media;
    private MediaPlayer mediaPlayer; 
    private MediaView mediaView; 

    private Text songTitle = new Text("Choose a song!");
    private TextArea description = new TextArea();

    private Image cover;
    private ImageView display;

    private Group root = new Group();

    int option = -1;
    boolean paused; 
    boolean goNext = false; 
    int checkIf;

    Color c = Color.web("#87ceeb");
 
    @Override
    public void start(Stage stage) throws Exception
    {
        //Setting the progress bar to 0 upon entering the program. If this isn't done the progress bar will glitch
        songProgressBar.setProgress(0);

        //Adding songs to the arraylist full of songs
        addSongs();

        //Setting the title of stage to Song Player Demo
        stage.setTitle("Song Player Demo");

        //Creating a vbox to store the combobox so I can add padding to it so it isn't attached to the top left
        VBox vbox = new VBox();

        //Prompting the user to use the combobox to choose a song
        comboBox.setPromptText("Choose a song");

        //Adding the titles of the songs that were added previously to the combobox
        for(int i = 0; i < info.size();i++){
            comboBox.getItems().add(info.get(i).title); 
        }

        //Setting up the description text area within the GUI
        description.setPrefSize(306,200);
        description.setLayoutX(250);
        description.setLayoutY(60);

        //Setting up the song title label within the GUI
        songTitle.setX(252);
        songTitle.setY(50);

        //Adding the combobox to the previously created vbox
        vbox.getChildren().add(comboBox);

        //Adding the padding to the vbox so it isnt attached to the top left 
        vbox.setPadding(new Insets(10, 0, 0, 10));

        //Adding a listener to the combobox so when the user changes the option within the 
        //combobox the code is triggered
        comboBox.setOnAction(e -> {
                //Setting speed to default when song is changed
                speedBox.getSelectionModel().select(3);
                
                //Setting the progress bar to zero when a new song is selected
                songProgressBar.setProgress(0);

                //Making a variable equal to the index of the selected item in the combobox
                option = comboBox.getSelectionModel().getSelectedIndex(); 

                //Setting the title of the song according to what is selected in the combobox
                songTitle.setText(info.get(option).title);
                songTitle.setFont(Font.font("Calibri", 25));

                //Clearing the description so when it is changed the texts won't overlap, then adding the description of the song that is selected
                description.clear();
                description.setWrapText(true);
                description.setEditable(false);
                description.appendText(info.get(option).description);

                //Formatting the seconds and minutes into proper mm:ss format
                int seconds = (info.get(option).length) % 60;
                int minutes =  (((info.get(option).length) / 60) % 60);

                String second = String.format("%02d", seconds); 

                //Adding the time to the description
                description.appendText("\n\nLength - " + minutes + " : " + second);

                //
                cover = new Image(info.get(option).image);

                display = new ImageView(cover); 

                display.setLayoutX(14);
                display.setLayoutY(56); 
                display.setFitHeight(215);
                display.setFitWidth(215);

                root.getChildren().add(display); 

                mediaPlayer.stop();

            });

        Image logo = new Image("FreeMindLogo.png"); 
        stage.getIcons().add(logo);

        ImageView imageview = new ImageView(logo); 
        imageview.setX(540);
        imageview.setY(7); 
        imageview.setFitHeight(40);
        imageview.setFitWidth(40);

        Button play = new Button("Play");
        play.setPrefSize(90,20);
        play.setLayoutX(14);
        play.setLayoutY(312);

        play.setOnAction(e -> playSong(option));

        Button pause = new Button("Pause");
        pause.setPrefSize(90,20);
        pause.setLayoutX(110);
        pause.setLayoutY(312);

        pause.setOnAction(e -> pauseSong());

        Button reset = new Button("Reset");
        reset.setPrefSize(90,20);
        reset.setLayoutX(206);
        reset.setLayoutY(312);

        reset.setOnAction(e -> resetSong());

        VBox speedvbox = new VBox(); 

        speedBox.setPromptText("100%"); 
        for (int i = 0; i < speeds.length; i++){
            speedBox.getItems().add(Integer.toString(speeds[i]) + "%"); 
        }

        speedvbox.getChildren().add(speedBox);
        speedvbox.setLayoutX(302);
        speedvbox.setLayoutY(312); 

        speedBox.setOnAction(this::changeSpeed); 

        volumeSlider.setLayoutX(398);
        volumeSlider.setLayoutY(316);
        volumeSlider.setPrefWidth(175);
        volumeSlider.setMax(100);
        volumeSlider.setMin(0); 
        volumeSlider.setValue(50); 

        volumeSlider.valueProperty().addListener(new ChangeListener<Number>(){
                @Override
                public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2){              
                    mediaPlayer.setVolume(volumeSlider.getValue() * 0.01); 
                }
            });

        songProgressBar.setPrefSize(567,17); 
        songProgressBar.setLayoutX(9);
        songProgressBar.setLayoutY(285); 

        root.getChildren().add(vbox); 
        root.getChildren().add(play); 
        root.getChildren().add(imageview); 
        root.getChildren().add(description);
        root.getChildren().add(songTitle);
        root.getChildren().add(pause); 
        root.getChildren().add(reset); 
        root.getChildren().add(speedvbox); 
        root.getChildren().add(volumeSlider); 
        root.getChildren().add(songProgressBar); 

        stage.setWidth(601);
        stage.setHeight(390);

        Scene scene = new Scene(root,c); 
        stage.setScene(scene);

        stage.show();  
    }

    public void playSong(int option){
        if (paused == true){
            beginTimer();

            if(option != checkIf){
                paused = false;
                playSong(option); 
            }

            mediaPlayer.play(); 

            paused = false; 
        }
        else {
            File f = new File(info.get(option).song); 
            media = new Media(f.toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            beginTimer(); 

            mediaPlayer.setVolume(volumeSlider.getValue() * 0.01); 

            mediaPlayer.play();

            checkIf = option; 

            paused = false; 

        }
    }

    public void pauseSong(){
        cancelTimer(); 
        mediaPlayer.pause(); 
        paused = true; 
    }

    public void resetSong(){
        songProgressBar.setProgress(0); 
        mediaPlayer.seek(Duration.ZERO); 
        mediaPlayer.play(); 
    }

    public void changeSpeed(ActionEvent event){
        mediaPlayer.setRate((Integer.parseInt(speedBox.getValue().substring(0, speedBox.getValue().length() -1))) * 0.01);
    }

    public void beginTimer(){
        timer = new Timer();

        task = new TimerTask(){
            public void run(){
                running = true; 
                double current = mediaPlayer.getCurrentTime().toSeconds(); 
                double end = media.getDuration().toSeconds(); 
                songProgressBar.setProgress(current/end);

                if (current/end == 1){
                    cancelTimer(); 
                }
            }
        }; 

        timer.scheduleAtFixedRate(task,0, 1);
    }

    public void cancelTimer(){
        running = false; 
        timer.cancel(); 
    }

    public void addSongs(){
        info.add(new Songs("a simple bunny girl", "Relax to some calming Lo-Fi beats by hit artist biosphere", 143, "a simple bunny girl.png", "a simple bunny girl.mp3")); 
        info.add(new Songs("Isabella's Lullaby", "Calm yourself with some calming instrumentals taken from The Promised Neverland Soundtrack", 149, "Isabella's Lullaby.png", "Isabella's Lullaby.mp3"));
        info.add(new Songs("Comet Observatory 3", "Get joyful with a upbeat melody from the Super Mario Galaxy soundtrack!", 122, "Comet Observatory 3.png", "Comet Observatory 3.mp3"));
        info.add(new Songs("Title Theme", "Zone out to the slow yet eloquent melody of the Title Theme from Legend of Zelda: Ocarina of Time, produced by Koji Kondo", 168, "Title Theme.png", "Title Theme.mp3"));
        info.add(new Songs("Relaxing sounds", "Go back to the basics and surround yourself with nature with this calming and peaceful natural sounds", 143, "Waterfall and Relaxing Birds Singing.png", "Waterfall and Relaxing Birds Singing.mp3"));

    }
}
