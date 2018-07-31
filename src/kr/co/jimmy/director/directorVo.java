package kr.co.jimmy.director;

public class directorVo {

	private int directorno;
	private String directorname;

	public int getDirectorno() {
		return directorno;
	}

	public void setDirectorno(int directorno) {
		this.directorno = directorno;
	}

	public String getDirectorname() {
		return directorname;
	}

	public void setDirectorname(String directorname) {
		this.directorname = directorname;
	}

	@Override
	public String toString() {
		return "directorVo [directorno=" + directorno + ", directorname=" + directorname + "]";
	}
}
