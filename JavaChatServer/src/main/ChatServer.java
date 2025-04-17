//JavaObjServer.java ObjectStream 기반 채팅 Server
package main;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

import data.ChatMsg;
import data.ChatRoom;

public class ChatServer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JTextArea textArea;
	private JTextField txtPortNumber;

	private ServerSocket socket; // 서버소켓
	private Socket client_socket; // accept() 에서 생성된 client 소켓
	private Vector<UserService> UserVec = new Vector<UserService>(); // 연결된 사용자를 저장할 벡터
	// private Vector<UserService> SleepUserVec = new Vector<UserService>(); //
	// logout 상태인 사용자를 저장할 벡터
	private Vector<ChatRoom> ChatRoomVec = new Vector<ChatRoom>(); // 채팅방을 저장할 벡터
	private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
	private int roomId = 1000;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatServer frame = new ChatServer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ChatServer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(resources.Colors.MAIN_BG_COLOR); // bg color
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblbugiIcon = new JLabel(new ImageIcon("src/images/main_logo_small.png"));
		lblbugiIcon.setVerticalAlignment(JLabel.CENTER);
		lblbugiIcon.setBounds(20, 15, 149, 100);
		contentPane.add(lblbugiIcon);

		JLabel lblProjectName = new JLabel("BGUI TALK");
		lblProjectName.setFont(resources.Fonts.MAIN_BOLD_24);
		lblProjectName.setForeground(resources.Colors.MAIN_DARK_BLUE_COLOR);
		lblProjectName.setHorizontalAlignment(JLabel.CENTER);
		lblProjectName.setBounds(180, 30, 150, 30);
		contentPane.add(lblProjectName);
		JLabel lblServer = new JLabel("SERVER");
		lblServer.setFont(resources.Fonts.MAIN_BOLD_24);
		lblServer.setForeground(resources.Colors.MAIN_DARK_BLUE_COLOR);
		lblServer.setHorizontalAlignment(JLabel.CENTER);
		lblServer.setBounds(180, 60, 150, 30);
		contentPane.add(lblServer);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 120, 320, 350);
		scrollPane.setBackground(resources.Colors.MAIN_BG_COLOR);
		contentPane.add(scrollPane);
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);

		JLabel lblPortLabel = new JLabel("Port Number");
		lblPortLabel.setBounds(15, 480, 100, 30);
		contentPane.add(lblPortLabel);

		txtPortNumber = new JTextField();
		txtPortNumber.setHorizontalAlignment(SwingConstants.CENTER);
		txtPortNumber.setText("30000");
		txtPortNumber.setBounds(110, 480, 220, 30);
		contentPane.add(txtPortNumber);
		txtPortNumber.setColumns(10);

		JButton btnServerStart = new JButton("Server Start");
		btnServerStart.setBackground(resources.Colors.MAIN_WHITE_COLOR);
		btnServerStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					socket = new ServerSocket(Integer.parseInt(txtPortNumber.getText()));
				} catch (NumberFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				AppendText("Chat Server Running..");
				btnServerStart.setText("Chat Server Running..");
				btnServerStart.setEnabled(false); // 서버를 더이상 실행시키지 못 하게 막는다
				txtPortNumber.setEnabled(false); // 더이상 포트번호 수정못 하게 막는다
				AcceptServer accept_server = new AcceptServer();
				accept_server.start();
			}
		});
		btnServerStart.setBounds(15, 515, 320, 40);
		contentPane.add(btnServerStart);
	}

	// 새로운 참가자 accept() 하고 user thread를 새로 생성한다.
	class AcceptServer extends Thread {
		// @SuppressWarnings("unchecked")
		public void run() {
			while (true) { // 사용자 접속을 계속해서 받기 위해 while문
				try {
					AppendText("Waiting new clients ...");
					client_socket = socket.accept(); // accept가 일어나기 전까지는 무한 대기중
					AppendText("새로운 참가자 from " + client_socket);
					// User 당 하나씩 Thread 생성
					UserService new_user = new UserService(client_socket);
					UserVec.add(new_user); // 새로운 참가자 배열에 추가
					new_user.start(); // 만든 객체의 스레드 실행
					// AppendText("현재 참가자 수 " + UserVec.size());
				} catch (IOException e) {
					AppendText("accept() error");
					// System.exit(0);
				}
			}
		}
	}

	public void AppendText(String str) {
		// textArea.append("사용자로부터 들어온 메세지 : " + str+"\n");
		textArea.append(str + "\n");
		textArea.setCaretPosition(textArea.getText().length());
	}

	public void AppendObject(ChatMsg msg) {
		// textArea.append("사용자로부터 들어온 object : " + str+"\n");
		textArea.append("code = " + msg.getCode() + "\n");
		textArea.append("id = " + msg.getId() + "\n");
		textArea.append("data = " + msg.getData() + "\n");
		// textArea.append("room id = " + msg.getRoomId() + "\n");
		textArea.setCaretPosition(textArea.getText().length());
	}

	// User 당 생성되는 Thread
	// Read One 에서 대기 -> Write All
	class UserService extends Thread {
		private ObjectInputStream ois;
		private ObjectOutputStream oos;

		private Socket client_socket;
		private Vector<UserService> user_vc; // 사용자 벡터

		public String UserName = ""; // 사용자 이름
		public String UserStatus = "O"; // 사용자 연결 상태
		public ImageIcon UserProfileImg = new ImageIcon("src/images/profile_default.png");
		public ImageIcon UserProfileImg_resized = new ImageIcon("src/images/profile_default.png"); // 50

		private Vector<String> friendNameVec = new Vector<String>(); // 해당 유저의 친구 리스트
		private Vector<String> friendWaitVec = new Vector<String>(); // 나 -> 유저 (친구요청) 답변 기다리기 위한 이름 리스트
		private Vector<String> friendRecvVec = new Vector<String>(); // 유저 -> 나 (친구요청) 받은 상대방 이름 리스트
		private Vector<ChatRoom> userRoomVec = new Vector<ChatRoom>(); // 내가 활동중인 채팅방 벡터

		public UserService(Socket client_socket) {
			// TODO Auto-generated constructor stub
			// 매개변수로 넘어온 자료 저장
			this.client_socket = client_socket;
			this.user_vc = UserVec;

			try {
				oos = new ObjectOutputStream(client_socket.getOutputStream());
				oos.flush();
				ois = new ObjectInputStream(client_socket.getInputStream());

			} catch (Exception e) {
				AppendText("userService error");
			}
		}

		public void Login() {
			AppendText("새로운 접속자 " + UserName + " 로그인.");
			String msg = "[" + UserName + "]님이 입장 하였습니다.\n";
		}

		public void Logout() {
			String msg = "[" + UserName + "]님이 퇴장 하였습니다.\n";
			UserStatus = "S";
			// if(!SleepUserVec.contains(this))
			// SleepUserVec.addElement(this); // 서버가 관리하는 Sleep 유저 리스트에 추가

			// 다른 유저들의 프로그램에 해당 유저가 로그아웃 했다는 표시를 함
			// WriteOthersObject(new ChatMsg(UserName, "610", "userListRefresh")); // 친구화면
			// 리스트 수정
			// WriteOthersObject(new ChatMsg(UserName, "710", "friendListRefresh")); // 접속자
			// 화면 리스트 수정
			AppendText("접속자 " + "[" + UserName + "] 종료. 현재 사용자 수 " + UserVec.size());
		}

		// 모든 User들에게 방송. 각각의 UserService Thread의 WriteONe() 을 호출한다.
		public void WriteAll(String str) {
			for (int i = 0; i < user_vc.size(); i++) {
				UserService user = (UserService) user_vc.elementAt(i);
				if (user.UserStatus == "O")
					user.WriteOne(str);
			}
		}

		// 모든 User들에게 Object를 방송. 채팅 message와 image object를 보낼 수 있다
		public void WriteAllObject(Object ob) {
			for (int i = 0; i < user_vc.size(); i++) { // 모든 user들에 대해
				UserService user = (UserService) user_vc.elementAt(i); // 각 user들의 객체를 가져와
				if (user.UserStatus == "O") {// 그 user가 Online 상태일 경우에만
					user.WriteOneObject(ob); // user마다 message를 전송
				}
			}
		}

		// 나를 제외한 User들에게 방송. 각각의 UserService Thread의 WriteONe() 을 호출한다.
		public void WriteOthers(String str) {
			for (int i = 0; i < user_vc.size(); i++) {
				UserService user = (UserService) user_vc.elementAt(i);
				if (user != this && user.UserStatus == "O")
					user.WriteOne(str);
			}
		}

		public void WriteOthersObject(Object ob) {
			for (int i = 0; i < user_vc.size(); i++) {
				UserService user = (UserService) user_vc.elementAt(i);
				if (user != this && user.UserStatus == "O")
					user.WriteOneObject(ob);
			}
		}

		// Windows 처럼 message 제외한 나머지 부분은 NULL 로 만들기 위한 함수
		public byte[] MakePacket(String msg) {
			byte[] packet = new byte[BUF_LEN];
			byte[] bb = null;
			int i;
			for (i = 0; i < BUF_LEN; i++)
				packet[i] = 0;
			try {
				bb = msg.getBytes("euc-kr");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (i = 0; i < bb.length; i++)
				packet[i] = bb[i];
			return packet;
		}

		// UserService Thread가 담당하는 Client 에게 1:1 전송
		public void WriteOne(String msg) {
			try {
				ChatMsg obcm = new ChatMsg("SERVER", "200", msg);
				oos.writeObject(obcm);
			} catch (IOException e) {
				AppendText("dos.writeObject() error - WriteOne");
				try {
					ois.close();
					oos.close();
					client_socket.close();
					client_socket = null;
					ois = null;
					oos = null;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Logout(); // 에러가난 현재 객체를 벡터에서 지운다
			}
		}

		// 귓속말 전송
		public void WritePrivate(String msg) {
			try {
				ChatMsg obcm = new ChatMsg("귓속말", "200", msg);
				oos.writeObject(obcm); // send
			} catch (IOException e) {
				AppendText("dos.writeObject() error - WritePrivate");
				try {
					oos.close();
					client_socket.close();
					client_socket = null;
					ois = null;
					oos = null;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Logout(); // 에러가난 현재 객체를 벡터에서 지운다
			}
		}

		public void WriteOneObject(Object ob) {
			try {
				oos.writeObject(ob);
				oos.reset();
			} catch (IOException e) {
				AppendText("oos.writeObject(ob) error - WriteOneObject");
				try {
					ois.close();
					oos.close();
					client_socket.close();
					client_socket = null;
					ois = null;
					oos = null;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Logout();
			}
		}

		// client의 list 화면에 user list를 출력
		public void WriteOneUserList(Object obcm) {
			try {
				oos.writeObject(obcm);
			} catch (IOException e) {
				AppendText("dos.writeObject() error - WriteOneUserList");
				try {
					ois.close();
					oos.close();
					client_socket.close();
					client_socket = null;
					ois = null;
					oos = null;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Logout(); // 에러가난 현재 객체를 벡터에서 지운다
			}
		}

		// 현재 시간 계산하여 출력
		private String calcTime() {
			String t;
			LocalTime nowTime = LocalTime.now();
			int hour = nowTime.getHour();
			int minute = nowTime.getMinute();
			String m;
			if (minute < 10) {
				m = "0" + minute;
			} else {
				m = "" + minute;
			}
			if (hour < 12) {
				t = "오전 " + hour + ":" + m;
			} else if (hour == 12) {
				t = "오후 12:" + m;
			} else {
				t = "오후 " + (hour - 12) + ":" + m;
			}
			return t;
		}

		public void run() {
			while (true) { // 사용자 접속을 계속해서 받기 위해 while문
				try {
					Object obect = null;
					String msg = null;
					ChatMsg cm = null;
					if (socket == null)
						break;
					try {
						obect = ois.readObject();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return; // run
					}
					if (obect == null)
						break;

					if (obect instanceof ChatMsg) {
						cm = (ChatMsg) obect;
						AppendObject(cm);
					} else
						continue;

					if (cm.getCode().equals("100")) { // login
						UserName = cm.getId();
						UserStatus = "O"; // Online 상태

						for (int i = 0; i < UserVec.size(); i++) {
							UserService user = UserVec.elementAt(i);
							if (this != user && user.UserName.equals(this.UserName)) { // 해당 유저와 닉네임이 동일하면,
								this.UserProfileImg = user.UserProfileImg; // 프로필 이미지 가져오기
								this.UserProfileImg_resized = user.UserProfileImg_resized;
								this.friendNameVec = user.friendNameVec; // 친구 이름 리스트 가져오기
								this.friendRecvVec = user.friendRecvVec; // 친구요청 승낙 기다림 리스트 가져오기
								this.friendWaitVec = user.friendWaitVec; // 친구요청 추가 전 리스트 가져오기
								// this.userRoomVec = user.userRoomVec;
								UserVec.remove(user);
								user_vc = UserVec;
							}
						}

						//System.out.println(user_vc.size());
						// 가지고 있던 프로필 이미지로 ui setting
//						ChatMsg cm2 = new ChatMsg(UserName, "500", "profileImg"); 
//						cm2.setProfileImg_ori(UserProfileImg);
//						cm2.setProfileImg_resized(UserProfileImg_resized);
//						WriteOneObject(cm2); 
//						Login();

						ChatMsg cm2 = new ChatMsg(UserName, "100", "login");
						cm2.setProfileImg_ori(UserProfileImg);
						cm2.setProfileImg_resized(UserProfileImg_resized);
						WriteOneObject(cm2);
						Login();

					} 
//					else if (cm.getCode().equals("200")) { // message
//						msg = String.format("[%s] %s", cm.getId(), cm.getData());
//						AppendText(msg); // server 화면에 출력
//						String[] args = msg.split(" "); // 단어들을 분리한다.
//
//						if (args.length == 1) { // Enter key 만 들어온 경우 Wake up 처리만 한다.
//							UserStatus = "O";
//
//						} else { // 일반 채팅 메시지
//							UserStatus = "O";
//
//							for (int i = 0; i < ChatRoomVec.size(); i++) {
//								ChatRoom r = ChatRoomVec.elementAt(i);
//								if (r.getRoomId() == cm.getRoomId()) {
//
//									String lastTime = calcTime();
//									String lastMsg = "/" + cm.getData();
//									for (int j = 0; j < user_vc.size(); j++) {
//										UserService user = user_vc.elementAt(j);
//										if (r.getUserList().contains(user.UserName) && user.UserStatus.matches("O")) {
//											cm.setProfileImg_resized(UserProfileImg_resized);
//											cm.setProfileImg_ori(UserProfileImg);
//											user.WriteOneObject(cm);
//
//											ChatMsg cm2 = new ChatMsg(UserName, "810", lastTime + lastMsg); // data :
//																											// lastTime/lastMsg
//											cm2.setRoomId(cm.getRoomId());
//											user.WriteOneObject(cm2);
//										}
//									}
//
//									r.addChatMsg(cm);
//									r.setLastTime(lastTime);
//									r.setLastMsg(cm.getData());
//								}
//							}
//						}
//					} else if (cm.getCode().equals("300")) { // image 보내기
//						for (int i = 0; i < ChatRoomVec.size(); i++) {
//							ChatRoom r = ChatRoomVec.elementAt(i);
//							if (r.getRoomId() == cm.getRoomId()) {
//
//								String lastTime = calcTime();
//								String lastMsg = "/(사진)";
//								for (int j = 0; j < user_vc.size(); j++) {
//									UserService user = user_vc.elementAt(j);
//
//									if (r.getUserList().contains(user.UserName) && user.UserStatus.matches("O")) {
//										cm.setProfileImg_resized(UserProfileImg_resized);
//										cm.setProfileImg_ori(UserProfileImg);
//										user.WriteOneObject(cm);
//
//										ChatMsg cm2 = new ChatMsg(UserName, "810", lastTime + lastMsg); // data :
//																										// lastTime/lastMsg
//										cm2.setRoomId(cm.getRoomId());
//										user.WriteOneObject(cm2);
//									}
//								}
//								r.addChatMsg(cm);
//								r.setLastTime(lastTime);
//								r.setLastMsg("(사진)");
//							}
//						}
//
//					} else if (cm.getCode().equals("400")) { // 이모티콘 보내기
//						for (int i = 0; i < ChatRoomVec.size(); i++) {
//							ChatRoom r = ChatRoomVec.elementAt(i);
//							if (r.getRoomId() == cm.getRoomId()) {
//
//								String lastTime = calcTime();
//								String lastMsg = "/" + cm.getData();
//								for (int j = 0; j < user_vc.size(); j++) {
//									UserService user = user_vc.elementAt(j);
//									if (r.getUserList().contains(user.UserName) && user.UserStatus.matches("O")) {
//										cm.setProfileImg_resized(UserProfileImg_resized);
//										cm.setProfileImg_ori(UserProfileImg);
//										user.WriteOneObject(cm);
//
//										ChatMsg cm2 = new ChatMsg(UserName, "810", lastTime + lastMsg); // data :
//																										// lastTime/lastMsg
//										cm2.setRoomId(cm.getRoomId());
//										user.WriteOneObject(cm2);
//									}
//								}
//								r.addChatMsg(cm);
//								r.setLastTime(lastTime);
//								r.setLastMsg(cm.getData());
//							}
//						}
//					} 
					else if (cm.getCode().equals("500")) { // user profile image set
						this.UserProfileImg = cm.getProfileImg_ori();
						this.UserProfileImg_resized = cm.getProfileImg_resized();

						for (int i = 0; i < user_vc.size(); i++) {
							UserService user = (UserService) user_vc.elementAt(i);
							if (user.UserStatus.equals("O")) {
								user.WriteOneObject(new ChatMsg(user.UserName, "610", "UserListRefresh"));
								user.WriteOneObject(new ChatMsg(user.UserName, "710", "friendListRefresh"));
							}

						}
						
						// 채팅쪽 완성하면 주석 제거하기
//						for (int j = 0; j < ChatRoomVec.size(); j++) {
//							ChatRoom cr = ChatRoomVec.elementAt(j);
//							if (cr.getUserList().contains(UserName)) {
//								for (int k = 0; k < cr.chatMsgs.size(); k++) {
//									ChatMsg c = cr.chatMsgs.elementAt(k);
//									if (c.getId().equals(UserName)) {
//										c.setProfileImg_resized(UserProfileImg_resized);
//									}
//								}
//							}
//						}
					} else if (cm.getCode().equals("510")) { // original profile image
						String[] data = cm.getId().split("\\["); // "", "user1]"
						data = data[1].split("\\]"); // "user1"
						for (int i = 0; i < user_vc.size(); i++) {
							UserService user = user_vc.elementAt(i);
							if (user.UserName.equals(data[0])) {
								ChatMsg c = new ChatMsg(UserName, "510", "original image");
								c.setProfileImg_ori(user.UserProfileImg);
								WriteOneObject(c);
							}
						}
					} else if (cm.getCode().equals("600")) { // user list 출력을 위해 user 정보 보내기
						
						// 내 사용자 리스트 
						ChatMsg up = new ChatMsg(this.UserName, "600", this.UserName + "/me");
						up.setProfileImg_ori(this.UserProfileImg);
						WriteOneObject(up); // 내 profile 정보 보내기

						for (int i = 0; i < user_vc.size(); i++) {
							UserService user = (UserService) user_vc.elementAt(i);

							// 내가 아닌 다른 사용자와의 관계 데이터 전송
							if (!(user.UserName.equals(this.UserName))) {
								up = new ChatMsg(this.UserName, "600", "");
								up.setProfileImg_ori(user.UserProfileImg);

								if (friendNameVec.contains(user.UserName)) { // 친구일 경우
									up.setData(user.UserName + "/friend");
								} else if (friendWaitVec.contains(user.UserName)) { // 친구x, 친구 신첨함
									up.setData(user.UserName + "/wait");

								} else if (friendRecvVec.contains(user.UserName)) { // 친구x, 친구신청 받음
									up.setData(user.UserName + "/recv");
								} else { // 친구x, 친구 신청x
									up.setData(user.UserName + "/none");
								}
								WriteOneObject(up);
							}

						}
						
						if (cm.getData().equals("all")) { // "all" : 다른 유저들의 사용자 리스트도 새로고침
							WriteOthersObject(new ChatMsg(UserName, "600", "refresh"));
						}
						
					} else if (cm.getCode().equals("700")) { // 친구 list 출력

						// 내 친구 리스트 전송
						ChatMsg fp;
						for (int i = 0; i < user_vc.size(); i++) {
							UserService user = (UserService) user_vc.elementAt(i);
							if (user != this) {
								if (friendNameVec.contains(user.UserName)) { // 친구일 경우
									fp = new ChatMsg(this.UserName, "700", user.UserName+"/friend");
									fp.setProfileImg_ori(user.UserProfileImg);
									fp.setStatus(user.UserStatus);
									WriteOneObject(fp);
								}
								if (friendRecvVec.contains(user.UserName)) { // 친구x, 신청 받음 상태o
									fp = new ChatMsg(this.UserName, "700", user.UserName+"/recv");
									fp.setProfileImg_ori(user.UserProfileImg);
									WriteOneObject(fp);
								}
								
							}
						}

						
						if (cm.getData().equals("all")) { // 내 친구 리스트 처음 요청 or 재요청
							WriteOthersObject(new ChatMsg(UserName, "700", "refresh"));
						}

					} else if (cm.getCode().equals("710")) { // 친구 추가 신청
						// 신청을 받은 user의 name
						String friend = cm.getData();

						for (int i = 0; i < user_vc.size(); i++) {
							UserService user = (UserService) user_vc.elementAt(i);
							if (user.UserName.equals(friend)) {
								this.friendWaitVec.add(friend); // 나는 상대방(friend)의 승낙을 기다림(wait)
								user.friendRecvVec.add(UserName); // 상대방(friend)는 나의 친구 요청을 받음(receive)

								// 화면에 바로 업데이트
								if (user.UserStatus.equals("O")) { // 상대방의 친구 목록 새로고침(내가 보낸 친구 요청을 버튼으로 바로 띄우기 위해)
									user.WriteOneObject(new ChatMsg(user.UserName, "700", "refresh"));
									user.WriteOneObject(new ChatMsg(user.UserName, "600", "refresh"));
								}
							}
						}
					} else if (cm.getCode().equals("720")) { // 친구 추가 승낙

						for (int i = 0; i < user_vc.size(); i++) {
							UserService user = (UserService) user_vc.elementAt(i);
							if (user.UserName.equals(cm.getData())) {
								this.friendRecvVec.remove(this.friendRecvVec.indexOf(user.UserName));
								user.friendWaitVec.remove(user.friendWaitVec.indexOf(this.UserName));
								if (user.friendRecvVec.contains(this.UserName))
									user.friendRecvVec.remove(user.friendRecvVec.indexOf(this.UserName));
								if (this.friendWaitVec.contains(user.UserName))
									this.friendWaitVec.remove(this.friendWaitVec.indexOf(user.UserName));

								this.friendNameVec.add(user.UserName);
								user.friendNameVec.add(this.UserName);

								// 화면에 바로 업데이트
								if (user.UserStatus.equals("O")) {
									user.WriteOneObject(new ChatMsg(user.UserName, "600", "refresh"));
									user.WriteOneObject(new ChatMsg(user.UserName, "700", "refresh"));
								}
								this.WriteOneObject(new ChatMsg(UserName, "700", "refresh"));
								// 상대방의 친구 목록과 접속자 목록 새로고침, 나의 친구 목록 새로고침
							}
						}
					} else if (cm.getCode().equals("730")) { // 친구 추가 거절
						for (int i = 0; i < user_vc.size(); i++) {
							UserService user = (UserService) user_vc.elementAt(i);
							if (user.UserName.matches(cm.getData())) {
								this.friendRecvVec.remove(this.friendRecvVec.indexOf(user.UserName));
								user.friendWaitVec.remove(user.friendWaitVec.indexOf(this.UserName));
								if (user.friendRecvVec.contains(this.UserName))
									user.friendRecvVec.remove(user.friendRecvVec.indexOf(this.UserName));
								if (this.friendWaitVec.contains(user.UserName))
									this.friendWaitVec.remove(this.friendWaitVec.indexOf(user.UserName));

								// 화면에 바로 업데이트
								if (user.UserStatus.matches("O")) {// 상대방의 친구 목록과 접속자 목록 새로고침
									user.WriteOneObject(new ChatMsg(user.UserName, "600", "refresh"));
									user.WriteOneObject(new ChatMsg(user.UserName, "700", "refresh"));
								}

							}
						}

					} else if (cm.getCode().equals("800")) { // 채팅방 새로고침(전체)
						
						
						Vector<ChatRoom> vec = new Vector<ChatRoom>();
						for(ChatRoom room : ChatRoomVec) {
							if(room.getUserList().contains(UserName)) {
								vec.addElement(room);
							}
						}
						
						ChatMsg cm2 = new ChatMsg(UserName, "800", cm.getData()); // "refresh, all"
						cm2.setRoomVec(vec);
						WriteOneObject(cm2);
						
					}

					else if (cm.getCode().equals("810")) { // 채팅방 만들기
						
						// 여기서 output 할때가 문제...
						// ChatMsg, ChatRoom 각 클래스가 내용 같은지 확인!!
						ChatRoom cr = new ChatRoom(roomId++, cm.getData()); // Data : user list
						ChatRoomVec.add(cr);

						for (int i = 0; i < user_vc.size(); i++) { // 모든 유저들에 대해서
							UserService user = (UserService) user_vc.elementAt(i);
							if (cm.getData().contains(user.UserName)) { // 초대한 유저와 초대된 유저들만
								
								// 채팅방 리스트에 새로 추가한 채팅방 아이템 추가
								ChatMsg mr = new ChatMsg(user.UserName, "810", "add room", cr);
								if (user.UserStatus.matches("O")) // 친구는 온라인 상태라면 채팅방 정보 넘김
									user.WriteOneObject(mr);
							}
						}
					}
//					else if (cm.getCode().matches("811")) { // 변경된 lastTime, lastMsg를 다른 유저의 리스트에도 출력하기 위한 채팅방 리스트
//																// 새로고침
//
//						for (int i = 0; i < ChatRoomVec.size(); i++) {
//							ChatRoom cr = ChatRoomVec.elementAt(i);
//							if (cr.getRoomId() == cm.getRoomId()) {
//								String[] data = new String[2];
//								data = cm.getData().split("/");
//								cr.setLastTime(data[0]);
//								cr.setLastMsg(data[1]);
//								System.out.println("RoomId:" + cr.getRoomId() + " " + data[0] + " " + data[1]);
//							}
//						}
//
//						for (int j = 0; j < user_vc.size(); j++) { // 모든 유저들에 대해서
//							UserService user = (UserService) user_vc.elementAt(j);
//							if (cm.getUserList().contains(user.UserName) && user.UserStatus.matches("O")) { // 초대한 유저와
//																											// 초대된 유저들만
//								ChatMsg cm2 = new ChatMsg(UserName, "810", cm.getData()); // data : lastTime/lastMsg
//								cm2.setRoomId(cm.getRoomId());
//								user.WriteOneObject(cm2);
//							}
//						}
//
//					} 
				else if (cm.getCode().equals("830")) { // 체크 박스를 위한 나의 친구 리스트 보내기
						String friendList = "";
						for (int i = 0; i < friendNameVec.size(); i++) {
							friendList = friendList + friendNameVec.elementAt(i) + " ";
						}
						ChatMsg fl = new ChatMsg(UserName, "830", friendList);
						WriteOneObject(fl);
//					} else if (cm.getCode().equals("830")) {
//						// 채팅방 리스트 보내기
//						if (ChatRoomVec.size() != 0) {
//							for (int i = 0; i < ChatRoomVec.size(); i++) {
//								ChatRoom cr = ChatRoomVec.elementAt(i);
//								if (cr.getUserList().contains(UserName)) {
//									ChatMsg roomData = new ChatMsg(UserName, "830",
//											cr.getLastTime() + "/" + cr.getLastMsg());
//									roomData.setRoomId(cr.getRoomId());
//									roomData.setUserList(cr.getUserList());
//									WriteOneObject(roomData);
//								}
//							}
//							WriteOneObject(new ChatMsg(UserName, "840", "printChatList"));
//						}
//					} else if (cm.getCode().equals("840")) {
//						// 채팅방 내역 보내기
//						for (int i = 0; i < ChatRoomVec.size(); i++) {
//							ChatRoom cr = ChatRoomVec.elementAt(i);
//							if (cr.getRoomId() == cm.getRoomId()) {
//								if (cr.chatMsgs.size() != 0) {
//									for (int j = 0; j < cr.getChatMsgs().size(); j++) {
//										ChatMsg c = cr.getChatMsgs().elementAt(j);
//
//										WriteOneObject(c);
//									}
//								}
//
//							}
//						}
//					}
				}else if (cm.getCode().equals("900")) { // 종료
						Logout();
					}

				} catch (IOException e) {
					AppendText("ois.readObject() error");
					try {
//						dos.close();
//						dis.close();
						ois.close();
						oos.close();
						client_socket.close();
						Logout(); // 에러가난 현재 객체를 벡터에서 지운다
						break;
					} catch (Exception ee) {
						break;
					} // catch문 끝
				} // 바깥 catch문끝
			} // while
		} // run
	}

}
