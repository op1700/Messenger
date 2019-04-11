package messenger.chat;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import messenger.utils.ImageLoad;

public class EmoticonList2 extends JPanel {
   JLabel la_north1, la_north2, la_north3, la_north4;
   JLabel la_center1, la_center2, la_center3, la_center4;
   JLabel la_south1, la_south2, la_south3, la_south4;
   ImageIcon[] iconList = new ImageIcon[12];
   ImageIcon[] iconList2 = new ImageIcon[12];
   Image emo_img, emo_img2;
   ChatMain chatMain;
   EmoticonMain emoticonMain;
   ImageLoad imageLoad;

   public EmoticonList2(ChatMain chatMain) {
      this.chatMain = chatMain;

      imageLoad = new ImageLoad();
      for (int i = 0; i < iconList.length; i++) {
         emo_img = imageLoad.getImage("emoji/emote_" + (i + 1) + ".png");
         emo_img = emo_img.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // 선택창
         emo_img2 = emo_img.getScaledInstance(20, 20, Image.SCALE_SMOOTH); // 입력창들어가는거
         iconList[i] = new ImageIcon(emo_img);
         iconList2[i] = new ImageIcon(emo_img2);
      }

      la_north1 = new JLabel(iconList[0]);
      la_north2 = new JLabel(iconList[1]);
      la_north3 = new JLabel(iconList[2]);
      la_north4 = new JLabel(iconList[3]);
      la_center1 = new JLabel(iconList[4]);
      la_center2 = new JLabel(iconList[5]);
      la_center3 = new JLabel(iconList[6]);
      la_center4 = new JLabel(iconList[7]);
      la_south1 = new JLabel(iconList[8]);
      la_south2 = new JLabel(iconList[9]);
      la_south3 = new JLabel(iconList[10]);
      la_south4 = new JLabel(iconList[11]);
      // 아이콘에 이름 붙이기
      la_north1.setName("#icon2_1");
      la_north2.setName("#icon2_2");
      la_north3.setName("#icon2_3");
      la_north4.setName("#icon2_4");
      la_center1.setName("#icon2_5");
      la_center2.setName("#icon2_6");
      la_center3.setName("#icon2_7");
      la_center4.setName("#icon2_8");
      la_south1.setName("#icon2_9");
      la_south2.setName("#icon2_10");
      la_south3.setName("#icon2_11");
      la_south4.setName("#icon2_12");

      // 사이즈 조절
      Dimension d = new Dimension(70, 55);
      la_north1.setPreferredSize(d);
      la_north2.setPreferredSize(d);
      la_north3.setPreferredSize(d);
      la_north4.setPreferredSize(d);
      la_center1.setPreferredSize(d);
      la_center2.setPreferredSize(d);
      la_center3.setPreferredSize(d);
      la_center4.setPreferredSize(d);
      la_south1.setPreferredSize(d);
      la_south2.setPreferredSize(d);
      la_south3.setPreferredSize(d);
      la_south4.setPreferredSize(d);

      // 부착
      add(la_north1);
      add(la_north2);
      add(la_north3);
      add(la_north4);
      add(la_center1);
      add(la_center2);
      add(la_center3);
      add(la_center4);
      add(la_south1);
      add(la_south2);
      add(la_south3);
      add(la_south4);

      // 각각의 이모티콘 클릭시 채팅입력칸에 부착
      la_north1.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            // chatMain.area_input.insertComponent(new JLabel(iconList2[0]));
            // chatMain.sendIcon(la_north1.getName());
            Image img = iconList[0].getImage();
            String curTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            MyBubble my = new MyBubble(img, curTime, chatMain.mainFrame, chatMain);
            chatMain.setEndline();
            chatMain.area.appendLine(my);
         }
      });
      la_north2.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            // chatMain.area_input.insertComponent(new JLabel(iconList2[1]));
            // chatMain.sendIcon(la_north2.getName());
            Image img = iconList[1].getImage();
            String curTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            MyBubble my = new MyBubble(img, curTime, chatMain.mainFrame, chatMain);
            chatMain.setEndline();
            chatMain.area.appendLine(my);
         }
      });
      la_north3.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            // chatMain.area_input.insertComponent(new JLabel(iconList2[2]));
            // chatMain.sendIcon(la_north3.getName());
            Image img = iconList[2].getImage();
            String curTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            MyBubble my = new MyBubble(img, curTime, chatMain.mainFrame, chatMain);
            chatMain.setEndline();
            chatMain.area.appendLine(my);
         }
      });
      la_north4.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            // chatMain.area_input.insertComponent(new JLabel(iconList2[3]));
            // chatMain.sendIcon(la_north4.getName());
            Image img = iconList[3].getImage();
            String curTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            MyBubble my = new MyBubble(img, curTime, chatMain.mainFrame, chatMain);
            chatMain.setEndline();
            chatMain.area.appendLine(my);
         }
      });
      la_center1.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            // chatMain.area_input.insertComponent(new JLabel(iconList2[4]));
            // chatMain.sendIcon(la_center1.getName());
            Image img = iconList[4].getImage();
            String curTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            MyBubble my = new MyBubble(img, curTime, chatMain.mainFrame, chatMain);
            chatMain.setEndline();
            chatMain.area.appendLine(my);
         }
      });
      la_center2.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            // chatMain.area_input.insertComponent(new JLabel(iconList2[5]));
            // chatMain.sendIcon(la_center2.getName());
            Image img = iconList[5].getImage();
            String curTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            MyBubble my = new MyBubble(img, curTime, chatMain.mainFrame, chatMain);
            chatMain.setEndline();
            chatMain.area.appendLine(my);
         }
      });
      la_center3.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            // chatMain.area_input.insertComponent(new JLabel(iconList2[6]));
            // chatMain.sendIcon(la_center3.getName());
            Image img = iconList[6].getImage();
            String curTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            MyBubble my = new MyBubble(img, curTime, chatMain.mainFrame, chatMain);
            chatMain.setEndline();
            chatMain.area.appendLine(my);
         }
      });
      la_center4.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            // chatMain.area_input.insertComponent(new JLabel(iconList2[7]));
            // chatMain.sendIcon(la_center4.getName());
            Image img = iconList[7].getImage();
            String curTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            MyBubble my = new MyBubble(img, curTime, chatMain.mainFrame, chatMain);
            chatMain.setEndline();
            chatMain.area.appendLine(my);
         }
      });
      la_south1.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            // chatMain.area_input.insertComponent(new JLabel(iconList2[8]));
            // chatMain.sendIcon(la_south1.getName());
            Image img = iconList[8].getImage();
            String curTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            MyBubble my = new MyBubble(img, curTime, chatMain.mainFrame, chatMain);
            chatMain.setEndline();
            chatMain.area.appendLine(my);
         }
      });
      la_south2.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            // chatMain.area_input.insertComponent(new JLabel(iconList2[9]));
            // chatMain.sendIcon(la_south2.getName());
            Image img = iconList[9].getImage();
            String curTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            MyBubble my = new MyBubble(img, curTime, chatMain.mainFrame, chatMain);
            chatMain.setEndline();
            chatMain.area.appendLine(my);
         }
      });
      la_south3.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            // chatMain.area_input.insertComponent(new JLabel(iconList2[10]));
            // chatMain.sendIcon(la_south3.getName());
            Image img = iconList[10].getImage();
            String curTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            MyBubble my = new MyBubble(img, curTime, chatMain.mainFrame, chatMain);
            chatMain.setEndline();
            chatMain.area.appendLine(my);
         }
      });
      la_south4.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            // chatMain.area_input.insertComponent(new JLabel(iconList2[11]));
            // chatMain.sendIcon(la_south4.getName());
            Image img = iconList[11].getImage();
            String curTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            MyBubble my = new MyBubble(img, curTime, chatMain.mainFrame, chatMain);
            chatMain.setEndline();
            chatMain.area.appendLine(my);
         }
      });

      setBackground(Color.WHITE);
      setPreferredSize(new Dimension(300, 200));
      setVisible(true);
   }
}