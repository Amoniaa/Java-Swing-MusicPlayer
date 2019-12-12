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
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import musicOnline.MusicSheetTaker;

public class SharedMusicSheetBlock extends JPanel {

	private static final long serialVersionUID = 1L;
	private static JList<String> mysheetlist = new JList<String>();
	public static String selectedSheetName;
	public static int id = 1;
	public SharedMusicSheetBlock() {
		this.setPreferredSize(new Dimension(250, 400));
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(layout);
		this.setBorder(BorderFactory.createRaisedBevelBorder());

		this.setBackground(new Color(39, 64, 128));
		JLabel SharedMusicSheetLabel = new JLabel("     共享歌单",JLabel.CENTER);
		SharedMusicSheetLabel.setForeground(Color.WHITE);
		SharedMusicSheetLabel.setFont(new Font("等线", Font.BOLD, 30));
		SharedMusicSheetLabel.setPreferredSize(new Dimension(250, 40));
		mysheetlist.setListData(MusicSheetTaker.getSharedMusicSheetName(MusicSheetTaker.querynumber, MusicSheetTaker.URL));
		mysheetlist.setPreferredSize(new Dimension(250, 1500));
		mysheetlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		mysheetlist.setFont(new Font("等线", Font.PLAIN, 20));
		mysheetlist.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()) {
					System.out.println("当前选择共享歌单！");
					MusicPlayerGUI.localSelected = false;
					System.out.println(mysheetlist.getSelectedValue());
					selectedSheetName = mysheetlist.getSelectedValue();
					
					LocalMusicBlock.updateSharedTable(selectedSheetName);
					MusicSheetDisplayBlock.updateDisplayPanelShared();	
				}
			}
		});
		
		JScrollPane SharedMusicSheetTablePanel = new JScrollPane(mysheetlist);
		this.add(Box.createVerticalStrut(5));
		this.add(SharedMusicSheetLabel);
		this.add(Box.createVerticalStrut(5));
		this.add(SharedMusicSheetTablePanel);
	}
}
