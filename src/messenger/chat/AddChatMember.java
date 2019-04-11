package messenger.chat;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import messenger.chat.ChatMain;
import messenger.mainframe.MainFrame;
import messenger.utils.ImageLoad;
import messenger.utils.Master;

public class AddChatMember extends JFrame {
   JPanel p_wrapper, p_title, p_left, p_addList, p_button;
   JPanel p_search, p_friendList;
   JLabel lb_title, lb_search, lb_info, lb_addList;
   JTextField t_search;
   JButton bt_add, bt_cancel;
   JScrollPane scroll, addScroll;
   Image img_search;
   ImageLoad imageLoad;
   ChatMain chatMain;
   Master master;

   Font font; // �̼����� �⺻ ��Ʈ
   boolean flag_visible = false; // AddChat �г��� visible
   boolean flag_exist = false; // �̹� ������� ����
   int search = 0; // �˻� ��Ȳ�� ��Ÿ���� ���� (0,1,2)
   int addCount = 0; // addList�� �ٴ� �г� ����

   ArrayList noMemberFriendList = new ArrayList(); // ä�ù� ����� �ƴ� ģ���� �������� ��� �迭
   ArrayList<JPanel> friendList = new ArrayList(); // ģ�� ��� �г��� ��� �迭
   ArrayList<String> addfriendList = new ArrayList(); // �߰��Ǵ� ģ�� �̸��� ��� �迭
   ArrayList<JPanel> addList = new ArrayList(); // �߰��Ǵ� ģ�� �г��� ��� �迭
   public JSONArray array = new JSONArray(); // ������ ���� ��ä�ù� ������ �ʿ��� ������ ��� �迭

