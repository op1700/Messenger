package messenger.server;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import messenger.db.ConnectionManager;

public class MultiServer extends JFrame {
	JPanel p_north;
	JTextField t_port;
	JButton bt;
	JTextArea area;
	JScrollBar bar;
	JScrollPane scroll;
	ServerSocket server; // ������ ������ ����

	int port = 8888;
	Thread serverThread; // main Thread�� �����¿� ������ �ʰ� �ϱ� ���� ������..
	// �÷��� �����ӿ� �� ������ �ִ� ó���� ���� List����� ���� ��ü��
	// Vector�� �ẻ�� ArrayList�� ���� ����������, �� ���� ������
	// ȯ�濡�� ����ȭ�� �����ϹǷ�, ��Ű�� ����...
	Vector<ServerThread> list = new Vector<ServerThread>();// �������� ������ �ƹ�Ÿ ���� ���� ��ü;
	public Connection con;
	ConnectionManager connectionManger;

	public MultiServer() {
		p_north = new JPanel();
		t_port = new JTextField(Integer.toString(port), 8);
		bt = new JButton("��������");
		area = new JTextArea();
		scroll = new JScrollPane(area);
		bar = scroll.getVerticalScrollBar();
		connectionManger = new ConnectionManager();
		con = connectionManger.getConnection();

		// ���⼭ ������ ���� �� ������!! �����͸����� ���� �״ϱ�
		serverThread = new Thread() {
			public void run() {
				runServer();
			}
		};

		p_north.add(t_port);
		p_north.add(bt);
		add(p_north, BorderLayout.NORTH);
		add(scroll);

		// ��ư�� ������ ���� ��������
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serverThread.start(); // �޼���� �߰��ؼ� ���������
			}
		});

		area.setFont(new Font("����", Font.BOLD, 23)); // font ������ �����ϱ�
		setSize(800, 800);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void runServer() {
		port = Integer.parseInt(t_port.getText());
		try {
			server = new ServerSocket(port); // ��������
			area.append("��������\n");

			while (true) {
				Socket client = server.accept(); // ���� ����(�����ڸ� ��ٸ�)
//	            Map<String, Socket> map = new HashMap<String, Socket>();
//	            map.put("cool", client);
//	            Socket socket= map.get("cool");

				String ip = client.getInetAddress().getHostAddress();
				area.append(ip + "�� �߰�\n");
				// ���Ӱ� ���ÿ� ��ȭ�� ���� �ƹ�Ÿ ����!!!
				ServerThread st = new ServerThread(this, client, connectionManger);
				st.start();// run�� ȣ��
				list.add(st);// Object������ ��ĳ���� �ǹ��� ���� ������ �� ���� �ڱ��ڽ��� �ڷ������� �ٿ�ĳ���� �ؾ���..
								// �ذ�å) ���ʸ�Ÿ������ �÷��� �����ӿ� ��ü�� �����ϸ� �� ��ȯ�� �������� �ذ�ȴ�.
				area.append("���� : " + list.size() + "�� ������\n");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new MultiServer();
	}
}