package messenger.chat;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import messenger.mainframe.MainFrame;
import messenger.utils.ImageLoad;

public class RoomSetting extends JFrame {
	JPanel p_wrapper, p_north, p_bg, p_button;
	JPanel p_title, p_room, p_roomImg, p_roomName;
	JPanel p_bgInfo, p_bgImg;
	JLabel la_title, la_roomImg, la_roomName, la_bgInfo;
	JTextField tf_chatTitle;
	JButton bt_chooseColor, bt_chooseImg, bt_confirm, bt_cancel;
	Image img, profile_img;
	Canvas previewCanvas;

	ImageLoad imageLoad;
	JFileChooser imgChooser;
	BackgroundColorList backgroundColorList;
	String profileImgPath;
	String regist_path;
	Color color; // 현재 배경색을 담는 변수
	ArrayList<Image> imgName;

	public RoomSetting(MainFrame mainFrame, ChatMain chatMain) {
		p_wrapper = new JPanel(); // 전체 패널
		p_north = new JPanel(); // 타이틀과 이름설정을 붙일 패널
		p_bg = new JPanel(); // 배경설정을 붙일 패널
		p_button = new JPanel(); // 버튼 패널
		p_title = new JPanel(); // 타이틀 패널
		p_room = new JPanel(); // 채팅방 사진과 이름을 붙일 패널
		p_roomImg = new JPanel(); // 채팅방 사진 패널
		p_roomName = new JPanel(); // 채팅방 이름 패널
		p_bgInfo = new JPanel(); // 배경 설정 패널
		p_bgImg = new JPanel(); // 배경 미리보기 패널

		la_title = new JLabel("채팅방 설정");
		la_title.setForeground(Color.WHITE);
		la_roomImg = new JLabel();

		imageLoad = new ImageLoad();
		profile_img = imageLoad.getImage(chatMain.getImgName()).getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		BufferedImage circleBuffer = new BufferedImage(80, 80, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = circleBuffer.createGraphics();
		g2.setComposite(AlphaComposite.Src);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.fill(new RoundRectangle2D.Float(0, 0, 80, 80, 70, 70));
		g2.setComposite(AlphaComposite.SrcAtop);

		g2.drawImage(profile_img, 0, 0, 80, 80, null);
		g2.dispose();
		la_roomImg.setIcon(new ImageIcon(circleBuffer));

		la_roomName = new JLabel("채팅방 이름", SwingConstants.CENTER);
		la_bgInfo = new JLabel("채팅방 배경화면", SwingConstants.CENTER);

		tf_chatTitle = new JTextField(chatMain.name, 15);
		imgChooser = new JFileChooser();

		bt_chooseColor = new JButton("색 선택");
		bt_chooseImg = new JButton("사진 선택");
		bt_confirm = new JButton("확인");
		bt_cancel = new JButton("취소");
		
		color = chatMain.getColor();

		img = imageLoad.getImage("preview.png");
		previewCanvas = new Canvas() {
			public void paint(Graphics g) {
				g.drawImage(img, 0, 0, 70, 100, this);
			}
		};

		// 폰트 설정
		Font f = new Font(mainFrame.getFontString(), Font.PLAIN, 12);
		la_title.setFont(new Font(mainFrame.getFontString(), Font.BOLD, 14));
		la_roomName.setFont(new Font(mainFrame.getFontString(), Font.BOLD, 16));
		la_bgInfo.setFont(new Font(mainFrame.getFontString(), Font.BOLD, 16));
		bt_chooseColor.setFont(f);
		bt_chooseImg.setFont(f);
		bt_confirm.setFont(f);
		bt_cancel.setFont(f);

		// 사이즈 조절
		p_title.setPreferredSize(new Dimension(300, 30));
		p_room.setPreferredSize(new Dimension(300, 100));
		p_roomImg.setPreferredSize(new Dimension(100, 100));
		p_roomImg.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		la_roomName.setPreferredSize(new Dimension(200, 50));
		tf_chatTitle.setPreferredSize(new Dimension(200, 25));
		p_roomName.setPreferredSize(new Dimension(200, 100));
		p_roomName.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
		p_north.setPreferredSize(new Dimension(300, 130));
		p_bg.setPreferredSize(new Dimension(300, 120));
		la_bgInfo.setPreferredSize(new Dimension(220, 70));
		bt_chooseColor.setPreferredSize(new Dimension(80, 25));
		bt_chooseImg.setPreferredSize(new Dimension(90, 25));
		p_bgInfo.setPreferredSize(new Dimension(220, 120));
		previewCanvas.setPreferredSize(new Dimension(70, 100));
		p_bgImg.setPreferredSize(new Dimension(80, 120));
		p_bgImg.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		bt_confirm.setPreferredSize(new Dimension(60, 30));
		bt_cancel.setPreferredSize(new Dimension(60, 30));
		p_button.setPreferredSize(new Dimension(300, 50));
		p_button.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		p_wrapper.setPreferredSize(new Dimension(300, 300));

		// 배경색 설정
		p_title.setBackground(mainFrame.getColor());
		p_title.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		p_roomImg.setBackground(Color.WHITE);
		p_roomName.setBackground(Color.WHITE);
		p_bgInfo.setBackground(Color.WHITE);
		p_bgImg.setBackground(Color.WHITE);
		bt_chooseColor.setBackground(Color.WHITE);
		bt_chooseImg.setBackground(Color.WHITE);
		p_bg.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		p_button.setBackground(Color.WHITE);
		bt_confirm.setBackground(Color.YELLOW);
		bt_cancel.setBackground(Color.WHITE);
		p_wrapper.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		// 레이아웃 설정
		p_room.setLayout(new BorderLayout());
		p_north.setLayout(new BorderLayout());
		p_bg.setLayout(new BorderLayout());
		p_wrapper.setLayout(new BorderLayout());

		// 부착
		p_title.add(la_title);
		p_roomImg.add(la_roomImg);
		p_room.add(p_roomImg, BorderLayout.WEST);
		p_roomName.add(la_roomName);
		p_roomName.add(tf_chatTitle);
		p_room.add(p_roomName);
		p_north.add(p_title, BorderLayout.NORTH);
		p_north.add(p_room);
		p_bgInfo.add(la_bgInfo);
		p_bgInfo.add(bt_chooseColor);
		p_bgInfo.add(bt_chooseImg);
		p_bgImg.add(previewCanvas);
		p_bg.add(p_bgInfo);
		p_bg.add(p_bgImg, BorderLayout.EAST);
		p_button.add(bt_confirm);
		p_button.add(bt_cancel);

		p_wrapper.add(p_north, BorderLayout.NORTH);
		p_wrapper.add(p_bg);
		p_wrapper.add(p_button, BorderLayout.SOUTH);
		add(p_wrapper);

		// 색 선택 버튼과 리스너 연결
		bt_chooseColor.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int x = (int) e.getComponent().getParent().getLocationOnScreen().getX();
				int y = (int) e.getComponent().getParent().getLocationOnScreen().getY();
				backgroundColorList = new BackgroundColorList(RoomSetting.this, chatMain);
				backgroundColorList.setBounds(x + 300, y, 370, 370);
			}
		});

		// 사진 선택 버튼과 리스너 연결
		bt_chooseImg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseImg(previewCanvas); // 이미지 가져오기
			}
		});

		// 확인 버튼 클릭시 채팅방 이름 변경과 배경 적용
		bt_confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = tf_chatTitle.getText();
				chatMain.la_name.setText(name);
				Dimension size = chatMain.la_name.getPreferredSize();
				chatMain.p_name.setPreferredSize(new Dimension(size.width + 10, 50));
				chatMain.p_empty.setPreferredSize(new Dimension(390 - size.width, 50));
				chatMain.p_name.updateUI();
				chatMain.p_empty.updateUI();

				chatMain.name = name;
				chatMain.setColor(color);
				dispose();
			}
		});

		// 취소 버튼과 리스너 연결
		bt_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color c = chatMain.getColor();
				color = c;
				chatMain.area.setBackground(c);
				dispose();
			}
		});

		setSize(300, 300);
		setResizable(false);
		setUndecorated(true);
		setVisible(true);
	}

	public RoomSetting(MainFrame mainFrame, ChatMain chatMain, ArrayList<Image> imgName) {
		p_wrapper = new JPanel(); // 전체 패널
		p_north = new JPanel(); // 타이틀과 이름설정을 붙일 패널
		p_bg = new JPanel(); // 배경설정을 붙일 패널
		p_button = new JPanel(); // 버튼 패널
		p_title = new JPanel(); // 타이틀 패널
		p_room = new JPanel(); // 채팅방 사진과 이름을 붙일 패널
		p_roomImg = new JPanel(); // 채팅방 사진 패널
		p_roomName = new JPanel(); // 채팅방 이름 패널
		p_bgInfo = new JPanel(); // 배경 설정 패널
		p_bgImg = new JPanel(); // 배경 미리보기 패널

		la_title = new JLabel("채팅방 설정");
		la_title.setForeground(Color.WHITE);
		la_roomImg = new JLabel();
		this.imgName = imgName;

		imageLoad = new ImageLoad();
		//profile_img = imageLoad.getImage(chatMain.getImgName()).getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		BufferedImage circleBuffer = new BufferedImage(80, 80, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = circleBuffer.createGraphics();
		g2.setComposite(AlphaComposite.Src);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.fill(new RoundRectangle2D.Float(0, 0, 80, 80, 70, 70));
		g2.setComposite(AlphaComposite.SrcAtop);
		if (imgName.size() == 1) { // 사진 한장만 넣을 때
			g2.drawImage(imgName.get(0), 0, 0, 80, 80, null);
		} else if (imgName.size() == 2) {
			g2.drawImage(imgName.get(0), 8, 8, 40, 40, null);
			g2.drawImage(imgName.get(1), 32, 32, 40, 40, null);
		} else if (imgName.size() == 3) {
			g2.drawImage(imgName.get(0), 20, 0, 40, 40, null);
			g2.drawImage(imgName.get(1), 2, 30, 40, 40, null);
			g2.drawImage(imgName.get(2), 38, 30, 40, 40, null);
		} else if (imgName.size() >= 4) {
			g2.drawImage(imgName.get(0), 0, 0, 40, 40, null);
			g2.drawImage(imgName.get(1), 40, 0, 40, 40, null);
			g2.drawImage(imgName.get(2), 0, 40, 40, 40, null);
			g2.drawImage(imgName.get(3), 40, 40, 40, 40, null);
		}

		g2.dispose();
		la_roomImg.setIcon(new ImageIcon(circleBuffer));

		la_roomName = new JLabel("채팅방 이름", SwingConstants.CENTER);
		la_bgInfo = new JLabel("채팅방 배경화면", SwingConstants.CENTER);

		tf_chatTitle = new JTextField(chatMain.name, 15);
		imgChooser = new JFileChooser();

		bt_chooseColor = new JButton("색 선택");
		bt_chooseImg = new JButton("사진 선택");
		bt_confirm = new JButton("확인");
		bt_cancel = new JButton("취소");

		img = imageLoad.getImage("preview.png");
		previewCanvas = new Canvas() {
			public void paint(Graphics g) {
				g.drawImage(img, 0, 0, 70, 100, this);
			}
		};

		// 폰트 설정
		Font f = new Font(mainFrame.getFontString(), Font.PLAIN, 12);
		la_title.setFont(new Font(mainFrame.getFontString(), Font.BOLD, 14));
		la_roomName.setFont(new Font(mainFrame.getFontString(), Font.BOLD, 16));
		la_bgInfo.setFont(new Font(mainFrame.getFontString(), Font.BOLD, 16));
		bt_chooseColor.setFont(f);
		bt_chooseImg.setFont(f);
		bt_confirm.setFont(f);
		bt_cancel.setFont(f);

		// 사이즈 조절
		p_title.setPreferredSize(new Dimension(300, 30));
		p_room.setPreferredSize(new Dimension(300, 100));
		p_roomImg.setPreferredSize(new Dimension(100, 100));
		p_roomImg.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		la_roomName.setPreferredSize(new Dimension(200, 50));
		tf_chatTitle.setPreferredSize(new Dimension(200, 25));
		p_roomName.setPreferredSize(new Dimension(200, 100));
		p_roomName.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
		p_north.setPreferredSize(new Dimension(300, 130));
		p_bg.setPreferredSize(new Dimension(300, 120));
		la_bgInfo.setPreferredSize(new Dimension(220, 70));
		bt_chooseColor.setPreferredSize(new Dimension(80, 25));
		bt_chooseImg.setPreferredSize(new Dimension(90, 25));
		p_bgInfo.setPreferredSize(new Dimension(220, 120));
		previewCanvas.setPreferredSize(new Dimension(70, 100));
		p_bgImg.setPreferredSize(new Dimension(80, 120));
		p_bgImg.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		bt_confirm.setPreferredSize(new Dimension(60, 30));
		bt_cancel.setPreferredSize(new Dimension(60, 30));
		p_button.setPreferredSize(new Dimension(300, 50));
		p_button.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		p_wrapper.setPreferredSize(new Dimension(300, 300));

		// 배경색 설정
		p_title.setBackground(mainFrame.getColor());
		p_title.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		p_roomImg.setBackground(Color.WHITE);
		p_roomName.setBackground(Color.WHITE);
		p_bgInfo.setBackground(Color.WHITE);
		p_bgImg.setBackground(Color.WHITE);
		bt_chooseColor.setBackground(Color.WHITE);
		bt_chooseImg.setBackground(Color.WHITE);
		p_bg.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		p_button.setBackground(Color.WHITE);
		bt_confirm.setBackground(Color.YELLOW);
		bt_cancel.setBackground(Color.WHITE);
		p_wrapper.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		// 레이아웃 설정
		p_room.setLayout(new BorderLayout());
		p_north.setLayout(new BorderLayout());
		p_bg.setLayout(new BorderLayout());
		p_wrapper.setLayout(new BorderLayout());

		// 부착
		p_title.add(la_title);
		p_roomImg.add(la_roomImg);
		p_room.add(p_roomImg, BorderLayout.WEST);
		p_roomName.add(la_roomName);
		p_roomName.add(tf_chatTitle);
		p_room.add(p_roomName);
		p_north.add(p_title, BorderLayout.NORTH);
		p_north.add(p_room);
		p_bgInfo.add(la_bgInfo);
		p_bgInfo.add(bt_chooseColor);
		p_bgInfo.add(bt_chooseImg);
		p_bgImg.add(previewCanvas);
		p_bg.add(p_bgInfo);
		p_bg.add(p_bgImg, BorderLayout.EAST);
		p_button.add(bt_confirm);
		p_button.add(bt_cancel);

		p_wrapper.add(p_north, BorderLayout.NORTH);
		p_wrapper.add(p_bg);
		p_wrapper.add(p_button, BorderLayout.SOUTH);
		add(p_wrapper);

		// 색 선택 버튼과 리스너 연결
		bt_chooseColor.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int x = (int) e.getComponent().getParent().getLocationOnScreen().getX();
				int y = (int) e.getComponent().getParent().getLocationOnScreen().getY();
				backgroundColorList = new BackgroundColorList(RoomSetting.this, chatMain);
				backgroundColorList.setBounds(x + 300, y, 370, 370);
			}
		});

		// 사진 선택 버튼과 리스너 연결
		bt_chooseImg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseImg(previewCanvas); // 이미지 가져오기
			}
		});

		// 확인 버튼 클릭시 채팅방 이름 변경과 배경 적용
		bt_confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = tf_chatTitle.getText();
				chatMain.la_name.setText(name);
				Dimension size = chatMain.la_name.getPreferredSize();
				chatMain.p_name.setPreferredSize(new Dimension(size.width + 20, 50));
				chatMain.p_empty.setPreferredSize(new Dimension(380 - size.width, 50));
				chatMain.p_name.updateUI();
				chatMain.p_empty.updateUI();

				chatMain.name = name;
				chatMain.setColor(color);
				dispose();
			}
		});

		// 취소 버튼과 리스너 연결
		bt_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color c = chatMain.getColor();
				color = c;
				chatMain.area.setBackground(c);
				dispose();
			}
		});

		setSize(300, 300);
		setResizable(false);
		setUndecorated(true);
		setVisible(true);
	}

	// 배경으로 사용할 이미지 가져와서 미리보기에 뿌리기
	public void chooseImg(Canvas can) {
		int result = imgChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = imgChooser.getSelectedFile();
			regist_path = file.getAbsolutePath();
			ImageIcon previewIcon = new ImageIcon(regist_path);
			img = previewIcon.getImage();
			img = img.getScaledInstance(350, 500, Image.SCALE_SMOOTH);
			can.repaint();
		}
	}
}