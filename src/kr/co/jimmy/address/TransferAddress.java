package kr.co.jimmy.address;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.StringJoiner;

import javax.swing.text.html.HTMLEditorKit.Parser;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TransferAddress {

	public static void write_naver_theater(ArrayList<TheaterVo> list) {
		String path = "new_theater_list.xls";
		
		try {
			File file = new File(path);
			FileOutputStream fileout = new FileOutputStream(file);

			HSSFWorkbook hworkbook = new HSSFWorkbook();
			HSSFSheet sheet = hworkbook.createSheet("TheaterList");

			HSSFRow curRow;
			int size = list.size();
			int j = 0;
			for (int i = 0; i < size; i++) {
				curRow = sheet.createRow(i);
				curRow.createCell(0).setCellValue(++j);
				curRow.createCell(1).setCellValue(list.get(i).getTheatername());
				curRow.createCell(2).setCellValue(list.get(i).getTheateraddress());
				curRow.createCell(3).setCellValue(list.get(i).getTheaterRoadaddress());
			}
			hworkbook.write(fileout);
			System.out.println("완료");
			fileout.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<TheaterVo> readexcel() {
		String path = "./new_theater_list.xls"; // 읽을 파일 경로
		ArrayList<TheaterVo> theaterlist = new ArrayList<TheaterVo>(); // 데이터를 담을 list

		try {
			File file = new File(path);
			FileInputStream inputStream = new FileInputStream(file);
			HSSFWorkbook hworkbook = new HSSFWorkbook(inputStream); // 2007 이전 버전(xls파일)
			// XSSFWorkbook xworkbook = new XSSFWorkbook(inputStream); // 2007 이후 버전(xlsx파일)

			HSSFSheet curSheet; // 현재 sheet
			HSSFCell curCell; // 현재 cell
			HSSFRow curRow; // 현재 row

			int sheetNumber = hworkbook.getNumberOfSheets(); // 엑셀 Sheet 총 갯수
			// System.out.println("sheetNumber : "+sheetNumber); Sheet 갯수 확인
			while (sheetNumber != 0) {
				sheetNumber--;

				curSheet = hworkbook.getSheetAt(sheetNumber);
				int row = curSheet.getPhysicalNumberOfRows();
				// System.out.println(row); 현재 sheet의 row 갯수 확인
				for (int i = 0; i < row; i++) {
					TheaterVo vo = new TheaterVo();
					curRow = curSheet.getRow(i);
					
					// vo의 setter를 이용해 담고 있습니다.
					// 여기서는 그대로 담는 것이 아니라 자료형에 맞춰서 형변환을 해주셔야 합니다.
					double theaterno = Double.valueOf(String.valueOf(curRow.getCell(0)));
					vo.setTheaterno((int)theaterno);
					vo.setTheatername(String.valueOf(curRow.getCell(1)));
					vo.setTheateraddress(String.valueOf(curRow.getCell(3)));
					// 아래의 brandno는 필요한 부분이라 따로 제가 만든 메소드 이기 떄문에 아래 2줄은 빼셔도 됩니다.
					int brandno = TransferAddress.brandno(String.valueOf(curRow.getCell(1)));
					vo.setBrandno(brandno);
					String address = TransferAddress.transfer(String.valueOf(curRow.getCell(2)));
					vo.setTheaterRoadaddress(String.valueOf(curRow.getCell(3)));
					if( address == "error") {
						address = TransferAddress.transfer(String.valueOf(curRow.getCell(3)));
					}
					System.out.println(theaterno+":"+address);
					vo.setTheaterxgps(address.split(":")[0].trim());
					vo.setTheaterygps(address.split(":")[1].trim());

					theaterlist.add(vo);
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return theaterlist;
	}

	public void writeexcel(ArrayList<TheaterVo> list) {

		String path = "transferTheaterList.xls"; // 저장할 파일 경로
		try {
			File file = new File(path);
			FileOutputStream fileout = new FileOutputStream(file);

			HSSFWorkbook hworkbook = new HSSFWorkbook();
			HSSFSheet sheet = hworkbook.createSheet("theaterList"); // sheet 생성

			HSSFRow curRow;

			int row = list.size(); // list 크기
			for (int i = 0; i < row; i++) {
				curRow = sheet.createRow(i); // row 생성
				curRow.createCell(0).setCellValue(list.get(i).getTheaterno()); // row에 각 cell에 저장
				curRow.createCell(1).setCellValue(list.get(i).getBrandno());
				curRow.createCell(2).setCellValue(list.get(i).getTheatername());
				curRow.createCell(3).setCellValue(list.get(i).getTheateraddress());
				curRow.createCell(4).setCellValue(list.get(i).getTheaterxgps());
				curRow.createCell(5).setCellValue(list.get(i).getTheaterygps());
			}
			hworkbook.write(fileout); // 파일 쓰기
			fileout.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 1. 메가박스
	// 2. CGV
	// 3. 롯데시네마
	// 4. 이외 시네마
	public static int brandno(String brandname) {
		int result = 0;

		if (brandname.equals("")) {
			return result;
		} else if (brandname.contains("메가박스")) {
			result = 1;
		} else if (brandname.contains("CGV")) {
			result = 2;
		} else if (brandname.contains("롯데시네마")) {
			result = 3;
		} else {
			result = 4;
		}
		return result;
	}

	// 좌표변환 naver API
	public static String transfer(String address) {
		String clientId = "85K3LBTERGnPmOpMLKtu";
		String clientSecret = "iyb1kOidfo";
		String path = "https://openapi.naver.com/v1/map/geocode?query=";
		String result = "";
		try {
			String addr = URLEncoder.encode(address, "UTF-8");
			String apiurl = path + addr;
			URL url = new URL(apiurl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			int responsecode = con.getResponseCode();
			String lon = "";
			String lat = "";

			BufferedReader br;

			if (responsecode == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			JSONParser parser = new JSONParser();
			JSONObject jsonobj = (JSONObject) parser.parse(response.toString());
			if(jsonobj == null)
				return "error";
			JSONObject tmp = (JSONObject)jsonobj.get("result");
			if(tmp == null) 
				return "error";
			JSONArray array = (JSONArray) tmp.get("items");
			for(int i=0;i<array.size();i++) {
				JSONObject result1 = (JSONObject) array.get(i);
				JSONObject point = (JSONObject) result1.get("point");
				double x = (double)point.get("x");
				double y = (double)point.get("y");
				
				result = x +":"+y;
			}
			br.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public static void main(String[] args) {
		TransferAddress transfer = new TransferAddress();
		ArrayList<TheaterVo> list = new ArrayList<TheaterVo>();
		list = transfer.readexcel();

		transfer.writeexcel(list);

		int size = list.size();
		for (int i = 0; i < size; i++) {
			System.out.println(list.get(i).toString());
		}
	}
}
