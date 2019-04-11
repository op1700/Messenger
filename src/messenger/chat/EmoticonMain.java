package messenger.chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import messenger.utils.ImageLoad;

public class EmoticonMain extends JFrame {
   JPanel p_wrapper, p_title, p_north, p_south;
   JPanel p_emoticonTitle, p_emoticon;
   JLabel la_title, la_x;
   JLabel la_emoticonTitle, la_emoticonTitle2;
   Image emoticonTitle_img, emoticonTitle_img2;

   ChatMain chatMain;
   EmoticonList1 emoticonList1;
   EmoticonList2 emoticonList2;
   ImageLoad imageLoad;

   public EmoticonMain(ChatMain chatMain) {
      this.chatMain = chatMain;

      p_wrapper = new JPanel(); // 전체 패널
      p_title = new JPanel(); // 타이틀 패널
      p_north = new JPanel(); // 타이틀과 이모티콘 메뉴를 붙일 패널
      p_south = new JPanel(); // 이모티콘을 붙일 패널
      p_emoticonTitle = new JPanel(); // 이모티콘 메뉴 패널
      p_emoticon = new JPanel(); // 이모티콘 패널

      la_title = new JLabel("    이모티콘", SwingConstants.CENTER);
      la_title.setForeground(Color.WHITE);
      la_x = new JLabel("X");
      la_x.setForeground(Color.WHITE);

      imageLoad = new ImageLoad();
      emoticonTitle_img = imageLoad.getImage("smile.png");
      emoticonTitle_img = emoticonTitle_img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
      la_emoticonTitle = new JLabel();
      la_emoticonTitle.setIcon(new ImageIcon(emoticonTitle_img));
      p_emoticonTitle.add(la_emoticonTitle);

      emoticonTitle_img2 = imageLoad.getImage("emoji/speechBubble1.png");
      emoticonTitle_img2 = emoticonTitle_img2.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
      la_emoticonTitle2 = new JLabel();
      la_emoticonTitle2.setIcon(new ImageIcon(emoticonTitle_img2));
      p_emoticonTitle.add(la_emoticonTitle2);

      emoticonList1 = new EmoticonList1(chatMain);
      emoticonList2 = new EmoticonList2(chatMain);

      // 폰트 설정
      la_title.setFont(new Font(chatMain.mainFrame.getFontString(), Font.BOLD, 14));
      la_x.setFont(new Font(chatMain.mainFrame.getFontString(), Font.BOLD, 15));

      // 사이즈 조절
      p_title.setPreferredSize(new Dimension(300, 30));
      la_title.setPreferredSize(new Dimension(270, 30));
      la_x.setPreferredSize(new Dimension(30, 30));
      p_emoticonTitle.setPreferredSize(new Dimension(300, 40));
      la_emoticonTitle.setPreferredSize(new Dimension(60, 40));
      la_emoticonTitle2.setPreferredSize(new Dimension(40, 40));
      p_emoticon.setPreferredSize(new Dimension(300, 190));
      p_north.setPreferredSize(new Dimension(300, 70));
      p_south.setPreferredSize(new Dimension(300, 200));
      p_wrapper.setPreferredSize(new Dimension(300, 270));

      // 배경색 설정
      p_title.setBackground(chatMain.mainFrame.getColor());
      p_emoticonTitle.setBackground(Color.WHITE);
      p_emoticon.setBackground(Color.WHITE);
      p_south.setBackground(Color.WHITE);
      p_wrapper.setBorder(BorderFactory.createLineBorder(Color.GRAY));

      // 레이아웃 설정
      p_title.setLayout(new BorderLayout());
      p_north.setLayout(new BorderLayout());
      p_wrapper.setLayout(new BorderLayout());

      // 부착
      p_title.add(la_title);
      p_title.add(la_x, BorderLayout.EAST);
      p_north.add(p_title, BorderLayout.NORTH);
      p_north.add(p_emoticonTitle);
      p_south.add(p_emoticon);
      p_emoticon.add(emoticonList1);
      p_emoticon.add(emoticonList2);
      p_wrapper.add(p_north, BorderLayout.NORTH);
      p_wrapper.add(p_south);
      add(p_wrapper);

      // 'x' 클릭시 이모티콘 창을 닫는다
      la_x.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            dispose();
         }
      });

      // 첫번째 이모티콘 라벨과 리스너 연결
      la_emoticonTitle.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            emoticonList1.setVisible(true);
            emoticonList2.setVisible(false);
         }
      });

      // 두번째 이모티콘 라벨과 리스너 연결
      la_emoticonTitle2.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            emoticonList2.setVisible(true);
            emoticonList1.setVisible(false);
         }
      });

      setSize(300, 270);
      setResizable(false);
      setUndecorated(true);
      setVisible(true);
   }

}