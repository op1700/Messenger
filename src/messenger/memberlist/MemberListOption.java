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
   Font font; // �̼����� �⺻��Ʈ
   String[] friendInfo; //������ ģ�� ����
   MemberProfile memberProfile;
   MainFrame mainFrame;

   public MemberListOption(MainFrame mainFrame, String[] friendInfo) {
      this.mainFrame = mainFrame;
      this.friendInfo = friendInfo;
      p_openChat = new JPanel();
      p_fixChat = new JPanel();
      p_exitChat = new JPanel(); 

      lb_openChat = new JLabel("ä�� ����");
      lb_profile = new JLabel("ģ�� �󼼺���");
      lb_del = new JLabel("ģ�� ����");

      // ��Ʈ ����
      font = new Font(mainFrame.getFontString(), Font.PLAIN, 12);
      lb_openChat.setFont(font);
      lb_profile.setFont(font);
      lb_del.setFont(font);

      // ������ ����
      Dimension d = new Dimension(100, 25);
      lb_openChat.setPreferredSize(d);
      lb_openChat.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
      lb_profile.setPreferredSize(d);
      lb_profile.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
      lb_del.setPreferredSize(d);
      lb_del.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

      // ���� ����
      p_openChat.setBackground(Color.WHITE);
      p_openChat.setBorder(BorderFactory.createLineBorder(Color.GRAY));
      p_fixChat.setBackground(Color.WHITE);
      p_fixChat.setBorder(BorderFactory.createLineBorder(Color.GRAY));
      p_exitChat.setBackground(Color.WHITE);
      p_exitChat.setBorder(BorderFactory.createLineBorder(Color.GRAY));

      // ���̾ƿ� ����
      setLayout(new GridLayout(3, 1));

      // ����
      p_openChat.add(lb_openChat);
      p_fixChat.add(lb_profile);
      p_exitChat.add(lb_del);

      add(p_openChat);
      add(p_fixChat);
      add(p_exitChat);

      // ä�� ����
      lb_openChat.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            //�ٲ����
            //���������� ä�ù��� �ִ��� ������ Ȯ���ؾ���...
            JSONObject obj = new JSONObject();
            obj.put("Type", "Start");
            obj.put("MyCode", mainFrame.myProfile[0]);
            obj.put("FriendCode", friendInfo[0]);
            mainFrame.ct.send(obj.toString());
            dispose();
         }
      });

      // ģ�� ������ ����
      lb_profile.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            // JOptionPane.showMessageDialog(mainFrame, "ģ�� ������ ����");
            memberProfile = new MemberProfile(mainFrame, friendInfo);
            int x = (int) mainFrame.getX();
            int y = (int) mainFrame.getY();
            memberProfile.setBounds(x - 300, y + 150, 300, 500);// �ɼ�ȭ�� ��ġ,ũ�� �����ֱ�
            dispose();
         }
      });

      // ģ�� ����
      lb_del.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            if (JOptionPane.showConfirmDialog(mainFrame, "���� �����Ͻðڽ��ϱ�?") == 0) {
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