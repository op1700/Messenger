package messenger.memberlist;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.json.simple.JSONObject;

import messenger.mainframe.MainFrame;

public class MemberListMain extends JPanel {
	TreeModel treeModel;
	public DefaultMutableTreeNode node_root; // �̰� ģ���� �����ɶ����� ���ܾ� �ϹǷ� ArrayList��ߵɵ�
	DefaultMutableTreeNode node_family, node_friend, node_colleague;
	ListCellRenderer renderer;// node�� ������ �̹��� ������ ���� �ʿ��� Ŭ����
	public JTree tree;
	// ���� ����Ʈ ����� ������ �迭
	ArrayList<DefaultMutableTreeNode> parentList = new ArrayList<DefaultMutableTreeNode>(); // �������� ������ ����Ʈ
	public ArrayList<DefaultMutableTreeNode> childList = new ArrayList<DefaultMutableTreeNode>();// ģ������ ������ ����Ʈ
	// Icon icon, icon2, icon3; ���򰡴� 
	MemberListAdd memberListAdd;
	MainFrame mainFrame;
	public JScrollPane scroll;
	MemberListOption memberListOption;

	public MemberListMain(MainFrame mainFrame) {
		this.mainFrame = mainFrame;

		node_root = new DefaultMutableTreeNode("���� ���");

		tree = new JTree(node_root);// ����Ʈ������(��ȣ)
		renderer = new ListCellRenderer(mainFrame);

		tree.setFont(new Font("�������", Font.BOLD, 30));
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e1) {
//			e1.printStackTrace();
//		}
		tree.setCellRenderer(renderer);
		
		
		// tree.setEditable(true);//ģ����Ͽ��� ���������ϰ��ϴ� �޼���
		// -------------------------------------------------------------------------------------------

		MouseListener mouse = new MouseAdapter() { // �� ��Ŭ�� ���� ��������� ������
			public void mousePressed(MouseEvent e) {
				int selRow = tree.getRowForLocation(e.getX(), e.getY());
				TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());

				if (memberListOption != null) {
					memberListOption.dispose();
				}
				if (selRow != -1) {
					if (SwingUtilities.isLeftMouseButton(e)) { // ����Ŭ���� ä�ù� ����
						if (e.getClickCount() > 1) {
							joinChatRoom();
							
						}
					} else if (SwingUtilities.isRightMouseButton(e)) { // ģ�� ������ �� Ŭ���� �޴�â �߰�
						//�̶� ���̸� ����ó���ؾ���..
						if((tree.getLastSelectedPathComponent())==null) {
		                     JOptionPane.showMessageDialog(MemberListMain.this, "�߸��� �����Դϴ�");
		                     return;
		                  }
						String nick = tree.getLastSelectedPathComponent().toString();
						String[] info = new String[8];
						for (int i = 0; i < mainFrame.friendList.size(); i++) { //������ ģ�� ���� ã��
							String[] friendInfo = new String[8];
							friendInfo = (String[]) mainFrame.friendList.get(i);
							if (friendInfo[2].equals(nick)) {
								info = friendInfo;
								break;
							}
						}
						memberListOption = new MemberListOption(mainFrame, info); //ģ�� ���� �Ѱ���
						int x = (int) e.getLocationOnScreen().getX(); // ���� x�� ������
						int y = (int) e.getLocationOnScreen().getY();// ���� y�� ������
						memberListOption.setBounds(x + 5, y + 5, 130, 125);// �ɼ�ȭ�� ��ġ,ũ�� �����ֱ�
					}

				}
			}
		};


		tree.addMouseListener(mouse);

		tree.setPreferredSize(new Dimension(500, 650));

		setLayout(new BorderLayout());
		scroll = new JScrollPane(tree, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setPreferredSize(new Dimension(500, 612));
		this.add(scroll, BorderLayout.NORTH);

		tree.setBackground(mainFrame.getColor());
		setPreferredSize(new Dimension(500, 650));
	}

	public DefaultMutableTreeNode createParent(String typeName) { // ���� ���� �����
		DefaultMutableTreeNode parent = new DefaultMutableTreeNode(typeName);
		parentList.add(parent);
		return parent;
	}

	public DefaultMutableTreeNode createChild(String name, String imgPath, String MyCode) { // �ڽĸ���� // �ϸ�ɵ�
		MemberListAdd child = new MemberListAdd(name, imgPath, MyCode);
		childList.add(child);

		return child;
	}

	public void joinChatRoom() {
		if (parentList.contains(tree.getSelectionPath().getLastPathComponent())) { // type�̹Ƿ� �ƹ��͵� �������� ����
			System.out.println("�θ� �̹Ƿ� �ƹ��͵� ���� ����");
			System.out.println(tree.getSelectionPath().getLastPathComponent());

		} else if (tree.getSelectionPath().getLastPathComponent().toString().equals("���� ���")) {// tree�̹Ƿ� �ƹ��͵� �������� ����
			System.out.println("�ֻ����Ƿ� �ƹ��͵� ���� ����");
		} else {
			// �̶� �����ؾ���..
			MemberListAdd node = (MemberListAdd) tree.getSelectionPath().getLastPathComponent();
			String a = node.toString();
			//System.out.println("���� ������ : " + a);
			// ���⼭ 1:1 ä���� �����;���...
			// �������� 1:1ä�ù��� ������ ä�÷α׸� ����;��ϰ�
			// ä�ù��� ������ �ȵ������� �۾����� ���������� insert into 'ä�ù�' ��������
			// ���ڵ�� ���� �ڵ� ����
			JSONObject obj = new JSONObject();
			obj.put("Type", "Start");
			obj.put("MyCode", mainFrame.myProfile[0]);
			obj.put("FriendCode", node.getCode());

			mainFrame.ct.send(obj.toString());
		}
	}

}
