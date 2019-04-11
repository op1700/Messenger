package messenger.regist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import messenger.client.ClientThread;
import messenger.utils.ImageLoad;

public class MemberRegist extends JFrame {
   JPanel p_container; // 전체를 감싸는 패널
   JPanel p_north, p_center, p_south; // 전체 패널을 크게 3개로 구분
   JPanel p_emptyLeft, p_emptyRight, p_eRNorth, p_eRCenter; // 빈 공간을 채울 패널
   JPanel p_label, p_txt; // p_center안의 기능에 따라 구분
   JLabel la_profileImage; //p_emptyRight 부분 안에 프로필 사진 영역 패널
   JLabel la_name, la_id, la_pw, la_nick, la_birth, la_phone, la_email;
   public JTextField t_name, t_id, t_pw, t_nick, t_birth, t_phone, t_email;
   JButton bt_checkId, bt_checkNick;
   JButton bt_regist;
   JButton bt_profile;
      
   JSONParser parser;
   JSONArray array;
   Object obj = null;
   JSONObject jsonObj;
   String type;

   ClientThread ct;
   Profile profile;
   public String imgPath;

   String order;
   String name, id, pw, nick, birth, phone, email; // 각 텍스트창의 내용
   MemberRegist memberRegist;
   ImageLoad imageLoad;
   Image image;
   Image icon;
   Image profileImage;
   
   int countId=0;
   int countNick=0;
   String checkId;
   String checkNick;

