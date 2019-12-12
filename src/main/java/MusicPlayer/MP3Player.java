package MusicPlayer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Header;
import javazoom.jl.player.Player;
import mainGUI.LocalMusicBlock;
import mainGUI.MusicPlayerBlock;
import musicDBUtil.DBUtility;
import musicModel.Music;

public class MP3Player extends Thread {
	
	private String filename;
	public static Player player;
	public static MP3Player MP3player = new MP3Player(LocalMusicBlock.selectedMusicpath);
	public static boolean issuspend = false;
	public static boolean isstop = true;
	public static CountingThread thread;  
	// 记录程序开始时间  
	public static long programStart = System.currentTimeMillis();  
	// 程序一开始就是暂停的  
	public static long pauseStart = programStart;  
	// 程序暂停的总时间  
	public static long pauseCount = 0; 
	
	public MP3Player(String filename) {
		this.filename = filename;
	}

	@Override
	public void run() {
		super.run();
		try {
			BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(filename));
			player = new Player(buffer);
			player.play();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static int getMusicSpanInt(String path) {
		FileInputStream fis;
		int time = 0;
		try {
			fis = new FileInputStream(new File(path));
			int b = fis.available();
			Bitstream bt = new Bitstream(fis);
			Header h = bt.readFrame();
			time = (int) h.total_ms(b);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BitstreamException e) {
			e.printStackTrace();
		}
		return time/1000;
	}

	public static String getMusicSpan(String path) {
		int seconds = getMusicSpanInt(path);
		int temp=0;
		StringBuffer sb=new StringBuffer();
		temp = seconds/3600;
		sb.append((temp<10)?"0"+temp+":":""+temp+":");
		temp=seconds%3600/60;
		sb.append((temp<10)?"0"+temp+":":""+temp+":");
		temp=seconds%3600%60;
		sb.append((temp<10)?"0"+temp:""+temp);
		return sb.toString();
	}

	@SuppressWarnings("deprecation")
	public static void musicPlay() {
		if(!issuspend) {
			musicStop();
			MP3player = new MP3Player(LocalMusicBlock.selectedMusicpath);
			MP3player.start();
			isstop = false;
			thread.stopped = false; 
			pauseStart = programStart;  
			pauseCount = 0;  
			
			pauseCount += (System.currentTimeMillis() - pauseStart);  
			thread.stopped = false;
		}
		else {
			MP3player.resume();
			issuspend = false;
			isstop = false;
			thread.stopped = false; 
			pauseCount += (System.currentTimeMillis() - pauseStart);  
		}
	}

	@SuppressWarnings("deprecation")
	public static void musicSuspend() {
		MP3player.suspend();
		issuspend = true;
		isstop = false;
		pauseStart = System.currentTimeMillis();  
		thread.stopped = true;
	}

	@SuppressWarnings("deprecation")
	public static void musicStop() {
		MP3player.stop();
		MP3player = new MP3Player(LocalMusicBlock.selectedMusicpath);
		issuspend = false;
		isstop = true;
		MusicPlayerBlock.currentlabel.setText(MusicPlayerBlock.INITIAL_LABEL_TEXT);
		pauseStart = programStart;  
		pauseCount = 0;  
		thread.stopped = true;  
	}
	public static void singleLoop() {
		MP3Player.musicPlay();
	}
	
	public static void sequentPlay() {
		if(LocalMusicBlock.localrow < LocalMusicBlock.localtablerows - 1)
		{
			LocalMusicBlock.localrow++;
			LocalMusicBlock.musicTable.setRowSelectionInterval(LocalMusicBlock.localrow,LocalMusicBlock.localrow);
		}
		LocalMusicBlock.selectedMusicid = Integer.parseInt((String) LocalMusicBlock.musicTable.getValueAt(LocalMusicBlock.localrow, 0));
		LocalMusicBlock.selectedMusic = new Music(DBUtility.getMusicById(LocalMusicBlock.selectedMusicid));
		LocalMusicBlock.selectedMusicpath = LocalMusicBlock.selectedMusic.getPath();
		
		MP3Player.musicStop();
		MusicPlayerBlock.slider.setValue(0);
		MusicPlayerBlock.currentlabel.setText(MusicPlayerBlock.INITIAL_LABEL_TEXT); 
		CountingThread.spantime = MP3Player.getMusicSpanInt(LocalMusicBlock.selectedMusicpath);
		MusicPlayerBlock.musicspan.setText("/ " + MP3Player.getMusicSpan(LocalMusicBlock.selectedMusicpath));
		MP3Player.MP3player = new MP3Player(LocalMusicBlock.selectedMusicpath);
		MP3Player.musicPlay();
	}
	
	public static void randomPlay() {
		int tablerows = LocalMusicBlock.localtablerows;
		int randomrow;
		do {
			randomrow = getRandom(0, tablerows-1);
		}while(randomrow == LocalMusicBlock.localrow);
		if(LocalMusicBlock.localrow < LocalMusicBlock.localtablerows - 1)
		{
			LocalMusicBlock.localrow = randomrow;
			LocalMusicBlock.musicTable.setRowSelectionInterval(LocalMusicBlock.localrow,LocalMusicBlock.localrow);
		}

		LocalMusicBlock.selectedMusicid = Integer.parseInt((String) LocalMusicBlock.musicTable.getValueAt(LocalMusicBlock.localrow, 0));
		LocalMusicBlock.selectedMusic = new Music(DBUtility.getMusicById(LocalMusicBlock.selectedMusicid));
		LocalMusicBlock.selectedMusicpath = LocalMusicBlock.selectedMusic.getPath();
		
		MP3Player.musicStop();
		MusicPlayerBlock.slider.setValue(0);
		MusicPlayerBlock.currentlabel.setText(MusicPlayerBlock.INITIAL_LABEL_TEXT); 
		CountingThread.spantime = MP3Player.getMusicSpanInt(LocalMusicBlock.selectedMusicpath);
		MusicPlayerBlock.musicspan.setText("/ " + MP3Player.getMusicSpan(LocalMusicBlock.selectedMusicpath));
		MP3Player.MP3player = new MP3Player(LocalMusicBlock.selectedMusicpath);
		MP3Player.musicPlay();
	}
	
	public static int getRandom(int min, int max){
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		return s;
	}

}
