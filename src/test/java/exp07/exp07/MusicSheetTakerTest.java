package exp07.exp07;

import musicModel.MusicSheet;
import musicOnline.MusicSheetTaker;

/**
 * MusicSheetTaker 测试类
 */
public class MusicSheetTakerTest {
	private static final String URL = "http://service.uspacex.com/music.server/queryMusicSheets?type=top20";

	public static void main(String[] args) throws Exception {

		/**
		 * 查询获取所有音乐单的UUID和名称
		 * 
		 */
		for (MusicSheet ms : MusicSheetTaker.queryMusicSheets(URL)) {
			System.out.println("[UUID] " + ms.getUuid() + "\t[Music sheet picture] " + ms.getPicture());
		}

	}
}
