
/*메인 프레임에 들어오는 result값 분석기*/
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
	// 아마 실행해야하는 모든클래스를 가진 애도 따로 클래스로 빼야할듯
	// MainFrame 불러오는 시점 알아야함
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
			JOptionPane.showMessageDialog(null, "아이디나 비번이 틀립니다");
		} else if (result.equals("newB")) {
			// System.out.println("newb 나옴");
			mainFrame = new MainFrame(ct, myProfile);
		} else {

			try {
				obj = parser.parse(result);// 반환형이 Object이기때문에 쓰려면 jsonobject로 형변환해야함
				// System.out.println("obj : " + obj);

				if (array.getClass() == obj.getClass()) { // json 배열 형식으로 받을때
					JSONObject orderObj = new JSONObject();
					array = (JSONArray) obj;
					ArrayList arrayFriendList = new ArrayList();

					JSONObject findType = (JSONObject) array.get(0);// 무슨 타입인지 확인하기 위해
					String type = (String) findType.get("Type");
					// System.out.println(type);
					if (type.equals("friendresult")) { // 친구 목록값
						for (int i = 0; i < array.size(); i++) {
							JSONObject selectObj = (JSONObject) array.get(i);
							String[] friendList = new String[selectObj.size() - 1];// 친구정보를 담기위한 배열 -1 이유는 type 은 빼기 위함
							// 처음 한번만 메인프레임 생성을 위해
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

								arrayFriendList.add(friendList);// 여기까지 오면 모든 친구정보를 배열 담기 끝

							}
						}
						// 메인프레임에 친구 리스트 저장
						mainFrame.friendList = arrayFriendList;
						if (master != null) {
							master.removeAllNode();// 지금 붙어있는 모든 노드 삭제
							master.makeFriendList();// 새로받은 친구 리스트 트리에 붙이기
							master.mainFrame.memberListMain.tree.updateUI();// 화면 리플레쉬
							master.expandAllNode(master.mainFrame.memberListMain.tree);// 모든 노드 열어놓기
						} else {
							master = new Master(ct, mainFrame, this);// 모든 객체에 접근하기 쉽게 만든 클래스, 처음 접속했을시
						}
						array.clear();
					} else if (type.equals("chatlistresult")) {
						// System.out.println("챗리스트 가져와");
						for (int i = 0; i < array.size(); i++) {
							JSONObject codeObj = new JSONObject();
							JSONObject selectObj = (JSONObject) array.get(i);
							String chatCode = (String) selectObj.get("ChatCode");
							String chatName = (String) selectObj.get("ChatName");
							// 여기서 나오는 챗코드를 다시 서버에 전송해 채팅방에 몇명인지 확인하고 이미지를 가져와야함
							codeObj.put("Type", "chatmember");
							codeObj.put("ChatCode", chatCode);
							codeObj.put("MyCode", myProfile[0]);
							codeObj.put("ChatName", chatName);

							ct.send(codeObj.toString());
						}
					} else if (type.equals("chatmemberresult")) {
						// 들어온 정보를 잘 저장해야함...
						// System.out.println("size : " + array.size());
						String[] chatInfo = new String[4 + array.size()];// 7
						JSONObject selectObj = (JSONObject) array.get(0);
						chatInfo[0] = (String) selectObj.get("ChatCode");
						chatInfo[1] = (String) selectObj.get("ChatName");
						chatInfo[2] = (String) selectObj.get("ChatLog");
						chatInfo[3] = (String) selectObj.get("ChatTime");
						chatInfo[4] = (String) selectObj.get("UserImg");
						// 여기서 채팅로그 마지막시간이랑 마지막 대화 가져와야함
						for (int i = 1; i < array.size(); i++) {// 5
							JSONObject selectObj2 = (JSONObject) array.get(i);
							chatInfo[i + 4] = (String) selectObj2.get("UserImg");
						}
						chatList.add(chatInfo);
						// 채팅방 목록 붙이기
						// if 문으로 이미지 몇개 들어오는 판별해야됨
						// 처음 가져오는 로그랑 다음에 가져오는 로그랑 같은지 판단해야함

						if (chatInfo.length == 5) {
							//
							mainFrame.chatListMain.createChatList(chatInfo[1], chatInfo[2], chatInfo[3], chatInfo[4],
									chatInfo[0]);// 1:1채팅
							// 2번
							// 최근로그,
							// 3
							// 시간
						} else if (chatInfo.length == 6) {
							mainFrame.chatListMain.createChatList(chatInfo[1], chatInfo[2], chatInfo[3], chatInfo[4],
									chatInfo[5], chatInfo.length - 3, chatInfo[0]);// 3명채팅, 랭쓰는 채팅방멤버수
						} else if (chatInfo.length == 7) {
							mainFrame.chatListMain.createChatList(chatInfo[1], chatInfo[2], chatInfo[3], chatInfo[4],
									chatInfo[5], chatInfo[6], chatInfo.length - 3, chatInfo[0]);// 4명채팅
						} else if (chatInfo.length > 7) {
							mainFrame.chatListMain.createChatList(chatInfo[1], chatInfo[2], chatInfo[3], chatInfo[4],
									chatInfo[5], chatInfo[6], chatInfo[7], chatInfo.length - 3, chatInfo[0]);// 5이상 채팅
						}

						mainFrame.chatListMain.updateUI();
						master.chatList = chatList;// 마스터에 챗목록 넘겨줌
						mainFrame.chatListMain.chatList = chatList;// 챗리스트 메인에 챗리스트 넘겨줌
						mainFrame.chatList = chatList;

					} else if (type.equals("chatupdateresult")) { // 채팅로그들
						for (int i = 0; i < array.size(); i++) {
							JSONObject selectObj = (JSONObject) array.get(i);

							String[] chatArray = new String[selectObj.size() - 1];// type을 제외한 채팅 로그들
							chatArray[0] = (String) selectObj.get("UserCode");
							chatArray[1] = (String) selectObj.get("ChatLog");
							chatArray[2] = (String) selectObj.get("Time");
							chatArray[3] = (String) selectObj.get("ChatName");
							chatArray[4] = (String) selectObj.get("ChatCode");

							// ChatMain - Array 보낸다
							// }
							arrayChatdList.add(chatArray);
						}
						// 마이코드를 제외한 나머지 사람을 닉네임을 추출해서 "안녕에 넣어야함.."
						String[] chat_list = (String[]) arrayChatdList.get(0);
						// System.out.println("하하하하");
						chatMain = new ChatMain(chat_list[3], mainFrame, chat_list[4], arrayChatdList, this);
						totalChatRoom.add(chatMain);
						arrayChatdList.clear();
					} else if (type.equals("UIresult")) { // 채팅로그들
						//System.out.println("업데이트내용 여기까지 왔어");
						JSONObject selectObj4 = (JSONObject) array.get(0);
						String CHAT_CODE = (String) selectObj4.get("ChatCode");
						for (int i = 0; i < array.size(); i++) {
							JSONObject selectObj = (JSONObject) array.get(i);
							String[] chatArray = new String[selectObj.size() - 1];// type을 제외한 채팅 로그들
							// System.out.println(selectObj.size());
							chatArray[0] = (String) selectObj.get("UserCode");
							chatArray[1] = (String) selectObj.get("ChatLog");
							chatArray[2] = (String) selectObj.get("Time");
							chatArray[3] = (String) selectObj.get("ChatName");
							chatArray[4] = (String) selectObj.get("ChatCode");
							arrayChatdList1.add(chatArray);
						}
						//System.out.println("여기까지는 왔어!!!");
						System.out.println("total 채팅방 : " + totalChatRoom.size());
						for (int i = 0; i < totalChatRoom.size(); i++) {
							ChatMain cm = totalChatRoom.get(i);
							if (cm.chat_code.equals(CHAT_CODE)) {
								// 여기서 크기 비교

								System.out.println("count size : " + cm.count);
								System.out.println("arrayChatdList1 size : " + arrayChatdList1.size());

								if (cm.count < arrayChatdList1.size()) {
									// 여기까지 잘나옴
									cm.comparison(cm.count, arrayChatdList1, CHAT_CODE);

									// cm.ChatArrays.clear();
									cm.count = arrayChatdList1.size();
									cm.ChatArrays1 = arrayChatdList1;
									// System.out.println("3333333333333333333333");
									break;
								}

							}
						}

						// 여기서 사이즈 비교 챗메인 2에 넘겨줌
						String[] chat_list = (String[]) arrayChatdList1.get(0);
						// new ChatMain(chat_list[3], mainFrame, chat_list[4], arrayChatdList1);
						arrayChatdList1.clear();
					} else if (type.equals("newresult")) {
						// 채팅목록 뿌리거나 해야될듯..보류
					} else if (type.equals("memberlistresult")) {
						ArrayList memberArray = new ArrayList();
						for (int i = 0; i < array.size(); i++) {
							JSONObject selectObj = (JSONObject) array.get(i);
							memberArray.add((String) selectObj.get("UserCode"));
						}
						mainFrame.memberArray = memberArray;
					} else if (type.equals("newchat2")) {
						JSONArray jsonArray = new JSONArray();
						JSONObject obj1 = new JSONObject(); // 내 닉과 채팅방 이름
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

				} else {// json을 배열 형식으로 받지 않을때
					JSONObject orderObj = new JSONObject();
					JSONObject jsonObj = (JSONObject) obj;
					String type = (String) jsonObj.get("Type");
					if (type.equals("loginresult")) {
						// 내 정보 저장!
						myProfile[0] = (String) jsonObj.get("Code");// 프린트 잘 나옴
						myProfile[1] = (String) jsonObj.get("Name");
						myProfile[2] = (String) jsonObj.get("Nick");
						myProfile[3] = (String) jsonObj.get("Id");
						myProfile[4] = (String) jsonObj.get("Img");
						myProfile[5] = (String) jsonObj.get("Phone");
						myProfile[6] = (String) jsonObj.get("Email");
						myProfile[7] = (String) jsonObj.get("Birth");

						// 로그인 성공 내 데이터를 가져옴
						orderObj.put("Type", "friendlist");
						orderObj.put("Code", myProfile[0]);
						ct.send(orderObj.toString());// 친구 목록 가져 오게 오더 함
						loginMain.dispose();// 로그인화면 없어지게
					} else if (type.equals("u_search")) { // 친구 찾기시 친구 정보 가져오기
						// System.out.println((String) jsonObj.get("User_nick"));
						if ((String) jsonObj.get("User_nick") == null) { // 해당 유저가 없는 경우
							mainFrame.addFriend.noExistUser();
						} else { // 해당 유저가 있는 경우
							String name = (String) jsonObj.get("User_name");
							String nick = (String) jsonObj.get("User_nick");
							String img = (String) jsonObj.get("User_img");
							mainFrame.addFriend.showProfile(name, nick, "dog/" + img); // 해당 유저의 프로필 부착
						}
					} else if (type.equals("youCanMake")) {// 회원가입시 사용가능한 아이디 일때
						JOptionPane.showMessageDialog(ct.memberRegist, "사용 가능한 아이디 입니다.");
					} else if (type.equals("alreadyHave")) {// 회원가입시 사용불가 아이디 일때
						JOptionPane.showMessageDialog(ct.memberRegist, "중복된 아이디 입니다.");
						ct.memberRegist.t_id.setText("");
					} else if (type.equals("youCanNick")) {// 회원가입시 사용가능한 닉네임 일때
						JOptionPane.showMessageDialog(ct.memberRegist, "사용 가능한 닉네임 입니다.");
					} else if (type.equals("alreadyHaveNick")) {// 회원가입시 사용불가 닉네임 일때
						JOptionPane.showMessageDialog(ct.memberRegist, "중복된 닉네임 입니다.");
						ct.memberRegist.t_nick.setText("");
					} else if (type.equals("YesYes")) {// 채팅 입력시 처음 인서트 일때
						ChatMain.Yes = false;
					} else if (type.equals("NoNo")) {// 채팅 입력시 두번째 입력일때
						ChatMain.Yes = true;
					} else if (type.equals("noroom")) {
						// System.out.println("여기까지 오니?");
//						addChat.send();
//						addChat.dispose();
					} else if (type.equals("coderesult")) {
						String chatCode=(String) jsonObj.get("ChatCode");
						
						
						chatMain.chat_code=chatCode;
				
						
					} else if (type.equals("NewChat")) {
						// 이때 채팅방만 열어주고 서버측에선 insert 하면 안됨.
						JSONArray jsonArray = new JSONArray();
						JSONObject obj1 = new JSONObject(); // 내 닉과 채팅방 이름
						JSONObject obj3 = new JSONObject(); // 상대방 닉과 채팅방 이름

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