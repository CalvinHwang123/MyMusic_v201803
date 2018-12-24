package calvin.baidumusic.view.widget.tree;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;

/*
 * 自定义JTree节点
 * 自定义DefaultMutableTreeNode
 */
public class IconTreeNode extends DefaultMutableTreeNode{

	private static final long serialVersionUID = 1L;
	//存放图片
	protected Icon icon; 
	//存放文本
	protected String txt; 
	
	public IconTreeNode() {
		super();
	}

	//只包含文本的节点构造
	public IconTreeNode(String txt){
		super();
		this.txt=txt;
	} 

	//包含文本和图片的节点构造
	public IconTreeNode(Icon icon,String txt){
		super();
		this.icon = icon; 
		this.txt = txt;
	}
	
	public void setIcon(Icon icon){ 
		this.icon = icon; 
	} 

	public Icon getIcon() { 
		return icon; 
	} 

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

}