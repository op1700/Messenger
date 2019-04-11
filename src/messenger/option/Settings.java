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
	String[] font = { "���� ���", "����", "�ü�", "����", "����", "�������", "���� ���", "���Ļ�浸��" };
	Color[] c = { new Color(225, 225, 128), new Color(128, 225, 128), new Color(128, 225, 225),
			new Color(255, 128, 128), new Color(225, 171, 87), new Color(192, 192, 192), new Color(140, 140, 225),
			new Color(193, 132, 255), new Color(225, 225, 0), new Color(147, 119, 94) };
	MainFrame mainFrame;
	MemberListMain member;
	ChatListMain chat;
	ChannelOption channelOption;

	String setFont; // ���� ��Ʈ�� ��� ����
	Color color; // ���� ������ ��� ����
	boolean flag_font = false; // ��Ʈ ���� ����
	boolean flag_color = false; // ���� ���� ����
	boolean flag_visible = false; // ����â visible

	public Settings(MainFrame mainFrame, MemberListMain member, ChatListMain chat, ChannelOption channelOption) {
		this.mainFrame = mainFrame;
		this.member = member;
		this.chat = chat;
		this.channelOption = channelOption;

		p_wrapper = new JPanel(); // ��ü �г�
		p_title = new JPanel(); // Ÿ��Ʋ �г�
		p_center = new JPanel(); // ��Ʈ�� ����� ���� �г�
		p_font = new JPanel(); // ��Ʈ �г�
		p_bg = new JPanel(); // ��� �г�
		p_bgCenter = new JPanel(); // ���� ���ø� ���� �г�
		p_button = new JPanel(); // Ȯ��,��ҹ�ư�� ���� �г�

		lb_title = new JLabel("����");
		lb_title.setForeground(Color.WHITE);

		lb_font = new JLabel("��Ʈ    ");
		ch_font = new Choice();
		for (int i = 0; i < font.length; i++) {
			ch_font.add(font[i]);
		}
		lb_bg = new JLabel("���", SwingConstants.CENTER);
		for (int i = 0; i < c.length; i++) {
			setCircle(i); // �������
		}

		bt_apply = new JButton("Ȯ��");
		bt_cancel = new JButton("���");

		// ��Ʈ ����
		lb_title.setFont(new Font(mainFrame.getFontString(), Font.BOLD, 16));
		lb_font.setFont(new Font(mainFrame.getFontString(), Font.BOLD, 16));
		lb_bg.setFont(new Font(mainFrame.getFontString(), Font.BOLD, 18));
		bt_apply.setFont(new Font(mainFrame.getFontString(), Font.PLAIN, 14));
		bt_cancel.setFont(new Font(mainFrame.getFontString(), Font.PLAIN, 14));

		// ������ ����
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

		// ���� ����
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

		// ���̾ƿ� ����
		p_bg.setLayout(new BorderLayout());
		p_center.setLayout(new BorderLayout());
		p_wrapper.setLayout(new BorderLayout());
		setLayout(new BorderLayout());

		// ����
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

		// ��Ʈ ���̽��� ������ ����
		ch_font.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				setFont = (String) e.getItem();
				p_font.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
				previewFont(setFont); // ��Ʈ ���� �̸�����
			}
		});

		// Ȯ�� ��ư ������ ��, ��Ʈ�� ��� ����
		bt_apply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeFont();// ��Ʈ ����
				chageBackground();// ���� ����
				JOptionPane.showMessageDialog(Settings.this, "���� �Ϸ�");
				dispose();
			}
		});

		// ��� ��ư�� ������ ����
		bt_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnFont(); // ���� ��Ʈ��
				returnBackground();// ���� ��������
				dispose();
			}
		});

		setSize(600, 500);
		setResizable(false);
		setUndecorated(true);
		setVisible(true);
	}

	// ��Ʈ ����� �̸����� ����
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

	// ���� ������ ��� �� ���� �Լ�
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

		// ���� �������� ���� �̸����� ����
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

	// ��Ʈ ���� �Լ�
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

	// ��� ���� �Լ�
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

	// ���� ��Ʈ�� ���ư��� �Լ�
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

	// ���� ������� ���ư��� �Լ�
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