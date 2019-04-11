package messenger.mainframe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.Position;
import javax.swing.tree.TreePath;

import messenger.utils.ImageLoad;

public class SearchPanel extends JPanel {
   public JTextField t_search; // �˻��ϴ� ��
   JLabel lb_search; // �˻������� ���̴� ��
   Image img_search; // �˻������� �̹���
   ImageLoad imageLoad;
   String name = "�̸� �˻�"; // �˻�â �ȳ�����
   MainFrame mainFrame;
   int p_num = 0; // ���� ���� �ִ� ������ (0,1)

   public SearchPanel(MainFrame mainFrame) {
      this.mainFrame = mainFrame;
      setLayout(new BorderLayout());

      imageLoad = new ImageLoad();
      img_search = imageLoad.getImage("search.png");
      img_search = img_search.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
      lb_search = new JLabel();
      lb_search.setIcon(new ImageIcon(img_search));

      t_search = new JTextField(name, 35) { // JTextField �׵θ� ���ֱ�
         public void setBorder(Border border) {

         }
      };
      t_search.setFocusable(false);

      // ������ ����
      lb_search.setBounds(20, 0, 80, 50);
      lb_search.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20)); // ������ ��ġ�� ���� padding(���ʰ���)
      t_search.setPreferredSize(new Dimension(410, 50));

      // ����
      add(lb_search, BorderLayout.WEST);
      add(t_search);

      t_search.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            t_search.setText("");
            t_search.setFocusable(true);
         }
      });

      t_search.addKeyListener(new KeyAdapter() { // ģ�� �˻��� Ű ������
         public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            int result = 0;
            if (key == KeyEvent.VK_ENTER) {
               if (p_num == 0) { // ģ����Ͽ��� ģ�� �г��� �˻�
                  String nick = t_search.getText();
                  t_search.setText("");
                  if(nick.equals("")) {
                	  
                  }else {             	  
                	  for (int i = 0; i < mainFrame.friendList.size(); i++) {
                		  String[] friend = (String[]) mainFrame.friendList.get(i);
                		  if (nick.equals(friend[2])) {
                			  // ģ�� ���̶���Ʈ ó���ؾ���..�����ϴ��� �𸣰���..
                			  result = 1;
                			  break;
                		  }
                	  }
                	  if (result == 1) { // ã�� ģ�� ������ ģ������ ���̶���Ʈ�༭ ������ �ٲ�� ������
                		  TreePath path = null;
                		  int row = (path == null ? 0 : mainFrame.memberListMain.tree.getRowForPath(path));
                		  path = mainFrame.memberListMain.tree.getNextMatch(nick, row, Position.Bias.Forward);
                		  if (path == null) {
                			  return;
                		  }
                		  mainFrame.memberListMain.tree.scrollPathToVisible(path);
                		  mainFrame.memberListMain.tree.setSelectionPath(path);
                		  result = 0;
                	  } else {
                		  JOptionPane.showMessageDialog(mainFrame, "ã�� ģ���� �����ϴ�.");
                	  }
                  }

               } else if (p_num == 1) { // ä�ø�Ͽ��� ä�ù� �̸� �˻�
                  String search = t_search.getText();
                  int num = -1;

                  for (int i = 0; i < mainFrame.chatListMain.panelList.size(); i++) {
                     String roomName = mainFrame.chatListMain.panelList.get(i).roomName;
                     if (search.equals(roomName)) { // ä�ù� �̸��� ��ġ�ϸ� num�� ����
                        num = i;
                     }
                  }

                  if (num != -1) { // ã�� ä�ù��� ������ �ش� ä�ù游 ���̰�
                     for (int a = 0; a < mainFrame.chatListMain.panelList.size(); a++) {
                        mainFrame.chatListMain.panelList.get(a).setVisible(false);
                     }
                     mainFrame.chatListMain.panelList.get(num).setVisible(true);
                  } else {
                     JOptionPane.showMessageDialog(mainFrame, "ã�� ä�ù��� �����ϴ�.");
                  }
               }
            }
         }
      });

   }

   // �������� ���� �˻� �ȳ����� ����
   public void setInfoText(int p_num) {
      this.p_num = p_num;
      if (p_num == 0) {
         name = "�г��� �˻�";
      } else if (p_num == 1) {
         name = "ä�ù� �̸� �˻�";
      }
      t_search.setText(name);
   }

}