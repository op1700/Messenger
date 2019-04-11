package messenger.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import messenger.login.LoginMain;
import messenger.login.MemberLogin;
import messenger.regist.MemberRegist;
import messenger.utils.ParserResult;

public class ClientThread extends Thread {

	Socket client;// 대화용 소켓
	BufferedReader buffr;
	BufferedWriter buffw;
	MemberLogin memberLogin;
	public ParserResult parserResult;
	FileInputStream fis;
	FileOutputStream fos;
	LoginMain loginMain;
	public int count = 0;
	public MemberRegist memberRegist;

	public ClientThread(Socket client, MemberLogin memberLogin, LoginMain loginMain) {
		this.memberLogin = memberLogin;
		this.loginMain = loginMain;
		parserResult = new ParserResult(this, loginMain);
		try {
			buffr = new BufferedReader(new InputStreamReader(client.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 서버에 메시지 보내기
	public void send(String order) {
		try {
			buffw.write(order + "\n");
			buffw.flush();// flush() 를 출력에만 적용한다!!
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void listen() {
		try {
			String result = buffr.readLine(); // 로그인 확인 메세지 받음
			//System.out.println("서버가 보낸 메세지" + result);
			// loginMain.setVisible(false);
			// sendResult(result); // result를 해석 하기위해 함수 호출
			// memberLogin.parseResult(result); //멤버로긴에 result 보내서 mainFrame에 뿌려줌
			parserResult.parse(result);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			listen();
		}
	}
}
