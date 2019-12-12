package exp07.exp07;

import java.util.ArrayList;
import java.util.List;

import musicModel.MusicSheet;
import musicOnline.MusicSheetAndFilesUploader;



public class MusicSheetAndFilesUploaderTest {
	public static void main(String[] args) {
		//String url = "http://localhost:8080/music.server/upload";
		String url = "http://service.uspacex.com/music.server/upload";

		List<String> filePaths = new ArrayList<String>();
		/*
		filePaths.add("/Users/xiaodong/Music/Beyond/无声的告别.mp3");
		filePaths.add("/Users/xiaodong/Music/Beyond/AMANI.mp3");
		filePaths.add("/Users/xiaodong/Music/Beyond/命运是你家.mp3");
		filePaths.add("/Users/xiaodong/Music/Beyond/长城.mp3");
		filePaths.add("/Users/xiaodong/Music/Beyond/谁伴我闯荡.mp3");
		filePaths.add("/Users/xiaodong/Music/Beyond/旧日的足迹.mp3");
		filePaths.add("/Users/xiaodong/Music/Beyond/光辉岁月.mp3");
		
		filePaths.add("/Users/xiaodong/Music/guns and roses/14 Years.mp3");
		filePaths.add("/Users/xiaodong/Music/guns and roses/Breakdown.mp3");
		filePaths.add("/Users/xiaodong/Music/guns and roses/Estranged.mp3");
		filePaths.add("/Users/xiaodong/Music/guns and roses/Knockin' On Heaven's Door.mp3");
		*/
		
		filePaths.add("./sound/Alan Walker - Fade.mp3");



		
		MusicSheet ms = new MusicSheet();
		// ms.setUuid("cddc055bfa33439a889cb611c1cb6db2");
		ms.setCreatorId("20191205");
		//ms.setPicture("/Users/xiaodong/Music/Beyond/fig-beyond-band.jpg");
		//ms.setPicture("/Users/xiaodong/Music/guns and roses/fig-guns and roses.jpg");
		ms.setPicture("./imgs/1.jpg");
		
		ms.setCreator("20191205");
		ms.setName("20191205");

		MusicSheetAndFilesUploader.createMusicSheetAndUploadFiles(url, ms, filePaths);
	}
}
