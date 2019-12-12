package musicModel;

public class Music {

	private int id;
	private int sheetid;
	private String name;
	private String singer;
	private String md5value;
	private String Path;

	public Music(int sheetid, String name, String md5value) {
		super();
		this.sheetid = sheetid;
		this.name = name;
		this.md5value = md5value;
	}

	
	public Music(int sheetid, String name, String md5value, String path) {
		super();
		this.sheetid = sheetid;
		this.name = name;
		this.md5value = md5value;
		this.Path = path;
	}

	public Music(int id, int sheetid, String name, String md5value, String path) {
		super();
		this.id = id;
		this.sheetid = sheetid;
		this.name = name;
		this.md5value = md5value;
		this.Path = path;
	}

	public Music(Music music) {
		this.id = music.getId();
		this.sheetid = music.getSheetid();
		this.name = music.getName();
		this.md5value = music.getMd5value();
		this.Path = music.getPath();
	}

	public Music() {}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSheetid() {
		return sheetid;
	}

	public void setSheetid(int sheetid) {
		this.sheetid = sheetid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	public String getMd5value() {
		return md5value;
	}

	public void setMd5value(String md5value) {
		this.md5value = md5value;
	}

	public String getPath() {
		return Path;
	}

	public void setPath(String path) {
		Path = path;
	}

	@Override
	public String toString() {
		return "Music [id=" + id + ", sheetid=" + sheetid + ", name=" + name + ", md5value=" + md5value + ", Path="
				+ Path + "]";
	}


}
