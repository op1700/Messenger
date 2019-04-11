package messenger.login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.json.simple.JSONObject;

import messenger.client.ClientThread;
import messenger.mainframe.MainFrame;
import messenger.regist.MemberRegist;
import messenger.utils.ImageLoad;

//�α��� ȭ���� �����Ѵ�
public class MemberLogin extends JPanel {
   JPanel p_north, p_center, p_idArea, p_pwArea, p_south; // ���� ������ ���� �г�
   JButton bt_accept, bt_regist; // �α��� Ȯ�� ��ư�� ȸ�� ���� ��ư
   JLabel n_id, n_pw; // ���̵�� ��й�ȣ�� ǥ���� �ؽ�Ʈ
   JTextField t_id; // ���̵� �Է� â
   JPasswordField t_pw; // ��й�ȣ �Է� â
   // -------------------------------------
   LoginMain loginMain;
   Image image;
   ImageLoad imageLoad;

   JSONObject obj = new JSONObject();

   int port = 8888;
   Socket client;// ��ȭ�� ����
   ClientThread ct;

   String c_id;
   String c_pw;
   String order;

   public String MyCode;
   public String MyId;
   public String MyName;
   public String MyBirth;
   public String MyPhone;
   public String MyEmail;
   public String MyNick;

   MainFrame mainFrame;// ���� �����ӿ� result�� ����������
   MemberRegist regist;
   int count = 0;

   public MemberLogin(LoginMain loginMain) {
      this.loginMain = loginMain;
      setLayout(new BorderLayout());
      imageLoad = new ImageLoad();
      image = imageLoad.getImage("talk.png");
      image = image.getScaledInstance(500, 300, Image.SCALE_SMOOTH);

      p_north = new JPanel() {
         public void paint(Graphics g) {
            g.drawImage(image, 0, 100, MemberLogin.this);
         }
      };
      p_center = new JPanel();
      p_south = new JPanel();
      p_idArea = new JPanel();
      p_pwArea = new JPanel();
      // -------------------------------------
      n_id = new JLabel();
      n_pw = new JLabel();
      // -------------------------------------
      t_id = new JTextField(15);
      t_pw = new JPasswordField(15);
      // -------------------------------------
      bt_accept = new JButton("Ȯ��");
      bt_regist = new JButton("����");

      /* <�ؽ�Ʈ ���� ����> */
      n_id.setFont(new Font("", Font.BOLD, 20));
      n_id.setPreferredSize(new Dimension(30, 40));
      n_id.setBackground(Color.ORANGE);
      n_id.setSize(30, 30);
      n_id.setText("id");
      // -------------------------------------
      n_pw.setFont(new Font("", Font.BOLD, 20));
      n_pw.setPreferredSize(new Dimension(30, 40));
      n_pw.setBackground(Color.ORANGE);
      n_pw.setSize(30, 30);
      n_pw.setText("pw");
      // -------------------------------------
      t_id.setFont(new Font(null, Font.PLAIN, 20));
      // -------------------------------------
      t_pw.setFont(new Font(null, Font.PLAIN, 20));

      /* <���� �гε� ���� ����> */
      p_north.setPreferredSize(new Dimension(500, 500));
      // -------------------------------------
      p_center.setPreferredSize(new Dimension(500, 190));
      p_center.setBackground(Color.ORANGE);
      p_center.add(p_idArea);
      p_center.add(p_pwArea, BorderLayout.SOUTH);
      // -------------------------------------
      p_idArea.setPreferredSize(new Dimension(500, 40));
      p_idArea.setBackground(Color.ORANGE);
      p_idArea.add(n_id);
      p_idArea.add(t_id);
      // -------------------------------------
      p_pwArea.setPreferredSize(new Dimension(500, 150));
      p_pwArea.setBackground(Color.ORANGE);
      p_pwArea.add(n_pw);
      p_pwArea.add(t_pw);
      // -------------------------------------
      p_south.setPreferredSize(new Dimension(500, 50));
      p_south.setBackground(Color.ORANGE);
      p_south.add(bt_accept);
      p_south.add(bt_regist);

      /* <�α��� Ȯ�� ��ư ��� ����> */
      bt_accept.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            c_id = t_id.getText();
            c_pw = new String(t_pw.getPassword());
            if (c_id.length() == 0) {
               JOptionPane.showMessageDialog(p_center, "���̵� �Է����ּ���");
               return;
            }
            if (c_pw.length() == 0) {
               JOptionPane.showMessageDialog(p_center, "��й�ȣ�� �Է����ּ���");
               return;
            }
            String order = accept();
            ct.send(order);
            t_id.setText("");
            t_pw.setText("");
         }
      });
      
      t_pw.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
            int key=e.getKeyCode();
            if(key==KeyEvent.VK_ENTER) {
               c_id = t_id.getText();
               c_pw = new String(t_pw.getPassword());
               if (c_id.length() == 0) {
                  JOptionPane.showMessageDialog(p_center, "���̵� �Է����ּ���");
                  return;
               }
               if (c_pw.length() == 0) {
                  JOptionPane.showMessageDialog(p_center, "��й�ȣ�� �Է����ּ���");
                  return;
               }
               String order = accept();
               ct.send(order);
               t_id.setText("");
               t_pw.setText("");
            }
         }
      });
      
      /* ȸ�� ���� ��ư�� ��� ���� */
      bt_regist.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            regist = new MemberRegist(ct);
            ct.memberRegist = regist;
         }
      });

      /* <Login �г� ���� ����> */
      setLayout(new BorderLayout());
      // -------------------------------------
      add(p_north, BorderLayout.NORTH);
      add(p_center, BorderLayout.CENTER);
      add(p_south, BorderLayout.SOUTH);
      // -------------------------------------
      setBackground(Color.ORANGE);
      setPreferredSize(new Dimension(500, 800));
      setVisible(true);
      connectServer();// ��������
   }

   public String accept() {
      c_id = t_id.getText();
      c_pw = new String(t_pw.getPassword());
         
      obj.put("Type", "login");
      obj.put("Id", c_id);
      obj.put("Pw", c_pw);
      String order = obj.toString();

      return order;
   }

   
   public void connectServer() {

      String ip = "192.168.0.200";
      // "192.168.0.20"
      //192.168.54.25
      try {
         client = new Socket(ip, port);
         ct = new ClientThread(client, this, loginMain);
         ct.start();

      } catch (UnknownHostException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

}