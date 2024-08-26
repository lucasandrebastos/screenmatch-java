package br.com.zanisk.models;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

public class Episode {
    private Integer season;
    private String title;
    private Integer episodeNumber;
    private Double imdbRating;
    private LocalDate releaseDate;

    public Episode(Integer seasonNumber, EpisodeData episodeData){
        this.season = seasonNumber;
        this.title = episodeData.title();
        this.episodeNumber = episodeData.episode();
        try{
            this.imdbRating = Double.valueOf(episodeData.imdbRating());
        } catch (NumberFormatException ex) {
            this.imdbRating = 0.0;
        }

        try{
            this.releaseDate = LocalDate.parse(episodeData.releaseDate());

        }catch (DateTimeException ex) {
            this.releaseDate = null;
        }


    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public Double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(Double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return
                "Season=" + season +
                ", release date=" + releaseDate +
                ", imdbRating=" + imdbRating +
                ", episodeNumber=" + episodeNumber +
                ", title='" + title + '\'' ;

    }
}
