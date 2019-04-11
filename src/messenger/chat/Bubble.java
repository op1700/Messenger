package messenger.chat;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import messenger.mainframe.MainFrame;
import messenger.utils.ImageLoad;

public class Bubble extends JPanel {
   JPanel p_bubble, p_img, p_center, p_chat, p_time, p_empty, p_line;
   JLabel la_img, la_name, la_chat, la_time;
   Image img;
   ImageLoad imageLoad;
   String str;
   int num = 0;

   public Bubble(String img, String name, String chat, String time, MainFrame mainFrame, ChatMain chatMain) {
      p_bubble = new JPanel(); // ��ǳ�� �ϳ��� ��� �г�
      p_img = new JPanel(); // �����ʻ��� �г�
      p_center = new JPanel(); // �̸��� ä�ó����� ��� �г�
      p_chat = new JPanel(); // ä�ó��� �г�
      p_time = new JPanel(); // �ð� �г�
      p_empty = new JPanel(); // ȭ�� ũ�⸦ ���߱����� �� �г�
      p_line = new JPanel();  //ä�ð��� ������ �ֱ����� �г�

      la_img = new JLabel();
      ImageLoad imageLoad = new ImageLoad();
      Image image = imageLoad.getImage("dog/" + img);
      image = image.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
      BufferedImage circleBuffer = new BufferedImage(35, 35, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2 = circleBuffer.createGraphics();
      g2.setComposite(AlphaComposite.Src);
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.fill(new RoundRectangle2D.Float(0, 0, 35, 35, 30, 30));
      g2.setComposite(AlphaComposite.SrcAtop);
      g2.drawImage(image, 0, 0, 35, 35, null);
      g2.dispose();
      la_img.setIcon(new ImageIcon(circleBuffer));

      la_name = new JLabel(name);

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
      la_name.setFont(new Font(mainFrame.getFontString(), Font.PLAIN, 13));
      la_chat.setFont(new Font(mainFrame.getFontString(), Font.PLAIN, 13));
      la_time.setFont(new Font(mainFrame.getFontString(), Font.PLAIN, 12));

      // ������ ����
      p_img.setPreferredSize(new Dimension(50, 50));
      p_img.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
      Dimension size = la_chat.getPreferredSize();
      la_name.setPreferredSize(new Dimension(size.width + 15, 13));
      p_chat.setPreferredSize(new Dimension(size.width + 15, 28 + ((19 - (num)) * num)));
      p_center.setPreferredSize(new Dimension(size.width + 15, 47 + ((19 - (num)) * num)));
      la_time.setPreferredSize(new Dimension(60, 20));
      p_time.setPreferredSize(new Dimension(60, 50));
      p_time.setBorder(BorderFactory.createEmptyBorder(27 + (18 * num), 5, 0, 0));
      p_bubble.setPreferredSize(new Dimension(size.width + 130, 65 + (18 * num)));
      Dimension mySize = p_bubble.getPreferredSize();
      p_empty.setPreferredSize(new Dimension(480 - mySize.width, mySize.height));
      this.setPreferredSize(new Dimension(490, mySize.height));
      p_line.setPreferredSize(new Dimension(500,10));

      // ���� ����
      p_img.setOpaque(false);
      p_chat.setBackground(Color.WHITE);
      p_chat.setBorder(BorderFactory.createLineBorder(Color.GRAY));
      p_center.setOpaque(false);
      p_time.setOpaque(false);
      p_empty.setOpaque(false);
      p_line.setOpaque(false);
      p_bubble.setOpaque(false);
      this.setOpaque(false);

      // ���̾ƿ� ����
      p_bubble.setLayout(new BorderLayout());
      setLayout(new BorderLayout());

      // ����
      p_img.add(la_img);
      p_chat.add(la_chat);
      p_center.add(la_name);
      p_center.add(p_chat);
      p_time.add(la_time);
      p_bubble.add(p_img, BorderLayout.WEST);
      p_bubble.add(p_center);
      p_bubble.add(p_time, BorderLayout.EAST);

      add(p_bubble, BorderLayout.WEST);
      add(p_empty);
      chatMain.area.insertComponent(p_line);
      chatMain.area.insertComponent(this);
      chatMain.area.insertComponent(p_line);
   }
}