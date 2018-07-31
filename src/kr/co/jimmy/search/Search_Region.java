package kr.co.jimmy.search;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import kr.co.jimmy.address.TheaterVo;
import kr.co.jimmy.address.TransferAddress;

public class Search_Region {

	private static String clientId = "85K3LBTERGnPmOpMLKtu";
	private static String clientSecret = "iyb1kOidfo";
	private static String search = "https://openapi.naver.com/v1/search/local?query=";

	public static ArrayList<TheaterVo> search_Region(String keyword, int number) {
		BufferedReader br;
		String inputLine;
		StringBuffer response = new StringBuffer();
		String result = null;
		int display = 100;
		String apiURL = null;
		ArrayList<TheaterVo> list = new ArrayList<TheaterVo>();
		try {
			String addr = URLEncoder.encode(keyword, "UTF-8");
			apiURL = search + addr + "&display=" + display + "&start=" + number;
			System.out.println(apiURL);
			number += display;
			URL url = new URL(apiURL);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			int responseCode = con.getResponseCode();
			if (responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			result = response.toString();
			br.close();
			JSONParser parser = new JSONParser();

			JSONObject jsonobj = (JSONObject) parser.parse(result);
			System.out.println(jsonobj.toString());
			JSONArray jsonarray = (JSONArray) jsonobj.get("items");
			
			for (int i = 0; i < jsonarray.size(); i++) {
				TheaterVo vo = new TheaterVo();
				JSONObject result1 = (JSONObject) jsonarray.get(i);
				String title = (String) result1.get("title");
				if(title.contains("메가박스") || title.contains("CGV") || title.contains("롯데")) {
					vo.setTheatername((String) result1.get("title"));
					vo.setTheateraddress((String) result1.get("address"));
					vo.setTheaterRoadaddress((String)result1.get("roadAddress"));
					list.add(vo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void main(String[] args) {
		ArrayList<TheaterVo> list = new ArrayList<TheaterVo>();
		ArrayList<TheaterVo> resultlist = new ArrayList<TheaterVo>();
		for (int i = 1; i < 900; i += 100) {
			list = search_Region("영화관",i);
			resultlist.addAll(list);
		}
		System.out.println(resultlist.size());
		TransferAddress.write_naver_theater(resultlist);
	}
}