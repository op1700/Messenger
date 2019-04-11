package messenger.login;

import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import messenger.utils.ImageLoad;

public class LoginMain extends JFrame {

	MemberLogin login;
	ImageLoad imageLoad;
	Image icon;

	// ���α׷��� ���� �� ����Ŭ �����ͺ��̽��� ��� ���ٵǾ� �Ѵ�
	public LoginMain() {
		super("����Ʈ��");
		login = new MemberLogin(this);
		add(login);

		// ������ â ����
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		// ������ �̹��� ����
		imageLoad = new ImageLoad();
		icon = imageLoad.getImage("speechBubbleIcon.png");
		icon.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		setIconImage(icon);

		setBounds(700, 200, 500, 800);
		setResizable(false);
		setVisible(true);
	}

	public static void main(String[] args) {
		new LoginMain();
	}
}