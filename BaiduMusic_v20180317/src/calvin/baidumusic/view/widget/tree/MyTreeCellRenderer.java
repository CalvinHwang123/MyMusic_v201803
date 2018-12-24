package calvin.baidumusic.view.widget.tree;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class MyTreeCellRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = 1L;
	
	public MyTreeCellRenderer() {
		super();
	}
	
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
			int row, boolean hasFocus) {
		//����super�ķ����������ѡ�нڵ�ı���ɫ
//		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,row, hasFocus); //���ø���ĸ÷��� 
		IconTreeNode node = (IconTreeNode)value;
		//��ȡ�ڵ��ı�
		String txt=node.getTxt(); //�ӽڵ��ȡ�ı�
		this.setText(txt);//�����ı�
		
		Icon icon = node.getIcon();// �ӽڵ��ȡͼƬ
		this.setIcon(icon);// ����ͼƬ
		// ����ѡ���ı���ɫ
		if (sel) {
			setForeground(new Color(173, 221, 255));
		} else {
			setForeground(Color.BLACK);
		}
		return this; 
	}
}
