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
		chatListMain.removeAll(); //화면에 모든 목록 지우기... 이방법말고 따로 없나...
		JSONObject obj = new JSONObject();
		obj.put("Type", "chatlist");
		obj.put("Code", MyCode); // 내코드

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
