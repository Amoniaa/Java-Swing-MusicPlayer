package exp07.exp07;

import musicOnline.FileDownloader;

/**
 * FileDownloader 测试类
 */
public class FileDownloaderTest {

	public static void main(String[] args) {
		/*FileDownloader.downloadMusicFile("http://service.uspacex.com/music.server/downloadMusic",
				"4c5e1725070f4ae0afa49de74dd9db75", "C:/Users/Lenovo/Desktop/download","FirstTest");
		System.out.println("下载成功");
		/*FileDownloader.downloadMusicSheetPicture("http://service.uspacex.com/music.server/downloadPicture",
				"235edc3a68144beb8e8980e59941c470", "/Users/xiaodong/Downloads");*/
		FileDownloader.downloadMusicSheetPicture("http://service.uspacex.com/music.server/downloadPicture",
				"2a2fa72e16c640d6a1e23a5cce67e98d", "./imgs");
	}
}