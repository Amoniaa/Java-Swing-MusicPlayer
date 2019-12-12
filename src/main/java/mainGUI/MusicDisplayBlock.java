package mainGUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import musicDBUtil.DBUtility;
import musicModel.Music;

public class MusicDisplayBlock extends JPanel {

	private static final long serialVersionUID = 1L;
	private static JLabel mainTitleLabel;
	private static JLabel musicTitleLabel;
	private static JLabel musicNameLabel;
	private static JLabel sheetNameLabel;
	private static JLabel sheetTitleLabel;
	private static JLabel musicPathLabel;
	private static JLabel pathTitleLabel;
	private static JPanel musicInfoPanel;

	public MusicDisplayBlock() {

		this.setBorder(BorderFactory.createRaisedBevelBorder());
		
		musicInfoPanel = new JPanel();
		musicInfoPanel.setPreferredSize(new Dimension(250, 400));
		musicInfoPanel.setLayout(new BoxLayout(musicInfoPanel, BoxLayout.Y_AXIS));
		musicInfoPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		
		mainTitleLabel = new JLabel("     详细信息",JLabel.CENTER);
		mainTitleLabel.setPreferredSize(new Dimension(250, 50));
		mainTitleLabel.setFont(new Font("等线", Font.BOLD, 30));
		
		musicNameLabel = new JLabel("歌曲名");
		musicNameLabel.setFont(new Font("等线", Font.BOLD, 25));
		
		musicTitleLabel = new JLabel();
		musicTitleLabel.setSize(200, 0);
		try {
			JlabelSetText(musicTitleLabel, "");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		musicTitleLabel.setFont(new Font("等线", Font.PLAIN, 20));
		
		sheetNameLabel = new JLabel("所在歌单");
		sheetNameLabel.setFont(new Font("等线", Font.BOLD, 25));
		
		sheetTitleLabel = new JLabel();
		sheetTitleLabel.setSize(200, 0);
		try {
			JlabelSetText(sheetTitleLabel, "");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		sheetTitleLabel.setFont(new Font("等线", Font.PLAIN, 20));
		
		pathTitleLabel = new JLabel("文件路径");
		pathTitleLabel.setFont(new Font("等线", Font.BOLD, 25));
		
		musicPathLabel = new JLabel();
		musicPathLabel.setSize(200, 0);
		try {
			JlabelSetText(musicPathLabel, "");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		musicPathLabel.setFont(new Font("等线", Font.PLAIN, 20));
		
		musicInfoPanel.add(Box.createVerticalStrut(10));
		musicInfoPanel.add(mainTitleLabel);
		musicInfoPanel.add(Box.createVerticalStrut(20));
		musicInfoPanel.add(musicTitleLabel);
		musicInfoPanel.add(Box.createVerticalStrut(10));
		musicInfoPanel.add(musicNameLabel);
		musicInfoPanel.add(Box.createVerticalStrut(10));
		musicInfoPanel.add(musicTitleLabel);
		musicInfoPanel.add(Box.createVerticalStrut(10));
		musicInfoPanel.add(sheetNameLabel);
		musicInfoPanel.add(Box.createVerticalStrut(10));
		musicInfoPanel.add(sheetTitleLabel);
		musicInfoPanel.add(Box.createVerticalStrut(10));
		musicInfoPanel.add(pathTitleLabel);
		musicInfoPanel.add(Box.createVerticalStrut(10));
		musicInfoPanel.add(musicPathLabel);
		this.add(musicInfoPanel);
	}

	public static void updateLocalDisplayPanel(Music music) {

		try {
			JlabelSetText(musicTitleLabel, music.getName());
			JlabelSetText(sheetTitleLabel, DBUtility.getSheetNameBySheetId(music.getSheetid()));
			JlabelSetText(musicPathLabel, music.getPath());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void updateSharedDisplayPanel(String musicname, String sheetname, String path) {

		try {
			JlabelSetText(musicTitleLabel, musicname);
			JlabelSetText(sheetTitleLabel, sheetname);
			JlabelSetText(musicPathLabel, path);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void JlabelSetText(JLabel jLabel, String longString) 
			throws InterruptedException {
		StringBuilder builder = new StringBuilder("<html>");
		char[] chars = longString.toCharArray();
		FontMetrics fontMetrics = jLabel.getFontMetrics(jLabel.getFont());
		int start = 0;
		int len = 0;
		while (start + len < longString.length()) {
			while (true) {
				len++;
				if (start + len > longString.length())break;
				if (fontMetrics.charsWidth(chars, start, len) 
						> jLabel.getWidth()) {
					break;
				}
			}
			builder.append(chars, start, len-1).append("<br/>");
			start = start + len - 1;
			len = 0;
		}
		builder.append(chars, start, longString.length()-start);
		builder.append("</html>");
		jLabel.setText(builder.toString());
	}
}
