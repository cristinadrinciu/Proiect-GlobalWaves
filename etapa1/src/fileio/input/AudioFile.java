package fileio.input;

public class AudioFile {
    private String name;

    public AudioFile (){
    }
    public AudioFile(String name) {
        this.name = name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
