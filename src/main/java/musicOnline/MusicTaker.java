package musicOnline;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import musicModel.Music;
import musicModel.MusicSheet;

public class MusicTaker {

	public static ArrayList<Music> getMusicsBySheetName(String sheetname)
	{
		ArrayList<Music> musics = new ArrayList<>();
		ArrayList<MusicSheet> musicsheets = MusicSheetTaker.getSharedMusicSheet(MusicSheetTaker.querynumber, MusicSheetTaker.URL);
		MusicSheet musicSheet = MusicSheetTaker.getSharedMusicSheetByName(sheetname, musicsheets);
		Iterator<Map.Entry<String, String>> it = musicSheet.getMusicItems().entrySet().iterator();  
		while (it.hasNext()) {  
			Map.Entry<String, String> entry = it.next();  
			//System.out.println("\t" + entry.getKey() + " - " + entry.getValue());
			musics.add(new Music(musicSheet.getSheetid(),entry.getValue(),entry.getKey()));
		}
		return musics;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Vector getRows(ArrayList<Music> musics)
	{
		Vector rows = new Vector();			
		for(int i = 0; i < musics.size(); i++){
			rows.addElement(getNextRow(musics.get(i)));
		}
		return rows;
	}


	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	private static Vector getNextRow(Music music)
	{
		Vector currentRow = new Vector();
		currentRow.addElement(music.getId());
		currentRow.addElement(music.getSheetid());
		currentRow.addElement(music.getName());
		currentRow.addElement(music.getMd5value());
		currentRow.addElement(music.getPath());
		currentRow.addElement("播放");
		currentRow.addElement("下载");
		return currentRow;
	}
}
