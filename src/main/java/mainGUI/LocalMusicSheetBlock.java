package mainGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import musicDBUtil.DBUtility;

public class LocalMusicSheetBlock extends JPanel {

	private static final long serialVersionUID = 1L;
	private static JList<String> mysheetlist = new JList<String>();
	public static int id = 1;
	public LocalMusicSheetBlock() {
		this.setPreferredSize(new Dimension(250, 400));
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(layout);
		this.setBackground(new Color(39, 64, 128));
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		JLabel localMusicSheetLabel = new JLabel("     本地歌单",JLabel.CENTER);
		localMusicSheetLabel.setForeground(Color.WHITE);
		localMusicSheetLabel.setFont(new Font("等线", Font.BOLD, 30));
		localMusicSheetLabel.setPreferredSize(new Dimension(250, 40));
		mysheetlist.setListData(DBUtility.getNameFromMusicSheet());
		mysheetlist.setPreferredSize(new Dimension(250, 400));
		mysheetlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		mysheetlist.setFont(new Font("等线", Font.PLAIN, 20));
		mysheetlist.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()) {				
					MusicPlayerGUI.localSelected = true;
					MusicPlayerGUI.selectedname = mysheetlist.getSelectedValue();
					id = DBUtility.getSheetIdBySheetName(MusicPlayerGUI.selectedname);
					LocalMusicBlock.updateTable(id);
					MusicSheetDisplayBlock.updateDisplayPanel();
				}
			}
		});
		JScrollPane localMusicSheetTablePanel = new JScrollPane(mysheetlist);
		this.add(Box.createVerticalStrut(5));
		this.add(localMusicSheetLabel);
		this.add(Box.createVerticalStrut(5));
		this.add(localMusicSheetTablePanel);
	}

	public static void updateList() {
		
		mysheetlist.setListData(DBUtility.getNameFromMusicSheet());
	}
}
