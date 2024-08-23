package br.com.zanisk.main;

import br.com.zanisk.models.SerieData;
import br.com.zanisk.service.ConsumeApi;
import br.com.zanisk.service.DataConverter;
import br.com.zanisk.models.SeasonData;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
		seasons.forEach(t -> t.episodes().forEach(e -> System.out.println(e.title())));

    }
}
