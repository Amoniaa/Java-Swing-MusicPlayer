package mainGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class AboutDialog extends JDialog
{
	private static final long serialVersionUID = 1L;
	private JLabel title;
	private JLabel edition;
	private JLabel programmer;
	private JLabel date;
	public AboutDialog(JFrame owner)
	{
		super(owner, "关于我", true);
		
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		int screenWidth = screenSize.width/2; // 获取屏幕的宽
		int screenHeight = screenSize.height/2; // 获取屏幕的高
		int height = 480;
		int width = 460;
		this.setLocation(screenWidth-width/2,screenHeight-height/2);

		title = new JLabel("音乐播放器");
		title.setFont(new Font("等线", Font.PLAIN, 36));
		title.setHorizontalAlignment(SwingConstants.CENTER);

		edition = new JLabel("版本号: 1.0.1");
		edition.setFont(new Font("等线", Font.PLAIN, 26));
		edition.setHorizontalAlignment(SwingConstants.CENTER);

		programmer = new JLabel("By: Wang Jiayi");
		programmer.setFont(new Font("等线", Font.PLAIN, 26));
		programmer.setHorizontalAlignment(SwingConstants.CENTER);
		
		date = new JLabel("2019/12/1");
		date.setFont(new Font("等线", Font.PLAIN, 26));
		date.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(400, 300));
		panel.setLayout(new GridLayout(4, 1));
		panel.add(title);
		panel.add(date);
		panel.add(edition);
		panel.add(programmer);
		add(panel, BorderLayout.CENTER);
		
		pack();
	}
}
