package messenger.chatlist;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import messenger.mainframe.MainFrame;
import messenger.utils.ImageLoad;

public class ChatListPanel extends JPanel {
   public JPanel p_left, p_center, p_right;
   public JPanel p_img, p_name, p_content;
   public JLabel la_img, la_name, la_count, la_content, la_time;
   Image img, img2, img3, img4;
   ImageLoad imageLoad;
   MainFrame mainFrame;
   public String room_code;
   public String roomName;

   public ChatListPanel(MainFrame mainFrame, String roomName, String imgOne, String currentLog, String currentTime,
         String room_code) {
      this.mainFrame = mainFrame;
      this.room_code = room_code;
      this.roomName = roomName;
      
      setLayout(new BorderLayout());
      imageLoad = new ImageLoad();
      img = imageLoad.getImage("dog/" + imgOne);
      img = img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);

      BufferedImage circleBuffer = new BufferedImage(60, 60, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2 = circleBuffer.createGraphics();
      g2.setComposite(AlphaComposite.Src);// 불투명 알파값 오브젝트(뭔 말인지 어려워서 모르겠음) 이게 시작이고 밑에꺼가 전송처 인듯?
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);// 이미지 계단현상 방지
      g2.fill(new RoundRectangle2D.Float(0, 0, 60, 60, 50, 50));
      g2.setComposite(AlphaComposite.SrcAtop);
      g2.drawImage(img, 0, 0, 60, 60, null);
      g2.dispose();

