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

	// 오버라이드
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {
		DefaultTreeCellRenderer result=(DefaultTreeCellRenderer)super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
		 row, hasFocus);
		Object obj = ((DefaultMutableTreeNode) value).getUserObject(); // 이름을 알수있는 명령어
		Object o = ((DefaultMutableTreeNode) value).getClass(); // 어떤 클래스인지 확인
		int aa = ((DefaultMutableTreeNode) value).getLevel(); // 레벨은 최상위는 0 그 아래로 내려가면 1 씩 증가
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
			//여기다 폴더이미지 넣어도됨 가족 친구 등등
			
			
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
