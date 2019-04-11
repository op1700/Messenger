
/*���� �����ӿ� ������ result�� �м���*/
package messenger.utils;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import messenger.chat.ChatMain;
import messenger.client.ClientThread;
import messenger.login.LoginMain;
import messenger.login.MemberLogin;
import messenger.mainframe.MainFrame;
import messenger.option.AddChat;

public class ParserResult {
	public String[] myProfile = new String[8];
	public String[][] chatArray;
	ChatMain chatMain;
	ClientThread ct;
	LoginMain loginMain;
	MemberLogin memberLogin;
	MainFrame mainFrame;
	Master master;
	ArrayList chatList = new ArrayList();
	ArrayList arrayChatdList = new ArrayList();
	// �Ƹ� �����ؾ��ϴ� ���Ŭ������ ���� �ֵ� ���� Ŭ������ �����ҵ�
	// MainFrame �ҷ����� ���� �˾ƾ���
	public ArrayList arrayChatdList1 = new ArrayList();

	public ArrayList<ChatMain> totalChatRoom = new ArrayList<ChatMain>();
	String ccc_code;

	public AddChat addChat;

	public ParserResult(ClientThread ct, LoginMain loginMain) {
		this.ct = ct;
		this.loginMain = loginMain;

	}

	public void parse(String result) {
		JSONParser parser = new JSONParser();
		JSONArray array = new JSONArray();
		Object obj = null;
		Object obj2 = null;
		if (result.equals("nodata")) {
			JOptionPane.showMessageDialog(null, "���̵� ����� Ʋ���ϴ�");
		} else if (result.equals("newB")) {
			// System.out.println("newb ����");
			mainFrame = new MainFrame(ct, myProfile);
		} else {

			try {
				obj = parser.parse(result);// ��ȯ���� Object�̱⶧���� ������ jsonobject�� ����ȯ�ؾ���
				// System.out.println("obj : " + obj);

				if (array.getClass() == obj.getClass()) { // json �迭 �������� ������
					JSONObject orderObj = new JSONObject();
					array = (JSONArray) obj;
					ArrayList arrayFriendList = new ArrayList();

					JSONObject findType = (JSONObject) array.get(0);// ���� Ÿ������ Ȯ���ϱ� ����
					String type = (String) findType.get("Type");
					// System.out.println(type);
					if (type.equals("friendresult")) { // ģ�� ��ϰ�
						for (int i = 0; i < array.size(); i++) {
							JSONObject selectObj = (JSONObject) array.get(i);
							String[] friendList = new String[selectObj.size() - 1];// ģ�������� ������� �迭 -1 ������ type �� ���� ����
							// ó�� �ѹ��� ���������� ������ ����
							if (mainFrame == null) {
								mainFrame = new MainFrame(ct, myProfile);

								friendList[0] = (String) selectObj.get("Code");
								friendList[1] = (String) selectObj.get("Name");
								friendList[2] = (String) selectObj.get("Nick");
								friendList[3] = (String) selectObj.get("Relation");
								friendList[4] = (String) selectObj.get("Img");
								friendList[5] = (String) selectObj.get("Phone");
								friendList[6] = (String) selectObj.get("Email");
								friendList[7] = (String) selectObj.get("Birth");
								arrayFriendList.add(friendList);
							} else {

								friendList[0] = (String) selectObj.get("Code");
								friendList[1] = (String) selectObj.get("Name");
								friendList[2] = (String) selectObj.get("Nick");
								friendList[3] = (String) selectObj.get("Relation");
								friendList[4] = (String) selectObj.get("Img");
								friendList[5] = (String) selectObj.get("Phone");
								friendList[6] = (String) selectObj.get("Email");
								friendList[7] = (String) selectObj.get("Birth");

								arrayFriendList.add(friendList);// ������� ���� ��� ģ�������� �迭 ��� ��

							}
						}
						// ���������ӿ� ģ�� ����Ʈ ����
						mainFrame.friendList = arrayFriendList;
						if (master != null) {
							master.removeAllNode();// ���� �پ��ִ� ��� ��� ����
							master.makeFriendList();// ���ι��� ģ�� ����Ʈ Ʈ���� ���̱�
							master.mainFrame.memberListMain.tree.updateUI();// ȭ�� ���÷���
							master.expandAllNode(master.mainFrame.memberListMain.tree);// ��� ��� �������
						} else {
							master = new Master(ct, mainFrame, this);// ��� ��ü�� �����ϱ� ���� ���� Ŭ����, ó�� ����������
						}
						array.clear();
					} else if (type.equals("chatlistresult")) {
						// System.out.println("ê����Ʈ ������");
						for (int i = 0; i < array.size(); i++) {
							JSONObject codeObj = new JSONObject();
							JSONObject selectObj = (JSONObject) array.get(i);
							String chatCode = (String) selectObj.get("ChatCode");
							String chatName = (String) selectObj.get("ChatName");
							// ���⼭ ������ ê�ڵ带 �ٽ� ������ ������ ä�ù濡 ������� Ȯ���ϰ� �̹����� �����;���
							codeObj.put("Type", "chatmember");
							codeObj.put("ChatCode", chatCode);
							codeObj.put("MyCode", myProfile[0]);
							codeObj.put("ChatName", chatName);

							ct.send(codeObj.toString());
						}
					} else if (type.equals("chatmemberresult")) {
						// ���� ������ �� �����ؾ���...
						// System.out.println("size : " + array.size());
						String[] chatInfo = new String[4 + array.size()];// 7
						JSONObject selectObj = (JSONObject) array.get(0);
						chatInfo[0] = (String) selectObj.get("ChatCode");
						chatInfo[1] = (String) selectObj.get("ChatName");
						chatInfo[2] = (String) selectObj.get("ChatLog");
						chatInfo[3] = (String) selectObj.get("ChatTime");
						chatInfo[4] = (String) selectObj.get("UserImg");
						// ���⼭ ä�÷α� �������ð��̶� ������ ��ȭ �����;���
						for (int i = 1; i < array.size(); i++) {// 5
							JSONObject selectObj2 = (JSONObject) array.get(i);
							chatInfo[i + 4] = (String) selectObj2.get("UserImg");
						}
						chatList.add(chatInfo);
						// ä�ù� ��� ���̱�
						// if ������ �̹��� � ������ �Ǻ��ؾߵ�
						// ó�� �������� �α׶� ������ �������� �α׶� ������ �Ǵ��ؾ���

						if (chatInfo.length == 5) {
							//
							mainFrame.chatListMain.createChatList(chatInfo[1], chatInfo[2], chatInfo[3], chatInfo[4],
									chatInfo[0]);// 1:1ä��
							// 2��
							// �ֱٷα�,
							// 3
							// �ð�
						} else if (chatInfo.length == 6) {
							mainFrame.chatListMain.createChatList(chatInfo[1], chatInfo[2], chatInfo[3], chatInfo[4],
									chatInfo[5], chatInfo.length - 3, chatInfo[0]);// 3��ä��, ������ ä�ù�����
						} else if (chatInfo.length == 7) {
							mainFrame.chatListMain.createChatList(chatInfo[1], chatInfo[2], chatInfo[3], chatInfo[4],
									chatInfo[5], chatInfo[6], chatInfo.length - 3, chatInfo[0]);// 4��ä��
						} else if (chatInfo.length > 7) {
							mainFrame.chatListMain.createChatList(chatInfo[1], chatInfo[2], chatInfo[3], chatInfo[4],
									chatInfo[5], chatInfo[6], chatInfo[7], chatInfo.length - 3, chatInfo[0]);// 5�̻� ä��
						}

						mainFrame.chatListMain.updateUI();
						master.chatList = chatList;// �����Ϳ� ê��� �Ѱ���
						mainFrame.chatListMain.chatList = chatList;// ê����Ʈ ���ο� ê����Ʈ �Ѱ���
						mainFrame.chatList = chatList;

					} else if (type.equals("chatupdateresult")) { // ä�÷α׵�
						for (int i = 0; i < array.size(); i++) {
							JSONObject selectObj = (JSONObject) array.get(i);

							String[] chatArray = new String[selectObj.size() - 1];// type�� ������ ä�� �α׵�
							chatArray[0] = (String) selectObj.get("UserCode");
							chatArray[1] = (String) selectObj.get("ChatLog");
							chatArray[2] = (String) selectObj.get("Time");
							chatArray[3] = (String) selectObj.get("ChatName");
							chatArray[4] = (String) selectObj.get("ChatCode");

							// ChatMain - Array ������
							// }
							arrayChatdList.add(chatArray);
						}
						// �����ڵ带 ������ ������ ����� �г����� �����ؼ� "�ȳ翡 �־����.."
						String[] chat_list = (String[]) arrayChatdList.get(0);
						// System.out.println("��������");
						chatMain = new ChatMain(chat_list[3], mainFrame, chat_list[4], arrayChatdList, this);
						totalChatRoom.add(chatMain);
						arrayChatdList.clear();
					} else if (type.equals("UIresult")) { // ä�÷α׵�
						//System.out.println("������Ʈ���� ������� �Ծ�");
						JSONObject selectObj4 = (JSONObject) array.get(0);
						String CHAT_CODE = (String) selectObj4.get("ChatCode");
						for (int i = 0; i < array.size(); i++) {
							JSONObject selectObj = (JSONObject) array.get(i);
							String[] chatArray = new String[selectObj.size() - 1];// type�� ������ ä�� �α׵�
							// System.out.println(selectObj.size());
							chatArray[0] = (String) selectObj.get("UserCode");
							chatArray[1] = (String) selectObj.get("ChatLog");
							chatArray[2] = (String) selectObj.get("Time");
							chatArray[3] = (String) selectObj.get("ChatName");
							chatArray[4] = (String) selectObj.get("ChatCode");
							arrayChatdList1.add(chatArray);
						}
						//System.out.println("��������� �Ծ�!!!");
						System.out.println("total ä�ù� : " + totalChatRoom.size());
						for (int i = 0; i < totalChatRoom.size(); i++) {
							ChatMain cm = totalChatRoom.get(i);
							if (cm.chat_code.equals(CHAT_CODE)) {
								// ���⼭ ũ�� ��

								System.out.println("count size : " + cm.count);
								System.out.println("arrayChatdList1 size : " + arrayChatdList1.size());

								if (cm.count < arrayChatdList1.size()) {
									// ������� �߳���
									cm.comparison(cm.count, arrayChatdList1, CHAT_CODE);

									// cm.ChatArrays.clear();
									cm.count = arrayChatdList1.size();
									cm.ChatArrays1 = arrayChatdList1;
									// System.out.println("3333333333333333333333");
									break;
								}

							}
						}

						// ���⼭ ������ �� ê���� 2�� �Ѱ���
						String[] chat_list = (String[]) arrayChatdList1.get(0);
						// new ChatMain(chat_list[3], mainFrame, chat_list[4], arrayChatdList1);
						arrayChatdList1.clear();
					} else if (type.equals("newresult")) {
						// ä�ø�� �Ѹ��ų� �ؾߵɵ�..����
					} else if (type.equals("memberlistresult")) {
						ArrayList memberArray = new ArrayList();
						for (int i = 0; i < array.size(); i++) {
							JSONObject selectObj = (JSONObject) array.get(i);
							memberArray.add((String) selectObj.get("UserCode"));
						}
						mainFrame.memberArray = memberArray;
					} else if (type.equals("newchat2")) {
						JSONArray jsonArray = new JSONArray();
						JSONObject obj1 = new JSONObject(); // �� �а� ä�ù� �̸�
						JSONObject obj6 = (JSONObject) array.get(0);
						String chatName = (String) obj6.get("ChatName");

						obj1.put("ChatName", chatName);
						obj1.put("Chatmember", myProfile[2]);
						jsonArray.add(obj1);
						for (int i = 0; i < array.size(); i++) {
							obj6 = (JSONObject) array.get(i);
							JSONObject obj3 = new JSONObject();
							String nick = (String) obj6.get("FriendNick");
							obj3.put("ChatName", chatName);
							obj3.put("Chatmember", nick);
							jsonArray.add(obj3);
						}

						mainFrame.firstChatList = jsonArray;
						chatMain = new ChatMain(chatName, mainFrame, jsonArray, this);
						totalChatRoom.add(chatMain);
						chatMain.setFirstChatList(jsonArray);
					}

				} else {// json�� �迭 �������� ���� ������
					JSONObject orderObj = new JSONObject();
					JSONObject jsonObj = (JSONObject) obj;
					String type = (String) jsonObj.get("Type");
					if (type.equals("loginresult")) {
						// �� ���� ����!
						myProfile[0] = (String) jsonObj.get("Code");// ����Ʈ �� ����
						myProfile[1] = (String) jsonObj.get("Name");
						myProfile[2] = (String) jsonObj.get("Nick");
						myProfile[3] = (String) jsonObj.get("Id");
						myProfile[4] = (String) jsonObj.get("Img");
						myProfile[5] = (String) jsonObj.get("Phone");
						myProfile[6] = (String) jsonObj.get("Email");
						myProfile[7] = (String) jsonObj.get("Birth");

						// �α��� ���� �� �����͸� ������
						orderObj.put("Type", "friendlist");
						orderObj.put("Code", myProfile[0]);
						ct.send(orderObj.toString());// ģ�� ��� ���� ���� ���� ��
						loginMain.dispose();// �α���ȭ�� ��������
					} else if (type.equals("u_search")) { // ģ�� ã��� ģ�� ���� ��������
						// System.out.println((String) jsonObj.get("User_nick"));
						if ((String) jsonObj.get("User_nick") == null) { // �ش� ������ ���� ���
							mainFrame.addFriend.noExistUser();
						} else { // �ش� ������ �ִ� ���
							String name = (String) jsonObj.get("User_name");
							String nick = (String) jsonObj.get("User_nick");
							String img = (String) jsonObj.get("User_img");
							mainFrame.addFriend.showProfile(name, nick, "dog/" + img); // �ش� ������ ������ ����
						}
					} else if (type.equals("youCanMake")) {// ȸ�����Խ� ��밡���� ���̵� �϶�
						JOptionPane.showMessageDialog(ct.memberRegist, "��� ������ ���̵� �Դϴ�.");
					} else if (type.equals("alreadyHave")) {// ȸ�����Խ� ���Ұ� ���̵� �϶�
						JOptionPane.showMessageDialog(ct.memberRegist, "�ߺ��� ���̵� �Դϴ�.");
						ct.memberRegist.t_id.setText("");
					} else if (type.equals("youCanNick")) {// ȸ�����Խ� ��밡���� �г��� �϶�
						JOptionPane.showMessageDialog(ct.memberRegist, "��� ������ �г��� �Դϴ�.");
					} else if (type.equals("alreadyHaveNick")) {// ȸ�����Խ� ���Ұ� �г��� �϶�
						JOptionPane.showMessageDialog(ct.memberRegist, "�ߺ��� �г��� �Դϴ�.");
						ct.memberRegist.t_nick.setText("");
					} else if (type.equals("YesYes")) {// ä�� �Է½� ó�� �μ�Ʈ �϶�
						ChatMain.Yes = false;
					} else if (type.equals("NoNo")) {// ä�� �Է½� �ι�° �Է��϶�
						ChatMain.Yes = true;
					} else if (type.equals("noroom")) {
						// System.out.println("������� ����?");
//						addChat.send();
//						addChat.dispose();
					} else if (type.equals("coderesult")) {
						String chatCode=(String) jsonObj.get("ChatCode");
						
						
						chatMain.chat_code=chatCode;
				
						
					} else if (type.equals("NewChat")) {
						// �̶� ä�ù游 �����ְ� ���������� insert �ϸ� �ȵ�.
						JSONArray jsonArray = new JSONArray();
						JSONObject obj1 = new JSONObject(); // �� �а� ä�ù� �̸�
						JSONObject obj3 = new JSONObject(); // ���� �а� ä�ù� �̸�

						String friendCode = (String) jsonObj.get("FriendCode");
						String chat_name = myProfile[2] + ",";

						for (int i = 0; i < mainFrame.friendList.size(); i++) {
							String[] friend = (String[]) mainFrame.friendList.get(i);
							if (friend[0].equals(friendCode)) {
								chat_name += friend[2];
								obj3.put("Chatmember", friend[2]);
								obj3.put("ChatName", chat_name);
							}
						}
						obj1.put("Chatmember", myProfile[2]);
						obj1.put("ChatName", chat_name);
						jsonArray.add(obj1);
						jsonArray.add(obj3);
						System.out.println(jsonArray);
						chatMain=new ChatMain(chat_name, mainFrame, jsonArray, this);
						mainFrame.firstChatList = jsonArray;
						totalChatRoom.add(chatMain);
					}

				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
}