package messenger.memberlist;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import messenger.mainframe.MainFrame;

public class ListCellRenderer extends DefaultTreeCellRenderer {
	MainFrame mainFrame;
	String font;
	Color color;
	public ListCellRenderer(MainFrame mainFrame) {
		this.mainFrame=mainFrame;
		color=mainFrame.getColor();
		font=mainFrame.getFontString();
		
		

	}

	// �������̵�
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {
		DefaultTreeCellRenderer result=(DefaultTreeCellRenderer)super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
		 row, hasFocus);
		Object obj = ((DefaultMutableTreeNode) value).getUserObject(); // �̸��� �˼��ִ� ��ɾ�
		Object o = ((DefaultMutableTreeNode) value).getClass(); // � Ŭ�������� Ȯ��
		int aa = ((DefaultMutableTreeNode) value).getLevel(); // ������ �ֻ����� 0 �� �Ʒ��� �������� 1 �� ����
		boolean bb = ((DefaultMutableTreeNode) value).isLeaf();
		result.setOpaque(true);
		
		if(sel) {
			result.setBackground(Color.red);
		}else {
			result.setBackground(color);
		}
		if (value instanceof MemberListAdd) {
			MemberListAdd listAdd = (MemberListAdd) value;
			Image image = listAdd.getImg();
			//System.out.println(image);
			if (image != null) {
				result.setIcon(new ImageIcon(image));
				result.setText(listAdd.getName());
				result.setFont(new Font(font, Font.BOLD, 16));
				
			}
			//����� �����̹��� �־�� ���� ģ�� ���
			
			
		} else {
			result.setIcon(null);
			result.setText("" + value);
			result.setFont(new Font(font, Font.BOLD, 16));
		}
//		try {
//			Thread.sleep(200);
//		} catch (InterruptedException e) {
//
//			e.printStackTrace();
//		}

		return result;
	}

}