   public AddChatMember(ChatMain chatMain) {
      this.chatMain = chatMain;

      p_wrapper = new JPanel(); // ��ü �г�
      p_title = new JPanel(); // Ÿ��Ʋ �г�
      p_left = new JPanel(); // �˻��� ģ������� ���� �г�
      p_search = new JPanel(); // �˻� �г�
      p_friendList = new JPanel(); // ģ����� �г�
      p_addList = new JPanel(); // ��ȭ���� �߰��� ģ����� �г�
      p_button = new JPanel(); // Ȯ��,��ҹ�ư�� ���� �г�

      lb_title = new JLabel("��ȭ��� �ʴ�");
      lb_title.setForeground(Color.WHITE);

      imageLoad = new ImageLoad();
      img_search = imageLoad.getImage("search.png");
      img_search = img_search.getScaledInstance(15, 15, Image.SCALE_SMOOTH);

      lb_search = new JLabel();
      lb_search.setIcon(new ImageIcon(img_search));
      t_search = new JTextField("�̸� �˻�", 20) { // JTextField �׵θ� ���ֱ�
         public void setBorder(Border border) {
         }
      };
      t_search.setFocusable(false);

      lb_addList = new JLabel("�ʴ��� ģ���� �����ϼ���");
      bt_add = new JButton("Ȯ��");
      bt_cancel = new JButton("���");
      scroll = new JScrollPane(p_left, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      addScroll = new JScrollPane(p_addList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

      // ��Ʈ ����
      font = new Font(chatMain.mainFrame.getFontString(), Font.PLAIN, 14);
      lb_title.setFont(new Font(chatMain.mainFrame.getFontString(), Font.BOLD, 16));
      t_search.setFont(font);
      lb_search.setFont(font);
      lb_addList.setFont(font);
      bt_add.setFont(font);
      bt_cancel.setFont(font);

      // ģ����� ����
      if (chatMain.log) {
         for (int a = 0; a < chatMain.mainFrame.friendList.size(); a++) {
            String[] friend = (String[]) chatMain.mainFrame.friendList.get(a);
            flag_exist = false;
            
            for (int i = 0; i < chatMain.memberArray.size(); i++) {
               String code = (String) chatMain.memberArray.get(i);
               if (code.equals(friend[0])) {
                  flag_exist = true;
               }
            }
            
            if(!flag_exist) {
               noMemberFriendList.add(friend);
            }
         }

      } else {
         for (int a = 0; a < chatMain.mainFrame.friendList.size(); a++) {
            String[] friend = (String[]) chatMain.mainFrame.friendList.get(a);
            flag_exist = false;
            
            for (int i = 0; i < chatMain.newChatArray.size(); i++) {
               JSONObject obj = (JSONObject) chatMain.newChatArray.get(i);
               String name = (String) obj.get("Chatmember");
               if (name.equals(friend[2])) {
                  flag_exist = true;
               }
            }
            
            if(!flag_exist) {
               noMemberFriendList.add(friend);
            }
         }
      }

      for (int i = 0; i < noMemberFriendList.size(); i++) {
         String[] obj = (String[]) noMemberFriendList.get(i);
         String ImgPath = "dog/" + obj[4];
         getFriend(i, obj[2], ImgPath);
      }

      // ������ ����
      p_title.setPreferredSize(new Dimension(600, 40));
      p_title.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
      lb_search.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
      t_search.setPreferredSize(new Dimension(300, 40));
      p_left.setPreferredSize(new Dimension(350, 400));
      lb_addList.setBounds(125, 200, 125, 20);
      p_addList.setPreferredSize(new Dimension(210, 400));
      p_addList.setBorder(BorderFactory.createEmptyBorder(180, 15, 0, 0));
      bt_add.setPreferredSize(new Dimension(70, 40));
      bt_cancel.setPreferredSize(new Dimension(70, 40));
      p_button.setPreferredSize(new Dimension(600, 60));
      p_button.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
      p_wrapper.setPreferredSize(new Dimension(600, 500));

      // ���� ����
      p_title.setBackground(chatMain.mainFrame.getColor());
      p_search.setBackground(Color.WHITE);
      p_search.setBorder(BorderFactory.createLineBorder(Color.GRAY));
      p_friendList.setBackground(Color.WHITE);
      bt_add.setBackground(Color.YELLOW);
      bt_cancel.setBackground(Color.WHITE);
      p_button.setBackground(Color.WHITE);
      p_wrapper.setBorder(BorderFactory.createLineBorder(Color.GRAY));

      // ���̾ƿ� ����
      p_left.setLayout(new BorderLayout());
      p_search.setLayout(new BorderLayout());
      p_wrapper.setLayout(new BorderLayout());

      // ����
      p_title.add(lb_title);
      p_search.add(lb_search, BorderLayout.WEST);
      p_search.add(t_search);
      p_left.add(p_search, BorderLayout.NORTH);
      p_left.add(p_friendList);
      p_addList.add(lb_addList);
      p_button.add(bt_add);
      p_button.add(bt_cancel);

      p_wrapper.add(p_title, BorderLayout.NORTH);
      p_wrapper.add(scroll, BorderLayout.WEST);
      p_wrapper.add(addScroll, BorderLayout.EAST);
      p_wrapper.add(p_button, BorderLayout.SOUTH);
      add(p_wrapper);

      // �˻�â�� Ŭ������ �� �ȳ������� �Ⱥ��̰� ����
      t_search.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            t_search.setText("");
            t_search.setFocusable(true);
         }
      });

