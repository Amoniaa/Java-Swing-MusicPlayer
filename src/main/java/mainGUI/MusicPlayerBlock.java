package mainGUI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import musicModel.Music;
import MusicPlayer.CountingThread;
import MusicPlayer.MP3Player;
import musicDBUtil.DBUtility;

public class MusicPlayerBlock extends JPanel {

	private static final long serialVersionUID = 1L;
	public static final String INITIAL_LABEL_TEXT = "00:00:00";  
	public static JLabel currentlabel = new JLabel(INITIAL_LABEL_TEXT); 
	public static JSlider slider;
	public static JLabel musicspan;
	public static JComboBox<String> patternCombo = new JComboBox<>();

	public MusicPlayerBlock() {

		MP3Player.thread = new CountingThread();
		MP3Player.thread.start(); // 计数线程一直就运行着  
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(layout);
		slider = new JSlider(0,1000);
		slider.setValue(0);
		this.add(slider, "普通滑动条");
		JPanel musicPlayerPanel = new JPanel();
		musicPlayerPanel.setBorder(BorderFactory.createRaisedBevelBorder());

		musicPlayerPanel.setLayout(new FlowLayout());
		musicPlayerPanel.setBackground(new Color(39, 64, 128));
		JButton previousMusicButton = new JButton("上一首");
		previousMusicButton.setFont(new Font("等线", Font.PLAIN, 20));
		previousMusicButton.addActionListener(event ->  {
			if(LocalMusicBlock.row > 0)
			{
				LocalMusicBlock.row--;
				LocalMusicBlock.musicTable.setRowSelectionInterval(LocalMusicBlock.row,LocalMusicBlock.row);
			}
			System.out.println(LocalMusicBlock.row);
			System.out.println(LocalMusicBlock.tablerows);
			LocalMusicBlock.selectedMusicid = Integer.parseInt((String) LocalMusicBlock.musicTable.getValueAt(LocalMusicBlock.row, 0));
			LocalMusicBlock.selectedMusic = new Music(DBUtility.getMusicById(LocalMusicBlock.selectedMusicid));
			LocalMusicBlock.selectedMusicpath = LocalMusicBlock.selectedMusic.getPath();
			
			MP3Player.musicStop();
			MusicPlayerBlock.slider.setValue(0);
			MusicPlayerBlock.currentlabel.setText(MusicPlayerBlock.INITIAL_LABEL_TEXT); 
			CountingThread.spantime = MP3Player.getMusicSpanInt(LocalMusicBlock.selectedMusicpath);
			MusicPlayerBlock.musicspan.setText("/ " + MP3Player.getMusicSpan(LocalMusicBlock.selectedMusicpath));
			MP3Player.MP3player = new MP3Player(LocalMusicBlock.selectedMusicpath);
			MP3Player.musicPlay();
		});
		JButton playMusicButton = new JButton("播放");
		playMusicButton.setFont(new Font("等线", Font.PLAIN, 20));
		playMusicButton.addActionListener(event ->  {
			CountingThread.spantime = MP3Player.getMusicSpanInt(LocalMusicBlock.selectedMusicpath);
			MusicPlayerBlock.musicspan.setText("/ " + MP3Player.getMusicSpan(LocalMusicBlock.selectedMusicpath));
			MP3Player.musicPlay();
		});
		JButton suspendMusicButton = new JButton("暂停");
		suspendMusicButton.setFont(new Font("等线", Font.PLAIN, 20));
		suspendMusicButton.addActionListener(event ->  {
			MP3Player.musicSuspend();
		});
		JButton quitMusicButton = new JButton("停止");
		quitMusicButton.setFont(new Font("等线", Font.PLAIN, 20));
		quitMusicButton.addActionListener(event ->  {
			MP3Player.musicStop();
			slider.setValue(0);
			currentlabel.setText(INITIAL_LABEL_TEXT); 
		});
		JButton nextMusicButtonButton = new JButton("下一首");
		nextMusicButtonButton.setFont(new Font("等线", Font.PLAIN, 20));
		nextMusicButtonButton.addActionListener(event ->  {
			if(LocalMusicBlock.row < LocalMusicBlock.tablerows - 1)
			{
				LocalMusicBlock.row++;
				LocalMusicBlock.musicTable.setRowSelectionInterval(LocalMusicBlock.row,LocalMusicBlock.row);
			}
			System.out.println(LocalMusicBlock.row);
			System.out.println(LocalMusicBlock.tablerows);
			LocalMusicBlock.selectedMusicid = Integer.parseInt((String) LocalMusicBlock.musicTable.getValueAt(LocalMusicBlock.row, 0));
			LocalMusicBlock.selectedMusic = new Music(DBUtility.getMusicById(LocalMusicBlock.selectedMusicid));
			LocalMusicBlock.selectedMusicpath = LocalMusicBlock.selectedMusic.getPath();
			
			MP3Player.musicStop();
			MusicPlayerBlock.slider.setValue(0);
			MusicPlayerBlock.currentlabel.setText(MusicPlayerBlock.INITIAL_LABEL_TEXT); 
			CountingThread.spantime = MP3Player.getMusicSpanInt(LocalMusicBlock.selectedMusicpath);
			MusicPlayerBlock.musicspan.setText("/ " + MP3Player.getMusicSpan(LocalMusicBlock.selectedMusicpath));
			MP3Player.MP3player = new MP3Player(LocalMusicBlock.selectedMusicpath);
			MP3Player.musicPlay();
		});
		currentlabel.setText(INITIAL_LABEL_TEXT); 
		musicspan = new JLabel("/ " + MP3Player.getMusicSpan(LocalMusicBlock.selectedMusicpath));
		musicspan.setFont(new Font("等线", Font.BOLD, 20));
		musicspan.setForeground(Color.WHITE);
		currentlabel.setFont(new Font("等线", Font.BOLD, 20));
		currentlabel.setForeground(Color.WHITE);
		
		patternCombo = new JComboBox<>();
		patternCombo.setFont(new Font("等线", Font.PLAIN, 20));
		patternCombo.addItem("单曲循环");
		patternCombo.addItem("顺序播放");
		patternCombo.addItem("随机播放");

		JLabel playpattern = new JLabel();
		playpattern.setText("      播放模式");
		playpattern.setFont(new Font("等线", Font.BOLD, 20));
		playpattern.setForeground(Color.WHITE);
		
		musicPlayerPanel.add(previousMusicButton);
		musicPlayerPanel.add(playMusicButton);
		musicPlayerPanel.add(suspendMusicButton);
		musicPlayerPanel.add(quitMusicButton);
		musicPlayerPanel.add(nextMusicButtonButton);
		musicPlayerPanel.add(currentlabel);
		musicPlayerPanel.add(musicspan);
		musicPlayerPanel.add(playpattern);
		musicPlayerPanel.add(patternCombo);
		this.add(musicPlayerPanel);
	}
}