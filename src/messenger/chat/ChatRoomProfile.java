package messenger.chat;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.json.simple.JSONObject;

import messenger.memberlist.MemberProfile;
import messenger.utils.ImageLoad;

public class ChatRoomProfile extends JFrame {
   JPanel p_wrapper, p_title, p_center, p_member;
   JPanel p_info, p_button, p_btImg, p_btInfo;
   JLabel la_title, la_x, la_img, la_name, la_count;
   JLabel la_plusBt, la_plus, la_exitBt, la_exit;
   JScrollPane scroll;
   Image roomImg, plusImg, exitImg;

   ImageLoad imageLoad;
   ChatMain chatMain;
   MemberProfile memberProfile;
   AddChatMember addChatMember;
   ArrayList<Image> imgName;
   ArrayList memberArray;

   public ChatRoomProfile(ChatMain chatMain, ArrayList<Image> imgName) {
      this.chatMain = chatMain;
      this.imgName = imgName;
      memberArray = chatMain.memberArray;

      p_wrapper = new JPanel(); // ��ü �г�
      p_title = new JPanel(); // Ÿ��Ʋ �г�
      p_info = new JPanel(); // ä�ù� ���� �г�
      p_button = new JPanel(); // ģ�� �ʴ�� ä�ù� ������ ��ư�� ���� �г�
      p_btImg = new JPanel(); // ��ư �̹��� �г�
      p_btInfo = new JPanel(); // ��ư ���� �г�
      p_center = new JPanel(); // ä�ù� ������ ��ư���� ���� �г�
      p_member = new JPanel(); // ä�ù� �����ڵ��� ������ ���� �г�
      scroll = new JScrollPane(p_member, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

      la_title = new JLabel("     ä�ù� ����", SwingConstants.CENTER);
      la_title.setForeground(Color.WHITE);
      la_x = new JLabel("X");
      la_x.setForeground(Color.WHITE);

      la_img = new JLabel();
      imageLoad = new ImageLoad();
      BufferedImage circleBuffer = new BufferedImage(80, 80, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2 = circleBuffer.createGraphics();
      g2.setComposite(AlphaComposite.Src);
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.fill(new RoundRectangle2D.Float(0, 0, 80, 80, 70, 70));
      g2.setComposite(AlphaComposite.SrcAtop);
      if (imgName.size() == 1) { // ���� ���常 ���� ��
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
      la_img.setIcon(new ImageIcon(circleBuffer));

      la_name = new JLabel(chatMain.name, SwingConstants.CENTER);
      la_count = new JLabel("", SwingConstants.CENTER);

      la_plusBt = new JLabel();
      plusImg = imageLoad.getImage("plus.png");
      plusImg = plusImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
      la_plusBt.setIcon(new ImageIcon(plusImg));
      la_plus = new JLabel(" ģ�� �ʴ�");

      la_exitBt = new JLabel();
      exitImg = imageLoad.getImage("exit.png");
      exitImg = exitImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
      la_exitBt.setIcon(new ImageIcon(exitImg));
      la_exit = new JLabel("ä�ù� ������", SwingConstants.CENTER);

      // ä�� �����ڵ��� �г� ����
      if(chatMain.log) {
         for(int i=0; i<memberArray.size();i++) {
            String code = (String) memberArray.get(i);
            if(code.equals(chatMain.mainFrame.myProfile[0])) {  //����� ���� ���
               String myname = chatMain.mainFrame.myProfile[2];
               String myimg = chatMain.mainFrame.myProfile[4];
               String[] myprofile = chatMain.mainFrame.myProfile;
               getMember(myname, myimg, myprofile);
            }else {  //����� ģ���� ���
               for (int a = 0; a < chatMain.mainFrame.friendList.size(); a++) {
                  String[] friend = (String[]) chatMain.mainFrame.friendList.get(a);
                  if (code.equals(friend[0])) {
                     String name = friend[2];
                     String img = friend[4];
                     String[] profile =friend;
                     getMember(name, img, profile);
                  }
               }
            }
            
            //ä�ù� �ο��� ǥ��
            int count = memberArray.size();
            la_count.setText(count + "��");
            la_count.setForeground(Color.GRAY);
         }
         
      }else {
         JSONObject myobj = (JSONObject) chatMain.newChatArray.get(0);
         String myname = (String) myobj.get("Chatmember");
         String myimg = chatMain.mainFrame.myProfile[4];
         String[] myprofile = chatMain.mainFrame.myProfile;
         getMember(myname, myimg, myprofile);
   
         for (int i = 1; i < chatMain.newChatArray.size(); i++) { // ä�� �������� ������ �����ڵ�
            JSONObject obj = (JSONObject) chatMain.newChatArray.get(i);
            String name = (String) obj.get("Chatmember");
            String img = null;
            String[] profile = new String[8];
   
            for (int a = 0; a < chatMain.mainFrame.friendList.size(); a++) {
               String[] friend = (String[]) chatMain.mainFrame.friendList.get(a);
               if (name.equals(friend[2])) {
                  img = friend[4];
                  profile = friend;
               }
            }
            getMember(name, img, profile);
         }
         
         //ä�ù� �ο��� ǥ��
         int count = chatMain.newChatArray.size();
         la_count.setText(count + "��");
         la_count.setForeground(Color.GRAY);
      }
         

      // ��Ʈ ����
      Font f = new Font(chatMain.mainFrame.getFontString(), Font.PLAIN, 14);
      la_title.setFont(new Font(chatMain.mainFrame.getFontString(), Font.BOLD, 15));
      la_x.setFont(new Font(chatMain.mainFrame.getFontString(), Font.BOLD, 16));
      la_name.setFont(new Font(chatMain.mainFrame.getFontString(), Font.BOLD, 18));
      la_count.setFont(new Font(chatMain.mainFrame.getFontString(), Font.PLAIN, 16));
      la_plus.setFont(f);
      la_exit.setFont(f);

      // ������ ����
      p_title.setPreferredSize(new Dimension(350, 40));
      la_title.setPreferredSize(new Dimension(320, 40));
      la_x.setPreferredSize(new Dimension(30, 40));
      p_info.setPreferredSize(new Dimension(350, 150));
      la_img.setPreferredSize(new Dimension(80, 80));
      la_name.setPreferredSize(new Dimension(350, 30));
      la_count.setPreferredSize(new Dimension(350, 20));
      la_plusBt.setPreferredSize(new Dimension(80, 50));
      la_exitBt.setPreferredSize(new Dimension(50, 50));
      p_btImg.setPreferredSize(new Dimension(350, 60));
      p_btImg.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
      la_plus.setPreferredSize(new Dimension(70, 40));
      la_exit.setPreferredSize(new Dimension(110, 40));
      p_btInfo.setPreferredSize(new Dimension(350, 55));
      p_button.setPreferredSize(new Dimension(350, 120));
      p_center.setPreferredSize(new Dimension(350, 270));
      p_member.setPreferredSize(new Dimension(350, 290));
      p_member.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
      p_wrapper.setPreferredSize(new Dimension(350, 600));

      // ���� ����
      p_title.setBackground(chatMain.mainFrame.getColor());
      p_info.setBackground(Color.WHITE);
      p_btImg.setBackground(Color.WHITE);
      p_btInfo.setBackground(Color.WHITE);
      p_button.setBackground(Color.WHITE);
      p_button.setBorder(BorderFactory.createLineBorder(Color.GRAY));
      p_center.setBackground(Color.WHITE);
      p_member.setBackground(Color.WHITE);
      p_wrapper.setBorder(BorderFactory.createLineBorder(Color.GRAY));

      // ���̾ƿ� ����
      p_title.setLayout(new BorderLayout());
      p_button.setLayout(new BorderLayout());
      p_wrapper.setLayout(new BorderLayout());

      // ����
      p_title.add(la_title);
      p_title.add(la_x, BorderLayout.EAST);
      p_info.add(la_img);
      p_info.add(la_name);
      p_info.add(la_count);
      p_btImg.add(la_plusBt);
      p_btImg.add(la_exitBt);
      p_btInfo.add(la_plus);
      p_btInfo.add(la_exit);
      p_button.add(p_btImg, BorderLayout.NORTH);
      p_button.add(p_btInfo);
      p_center.add(p_info);
      p_center.add(p_button);

      p_wrapper.add(p_title, BorderLayout.NORTH);
      p_wrapper.add(p_center);
      p_wrapper.add(scroll, BorderLayout.SOUTH);
      add(p_wrapper);

      // 'x' Ŭ���� ���� â�� �ݴ´�
      la_x.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            dispose();
         }
      });

      // �÷��� ��ư Ŭ���� ä�ù濡 ģ�� �ʴ�
      la_plusBt.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            int x = (int) e.getComponent().getParent().getLocationOnScreen().getX();
            int y = (int) e.getComponent().getParent().getLocationOnScreen().getY();
            addChatMember = new AddChatMember(chatMain);
            addChatMember.setBounds(x + 300, y, 600, 500);
         }
      });

      // ������ ��ư Ŭ���� ä�ù� ������
      la_exitBt.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            JSONObject orderObj = new JSONObject();
            orderObj.put("Type", "chat_del");
            orderObj.put("UserCode", chatMain.mainFrame.myProfile[0]);
            orderObj.put("ChatCode", chatMain.chat_code);
            chatMain.mainFrame.ct.send(orderObj.toString());

            dispose();
            chatMain.dispose();
            JOptionPane.showMessageDialog(chatMain.mainFrame, "ä�ù濡�� �������ϴ�");
         }
      });

      setSize(350, 600);
      setResizable(false);
      setUndecorated(true);
      setVisible(true);
   }

   // ä�� ������ ��Ͽ� ���� �г��� �����ϴ� �Լ�
   public void getMember(String name, String img, String[] profile) {
      JPanel p_content = new JPanel(); // ����� �̸��� ���� �г�
      JLabel lb_img = new JLabel();
      JLabel lb_name = new JLabel(name);
      lb_name.setFont(new Font(chatMain.mainFrame.getFontString(), Font.PLAIN, 15));

      Image image = imageLoad.getImage("dog/" + img);
      image = image.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
      BufferedImage circleBuffer = new BufferedImage(35, 35, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2 = circleBuffer.createGraphics();
      g2.setComposite(AlphaComposite.Src);
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.fill(new RoundRectangle2D.Float(0, 0, 35, 35, 25, 25));
      g2.setComposite(AlphaComposite.SrcAtop);
      g2.drawImage(image, 0, 0, 35, 35, null);
      g2.dispose();
      lb_img.setIcon(new ImageIcon(circleBuffer));

      // ���̾ƿ� ����
      p_content.setLayout(new BorderLayout());

      // ������ ����
      p_content.setPreferredSize(new Dimension(310, 40));
      p_content.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
      lb_name.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

      // ���� ����
      p_content.setBackground(Color.WHITE);
      p_content.setBorder(BorderFactory.createLineBorder(Color.GRAY));

      // ����
      p_content.add(lb_img, BorderLayout.WEST);
      p_content.add(lb_name);
      p_member.add(p_content);

      // ģ����Ͽ� ���콺�� �ø��� �гο� ���� ĥ������, ������ �ٽ� ���ƿ��� ����
      p_content.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() > 1) { // ���� Ŭ�� ���� �� ������ ����
               int x = (int) e.getComponent().getParent().getLocationOnScreen().getX();
               int y = (int) e.getComponent().getParent().getLocationOnScreen().getY();
               memberProfile = new MemberProfile(chatMain.mainFrame, profile);
               memberProfile.setBounds(x - 360, y - 310, 350, 600);
            }
         }

         public void mouseEntered(MouseEvent e) {
            Color c = chatMain.mainFrame.getColor();
            p_content.setBackground(c);
         }

         public void mouseExited(MouseEvent e) {
            p_content.setBackground(Color.WHITE);
         }
      });
   }
}