      setPanelModel(roomName, circleBuffer, currentLog, currentTime);

   }

   public ChatListPanel(MainFrame mainFrame, String roomName, String imgOne, String imgTwo, int count,
         String currentLog, String currentTime, String room_code) {
      this.mainFrame = mainFrame;
      this.room_code = room_code;
      this.roomName = roomName;

      setLayout(new BorderLayout());
      imageLoad = new ImageLoad();
      img = imageLoad.getImage("dog/" + imgOne);
      img = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
      img2 = imageLoad.getImage("dog/" + imgTwo);
      img2 = img2.getScaledInstance(30, 30, Image.SCALE_SMOOTH);

      Image icon1 = createImg(img, 30);
      Image icon2 = createImg(img2, 30);

      BufferedImage circleBuffer = new BufferedImage(60, 60, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2 = circleBuffer.createGraphics();
      g2.setComposite(AlphaComposite.Src);// 불투명 알파값 오브젝트(뭔 말인지 어려워서 모르겠음) 이게 시작이고 밑에꺼가 전송처 인듯?
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);// 이미지 계단현상 방지
      g2.fill(new RoundRectangle2D.Float(0, 0, 60, 60, 50, 50));
      g2.setComposite(AlphaComposite.SrcAtop);
      g2.drawImage(icon1, 6, 6, 30, 30, null);
      g2.drawImage(icon2, 24, 24, 30, 30, null);
      g2.dispose();

      setPanelModel(roomName, circleBuffer, currentLog, currentTime, count);
   }

   public ChatListPanel(MainFrame mainFrame, String roomName, String imgOne, String imgTwo, String imgThree, int count,
         String currentLog, String currentTime, String room_code) {
      this.mainFrame = mainFrame;
      this.room_code = room_code;
      this.roomName = roomName;

      setLayout(new BorderLayout());
      imageLoad = new ImageLoad();
      img = imageLoad.getImage("dog/" + imgOne);
      img = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
      img2 = imageLoad.getImage("dog/" + imgTwo);
      img2 = img2.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
      img3 = imageLoad.getImage("dog/" + imgThree);
      img3 = img3.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
      Image icon1 = createImg(img, 30);
      Image icon2 = createImg(img2, 30);
      Image icon3 = createImg(img3, 30);

      BufferedImage circleBuffer = new BufferedImage(60, 60, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2 = circleBuffer.createGraphics();
      g2.setComposite(AlphaComposite.Src);// 불투명 알파값 오브젝트(뭔 말인지 어려워서 모르겠음) 이게 시작이고 밑에꺼가 전송처 인듯?
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);// 이미지 계단현상 방지
      g2.fill(new RoundRectangle2D.Float(0, 0, 60, 60, 50, 50));
      g2.setComposite(AlphaComposite.SrcAtop);
      g2.drawImage(icon1, 15, 0, 30, 30, null);
      g2.drawImage(icon2, 2, 25, 30, 30, null);
      g2.drawImage(icon3, 28, 25, 30, 30, null);
      g2.dispose();

      setPanelModel(roomName, circleBuffer, currentLog, currentTime, count);
   }

   public ChatListPanel(MainFrame mainFrame, String roomName, String imgOne, String imgTwo, String imgThree,
         String imgFour, int count, String currentLog, String currentTime, String room_code) {
      this.mainFrame = mainFrame;
      this.room_code = room_code;
      this.roomName = roomName;

      setLayout(new BorderLayout());
      imageLoad = new ImageLoad();
      img = imageLoad.getImage("dog/" + imgOne);
      img = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
      img2 = imageLoad.getImage("dog/" + imgTwo);
      img2 = img2.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
      img3 = imageLoad.getImage("dog/" + imgThree);
      img3 = img3.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
      img4 = imageLoad.getImage("dog/" + imgFour);
      img4 = img4.getScaledInstance(30, 30, Image.SCALE_SMOOTH);

      Image icon1 = createImg(img, 30);
      Image icon2 = createImg(img2, 30);
      Image icon3 = createImg(img3, 30);
      Image icon4 = createImg(img4, 30);

      BufferedImage circleBuffer = new BufferedImage(60, 60, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2 = circleBuffer.createGraphics();
      g2.setComposite(AlphaComposite.Src);// 불투명 알파값 오브젝트(뭔 말인지 어려워서 모르겠음) 이게 시작이고 밑에꺼가 전송처 인듯?
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);// 이미지 계단현상 방지
      g2.fill(new RoundRectangle2D.Float(0, 0, 60, 60, 50, 50));
      g2.setComposite(AlphaComposite.SrcAtop);
      g2.drawImage(icon1, 0, 0, 30, 30, null);
      g2.drawImage(icon2, 30, 0, 30, 30, null);
      g2.drawImage(icon3, 0, 30, 30, 30, null);
      g2.drawImage(icon4, 30, 30, 30, 30, null);
      g2.dispose();

      setPanelModel(roomName, circleBuffer, currentLog, currentTime, count);
   }

   public BufferedImage createImg(Image img, int size) {
      BufferedImage circleBuffer = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2 = circleBuffer.createGraphics();
      g2.setComposite(AlphaComposite.Src);// 불투명 알파값 오브젝트(뭔 말인지 어려워서 모르겠음) 이게 시작이고 밑에꺼가 전송처 인듯?
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);// 이미지 계단현상 방지
      g2.fill(new RoundRectangle2D.Float(0, 0, size, size, size / 2, size / 2));
      g2.setComposite(AlphaComposite.SrcAtop);
      g2.drawImage(img, 0, 0, size, size, null);
      g2.dispose();
      return circleBuffer;
   }

   public void setPanelModel(String roomName, BufferedImage circleBuffer, String currentLog, String currentTime) {
      p_left = new JPanel();
      p_center = new JPanel();
      p_right = new JPanel();
      p_img = new JPanel();
      p_name = new JPanel();
      p_content = new JPanel();
      la_img = new JLabel();
      la_name = new JLabel(roomName, SwingConstants.LEFT);
      la_content = new JLabel(currentLog, SwingConstants.LEFT);

      // 시간을 시와 분으로 가져오기
      String hour_str = currentTime.substring(8, 10);
      String min = currentTime.substring(10, 12);
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

      String font = mainFrame.getFontString();
      la_img.setIcon(new ImageIcon(circleBuffer));
      la_name.setFont(new Font(font, Font.BOLD, 18));
      la_name.setPreferredSize(new Dimension(250, 20));
      la_content.setFont(new Font(font, Font.PLAIN, 14));
      la_content.setPreferredSize(new Dimension(250, 20));
      la_time.setFont(new Font(font, Font.PLAIN, 14));
      la_time.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 40));

      p_left.setBackground(Color.WHITE);
      p_center.setBackground(Color.WHITE);
      p_right.setBackground(Color.WHITE);
      p_img.setBackground(Color.WHITE);
      p_name.setBackground(Color.WHITE);
      p_content.setBackground(Color.WHITE);

      p_img.add(la_img);
      p_name.add(la_name);
      p_content.add(la_content);

      p_img.setPreferredSize(new Dimension(60, 70));
      la_img.setBounds(0, 0, 60, 60);

      p_img.setLayout(new BorderLayout());
      p_center.setLayout(new BorderLayout());
      p_left.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
      p_center.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

      p_center.add(p_name, BorderLayout.NORTH);
      p_center.add(p_content, BorderLayout.SOUTH);
      p_left.add(p_img);
      p_left.add(p_center);
      p_right.add(la_time);

      add(p_left, BorderLayout.WEST);
      add(p_right, BorderLayout.EAST);

      addMouseListener(new MouseAdapter() {
         public void mouseEntered(MouseEvent e) {
            ChatListPanel p = (ChatListPanel) e.getComponent();
            Color c = mainFrame.getColor();
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

      setPreferredSize(new Dimension(500, 70));
      setBackground(Color.WHITE);
   }

   public void setPanelModel(String roomName, BufferedImage circleBuffer, String currentLog, String currentTime,
         int count) {
      p_left = new JPanel();
      p_center = new JPanel();
      p_right = new JPanel();
      p_img = new JPanel();
      p_name = new JPanel();
      p_content = new JPanel();
      la_img = new JLabel();
      la_name = new JLabel(roomName, SwingConstants.LEFT);
      la_count = new JLabel("" + count);
      la_content = new JLabel(currentLog, SwingConstants.LEFT);

      // 시간을 시와 분으로 가져오기
      String hour_str = currentTime.substring(8, 10);
      String min = currentTime.substring(10, 12);
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

      String font = mainFrame.getFontString();
      la_img.setIcon(new ImageIcon(circleBuffer));
      int width_p = p_name.getWidth();
      la_name.setFont(new Font(font, Font.BOLD, 18));
      Dimension size = la_name.getPreferredSize();
      la_count.setPreferredSize(new Dimension(250 - size.width, 20));
      la_count.setFont(new Font(font, Font.BOLD, 14));
      la_count.setForeground(Color.GRAY);
      la_content.setFont(new Font(font, Font.PLAIN, 14));
      la_content.setPreferredSize(new Dimension(250, 20));
      la_time.setFont(new Font(font, Font.PLAIN, 14));
      la_time.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 40));

      p_left.setBackground(Color.WHITE);
      p_center.setBackground(Color.WHITE);
      p_right.setBackground(Color.WHITE);
      p_img.setBackground(Color.WHITE);
      p_name.setBackground(Color.WHITE);
      p_content.setBackground(Color.WHITE);

      p_img.add(la_img);
      p_name.add(la_name);
      p_name.add(la_count);
      p_content.add(la_content);

      p_img.setPreferredSize(new Dimension(60, 70));
      la_img.setBounds(0, 0, 60, 60);

      p_img.setLayout(new BorderLayout());
      p_center.setLayout(new BorderLayout());
      p_left.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
      p_center.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

      p_center.add(p_name, BorderLayout.NORTH);
      p_center.add(p_content, BorderLayout.SOUTH);
      p_left.add(p_img);
      p_left.add(p_center);
      p_right.add(la_time);

      add(p_left, BorderLayout.WEST);
      add(p_right, BorderLayout.EAST);

      addMouseListener(new MouseAdapter() {
         public void mouseEntered(MouseEvent e) {
            ChatListPanel p = (ChatListPanel) e.getComponent();
            Color c = mainFrame.getColor();
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

      setPreferredSize(new Dimension(500, 70));
      setBackground(Color.WHITE);
   }
}