package calvin.baidumusic.view.widget.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import calvin.baidumusic.bean.Song;

/**
 * �Զ�����
 * ʵ�ָ����б���ʾ
 * @author Calvin
 *
 */
public class MyTable extends JTable {

	private static final long serialVersionUID = 1L;

	public DefaultTableModel model = null;
	
	public MyTable(DefaultTableModel model, int width, int height, JPanel parent) {
		super(model);
		this.model = model;
		
		setDefaultRenderer(Object.class, new MyTableCellRenderer());
		JTableHeader header = getTableHeader();
		// JTableĬ��û�б�ͷ
		header.setBounds(0, 38, width, 30);
		header.setBorder(BorderFactory.createEtchedBorder(null, new Color(218, 218, 218)));
		header.setBackground(Color.WHITE);
		header.setReorderingAllowed(false);// ��ͷ�����϶�
		header.setResizingAllowed(false);// ��񲻿ɸı��С
		parent.add(header);
		setRowHeight(38);
		setIntercellSpacing(new Dimension(0, 0));// ȡ�������С
		setShowGrid(false);
//		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setBounds(0, 68, width, height - 30);
//		setCellSelectionEnabled(false);
		TableColumnModel tcm = (TableColumnModel) getColumnModel();
		TableColumn tm = tcm.getColumn(0);
		tcm.removeColumn(tm);
		TableColumn tm2 = tcm.getColumn(4);
		tcm.removeColumn(tm2);
	}
	
	/**
	 * ���µ�ǰҳ
	 */
	public List<Song> updateCurrentPage(List<Song> newDataList, int currentPage) {
		model.setRowCount(0);
		List<Song> subDataList = null;
		// ��������10������
		if (newDataList.size() < 10) {
			subDataList = newDataList.subList(0, newDataList.size());
		// ���һҳ����10������
		} else if (newDataList.size() / 10 < currentPage) {
			subDataList = newDataList.subList(10 * (currentPage - 1), newDataList.size());
		} else {
			subDataList = newDataList.subList(10 * (currentPage - 1), 10 * currentPage);
		}
		for (Song song: subDataList) {
			model.addRow(new Object[]{song.getsId(), song.getsName(), song.getSinger(), song.getSize(), song.getsDuration(), song.getsPath()});
		}
		return subDataList;
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	private class MyTableCellRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 1L;

		public MyTableCellRenderer() {
			super();
			setHorizontalAlignment(JLabel.CENTER);// ��Ԫ�����
		}
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			
			if (row % 2 == 0) {
				setBackground(Color.WHITE);
			} else {
				setBackground(new Color(239, 239, 239));
			}
			
			if (isSelected) {
				setBackground(new Color(173, 221, 255));
			}
			return this;
		}
		
	}

}
