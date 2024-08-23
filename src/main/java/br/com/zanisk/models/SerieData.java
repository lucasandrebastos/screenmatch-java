package br.com.zanisk.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record SerieData(@JsonAlias("Title") String title,
                        Integer totalSeasons,
                        String imdbRating
                        ) {
}
