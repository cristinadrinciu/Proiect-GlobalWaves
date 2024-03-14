package audiofiles;

import java.util.stream.Stream;

public class AudioFile {
    private String name;
    private String type;

    public AudioFile(final String type) {
        this.type = type;
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

    /**
     *
     * @param type for the type of the audio file
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * getter for the type of the audio file
     * @return type
     */
    public String getType() {
        return type;
    }
}
