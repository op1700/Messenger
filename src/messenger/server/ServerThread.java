package messenger.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import messenger.db.ConnectionManager;

public class ServerThread extends Thread {
	ServerParser sp;
	Socket client;
	BufferedReader buffr;
	BufferedWriter buffw;
	MultiServer multiServer;
	String order;
	String result;
	Object obj;
//	ConnectionManager conManger;
//	PreparedStatement pstmt;
	ResultSet rs;
	Connection con;
	ConnectionManager connectionManger;
	JSONArray array = new JSONArray();
	String ClientId;
	boolean flag = true;

	public ServerThread(MultiServer multiServer, Socket client, ConnectionManager connectionManger) {
		this.multiServer = multiServer;
		this.client = client;
		this.connectionManger = connectionManger;
		this.sp = new ServerParser(multiServer, this);
		try {
			buffr = new BufferedReader(new InputStreamReader(client.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ���
	public void listen() {
		try {
			order = buffr.readLine();
			// ������ ������ ��� �ƹ�Ÿ�� send�� ȣ������!!
//			for (int i = 0; i < multiServer.list.size(); i++) {
//				ServerThread st = multiServer.list.get(i);
//				st.send(order);
//				// st.send(type);
//			}
			multiServer.area.append(order + "\n");
			multiServer.bar.setValue(multiServer.bar.getMaximum());
			if (order != null) {
				sp.Parser(order);
			}
		} catch (IOException e) {
			flag = false;
			System.out.println("Ŭ���̾�Ʈ�� �������ϴ�\n ");
			// ���Ϳ��� ����
			multiServer.list.remove(this);
			multiServer.area.append("���� : " + multiServer.list.size() + "�� �Դϴ�\n");

		}
	}

	// ���ϱ�
	public void send(String result) {
		try {
			buffw.write(result + "\n");
			buffw.flush();
			//System.out.println("result : "+result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (flag) {
			listen();
		}
	}


}