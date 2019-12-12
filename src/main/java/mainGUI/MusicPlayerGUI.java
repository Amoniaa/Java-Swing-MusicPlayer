package mainGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import MusicPlayer.CountingThread;
import MusicPlayer.MP3Player;
import musicDBUtil.DBUtility;
import musicModel.Music;

public class MusicPlayerGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	public static DownloadAllDialogue downloadAllDialogue;
	static JPanel westPanel = new JPanel();
	public static String selectedname = null;
	public static boolean localSelected = true;
	private AboutDialog dialog;

	class TestAction extends AbstractAction
	{
		private static final long serialVersionUID = 1L;
		public TestAction(String name)
		{
			super(name);
		}
		public void actionPerformed(ActionEvent event)
		{
			CountingThread.spantime = MP3Player.getMusicSpanInt(LocalMusicBlock.selectedMusicpath);
			MusicPlayerBlock.musicspan.setText("/ " + MP3Player.getMusicSpan(LocalMusicBlock.selectedMusicpath));
			MP3Player.musicPlay();
		}
	}

	public MusicPlayerGUI(String title) {
		this.setTitle(title);
		this.setSize(1300, 800);
		this.setLocation(100, 100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container container = this.getContentPane();
		container.setLayout(new BorderLayout(4, 4));
		container.setBackground(Color.WHITE);

		JMenu playMenu = new JMenu("播放器");
		JMenuItem playItem = playMenu.add(new PlayAction("播放") );
		playItem.setAccelerator(KeyStroke.getKeyStroke("ctrl P"));
		JMenuItem openItem = playMenu.add(new SuspendAction("暂停"));
		openItem.setAccelerator(KeyStroke.getKeyStroke("ctrl U"));
		JMenuItem stopItem = playMenu.add(new StopAction("停止"));
		stopItem.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
		playMenu.addSeparator();
		JMenuItem nextItem = playMenu.add(new NextAction("下一首"));
		nextItem.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
		JMenuItem backItem = playMenu.add(new BackAction("上一首"));
		backItem.setAccelerator(KeyStroke.getKeyStroke("ctrl B"));
		
		JMenu sheetMenu = new JMenu("歌单");
		JMenuItem newsheetItem = sheetMenu.add(new NewSheetAction("新建歌单"));
		newsheetItem.setAccelerator(KeyStroke.getKeyStroke("ctrl I"));
		JMenuItem deletesheetItem = sheetMenu.add(new DeleteSheetAction("删除歌单"));
		deletesheetItem.setAccelerator(KeyStroke.getKeyStroke("ctrl D"));
		sheetMenu.addSeparator();
		sheetMenu.add(new UpdateAction("上传"));
		sheetMenu.add(new DownloadAction("下载"));
		JMenu musicMenu = new JMenu("音乐");
		musicMenu.add(new AddAction("添加音乐"));
		musicMenu.add(new DeleteAction("删除音乐"));
		JMenu helpMenu = new JMenu("帮助");
		helpMenu.setMnemonic('H');
		JMenuItem aboutItem = new JMenuItem("关于我");
		
		aboutItem.addActionListener(event -> {
	         if (dialog == null)
	            dialog = new AboutDialog(MusicPlayerGUI.this);
	         dialog.setVisible(true);
	      });
		
		helpMenu.add(aboutItem);
		JMenu exit = new JMenu("程序");
		exit.add(new AbstractAction("退出")
		{
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent event)
			{
				System.exit(0);
			}
		});

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		menuBar.add(playMenu);
		menuBar.add(sheetMenu);
		menuBar.add(musicMenu);
		menuBar.add(helpMenu);
		menuBar.add(exit);

		/* WEST ************************************/
		westPanel.setPreferredSize(new Dimension(250, 800));
		BoxLayout westPanelLayout = new BoxLayout(westPanel, BoxLayout.Y_AXIS);
		westPanel.setLayout(westPanelLayout);
		westPanel.add(new SharedMusicSheetBlock());
		container.add("West", westPanel);

		/* SOUTH ************************************/
		JPanel southPanel = new JPanel();
		BoxLayout southPanelLayout = new BoxLayout(southPanel, BoxLayout.Y_AXIS);
		southPanel.setLayout(southPanelLayout);
		southPanel.add(new MusicPlayerBlock());
		container.add("South", southPanel);

		/* CENTER ************************************/
		JPanel centerPanel = new JPanel();
		BoxLayout centerPanelLayout = new BoxLayout(centerPanel, BoxLayout.Y_AXIS);
		centerPanel.setLayout(centerPanelLayout);
		centerPanel.add(new MusicSheetDisplayBlock());
		centerPanel.add(new LocalMusicBlock());
		container.add("Center", centerPanel);

		/* EAST ************************************/
		JPanel eastPanel = new JPanel();
		eastPanel.setPreferredSize(new Dimension(250, 800));
		BoxLayout eastPanelLayout = new BoxLayout(eastPanel, BoxLayout.Y_AXIS);
		eastPanel.setLayout(eastPanelLayout);
		eastPanel.add(new LocalMusicSheetBlock());
		eastPanel.add(new MusicSheetManagementBlock());
		eastPanel.add(new MusicDisplayBlock());
		container.add("East", eastPanel);
		ImageIcon imageIcon = new ImageIcon("./imgs/defult.jpg");
 
		// 设置标题栏的图标为face.gif
		this.setIconImage(imageIcon.getImage());
	}

	public static void main(String[] args) {
		
		new MusicPlayerGUI("音乐播放器").setVisible(true);
	}

	
	class PlayAction extends AbstractAction
	{
		private static final long serialVersionUID = 1L;
		public PlayAction(String name)
		{
			super(name);
		}
		public void actionPerformed(ActionEvent event)
		{
			CountingThread.spantime = MP3Player.getMusicSpanInt(LocalMusicBlock.selectedMusicpath);
			MusicPlayerBlock.musicspan.setText("/ " + MP3Player.getMusicSpan(LocalMusicBlock.selectedMusicpath));
			MP3Player.musicPlay();
		}
	}
	
	class SuspendAction extends AbstractAction
	{
		private static final long serialVersionUID = 1L;
		public SuspendAction(String name)
		{
			super(name);
		}
		public void actionPerformed(ActionEvent event)
		{
			MP3Player.musicSuspend();
		}
	}
	
	class StopAction extends AbstractAction
	{
		private static final long serialVersionUID = 1L;
		public StopAction(String name)
		{
			super(name);
		}
		public void actionPerformed(ActionEvent event)
		{
			MP3Player.musicStop();
			MusicPlayerBlock.slider.setValue(0);
			MusicPlayerBlock.currentlabel.setText(MusicPlayerBlock.INITIAL_LABEL_TEXT); 
		}
	}
	
	class NextAction extends AbstractAction
	{
		private static final long serialVersionUID = 1L;
		public NextAction(String name)
		{
			super(name);
		}
		public void actionPerformed(ActionEvent event)
		{
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
		}
	}
	
	class BackAction extends AbstractAction
	{
		private static final long serialVersionUID = 1L;
		public BackAction(String name)
		{
			super(name);
		}
		public void actionPerformed(ActionEvent event)
		{
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
		}
	}
	
	class NewSheetAction extends AbstractAction
	{
		private static final long serialVersionUID = 1L;
		public NewSheetAction(String name)
		{
			super(name);
		}
		public void actionPerformed(ActionEvent event)
		{
			if (MusicSheetManagementBlock.dialog == null) // first time
				MusicSheetManagementBlock.dialog = new SheetDialogue(MusicSheetManagementBlock.dialog);
			MusicSheetManagementBlock.dialog.setVisible(true); // pop up dialog
		}
	}
	
	class DeleteSheetAction extends AbstractAction
	{
		private static final long serialVersionUID = 1L;
		public DeleteSheetAction(String name)
		{
			super(name);
		}
		public void actionPerformed(ActionEvent event)
		{
			DBUtility.deleteMusicSheet(MusicPlayerGUI.selectedname);
			LocalMusicSheetBlock.updateList();
			LocalMusicBlock.updateTable(LocalMusicSheetBlock.id);
			MusicSheetDisplayBlock.updateDisplayPaneldeleted();
		}
	}
	
	class UpdateAction extends AbstractAction
	{
		private static final long serialVersionUID = 1L;
		public UpdateAction(String name)
		{
			super(name);
		}
		public void actionPerformed(ActionEvent event)
		{
			if (MusicSheetDisplayBlock.uploadDialogue == null) // first time
				MusicSheetDisplayBlock.uploadDialogue = new UploadDialogue(MusicSheetDisplayBlock.uploadDialogue);
			MusicSheetDisplayBlock.uploadDialogue.setVisible(true); // pop up dialog
		}
	}
	
	class DownloadAction extends AbstractAction
	{
		private static final long serialVersionUID = 1L;
		public DownloadAction(String name)
		{
			super(name);
		}
		public void actionPerformed(ActionEvent event)
		{
			if (downloadAllDialogue == null) // first time
				downloadAllDialogue = new DownloadAllDialogue(downloadAllDialogue);
			downloadAllDialogue.setVisible(true); // pop up dialog
		}
	}
	
	class AddAction extends AbstractAction
	{
		private static final long serialVersionUID = 1L;
		public AddAction(String name)
		{
			super(name);
		}
		public void actionPerformed(ActionEvent event)
		{
			if (MusicSheetDisplayBlock.dialog == null) // first time
				MusicSheetDisplayBlock.dialog = new MusicDialogue(MusicSheetDisplayBlock.dialog);
			MusicSheetDisplayBlock.dialog.setVisible(true); // pop up dialog
		}
	}
	
	class DeleteAction extends AbstractAction
	{
		private static final long serialVersionUID = 1L;
		public DeleteAction(String name)
		{
			super(name);
		}
		public void actionPerformed(ActionEvent event)
		{
			DBUtility.deleteMusic(LocalMusicBlock.selectedMusicid);
			LocalMusicBlock.updateTable(LocalMusicSheetBlock.id);
			MusicSheetDisplayBlock.updateDisplayPanel();
		}
	}
}
