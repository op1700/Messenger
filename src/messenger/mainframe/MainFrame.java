package messenger.mainframe;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import messenger.chatlist.ChatListMain;
import messenger.client.ClientThread;
import messenger.login.MemberLogin;
import messenger.memberlist.FriendListThread;
import messenger.memberlist.MemberListMain;
import messenger.option.AddChat;
import messenger.option.AddFriend;
import messenger.option.ChannelOption;
import messenger.utils.ImageLoad;

public class MainFrame extends JFrame {
	JPanel p_north;
	public JPanel p_state; // 현재 나의 상태를 나타내는 패널
	public JPanel p_channel; // 탭 목록을 붙이는 패널
	JPanel[] pages = new JPanel[2]; 
	public JPanel p_cleft, p_cright; // 탭을 나타내는 라벨을 붙일 패널
	JLabel lb_person, lb_bubble, lb_alarm, lb_option;
	public SearchPanel p_search; // 검색창 패널
	JPanel p_south; // 내용을 붙일 패널
	JScrollPane scroll;
	Image mainIcon, img_person, img_bubble, img_alarm, img_option;
	ImageLoad imageLoad;
	ChannelOption channelOption;
	public MemberListMain memberListMain;
	public ChatListMain chatListMain; 
	public AddChat addChat;
	public AddFriend addFriend;
	public static final int WIDTH = 500; // 전체화면 가로
	public static final int HEIGHT = 800; // 전체화면 세로
	boolean flag_alarm = true; // 알람 온오프
	boolean flag_option = false; // 옵션창 visible
	Thread thread;
	boolean chat = false;
	private String font = "바탕";
	private Color color = new Color(147, 119, 94);
	public JLabel lb_img;
	public JLabel lb_name;
	public ClientThread ct;
	public MemberLogin memberLogin;

	public String result;
	public String[] myProfile; // 내 프로필을 저장하는 배열
	public ArrayList friendList; // 친구 리스트를 저장하는 array list (중요 계속 가져다 써야함)
	public ArrayList chatList = new ArrayList();
	MainFrame mainFrame;
	public ArrayList memberArray = new ArrayList();
	public JSONArray firstChatList = new JSONArray();

	public MainFrame(ClientThread ct, String[] myProfile) {
		this.ct = ct;
		this.myProfile = myProfile;
		
		mainFrame = this;
		p_north = new JPanel();
		p_state = new JPanel();
		p_channel = new JPanel();
		p_cleft = new JPanel();
		p_cright = new JPanel();
		p_search = new SearchPanel(this);
		p_south = new JPanel();
		imageLoad = new ImageLoad();
		memberListMain = new MemberListMain(this);
		chatListMain = new ChatListMain(this);

		pages[0] = memberListMain;
		pages[1] = chatListMain;

		scroll = new JScrollPane(pages[1], JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		createState(); // 상태창 패널에 붙일 라벨 생성

		// 탭 라벨에 붙일 이미지 가져오기
		mainIcon = imageLoad.getImage("speechBubbleIcon.png");
		img_person = imageLoad.getImage("users.png");
		img_person = img_person.getScaledInstance(35, 50, Image.SCALE_SMOOTH); // 이미지 사이즈 조절
		img_bubble = imageLoad.getImage("chat.png");
		img_bubble = img_bubble.getScaledInstance(35, 50, Image.SCALE_SMOOTH);
		img_alarm = imageLoad.getImage("alarm.png");
		img_alarm = img_alarm.getScaledInstance(35, 50, Image.SCALE_SMOOTH);
		img_option = imageLoad.getImage("settings.png");
		img_option = img_option.getScaledInstance(35, 50, Image.SCALE_SMOOTH);

		lb_person = new JLabel();
		lb_person.setIcon(new ImageIcon(img_person));
		lb_bubble = new JLabel();
		lb_bubble.setIcon(new ImageIcon(img_bubble));
		lb_alarm = new JLabel();
		lb_alarm.setIcon(new ImageIcon(img_alarm));
		lb_option = new JLabel();
		lb_option.setIcon(new ImageIcon(img_option));

		// 사이즈 조절
		p_north.setPreferredSize(new Dimension(500, 150));
		p_state.setPreferredSize(new Dimension(500, 50));
		p_channel.setPreferredSize(new Dimension(500, 50));
		p_cleft.setPreferredSize(new Dimension(150, 50));
		p_cleft.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		p_cright.setPreferredSize(new Dimension(150, 50));
		p_cright.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		p_search.setPreferredSize(new Dimension(500, 50));
		p_south.setPreferredSize(new Dimension(500, 650));

		Dimension d = new Dimension(60, 50);
		lb_person.setPreferredSize(d);
		lb_bubble.setPreferredSize(d);
		lb_alarm.setPreferredSize(d);
		lb_option.setPreferredSize(d);

		// 배경색 설정
		Color c = getColor();
		p_state.setBackground(c);
		p_channel.setBackground(c);
		p_cleft.setBackground(c);
		p_cright.setBackground(c);
		p_search.setBackground(Color.WHITE);

		p_north.setLayout(new BorderLayout());
		p_state.setLayout(new BorderLayout());
		p_channel.setLayout(new BorderLayout());

		// 부착
		p_cleft.add(lb_person);
		p_cleft.add(lb_bubble);
		p_cright.add(lb_alarm);
		p_cright.add(lb_option);

		p_channel.add(p_cleft, BorderLayout.WEST);
		p_channel.add(p_cright, BorderLayout.EAST);

		p_north.add(p_state, BorderLayout.NORTH);
		p_north.add(p_channel);
		p_north.add(p_search, BorderLayout.SOUTH);

		p_south.add(pages[0]);
		p_south.add(scroll);

		p_south.setLayout(new CardLayout());
		add(p_north, BorderLayout.NORTH);
		add(p_south);

		// 친구목록 보는 리스너
		lb_person.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				chat = false;
				chageChannel(0);
				chatListMain.removeAll();// 친구목록 불러올때 새로 기존에 있던 목록 지우려고 사용
			}
		});
		// 채팅목록 보는 리스너
		lb_bubble.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				chatListMain.removeAll();// 친구목록 불러올때 새로 기존에 있던 목록 지우려고 사용
				// 좌표값 가져와서 뿌려주기(민호)
				// 이때 DB에서 친구 목록 가져와야 함.. 클릭했을시
				Thread FT = new FriendListThread(chatListMain, ct, myProfile[0]);
				FT.start();
