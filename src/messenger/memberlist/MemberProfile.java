package messenger.memberlist;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import messenger.mainframe.MainFrame;
import messenger.utils.ImageLoad;

public class MemberProfile extends JFrame {
   JPanel p_wrapper, p_north, p_center;
   JPanel p_title, p_img, p_name, p_nick, p_phone, p_email, p_birth;
   JLabel lb_x, lb_img, lb_name, lb_nick, lb_phone, lb_email, lb_birth;

   ImageLoad imageLoad;
   MainFrame mainFrame;
   String[] friendInfo;

   public MemberProfile(MainFrame mainFrame, String[] friendInfo) {
      this.mainFrame = mainFrame;
      this.friendInfo = friendInfo;

      p_wrapper = new JPanel();
      p_title = new JPanel();
      p_img = new JPanel();
      p_north = new JPanel();
      p_center = new JPanel();
      p_name = new JPanel();
      p_nick = new JPanel();
      p_phone = new JPanel();
      p_email = new JPanel();
      p_birth = new JPanel();

      lb_x = new JLabel("X", SwingConstants.RIGHT);
      lb_x.setForeground(Color.WHITE);

      lb_img = new JLabel();
      imageLoad = new ImageLoad();
      Image image = imageLoad.getImage("dog/" + friendInfo[4]);
      int size = 130;
      image = image.getScaledInstance(size, size, Image.SCALE_SMOOTH);
      BufferedImage circleBuffer = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2 = circleBuffer.createGraphics();
      g2.setComposite(AlphaComposite.Src);
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.fill(new RoundRectangle2D.Float(0, 0, size, size, 50, 50));
      g2.setComposite(AlphaComposite.SrcAtop);
      g2.drawImage(image, 0, 0, size, size, null);
      g2.dispose();
      lb_img.setIcon(new ImageIcon(circleBuffer));

      lb_name = new JLabel("이름 : " + friendInfo[1]);
      lb_nick = new JLabel("닉네임 : " + friendInfo[2]);
      lb_phone = new JLabel("전화번호 : " + friendInfo[5]);
      lb_email = new JLabel("이메일 : " + friendInfo[6]);
      lb_birth = new JLabel("생일 : " + friendInfo[7]);

      // 폰트 설정
      lb_x.setFont(new Font(mainFrame.getFontString(), Font.BOLD, 18));
      lb_name.setFont(new Font(mainFrame.getFontString(), Font.BOLD, 14));
      lb_nick.setFont(new Font(mainFrame.getFontString(), Font.BOLD, 14));
      lb_phone.setFont(new Font(mainFrame.getFontString(), Font.BOLD, 14));
      lb_email.setFont(new Font(mainFrame.getFontString(), Font.BOLD, 14));
      lb_birth.setFont(new Font(mainFrame.getFontString(), Font.BOLD, 14));

      // 사이즈 조절
      lb_x.setPreferredSize(new Dimension(260, 30));
      p_title.setPreferredSize(new Dimension(300, 30));
      p_img.setPreferredSize(new Dimension(300, 150));
      p_north.setPreferredSize(new Dimension(300, 180));
      p_center.setPreferredSize(new Dimension(300, 350));
      p_wrapper.setPreferredSize(new Dimension(300, 530));
      lb_name.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
      lb_nick.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
      lb_phone.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
      lb_email.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
      lb_birth.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

      // 배경색 설정
      p_title.setBackground(mainFrame.getColor());
      p_img.setBackground(mainFrame.getColor());
      p_center.setBackground(mainFrame.getColor());
      p_name.setBackground(Color.WHITE);
      p_nick.setBackground(Color.WHITE);
      p_phone.setBackground(Color.WHITE);
      p_email.setBackground(Color.WHITE);
      p_birth.setBackground(Color.WHITE);
      p_wrapper.setBorder(new LineBorder(mainFrame.getColor(), 3));
      p_north.setBorder(new LineBorder(mainFrame.getColor(), 3));
      p_name.setBorder(new LineBorder(mainFrame.getColor(), 3));
      p_nick.setBorder(new LineBorder(mainFrame.getColor(), 3));
      p_phone.setBorder(new LineBorder(mainFrame.getColor(), 3));
      p_email.setBorder(new LineBorder(mainFrame.getColor(), 3));
      p_birth.setBorder(new LineBorder(mainFrame.getColor(), 3));

      // 레이아웃 설정
      p_north.setLayout(new BorderLayout());
      p_center.setLayout(new GridLayout(5, 1));
      p_name.setLayout(new GridLayout());
      p_nick.setLayout(new GridLayout());
      p_phone.setLayout(new GridLayout());
      p_email.setLayout(new GridLayout());
      p_birth.setLayout(new GridLayout());
      p_wrapper.setLayout(new BorderLayout());

      // 부착
      p_title.add(lb_x);
      p_img.add(lb_img);
      p_north.add(p_title, BorderLayout.NORTH);
      p_north.add(p_img);

      p_name.add(lb_name);
      p_nick.add(lb_nick);
      p_phone.add(lb_phone);
      p_email.add(lb_email);
      p_birth.add(lb_birth);
      p_center.add(p_name);
      p_center.add(p_nick);
      p_center.add(p_phone);
      p_center.add(p_email);
      p_center.add(p_birth);

      p_wrapper.add(p_north, BorderLayout.NORTH);
      p_wrapper.add(p_center);
      add(p_wrapper);

      // 'x' 클릭시 프로필 창을 닫는다
      lb_x.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            dispose();
         }
      });

      setSize(300, 530);
      setResizable(false);
      setUndecorated(true);
      setVisible(true);
   }
}