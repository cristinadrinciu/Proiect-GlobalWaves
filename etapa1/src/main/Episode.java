package main;
import fileio.input.AudioFile;

public class Episode extends AudioFile{
	private Integer duration;
	private String description;

	public Episode() {
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
