package exp07.exp07;

public class MusicOnlinePlayTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "http://service.uspacex.com/music.server/music?md5=";
		String md5 = "77008b41f4c692808ac7b414722269e0";
		//http://service.uspacex.com/music.server/music?md5={md5value of music}
		MusicOnlinePlay.musicOnlinePlay(url, md5);
	}
}
