package kr.co.jimmy.movie;

public class MovieVo {

	private int movieno;
	private int directorno;
	private int actorno;
	private String koname;
	private String enname;
	private int grade;
	private String genre;
	private String stroy;
	private String opening;
	private String poster;

	public int getMovieno() {
		return movieno;
	}

	public void setMovieno(int movieno) {
		this.movieno = movieno;
	}

	public int getDirectorno() {
		return directorno;
	}

	public void setDirectorno(int directorno) {
		this.directorno = directorno;
	}

	public int getActorno() {
		return actorno;
	}

	public void setActorno(int actorno) {
		this.actorno = actorno;
	}

	public String getKoname() {
		return koname;
	}

	public void setKoname(String koname) {
		this.koname = koname;
	}

	public String getEnname() {
		return enname;
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getStroy() {
		return stroy;
	}

	public void setStroy(String stroy) {
		this.stroy = stroy;
	}

	public String getOpening() {
		return opening;
	}
	
	@Override
	public String toString() {
		return "MovieVo [movieno=" + movieno + ", directorno=" + directorno + ", actorno=" + actorno + ", koname="
				+ koname + ", enname=" + enname + ", grade=" + grade + ", genre=" + genre + ", stroy=" + stroy
				+ ", opening=" + opening + ", poster=" + poster + "]";
	}

	public void setOpening(String opening) {
		this.opening = opening;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

}