   public MemberRegist(ClientThread ct) {
      super("네이트톡");
      this.ct = ct;
      memberRegist = this;
      
      /* <이미지 관련 설정> */
      imageLoad = new ImageLoad();
       image = imageLoad.getImage("rion.png");
       image = image.getScaledInstance(360, 400, Image.SCALE_SMOOTH);
             
      /* <인스턴스 호출> */
      p_container = new JPanel();
      p_north = new JPanel() {
         public void paint(Graphics g) {
            g.drawImage(image, 0, 0, MemberRegist.this);
         }
      };
      p_center = new JPanel();
      p_south = new JPanel();
      // --------------------------------------
      p_label = new JPanel();
      p_txt = new JPanel();
      // --------------------------------------
      p_emptyLeft = new JPanel();
      p_emptyRight = new JPanel();
      p_eRNorth = new JPanel();
      p_eRCenter = new JPanel();
      la_profileImage=new JLabel();
      // --------------------------------------
      la_name = new JLabel("이름");
      la_id = new JLabel("ID");
      la_pw = new JLabel("password");
      la_nick = new JLabel("nickName");
      la_birth = new JLabel("생일");
      la_phone = new JLabel("휴대폰");
      la_email = new JLabel("Email");
      // --------------------------------------
      t_name = new JTextField();
      t_id = new JTextField();
      t_pw = new JTextField();
      t_nick = new JTextField();
      t_birth = new JTextField();
      t_phone = new JTextField();
      t_email = new JTextField();

      // --------------------------------------
      bt_checkId = new JButton("중복 확인");
      bt_checkNick = new JButton("중복 확인");
      bt_regist = new JButton("회원 가입");
      bt_profile = new JButton("프로필 사진");

      /* <label 관련 설정> */
      Dimension d_la = new Dimension(70, 30);
      la_name.setPreferredSize(d_la);
      la_id.setPreferredSize(d_la);
      la_pw.setPreferredSize(d_la);
      la_nick.setPreferredSize(d_la);
      la_birth.setPreferredSize(d_la);
      la_phone.setPreferredSize(d_la);
      la_email.setPreferredSize(d_la);
      // bt_check.setPreferredSize(new Dimension(100, 30));

      /* <textField 관련 설정> */
      Dimension d_txt = new Dimension(200, 30);
      t_name.setPreferredSize(d_txt);
      t_id.setPreferredSize(d_txt);
      t_pw.setPreferredSize(d_txt);
      t_nick.setPreferredSize(d_txt);
      t_birth.setPreferredSize(d_txt);
      t_phone.setPreferredSize(d_txt);
      t_email.setPreferredSize(d_txt);

      /* <내부 패널 관련 설정> */
      p_emptyLeft.setBounds(0, 700, 50, 800);
      // p_emptyLeft.setPreferredSize(new Dimension(50, 800));
      p_emptyLeft.setBackground(Color.ORANGE);
      // --------------------------------------
      p_north.setPreferredSize(new Dimension(450, 400));
      p_north.setBackground(Color.ORANGE);
      // --------------------------------------
      p_center.setBounds(50, 500, 450, 350);
      // p_center.setPreferredSize(new Dimension(450, 300));
      p_center.setLayout(new BorderLayout());
      p_center.add(p_label, BorderLayout.WEST);
      p_center.add(p_txt);
      p_center.add(p_emptyRight, BorderLayout.EAST);
      // --------------------------------------
      p_label.setPreferredSize(new Dimension(100, 350));
      p_label.setBackground(Color.ORANGE);
      p_label.add(la_name);
      p_label.add(la_id);
      p_label.add(la_pw);
      p_label.add(la_nick);
      p_label.add(la_birth);
      p_label.add(la_phone);
      p_label.add(la_email);
      // --------------------------------------
      p_txt.setPreferredSize(new Dimension(200, 350));
      p_txt.setBackground(Color.ORANGE);
      p_txt.add(t_name);
      p_txt.add(t_id);
      p_txt.add(t_pw);
      p_txt.add(t_nick);
      p_txt.add(t_birth);
      p_txt.add(t_phone);
      p_txt.add(t_email);
      p_txt.add(bt_profile);
      bt_profile.setPreferredSize(new Dimension(200, 30));
      // --------------------------------------
      la_profileImage.setPreferredSize(new Dimension(120, 120));
      // --------------------------------------
      p_emptyRight.setPreferredSize(new Dimension(150, 350));
      p_emptyRight.setBackground(Color.ORANGE);// 아님
      p_emptyRight.add(p_eRNorth);
      p_emptyRight.add(bt_checkId);
      p_emptyRight.add(p_eRCenter);
      p_emptyRight.add(bt_checkNick);
      p_emptyRight.add(la_profileImage);
      bt_checkId.setBounds(450, 635, 80, 30);
      bt_checkNick.setPreferredSize(new Dimension(90, 30));
      // --------------------------------------
      p_eRNorth.setPreferredSize(new Dimension(150, 30));
      p_eRNorth.setBackground(Color.ORANGE);// 아님
      // --------------------------------------
      p_eRCenter.setPreferredSize(new Dimension(150, 30));
      p_eRCenter.setBackground(Color.ORANGE);
      // --------------------------------------

      p_south.setPreferredSize(new Dimension(450, 50));
      p_south.setBackground(Color.ORANGE);
      p_south.add(bt_regist);

      /* <p_container 관련 설정> */
      p_container.setPreferredSize(new Dimension(450, 800));
      p_container.setBackground(Color.ORANGE);
      p_container.setLayout(new BorderLayout());
      p_container.add(p_north, BorderLayout.NORTH);
      p_container.add(p_center);
      p_container.add(p_south, BorderLayout.SOUTH);

      /* <버튼에 이벤트 구현> */
      bt_checkId.addActionListener(new ActionListener() { //아이디 중복확인
         public void actionPerformed(ActionEvent e) {
            //System.out.println(t_id.getText());
            if(t_id.getText().length()==0) {
               JOptionPane.showMessageDialog(memberRegist, "아이디를 입력해주세요");
            }else {
               checkId=t_id.getText();
               checkId();
            }
         }
      });

      bt_checkNick.addActionListener(new ActionListener() { //닉네임 중복확인
         public void actionPerformed(ActionEvent e) {
            if(t_nick.getText().length()==0) {
               JOptionPane.showMessageDialog(memberRegist, "닉네임을 입력해주세요");
            }else {
               checkNick=t_nick.getText();
               checkNick();
            }         
         }
      });

      bt_regist.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             //아이디와 닉네임 중복체크를 했는지, 중복체크를 한 후 아이디와 닉네임을 바꿨는지 확인
             if (checkId == null || !checkId.equals(t_id.getText())) { //null인지 확인 먼저 안하면 오류남
                JOptionPane.showMessageDialog(memberRegist, "다시한번 아이디 중복체크를 해주세요");
                return;
             } else if (checkId == null || !checkNick.equals(t_nick.getText())) {
                JOptionPane.showMessageDialog(memberRegist, "다시한번 아이디 중복체크를 해주세요");
                return;
             }
             if (imgPath.length() == 0) {
                JOptionPane.showMessageDialog(memberRegist, "프로필 사진을 저장해주세요");
                return;
             } else {
                regist();
             }
          }
       });

      bt_profile.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            profile = new Profile(memberRegist);
         }
      });

      /* <Frame 관련 설정> */
      setLayout(new BorderLayout());
      add(p_emptyLeft);
      add(p_container, BorderLayout.EAST);
      
      //아이콘 이미지 세팅
      imageLoad = new ImageLoad();
      icon = imageLoad.getImage("speechBubbleIcon.png");
      icon.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
      setIconImage(icon);

      setBounds(200, 200, 500, 800);
      setResizable(false);
      setVisible(true);
      addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            validate();
         }
      });
   }
   
   public void getProfileImage(ImageIcon img) {
      la_profileImage.setIcon(img);
   }

   public void checkId() {
      JSONObject obj = new JSONObject();
      obj.put("Type", "checkId");
      obj.put("Id", t_id.getText());
      String order = obj.toString();
      ct.send(order);
   }

   public void checkNick() {
      JSONObject obj = new JSONObject();
      obj.put("Type", "checkNick");
      obj.put("Nick", t_nick.getText());
      String order = obj.toString();
      ct.send(order);
   }

   public void regist() {
      name = t_name.getText();
      id = t_id.getText();
      pw = t_pw.getText();
      nick = t_nick.getText();
      birth = t_birth.getText();
      phone = t_phone.getText();
      email = t_email.getText();      
      
    //다시한번 아이디 닉넴체크( 아이디 닉넴 중복체크하고 난뒤 다시 아이디나 닉네임 바꿨을시 확인)
      if(checkId.equals(t_id.getText())) {
      }else {
         JOptionPane.showMessageDialog(this, "다시한번 아이디 중복체크를 해주세요");
         return;      
      }
      if(checkNick.equals(t_nick.getText())) {
      }else {
         JOptionPane.showMessageDialog(this, "다시한번 닉네임 중복체크를 해주세요");
         return;         
      }

      // 텍스트창의 내용이 비어있는지 확인하기
      if (name.length() == 0) {
         JOptionPane.showMessageDialog(this, "본인의 이름을 입력해주세요");
         return;
      }
      if (id.length() == 0) {
         JOptionPane.showMessageDialog(this, "사용할 아이디를 입력해주세요");
         return;
      }
      if (pw.length() == 0) {
         JOptionPane.showMessageDialog(this, "사용할 비밀번호를 입력해주세요(10자 이내)");
         return;
      }
      if (nick.length() == 0) {
         JOptionPane.showMessageDialog(this, "사용할 닉네임을 입력해주세요");
         return;
      }
      if (birth.length() == 0) {
         JOptionPane.showMessageDialog(this, "본인의 생일을 입력해주세요");
         return;
      }
      if (phone.length() == 0) {
         JOptionPane.showMessageDialog(this, "본인의 핸드폰 번호를 입력해주세요");
         return;
      }
      if (email.length() == 0) {
         JOptionPane.showMessageDialog(this, "등록할 이메일 주소를 입력해주세요");
         return;
      }
      /*if(imgPath.length()==0) {
         JOptionPane.showMessageDialog(this, "프로필 사진을 저장해주세요");
         return;
      }*/

      JSONObject json = new JSONObject();
      json.put("Type", "join");
      json.put("Name", name);
      json.put("Id", id);
      json.put("Pw", pw);
      json.put("Nick", nick);
      json.put("Birth", birth);
      json.put("Phone", phone);
      json.put("Email", email);
      json.put("Img", imgPath);
      order = json.toString();

      ct.send(order);
      dispose();
   }   
}