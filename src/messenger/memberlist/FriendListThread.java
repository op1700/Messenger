package messenger.memberlist;

import org.json.simple.JSONObject;

import messenger.chatlist.ChatListMain;
import messenger.client.ClientThread;

public class FriendListThread extends Thread{

	ChatListMain chatListMain;
	ClientThread ct;
	String MyCode;
	public FriendListThread(ChatListMain chatListMain, ClientThread ct, String MyCode) {
		this.chatListMain=chatListMain;
		this.ct=ct;
		this.MyCode=MyCode;
	}
	
	public void spread() {
		chatListMain.removeAll(); //ȭ�鿡 ��� ��� �����... �̹������ ���� ����...
		JSONObject obj = new JSONObject();
		obj.put("Type", "chatlist");
		obj.put("Code", MyCode); // ���ڵ�

		ct.send(obj.toString());
		obj.clear();
	}
	

	public void run() {
		while(true) {
			spread();
			try {
				this.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
