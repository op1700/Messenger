package messenger.memberlist;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.tree.DefaultMutableTreeNode;

import messenger.utils.ImageLoad;

public class MemberListAdd extends DefaultMutableTreeNode {
	private String name;
	private Image image, setImage;
	private String code;//ģ������Ʈ�� �޾ƿ��鼭 ģ���� �ڵ尪�� ����
	

	ImageLoad imageload;


	public MemberListAdd(String name, String img, String code) {

		super(name);
		this.name = name;
		this.code = code;
		imageload = new ImageLoad();
		image = imageload.getImage(img);
		BufferedImage roundedImage = makeRoundCorner(image, 50); // ������ �̹����� �ձ� �𼭸� ���� 50 �϶� ������
		setImage = roundedImage;

	}

	public MemberListAdd(String name) {
		super(name);
		this.name = name;
	}

	// ---------------------------------------------------------------------------------------------
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Image getImg() {
		return setImage;
	}

	public void setImg(Image img) {
		this.image = img;
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BufferedImage makeRoundCorner(Image image, int connerRadius) { // �ձٸ𼭸� ����� �Լ� //Ŭ������ ���� ������ ����
		Image makeImage;
		makeImage = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		int x = makeImage.getWidth(null);
		int y = makeImage.getHeight(null);
		BufferedImage output = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);//

		Graphics2D g2 = output.createGraphics();

		g2.setComposite(AlphaComposite.Src);// ������ ���İ� ������Ʈ(�� ������ ������� �𸣰���) �̰� �����̰� �ؿ����� ����ó �ε�?
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);// �̹��� ������� ����
		g2.setColor(Color.white);// �ڸ��κ� �������(�Ƹ� ������ �ٲ�� �ٲ�� �ҵ�) �����غ��� ������� �ٲ㵵 ����
		g2.fill(new RoundRectangle2D.Float(0, 0, x, y, connerRadius, connerRadius));// �̹��� ũ�⸸ŭ ���� ä��

		g2.setComposite(AlphaComposite.SrcAtop);
		g2.drawImage(makeImage, 0, 0, null);
		g2.dispose();

		return output;
	}
}
