package MusicPlayer;

import mainGUI.LocalMusicBlock;
import mainGUI.MusicPlayerBlock;
import mainGUI.MusicPlayerGUI;


public class CountingThread extends Thread {  

	public boolean stopped = true;  
	public static long currenttime;
	public static long spantime;

	public static final String INITIAL_LABEL_TEXT = "00:00:00";  
	public static boolean isfinished = false;

	public CountingThread() {  
		setDaemon(true);  
	}  

	@Override  
	public void run() {  
		while (true) {  
			if (!stopped) {  
				if((currenttime / 1000 )== spantime) {
					currenttime = 0;
					MP3Player.musicStop();
					isfinished = true;
					if(MusicPlayerGUI.localSelected) {
						if(isfinished) {
							if(MusicPlayerBlock.patternCombo.getSelectedIndex() == 0) {
								MP3Player.singleLoop();
								isfinished = false;
							}else if (MusicPlayerBlock.patternCombo.getSelectedIndex() == 1) {
								MP3Player.sequentPlay();
								isfinished = false;
							}else if (MusicPlayerBlock.patternCombo.getSelectedIndex() == 2) {
								MP3Player.randomPlay();
								isfinished = false;
							}
						}
					}
				}else {
					currenttime = System.currentTimeMillis() - MP3Player.programStart - MP3Player.pauseCount;
				}
				MusicPlayerBlock.currentlabel.setText(format(currenttime));
				spantime = MP3Player.getMusicSpanInt(LocalMusicBlock.selectedMusicpath);
				MusicPlayerBlock.slider.setValue((int)((float)(currenttime)/spantime));
			}
			try {  
				sleep(1);  // 1毫秒更新一次显示
			} catch (InterruptedException e) {  
				e.printStackTrace();  
				System.exit(1);  
			}  
		}
	}  


	private String format(long elapsed) {  
		int hour, minute, second;  
		elapsed = elapsed / 1000;  
		second = (int) (elapsed % 60);  
		elapsed = elapsed / 60;  
		minute = (int) (elapsed % 60);  
		elapsed = elapsed / 60;  
		hour = (int) (elapsed % 60);  
		return String.format("%02d:%02d:%02d", hour, minute, second);  
	}  

}  