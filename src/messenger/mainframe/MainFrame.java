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
	public JPanel p_state; // ���� ���� ���¸� ��Ÿ���� �г�
	public JPanel p_channel; // �� ����� ���̴� �г�
	JPanel[] pages = new JPanel[2]; 
	public JPanel p_cleft, p_cright; // ���� ��Ÿ���� ���� ���� �г�
	JLabel lb_person, lb_bubble, lb_alarm, lb_option;
	public SearchPanel p_search; // �˻�â �г�
	JPanel p_south; // ������ ���� �г�
	JScrollPane scroll;
	Image mainIcon, img_person, img_bubble, img_alarm, img_option;
	ImageLoad imageLoad;
	ChannelOption channelOption;
	public MemberListMain memberListMain;
	public ChatListMain chatListMain; 
	public AddChat addChat;
	public AddFriend addFriend;
	public static final int WIDTH = 500; // ��üȭ�� ����
	public static final int HEIGHT = 800; // ��üȭ�� ����
	boolean flag_alarm = true; // �˶� �¿���
	boolean flag_option = false; // �ɼ�â visible
	Thread thread;
	boolean chat = false;
	private String font = "����";
	private Color color = new Color(147, 119, 94);
	public JLabel lb_img;
	public JLabel lb_name;
	public ClientThread ct;
	public MemberLogin memberLogin;

	public String result;
	public String[] myProfile; // �� �������� �����ϴ� �迭
	public ArrayList friendList; // ģ�� ����Ʈ�� �����ϴ� array list (�߿� ��� ������ �����)
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

		createState(); // ����â �гο� ���� �� ����

		// �� �󺧿� ���� �̹��� ��������
		mainIcon = imageLoad.getImage("speechBubbleIcon.png");
		img_person = imageLoad.getImage("users.png");
		img_person = img_person.getScaledInstance(35, 50, Image.SCALE_SMOOTH); // �̹��� ������ ����
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

		// ������ ����
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

		// ���� ����
		Color c = getColor();
		p_state.setBackground(c);
		p_channel.setBackground(c);
		p_cleft.setBackground(c);
		p_cright.setBackground(c);
		p_search.setBackground(Color.WHITE);

		p_north.setLayout(new BorderLayout());
		p_state.setLayout(new BorderLayout());
		p_channel.setLayout(new BorderLayout());

		// ����
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

		// ģ����� ���� ������
		lb_person.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				chat = false;
				chageChannel(0);
				chatListMain.removeAll();// ģ����� �ҷ��ö� ���� ������ �ִ� ��� ������� ���
			}
		});
		// ä�ø�� ���� ������
		lb_bubble.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				chatListMain.removeAll();// ģ����� �ҷ��ö� ���� ������ �ִ� ��� ������� ���
				// ��ǥ�� �����ͼ� �ѷ��ֱ�(��ȣ)
				// �̶� DB���� ģ�� ��� �����;� ��.. Ŭ��������
				Thread FT = new FriendListThread(chatListMain, ct, myProfile[0]);
				FT.start();
//				JSONObject obj = new JSONObject();
//				obj.put("Type", "chatlist");
//				obj.put("Code", myProfile[0]); // ���ڵ�

//				ct.send(obj.toString());

				if (!chat) {
					chageChannel(1);
					chat = true;
				}
				int x = (int) e.getComponent().getLocationOnScreen().getX();
				int y = (int) e.getComponent().getLocationOnScreen().getY();
				// chatListMain.plusBt.setVisible(true);
				// chatListMain.plusBt.setBounds(x + 300, y + 620, 70, 70);
				// �̶� ä�ø���Ʈ ����� �޼��� �����ؾߵɵ�

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
					int x = (int) e.getComponent().getLocationOnScreen().getX(); // ���� x�� ������
					int y = (int) e.getComponent().getLocationOnScreen().getY();// ���� y�� ������
					channelOption.setBounds(x - 60, y + 50, 140, 250);// �ɼ�ȭ�� ��ġ,ũ�� �����ֱ�
				} else {
					channelOption.dispose();
				}
			}
		});

		addComponentListener(new ComponentAdapter() { // �߿� ȭ�� ���� �����ϴ� ������
			public void componentResized(ComponentEvent e) {
				Dimension d = e.getComponent().getSize();
				int width = (int) d.getWidth();
				int height = (int) d.getHeight();
				memberListMain.scroll.setPreferredSize(new Dimension(width, height - 188));
				// scroll.setPreferredSize(d);
			}
		});

		addWindowFocusListener(new WindowFocusListener() { /// ��Ŀ�� �ٸ� ������ �ٲٸ� �ɼ�â �������
			public void windowLostFocus(WindowEvent e) {

			}

			public void windowGainedFocus(WindowEvent e) {
				if (channelOption != null) {
					channelOption.dispose();
				}
			}
		});

		setIconImage(mainIcon); // �������� ���� ������
		setTitle("����Ʈ��");

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

	// ����â ���� �Լ�
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