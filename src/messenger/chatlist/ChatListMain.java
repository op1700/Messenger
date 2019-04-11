package messenger.chatlist;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.json.simple.JSONObject;

import messenger.mainframe.MainFrame;
import messenger.utils.ImageLoad;

public class ChatListMain extends JPanel {
   public JFrame plusBt;
   public JPanel p_plus;
   JLabel lb_plus;
   Image img_plus;
   ImageLoad imageLoad;
   // public ChatListPanel[] chatArray = new ChatListPanel[10]; // ä�ø���� ��� �迭
   MainFrame mainFrame;
   ChatListOption chatListOption;
   JScrollPane scroll;
   public ArrayList chatList;// ä�ø�� ������ ����Ʈ
   public ArrayList<ChatListPanel> panelList = new ArrayList<ChatListPanel>();

   public ChatListMain(MainFrame mainFrame) {
      this.mainFrame = mainFrame;
      scroll = new JScrollPane(this);
      // ä�ø�� ����

      addComponentListener(new ComponentAdapter() {
         public void componentResized(ComponentEvent e) {
            Dimension d = e.getComponent().getSize();
            int width = (int) d.getWidth();
            for (int i = 0; i < panelList.size(); i++) {
               ((Component) panelList.get(i)).setPreferredSize(new Dimension(width, 70));
            }
         }
      });

      setBackground(mainFrame.getColor());
      setPreferredSize(new Dimension(500, 650));
      setVisible(false);
   }

   public void createChatList(String roomName, String currentLog, String currentTime, String img1, String room_code) {
      ChatListPanel list = new ChatListPanel(mainFrame, roomName, img1, currentLog, currentTime, room_code);
      add(list);
      panelList.add(list);
      list.addMouseListener(new MouseAdapter() {
         // ä��â Ŭ���� �̺�Ʈ ����
         public void mouseClicked(MouseEvent e) {
            listMouseEvent(e);
         }
      });
   }

   public void createChatList(String roomName, String currentLog, String currentTime, String img1, String img2,
         int count, String room_code) {
      ChatListPanel list = new ChatListPanel(mainFrame, roomName, img1, img2, count, currentLog, currentTime,
            room_code);
      add(list);
      panelList.add(list);
      list.addMouseListener(new MouseAdapter() {
         // ä��â Ŭ���� �̺�Ʈ ����
         public void mouseClicked(MouseEvent e) {
            listMouseEvent(e);
         }
      });
   }

   public void createChatList(String roomName, String currentLog, String currentTime, String img1, String img2,
         String img3, int count, String room_code) {
      ChatListPanel list = new ChatListPanel(mainFrame, roomName, img1, img2, img3, count, currentLog, currentTime,
            room_code);
      add(list);
      panelList.add(list);
      list.addMouseListener(new MouseAdapter() {
         // ä��â Ŭ���� �̺�Ʈ ����
         public void mouseClicked(MouseEvent e) {
            listMouseEvent(e);
         }
      });
   }

   public void createChatList(String roomName, String currentLog, String currentTime, String img1, String img2,
         String img3, String img4, int count, String room_code) {
      ChatListPanel list = new ChatListPanel(mainFrame, roomName, img1, img2, img3, img4, count, currentLog,
            currentTime, room_code);
      add(list);
      panelList.add(list);
      // String chat_code=list.room_code;
      list.addMouseListener(new MouseAdapter() {
         // ä��â Ŭ���� �̺�Ʈ ����
         public void mouseClicked(MouseEvent e) {

            listMouseEvent(e);
         }
      });
   }

   public void listMouseEvent(MouseEvent e) {
      if (chatListOption != null) {
         chatListOption.dispose();
      }
      ChatListPanel chatPanel = (ChatListPanel) e.getComponent();
      String chat_code = chatPanel.room_code.toString();

      if (e.getClickCount() > 1) { // ���� Ŭ�� ���� �� ä�ù� ����
         // ä�ù� �ڵ� �־������
         JSONObject obj = new JSONObject();
         obj.put("Type", "chatupdate");
         obj.put("Code", chat_code); // ä�ù� ���ڵ� ������
         mainFrame.ct.send(obj.toString());

      }
      if (SwingUtilities.isRightMouseButton(e)) { // ���콺 ��Ŭ�� ���� ��
         chatListOption = new ChatListOption(mainFrame, chat_code, e);
         int x = (int) e.getLocationOnScreen().getX(); // ���� x�� ������
         int y = (int) e.getLocationOnScreen().getY();// ���� y�� ������
         chatListOption.setBounds(x + 5, y + 5, 100, 90);// �ɼ�ȭ�� ��ġ,ũ�� �����ֱ�
      }
   }

}