      // �˻�â�� ������ ����
      t_search.addKeyListener(new KeyAdapter() {
         public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_ENTER) {
               searchName(t_search.getText());
               t_search.setText("�̸� �˻�");
               t_search.setFocusable(false);
            }
         }
      });

      // Ȯ�� ��ư ������ �� ��� �ʴ�
      bt_add.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(AddChatMember.this, "�ʴ� �Ϸ�");
            dispose();
            send();
         }
      });

      // ��� ��ư�� ������ ����
      bt_cancel.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            dispose();
         }
      });

      setSize(600, 500);
      setResizable(false);
      setUndecorated(true);
      setVisible(true);
   }

   // ģ����Ͽ� ���� �г��� �����ϴ� �Լ�
   public void getFriend(int code, String name, String img) {
      JPanel p_friend = new JPanel(); // ģ�� �Ѹ��� ��ü �г�
      JPanel p_content = new JPanel(); // ����� �̸��� ���� �г�
      JPanel p_check = new JPanel(); // üũ�ڽ��� ���� �г�

      JLabel lb_img = new JLabel();
      JLabel lb_name = new JLabel(name);
      lb_name.setFont(font);

      Image image = imageLoad.getImage(img);
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
      Checkbox check = new Checkbox();

      // ���̾ƿ� ����
      p_friend.setLayout(new BorderLayout());
      p_content.setLayout(new BorderLayout());

      // ������ ����
      p_friend.setPreferredSize(new Dimension(330, 40));
      p_content.setPreferredSize(new Dimension(300, 40));
      p_content.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
      lb_name.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
      p_check.setPreferredSize(new Dimension(30, 40));

      // ���� ����
      p_friend.setBackground(Color.WHITE);
      p_friend.setBorder(BorderFactory.createLineBorder(Color.GRAY));
      p_content.setBackground(Color.WHITE);
      p_check.setBackground(Color.WHITE);

      // ����
      p_content.add(lb_img, BorderLayout.WEST);
      p_content.add(lb_name);
      p_check.add(check);
      p_friend.add(p_content, BorderLayout.WEST);
      p_friend.add(p_check, BorderLayout.EAST);
      p_friendList.add(p_friend);

      friendList.add(p_friend);

      // üũ�ڽ� ������ p_addList�� �г� ����, ������ ������ �г� ����
      check.addItemListener(new ItemListener() {
         public void itemStateChanged(ItemEvent e) {
            if (check.getState()) {
               addFriend(code, name, img, check);
               addfriendList.add(name);
            } else {
               removeFriend(code);
               addfriendList.remove(name);
            }
         }
      });

      // ģ����Ͽ� ���콺�� �ø��� �гο� ���� ĥ������, ������ �ٽ� ���ƿ��� ����
      p_friend.addMouseListener(new MouseAdapter() {
         public void mouseEntered(MouseEvent e) {
            Color c = chatMain.mainFrame.getColor();
            p_friend.setBackground(c);
            p_content.setBackground(c);
            p_check.setBackground(c);
            check.setBackground(c);
         }

         public void mouseExited(MouseEvent e) {
            p_friend.setBackground(Color.WHITE);
            p_content.setBackground(Color.WHITE);
            p_check.setBackground(Color.WHITE);
            check.setBackground(Color.WHITE);
         }
      });
   }

   // ��ȭ���� �߰��ϴ� ģ����Ͽ� ���� �г��� �����ϴ� �Լ�
   public void addFriend(int code, String name, String img, Checkbox check) {
      JPanel p_addfriend = new JPanel(); // ģ�� �Ѹ��� ��ü �г�
      JPanel p_content = new JPanel(); // ����� �̸��� ���� �г�
      JPanel p_x = new JPanel(); // 'x' ǥ�ø� ���� �г�
      JPanel p_empty; // addList�� ä�� �� �г�

      JLabel lb_img = new JLabel();
      ImageLoad imageLoad = new ImageLoad();
      Image image = imageLoad.getImage(img);
      image = image.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
      BufferedImage circleBuffer = new BufferedImage(25, 25, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2 = circleBuffer.createGraphics();
      g2.setComposite(AlphaComposite.Src);
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.fill(new RoundRectangle2D.Float(0, 0, 25, 25, 20, 20));
      g2.setComposite(AlphaComposite.SrcAtop);
      g2.drawImage(image, 0, 0, 25, 25, null);
      g2.dispose();
      lb_img.setIcon(new ImageIcon(circleBuffer));

      JLabel lb_name = new JLabel(name);
      lb_name.setFont(font);
      lb_name.setForeground(Color.WHITE);
      JLabel lb_x = new JLabel("x");
      lb_x.setFont(new Font(chatMain.mainFrame.getFontString(), Font.BOLD, 18));
      lb_x.setForeground(Color.WHITE);

      addCount++;

      // ���̾ƿ� ����
      p_addfriend.setLayout(new BorderLayout());
      p_content.setLayout(new BorderLayout());
      p_x.setLayout(new BorderLayout());

      // ������ ����
      p_addfriend.setPreferredSize(new Dimension(210, 30));
      p_content.setPreferredSize(new Dimension(180, 30));
      p_content.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
      lb_name.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
      p_x.setPreferredSize(new Dimension(30, 30));
      lb_x.setBorder(BorderFactory.createEmptyBorder(0, 5, 8, 0));

      // ���� ����
      p_addfriend.setBackground(chatMain.mainFrame.getColor());
      p_addfriend.setBorder(BorderFactory.createLineBorder(Color.GRAY));
      p_content.setBackground(chatMain.mainFrame.getColor());
      p_x.setBackground(chatMain.mainFrame.getColor());

      // ����
      p_content.add(lb_img, BorderLayout.WEST);
      p_content.add(lb_name);
      p_x.add(lb_x);
      p_addfriend.add(p_content, BorderLayout.WEST);
      p_addfriend.add(p_x, BorderLayout.EAST);

      // ��ȭ��븦 �߰��ϸ� �ȳ������� �Ⱥ��̰� ����
      if (addCount == 1) {
         p_addList.remove(lb_addList);
         p_addList.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      }

      // addList�� ���� �� �г��� ��´�
      for (int i = 0; i < noMemberFriendList.size(); i++) {
         addList.add(p_empty = new JPanel());
      }
      addList.set(code, p_addfriend);
      p_addList.add(p_addfriend);
      p_addList.updateUI();

      // 'x' Ŭ���� �߰��� �г��� �ٽ� �����
      lb_x.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            addCount--;
            addfriendList.remove(name);
            p_addList.remove(p_addfriend);
            p_addList.updateUI();
            check.setState(false);
         }
      });
   }

   // ��ȭ���� �߰��� ģ���г��� �����ϴ� �Լ�
   public void removeFriend(int code) {
      JPanel p_empty; // addList�� �ٽ� ä�� �� �г�

      addCount--;

      // �߰��� ģ���� ������ �ȳ������� ���̵��� ����
      if (addCount == 0) {
         p_addList.add(lb_addList);
         p_addList.setBorder(BorderFactory.createEmptyBorder(180, 15, 0, 0));
      }

      p_addList.remove(addList.get(code));
      p_addList.updateUI();
      addList.set(code, p_empty = new JPanel());
   }

   // �˻��� �̸��� �ִ��� Ȯ���ϴ� �Լ�
   public void searchName(String name) {
      int selectNum = -1;
      for (int i = 0; i < noMemberFriendList.size(); i++) {
         String[] obj = (String[]) noMemberFriendList.get(i);
         if (name.equals(obj[2])) { // �˻��� �̸��� �ִ� ���
            search = 1;
            selectNum = i;
         }
      }

      // �˻��� �̸��� ���� ���
      if (search == 0) {
         search = 2;
      }

      // �˻��� ���� ���
      if (name.equals("")) {
         search = 0;
      }

      // visible ����
      if (lb_info != null) { // �ȳ������� �ִ� ��� ���� ����� ����
         p_friendList.remove(lb_info);
         p_friendList.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      }
      if (search == 0) { // �˻��� ���� ���
         for (int i = 0; i < friendList.size(); i++) {
            friendList.get(i).setVisible(true);
         }
      } else if (search == 1) { // �˻��� ����� �ִ� ���
         for (int i = 0; i < friendList.size(); i++) {
            if (i == selectNum) {
               friendList.get(i).setVisible(true);
            } else {
               friendList.get(i).setVisible(false);
            }
            friendList.get(i).updateUI();
         }
         p_friendList.updateUI();
      } else if (search == 2) { // �˻��� ����� ���� ���
         for (int i = 0; i < friendList.size(); i++) {
            friendList.get(i).setVisible(false);
         }
         p_friendList.updateUI();
         lb_info = new JLabel(name + "��(��) ã�� �� �����ϴ�.");
         lb_info.setFont(new Font(chatMain.mainFrame.getFontString(), Font.PLAIN, 14));
         lb_info.setBounds(125, 200, 125, 20);
         p_friendList.setBorder(BorderFactory.createEmptyBorder(160, 15, 0, 0));
         p_friendList.add(lb_info);
      }
      p_friendList.updateUI();
   }

   public void send() { // ä�ù��� �ֳ� ���� Ȯ�� �ؾ���
      String chat_name = chatMain.name;

      // ä�ù� �̸� ����
      if (addfriendList.size() == 1) {
         chat_name += ", " + addfriendList.get(0);
      } else {
         for (int i = 0; i < addfriendList.size(); i++) {
            if (i == addfriendList.size() - 1) {
               chat_name += addfriendList.get(i);
            } else if (i == 0) {
               chat_name += ", " + addfriendList.get(i) + ", ";
            } else {
               chat_name += addfriendList.get(i) + ", ";
            }
         }
      }

      if (chatMain.log) { // �αװ� �ִ� ä�ù��� ���
         // ���� ������� ���� ��´�
         for (int i = 0; i < chatMain.memberArray.size(); i++) {
            String code = (String) chatMain.memberArray.get(i);
            for (int a = 0; a < chatMain.mainFrame.friendList.size(); a++) {
               String[] friend = (String[]) chatMain.mainFrame.friendList.get(a);
               if (code.equals(friend[0])) {
                  JSONObject obj = new JSONObject();
                  obj.put("Type", "checkRoom");
                  obj.put("MyCode", chatMain.mainFrame.myProfile[0]);
                  obj.put("ChatName", chat_name);
                  obj.put("FriendNick", friend[2]);

                  array.add(obj);
               }
            }
         }

         // ���� �ʴ��ϴ� ������� ��´�
         for (int a = 0; a < addfriendList.size(); a++) {
            JSONObject obj = new JSONObject();
            obj.put("Type", "checkRoom");
            obj.put("MyCode", chatMain.mainFrame.myProfile[0]);
            obj.put("ChatName", chat_name);
            obj.put("FriendNick", addfriendList.get(a));

            array.add(obj);
         }

      } else { // �αװ� ���� ä�ù��� ���
         // ���� ������� ���� ��´�
         for (int i = 0; i < chatMain.newChatArray.size(); i++) {
            JSONObject chatObj = (JSONObject) chatMain.newChatArray.get(i);
            String name = (String) chatObj.get("Chatmember");
            for (int a = 0; a < chatMain.mainFrame.friendList.size(); a++) {
               String[] friend = (String[]) chatMain.mainFrame.friendList.get(a);
               if (name.equals(friend[2])) {
                  JSONObject obj = new JSONObject();
                  obj.put("Type", "checkRoom");
                  obj.put("MyCode", chatMain.mainFrame.myProfile[0]);
                  obj.put("ChatName", chat_name);
                  obj.put("FriendNick", friend[2]);

                  array.add(obj);
               }
            }
         }

         // ���� �ʴ��ϴ� ������� ��´�
         for (int a = 0; a < addfriendList.size(); a++) {
            JSONObject obj = new JSONObject();
            obj.put("Type", "checkRoom");
            obj.put("MyCode", chatMain.mainFrame.myProfile[0]);
            obj.put("ChatName", chat_name);
            obj.put("FriendNick", addfriendList.get(a));

            array.add(obj);
         }
      }
      System.out.println("@@@@@@@@@" + array);
      chatMain.mainFrame.ct.send(array.toJSONString());
   }
}