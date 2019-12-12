package mainGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import musicOnline.FileDownloader;

public class DownloadAllDialogue extends JDialog
{
	private static final long serialVersionUID = 1L;
	public static String url = "http://service.uspacex.com/music.server/downloadMusic";
	private static JTextField targetPath = new JTextField();
	private JLabel URL = new JLabel();

	public DownloadAllDialogue(DownloadAllDialogue owner)
	{
		super(owner, "下载音乐", true);
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
		JPanel northPanel = new JPanel();
		northPanel.setPreferredSize(new Dimension(400, 180));
		BoxLayout northPanelLayout = new BoxLayout(northPanel, BoxLayout.Y_AXIS);
		northPanel.setLayout(northPanelLayout);
		container.add("North", northPanel);
		northPanel.setLayout(new GridLayout(6, 1));

		JButton back = new JButton("返回");
		back.setFont(new Font("等线", Font.PLAIN, 20));
		JButton sure = new JButton("下载");
		sure.setFont(new Font("等线", Font.PLAIN, 20));
		
		URL.setText(url);
		JLabel URLlabel = new JLabel("URL:");
		URLlabel.setFont(new Font("等线", Font.PLAIN, 20));
		northPanel.add(URLlabel);
		northPanel.setFont(new Font("等线", Font.PLAIN, 20));
		URL.setFont(new Font("等线", Font.PLAIN, 20));
		northPanel.add(URL);
		JLabel Target = new JLabel("目标路径:");
		Target.setFont(new Font("等线", Font.PLAIN, 20));
		northPanel.add(Target);
		northPanel.add(targetPath);
		targetPath.setFont(new Font("等线", Font.PLAIN, 20));
		northPanel.add(sure);
		northPanel.add(back);

		add(northPanel, BorderLayout.CENTER);
		back.addActionListener(event -> setVisible(false));
		sure.addActionListener(event ->  {
			try {
				for(int row = 0; row < LocalMusicBlock.tablerows; row++) {
					String MusicMd5 = (String) LocalMusicBlock.musicTable.getValueAt(row, 3);
					FileDownloader.downloadMusicFile(DownloadDialogue.url, MusicMd5, targetPath.getText(),"file" + String.valueOf(row) + ".mp3");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			setVisible(false);
		});
		this.setResizable(false);
		pack();
	}
}
