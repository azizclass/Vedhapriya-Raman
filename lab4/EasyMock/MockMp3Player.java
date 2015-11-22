import java.util.ArrayList;
 
 
public class MockMp3Player implements Mp3Player{
 
    private ArrayList songs;
    private double songtime = 0.0;
    private boolean isPlaying = false;
    private int currentSong = 0;
    
    
    public MockMp3Player(){
        this.songs = new ArrayList<String>();
    }
 
    @Override
    public void play() {
        if(this.songs.size() > 0){ 
            this.isPlaying = true;
            songtime += 0.1;
        }
    }
 
    @Override
    public void pause() {
        this.isPlaying = false;
    }
 
    @Override
    public void stop() {
        this.isPlaying = false;
        this.songtime = 0.0;
    }
 
    @Override
    public double currentPosition() {
        return this.songtime;
    }
  
    @Override
    public String currentSong() {
        return String.valueOf(this.songs.get(currentSong));
    }
 
    @Override
    public void next() {
        this.songtime = 0;
        if(this.currentSong < this.songs.size() - 1) 
            this.currentSong++;  
    }
 
    @Override
    public void prev() {
        this.songtime = 0;
        if(this.currentSong > 0) 
            this.currentSong--;
    }
 
    @Override
    public boolean isPlaying() {
        return this.isPlaying;
    }
 
    @Override
    public void loadSongs(ArrayList inputList) {
        for (int i=0; i<inputList.size();i++)
            this.songs.add(inputList.get(i));
    }
 
}
