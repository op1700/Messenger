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
	ServerSocket server; // 접속자 감지용 소켓

	int port = 8888;
	Thread serverThread; // main Thread가 대기상태에 빠지지 않게 하기 위한 쓰레드..
	// 컬렉션 프레임웍 중 순서가 있는 처리를 위한 List계월의 하위 객체중
	// Vector를 써본다 ArrayList와 완전 동일하지만, 단 다중 쓰레드
	// 환경에서 동기화를 지원하므로, 엉키지 않음...
	Vector<ServerThread> list = new Vector<ServerThread>();// 서버측에 생성된 아바타 들을 담을 객체;
	public Connection con;
	ConnectionManager connectionManger;

	public MultiServer() {
		p_north = new JPanel();
		t_port = new JTextField(Integer.toString(port), 8);
		bt = new JButton("서버가동");
		area = new JTextArea();
		scroll = new JScrollPane(area);
		bar = scroll.getVerticalScrollBar();
		connectionManger = new ConnectionManager();
		con = connectionManger.getConnection();

		// 여기서 쓰레드 생성 및 재정의!! 내부익명으로 가자 그니까
		serverThread = new Thread() {
			public void run() {
				runServer();
			}
		};

		p_north.add(t_port);
		p_north.add(bt);
		add(p_north, BorderLayout.NORTH);
		add(scroll);

		// 버튼에 리스너 연결 ㄱㄱㄱㄱ
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serverThread.start(); // 메서드로 추가해서 만들어주자
			}
		});

		area.setFont(new Font("돋움", Font.BOLD, 23)); // font 사이즈 조절하기
		setSize(800, 800);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void runServer() {
		port = Integer.parseInt(t_port.getText());
		try {
			server = new ServerSocket(port); // 서버생성
			area.append("서버가동\n");

			while (true) {
				Socket client = server.accept(); // 서버 가동(접속자를 기다림)
//	            Map<String, Socket> map = new HashMap<String, Socket>();
//	            map.put("cool", client);
//	            Socket socket= map.get("cool");

				String ip = client.getInetAddress().getHostAddress();
				area.append(ip + "님 발견\n");
				// 접속과 동시에 대화를 나눌 아바타 생성!!!
				ServerThread st = new ServerThread(this, client, connectionManger);
				st.start();// run을 호출
				list.add(st);// Object형으로 업캐스팅 되버림 따라서 끄집어 낼 때는 자기자신의 자료형으로 다운캐스팅 해야함..
								// 해결책) 제너릭타임으로 컬렉션 프레임웍 객체를 선언하면 형 변환의 불편함이 해결된다.
				area.append("현재 : " + list.size() + "명 접속중\n");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new MultiServer();
	}
}