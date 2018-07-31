package kr.co.jimmy.Main;

import kr.co.jimmy.movie.MovieList;
import kr.or.kobis.kobisopenapi.consumer.rest.KobisOpenAPIRestService;

public class KobisMain {
	
	static String key = "f8c8304f59b2c2708e80a56028caf37f";
	
	public static void main(String[] args) {
		KobisOpenAPIRestService service = new KobisOpenAPIRestService(key);
		
		MovieList movie = new MovieList();
		try {
			System.out.println(movie.MovieList(service));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
