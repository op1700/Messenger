package messenger.option;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import org.json.simple.JSONObject;

import messenger.mainframe.MainFrame;
import messenger.utils.ImageLoad;

public class AddFriend extends JFrame {
   JPanel p_wrapper, p_title, p_center, p_button;
   JPanel p_search, p_list;
   JPanel p_profile;
   JLabel lb_title, lb_search, lb_list;
   JTextField t_search;
   JButton bt_add, bt_cancel;
   Image img_search;
   ImageLoad imageLoad;
   MainFrame mainFrame;
 
   Font font; // �̼����� �⺻ ��Ʈ
   boolean flag_search = false; // searchâ���� enter�� �������� ����
   boolean flag_profile = false; // �˻��� ������ �������� ���̴��� ����
   String search_nick; // �˻��� ������ ������ ��� ����
   String user_nick; // �߰��� ģ���� ������ ��� ����
   private String user_type; // �߰��� ģ���� ������ ��� ����

   public AddFriend(MainFrame mainFrame) {
      this.mainFrame = mainFrame;

      p_wrapper = new JPanel(); // ��ü �г�
      p_title = new JPanel(); // Ÿ��Ʋ �г�
      p_search = new JPanel(); // �˻� �г�
      p_list = new JPanel(); // �˻� ����� ���� �г�
      p_center = new JPanel(); // �˻��� �˻� ����� ���� �г�
      p_button = new JPanel(); // Ȯ��,��ҹ�ư�� ���� �г�

      lb_title = new JLabel("ģ�� �߰�");
      lb_title.setForeground(Color.WHITE); 

      imageLoad = new ImageLoad();
      img_search = imageLoad.getImage("search.png");
      img_search = img_search.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
      lb_search = new JLabel();
      lb_search.setIcon(new ImageIcon(img_search));
      t_search = new JTextField("�г������� ģ�� ã��", 20) { // JTextField �׵θ� ���ֱ�
         public void setBorder(Border border) {
         }
      };
      t_search.setFocusable(false);

      lb_list = new JLabel("�г������� ģ���� �߰��ϼ���", SwingConstants.CENTER);
      bt_add = new JButton("�߰�");
      bt_cancel = new JButton("���");

      // ��Ʈ ����
      font = new Font(mainFrame.getFontString(), Font.PLAIN, 14);
      lb_title.setFont(new Font(mainFrame.getFontString(), Font.BOLD, 16));
      t_search.setFont(font);
      lb_search.setFont(font);
      lb_list.setFont(font);
      bt_add.setFont(font);
      bt_cancel.setFont(font);

      // ������ ����
      p_title.setPreferredSize(new Dimension(400, 40));
      p_title.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
      lb_search.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
      t_search.setPreferredSize(new Dimension(250, 40));
      p_search.setPreferredSize(new Dimension(400, 50));
      lb_list.setPreferredSize(new Dimension(300, 20));
      p_list.setPreferredSize(new Dimension(400, 250));
      p_list.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
      p_center.setPreferredSize(new Dimension(400, 300));
      bt_add.setPreferredSize(new Dimension(70, 40));
      bt_cancel.setPreferredSize(new Dimension(70, 40));
      p_button.setPreferredSize(new Dimension(400, 60));
      p_button.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
      p_wrapper.setPreferredSize(new Dimension(400, 400));

      // ���� ����
      p_title.setBackground(mainFrame.getColor());
      p_search.setBackground(Color.WHITE);
      p_search.setBorder(BorderFactory.createLineBorder(Color.GRAY));
      p_list.setBackground(Color.WHITE);
      p_center.setBorder(BorderFactory.createLineBorder(Color.GRAY));
      bt_add.setBackground(Color.YELLOW);
      bt_cancel.setBackground(Color.WHITE);
      p_button.setBackground(Color.WHITE);
      p_wrapper.setBorder(BorderFactory.createLineBorder(Color.GRAY));

      // ���̾ƿ� ����
      p_search.setLayout(new BorderLayout());
      p_center.setLayout(new BorderLayout());
      p_wrapper.setLayout(new BorderLayout());

      // ����
      p_title.add(lb_title);
      p_search.add(lb_search, BorderLayout.WEST);
      p_search.add(t_search);
      p_list.add(lb_list);
      p_center.add(p_search, BorderLayout.NORTH);
      p_center.add(p_list);
      p_button.add(bt_add);
      p_button.add(bt_cancel);

      p_wrapper.add(p_title, BorderLayout.NORTH);
      p_wrapper.add(p_center);
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
               flag_search = true;
               search_nick = t_search.getText();

               JSONObject orderObj = new JSONObject();
               orderObj.put("Type", "f_search");
               orderObj.put("Search", t_search.getText());
               orderObj.put("MyCode", mainFrame.myProfile[0]);
               mainFrame.ct.send(orderObj.toString());// ȸ�� ���� ���� ���� ���� ��

               t_search.setText("�г������� ģ�� ã��");
               t_search.setFocusable(false);
            }
         }
      });

      // Ȯ�� ��ư ������ �� ģ�� �߰�
      bt_add.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            if (flag_profile) {
               // �߰��� ģ���� ������ �����ϴ� â ����
               int x = (int) e.getComponent().getParent().getLocationOnScreen().getX();
               int y = (int) e.getComponent().getParent().getLocationOnScreen().getY();
               SelectType selectType = new SelectType(mainFrame, AddFriend.this);
               selectType.setBounds(x + 27, y - 210, 350, 180);
            } else {
               JOptionPane.showMessageDialog(AddFriend.this, "�߰��� ģ���� �˻��ϼ���");
            }
         }
      });

      // ��� ��ư�� ������ ����
      bt_cancel.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            dispose();
         }
      });

      setSize(400, 400);
      setResizable(false);
      setUndecorated(true);
      setVisible(true);
   }

   // �˻� ����� ��Ÿ���� ������ ����
   public void showProfile(String name, String nick, String img) {
      // �˻��ϸ� �ȳ������� �Ⱥ��̰� ����
      if (flag_search) {
         p_list.remove(lb_list);
         p_list.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
      }
      // �̹� ã�� ���� ������ ������ �г� ����
      if (p_profile != null) {
         p_list.remove(p_profile);
         p_list.updateUI();
         flag_profile = false;
      }
      
      flag_profile = true;

      user_nick = nick;
      p_profile = new JPanel(); // ������ ��ü �г�
      JPanel p_img = new JPanel(); // ���縦 ���� �г�
      JPanel p_name = new JPanel(); // �̸��� ������ ���� �г�

      JLabel lb_img = new JLabel();
      ImageLoad imageLoad = new ImageLoad();
      Image image = imageLoad.getImage(img);
      image = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
      BufferedImage circleBuffer = new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2 = circleBuffer.createGraphics();
      g2.setComposite(AlphaComposite.Src);
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.fill(new RoundRectangle2D.Float(0, 0, 150, 150, 130, 130));
      g2.setComposite(AlphaComposite.SrcAtop);
      g2.drawImage(image, 0, 0, 150, 150, null);
      g2.dispose();
      lb_img.setIcon(new ImageIcon(circleBuffer));

      JLabel lb_name_info = new JLabel("�̸�", SwingConstants.LEFT);
      JLabel lb_name = new JLabel(name, SwingConstants.LEFT);
      JLabel lb_nick_info = new JLabel("����", SwingConstants.LEFT);
      JLabel lb_nick = new JLabel(nick, SwingConstants.LEFT);

      // ��Ʈ ����
      font = new Font(mainFrame.getFontString(), Font.BOLD, 17);
      lb_name_info.setFont(font);
      lb_nick_info.setFont(font);
      font = new Font(mainFrame.getFontString(), Font.PLAIN, 15);
      lb_name.setFont(font);
      lb_nick.setFont(font);

      // ���̾ƿ� ����
      p_profile.setLayout(new BorderLayout());

      // ������ ����
      p_profile.setPreferredSize(new Dimension(350, 200));
      p_img.setPreferredSize(new Dimension(170, 200));
      p_img.setBorder(BorderFactory.createEmptyBorder(17, 20, 0, 0));
      lb_name_info.setPreferredSize(new Dimension(100, 30));
      lb_name.setPreferredSize(new Dimension(100, 30));
      lb_nick_info.setPreferredSize(new Dimension(100, 30));
      lb_nick.setPreferredSize(new Dimension(100, 30));
      p_name.setPreferredSize(new Dimension(180, 200));
      p_name.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

      // ���� ����
      p_img.setBackground(mainFrame.getColor());
      p_name.setBackground(mainFrame.getColor());
      p_profile.setBorder(BorderFactory.createLineBorder(Color.GRAY));

      // ����
      p_img.add(lb_img);
      p_name.add(lb_name_info);
      p_name.add(lb_name);
      p_name.add(lb_nick_info);
      p_name.add(lb_nick);

      p_profile.add(p_img, BorderLayout.WEST);
      p_profile.add(p_name);
      p_list.add(p_profile);
      p_list.updateUI();
   }

   // ã�� ������ ���ų� �̹� ģ���� ��� �ȳ����� ����
   public void noExistUser() {
      if (p_profile == null) {
         lb_list.setText("'" + search_nick + "'��(��) ã�� �� ���ų� �̹� ģ�� �Դϴ�.");
         p_list.updateUI();
      } else { // �гλ��� �� �ȳ����� ����
         p_list.remove(p_profile);
         flag_profile = false;
         lb_list.setText("'" + search_nick + "'��(��) ã�� �� ���ų� �̹� ģ�� �Դϴ�.");
         p_list.add(lb_list);
         p_list.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
         p_list.updateUI();
      }
   }
   
   public void sendOrder() {
      // ������ ģ�� �߰��� ����
      JSONObject obj = new JSONObject();
      obj.put("Type", "friendadd");
      obj.put("Code", mainFrame.myProfile[0]); 
      obj.put("Relation", getUserType());
      obj.put("Nick", user_nick);
      mainFrame.ct.send(obj.toString());
      
   }

   public String getUserType() {
      return user_type;
   }

   public void setUserType(String user_type) {
      this.user_type = user_type;
   }
}