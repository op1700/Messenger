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

		// mainFrame.friendList.size();/// 5가 나오면 총 친구 5명
		makeFriendList();//친구노드를 만들어서 tree에 붙인다

		expandAllNode(mainFrame.memberListMain.tree);//프로그램 시작하자마자 친구목록 전체다 열어놓게하는 메서드 버그있음...강사님께 질문
		
		
	}// 생성자

	// 친구목록 화면에 뿌려주게하는 메서드
	public void makeFriendList() {
		ArrayList<String> TypeList = new ArrayList<String>();// 타입 이름 저장
		parentType = new ArrayList<DefaultMutableTreeNode>();// 부모를 저장
		boolean firstMake = true;

		for (int i = 0; i < mainFrame.friendList.size(); i++) {
			String[] obj = (String[]) mainFrame.friendList.get(i);
//			System.out.println(obj[0]); //코드
//			System.out.println(obj[1]); // 이름
//			System.out.println(obj[2]);  //닉
//			System.out.println(obj[3]); //타입  얘는 따로 배열 저장해서 돌때마다 비교해야함
//			System.out.println(obj[4]); //이미지
			String ImgPath = "dog/" + obj[4];
			if (firstMake) { /// 처음 한번은 무조건 생성
				DefaultMutableTreeNode parent = mainFrame.memberListMain.createParent(obj[3]);
				mainFrame.memberListMain.node_root.add(parent);
				DefaultMutableTreeNode child = mainFrame.memberListMain.createChild(obj[2], ImgPath, obj[0]);
				parent.add(child);
				TypeList.add(obj[3]); // 타입이름 String 형으로 하나를 리스트에 담는다
				firstMake = !firstMake;
				parentType.add(parent);// 부모노드를 저장
			} else {
				if (TypeList.contains(obj[3])) { // 타입 중복일때
					System.out.println(TypeList.size());
					for (int a = 0; a < TypeList.size(); a++) {
						if (parentType.get(a).toString().equals(obj[3])) {
							DefaultMutableTreeNode child = mainFrame.memberListMain.createChild(obj[2], ImgPath,
									obj[0]);
							parentType.get(a).add(child);
							break;
						}
					}
				} else { // 타입 중복이 없을때
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
	public void expandAllNode(JTree tree) { // tree에 붙어잇는 모든 노드를 펼쳐놓기 위한 메서드
		for (int i = 0; i < tree.getRowCount(); i++) {
		    tree.expandRow(i);
		}
	}
	
	public void removeAllNode() {//모든 노드를 tree에서 삭제하기 위한 메서드
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
