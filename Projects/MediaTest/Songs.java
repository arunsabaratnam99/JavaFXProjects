
public class Songs
{
    String title;
    String description;
    int length;
    String image;
    String song; 
    
    public Songs()
    {
        this.title = "TBD";
        this.description = "TBD";
        this.length = 0;
        this.image = "TBD";
        this.song = "TBD";
    }
    
    public Songs(String title, String description, int length, String image, String song){
        this.title = title;
        this.description = description;
        this.length = length;
        this.image = image; 
        this.song = song; 
    }
 
    public String toString(){
        
        return this.description + "\nLength: " + this.length;
    }
}
