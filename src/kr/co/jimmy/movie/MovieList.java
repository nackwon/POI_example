package kr.co.jimmy.movie;

import java.util.HashMap;

import org.codehaus.jackson.map.ObjectMapper;

import kr.or.kobis.kobisopenapi.consumer.rest.KobisOpenAPIRestService;
import kr.or.kobis.kobisopenapi.consumer.rest.exception.OpenAPIFault;

public class MovieList {
	
	public HashMap<String, Object> MovieList(KobisOpenAPIRestService service) throws OpenAPIFault, Exception{
		ObjectMapper mapper = new ObjectMapper();
		
		HashMap<String, Object> MovieList = null;
		String response = null;
		
		response = service.getMovieList(true, "1", "10", null, null, null, null, null, null, null, null);
		
		MovieList = mapper.readValue(response, HashMap.class);
		
		return MovieList;
	}
}
