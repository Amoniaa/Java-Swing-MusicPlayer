package mainGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import myUtilities.FileIconView;
import myUtilities.ImagePreviewer;

import musicDBUtil.DBUtility;
import musicModel.MusicSheet;

public class SheetDialogue extends JDialog{

	private static final long serialVersionUID = 1L;
	private static JTextField owner;
	private static JTextField ownerId;
	private static JTextField name;
	private static JLabel label;
	private static JFileChooser chooser;
	private static String imgpath;
	
	public SheetDialogue(SheetDialogue dialog)
	{
		super(dialog, "添加新歌单", true);
		
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		int screenWidth = screenSize.width/2; // 获取屏幕的宽
		int screenHeight = screenSize.height/2; // 获取屏幕的高
		int height = 480;
		int width = 460;
		this.setLocation(screenWidth-width/2,screenHeight-height/2);
		
		Container container = this.getContentPane();
		container.setLayout(new BorderLayout(0, 0));
		container.setBackground(Color.WHITE);
		name = new JTextField();
		owner = new JTextField();
		ownerId = new JTextField();
		JPanel northPanel = new JPanel();
		JPanel southPanel = new JPanel();
		northPanel.setPreferredSize(new Dimension(480, 160));
		BoxLayout northPanelLayout = new BoxLayout(northPanel, BoxLayout.Y_AXIS);
		northPanel.setLayout(northPanelLayout);
		container.add("North", northPanel);
		northPanel.setLayout(new GridLayout(6, 2));
		
		JLabel namelabel = new JLabel("歌单名称:");
		namelabel.setFont(new Font("等线", Font.PLAIN, 20));
		northPanel.add(namelabel);
		
		name.setFont(new Font("等线", Font.PLAIN, 20));
		northPanel.add(name);
		
		JLabel username = new JLabel("用户名:");
		username.setFont(new Font("等线", Font.PLAIN, 20));
		northPanel.add(username);
		
		owner.setFont(new Font("等线", Font.PLAIN, 20));
		northPanel.add(owner);
		
		JLabel IDLabel = new JLabel("用户ID:");
		IDLabel.setFont(new Font("等线", Font.PLAIN, 20));
		northPanel.add(IDLabel);
		
		ownerId.setFont(new Font("等线", Font.PLAIN, 20));
		northPanel.add(ownerId);
		
		JLabel photo = new JLabel("图片:");
		photo.setFont(new Font("等线", Font.PLAIN, 20));
		northPanel.add(photo);
		
		JButton uploader = new JButton("打开文件");
		uploader.setFont(new Font("等线", Font.PLAIN, 20));
		JButton back = new JButton("返回");
		back.setFont(new Font("等线", Font.PLAIN, 20));
		JButton sure = new JButton("添加");
		sure.setFont(new Font("等线", Font.PLAIN, 20));
		
		northPanel.add(uploader);
		northPanel.add(sure);
		northPanel.add(back);
		
		JLabel preLook = new JLabel("		图片预览:");
		preLook.setFont(new Font("等线", Font.PLAIN, 20));
		northPanel.add(preLook);
		
		container.add(northPanel,"Center");
		uploader.addActionListener(event -> {
			chooser.setCurrentDirectory(new File("."));
			int result = chooser.showOpenDialog(SheetDialogue.this);
			if (result == JFileChooser.APPROVE_OPTION)
			{
				String path = chooser.getSelectedFile().getPath();
				System.out.println(path);
				imgpath = path;
				ImageIcon icon = new ImageIcon(path);
				icon.setImage(icon.getImage().getScaledInstance(480, 360,Image.SCALE_DEFAULT));	
				label.setIcon(icon);
				pack();
			}
		});
		back.addActionListener(event -> setVisible(false));
		sure.addActionListener(event ->  {
			DBUtility.addMusicSheet(new MusicSheet(name.getText(),ownerId.getText(),owner.getText(),imgpath));
			LocalMusicSheetBlock.updateList();
			MusicSheetDisplayBlock.updateDisplayPaneldeleted();
			MusicDialogue.updateCombox();
			setVisible(false);
		});

		container.add("South", southPanel);
		label = new JLabel();
		label.setFont(new Font("等线", Font.PLAIN, 20));
		southPanel.add(label);
		southPanel.setPreferredSize(new Dimension(480, 360));
		chooser = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "gif");
		chooser.setFileFilter(filter);
		chooser.setAccessory(new ImagePreviewer(chooser));
		chooser.setFileView(new FileIconView(filter, new ImageIcon("palette.gif")));
		this.setResizable(false);
		pack();
	}
}
