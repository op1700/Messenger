package messenger.chat;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import messenger.mainframe.MainFrame;
import messenger.option.AddChat;
import messenger.server.ServerThread;
import messenger.utils.ImageLoad;
import messenger.utils.ParserResult;

public class ChatMain extends JFrame implements ActionListener {
	JPanel p_north, p_img, p_name, p_empty, p_center, p_setting;
	JPanel p_south, p_text, p_function, p_emoticon, p_addFile, p_addImg;
	JLabel la_img, la_name, la_setting, la_emoticon, la_addFile, la_addImg, la_chat;
	JTextPane area_input;
	Pane area;
	JScrollPane scroll, scroll2;
	Image mainIcon, profile_img, setting_img, emoticon_img, addFile_img, addImg_img;
	JButton bt_input;
	ImageLoad imageLoad;
	JFileChooser chooser; // Ž����
	File file;

	MainFrame mainFrame;
	ServerThread st;
	ParserResult p_parser;
	AddChat addChat;
	ChatRoomProfile chatRoomProfile;
	RoomSetting setting;
	EmoticonMain emoticon;
	Bubble Bubble;
	MyBubble MyBubble;

	JSONArray newChatArray, farray = new JSONArray(); // ������ ���� �����͸� ��� �迭
	ArrayList chatLogs; // ä�� �α׸� ��� �迭
	public ArrayList ChatArrays = new ArrayList();
	public ArrayList ChatArrays1 = new ArrayList();

	String name; // ���� ä�ù� �̸�
	public String chat_code; // ���� ä�ù� �ڵ�
	int cnt = 0; // ���ο� ��ȭ�� ������ �����ϴ� ����
	private Color color = Color.WHITE; // �̼����� �⺻ ä�� ����
	public static boolean Yes = false;

	boolean scrollmove = false;

	String icon = null;
	public Thread thread;
	public ArrayList ChatArrays2;
	public int count;
	boolean firstFlag = true;
	public ArrayList memberArray = new ArrayList();
	boolean log;
	public JSONArray firstChatList = new JSONArray();

	ChatMain chatMain;
	ParserResult parserResult;
	
