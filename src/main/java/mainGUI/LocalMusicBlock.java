package mainGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import musicModel.Music;
import musicOnline.MusicTaker;
import MusicPlayer.CountingThread;
import MusicPlayer.MP3Player;
import musicDBUtil.DBUtility;

public class LocalMusicBlock extends JPanel {

	public static final long serialVersionUID = 1L;
	public static int row = 0;
	public static int localrow = 0;
	public static int col = 0;
	public static int tablerows = 0;
	public static int localtablerows = 0;
	public static int selectedMusicid;
	public static String selectedMusicMd5;
	public static Music selectedMusic;
	public static JTable musicTable;
	public static String selectedMusicpath = "./sound/我们不一样.mp3";
	public static DefaultTableModel tableModel;
	private static DownloadDialogue downloadDialogue;

	@SuppressWarnings("serial")
	public LocalMusicBlock() {

		this.setPreferredSize(new Dimension(550, 300));
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(layout);
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		@SuppressWarnings("rawtypes")
		Vector rowData = DBUtility.getRowsBySheetId(1);
		tablerows = 0;//rowData.size();
		localtablerows = 0;//rowData.size();
		Vector<String> columnNames = new Vector<>();
		columnNames.add("ID");
		columnNames.add("歌单ID");
		columnNames.add("名称");
		columnNames.add("编码");
		columnNames.add("路径");
		columnNames.add("播放");
		columnNames.add("更多操作");

		tableModel = new DefaultTableModel(rowData,columnNames);	
		musicTable = new JTable(tableModel){
			public boolean isCellEditable(int rowIndex, int ColIndex){
				return false;
			}
		};
		musicTable.getTableHeader().setBackground(Color.BLUE);

		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setBackground(new Color(39, 64, 128));
		cellRenderer.setForeground(Color.WHITE);

		for(int i=0;i<7;i++){
			//i是表头的列
			TableColumn column = musicTable.getTableHeader().getColumnModel().getColumn(i);
			column.setHeaderRenderer(cellRenderer);
			//表头文字居中
			cellRenderer.setHorizontalAlignment(SwingConstants.CENTER); 
		}
		JTableHeader head = musicTable.getTableHeader(); // 创建表格标题对象
		head.setPreferredSize(new Dimension(head.getWidth(), 35));// 设置表头大小

		DefaultTableCellRenderer hr = new DefaultTableCellRenderer();
		hr.setHorizontalAlignment(JLabel.CENTER);
		//musicTable.getTableHeader().setDefaultRenderer(hr);
		musicTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane musicTablePanel = new JScrollPane(musicTable);
		for(int i = 0; i < musicTable.getRowCount(); i++) {
			musicTable.getModel().setValueAt("播放", i,5);
			musicTable.getModel().setValueAt("删除", i,6);
			columnNames.add("ID");
			columnNames.add("歌单ID");
			DefaultTableCellRenderer cellRanderer0 = new DefaultTableCellRenderer();
			cellRanderer0.setHorizontalAlignment(JTextField.CENTER);
			musicTable.getColumn("ID").setCellRenderer(cellRanderer0);
			musicTable.getColumn("歌单ID").setCellRenderer(cellRanderer0);
			//初始化table的渲染器
			DefaultTableCellRenderer cellRanderer = new DefaultTableCellRenderer();
			//设置前景色也就是字体颜色
			cellRanderer.setHorizontalAlignment(JTextField.CENTER);   
			cellRanderer.setForeground(Color.RED);
			cellRanderer.setBackground(new Color(222,222,222));
			//将上面的渲染器对象放到tableColumn中
			musicTable.getColumn("更多操作").setCellRenderer(cellRanderer);
			DefaultTableCellRenderer cellRanderer1 = new DefaultTableCellRenderer();
			cellRanderer1.setHorizontalAlignment(JTextField.CENTER);   
			cellRanderer1.setForeground(Color.BLUE);
			cellRanderer1.setBackground(new Color(222,222,222));
			musicTable.getColumn("播放").setCellRenderer(cellRanderer1);
		}
		musicTable.setRowHeight(25);
		musicTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		musicTable.getColumnModel().getColumn(1).setPreferredWidth(30);
		musicTable.getColumnModel().getColumn(5).setPreferredWidth(30);
		musicTable.getColumnModel().getColumn(6).setPreferredWidth(30);
		musicTable.setFont(new Font(null, Font.PLAIN, 20));
		musicTable.setRowSelectionAllowed(true);//设置是否可以选择此模型中的行
		//musicTable.setColumnSelectionAllowed(true);//设置是否可以选择此模型中的列
		musicTable.addMouseListener(new MouseAdapter()
		{

			public void mouseClicked(MouseEvent event)
			{
				if(MusicPlayerGUI.localSelected) {
					row = musicTable.rowAtPoint(event.getPoint());
					localrow = musicTable.rowAtPoint(event.getPoint());
					col = musicTable.columnAtPoint(event.getPoint());
					selectedMusicid = Integer.parseInt((String) musicTable.getValueAt(row, 0));
					selectedMusic = new Music(DBUtility.getMusicById(selectedMusicid));
					selectedMusicpath = selectedMusic.getPath();
					MusicDisplayBlock.updateLocalDisplayPanel(LocalMusicBlock.selectedMusic);
					if(col == 5) {
						MP3Player.musicStop();
						MusicPlayerBlock.slider.setValue(0);
						MusicPlayerBlock.currentlabel.setText(MusicPlayerBlock.INITIAL_LABEL_TEXT); 
						CountingThread.spantime = MP3Player.getMusicSpanInt(LocalMusicBlock.selectedMusicpath);
						MusicPlayerBlock.musicspan.setText("/ " + MP3Player.getMusicSpan(LocalMusicBlock.selectedMusicpath));
						MP3Player.MP3player = new MP3Player(LocalMusicBlock.selectedMusicpath);
						MP3Player.musicPlay();
					}
					if(col == 6) {
						DBUtility.deleteMusic(LocalMusicBlock.selectedMusicid);
						LocalMusicBlock.updateTable(LocalMusicSheetBlock.id);
						MusicSheetDisplayBlock.updateDisplayPanel();
					}
				}
				else {
					row = musicTable.rowAtPoint(event.getPoint());
					col = musicTable.columnAtPoint(event.getPoint());
					selectedMusicMd5 = (String) musicTable.getValueAt(row, 3);
					MusicDisplayBlock.updateSharedDisplayPanel(SharedMusicSheetBlock.selectedSheetName, (String) musicTable.getValueAt(row, 2) ,selectedMusicMd5);
					if(col == 6) {
						if (downloadDialogue == null) // first time
							downloadDialogue = new DownloadDialogue(downloadDialogue);
						downloadDialogue.setVisible(true); // pop up dialog
					}
				}
			}
		});
		this.add(musicTablePanel);
	}

