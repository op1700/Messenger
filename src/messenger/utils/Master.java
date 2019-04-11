package messenger.utils;

import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import messenger.chatlist.ChatListMain;
import messenger.client.ClientThread;
import messenger.mainframe.MainFrame;
import messenger.memberlist.MemberListMain;
import messenger.option.AddChat;
import messenger.option.AddFriend;

public class Master {
	Socket socket;
	public ClientThread ct;
	public MainFrame mainFrame;
	public ParserResult parse;
	MemberListMain mamberListMain;
	ChatListMain chatListMain;
	ArrayList chatList;
	AddChat addChat;
	
	AddFriend addFriend;
	
	ArrayList<DefaultMutableTreeNode> parentType;
	public Master(ClientThread ct, MainFrame mainFrame, ParserResult parse) {
		this.ct = ct;
		this.mainFrame = mainFrame;
		this.parse = parse;

		// mainFrame.friendList.size();/// 5�� ������ �� ģ�� 5��
		makeFriendList();//ģ����带 ���� tree�� ���δ�

		expandAllNode(mainFrame.memberListMain.tree);//���α׷� �������ڸ��� ģ����� ��ü�� ��������ϴ� �޼��� ��������...����Բ� ����
		
		
	}// ������

	// ģ����� ȭ�鿡 �ѷ��ְ��ϴ� �޼���
	public void makeFriendList() {
		ArrayList<String> TypeList = new ArrayList<String>();// Ÿ�� �̸� ����
		parentType = new ArrayList<DefaultMutableTreeNode>();// �θ� ����
		boolean firstMake = true;

		for (int i = 0; i < mainFrame.friendList.size(); i++) {
			String[] obj = (String[]) mainFrame.friendList.get(i);
//			System.out.println(obj[0]); //�ڵ�
//			System.out.println(obj[1]); // �̸�
//			System.out.println(obj[2]);  //��
//			System.out.println(obj[3]); //Ÿ��  ��� ���� �迭 �����ؼ� �������� ���ؾ���
//			System.out.println(obj[4]); //�̹���
			String ImgPath = "dog/" + obj[4];
			if (firstMake) { /// ó�� �ѹ��� ������ ����
				DefaultMutableTreeNode parent = mainFrame.memberListMain.createParent(obj[3]);
				mainFrame.memberListMain.node_root.add(parent);
				DefaultMutableTreeNode child = mainFrame.memberListMain.createChild(obj[2], ImgPath, obj[0]);
				parent.add(child);
				TypeList.add(obj[3]); // Ÿ���̸� String ������ �ϳ��� ����Ʈ�� ��´�
				firstMake = !firstMake;
				parentType.add(parent);// �θ��带 ����
			} else {
				if (TypeList.contains(obj[3])) { // Ÿ�� �ߺ��϶�
					System.out.println(TypeList.size());
					for (int a = 0; a < TypeList.size(); a++) {
						if (parentType.get(a).toString().equals(obj[3])) {
							DefaultMutableTreeNode child = mainFrame.memberListMain.createChild(obj[2], ImgPath,
									obj[0]);
							parentType.get(a).add(child);
							break;
						}
					}
				} else { // Ÿ�� �ߺ��� ������
					DefaultMutableTreeNode parent = mainFrame.memberListMain.createParent(obj[3]);
					mainFrame.memberListMain.node_root.add(parent);
					DefaultMutableTreeNode child = mainFrame.memberListMain.createChild(obj[2], ImgPath, obj[0]);
					parent.add(child);
					TypeList.add(obj[3]);
					parentType.add(parent);
				}

			}

		}
	}
	public void expandAllNode(JTree tree) { // tree�� �پ��մ� ��� ��带 ���ĳ��� ���� �޼���
		for (int i = 0; i < tree.getRowCount(); i++) {
		    tree.expandRow(i);
		}
	}
	
	public void removeAllNode() {//��� ��带 tree���� �����ϱ� ���� �޼���
		for(int i=0;i<parentType.size();i++) {
			DefaultMutableTreeNode parent=parentType.get(i);
			while(parent.isLeaf()) {
				parent.removeAllChildren();
			}
			parent.removeFromParent();			
		}
		parentType.clear();	
	}
	
	

}