	public ChatMain(String name, MainFrame mainFrame, String chat_code, ArrayList Arrays, ParserResult parserResult) {// ä�÷α�������
		this.mainFrame = mainFrame;
		this.name = name;
		this.chat_code = chat_code;
		this.ChatArrays = Arrays;
		this.parserResult=parserResult;
		log = true;
		ChatArrays1 = Arrays;
		ArrayList friendCode = new ArrayList();
		ChatArrays2 = new ArrayList();
		chatMain=this;
		count = ChatArrays.size();

		for (int a = 0; a < Arrays.size(); a++) {
			String[] friend = (String[]) Arrays.get(a);
			if (friendCode.contains(friend[0])) {
				// �����Ƿ� ���� ����
			} else {
				friendCode.add(friend[0]);
			}
		}
//		if(ChatArrays.size()==1) {
//			
//		}else {
//			
//		}
		String[] images = new String[friendCode.size()]; // ���� ������ ������� �� ������ ������
		images[0] = mainFrame.myProfile[4];// ù��°�� ������ ���̹���
		// �������ڵ� �����߰�~������ �̹��� �������
		int cnt = 1; // �̹��� ������� ���� ����
		for (int i = 0; i < friendCode.size(); i++) {
			String code = (String) friendCode.get(i);
			if (code.equals(mainFrame.myProfile[0])) {
				// �ϰ͵�����
			} else {
				for (int a = 0; a < mainFrame.friendList.size(); a++) {
					String[] friend = (String[]) mainFrame.friendList.get(a);
					if (code.equals(friend[0])) {
						images[cnt] = friend[4];
						cnt++;
					} else if (code.equals(mainFrame.myProfile[0])) {
						// �ϰ͵� ����
					}
				}
			}
		}


		memberList();
		overLoading(name, images);/// �̹��� �־������
		area_input.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					chatTing();
				}
			}
		});
		chatUpdate(chat_code);// ���� DB���ִ� ����� ���̱�

		// ���⼭ ������ ��� �Ұ� ����
		thread = new ChatThread(mainFrame, this, chat_code);
		thread.start();
	}

	public ChatMain(String name, MainFrame mainFrame, JSONArray array, ParserResult parserResult) {// ���ο�ä�� ä�÷α� ��������
		this.mainFrame = mainFrame;
		this.name = name;
		this.newChatArray = array;
		this.parserResult=parserResult;
		log = false;
		firstChatList = mainFrame.firstChatList;
		chatMain=this;
		
		String[] images = new String[array.size()]; // �������� ������
		images[0] = mainFrame.myProfile[4];
		for (int i = 1; i < array.size(); i++) { // ù��°�� ������ �����̵� �̹Ƿ� 1���� �����ؼ� ģ�� �̹����� ã�ƿ�
			JSONObject obj = (JSONObject) array.get(i);
			String nick = (String) obj.get("Chatmember");
			for (int a = 0; a < mainFrame.friendList.size(); a++) {
				String[] friend = (String[]) mainFrame.friendList.get(a);
				if (nick.equals(friend[2])) {
					images[i] = friend[4];
				}
			}
		}
		overLoading(name, images);

		area_input.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					if (firstFlag) {
						firstChat(area_input.getText());
						count = 0; // ó�� ���� �Է��ϰ� ���� ������ �ִٰ� �����ϰ� �����
						firstFlag = !firstFlag; // ó�� �Է��ϰ� ���� ����ĥ���� �ٲ���� ���ư�
						//System.out.println(firstFlag);
						area_input.setText("");
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						System.out.println("ê�ڵ� ������?"+chat_code);
						thread = new ChatThread(mainFrame, chatMain, chat_code);
						thread.start();
					} else {
						chatTing();
					}
				}
			}
		});

		// �̹��� ��Ʈ�� �迭�� ������	

	}

	public void memberList() {
		JSONObject orderObj = new JSONObject();
		orderObj.put("Type", "memberlist");
		orderObj.put("ChatCode", chat_code);
		mainFrame.ct.send(orderObj.toString());
	}

	public void chatTing() {
		String say = area_input.getText();
		JSONObject obj = new JSONObject();
		obj.put("Type", "chating");
		obj.put("C.Code", chat_code);
		obj.put("U.Code", mainFrame.myProfile[0]);
		obj.put("Log", say);
		mainFrame.ct.send(obj.toString());
		System.out.println("�ι�° �Է��Դϴ�");
		area_input.setText("");
		Basic(chat_code);
	}

	public void firstChat(String say) { // ģ���� ������ ê �ڵ� �̸����� ��� ����??
		System.out.println("��� ������ : " + firstChatList.size());
		for (int i = 0; i < firstChatList.size(); i++) {
			JSONObject obj = (JSONObject) firstChatList.get(i);// array���� �ϳ��� �� ������
			System.out.println(obj.get("Chatmember").toString());
			System.out.println(obj.get("ChatName").toString());
			obj.put("Type", "newchating");
			obj.put("User", mainFrame.myProfile[0]); // mainFrame.Mycode
			obj.put("Say", say);
			farray.add(obj);

		}
		System.out.println("ó�� �Է� �ϰ� ���� farray" + farray);
		mainFrame.ct.send(farray.toJSONString());
		//area_input.setText("");
	}

	public void comparison(int one, ArrayList two, String chat_code) {
		// one = 10 , two = 13 11 12 13���� ������
		//System.out.println("one size : " + one);
		//System.out.println("two size : " + two.size());
		int count = two.size() - one;
		//System.out.println("���� ���ư��� ���ּ����������");
		for (int i = one; i < two.size(); i++) {
			String[] logs = (String[]) two.get(i);
			String code = logs[0];
			String log = logs[1];
			String time = logs[2];
			//System.out.println("11111111111111111111111111");
			for (int a = 0; a < mainFrame.friendList.size(); a++) {
				String[] friend = (String[]) mainFrame.friendList.get(a);
				// String friend_code = friend[0];
				if (code.equals(mainFrame.myProfile[0])) {
					MyBubble my = new MyBubble(log, time, this.mainFrame, this);

					setEndline();
					area.appendLine(my);
					break;
				} else {
					if (code.equals(friend[0])) {
						Bubble bu = new Bubble(friend[4], friend[1], log, time, this.mainFrame, this);
						setEndline();
						area.appendLine(bu);
						break;
					}
				}
			}
		}
		//System.out.println("22222222222222222222222222");
		area.updateUI();
		//System.out.println("������Ʈ UI");
	}

	public void setEndline() {
		area.selectAll();
		// ������ ���� ������ Ŀ�� �̵��ϰ� ����
		area.setSelectionStart(area.getSelectionEnd());
	}

	public void chatUpdate(String room_code) { // ó�� �ѹ��� �ؾ���
		this.chat_code = room_code;
		for (int i = 0; i < ChatArrays.size(); i++) {
			String[] logs = (String[]) ChatArrays.get(i);
			String code = logs[0];
			String log = logs[1];
			String time = logs[2];
			for (int a = 0; a < mainFrame.friendList.size(); a++) {
				String[] friend = (String[]) mainFrame.friendList.get(a);
				// String friend_code = friend[0];
				if (code.equals(mainFrame.myProfile[0])) {
					MyBubble my = new MyBubble(log, time, this.mainFrame, this);
					// System.out.println("���� " + log);
					setEndline();
					area.insertComponent(my);
					break;
				} else {
					if (code.equals(friend[0])) {
						Bubble bu = new Bubble(friend[4], friend[1], log, time, this.mainFrame, this);
						// System.out.println("�ϲ� " + log);
						setEndline();
						area.insertComponent(bu);
						break;
					}
				}
			}
		}
	}

	// updateUI
	public void Basic(String room_code) {
		this.chat_code = room_code;
		JSONObject obj = new JSONObject();
		obj.put("Type", "chatUI");
		obj.put("C.Code", chat_code);
		mainFrame.ct.send(obj.toString());
	}

