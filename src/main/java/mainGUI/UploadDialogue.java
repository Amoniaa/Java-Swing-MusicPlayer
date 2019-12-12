package mainGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import musicDBUtil.DBUtility;
import musicModel.MusicSheet;
import musicOnline.MusicSheetAndFilesUploader;


public class UploadDialogue extends JDialog
{

	private static final long serialVersionUID = 1L;
	private static String sheetname;
	private static String url = "http://service.uspacex.com/music.server/upload";
	private JLabel URL = new JLabel();

	private static JComboBox<String> musicsheetCombo;

	public UploadDialogue(UploadDialogue owner)
	{
		super(owner, "上传歌单", true);
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
		musicsheetCombo = new JComboBox<>();
		for(int i = 0; i < DBUtility.getNameFromMusicSheet().length; i++) {
			musicsheetCombo.addItem(DBUtility.getNameFromMusicSheet()[i]);
		}
		musicsheetCombo.addActionListener(event ->
		sheetname = musicsheetCombo.getItemAt(musicsheetCombo.getSelectedIndex()));
		JPanel northPanel = new JPanel();

		northPanel.setPreferredSize(new Dimension(400, 150));
		BoxLayout northPanelLayout = new BoxLayout(northPanel, BoxLayout.Y_AXIS);
		northPanel.setLayout(northPanelLayout);
		container.add("North", northPanel);
		northPanel.setLayout(new GridLayout(6, 1));
		
		JLabel chooselabel = new JLabel("请选择歌单:");
		chooselabel.setFont(new Font("等线", Font.PLAIN, 20));
		northPanel.add(chooselabel);
		
		musicsheetCombo.setFont(new Font("等线", Font.PLAIN, 20));
		northPanel.add(musicsheetCombo);
		
		JButton back = new JButton("返回");
		back.setFont(new Font("等线", Font.PLAIN, 20));
		JButton sure = new JButton("上传");
		sure.setFont(new Font("等线", Font.PLAIN, 20));
		
		JLabel urlLabel = new JLabel("URL:");
		urlLabel.setFont(new Font("等线", Font.PLAIN, 20));
		northPanel.add(urlLabel);
		
		URL.setFont(new Font("等线", Font.PLAIN, 20));
		URL.setText(url);
		
		northPanel.add(URL);
		northPanel.add(sure);
		northPanel.add(back);

		add(northPanel, BorderLayout.CENTER);
		back.addActionListener(event -> setVisible(false));
		sure.addActionListener(event ->  {
			try {
				MusicSheet nusicsheet = DBUtility.getResultFromSheetByName(sheetname);
				ArrayList<String> filePaths = DBUtility.getPathsFromMusicBySheetId(DBUtility.getSheetIdBySheetName(sheetname));
				List<String> Paths = new ArrayList<String>();
				for(int i = 0; i < filePaths.size(); i++) {
					Paths.add(filePaths.get(i));
				}
				MusicSheetAndFilesUploader.createMusicSheetAndUploadFiles(url, nusicsheet, Paths);
			} catch (Exception e) {
				e.printStackTrace();
			}
			setVisible(false);
		});
		this.setResizable(false);
		pack();
	}
}
