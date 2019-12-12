package mainGUI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import musicDBUtil.DBUtility;
import musicModel.MusicSheet;
import musicOnline.FileDownloader;
import musicOnline.MusicSheetTaker;

public class MusicSheetDisplayBlock extends JPanel {

	private static final long serialVersionUID = 1L;
	private static JLabel musicSheetTitleLabel;
	private static JLabel musicSheetCreatorLabel;
	private static String imgpath;
	private static JPanel musicSheetInfoPanel;
	private static JPanel musicSheetButtonPanel;
	private static JLabel musicSheetPictureLabel;
	private static ImageIcon musicSheetPicture;
	private static int musicSheetPictureWidth;
	private static int musicSheetPictureHeight; 
	public static MusicDialogue dialog;
	public static UploadDialogue uploadDialogue;
	public static DownloadAllDialogue downloadAllDialogue;
	private static JButton addMusicButton = new JButton("添加歌曲");
	private static JButton deleteMusicButton = new JButton("删除歌曲");
	private static JButton uploadMusicButton = new JButton("上传歌单");
	private static JButton downloadMusicButton = new JButton("一键全部下载");
	
	public MusicSheetDisplayBlock() {
		
		imgpath = "./imgs/defult.jpg";
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		musicSheetPicture = new ImageIcon(imgpath);
		musicSheetPictureWidth = 250;
		musicSheetPictureHeight = 250 * musicSheetPicture.getIconHeight() / musicSheetPicture.getIconWidth();
		musicSheetPicture.setImage(musicSheetPicture.getImage().getScaledInstance(musicSheetPictureWidth,
				musicSheetPictureHeight, Image.SCALE_DEFAULT));
		musicSheetPictureLabel = new JLabel(musicSheetPicture);
		musicSheetPictureLabel.setPreferredSize(new Dimension(musicSheetPictureWidth, musicSheetPictureHeight));

		musicSheetInfoPanel = new JPanel();
		musicSheetInfoPanel.setLayout(new BoxLayout(musicSheetInfoPanel, BoxLayout.Y_AXIS));
		
		musicSheetTitleLabel = new JLabel("请选择歌单");
		musicSheetTitleLabel.setFont(new Font("等线", Font.BOLD, 30));
		musicSheetCreatorLabel = new JLabel(".		...于.		...创建");
		musicSheetCreatorLabel.setFont(new Font("等线", Font.BOLD, 20));
		musicSheetButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		musicSheetButtonPanel.setFont(new Font("等线", Font.PLAIN, 20));
		
		addMusicButton.setFont(new Font("等线", Font.PLAIN, 20));
		addMusicButton.addActionListener(event -> {
	         if (dialog == null) // first time
	            dialog = new MusicDialogue(dialog);
	         dialog.setVisible(true); // pop up dialog
	      });
		
		deleteMusicButton.setFont(new Font("等线", Font.PLAIN, 20));
		deleteMusicButton.addActionListener(event ->  {
			DBUtility.deleteMusic(LocalMusicBlock.selectedMusicid);
			LocalMusicBlock.updateTable(LocalMusicSheetBlock.id);
			MusicSheetDisplayBlock.updateDisplayPanel();
		});
		
		uploadMusicButton.setFont(new Font("等线", Font.PLAIN, 20));
		uploadMusicButton.addActionListener(event -> {
			if (uploadDialogue == null) // first time
				uploadDialogue = new UploadDialogue(uploadDialogue);
			uploadDialogue.setVisible(true); // pop up dialog
	      });
		
		downloadMusicButton.setFont(new Font("等线", Font.PLAIN, 20));
		downloadMusicButton.addActionListener(event ->  {
			if (downloadAllDialogue == null) // first time
				downloadAllDialogue = new DownloadAllDialogue(downloadAllDialogue);
			downloadAllDialogue.setVisible(true); // pop up dialog
		});
		
		musicSheetButtonPanel.add(addMusicButton);
		musicSheetButtonPanel.add(deleteMusicButton);
		musicSheetButtonPanel.add(uploadMusicButton);

		musicSheetInfoPanel.add(musicSheetTitleLabel);
		musicSheetInfoPanel.add(Box.createVerticalStrut(10));
		musicSheetInfoPanel.add(musicSheetCreatorLabel);
		musicSheetInfoPanel.add(Box.createVerticalStrut(50));
		musicSheetInfoPanel.add(Box.createHorizontalStrut(100));
		musicSheetInfoPanel.add(musicSheetButtonPanel);
		
		musicSheetButtonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

		this.add(musicSheetPictureLabel);
		this.add(musicSheetInfoPanel);
	}
	
