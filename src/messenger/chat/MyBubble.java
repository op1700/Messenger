package messenger.chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import messenger.mainframe.MainFrame;

public class MyBubble extends JPanel {
   JPanel p_bubble, p_center, p_chat, p_time, p_empty, p_line;
   JLabel la_chat, la_time;
   String str;
   int num = 0;

   public MyBubble(String chat, String time, MainFrame mainFrame, ChatMain chatMain) {
      p_bubble = new JPanel(); // 말풍선 하나를 담는 패널
      p_center = new JPanel(); // 윗공백과 채팅내용을 담는 패널
      p_chat = new JPanel(); // 채팅내용 패널
      p_time = new JPanel(); // 시간 패널
      p_empty = new JPanel(); // 화면 크기를 맞추기위한 빈 패널
      p_line = new JPanel();  //채팅간의 여백을 주기위한 패널

      // 14글자마다 개행되도록
      num = (chat.length() - 1) / 14;
      if (num > 0) {
         str = "<html>";
         for (int i = 0; i < num; i++) {
            str += chat.substring(14 * i, 14 * (i + 1)) + "<br>";
         }
         str += chat.substring(14 * num);
         str += "</html>";
      } else {
         str = chat;
      }
      la_chat = new JLabel(str);

      // 시간을 시와 분으로 가져오기
      String hour_str = time.substring(8, 10);
      String min = time.substring(10, 12);
      int hour_int = Integer.parseInt(hour_str);
      if (hour_int < 12) {
         if (hour_int == 0) {
            hour_str = "오전 12:" + min;
         } else {
            hour_str = "오전 " + Integer.toString(hour_int) + ":" + min;
         }
      } else {
         if (hour_int == 12) {
            hour_str = "오후 12:" + min;
         } else {
            hour_str = "오후 " + Integer.toString(hour_int - 12) + ":" + min;
         }
      }
      la_time = new JLabel(hour_str);

      // 폰트 설정
      la_chat.setFont(new Font(mainFrame.getFontString(), Font.PLAIN, 13));
      la_time.setFont(new Font(mainFrame.getFontString(), Font.PLAIN, 12));

      // 사이즈 조절
      Dimension size = la_chat.getPreferredSize();
      p_chat.setPreferredSize(new Dimension(size.width + 15, 28 + ((19 - (num)) * num)));
      p_center.setPreferredSize(new Dimension(size.width + 15, 28 + ((19 - (num)) * num)));
      la_time.setPreferredSize(new Dimension(60, 20));
      p_time.setPreferredSize(new Dimension(60, 25));
      p_time.setBorder(BorderFactory.createEmptyBorder(10 + (15 * num), 0, 0, 0));
      p_bubble.setPreferredSize(new Dimension(size.width + 80, 50 + (18 * num)));
      Dimension mySize = p_bubble.getPreferredSize();
      p_empty.setPreferredSize(new Dimension(470 - mySize.width, mySize.height));
      this.setPreferredSize(new Dimension(480, mySize.height));
      p_line.setPreferredSize(new Dimension(500,10));

      // 배경색 설정
      p_chat.setBackground(Color.YELLOW);
      p_chat.setBorder(BorderFactory.createLineBorder(Color.GRAY));
      p_center.setOpaque(false);
      p_time.setOpaque(false);
      p_bubble.setOpaque(false);
      p_empty.setOpaque(false);
      this.setOpaque(false);
      p_line.setOpaque(false);

      // 레이아웃 설정
      p_bubble.setLayout(new BorderLayout());
      setLayout(new BorderLayout());

      // 부착
      p_chat.add(la_chat);
      p_time.add(la_time);
      p_center.add(p_chat);
      p_bubble.add(p_time);
      p_bubble.add(p_center, BorderLayout.EAST);

      add(p_empty);
      add(p_bubble, BorderLayout.EAST);
      chatMain.area.insertComponent(p_line);
      chatMain.area.insertComponent(this);
      chatMain.area.insertComponent(p_line);
   }

   public MyBubble(Image img, String time, MainFrame mainFrame, ChatMain chatMain) {
         p_bubble = new JPanel(); // 말풍선 하나를 담는 패널
         p_center = new JPanel(); // 윗공백과 채팅내용을 담는 패널
         p_chat = new JPanel(); // 채팅내용 패널
         p_time = new JPanel(); // 시간 패널
         p_empty = new JPanel(); // 화면 크기를 맞추기위한 빈 패널
         p_line = new JPanel(); // 채팅간의 여백을 주기위한 패널

         la_chat = new JLabel();
         img = img.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
         la_chat.setIcon(new ImageIcon(img));

         // 시간을 시와 분으로 가져오기
         String hour_str = time.substring(8, 10);
         String min = time.substring(10, 12);
         int hour_int = Integer.parseInt(hour_str);
         if (hour_int < 12) {
            if (hour_int == 0) {
               hour_str = "오전 12:" + min;
            } else {
               hour_str = "오전 " + Integer.toString(hour_int) + ":" + min;
            }
         } else {
            if (hour_int == 12) {
               hour_str = "오후 12:" + min;
            } else {
               hour_str = "오후 " + Integer.toString(hour_int - 12) + ":" + min;
            }
         }
         la_time = new JLabel(hour_str);

         // 폰트 설정
         la_time.setFont(new Font(mainFrame.getFontString(), Font.PLAIN, 12));

         // 사이즈 조절
         p_chat.setPreferredSize(new Dimension(45,45));
         p_center.setPreferredSize(new Dimension(45,45));
         la_time.setPreferredSize(new Dimension(60, 20));
         p_time.setPreferredSize(new Dimension(60, 25));
         p_time.setBorder(BorderFactory.createEmptyBorder(28, 5, 0, 0));
         p_bubble.setPreferredSize(new Dimension(115, 50));
         Dimension mySize = p_bubble.getPreferredSize();
         p_empty.setPreferredSize(new Dimension(470 - mySize.width, mySize.height));
         this.setPreferredSize(new Dimension(480, mySize.height));
         p_line.setPreferredSize(new Dimension(500, 10));

         // 배경색 설정
         p_chat.setBackground(Color.YELLOW);
         p_chat.setBorder(BorderFactory.createLineBorder(Color.GRAY));
         p_center.setOpaque(false);
         p_time.setOpaque(false);
         p_bubble.setOpaque(false);
         p_empty.setOpaque(false);
         this.setOpaque(false);
         p_line.setOpaque(false);

         // 레이아웃 설정
         p_bubble.setLayout(new BorderLayout());
         setLayout(new BorderLayout());

         // 부착
         p_chat.add(la_chat);
         p_time.add(la_time);
         p_center.add(p_chat);
         p_bubble.add(p_time);
         p_bubble.add(p_center, BorderLayout.EAST);

         add(p_empty);
         add(p_bubble, BorderLayout.EAST);
         chatMain.area.insertComponent(p_line);
         chatMain.area.insertComponent(this);
         chatMain.area.insertComponent(p_line);
      }
}