package mainGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import musicModel.Music;
import myUtilities.MD5Creator;
import musicDBUtil.DBUtility;

public class MusicDialogue extends JDialog{

	private static final long serialVersionUID = 1L;
	private static String sheetname;
	private JLabel musicname;
	private JLabel musicPath;
	private JFileChooser chooser;
	private static String musicpath;
	private static String[] strArray = null;
	private static JPanel northPanel;
	private static JComboBox<String> musicsheetCombo;

	public MusicDialogue(MusicDialogue dialog)
	{
		super(dialog, "添加新歌曲", true);

		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		int screenWidth = screenSize.width/2; // 获取屏幕的宽
		int screenHeight = screenSize.height/2; // 获取屏幕的高
		int height = 520;
		int width = 480;
		this.setLocation(screenWidth-width/2,screenHeight-height/2);

		Container container = this.getContentPane();
		container.setLayout(new BorderLayout(0, 0));
		container.setBackground(Color.WHITE);
		ArrayList<String> listname = new ArrayList<>();
		for(int i = 0; i < DBUtility.getNameFromMusicSheet().length; i++) {
			listname.add(DBUtility.getNameFromMusicSheet()[i]);
		}
		strArray = (String[]) listname.toArray(new String[0]);  
		musicsheetCombo = new JComboBox<>(strArray);
		musicsheetCombo.addActionListener(event ->
		sheetname = musicsheetCombo.getItemAt(musicsheetCombo.getSelectedIndex()));
		northPanel = new JPanel();

		northPanel.setPreferredSize(new Dimension(300, 150));
		BoxLayout northPanelLayout = new BoxLayout(northPanel, BoxLayout.Y_AXIS);
		northPanel.setLayout(northPanelLayout);
		container.add("North", northPanel);
		northPanel.setLayout(new GridLayout(5, 2));

		JLabel chooseTitle = new JLabel("请选择歌单:");
		chooseTitle.setFont(new Font("等线", Font.PLAIN, 20));
		northPanel.add(chooseTitle);

		musicsheetCombo.setFont(new Font("等线", Font.PLAIN, 20));
		northPanel.add(musicsheetCombo);

		JLabel fileLabel = new JLabel("选择歌曲文件:");
		fileLabel.setFont(new Font("等线", Font.PLAIN, 20));
		northPanel.add(fileLabel);

		JButton uploader = new JButton("打开");
		uploader.setFont(new Font("等线", Font.PLAIN, 20));
		JButton back = new JButton("返回");
		back.setFont(new Font("等线", Font.PLAIN, 20));
		JButton sure = new JButton("添加");
		sure.setFont(new Font("等线", Font.PLAIN, 20));

		northPanel.add(uploader);

		JLabel musicLabel = new JLabel("歌曲名:");
		musicLabel.setFont(new Font("等线", Font.PLAIN, 20));
		northPanel.add(musicLabel);

		musicname = new JLabel();
		musicname.setFont(new Font("等线", Font.PLAIN, 20));
		northPanel.add(musicname);

		JLabel pathLabel = new JLabel("文件路径:");
		pathLabel.setFont(new Font("等线", Font.PLAIN, 20));
		northPanel.add(pathLabel);

		musicPath = new JLabel();
		musicPath.setFont(new Font("等线", Font.PLAIN, 20));
		northPanel.add(musicPath);

		northPanel.add(sure);
		northPanel.add(back);

		add(northPanel, BorderLayout.CENTER);
		uploader.addActionListener(event -> {
			chooser.setCurrentDirectory(new File("."));
			int result = chooser.showOpenDialog(MusicDialogue.this);
			if (result == JFileChooser.APPROVE_OPTION)
			{
				String path = chooser.getSelectedFile().getPath();
				System.out.println(path);
				musicpath = path;
				musicname.setText(getMusicName());
				musicPath.setText(musicpath);
				pack();
			}
		});
		back.addActionListener(event -> setVisible(false));
		sure.addActionListener(event ->  {
			int sheetid = DBUtility.getSheetIdBySheetName(sheetname);
			DBUtility.addMusic(new Music(sheetid, getMusicName(), MD5Creator.encodeByMD5(musicpath), musicpath));
			LocalMusicBlock.updateTable(LocalMusicSheetBlock.id);
			MusicSheetDisplayBlock.updateDisplayPanel();
			setVisible(false);
		});
		chooser = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter(
				"Music files", "mp3");
		chooser.setFileFilter(filter);
		this.setResizable(false);
		pack();
	}

	public static String getCreateDate() {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date());
	}

	public static String getMusicName() {
		String fileName = musicpath;
		String temp[] = fileName.split("\\\\");
		String musicname = temp[temp.length-1];
		System.out.println(musicname);
		return musicname;
	}

	public static void updateCombox()
	{
		if(strArray!=null) {
			ArrayList<String> listname = new ArrayList<>();
			for(int i = 0; i < DBUtility.getNameFromMusicSheet().length; i++) {
				listname.add(DBUtility.getNameFromMusicSheet()[i]);
			}
			strArray = (String[]) listname.toArray(new String[0]);
			musicsheetCombo.setModel(new DefaultComboBoxModel<String>(strArray));
		}
	}
}