	public static void updateTable(int sheetid) {

		for (int i = musicTable.getRowCount(); i > 0; i--) 
			tableModel.removeRow(i - 1);
		LocalMusicBlock.row = 0;
		LocalMusicBlock.localrow = 0;
		Vector<?> rowData = DBUtility.getRowsBySheetId(sheetid);
		tablerows = rowData.size();
		localtablerows = rowData.size();
		for (int i = 0; i < rowData.size(); i++) 
			tableModel.addRow((Vector<?>) rowData.get(i));
		for(int i = 0; i < musicTable.getRowCount(); i++) {
			musicTable.getModel().setValueAt("播放", i,5);
			musicTable.getModel().setValueAt("删除", i,6);
			DefaultTableCellRenderer cellRanderer0 = new DefaultTableCellRenderer();
			cellRanderer0.setHorizontalAlignment(JTextField.CENTER);
			musicTable.getColumn("ID").setCellRenderer(cellRanderer0);
			musicTable.getColumn("歌单ID").setCellRenderer(cellRanderer0);
			//初始化table的渲染器
			DefaultTableCellRenderer cellRanderer = new DefaultTableCellRenderer();
			//设置前景色也就是字体颜色
			cellRanderer.setHorizontalAlignment(JTextField.CENTER);   
			cellRanderer.setForeground(Color.RED);
			cellRanderer.setBackground(new Color(222,222,222));
			//将上面的渲染器对象放到tableColumn中
			musicTable.getColumn("更多操作").setCellRenderer(cellRanderer);
			DefaultTableCellRenderer cellRanderer1 = new DefaultTableCellRenderer();
			cellRanderer1.setHorizontalAlignment(JTextField.CENTER);   
			cellRanderer1.setForeground(Color.BLUE);
			cellRanderer1.setBackground(new Color(222,222,222));
			musicTable.getColumn("播放").setCellRenderer(cellRanderer1);
		}
	}

	public static void updateSharedTable(String sheetname) {

		for (int i = musicTable.getRowCount(); i > 0; i--) 
			tableModel.removeRow(i - 1);
		LocalMusicBlock.row = 0;
		Vector<?> rowData = MusicTaker.getRows(MusicTaker.getMusicsBySheetName(sheetname));
		tablerows = rowData.size();
		for (int i = 0; i < rowData.size(); i++) 
			tableModel.addRow((Vector<?>) rowData.get(i));
		for(int i = 0; i < musicTable.getRowCount(); i++) {
			musicTable.getModel().setValueAt(DownloadDialogue.url, i,4);
			DefaultTableCellRenderer cellRanderer0 = new DefaultTableCellRenderer();
			cellRanderer0.setHorizontalAlignment(JTextField.CENTER);
			musicTable.getColumn("ID").setCellRenderer(cellRanderer0);
			musicTable.getColumn("歌单ID").setCellRenderer(cellRanderer0);
			//初始化table的渲染器
			DefaultTableCellRenderer cellRanderer = new DefaultTableCellRenderer();
			//设置前景色也就是字体颜色
			cellRanderer.setHorizontalAlignment(JTextField.CENTER);   
			cellRanderer.setForeground(Color.BLUE);
			//将上面的渲染器对象放到tableColumn中
			cellRanderer.setBackground(new Color(222,222,222));
			musicTable.getColumn("更多操作").setCellRenderer(cellRanderer);
			DefaultTableCellRenderer cellRanderer1 = new DefaultTableCellRenderer();
			cellRanderer1.setHorizontalAlignment(JTextField.CENTER);   
			cellRanderer1.setForeground(Color.BLUE);
			cellRanderer1.setBackground(new Color(222,222,222));
			musicTable.getColumn("播放").setCellRenderer(cellRanderer1);
		}
	}
}
