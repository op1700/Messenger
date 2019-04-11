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
      p_bubble = new JPanel(); // ��ǳ�� �ϳ��� ��� �г�
      p_center = new JPanel(); // ������� ä�ó����� ��� �г�
      p_chat = new JPanel(); // ä�ó��� �г�
      p_time = new JPanel(); // �ð� �г�
      p_empty = new JPanel(); // ȭ�� ũ�⸦ ���߱����� �� �г�
      p_line = new JPanel();  //ä�ð��� ������ �ֱ����� �г�

      // 14���ڸ��� ����ǵ���
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

      // �ð��� �ÿ� ������ ��������
      String hour_str = time.substring(8, 10);
      String min = time.substring(10, 12);
      int hour_int = Integer.parseInt(hour_str);
      if (hour_int < 12) {
         if (hour_int == 0) {
            hour_str = "���� 12:" + min;
         } else {
            hour_str = "���� " + Integer.toString(hour_int) + ":" + min;
         }
      } else {
         if (hour_int == 12) {
            hour_str = "���� 12:" + min;
         } else {
            hour_str = "���� " + Integer.toString(hour_int - 12) + ":" + min;
         }
      }
      la_time = new JLabel(hour_str);

      // ��Ʈ ����
      la_chat.setFont(new Font(mainFrame.getFontString(), Font.PLAIN, 13));
      la_time.setFont(new Font(mainFrame.getFontString(), Font.PLAIN, 12));

      // ������ ����
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

      // ���� ����
      p_chat.setBackground(Color.YELLOW);
      p_chat.setBorder(BorderFactory.createLineBorder(Color.GRAY));
      p_center.setOpaque(false);
      p_time.setOpaque(false);
      p_bubble.setOpaque(false);
      p_empty.setOpaque(false);
      this.setOpaque(false);
      p_line.setOpaque(false);

      // ���̾ƿ� ����
      p_bubble.setLayout(new BorderLayout());
      setLayout(new BorderLayout());

      // ����
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
         p_bubble = new JPanel(); // ��ǳ�� �ϳ��� ��� �г�
         p_center = new JPanel(); // ������� ä�ó����� ��� �г�
         p_chat = new JPanel(); // ä�ó��� �г�
         p_time = new JPanel(); // �ð� �г�
         p_empty = new JPanel(); // ȭ�� ũ�⸦ ���߱����� �� �г�
         p_line = new JPanel(); // ä�ð��� ������ �ֱ����� �г�

         la_chat = new JLabel();
         img = img.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
         la_chat.setIcon(new ImageIcon(img));

         // �ð��� �ÿ� ������ ��������
         String hour_str = time.substring(8, 10);
         String min = time.substring(10, 12);
         int hour_int = Integer.parseInt(hour_str);
         if (hour_int < 12) {
            if (hour_int == 0) {
               hour_str = "���� 12:" + min;
            } else {
               hour_str = "���� " + Integer.toString(hour_int) + ":" + min;
            }
         } else {
            if (hour_int == 12) {
               hour_str = "���� 12:" + min;
            } else {
               hour_str = "���� " + Integer.toString(hour_int - 12) + ":" + min;
            }
         }
         la_time = new JLabel(hour_str);

         // ��Ʈ ����
         la_time.setFont(new Font(mainFrame.getFontString(), Font.PLAIN, 12));

         // ������ ����
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

         // ���� ����
         p_chat.setBackground(Color.YELLOW);
         p_chat.setBorder(BorderFactory.createLineBorder(Color.GRAY));
         p_center.setOpaque(false);
         p_time.setOpaque(false);
         p_bubble.setOpaque(false);
         p_empty.setOpaque(false);
         this.setOpaque(false);
         p_line.setOpaque(false);

         // ���̾ƿ� ����
         p_bubble.setLayout(new BorderLayout());
         setLayout(new BorderLayout());

         // ����
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