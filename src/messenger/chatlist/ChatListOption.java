package messenger.chatlist;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.simple.JSONObject;

import messenger.mainframe.MainFrame;

public class ChatListOption extends JFrame {
   JPanel p_openChat, p_exitChat;
   JLabel lb_openChat, lb_exitChat;
   Font font; // �̼����� �⺻��Ʈ
   
   public ChatListOption(MainFrame mainFrame, String chat_code, MouseEvent e) {
      p_openChat = new JPanel(); // ä�ù� ���� �г�
      p_exitChat = new JPanel(); // ä�ù� ������ �г�
      lb_openChat = new JLabel("ä�ù� ����");
      lb_exitChat = new JLabel("ä�ù� ������");

      // ��Ʈ ����
      font = new Font(mainFrame.getFontString(), Font.PLAIN, 12);
      lb_openChat.setFont(font);
      lb_exitChat.setFont(font);

      // ������ ����
      Dimension d = new Dimension(80, 30);
      lb_openChat.setPreferredSize(d);
      lb_openChat.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
      lb_exitChat.setPreferredSize(d);
      lb_exitChat.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));

      // ���� ����
      p_openChat.setBackground(Color.WHITE);
      p_openChat.setBorder(BorderFactory.createLineBorder(Color.GRAY));

      p_exitChat.setBackground(Color.WHITE);
      p_exitChat.setBorder(BorderFactory.createLineBorder(Color.GRAY));

      // ���̾ƿ� ����
      setLayout(new GridLayout(2, 1));

      // ����
      p_openChat.add(lb_openChat);
      p_exitChat.add(lb_exitChat);

      add(p_openChat);
      add(p_exitChat);

      // ä�ù� ���� �󺧰� ������ ����
      lb_openChat.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            JSONObject obj = new JSONObject();
            obj.put("Type", "chatupdate");
            obj.put("Code", chat_code); 
            mainFrame.ct.send(obj.toString());
            dispose();
         }
      });

      // ä�ù� ������ �󺧰� ������ ����
      lb_exitChat.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            JSONObject orderObj = new JSONObject();
            orderObj.put("Type", "chat_del");
            orderObj.put("UserCode", mainFrame.myProfile[0]);
            orderObj.put("ChatCode", chat_code);
            mainFrame.ct.send(orderObj.toString());
            dispose();
         }
      });

      setSize(80, 60);
      setUndecorated(true);
      setVisible(true);
   }
}