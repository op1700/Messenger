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
	private String code;//친구리스트를 받아오면서 친구의 코드값을 저장
	

	ImageLoad imageload;


	public MemberListAdd(String name, String img, String code) {

		super(name);
		this.name = name;
		this.code = code;
		imageload = new ImageLoad();
		image = imageload.getImage(img);
		BufferedImage roundedImage = makeRoundCorner(image, 50); // 프로필 이미지의 둥근 모서리 만듬 50 일때 보름달
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

	public BufferedImage makeRoundCorner(Image image, int connerRadius) { // 둥근모서리 만드는 함수 //클래스로 뺄지 결정은 상의
		Image makeImage;
		makeImage = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		int x = makeImage.getWidth(null);
		int y = makeImage.getHeight(null);
		BufferedImage output = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);//

		Graphics2D g2 = output.createGraphics();

		g2.setComposite(AlphaComposite.Src);// 불투명 알파값 오브젝트(뭔 말인지 어려워서 모르겠음) 이게 시작이고 밑에꺼가 전송처 인듯?
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);// 이미지 계단현상 방지
		g2.setColor(Color.white);// 자른부분 흰색으로(아마 바탕색 바뀌면 바꿔야 할듯) 실험해보니 상관없음 바꿔도 무관
		g2.fill(new RoundRectangle2D.Float(0, 0, x, y, connerRadius, connerRadius));// 이미지 크기만큼 라운드 채움

		g2.setComposite(AlphaComposite.SrcAtop);
		g2.drawImage(makeImage, 0, 0, null);
		g2.dispose();

		return output;
	}
}
