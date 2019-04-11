package messenger.chat;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import messenger.mainframe.MainFrame;
import messenger.utils.ParserResult;

public class ChatThread extends Thread {
	MainFrame mainFrame;
	public String chat_code;


	ParserResult parser;
	ArrayList array;
	ChatMain chatmain;
	public boolean flag=true;

	public ChatThread(MainFrame mainFrame, ChatMain chatmain, String chat_code) {
		this.chat_code = chat_code;
		this.mainFrame = mainFrame;
		this.chatmain=chatmain;
		
	}

	public void Basic() {
		JSONObject obj = new JSONObject();
		obj.put("Type", "chatUI");
		obj.put("C.Code", chat_code);
		System.out.println("챗코드 나오냐!!!!!!!!?:   "+chat_code);
		mainFrame.ct.send(obj.toString());

	}
	
	
	public void run() {
		while (flag) {
			Basic();
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag=!flag;
			}
			
		}
	}

}
