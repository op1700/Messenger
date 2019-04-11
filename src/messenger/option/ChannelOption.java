package messenger.option;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import messenger.chatlist.ChatListMain;
import messenger.login.LoginMain;
import messenger.mainframe.MainFrame;
import messenger.memberlist.MemberListMain;

public class ChannelOption extends JFrame {
	JPanel p_newChat, p_addFriend, p_option, p_logout, p_close;
	JLabel lb_newChat, lb_addFriend, lb_option, lb_logout, lb_close;
	LoginMain loginMain;
	AddChat addChat;
	AddFriend addFriend;
	Settings settings;
	Font font; // 미설정시 기본폰트

	public ChannelOption(MainFrame mainFrame, MemberListMain member, ChatListMain chat) {
		p_newChat = new JPanel(); // 새로운 채팅 패널
		p_addFriend = new JPanel(); // 친구 추가 패널
		p_option = new JPanel(); // 설정 패널
		p_logout = new JPanel(); // 로그아웃 패널
		p_close = new JPanel(); // 종료 패널

		lb_newChat = new JLabel("새로운 채팅");
		lb_addFriend = new JLabel("친구 추가");
		lb_option = new JLabel("설정");
		lb_logout = new JLabel("로그아웃");
		lb_close = new JLabel("종료");

		// 폰트 설정
		font = new Font(mainFrame.getFontString(), Font.BOLD, 12);
		lb_newChat.setFont(font);
		lb_addFriend.setFont(font);
		lb_option.setFont(font);
		lb_logout.setFont(font);
		lb_close.setFont(font);

		// 사이즈 조절
		Dimension d = new Dimension(70, 25);
		lb_newChat.setPreferredSize(d);
		lb_newChat.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
		lb_addFriend.setPreferredSize(d);
		lb_addFriend.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
		lb_option.setPreferredSize(d);
		lb_option.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
		lb_logout.setPreferredSize(d);
		lb_logout.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
		lb_close.setPreferredSize(d);
		lb_close.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

		// 배경색 설정
		p_newChat.setBackground(Color.WHITE);
		p_newChat.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		p_addFriend.setBackground(Color.WHITE);
		p_addFriend.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		p_option.setBackground(Color.WHITE);
		p_option.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		p_logout.setBackground(Color.WHITE);
		p_logout.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		p_close.setBackground(Color.WHITE);
		p_close.setBorder(BorderFactory.createLineBorder(Color.gray));

		// 레이아웃 설정
		setLayout(new GridLayout(5, 1));

		// 부착
		p_newChat.add(lb_newChat);
		p_addFriend.add(lb_addFriend);
		p_option.add(lb_option);
		p_logout.add(lb_logout);
		p_close.add(lb_close);

		add(p_newChat);
		add(p_addFriend);
		add(p_option);
		add(p_logout);
		add(p_close);

		// 새로운 채팅 라벨과 리스너 연결
		lb_newChat.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// x,y값을 미리구해놔야 옵션창이 사라지기 전에 구해서 오류가 안남, 옵션창의 부모의 좌표값을 가져옴
				int x = (int) e.getComponent().getParent().getLocationOnScreen().getX();
				int y = (int) e.getComponent().getParent().getLocationOnScreen().getY();
				mainFrame.addChat = new AddChat(mainFrame);
				mainFrame.addChat.setBounds(x - 420, y + 50, 600, 500);
				dispose();
			}
		});

		// 친구 추가 라벨과 리스너 연결
		lb_addFriend.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int x = (int) e.getComponent().getParent().getLocationOnScreen().getX();
				int y = (int) e.getComponent().getParent().getLocationOnScreen().getY();
				mainFrame.addFriend = new AddFriend(mainFrame);
				mainFrame.addFriend.setBounds(x - 320, y + 50, 400, 400);
				dispose();
			}
		});

		// 설정 라벨과 리스너 연결
		lb_option.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int x = (int) e.getComponent().getParent().getLocationOnScreen().getX();
				int y = (int) e.getComponent().getParent().getLocationOnScreen().getY();
				settings = new Settings(mainFrame, member, chat, ChannelOption.this);
				settings.setBounds(x - 420, y - 50, 600, 500);
				dispose();
			}
		});

		// 로그아웃 라벨과 리스너 연결
		lb_logout.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(mainFrame, "로그아웃 되었습니다");
				loginMain = new LoginMain();
				dispose();
				mainFrame.dispose();
			}
		});

		// 종료 라벨과 리스너 연결
		lb_close.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});

		setSize(70, 125);
		setUndecorated(true); // 타이틀바 없애기
		setVisible(true);
	}
}