
package messenger.regist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Profile extends JFrame {
	JPanel p_con;
	JPanel p_north, p_south;
	JPanel p_txt, p_bt;
	JLabel la_img;
	JTextArea txt;
	JFileChooser chooser;
	JButton bt_choose, bt_accept, bt_exit;
	Image img;
	String fileName;
	MemberRegist memberRegist;
	String subFileName;
	File file;
	Image newImg;
	String path;

	public Profile(MemberRegist memberRegist) {
		super("파일 찾기");
		this.memberRegist = memberRegist;
		// -------------------------------------
		p_con = new JPanel();
		// -------------------------------------
		p_north = new JPanel();
		p_south = new JPanel();
		// -------------------------------------
		la_img = new JLabel();
		p_txt = new JPanel();
		p_bt = new JPanel();
		// -------------------------------------
		txt = new JTextArea();
		bt_choose = new JButton("파일 찾기");
		bt_accept = new JButton("확인");
		bt_exit = new JButton("취소");

		txt.setPreferredSize(new Dimension(300, 100));
		txt.setText("자신의 프로필 사진을 지정해주세요\n(확장자명이 jpeg, jpg인 파일만 지원합니다");
		txt.setEditable(false);

		la_img.setPreferredSize(new Dimension(100, 100));
		la_img.setBackground(Color.BLACK);
		// -------------------------------------
		p_txt.setPreferredSize(new Dimension(300, 100));
		p_txt.setLayout(new BorderLayout());
		p_txt.add(txt);
		// -------------------------------------
		p_north.setPreferredSize(new Dimension(400, 100));
		p_north.setLayout(new BorderLayout());
		p_north.add(la_img, BorderLayout.WEST);
		p_north.add(p_txt);
		// -------------------------------------
		p_south.setPreferredSize(new Dimension(400, 50));
		p_south.setBackground(Color.ORANGE);
		p_south.add(bt_choose);
		p_south.add(bt_accept);
		p_south.add(bt_exit);
		// -------------------------------------
		p_con.setPreferredSize(new Dimension(400, 180));
		p_con.setLayout(new BorderLayout());
		p_con.add(p_north, BorderLayout.NORTH);
		p_con.add(p_south);

		add(p_con);

		bt_choose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooser = new JFileChooser();
				chooser.showOpenDialog(p_con);
				file = chooser.getSelectedFile();
				path = file.getAbsolutePath();

				int setCount = path.indexOf("dog");
				subFileName = path.substring(setCount + 4);
				ImageIcon icon = new ImageIcon(path);
				img = icon.getImage();
				newImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
				la_img.setIcon(new ImageIcon(newImg));
			}
		});

		bt_accept.addActionListener(new ActionListener() {// 확인버튼
			public void actionPerformed(ActionEvent e) {
				// 프로필 사진을 전송
				if (path != null) {
					memberRegist.imgPath = subFileName;
					System.out.println(subFileName);
					memberRegist.getProfileImage(new ImageIcon(newImg));
					dispose();
				} else {
					JOptionPane.showMessageDialog(memberRegist, "사진파일을 선택해주세요");
				}
			}
		});
		bt_exit.addActionListener(new ActionListener() {// 취소버튼
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

		setVisible(true);
		setSize(400, 180);
	}
	/*
	 * public void sendImage(Image img) { img }
	 */
}