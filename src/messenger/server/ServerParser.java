package messenger.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import messenger.db.ConnectionManager;

public class ServerParser {
	ServerThread st;
	MultiServer multiServer;
	String order;
	String result;
	Object obj;
//   ConnectionManager conManger;
//   PreparedStatement pstmt;
	ResultSet rs;
	Connection con;
	ConnectionManager connectionManger;
	JSONArray array = new JSONArray();
	String ClientId;
	boolean flag = true;

	public ServerParser(MultiServer multiServer, ServerThread st) {
		this.multiServer = multiServer;
		this.st = st;
		connectionManger = new ConnectionManager();
	}

	public void Parser(String order) {
		this.order = order;
		JSONParser parser = new JSONParser();
		String sql = null;
		Connection con = multiServer.con;
//      PreparedStatement pstmt = null;
//
//      ResultSet rs = null;
		Object obj2 = null;
		try {
			obj2 = parser.parse(order);
			if (array.getClass() != obj2.getClass()) {
				JSONObject jsonObj = (JSONObject) obj2;
				String type = (String) jsonObj.get("Type");
				// System.out.println(type);
				if (type.equals("join")) {
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					multiServer.area.append("���� �ϰ�???\n");
					sql = "insert into users(user_code, user_name, user_id, user_pw, user_birth, user_phone, user_email, user_nick, user_img)"
							+ " values(seq_users.nextval, '" + (String) jsonObj.get("Name") + "', '"
							+ (String) jsonObj.get("Id") + "', '" + (String) jsonObj.get("Pw") + "', '"
							+ (String) jsonObj.get("Birth") + "', '" + (String) jsonObj.get("Phone") + "', '"
							+ (String) jsonObj.get("Email") + "', '" + (String) jsonObj.get("Nick") + "', '"
							+ (String) jsonObj.get("Img") + "')";
					try {
						pstmt = con.prepareStatement(sql);
						int result = pstmt.executeUpdate();

						if (result == 1) {
							// ���������� �޽��� ������
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						connectionManger.closeDB(pstmt);
					}
				} else if (type.equals("checkId")) {
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					multiServer.area.append("���̵� �ߺ� üũ\n");
					sql = "select * from users where user_id='" + (String) jsonObj.get("Id") + "'";
					JSONObject obj = new JSONObject();
					try {
						pstmt = con.prepareStatement(sql);
						rs = pstmt.executeQuery();
						if (!rs.isBeforeFirst()) {
							obj.put("Type", "youCanMake");
							st.send(obj.toString());
						} else {
							obj.put("Type", "alreadyHave");
							st.send(obj.toString());
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						connectionManger.closeDB(pstmt, rs);
					}
				} else if (type.equals("checkNick")) {
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					multiServer.area.append("�г��� �ߺ� üũ\n");
					sql = "select * from users where user_nick='" + (String) jsonObj.get("Nick") + "'";
					JSONObject obj = new JSONObject();
					try {
						pstmt = con.prepareStatement(sql);
						rs = pstmt.executeQuery();
						if (!rs.isBeforeFirst()) {
							obj.put("Type", "youCanNick");
							st.send(obj.toString());
						} else {
							obj.put("Type", "alreadyHaveNick");
							st.send(obj.toString());
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						connectionManger.closeDB(pstmt, rs);
					}
				} else if (type.equals("login")) {
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					multiServer.area.append("�α��� �ϰ�???\n");
					sql = "select * from users where user_id = '" + (String) jsonObj.get("Id") + "' and user_pw = '"
							+ (String) jsonObj.get("Pw") + "'";
					try {
						pstmt = con.prepareStatement(sql);
						rs = pstmt.executeQuery();
						if (!rs.isBeforeFirst()) {
							st.send("nodata");
						}
						while (rs.next() == true) {
							JSONObject rObj = new JSONObject();
							String id = rs.getString("user_id");
							String code = rs.getString("user_code");
							String name = rs.getString("user_name");
							String birth = rs.getString("user_birth");
							String phone = rs.getString("user_phone");
							String email = rs.getString("user_email");
							String nick = rs.getString("user_nick");
							String img = rs.getString("user_img");
							rObj.put("Type", "loginresult");
							rObj.put("Code", code);
							rObj.put("Id", id);
							rObj.put("Name", name);
							rObj.put("Birth", birth);
							rObj.put("Phone", phone);
							rObj.put("Email", email);
							rObj.put("Nick", nick);
							rObj.put("Img", img);
							st.send(rObj.toString());
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						connectionManger.closeDB(pstmt, rs);
					}
				} else if (type.equals("friendlist")) {
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					multiServer.area.append("ģ����Ϻ���???\n");
					sql = "select distinct users.user_code, users.user_name, users.user_nick, users_list.type, users.user_img, users.user_phone, users.user_email, users.user_birth  from users join users_list on users.user_code = users_list.friend where users_list.user_code = "
							+ (String) jsonObj.get("Code") + " ORDER BY users.user_name";
					try {
						pstmt = con.prepareStatement(sql);
						rs = pstmt.executeQuery();
						if (!rs.isBeforeFirst()) { // ģ�� ��� ������ ���°Ŵ� ���� Ŭ��� �޼��� �����ʿ����
							st.send("newB");
						} else {
							while (rs.next() == true) {
								JSONObject rObj = new JSONObject();
								String code = rs.getString("user_code");
								String name = rs.getString("user_name");
								String nick = rs.getString("user_nick");
								String relation = rs.getString("type");
								String img = rs.getString("user_img");
								String phone = rs.getString("user_phone");
								String email = rs.getString("user_email");
								String birth = rs.getString("user_birth");
								rObj.put("Type", "friendresult");
								rObj.put("Code", code);
								rObj.put("Name", name);
								rObj.put("Nick", nick);
								rObj.put("Relation", relation);
								rObj.put("Img", img);
								rObj.put("Phone", phone);
								rObj.put("Email", email);
								rObj.put("Birth", birth);
								array.add(rObj);
							}
							st.send(array.toJSONString());
							array.clear();
						}
						// System.out.println("��� ������ : "+array.size());
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						connectionManger.closeDB(pstmt, rs);
					}
				} else if (type.equals("Start")) { // ģ����Ͽ��� ����Ŭ���� ����
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					PreparedStatement pstmt2 = null;
					ResultSet rs2 = null;
					String myCode = (String) jsonObj.get("MyCode");
					String friendCode = (String) jsonObj.get("FriendCode");
					sql = "SELECT CHAT_CODE FROM(SELECT CHAT_CODE, SUM(CASE WHEN USER_CODE IN (" + myCode + ", "
							+ friendCode
							+ ") THEN 1 ELSE 0 END) AS TargetCnt FROM CHAT_MEMBERS GROUP BY CHAT_CODE HAVING COUNT(*)  = 2) Tbl WHERE Tbl.TargetCnt = 2";

					try {
						pstmt = con.prepareStatement(sql);
						rs = pstmt.executeQuery();
						if (!rs.isBeforeFirst()) { // ���� ä�ù��� �����Ƿ� �׳� ȭ�鸸 ����ؾ���
							JSONObject rObj = new JSONObject();
							rObj.put("Type", "NewChat");
							rObj.put("FriendCode", friendCode);
							st.send(rObj.toString());
						} else {
							rs.next(); // ä�ù� �����Ƿ� db���� chat log ���ܿ;���

							String chat_Code = rs.getString("CHAT_CODE");

							multiServer.area.append("ä�ó�����̾�!!!!!!!!!");
							sql = "select chat_name from chat_room where chat_code=" + chat_Code;
							try {
								pstmt = con.prepareStatement(sql);
								rs = pstmt.executeQuery();
								if (!rs.isBeforeFirst()) {
									// send("newC");

								} else {
									rs.next();
									String chatName = rs.getString("chat_name");
									String chatCode = chat_Code;

									sql = "select chat_log, user_code, time from chat_logs where chat_code = "
											+ chat_Code + "ORDER BY time";
									pstmt2 = con.prepareStatement(sql);
									rs2 = pstmt2.executeQuery();
									while (rs2.next() == true) {
										System.out.println("while����~~");
										JSONObject rObj = new JSONObject();
										String userCode = rs2.getString("user_code");
										String chatLog = rs2.getString("chat_log");
										String time = rs2.getString("time");
										rObj.put("Type", "chatupdateresult");
										rObj.put("UserCode", userCode);
										rObj.put("ChatLog", chatLog);
										rObj.put("Time", time);
										rObj.put("ChatName", chatName);
										rObj.put("ChatCode", chatCode);
										array.add(rObj);
									}
									connectionManger.closeDB(pstmt2, rs2);
									st.send(array.toJSONString());
									//System.out.println(array);
									//System.out.println(array.size());
									array.clear();
								}
							} catch (SQLException e) {
								e.printStackTrace();
							} finally {
								connectionManger.closeDB(pstmt, rs);
							}

						}
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						connectionManger.closeDB(pstmt, rs);
					}
				} else if (type.equals("friendadd")) {
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					PreparedStatement pstmt2 = null;
					ResultSet rs2 = null;
					multiServer.area.append("�νγ�???\n");
					sql = "insert into users_list(user_code   ,type   ,friend) values(" + (String) jsonObj.get("Code")
							+ ", '" + (String) jsonObj.get("Relation")
							+ "'  , (select user_code from users where user_nick = '" + (String) jsonObj.get("Nick")
							+ "'))";
					try {
						pstmt = con.prepareStatement(sql);
						int result = pstmt.executeUpdate();
						if (result == 1) {
							System.out.println("�����");
							// �׸��� ģ�� ����Ʈ �ٽ� ��������
							sql = "select distinct users.user_code, users.user_name, users.user_nick, users_list.type, users.user_img, users.user_phone, users.user_email, users.user_birth  from users join users_list on users.user_code = users_list.friend where users_list.user_code = "
									+ (String) jsonObj.get("Code") + " ORDER BY users.user_name";
							try {

								pstmt2 = con.prepareStatement(sql);
								rs2 = pstmt2.executeQuery();
								if (!rs2.isBeforeFirst()) { // ģ�� ��� ������ ���°Ŵ� ���� Ŭ��� �޼��� �����ʿ����
									st.send("newB");
								} else {
									System.out.println("���� ���;� ��");
									while (rs2.next()) {
										JSONObject rObj = new JSONObject();
										String code = rs2.getString("user_code");
										String name = rs2.getString("user_name");
										String nick = rs2.getString("user_nick");
										String relation = rs2.getString("type");
										String img = rs2.getString("user_img");
										String phone = rs2.getString("user_phone");
										String email = rs2.getString("user_email");
										String birth = rs2.getString("user_birth");
										rObj.put("Type", "friendresult");
										rObj.put("Code", code);
										rObj.put("Name", name);
										rObj.put("Nick", nick);
										rObj.put("Relation", relation);
										rObj.put("Img", img);
										rObj.put("Phone", phone);
										rObj.put("Email", email);
										rObj.put("Birth", birth);
										array.add(rObj);
									}
									st.send(array.toJSONString());
									array.clear();
									connectionManger.closeDB(pstmt2, rs2);
								}
							} catch (SQLException e) {
								e.printStackTrace();
							} finally {
								connectionManger.closeDB(pstmt, rs);
							}

						}

					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						connectionManger.closeDB(pstmt);
					}

				} else if (type.equals("frienddelete")) {
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					multiServer.area.append("�����ϰ�???\n");
					sql = "delete from users_list where user_code =" + (String) jsonObj.get("MyCode") + " and friend = "
							+ (String) jsonObj.get("FriendCode");
					try {
						pstmt = con.prepareStatement(sql);
						int result = pstmt.executeUpdate(sql);
						if (result == 1) {
							// ģ����� �ٽú���
							sql = "select distinct users.user_code, users.user_name, users.user_nick, users_list.type, users.user_img, users.user_phone, users.user_email, users.user_birth  from users join users_list on users.user_code = users_list.friend where users_list.user_code = "
									+ (String) jsonObj.get("MyCode") + " ORDER BY users.user_name";
							try {
								pstmt = con.prepareStatement(sql);
								rs = pstmt.executeQuery();
								if (!rs.isBeforeFirst()) { // ģ�� ��� ������ ���°Ŵ� ���� Ŭ��� �޼��� �����ʿ����
									JSONObject rObj = new JSONObject();
									rObj.put("Type", "NewB");
									st.send(rObj.toString());
								} else {
									while (rs.next() == true) {
										JSONObject rObj = new JSONObject();
										String code = rs.getString("user_code");
										String name = rs.getString("user_name");
										String nick = rs.getString("user_nick");
										String relation = rs.getString("type");
										String img = rs.getString("user_img");
										String phone = rs.getString("user_phone");
										String email = rs.getString("user_email");
										String birth = rs.getString("user_birth");
										rObj.put("Type", "friendresult");
										rObj.put("Code", code);
										rObj.put("Name", name);
										rObj.put("Nick", nick);
										rObj.put("Relation", relation);
										rObj.put("Img", img);
										rObj.put("Phone", phone);
										rObj.put("Email", email);
										rObj.put("Birth", birth);
										array.add(rObj);
									}
								}
								st.send(array.toJSONString());
								array.clear();
								// System.out.println("��� ������ : "+array.size());
							} catch (SQLException e) {
								e.printStackTrace();
							} finally {
								connectionManger.closeDB(pstmt, rs);
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						connectionManger.closeDB(pstmt);
					}
				} else if (type.equals("chatlist")) {
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					multiServer.area.append("ä�ø�� �ʿ���???");
					sql = "select chat_room.chat_code, chat_room.chat_name from chat_room join chat_members on chat_room.chat_code = chat_members.chat_code where chat_members.user_code = "
							+ jsonObj.get("Code") + " ORDER BY CHAT_CODE";
					try {
						pstmt = con.prepareStatement(sql);
						rs = pstmt.executeQuery();
						if (!rs.isBeforeFirst()) {// �����غ��� ä�ø�� ������ �ƹ��͵� ���ص� �ǹǷ� �ƹ��͵� �Ⱥ�������
							// send("newC");
						} else {
							while (rs.next() == true) {
								JSONObject rObj = new JSONObject();
								// �̹����� �Ѱܾ� ��
								String chatCode = rs.getString("chat_code");
								String chatName = rs.getString("chat_name");
								rObj.put("Type", "chatlistresult");
								rObj.put("ChatCode", chatCode);
								rObj.put("ChatName", chatName);
								array.add(rObj);
							}
							// System.out.println("array size : " + array.size());
							st.send(array.toJSONString());
							array.clear();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						connectionManger.closeDB(pstmt, rs);
					}
				} else if (type.equals("chatupdate")) {// ä�� �α� �������°�
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					PreparedStatement pstmt2 = null;
					ResultSet rs2 = null;
					multiServer.area.append("ä�ó�����̾�!!!!!!!!!");
					sql = "select chat_name from chat_room where chat_code=" + jsonObj.get("Code");
					try {
						pstmt = con.prepareStatement(sql);
						rs = pstmt.executeQuery();
						rs.next();
						String chatName = rs.getString("chat_name");
						String chatCode = (String) jsonObj.get("Code");
						System.out.println(chatName);
						System.out.println(chatCode);
						sql = "select chat_log, user_code, time from chat_logs where chat_code = " + chatCode
								+ "ORDER BY time";
						pstmt2 = con.prepareStatement(sql);
						rs2 = pstmt2.executeQuery();
						if (!rs2.isBeforeFirst()) {
							// send("newC");
						} else {
							while (rs2.next()) {
								JSONObject rObj = new JSONObject();
								String userCode = rs2.getString("user_code");
								String chatLog = rs2.getString("chat_log");
								String time = rs2.getString("time");
								rObj.put("Type", "chatupdateresult");
								rObj.put("UserCode", userCode);
								rObj.put("ChatLog", chatLog);
								rObj.put("Time", time);
								rObj.put("ChatName", chatName);
								rObj.put("ChatCode", chatCode);
								array.add(rObj);
							}
							connectionManger.closeDB(pstmt2, rs2);
							// System.out.println("array size : " + array.size());
							st.send(array.toJSONString());
							array.clear();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						connectionManger.closeDB(pstmt, rs);
					}
				} else if (type.equals("chatUI")) {// ������Ʈ
					//System.out.println("������Ʈ �ʿ���??");
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					PreparedStatement pstmt2 = null;
					ResultSet rs2 = null;
					//System.out.println(jsonObj.get("C.Code"));
					sql = "select chat_name from chat_room where chat_code=" + (String) jsonObj.get("C.Code");

					try {
						pstmt = con.prepareStatement(sql);
						rs = pstmt.executeQuery();
						//System.out.println(rs.isBeforeFirst());
						rs.next();
						String chatName = rs.getString("chat_name");
						String chatCode = (String) jsonObj.get("C.Code");
//						System.out.println(chatName);
//						System.out.println(chatCode);
						sql = "select chat_log, user_code, time from chat_logs where chat_code = " + chatCode
								+ "ORDER BY time";
						pstmt2 = con.prepareStatement(sql);
						rs2 = pstmt2.executeQuery();
						if (!rs2.isBeforeFirst()) {
							System.out.println("������Ʈ �Ұ� ����");
							// send("newC");
						} else {
							while (rs2.next()) {
								JSONObject rObj = new JSONObject();
								String userCode = rs2.getString("user_code");
								String chatLog = rs2.getString("chat_log");
								String time = rs2.getString("time");
								rObj.put("Type", "UIresult");
								rObj.put("UserCode", userCode);
								rObj.put("ChatLog", chatLog);
								rObj.put("Time", time);
								rObj.put("ChatName", chatName);
								rObj.put("ChatCode", chatCode);
								array.add(rObj);
							}
							//System.out.println("����Ǵ�?");
							connectionManger.closeDB(pstmt2, rs2);
							// System.out.println(array.toJSONString());
							st.send(array.toJSONString());
							array.clear();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						connectionManger.closeDB(pstmt, rs);
					}
				} else if (type.equals("chatmember")) {
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					PreparedStatement pstmt2 = null;
					ResultSet rs2 = null;
					PreparedStatement pstmt3 = null;
					ResultSet rs3 = null;
					String chatCode = (String) jsonObj.get("ChatCode");
					String myCode = (String) jsonObj.get("MyCode");
					String chatName = (String) jsonObj.get("ChatName");

					JSONArray memberArray = new JSONArray();
					// ã�������� �� �ڵ�� ���� �̹����� �����;���
					sql = "SELECT USER_CODE FROM CHAT_MEMBERS mem WHERE mem.CHAT_CODE=" + chatCode + "";
					try {
						pstmt = con.prepareStatement(sql);
						rs = pstmt.executeQuery();
						while (rs.next()) {
							String user = rs.getString("user_code");
							if (user.equals(myCode)) {
							} else {
								// �̹����� �ʿ��� ���� �ڵ�--->member�� ���������
								// �迭�� ��������
								JSONObject rObj = new JSONObject();

								sql = "select user_img from users where user_code=" + user + "";
								pstmt2 = con.prepareStatement(sql);
								rs2 = pstmt2.executeQuery();
								rs2.next();
								String userImg = rs2.getString("user_img");
								// ä�ù� ������ ���� ���� ������
								rObj.put("Type", "chatmemberresult");
								rObj.put("ChatCode", chatCode);
								rObj.put("ChatName", chatName);
								rObj.put("UserImg", userImg);
								// ���� �ֱ� ä�÷α׿� �ð� ��������

								sql = "select * from chat_logs where CHAT_CODE=" + chatCode + " ORDER BY time desc";
								pstmt3 = con.prepareStatement(sql);
								rs3 = pstmt3.executeQuery();
								rs3.next();
								String chatLog = rs3.getString("chat_log");
								String chatTime = rs3.getString("time");
								rObj.put("ChatLog", chatLog);
								rObj.put("ChatTime", chatTime);
								array.add(rObj);
								connectionManger.closeDB(pstmt2, rs2);
								connectionManger.closeDB(pstmt3, rs3);
							}
						}
						st.send(array.toJSONString());

						array.clear();
						// Ŭ���̾�Ʈ�� �Ѱ������
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						connectionManger.closeDB(pstmt, rs);
					}

				} else if (type.equals("chating")) {
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					sql = "insert into chat_logs(chat_code, user_code, chat_log, time) values ('"
							+ (String) jsonObj.get("C.Code") + "', '" + (String) jsonObj.get("U.Code") + "', '"
							+ (String) jsonObj.get("Log")
							+ "', (SELECT TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') FROM DUAL))";
					try {
						System.out.println("�Է��Ѵ�!!   " + sql);
						pstmt = con.prepareStatement(sql);
						int result = pstmt.executeUpdate();
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						connectionManger.closeDB(pstmt);
					}
					multiServer.area.append("���ϰ�???");
				} else if (type.equals("chatmemberadd")) {
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					multiServer.area.append("�ϸ� ������� ���ʿ���???");
					sql = "insert into chat_members(chat_code, user_code) values ('" + (String) jsonObj.get("C.Code")
							+ "', '" + (String) jsonObj.get("U.Code") + ")";
					try {
						pstmt = con.prepareStatement(sql);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else if (type.equals("chat_code_check")) {
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					sql = "select * from chat_logs where chat_code = " + jsonObj.get("C.Code");
					System.out.println("���� ������???");
					try {
						pstmt = con.prepareStatement(sql);
						rs = pstmt.executeQuery();
						JSONObject rObj = new JSONObject();
						if (!rs.isBeforeFirst()) {
							rObj.put("Type", "NoNo");
							st.send(rObj.toString());
						} else {
							rObj.put("Type", "YesYes");
							st.send(rObj.toString());

							System.out.println("���� ����� : ");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					multiServer.area.append("ä�ù� �־�???\n");
				} else if (type.equals("file")) {
					System.out.println("���� ������???");
				} else if (type.equals("img")) {
					System.out.println("�̹��� ������???");
				} else if (type.equals("emoticon")) {
					System.out.println("�̸�Ƽ�� ������???");
				} else if (type.equals("f_search")) {
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					sql = "select user_name, user_nick, user_img from users" + " where (user_nick = '"
							+ (String) jsonObj.get("Search") + "') and "
							+ "((SELECT USER_CODE FROM USERS WHERE USER_NICK = '" + (String) jsonObj.get("Search")
							+ "')" + " NOT IN (SELECT friend FROM USERS_LIST WHERE USER_CODE = "
							+ (String) jsonObj.get("MyCode") + "))";
					System.out.println(sql);
					try {
						pstmt = con.prepareStatement(sql);
						rs = pstmt.executeQuery();
						JSONObject rObj = new JSONObject();
						if (!rs.isBeforeFirst()) {
							rObj.put("Type", "u_search");
						} else {
							while (rs.next()) {
								rObj.put("Type", "u_search");
								String name = rs.getString("user_name");
								rObj.put("User_name", name);
								String nick = rs.getString("user_nick");
								rObj.put("User_nick", nick);
								String img = rs.getString("user_img");
								rObj.put("User_img", img);
							}
						}
						st.send(rObj.toString());
						//System.out.println(rObj);
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						connectionManger.closeDB(pstmt, rs);
					}
					multiServer.area.append("ģ�� �˻�");
				} else if (type.equals("chat_del")) {
					PreparedStatement pstmt = null;
					sql = "delete from chat_members where user_code =" + (String) jsonObj.get("UserCode")
							+ " and chat_code =" + (String) jsonObj.get("ChatCode");
					try {
						pstmt = con.prepareStatement(sql);
						int result = pstmt.executeUpdate(sql);
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						connectionManger.closeDB(pstmt);
					}
					multiServer.area.append("ä�ù� ������");
				} else if (type.equals("memberlist")) {
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					sql = "SELECT USER_CODE FROM CHAT_MEMBERS mem WHERE mem.CHAT_CODE="
							+ (String) jsonObj.get("ChatCode");
					try {
						pstmt = con.prepareStatement(sql);
						rs = pstmt.executeQuery();
						while (rs.next()) {
							JSONObject rObj = new JSONObject();
							String user_code = rs.getString("USER_CODE");
							rObj.put("Type", "memberlistresult");
							rObj.put("UserCode", user_code);
							array.add(rObj);
						}
						st.send(array.toJSONString());
						System.out.println("�ߵ���?" + array.toJSONString());
						array.clear();
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						connectionManger.closeDB(pstmt, rs);
					}
				}
			} else { // �迭�� ������
				JSONObject orderObj = new JSONObject();
				ArrayList arrayFriendList = new ArrayList();
				//System.out.println("obj2" + obj2);
				array = (JSONArray) obj2;
				//System.out.println("array" + array);
				String friend;// ģ������
				JSONObject Type = (JSONObject) array.get(0);// ���� Ÿ������ Ȯ���ϱ� ����
				String type1 = (String) Type.get("Type");

				if (type1.equals("newchating")) { // ä�� ó�� �Է½�
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					for (int i = 0; i < array.size(); i++) { // ģ���� ���
						JSONObject Obj = (JSONObject) array.get(i);
						friend = (String) Obj.get("Chatmember");
						arrayFriendList.add(friend);
						//System.out.println("ģ�� : " + friend);
					}
					//System.out.println("ģ����� : " + arrayFriendList.size());

					JSONObject Obj = (JSONObject) array.get(0);
					sql = "insert into chat_room(chat_code, chat_name) values (seq_chat.nextval, '"
							+ (String) Obj.get("ChatName") + "')";
					//System.out.println("ê �� �μ�Ʈ: " + sql);
					try {
						pstmt = con.prepareStatement(sql);
						int result = pstmt.executeUpdate();
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						connectionManger.closeDB(pstmt);
					}
					for (int i = 0; i < arrayFriendList.size(); i++) {
						sql = "insert into chat_members(chat_code, user_code) values (seq_chat.currval,"
								+ "(select user_code from users where user_nick = '" + arrayFriendList.get(i) + "'))";
						//System.out.println("ê �ɹ� �μ�Ʈ: " + sql);
						try {
							pstmt = con.prepareStatement(sql);
							int result = pstmt.executeUpdate();
						} catch (SQLException e) {
							e.printStackTrace();
						} finally {
							connectionManger.closeDB(pstmt);
						}
					}
					sql = "insert into chat_logs(chat_code, user_code, chat_log, time) values (seq_chat.currval, "
							+ (String) Obj.get("User") + ", '" + // ���ѻ��
							(String) Obj.get("Say") + "', " + // ���ѳ���
							"(SELECT TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') FROM DUAL))";

					try {
						pstmt = con.prepareStatement(sql);
						int result = pstmt.executeUpdate();
						if (result == 1) {

							sql = "select seq_chat.currval AS chat_code from dual";
							//sql = "select chat_code from chat_room where chat_code=seq_chat.currval";
							try {
								pstmt=con.prepareStatement(sql);
								rs=pstmt.executeQuery();
								JSONObject rObj = new JSONObject();
								rs.next();
								rObj.put("Type", "coderesult");
								String chatCode = rs.getString("chat_code");
								rObj.put("ChatCode", chatCode);
								st.send(rObj.toString());
							} catch (SQLException e) {
								e.printStackTrace();
							} finally {
								connectionManger.closeDB(pstmt, rs);
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						connectionManger.closeDB(pstmt);
					}

					multiServer.area.append("ó�����ϰ�???");
				} else if (type1.equals("checkRoom")) { // ����â���� ���ο�� �������� �����ִ��� ������ Ȯ���ϰ� �����ִ� ��
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					PreparedStatement pstmt2 = null;
					ResultSet rs2 = null;
					// String chatName=(String) arrays.get(0);
					JSONObject objobj = (JSONObject) array.get(0);
					String chatName = (String) objobj.get("ChatName");
					//System.out.println(chatName);
					sql = "select chat_code from chat_room where chat_name='" + chatName + "'";
					try {
						pstmt = con.prepareStatement(sql);
						rs = pstmt.executeQuery();
						if (!rs.isBeforeFirst()) { // ���� ������ �����ؾ���
							// System.out.println("1111111111111111111111111111");
							// �����Ƿ� ��ä�ù� ����.,.,.,.
							JSONArray array2 = new JSONArray();
							for (int i = 0; i < array.size(); i++) {
								JSONObject rObj = new JSONObject();
								JSONObject obj6 = (JSONObject) array.get(i);
								String nick = (String) obj6.get("FriendNick");
								rObj.put("Type", "newchat2");
								rObj.put("ChatName", chatName);
								rObj.put("FriendNick", nick);
								array2.add(rObj);
							}
							st.send(array2.toJSONString());
						} else {
							rs.next();
							String chatCode = rs.getString("chat_code");
							sql = "select chat_log, user_code, time from chat_logs where chat_code = " + chatCode
									+ "ORDER BY time";
							pstmt2 = con.prepareStatement(sql);
							rs2 = pstmt2.executeQuery();
							while (rs2.next()) {
								System.out.println("22222222222222222");
								// System.out.println("while����~~");
								JSONObject rObj = new JSONObject();
								String userCode = rs2.getString("user_code");
								String chatLog = rs2.getString("chat_log");
								String time = rs2.getString("time");
								rObj.put("Type", "chatupdateresult");
								rObj.put("UserCode", userCode);
								rObj.put("ChatLog", chatLog);
								rObj.put("Time", time);
								rObj.put("ChatName", chatName);
								rObj.put("ChatCode", chatCode);
								array.add(rObj);
							}
							connectionManger.closeDB(pstmt2, rs2);
							st.send(array.toJSONString());
//							System.out.println(array);
//							System.out.println(array.size());
							array.clear();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						connectionManger.closeDB(pstmt, rs);
					}

				}

			}
		} catch (org.json.simple.parser.ParseException e1) {
			e1.printStackTrace();
		}
	}
}