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
   public JTextField t_search; // 검색하는 곳
   JLabel lb_search; // 검색아이콘 붙이는 라벨
   Image img_search; // 검색아이콘 이미지
   ImageLoad imageLoad;
   String name = "이름 검색"; // 검색창 안내문구
   MainFrame mainFrame;
   int p_num = 0; // 현재 보고 있는 페이지 (0,1)

   public SearchPanel(MainFrame mainFrame) {
      this.mainFrame = mainFrame;
      setLayout(new BorderLayout());

      imageLoad = new ImageLoad();
      img_search = imageLoad.getImage("search.png");
      img_search = img_search.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
      lb_search = new JLabel();
      lb_search.setIcon(new ImageIcon(img_search));

      t_search = new JTextField(name, 35) { // JTextField 테두리 없애기
         public void setBorder(Border border) {

         }
      };
      t_search.setFocusable(false);

      // 사이즈 조절
      lb_search.setBounds(20, 0, 80, 50);
      lb_search.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20)); // 아이콘 배치를 위해 padding(안쪽공백)
      t_search.setPreferredSize(new Dimension(410, 50));

      // 부착
      add(lb_search, BorderLayout.WEST);
      add(t_search);

      t_search.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            t_search.setText("");
            t_search.setFocusable(true);
         }
      });

      t_search.addKeyListener(new KeyAdapter() { // 친구 검색시 키 리스너
         public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            int result = 0;
            if (key == KeyEvent.VK_ENTER) {
               if (p_num == 0) { // 친구목록에서 친구 닉네임 검색
                  String nick = t_search.getText();
                  t_search.setText("");
                  if(nick.equals("")) {
                	  
                  }else {             	  
                	  for (int i = 0; i < mainFrame.friendList.size(); i++) {
                		  String[] friend = (String[]) mainFrame.friendList.get(i);
                		  if (nick.equals(friend[2])) {
                			  // 친구 하이라이트 처리해야함..어케하는지 모르겟음..
                			  result = 1;
                			  break;
                		  }
                	  }
                	  if (result == 1) { // 찾는 친구 있을시 친구에게 하이라이트줘서 바탕색 바뀌게 선택함
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
                		  JOptionPane.showMessageDialog(mainFrame, "찾는 친구가 없습니다.");
                	  }
                  }

               } else if (p_num == 1) { // 채팅목록에서 채팅방 이름 검색
                  String search = t_search.getText();
                  int num = -1;

                  for (int i = 0; i < mainFrame.chatListMain.panelList.size(); i++) {
                     String roomName = mainFrame.chatListMain.panelList.get(i).roomName;
                     if (search.equals(roomName)) { // 채팅방 이름과 일치하면 num에 저장
                        num = i;
                     }
                  }

                  if (num != -1) { // 찾는 채팅방이 있으면 해당 채팅방만 보이게
                     for (int a = 0; a < mainFrame.chatListMain.panelList.size(); a++) {
                        mainFrame.chatListMain.panelList.get(a).setVisible(false);
                     }
                     mainFrame.chatListMain.panelList.get(num).setVisible(true);
                  } else {
                     JOptionPane.showMessageDialog(mainFrame, "찾는 채팅방이 없습니다.");
                  }
               }
            }
         }
      });

   }

   // 페이지에 따른 검색 안내문자 설정
   public void setInfoText(int p_num) {
      this.p_num = p_num;
      if (p_num == 0) {
         name = "닉네임 검색";
      } else if (p_num == 1) {
         name = "채팅방 이름 검색";
      }
      t_search.setText(name);
   }

}