//	public void UpdateUI(ArrayList ChatArrays1) { // ������
//		this.ChatArrays1 = ChatArrays1;
//		int chatsize1 = ChatArrays.size();
//		int chatsize2 = ChatArrays1.size();
//		if (chatsize1 != chatsize2) {
//			ChatArrays = ChatArrays1;
//			new ChatMain(this.name, this.mainFrame, this.chat_code, ChatArrays);
//		}
//	}

	// ���� ���� �Լ�
	public void addFile() {
		int result = chooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			// ����(������ ������ ������ ��ȯ)
			file = chooser.getSelectedFile(); // ���ϼ���
		}
	}

	// �̹��� ���� �Լ�
	public void addImg() {
		int result = chooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
			String path = file.getAbsolutePath();
			ImageIcon icon = new ImageIcon(path);
			Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			JLabel la_icon = new JLabel(new ImageIcon(img));
			la_icon.setPreferredSize(new Dimension(800, 50));
			area.insertComponent(la_icon);

			la_icon.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					System.out.println("�̹��� �ٿ�ε� �۾�");
				}
			});
		}

	}

	public void sendIcon(String iconName) {
		String say = iconName;
		JSONObject obj = new JSONObject();
		obj.put("Type", "chating");
		obj.put("C.Code", chat_code);
		obj.put("U.Code", mainFrame.myProfile[0]);
		obj.put("Log", say);
		mainFrame.ct.send(obj.toString());
	}

	// ä�ù� ���� �̸��� �������� �Լ� @@@@@@@@@@
	public String getImgName() {
		String img = "cat.jpg";
		return img;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public JSONArray getFirstChatList() {
		return firstChatList;
	}

	public void setFirstChatList(JSONArray firstChatList) {
		this.firstChatList = firstChatList;
	}

	public void actionPerformed(ActionEvent e) {
	}

	public void overLoading(String name, String[] images) {
		chatLogs = new ArrayList(); // ä�� �α׸� ��� �迭
		p_north = new JPanel(); // ä�ù� ������ ������ ���� �г�
		p_img = new JPanel(); // ä�ù� ���� �г�
		p_name = new JPanel(); // ä�ù� �̸� �г�
		p_empty = new JPanel(); // ä�ù� �̸��� ������ ���� �г�
		p_center = new JPanel(); // ä�ù� �̸��� �� �г��� ���� �г�
		p_setting = new JPanel(); // ���� �г�

		area = new Pane(); // ��ȭ������ �ٴ� ��

		area.setOpaque(true);
		area.setEditable(false);
		scroll = new JScrollPane(area);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		area_input = new JTextPane(); // ��ȭ�� �Է��ϴ� ��
		scroll2 = new JScrollPane(area_input);

		p_south = new JPanel(); // �Է�â�� �̸�Ƽ��,���� ������ ���� �г�
		p_text = new JPanel(); // �Է�â�� ��ư�� ���� �г�
		p_function = new JPanel(); // �̸�Ƽ�ܰ� ���� ������ ���� �г�
		p_emoticon = new JPanel(); // �̸�Ƽ�� �г�
		p_addFile = new JPanel(); // ���� ���� �г�
		p_addImg = new JPanel(); // �̹��� ���� �г�

		imageLoad = new ImageLoad();
		mainIcon = imageLoad.getImage("speechBubbleIcon.png");

		String[] path = new String[images.length];
		ArrayList<Image> imgName = new ArrayList<Image>();
		ArrayList<Image> imgName2 = new ArrayList<Image>(); // �ɼǿ� �̹��� ����������
		if (images.length < 5) {
			for (int i = 0; i < images.length; i++) {
				Image image = imageLoad.getImage("dog/" + images[i]);
				image = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				Image image2 = imageLoad.getImage("dog/" + images[i]);
				image2 = image2.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
				if (images.length == 2) { // �̰� 1:1 ä���϶� ���̹��� ����̹����� ������
					image = createImg(image, 40);
					imgName.add(image);

					image2 = createImg(image2, 40);
					imgName2.add(image2);
				} else {
					image = createImg(image, 20);
					imgName.add(image);

					image2 = createImg(image2, 40);
					imgName2.add(image2);
				}

			}
		} else {
			for (int i = 1; i < 5; i++) {
				Image image = imageLoad.getImage("dog/" + images[i]);
				image = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);

				Image image2 = imageLoad.getImage("dog/" + images[i]);
				image2 = image2.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
				if (images.length == 2) { // �̰� 1:1 ä���϶� ���̹��� ����̹����� ������
					image = createImg(image, 40);
					imgName.add(image);

					image2 = createImg(image2, 40);
					imgName2.add(image2);
				} else {
					image = createImg(image, 20);
					imgName.add(image);

					image2 = createImg(image2, 40);
					imgName2.add(image2);
				}

			}
		}

		la_img = new JLabel();
		// profile_img = imageLoad.getImage(getImgName()).getScaledInstance(40, 40,
		// Image.SCALE_SMOOTH);
		BufferedImage circleBuffer = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = circleBuffer.createGraphics();
		g2.setComposite(AlphaComposite.Src);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.fill(new RoundRectangle2D.Float(0, 0, 40, 40, 35, 35));
		g2.setComposite(AlphaComposite.SrcAtop);
		if (imgName.size() == 1) { // ���� ���常 ���� ��
			g2.drawImage(imgName.get(0), 0, 0, 40, 40, null);
		} else if (imgName.size() == 2) {
			g2.drawImage(imgName.get(0), 4, 4, 20, 20, null);
			g2.drawImage(imgName.get(1), 16, 16, 20, 20, null);
		} else if (imgName.size() == 3) {
			g2.drawImage(imgName.get(0), 10, 0, 20, 20, null);
			g2.drawImage(imgName.get(1), 1, 14, 20, 20, null);
			g2.drawImage(imgName.get(2), 19, 14, 20, 20, null);
		} else if (imgName.size() >= 4) {
			g2.drawImage(imgName.get(0), 0, 0, 20, 20, null);
			g2.drawImage(imgName.get(1), 20, 0, 20, 20, null);
			g2.drawImage(imgName.get(2), 0, 20, 20, 20, null);
			g2.drawImage(imgName.get(3), 20, 20, 20, 20, null);
		}
		g2.dispose();

		la_img.setIcon(new ImageIcon(circleBuffer));

		la_name = new JLabel(name);
		la_name.setForeground(Color.WHITE);

		la_setting = new JLabel();
		setting_img = imageLoad.getImage("settings.png");
		setting_img = setting_img.getScaledInstance(40, 45, Image.SCALE_SMOOTH);
		la_setting.setIcon(new ImageIcon(setting_img));

		la_emoticon = new JLabel();
		emoticon_img = imageLoad.getImage("smile.png");
		emoticon_img = emoticon_img.getScaledInstance(27, 27, Image.SCALE_SMOOTH);
		la_emoticon.setIcon(new ImageIcon(emoticon_img));

		la_addFile = new JLabel();
		addFile_img = imageLoad.getImage("folder.png");
		addFile_img = addFile_img.getScaledInstance(27, 27, Image.SCALE_SMOOTH);
		la_addFile.setIcon(new ImageIcon(addFile_img));

		la_addImg = new JLabel();
		addImg_img = imageLoad.getImage("image.png");
		addImg_img = addImg_img.getScaledInstance(27, 27, Image.SCALE_SMOOTH);
		la_addImg.setIcon(new ImageIcon(addImg_img));

		bt_input = new JButton("����");
		chooser = new JFileChooser("");

		// ��Ʈ ����
		la_name.setFont(new Font(mainFrame.getFontString(), Font.BOLD, 18));
		bt_input.setFont(new Font(mainFrame.getFontString(), Font.BOLD, 15));

		// ������ ����
		p_img.setPreferredSize(new Dimension(50, 50));
		p_img.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
		Dimension size = la_name.getPreferredSize();
		p_name.setPreferredSize(new Dimension(size.width + 10, 50));
		p_name.setBorder(BorderFactory.createEmptyBorder(12, 10, 0, 0));
		p_empty.setPreferredSize(new Dimension(390 - size.width, 50));
		p_center.setPreferredSize(new Dimension(400, 50));
		p_setting.setPreferredSize(new Dimension(50, 50));
		p_setting.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		p_north.setPreferredSize(new Dimension(500, 55));
		area.setPreferredSize(new Dimension(500, 645));
		area_input.setPreferredSize(new Dimension(350, 40));
		p_text.setPreferredSize(new Dimension(500, 50));
		p_text.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		p_function.setPreferredSize(new Dimension(500, 50));
		p_south.setPreferredSize(new Dimension(500, 100));

		// ���� ����
		p_img.setBackground(mainFrame.getColor());
		p_name.setBackground(mainFrame.getColor());
		p_empty.setBackground(mainFrame.getColor());
		p_setting.setBackground(mainFrame.getColor());
		bt_input.setBackground(Color.WHITE);

		// ���̾ƿ� ����
		p_north.setLayout(new BorderLayout());
		p_center.setLayout(new BorderLayout());
		p_south.setLayout(new BorderLayout());

		// ����
		p_img.add(la_img);
		p_north.add(p_img, BorderLayout.WEST);
		p_name.add(la_name);
		p_center.add(p_name);
		p_center.add(p_empty, BorderLayout.EAST);
		p_north.add(p_center);
		p_setting.add(la_setting);
		p_north.add(p_setting, BorderLayout.EAST);

		p_text.add(scroll2);
		p_text.add(bt_input);
		p_south.add(p_text, BorderLayout.NORTH);
		p_emoticon.add(la_emoticon);
		p_function.add(p_emoticon);
		p_addFile.add(la_addFile);
		p_function.add(p_addFile);
		p_addImg.add(la_addImg);
		p_function.add(p_addImg);
		p_south.add(p_function);

		add(p_north, BorderLayout.NORTH);
		add(scroll);
		add(p_south, BorderLayout.SOUTH);

		// ���콺 ���� �������� ��ũ�ѹٰ� �����̴� ���� ����Ѵ�
		scroll.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent arg0) {
				scrollmove = true;
			}
		});

		scroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent arg0) { //
				if (scrollmove) { // ���� ��ũ�� ���갡 ����������
					scrollmove = false; // ������ ������ ���� �����ʰ�, ��������� �ٲ۴�.
				} else {
					JScrollBar src = (JScrollBar) arg0.getSource();
					src.setValue(src.getMaximum());
				}
			}
		});

		// ä�ù� ���� Ŭ���� ä�ù� ���� â�� �߰� �Ѵ�
		la_img.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				memberArray = mainFrame.memberArray;
				int x = (int) e.getComponent().getParent().getLocationOnScreen().getX();
				int y = (int) e.getComponent().getParent().getLocationOnScreen().getY();
				chatRoomProfile = new ChatRoomProfile(ChatMain.this, imgName2);
				chatRoomProfile.setBounds(x - 360, y, 350, 600);
			}
		});

		// ���� Ŭ���� ä�ù� ���� â�� �߰� �Ѵ�
		la_setting.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int x = (int) e.getComponent().getParent().getLocationOnScreen().getX();
				int y = (int) e.getComponent().getParent().getLocationOnScreen().getY();
				setting = new RoomSetting(mainFrame, ChatMain.this, imgName2);
				setting.setBounds(x + 60, y, 300, 300);
			}
		});

		// �̸�Ƽ�� Ŭ���� �̸�Ƽ�� â ����
		la_emoticon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int x = (int) e.getComponent().getParent().getLocationOnScreen().getX();
				int y = (int) e.getComponent().getParent().getLocationOnScreen().getY();
				emoticon = new EmoticonMain(ChatMain.this);
				emoticon.setBounds(x - 500, y - 200, 300, 270);
			}
		});

		// ���� ���� Ŭ���� ���� Ž���� ����
		la_addFile.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				addFile();
			}
		});

		// �̹��� ���� Ŭ���� ���� Ž���� ����
		la_addImg.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				addImg();
			}
		});

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				parserResult.totalChatRoom.remove(chatMain);
				dispose();
				firstChatList.clear();
				
			}
		});

		setIconImage(mainIcon); // �������� ���� ������
		setTitle("����Ʈ��");
		setBounds(800, 300, 500, 800); // ä�ø�Ͽ��� ��ġ ��������@@@@@@
		setResizable(false);
		setVisible(true);
	}

	public BufferedImage createImg(Image img, int size) {
		BufferedImage circleBuffer = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = circleBuffer.createGraphics();
		g2.setComposite(AlphaComposite.Src);// ������ ���İ� ������Ʈ(�� ������ ������� �𸣰���) �̰� �����̰� �ؿ����� ����ó �ε�?
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);// �̹��� ������� ����
		g2.fill(new RoundRectangle2D.Float(0, 0, size, size, size / 2, size / 2));
		g2.setComposite(AlphaComposite.SrcAtop);
		g2.drawImage(img, 0, 0, size, size, null);
		g2.dispose();
		return circleBuffer;
	}

}