//				JSONObject obj = new JSONObject();
//				obj.put("Type", "chatlist");
//				obj.put("Code", myProfile[0]); // 내코드

//				ct.send(obj.toString());

				if (!chat) {
					chageChannel(1);
					chat = true;
				}
				int x = (int) e.getComponent().getLocationOnScreen().getX();
				int y = (int) e.getComponent().getLocationOnScreen().getY();
				// chatListMain.plusBt.setVisible(true);
				// chatListMain.plusBt.setBounds(x + 300, y + 620, 70, 70);
				// 이때 채팅리스트 만드는 메서드 실행해야될듯

			}

		});

		lb_alarm.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				flag_alarm = !flag_alarm;
				if (flag_alarm) {
					img_alarm = imageLoad.getImage("alarm.png");
				} else {
					img_alarm = imageLoad.getImage("alarmoff.png");
				}
				img_alarm = img_alarm.getScaledInstance(35, 50, Image.SCALE_SMOOTH);
				lb_alarm.setIcon(new ImageIcon(img_alarm));
			}
		});

		lb_option.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				flag_option = !flag_option;
				if (flag_option) {
					channelOption = new ChannelOption(MainFrame.this, memberListMain, chatListMain);
					int x = (int) e.getComponent().getLocationOnScreen().getX(); // 현재 x값 얻어오기
					int y = (int) e.getComponent().getLocationOnScreen().getY();// 현재 y값 얻어오기
					channelOption.setBounds(x - 60, y + 50, 140, 250);// 옵션화면 위치,크기 정해주기
				} else {
					channelOption.dispose();
				}
			}
		});

		addComponentListener(new ComponentAdapter() { // 중요 화면 비율 조정하는 리스너
			public void componentResized(ComponentEvent e) {
				Dimension d = e.getComponent().getSize();
				int width = (int) d.getWidth();
				int height = (int) d.getHeight();
				memberListMain.scroll.setPreferredSize(new Dimension(width, height - 188));
				// scroll.setPreferredSize(d);
			}
		});

		addWindowFocusListener(new WindowFocusListener() { /// 포커스 다른 곳으로 바꾸면 옵션창 사라지게
			public void windowLostFocus(WindowEvent e) {

			}

			public void windowGainedFocus(WindowEvent e) {
				if (channelOption != null) {
					channelOption.dispose();
				}
			}
		});

		setIconImage(mainIcon); // 제일위에 메인 아이콘
		setTitle("네이트톡");

		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setBounds(700, 200, WIDTH, HEIGHT);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void chageChannel(int page) {
		for (int i = 0; i < pages.length; i++) {
			if (i == page) {
				p_search.setInfoText(i);
				p_search.t_search.setFocusable(false);
				pages[i].setVisible(true);
			} else {
				pages[i].setVisible(false);
			}
		}
	}

	// 상태창 생성 함수
	public void createState() {
		lb_img = new JLabel();
		lb_name = new JLabel();

		ImageLoad imageLoad = new ImageLoad();
		Image image = imageLoad.getImage("dog/" + myProfile[4]);
		image = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		BufferedImage circleBuffer = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = circleBuffer.createGraphics();
		g2.setComposite(AlphaComposite.Src);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.fill(new RoundRectangle2D.Float(0, 0, 40, 40, 35, 35));
		g2.setComposite(AlphaComposite.SrcAtop);
		g2.drawImage(image, 0, 0, 40, 40, null);
		g2.dispose();

		lb_img.setIcon(new ImageIcon(circleBuffer));
		lb_img.setBounds(10, 0, 50, 50);
		lb_name.setText(myProfile[1]);
		lb_name.setFont(new Font(font, Font.BOLD, 16));
		lb_name.setBounds(60, 10, 440, 30);

		p_state.add(lb_img);
		p_state.add(lb_name);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getFontString() {
		return font;
	}

	public void setFontString(String font) {
		this.font = font;
	}

//   public static void main(String[] args) {
//      new MainFrame(null, null);
//   }

}