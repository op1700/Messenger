package messenger.option;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import messenger.chatlist.ChatListMain;
import messenger.chatlist.ChatListPanel;
import messenger.mainframe.MainFrame;
import messenger.memberlist.ListCellRenderer;
import messenger.memberlist.MemberListMain;

public class Settings extends JFrame {
	JPanel p_wrapper, p_title, p_center, p_button;
	JPanel p_font, p_bg, p_bgCenter;
	JLabel lb_title, lb_font, lb_bg;
	Choice ch_font;
	JButton bt_apply, bt_cancel;
	String[] font = { "맑은 고딕", "굴림", "궁서", "돋움", "바탕", "나눔고딕", "한컴 고딕", "한컴산뜻돋움" };
	Color[] c = { new Color(225, 225, 128), new Color(128, 225, 128), new Color(128, 225, 225),
			new Color(255, 128, 128), new Color(225, 171, 87), new Color(192, 192, 192), new Color(140, 140, 225),
			new Color(193, 132, 255), new Color(225, 225, 0), new Color(147, 119, 94) };
	MainFrame mainFrame;
	MemberListMain member;
	ChatListMain chat;
	ChannelOption channelOption;

	String setFont; // 현재 폰트를 담는 변수
	Color color; // 현재 배경색을 담는 변수
	boolean flag_font = false; // 폰트 변경 여부
	boolean flag_color = false; // 배경색 변경 여부
	boolean flag_visible = false; // 설정창 visible

