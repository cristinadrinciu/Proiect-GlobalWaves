package AudioFiles;

public class AudioFile {
    private String name;

    public AudioFile() {
    }

    /**
     *
     * @param name for the name of the audio file
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * getter for the name of the audio file
     * @return name
     */
    public String getName() {
        return name;
    }
}
