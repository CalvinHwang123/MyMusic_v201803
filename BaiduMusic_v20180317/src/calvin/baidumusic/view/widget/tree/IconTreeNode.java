package calvin.baidumusic.view.widget.tree;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;

/*
 * �Զ���JTree�ڵ�
 * �Զ���DefaultMutableTreeNode
 */
public class IconTreeNode extends DefaultMutableTreeNode{

	private static final long serialVersionUID = 1L;
	//���ͼƬ
	protected Icon icon; 
	//����ı�
	protected String txt; 
	
	public IconTreeNode() {
		super();
	}

	//ֻ�����ı��Ľڵ㹹��
	public IconTreeNode(String txt){
		super();
		this.txt=txt;
	} 

	//�����ı���ͼƬ�Ľڵ㹹��
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