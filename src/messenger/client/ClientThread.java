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

	Socket client;// ��ȭ�� ����
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

	// ������ �޽��� ������
	public void send(String order) {
		try {
			buffw.write(order + "\n");
			buffw.flush();// flush() �� ��¿��� �����Ѵ�!!
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void listen() {
		try {
			String result = buffr.readLine(); // �α��� Ȯ�� �޼��� ����
			//System.out.println("������ ���� �޼���" + result);
			// loginMain.setVisible(false);
			// sendResult(result); // result�� �ؼ� �ϱ����� �Լ� ȣ��
			// memberLogin.parseResult(result); //����α信 result ������ mainFrame�� �ѷ���
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