	public Settings(MainFrame mainFrame, MemberListMain member, ChatListMain chat, ChannelOption channelOption) {
		this.mainFrame = mainFrame;
		this.member = member;
		this.chat = chat;
		this.channelOption = channelOption;

		p_wrapper = new JPanel(); // 전체 패널
		p_title = new JPanel(); // 타이틀 패널
		p_center = new JPanel(); // 폰트와 배경을 붙일 패널
		p_font = new JPanel(); // 폰트 패널
		p_bg = new JPanel(); // 배경 패널
		p_bgCenter = new JPanel(); // 배경색 예시를 붙일 패널
		p_button = new JPanel(); // 확인,취소버튼을 붙일 패널

		lb_title = new JLabel("설정");
		lb_title.setForeground(Color.WHITE);

		lb_font = new JLabel("폰트    ");
		ch_font = new Choice();
		for (int i = 0; i < font.length; i++) {
			ch_font.add(font[i]);
		}
		lb_bg = new JLabel("배경", SwingConstants.CENTER);
		for (int i = 0; i < c.length; i++) {
			setCircle(i); // 원만들기
		}

		bt_apply = new JButton("확인");
		bt_cancel = new JButton("취소");

		// 폰트 설정
		lb_title.setFont(new Font(mainFrame.getFontString(), Font.BOLD, 16));
		lb_font.setFont(new Font(mainFrame.getFontString(), Font.BOLD, 16));
		lb_bg.setFont(new Font(mainFrame.getFontString(), Font.BOLD, 18));
		bt_apply.setFont(new Font(mainFrame.getFontString(), Font.PLAIN, 14));
		bt_cancel.setFont(new Font(mainFrame.getFontString(), Font.PLAIN, 14));

		// 사이즈 조절
		p_title.setPreferredSize(new Dimension(600, 40));
		p_title.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
		ch_font.setPreferredSize(new Dimension(300, 40));
		p_font.setPreferredSize(new Dimension(600, 80));
		p_font.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
		lb_bg.setPreferredSize(new Dimension(600, 60));
		p_bgCenter.setPreferredSize(new Dimension(600, 260));
		p_bg.setPreferredSize(new Dimension(600, 320));
		p_center.setPreferredSize(new Dimension(600, 400));
		bt_apply.setPreferredSize(new Dimension(70, 40));
		bt_cancel.setPreferredSize(new Dimension(70, 40));
		p_button.setPreferredSize(new Dimension(600, 60));
		p_button.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		p_wrapper.setPreferredSize(new Dimension(600, 500));

		// 배경색 설정
		p_title.setBackground(mainFrame.getColor());
		p_font.setBackground(Color.WHITE);
		p_bg.setBackground(Color.WHITE);
		p_bgCenter.setBackground(Color.WHITE);
		p_bg.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		p_center.setBackground(Color.WHITE);
		p_button.setBackground(Color.WHITE);
		bt_apply.setBackground(Color.YELLOW);
		bt_cancel.setBackground(Color.WHITE);
		p_wrapper.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		// 레이아웃 설정
		p_bg.setLayout(new BorderLayout());
		p_center.setLayout(new BorderLayout());
		p_wrapper.setLayout(new BorderLayout());
		setLayout(new BorderLayout());

		// 부착
		p_title.add(lb_title);
		p_font.add(lb_font);
		p_font.add(ch_font);
		p_bg.add(lb_bg, BorderLayout.NORTH);
		p_bg.add(p_bgCenter);
		p_center.add(p_font, BorderLayout.NORTH);
		p_center.add(p_bg);
		p_button.add(bt_apply);
		p_button.add(bt_cancel);

		p_wrapper.add(p_title, BorderLayout.NORTH);
		p_wrapper.add(p_center);
		p_wrapper.add(p_button, BorderLayout.SOUTH);
		add(p_wrapper);

		// 폰트 초이스와 리스너 연결
		ch_font.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				setFont = (String) e.getItem();
				p_font.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
				previewFont(setFont); // 폰트 적용 미리보기
			}
		});

		// 확인 버튼 눌렀을 때, 폰트와 배경 적용
		bt_apply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeFont();// 폰트 변경
				chageBackground();// 배경색 변경
				JOptionPane.showMessageDialog(Settings.this, "적용 완료");
				dispose();
			}
		});

		// 취소 버튼과 리스너 연결
		bt_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnFont(); // 원래 폰트로
				returnBackground();// 원래 배경색으로
				dispose();
			}
		});

		setSize(600, 500);
		setResizable(false);
		setUndecorated(true);
		setVisible(true);
	}

	// 폰트 변경시 미리보기 지원
	public void previewFont(String font) {
		mainFrame.lb_name.setFont(new Font(setFont, Font.BOLD, 16));

		mainFrame.p_search.t_search
				.setFont(new Font(setFont, Font.PLAIN, mainFrame.p_search.t_search.getFont().getSize()));
		member.tree.setFont(new Font(setFont, Font.BOLD, 30));
		for (int i = 0; i < chat.panelList.size(); i++) {
			ChatListPanel panel = chat.panelList.get(i);
			panel.la_name.setFont(new Font(setFont, Font.BOLD, 18));
			panel.la_content.setFont(new Font(setFont, Font.PLAIN, 14));
			panel.la_time.setFont(new Font(setFont, Font.PLAIN, 14));
		}

		lb_title.setFont(new Font(setFont, Font.BOLD, 16));
		lb_font.setFont(new Font(setFont, Font.BOLD, 16));
		lb_bg.setFont(new Font(setFont, Font.BOLD, 18));
		bt_apply.setFont(new Font(setFont, Font.PLAIN, 14));
		bt_cancel.setFont(new Font(setFont, Font.PLAIN, 14));
		ch_font.setFont(new Font(setFont, Font.PLAIN, ch_font.getFont().getSize()));
		flag_font = true;
	}

	// 예시 배경색을 담는 원 생성 함수
	public void setCircle(int i) {
		JPanel p_cwrapper = new JPanel();
		JPanel p_circle = new JPanel() {
			public void paint(Graphics g) {
				g.setColor(c[i]);
				g.fillOval(0, 0, 90, 90);
			}
		};
		p_cwrapper.setBackground(Color.WHITE);
		p_cwrapper.setPreferredSize(new Dimension(110, 120));
		p_circle.setPreferredSize(new Dimension(95, 95));
		p_cwrapper.add(p_circle);
		p_bgCenter.add(p_cwrapper);

		// 원을 눌렀을때 배경색 미리보기 지원
		p_circle.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				color = c[i];
				mainFrame.p_state.setBackground(c[i]);
				mainFrame.p_channel.setBackground(c[i]);
				mainFrame.p_cleft.setBackground(c[i]);
				mainFrame.p_cright.setBackground(c[i]);
				member.tree.setBackground(c[i]);

				chat.setBackground(c[i]);
				for (int a = 0; a < chat.panelList.size(); a++) {
					chat.panelList.get(a).addMouseListener(new MouseAdapter() {
						public void mouseEntered(MouseEvent e) {
							ChatListPanel p = (ChatListPanel) e.getComponent();
							p.p_left.setBackground(c[i]);
							p.p_center.setBackground(c[i]);
							p.p_right.setBackground(c[i]);
							p.p_img.setBackground(c[i]);
							p.p_name.setBackground(c[i]);
							p.p_content.setBackground(c[i]);
							p.setBackground(c[i]);
						}

						public void mouseExited(MouseEvent e) {
							ChatListPanel p = (ChatListPanel) e.getComponent();
							p.p_left.setBackground(Color.WHITE);
							p.p_center.setBackground(Color.WHITE);
							p.p_right.setBackground(Color.WHITE);
							p.p_img.setBackground(Color.WHITE);
							p.p_name.setBackground(Color.WHITE);
							p.p_content.setBackground(Color.WHITE);
							p.setBackground(Color.WHITE);
						}
					});
				}
				p_title.setBackground(c[i]);
				flag_color = true;
			}
		});
	}

	// 폰트 변경 함수
	public void changeFont() {
		if (flag_font) {
			mainFrame.lb_name.setFont(new Font(setFont, Font.BOLD, 16));
			mainFrame.p_search.t_search
					.setFont(new Font(setFont, Font.PLAIN, mainFrame.p_search.t_search.getFont().getSize()));
			member.tree.setFont(new Font(setFont, Font.BOLD, 30));
			for (int i = 0; i < chat.panelList.size(); i++) {
				chat.panelList.get(i).la_name.setFont(new Font(setFont, Font.BOLD, 18));
				chat.panelList.get(i).la_content.setFont(new Font(setFont, Font.PLAIN, 14));
				chat.panelList.get(i).la_time.setFont(new Font(setFont, Font.PLAIN, 14));
			}
			mainFrame.setFontString(setFont);
		} else {
			setFont = mainFrame.getFontString();
		}
	}

	// 배경 변경 함수
	public void chageBackground() {
		if (flag_color) {
			mainFrame.setColor(color);
			mainFrame.p_state.setBackground(color);
			mainFrame.p_channel.setBackground(color);
			mainFrame.p_cleft.setBackground(color);
			mainFrame.p_cright.setBackground(color);
			member.tree.setBackground(color);
			ListCellRenderer render= new ListCellRenderer(mainFrame);
			member.tree.setCellRenderer(render);
			chat.setBackground(color);
		} else {
			color = mainFrame.getColor();
		}
	}

	// 원래 폰트로 돌아가는 함수
	public void returnFont() {
		String f = mainFrame.getFontString();
		mainFrame.lb_name.setFont(new Font(f, Font.BOLD, 16));
		mainFrame.p_search.t_search.setFont(new Font(f, Font.PLAIN, mainFrame.p_search.t_search.getFont().getSize()));
		member.tree.setFont(new Font(f, Font.BOLD, 30));
		for (int i = 0; i < chat.panelList.size(); i++) {
			chat.panelList.get(i).la_name.setFont(new Font(f, Font.BOLD, 18));
			chat.panelList.get(i).la_content.setFont(new Font(f, Font.PLAIN, 14));
			chat.panelList.get(i).la_time.setFont(new Font(f, Font.PLAIN, 14));
		}
	}

	// 원래 배경으로 돌아가는 함수
	public void returnBackground() {
		Color c = mainFrame.getColor();
		mainFrame.p_state.setBackground(c);
		mainFrame.p_channel.setBackground(c);
		mainFrame.p_cleft.setBackground(c);
		mainFrame.p_cright.setBackground(c);
		member.tree.setBackground(c);

		chat.setBackground(c);
		for (int a = 0; a < chat.panelList.size(); a++) {
			chat.panelList.get(a).addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					ChatListPanel p = (ChatListPanel) e.getComponent();
					p.p_left.setBackground(c);
					p.p_center.setBackground(c);
					p.p_right.setBackground(c);
					p.p_img.setBackground(c);
					p.p_name.setBackground(c);
					p.p_content.setBackground(c);
					p.setBackground(c);
				}

				public void mouseExited(MouseEvent e) {
					ChatListPanel p = (ChatListPanel) e.getComponent();
					p.p_left.setBackground(Color.WHITE);
					p.p_center.setBackground(Color.WHITE);
					p.p_right.setBackground(Color.WHITE);
					p.p_img.setBackground(Color.WHITE);
					p.p_name.setBackground(Color.WHITE);
					p.p_content.setBackground(Color.WHITE);
					p.setBackground(Color.WHITE);
				}
			});
		}
	}
}