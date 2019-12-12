package mainGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import musicDBUtil.DBUtility;

public class MusicSheetManagementBlock extends JPanel {

	private static final long serialVersionUID = 1L;
	public static SheetDialogue dialog;
	
	public MusicSheetManagementBlock() {
		this.setPreferredSize(new Dimension(250, 50));
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		this.setBackground(new Color(39, 64, 128));
		JButton createMusicSheetButton = new JButton("新建歌单");
		createMusicSheetButton.setFont(new Font("等线", Font.PLAIN, 18));
		JButton deleteMusicSheetButton = new JButton("删除歌单");
		deleteMusicSheetButton.setFont(new Font("等线", Font.PLAIN, 18));
		this.add(createMusicSheetButton);
		this.add(deleteMusicSheetButton);
		
		createMusicSheetButton.addActionListener(event -> {
	         if (dialog == null)
	            dialog = new SheetDialogue(dialog);
	         dialog.setVisible(true);
	      });
		
		deleteMusicSheetButton.addActionListener(event -> {
			DBUtility.deleteMusicSheet(MusicPlayerGUI.selectedname);
			LocalMusicSheetBlock.updateList();
			LocalMusicBlock.updateTable(LocalMusicSheetBlock.id);
			MusicSheetDisplayBlock.updateDisplayPaneldeleted();
			MusicDialogue.updateCombox();
		});
	}
}
