
/**
 * Write a description of class ExerciseVideo here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ExerciseVideo
{
    String description;
    String title; 
    String link;
    int time; 
    String image;  
    
    ExerciseVideo(){
        this.description = "TBD";
        this.title = "TBD"; 
        this.link = "TBD";
        this.time = 0;
        this.image = "TBD"; 
    }
    
    ExerciseVideo(String description, String title, String link, int time, String image){
        this.description = description;
        this.title = title; 
        this.link = link; 
        this.time = time; 
        this.image = image;
    }
    
    public String toString(){
        return "Description: " + this.description + "\nTitle: " + this.title + "\nTime To Complete" + this.time; 
    }
}
