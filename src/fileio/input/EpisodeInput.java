package fileio.input;

import audio.files.AudioFile;

public final class EpisodeInput extends AudioFile {
    private Integer duration;
    private String description;

    public EpisodeInput() {
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(final Integer duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
