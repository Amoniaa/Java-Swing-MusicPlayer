package musicModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class MusicSheet {

	private int sheetid;
	private String uuid;
	private String name;
	private String creatorId;
	private String creator;
	private String dateCreated;
	private String picture;
	// <MD5, Music file name>
	private Map<String, String> musicItems;
	
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public MusicSheet() {
		uuid = UUID.randomUUID().toString().replace("-", "");

		dateCreated = formatter.format(new Date());
	}
	
	public MusicSheet(String name) {
		this();
		this.name = name;
	}

	public MusicSheet(int sheetid, String uuid, String name, String creatorId, String creator, String dateCreated,
			String picture, Map<String, String> musicItems) {
		super();
		this.sheetid = sheetid;
		this.uuid = uuid;
		this.name = name;
		this.creatorId = creatorId;
		this.creator = creator;
		this.dateCreated = dateCreated;
		this.picture = picture;
		this.musicItems = musicItems;
	}
	
	public MusicSheet(String uuid, String name, String creatorId, String creator, String dateCreated,
			String picture, Map<String, String> musicItems) {
		super();
		this.uuid = uuid;
		this.name = name;
		this.creatorId = creatorId;
		this.creator = creator;
		this.dateCreated = dateCreated;
		this.picture = picture;
		this.musicItems = musicItems;
	}
	
	public MusicSheet(int sheetid, String uuid, String name, String creatorId, String creator, String dateCreated,
			String picture) {
		super();
		this.sheetid = sheetid;
		this.uuid = uuid;
		this.name = name;
		this.creatorId = creatorId;
		this.creator = creator;
		this.dateCreated = dateCreated;
		this.picture = picture;
	}

	public MusicSheet(String name, String creatorId, String creator, String picture) {
		super();
		this.uuid = UUID.randomUUID().toString().replace("-", "");
		this.name = name;
		this.creatorId = creatorId;
		this.creator = creator;
		this.dateCreated = formatter.format(new Date());
		this.picture = picture;
	}
	
	public int getSheetid() {
		return sheetid;
	}

	public void setSheetid(int sheetid) {
		this.sheetid = sheetid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Map<String, String> getMusicItems() {
		return musicItems;
	}

	public void setMusicItems(Map<String, String> musicItems) {
		this.musicItems = musicItems;
	}

	public void printMusicItems(Map<String,String> map) {
		//获取Map中的所有key与value的对应关系
        Set<Map.Entry<String,String>> entrySet = map.entrySet();
		Iterator<Map.Entry<String,String>> it =entrySet.iterator();
        while(it.hasNext()){
            //得到每一对对应关系
            Map.Entry<String,String> entry = it.next();
            //通过每一对对应关系获取对应的key
            String key = entry.getKey();
            //通过每一对对应关系获取对应的value
            String value = entry.getValue();
            System.out.println(key+"="+value);
        }
	}
	
	
	@Override
	public String toString() {
		return "MusicSheet [sheetid=" + sheetid + ", uuid=" + uuid + ", name=" + name + ", creatorId=" + creatorId
				+ ", creator=" + creator + ", dateCreated=" + dateCreated + ", picture=" + picture + ", musicItems="
				+ musicItems + "]";
	}

	
}