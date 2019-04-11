package messenger.chat;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BackgroundColorList extends JFrame {
   JPanel p_wrapper;
   Color[] c = { new Color(225, 225, 128), new Color(128, 225, 128), new Color(128, 225, 225),
         new Color(255, 128, 128), new Color(225, 171, 87), new Color(192, 192, 192), new Color(140, 140, 225),
         new Color(193, 132, 255), new Color(255, 255, 255) };
   boolean flag = false; // 배경색 선택했는지

   RoomSetting setting;
   ChatMain chatMain;

   public BackgroundColorList(RoomSetting setting, ChatMain chatMain) {
      this.setting = setting;
      this.chatMain = chatMain;

      p_wrapper = new JPanel();

      for (int i = 0; i < c.length; i++) {
         setCircle(i); // 원만들기
      }

      // 배경색 설정
      p_wrapper.setBackground(Color.WHITE);
      p_wrapper.setBorder(BorderFactory.createLineBorder(Color.GRAY));

      // 부착
      add(p_wrapper);

      setSize(370, 370);
      setResizable(false);
      setUndecorated(true);
      setVisible(true);
   }

   // 예시 배경색을 담는 원 생성 함수
   public void setCircle(int i) {
      JPanel p_cwrapper = new JPanel();
      JPanel p_circle = new JPanel() {
         public void paint(Graphics g) {
            if (i == c.length - 1) {
               g.setColor(Color.DARK_GRAY);
               g.drawOval(0, 0, 92, 92);
            }
            g.setColor(c[i]);
            g.fillOval(1, 1, 90, 90);
         }
      };
      p_cwrapper.setBackground(Color.WHITE);
      p_cwrapper.setPreferredSize(new Dimension(110, 120));
      p_circle.setPreferredSize(new Dimension(95, 95));
      p_cwrapper.add(p_circle);
      p_wrapper.add(p_cwrapper);

      // 원을 눌렀을때 배경색 미리보기 지원
      p_circle.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            setting.color = c[i];
            chatMain.area.setBackground(c[i]);
            flag = true;
            dispose();
         }
      });
   }
}