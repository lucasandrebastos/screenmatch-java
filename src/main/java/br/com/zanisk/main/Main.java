package br.com.zanisk.main;

import br.com.zanisk.models.Episode;
import br.com.zanisk.models.EpisodeData;
import br.com.zanisk.models.SerieData;
import br.com.zanisk.service.ConsumeApi;
import br.com.zanisk.service.DataConverter;
import br.com.zanisk.models.SeasonData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main  {
    private ConsumeApi consumeApi = new ConsumeApi();

    private final String API_KEY = "&apikey=8f43ebdd";

    private final String URL = "https://www.omdbapi.com/?t=";

    private Scanner scan = new Scanner(System.in);

    private DataConverter converter = new DataConverter();


    public void showMenu(){
        System.out.println("Digite o nome da série: ");
        var serieTitle = scan.nextLine().replace(" ", "+");
        var json = consumeApi.getData(URL + serieTitle + API_KEY);
        SerieData data = converter.getData(json, SerieData.class);
        System.out.println(data);

        List<SeasonData> seasons = new ArrayList<>();

		for (int i = 1; i <= data.totalSeasons(); i++){
			json = consumeApi.getData(URL + serieTitle + "&season=" + i +  API_KEY);
			SeasonData seasonData = converter.getData(json, SeasonData.class);
			seasons.add(seasonData);


		}
//		seasons.forEach(t -> t.episodes().forEach(e -> System.out.println(e.title())));

        List<EpisodeData> episodesData = seasons.stream()
                .flatMap(s ->s.episodes().stream())
                .collect(Collectors.toList());



        episodesData.stream()
                .filter(e -> !e.imdbRating().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(EpisodeData::imdbRating).reversed())
                .limit(5);


        List<Episode> episodes = seasons.stream()
                .flatMap(s ->s.episodes().stream()
                        .map(d -> new Episode(s.season(), d))
                ).collect(Collectors.toList());


        System.out.println("\nTOP 10 EPISÓDIOS");
        episodes.stream().sorted(Comparator.comparing(Episode::getImdbRating).reversed())
                .limit(10)
                .forEach(System.out::println);

        System.out.println("A partir de que ano você deseja ver os episódios? ");
        var year = scan.nextInt();
        scan.nextLine();

        LocalDate searchDate = LocalDate.of(year,1,1);

        DateTimeFormatter dTF = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodes.stream()
                .filter(e-> e.getReleaseDate() != null && e.getReleaseDate().isAfter(searchDate))
                .forEach(e -> System.out.println(
                        "Temporada:  " + e.getSeason() +
                                " Episódio: " + e.getTitle() +
                                " Data lançamento: " + e.getReleaseDate().format(dTF)
                ));

        Map<Integer, Double> ratingPerSeason = episodes.stream()
                .filter(e -> e.getImdbRating() >  0.0)
                .collect(Collectors.groupingBy(Episode::getSeason, Collectors.averagingDouble(Episode::getImdbRating)));
        System.out.println(ratingPerSeason);

        DoubleSummaryStatistics est = episodes.stream()
                .filter(e -> e.getImdbRating() > 0.0)
                .collect(Collectors.summarizingDouble(Episode::getImdbRating));
        System.out.println("Média: " + est.getAverage());
        System.out.println("Melhor ep: " + est.getMax());
        System.out.println("Pior ep: " + est.getMin());
        System.out.println("Quantidade: " + est.getCount());

    }


}
