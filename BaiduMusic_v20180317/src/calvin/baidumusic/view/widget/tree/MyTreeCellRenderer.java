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
		//调用super的方法，会出现选中节点的背景色
//		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,row, hasFocus); //调用父类的该方法 
		IconTreeNode node = (IconTreeNode)value;
		//获取节点文本
		String txt=node.getTxt(); //从节点读取文本
		this.setText(txt);//设置文本
		
		Icon icon = node.getIcon();// 从节点读取图片
		this.setIcon(icon);// 设置图片
		// 更改选中文本颜色
		if (sel) {
			setForeground(new Color(173, 221, 255));
		} else {
			setForeground(Color.BLACK);
		}
		return this; 
	}
}
