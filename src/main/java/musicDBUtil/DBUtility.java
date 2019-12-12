package musicDBUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import musicModel.Music;
import musicModel.MusicSheet;

public class DBUtility {

	public static void main(String[] args){

		creatsheets();
	}
	
	public static ArrayList<Music> getResultFromMusic() {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList <Music> music = new ArrayList<>();
		try{
			Class.forName("org.sqlite.JDBC");   
			conn = DriverManager.getConnection("jdbc:sqlite:music.db");  		
			String sql = "select * from music;";
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) { 
				music.add(new Music(rs.getInt("id"),rs.getInt("sheetid"),rs.getString("name"),rs.getString("md5value"),rs.getString("path")));			
			}
		}
		catch( Exception e )
		{
			e.printStackTrace ( );
		}
		return music;
	}
	
	public static ArrayList<MusicSheet> getResultFromMusicSheet() {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList <MusicSheet> musicsheet = new ArrayList<>();
		try{
			Class.forName("org.sqlite.JDBC");   
			conn = DriverManager.getConnection("jdbc:sqlite:music.db");  		
			String sql = "select * from musicsheet;";
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) { 
				musicsheet.add(new MusicSheet(rs.getInt("sheetid"),rs.getString("uuid"),rs.getString("sheetname"),rs.getString("creatorId"),rs.getString("creator"),rs.getString("date"),rs.getString("imgpath")));
			}
		}
		catch( Exception e )
		{
			e.printStackTrace ( );
		}
		return musicsheet;
	}
	
	public static ArrayList<String> getPathsFromMusicBySheetId(int sheetid) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList <String> paths = new ArrayList<>();
		try{
			Class.forName("org.sqlite.JDBC");   
			conn = DriverManager.getConnection("jdbc:sqlite:music.db");  		
			String sql = "select path from music where sheetid = ?;";
			pst = conn.prepareStatement(sql);
			pst.setString(1,  String.valueOf(sheetid));
			rs = pst.executeQuery();
			
			while (rs.next()) { 
				paths.add(rs.getString("path"));			
			}
		}
		catch( Exception e )
		{
			e.printStackTrace ( );
		}
		return paths;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Vector getRows(){
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Vector rows = null;
		try{
			Class.forName("org.sqlite.JDBC");   
			conn = DriverManager.getConnection("jdbc:sqlite:music.db");  		
			String sql = "select * from music;";
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			rows = new Vector();	
			ResultSetMetaData rsmd = rs.getMetaData();			
			while(rs.next()){
				rows.addElement(getNextRow(rs,rsmd));
			}	
		}
		catch( Exception e )
		{
			e.printStackTrace ( );
		}
		return rows;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Vector getRowsByname(String name){
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Vector rows = null;
		try{
			Class.forName("org.sqlite.JDBC");   
			conn = DriverManager.getConnection("jdbc:sqlite:music.db");  		
			String sql = "select * from music where name = ?;";
			pst = conn.prepareStatement(sql);
			pst.setString(1,  String.valueOf(name));
			rs = pst.executeQuery();
			rows = new Vector();	
			ResultSetMetaData rsmd = rs.getMetaData();			
			while(rs.next()){
				rows.addElement(getNextRow(rs,rsmd));
			}	
		}
		catch( Exception e )
		{
			e.printStackTrace ( );
		}
		return rows;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Vector getRowsBySheetId(int sheetid){
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Vector rows = null;
		try{
			Class.forName("org.sqlite.JDBC");   
			conn = DriverManager.getConnection("jdbc:sqlite:music.db");  		
			String sql = "select * from music where sheetid = ?;";
			pst = conn.prepareStatement(sql);
			pst.setString(1,  String.valueOf(sheetid));
			rs = pst.executeQuery();
			rows = new Vector();	
			ResultSetMetaData rsmd = rs.getMetaData();			
			while(rs.next()){
				rows.addElement(getNextRow(rs,rsmd));
			}	
		}
		catch( Exception e )
		{
			e.printStackTrace ( );
		}
		return rows;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Vector getNextRow(ResultSet rs,ResultSetMetaData rsmd) throws SQLException{
		Vector currentRow = new Vector();
		for(int i = 1; i <= rsmd.getColumnCount(); i++){
			currentRow.addElement(rs.getString(i));
		}
		return currentRow;
	}

	public static synchronized int addMusic(Music music) {
		Connection conn = null;
		PreparedStatement pst = null;
		int result = 0;
		try{
			Class.forName("org.sqlite.JDBC");   
			conn = DriverManager.getConnection("jdbc:sqlite:music.db");  		
			String sql = "INSERT INTO `music` (`sheetid`,`name`,`md5value`,`path`)VALUES(?,?,?,?);"; //插入数据
			pst = conn.prepareStatement(sql);
			pst.setString(1, String.valueOf(music.getSheetid()));
			pst.setString(2, music.getName());
			pst.setString(3, music.getMd5value());
			pst.setString(4, music.getPath());
			result = pst.executeUpdate();
		}
		catch( Exception e )
		{
			e.printStackTrace ( );
		}
		return result;
	}

	public static synchronized int addMusicSheet(MusicSheet musicsheet) {
		Connection conn = null;
		PreparedStatement pst = null;
		int result = 0;
		try{
			Class.forName("org.sqlite.JDBC");   
			conn = DriverManager.getConnection("jdbc:sqlite:music.db");  		
			String sql = "INSERT INTO `musicsheet` (`uuid`,`sheetname`,`creatorId`,`creator`,`date`,`imgpath`)VALUES(?,?,?,?,?,?);"; //插入数据
			pst = conn.prepareStatement(sql);
			pst.setString(1, musicsheet.getUuid());
			pst.setString(2, musicsheet.getName());
			pst.setString(3, musicsheet.getCreatorId());
			pst.setString(4, musicsheet.getCreator());
			pst.setString(5, musicsheet.getDateCreated());
			pst.setString(6, musicsheet.getPicture());
			result = pst.executeUpdate();
		}
		catch( Exception e )
		{
			e.printStackTrace ( );
		}
		return result;
	}
	
	public static synchronized int deleteMusic(int id) {
		Connection conn = null;
		PreparedStatement pst = null;
		int result = 0;
		try{
			Class.forName("org.sqlite.JDBC");   
			conn = DriverManager.getConnection("jdbc:sqlite:music.db");  		
			String sql = "DELETE FROM `music` WHERE id = ?;"; 
			pst = conn.prepareStatement(sql);
			pst.setString(1, String.valueOf(id));
			result = pst.executeUpdate();
		}
		catch( Exception e )
		{
			e.printStackTrace ( );
		}
		return result;
	}
	
	public static synchronized int deleteMusicSheet(String name) {
		Connection conn = null;
		PreparedStatement pst = null;
		int result = 0;
		try{
			Class.forName("org.sqlite.JDBC");   
			conn = DriverManager.getConnection("jdbc:sqlite:music.db");  		
			String sql = "DELETE FROM `musicsheet` WHERE sheetname = ?;"; 
			pst = conn.prepareStatement(sql);
			pst.setString(1, name);
			result = pst.executeUpdate();
		}
		catch( Exception e )
		{
			e.printStackTrace ( );
		}
		return result;
	}
	
	public static ArrayList<Music> getResultFromMusicByName(String name) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList <Music> music = new ArrayList<>();
		try{
			Class.forName("org.sqlite.JDBC");   
			conn = DriverManager.getConnection("jdbc:sqlite:music.db");  		
			String sql = "select * from music where name = ?;";
			pst = conn.prepareStatement(sql);
			pst.setString(1,  String.valueOf(name));
			rs = pst.executeQuery();
			while (rs.next()) { 
				music.add(new Music(rs.getInt("id"),rs.getInt("sheetid"),rs.getString("name"),rs.getString("md5value"),rs.getString("path")));			
			}
		}
		catch( Exception e )
		{
			e.printStackTrace ( );
		}
		return music;
	}

	public static int getSheetIdBySheetName(String sheetname) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try{
			Class.forName("org.sqlite.JDBC");   
			conn = DriverManager.getConnection("jdbc:sqlite:music.db");  		
			String sql = "select sheetid from musicsheet where sheetname = ?;";
			pst = conn.prepareStatement(sql);
			pst.setString(1, sheetname);
			rs = pst.executeQuery();
			while (rs.next()) { 
				return rs.getInt("sheetid");
			}
			
		}catch( Exception e1 )
		{
			e1.printStackTrace ( );
		}
		return -1;
	}
	
	public static String getSheetNameBySheetId(int sheetid) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try{
			Class.forName("org.sqlite.JDBC");   
			conn = DriverManager.getConnection("jdbc:sqlite:music.db");  		
			String sql = "select sheetname from musicsheet where sheetid = ?;";
			pst = conn.prepareStatement(sql);
			pst.setString(1, String.valueOf(sheetid));
			rs = pst.executeQuery();
			while (rs.next()) { 
				return rs.getString("sheetname");
			}
			
		}catch( Exception e1 )
		{
			e1.printStackTrace ( );
		}
		return null;
	}
	
	public static Music getMusicById(int id) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Music music = new Music();
		try{
			Class.forName("org.sqlite.JDBC");   
			conn = DriverManager.getConnection("jdbc:sqlite:music.db");  		
			String sql = "select * from music where id = ?;";
			pst = conn.prepareStatement(sql);
			pst.setString(1,  String.valueOf(id));
			rs = pst.executeQuery();
			while (rs.next()) { 
				music.setId(id);
				music.setSheetid(rs.getInt("sheetid"));
				music.setName(rs.getString("name"));
				music.setMd5value(rs.getString("md5value"));
				music.setPath(rs.getString("path"));
			}
		}catch( Exception e1 )
		{
			e1.printStackTrace ( );
		}
		return music;
	}

	public static MusicSheet getResultFromSheetByName(String name) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		MusicSheet musicsheet = new MusicSheet();
		try{
			Class.forName("org.sqlite.JDBC");   
			conn = DriverManager.getConnection("jdbc:sqlite:music.db");  		
			String sql = "select * from musicsheet where sheetname = ?;";
			pst = conn.prepareStatement(sql);
			pst.setString(1,  String.valueOf(name));
			rs = pst.executeQuery();
			while (rs.next()) { 
				musicsheet.setSheetid(rs.getInt("sheetid"));
				musicsheet.setUuid(rs.getString("uuid"));
				musicsheet.setName(rs.getString("sheetname"));
				musicsheet.setCreator(rs.getString("creator"));
				musicsheet.setCreatorId(rs.getString("creatorId"));
				musicsheet.setDateCreated(rs.getString("date"));
				musicsheet.setPicture(rs.getString("imgpath"));
			}
		}
		catch( Exception e )
		{
			e.printStackTrace ( );
		}
		return musicsheet;
	}

	public static String[] getNameFromMusicSheet() {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList <String> sheetname = new ArrayList<>();
		try{	
			Class.forName("org.sqlite.JDBC");   
			conn = DriverManager.getConnection("jdbc:sqlite:music.db");  		
			String sql = "select * from musicsheet;";
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) { 
				sheetname.add(rs.getString("sheetname"));
			}
		}
		catch( Exception e )
		{
			e.printStackTrace ( );
		}
		return sheetname.toArray(new String[sheetname.size()]);
	}
	
	public static void print(ArrayList<?> array) {
		for(Object a :array) {
			System.out.println(a.toString());
		}
	}

	public static int creatsheets() {
		try{
			Class.forName("org.sqlite.JDBC");   
			Connection conn = DriverManager.getConnection("jdbc:sqlite:music.db");  
			Statement stat = conn.createStatement();
			stat.executeUpdate("pragma journal_mode = WAL");
			
			stat.executeUpdate("DROP TABLE IF EXISTS `music`;");
			stat.executeUpdate("CREATE TABLE `music` ("
					+ "`id` INTEGER NOT NULL,"
					+ "`sheetid` INTEGER NOT NULL,"
					+ "`name` varchar(100) DEFAULT NULL,"
					+ "`md5value` varchar(50) DEFAULT NULL,"
					+ "`path` varchar(50) DEFAULT NULL,"
					+ "PRIMARY KEY (`id`));");
			stat.executeUpdate( "INSERT INTO `music` (`sheetid`,`name`,`md5value`,`path`)" + 
					"VALUES" + 
					"('1','我们不一样.mp3','cdc8b7a47b61ce5c0eef1b050f6ce41c','./sound/我们不一样.mp3')," + 
					"('1','Alan Walker - Fade.mp3','0bcb24befd73757f6a769b43656789d6','./sound/Alan Walker - Fade.mp3')," + 
					"('1','纯音乐 - River Flows In You (钢琴).mp3','0bcb24befd73757f6a769b43656789d6','./sound/纯音乐 - River Flows In You (钢琴).mp3')," + 
					"('2','Bandari - Luna.mp3','0bcb24befd73757f6a769b43656789d6','./sound/Bandari - Luna.mp3')," + 
					"('2','陈忻杰 - 友情.mp3','0bcb24befd73757f6a769b43656789d6','./sound/陈忻杰 - 友情.mp3')," + 
					"('3','宗次郎 - 故郷の原風景.mp3','0bcb24befd73757f6a769b43656789d6','./sound/宗次郎 - 故郷の原風景.mp3');" );

			stat.executeUpdate("DROP TABLE IF EXISTS `musicsheet`;");
			stat.executeUpdate("CREATE TABLE `musicsheet` ("
					+ "`sheetid` INTEGER NOT NULL,"
					+ "`uuid` varchar(100) DEFAULT NULL,"
					+ "`sheetname` varchar(100) DEFAULT NULL,"
					+ "`creatorId` varchar(50) DEFAULT NULL,"
					+ "`creator` varchar(50) DEFAULT NULL,"
					+ "`date` varchar(50) DEFAULT NULL,"
					+ "`imgpath` varchar(50) DEFAULT NULL,"
					+ "PRIMARY KEY (`sheetid`));");
			stat.executeUpdate( "INSERT INTO `musicsheet` (`uuid`,`sheetname`,`creatorId`,`creator`,`date`,`imgpath`)" + 
					"VALUES" + 
					"('235edc3a68144beb8e8980e59941c470','歌单1','18776','张三','20190101','./imgs/1.jpg')," + 
					"('235edc3a68144beb8e8980e59941c470','歌单2','18777','张三','20190102','./imgs/2.jpg')," + 
					"('235edc3a68144beb8e8980e59941c470','歌单3','18778','张三','20190103','./imgs/3.jpg')," + 
					"('235edc3a68144beb8e8980e59941c470','歌单4','18779','张三','20190104','./imgs/2.jpg')," + 
					"('235edc3a68144beb8e8980e59941c470','歌单5','18780','张三','20190105','./imgs/3.jpg');"
					);
			conn.close();
			return 1;
		}
		catch( Exception e )
		{
			e.printStackTrace ( );
			return 0;
		}
	}
}
