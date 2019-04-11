package messenger.memberlist;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.json.simple.JSONObject;

import messenger.chat.ChatMain;
import messenger.mainframe.MainFrame;

public class MemberListOption extends JFrame {
   JPanel p_openChat, p_fixChat, p_exitChat;
   JLabel lb_openChat, lb_profile, lb_del;
   Font font; // 미설정시 기본폰트
   String[] friendInfo; //선택한 친구 정보
   MemberProfile memberProfile;
   MainFrame mainFrame;

   public MemberListOption(MainFrame mainFrame, String[] friendInfo) {
      this.mainFrame = mainFrame;
      this.friendInfo = friendInfo;
      p_openChat = new JPanel();
      p_fixChat = new JPanel();
      p_exitChat = new JPanel(); 

      lb_openChat = new JLabel("채팅 시작");
      lb_profile = new JLabel("친구 상세보기");
      lb_del = new JLabel("친구 삭제");

      // 폰트 설정
      font = new Font(mainFrame.getFontString(), Font.PLAIN, 12);
      lb_openChat.setFont(font);
      lb_profile.setFont(font);
      lb_del.setFont(font);

      // 사이즈 조절
      Dimension d = new Dimension(100, 25);
      lb_openChat.setPreferredSize(d);
      lb_openChat.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
      lb_profile.setPreferredSize(d);
      lb_profile.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
      lb_del.setPreferredSize(d);
      lb_del.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

      // 배경색 설정
      p_openChat.setBackground(Color.WHITE);
      p_openChat.setBorder(BorderFactory.createLineBorder(Color.GRAY));
      p_fixChat.setBackground(Color.WHITE);
      p_fixChat.setBorder(BorderFactory.createLineBorder(Color.GRAY));
      p_exitChat.setBackground(Color.WHITE);
      p_exitChat.setBorder(BorderFactory.createLineBorder(Color.GRAY));

      // 레이아웃 설정
      setLayout(new GridLayout(3, 1));

      // 부착
      p_openChat.add(lb_openChat);
      p_fixChat.add(lb_profile);
      p_exitChat.add(lb_del);

      add(p_openChat);
      add(p_fixChat);
      add(p_exitChat);

      // 채팅 시작
      lb_openChat.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            //바꿔야함
            //서버측에서 채팅방이 있는지 없는지 확인해야함...
            JSONObject obj = new JSONObject();
            obj.put("Type", "Start");
            obj.put("MyCode", mainFrame.myProfile[0]);
            obj.put("FriendCode", friendInfo[0]);
            mainFrame.ct.send(obj.toString());
            dispose();
         }
      });

      // 친구 프로필 보기
      lb_profile.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            // JOptionPane.showMessageDialog(mainFrame, "친구 프로필 보기");
            memberProfile = new MemberProfile(mainFrame, friendInfo);
            int x = (int) mainFrame.getX();
            int y = (int) mainFrame.getY();
            memberProfile.setBounds(x - 300, y + 150, 300, 500);// 옵션화면 위치,크기 정해주기
            dispose();
         }
      });

      // 친구 삭제
      lb_del.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            if (JOptionPane.showConfirmDialog(mainFrame, "정말 삭제하시겠습니까?") == 0) {
               String friendCode = friendInfo[0];
               JSONObject obj = new JSONObject();
               obj.put("Type", "frienddelete");
               obj.put("MyCode", mainFrame.myProfile[0]);
               obj.put("FriendCode", friendCode);
               
               mainFrame.ct.send(obj.toString());
            } else {

            }

         }
      });

      setSize(100, 75);
      setUndecorated(true);
      setVisible(true);
   }
}