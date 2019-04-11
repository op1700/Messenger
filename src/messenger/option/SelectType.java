package messenger.option;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import messenger.mainframe.MainFrame;

public class SelectType extends JFrame {
   JPanel p_wrapper, p_title, p_content;
   JLabel lb_title;
   JTextField t_type;
   JButton bt_add;
   MainFrame mainFrame;
   AddFriend addFriend;

   Font font; // �̼����� �⺻ ��Ʈ

   public SelectType(MainFrame mainFrame, AddFriend addFriend) {
      this.addFriend = addFriend;
      p_wrapper = new JPanel(); // ��ü �г�
      p_title = new JPanel(); // Ÿ��Ʋ �г�
      p_content = new JPanel(); // �Է��ʵ�� ��ư�� ���� �г�

      lb_title = new JLabel("�߰��� ģ���� ���� ����");
      lb_title.setForeground(Color.WHITE);
      t_type = new JTextField("������ �Է��ϼ���");
      t_type.setFocusable(false);
      bt_add = new JButton("Ȯ��");

      // ��Ʈ ����
      font = new Font(mainFrame.getFontString(), Font.PLAIN, 14);
      lb_title.setFont(new Font(mainFrame.getFontString(), Font.BOLD, 16));
      t_type.setFont(font);
      bt_add.setFont(font);

      // ������ ����
      p_title.setPreferredSize(new Dimension(350, 40));
      p_title.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
      t_type.setPreferredSize(new Dimension(260, 50));
      bt_add.setPreferredSize(new Dimension(70, 40));
      p_content.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

      // ���� ����
      p_title.setBackground(mainFrame.getColor());
      p_content.setBackground(Color.WHITE);
      bt_add.setBackground(Color.YELLOW);
      p_wrapper.setBorder(BorderFactory.createLineBorder(Color.GRAY));

      // ���̾ƿ� ����
      p_wrapper.setLayout(new BorderLayout());

      // ����
      p_title.add(lb_title);
      p_content.add(t_type);
      p_content.add(bt_add, BorderLayout.EAST);
      p_wrapper.add(p_title, BorderLayout.NORTH);
      p_wrapper.add(p_content);
      add(p_wrapper);

      // �Է�â�� Ŭ������ �� �ȳ������� �Ⱥ��̰� ����
      t_type.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            t_type.setText("");
            t_type.setFocusable(true);
         }
      });

      // Ȯ�� ��ư ������ �� ���� ����
      bt_add.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            addFriend.setUserType(t_type.getText());
            addFriend.sendOrder();
            dispose();
            JOptionPane.showMessageDialog(addFriend, "ģ�� �߰� �Ϸ�");
            addFriend.dispose();
         }
      });

      setSize(350, 180);
      setResizable(false);
      setUndecorated(true);
      setVisible(true);
   }
}