	public static void updateDisplayPanel() {
		musicSheetButtonPanel.removeAll();
		musicSheetButtonPanel.add(addMusicButton);
		musicSheetButtonPanel.add(deleteMusicButton);
		musicSheetButtonPanel.add(uploadMusicButton);
		String owner = DBUtility.getResultFromSheetByName(MusicPlayerGUI.selectedname).getCreator();
		String date = DBUtility.getResultFromSheetByName(MusicPlayerGUI.selectedname).getDateCreated();
		String sheetname = DBUtility.getResultFromSheetByName(MusicPlayerGUI.selectedname).getName();
		imgpath = DBUtility.getResultFromSheetByName(MusicPlayerGUI.selectedname).getPicture();
		System.out.println(imgpath);
		musicSheetPicture = new ImageIcon(imgpath);
		musicSheetPictureWidth = 250;
		musicSheetPictureHeight = 250 * musicSheetPicture.getIconHeight() / musicSheetPicture.getIconWidth();
		musicSheetPicture.setImage(musicSheetPicture.getImage().getScaledInstance(musicSheetPictureWidth,
								   musicSheetPictureHeight, Image.SCALE_DEFAULT));
		musicSheetPictureLabel.setIcon(musicSheetPicture);
		musicSheetPictureLabel.setPreferredSize(new Dimension(musicSheetPictureWidth, musicSheetPictureHeight));
		musicSheetCreatorLabel.setText(owner + "于 " + date + "创建");
		musicSheetTitleLabel.setText(sheetname + "(共" + LocalMusicBlock.tablerows  +"首歌曲)");
	}
	
	public static void updateDisplayPanelShared() {
		
		musicSheetButtonPanel.removeAll();
		musicSheetButtonPanel.add(downloadMusicButton);
		MusicSheet musicsheet = MusicSheetTaker.getSharedMusicSheetByName(SharedMusicSheetBlock.selectedSheetName, MusicSheetTaker.getSharedMusicSheet(MusicSheetTaker.querynumber, MusicSheetTaker.URL));
		String owner = musicsheet.getCreator();
		String date = musicsheet.getDateCreated();
		String sheetname = musicsheet.getName();
		FileDownloader.downloadMusicSheetPicture("http://service.uspacex.com/music.server/downloadPicture",
				musicsheet.getUuid(), "./imgs");
		//if(musicsheet.getPicture() != null)
			//imgpath = "./imgs/defult.jpg";
		//else
		imgpath = "./imgs/" + musicsheet.getUuid() + ".jpg";
		System.out.println(musicsheet.getPicture());
		musicSheetPicture = new ImageIcon(imgpath);
		musicSheetPictureWidth = 250;
		musicSheetPictureHeight = 250 * musicSheetPicture.getIconHeight() / musicSheetPicture.getIconWidth();
		musicSheetPicture.setImage(musicSheetPicture.getImage().getScaledInstance(musicSheetPictureWidth,
								   musicSheetPictureHeight, Image.SCALE_DEFAULT));
		musicSheetPictureLabel.setIcon(musicSheetPicture);
		musicSheetPictureLabel.setPreferredSize(new Dimension(musicSheetPictureWidth, musicSheetPictureHeight));
		musicSheetCreatorLabel.setText(owner + "于 " + date + "创建");
		musicSheetTitleLabel.setText(sheetname + "(共" + LocalMusicBlock.tablerows  +"首歌曲)");
	}
	
	public static void updateDisplayPaneldeleted() {
		musicSheetButtonPanel.removeAll();
		musicSheetButtonPanel.add(addMusicButton);
		musicSheetButtonPanel.add(deleteMusicButton);
		musicSheetButtonPanel.add(uploadMusicButton);
		imgpath = "./imgs/defult.jpg";
		musicSheetPicture = new ImageIcon(imgpath);
		musicSheetPictureWidth = 250;
		musicSheetPictureHeight = 250 * musicSheetPicture.getIconHeight() / musicSheetPicture.getIconWidth();
		musicSheetPicture.setImage(musicSheetPicture.getImage().getScaledInstance(musicSheetPictureWidth,
								   musicSheetPictureHeight, Image.SCALE_DEFAULT));
		musicSheetPictureLabel.setIcon(musicSheetPicture);
		musicSheetPictureLabel.setPreferredSize(new Dimension(musicSheetPictureWidth, musicSheetPictureHeight));
		musicSheetTitleLabel.setText("请选择歌单");
		musicSheetCreatorLabel.setText(".		...于.		...创建");
	}
}
