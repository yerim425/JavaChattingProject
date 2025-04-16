// JavaObjClientView.java ObjecStram 기반 Client
//실질적인 채팅 창
package main;

import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Vector;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.text.AttributeSet.FontAttribute;

import data.ChatMsg;
import data.ChatRoom;
import main.view.FriendViewPanel;
import main.view.ImgFrame;
import main.view.SettingViewPanel;
import main.view.UserViewPanel;
import main.view.item.FriendProfileItem;

import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import main.chat.ChatClientChat;

//import JavaObjClientView.ImageSendAction;
//import JavaObjClientView.ListenNetwork;
//import JavaObjClientView.TextSendAction;

import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.JList;
import javax.swing.JOptionPane;
import java.awt.SystemColor;

public class ChatClientMain extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ChatClientMain clientView;
	private JPanel contentPane;
	private JPanel viewPanel; // [친구, 채팅, 접속자, 설정] 뷰 패널
	private JPanel bottomPanel; // [친구 채팅 접속자, 설정] 바텀 버튼 패널

	private FriendViewPanel friendViewPanel; // 친구 화면
	private JPanel chatListViewPanel; // 채팅 화면
	private UserViewPanel userViewPanel; // 사용자 화면
	private SettingViewPanel settingViewPanel; // 설정 화면

	private JButton friendViewBtn; // 친구 바텀 버튼
	private JButton chatListViewBtn; // 채팅 바텀 버튼
	private JButton userListViewBtn; // 접속자 바텀 버튼
	private JButton settingViewBtn; // 설정 바텀 버튼

	private JScrollPane friendListScrollPane; // 친구 화면 스크롤
	private JScrollPane chatListScrollPane; // 채팅 화면 스크롤
	private JScrollPane userListScrollPane; // 접속자 화면 스크롤
	private JScrollPane settingScrollPane; // 설정 화면 스크롤
	private JScrollPane checkBoxScrollPane = new JScrollPane();

	// GridBagLayout

	private JPanel friendListScrollPanel; // 친구 리스트 패널
	private JPanel chatListScrollPanel; // 채팅방 리스트 패널
	private JPanel userListScrollPanel; // 접속자 화면 패널
	private JPanel settingScrollPanel; // /???
	private JPanel checkBoxContentPane = new JPanel();
	private JPanel checkBoxListPanel = new JPanel();

	// private JTextField txtInput;
	private String UserName; // 사용자 닉네임
	private String UserStatus; // 접속 상태
	private String IpAddr;
	private String PortN;
	private JButton btnSend;
	private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
	private Socket socket; // 연결소켓

	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	// 친구 화면
	private JLabel lblUserName; // 닉네임
	private JLabel lblmetxt; // "나"
	private JLabel lblfriendtxt; // "친구"

	private Frame frame; // ?
	private FileDialog fd; // 내 프로필 사진 클릭 시 생성
	private JButton profileImgBtn; // 내 프로필 버튼
	private ImageIcon UserProfileImg_ori = new ImageIcon("src/images/profile_default.png"); // original
	private ImageIcon UserProfileImg_resized = new ImageIcon("src/images/profile_default.png"); // 50x50
	////

	private Color backColor = new Color(115, 175, 255);
	private Color darkBlueColor = new Color(5, 0, 120);
	private Color blueColor = new Color(70, 125, 240);

	private int checkBoxPosY = 5;
	private Vector<JCheckBox> checkBoxVec = new Vector<JCheckBox>();
	private Vector<ChatClientChat> chatRoomViewVec = new Vector<ChatClientChat>();
	private Vector<ChatRoom> chatRoomVec = new Vector<ChatRoom>();

	int userListIdx = 0;
	int friendListIdx = 0;
	int chatListIdx = 0;
	int checkBoxIdx = 0;

	String lastMsg;
	String lastTime;

	int check = 0;

	/**
	 * Create the frame.
	 */
	public ChatClientMain(String username, String ip_addr, String port_no) {
		this.setTitle("BUGI TALK");
		this.setResizable(false);
		clientView = this;
		this.UserName = username;
		this.IpAddr = ip_addr;
		this.PortN = port_no;
		this.UserStatus = "O";
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// View 바텀 패널
		bottomPanel = new JPanel();
		bottomPanel.setBackground(resources.Colors.MAIN_BG_COLOR);
		bottomPanel.setBorder(new EmptyBorder(2, 2, 2, 2));
		bottomPanel.setLayout(new GridLayout(1, 3, 3, 3));
		bottomPanel.setBounds(0, 515, 350, 50);

		// View 파텀 버튼 4개 만들기
		friendViewBtn = new JButton(resources.Strings.FRIEND);
		friendViewBtn.setFont(resources.Fonts.MAIN_BOLD_15);
		friendViewBtn.setForeground(resources.Colors.MAIN_DARK_BLUE_COLOR);
		friendViewBtn.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		//friendViewBtn.setSelected(true);
		friendViewBtn.addActionListener(new MyActionListener());
		bottomPanel.add(friendViewBtn);

		chatListViewBtn = new JButton(resources.Strings.CHATTING);
		chatListViewBtn.setFont(resources.Fonts.MAIN_BOLD_15);
		chatListViewBtn.setForeground(resources.Colors.MAIN_WHITE_COLOR);
		chatListViewBtn.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chatListViewBtn.addActionListener(new MyActionListener());
		bottomPanel.add(chatListViewBtn);

		userListViewBtn = new JButton(resources.Strings.USER);
		userListViewBtn.setFont(resources.Fonts.MAIN_BOLD_15);
		userListViewBtn.setForeground(resources.Colors.MAIN_WHITE_COLOR);
		userListViewBtn.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		userListViewBtn.addActionListener(new MyActionListener());
		bottomPanel.add(userListViewBtn);

		settingViewBtn = new JButton(resources.Strings.SETTING);
		settingViewBtn.setFont(resources.Fonts.MAIN_BOLD_15);
		settingViewBtn.setForeground(resources.Colors.MAIN_WHITE_COLOR);
		settingViewBtn.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		settingViewBtn.addActionListener(new MyActionListener());
		bottomPanel.add(settingViewBtn);

		contentPane = new JPanel(null);
		contentPane.setBackground(resources.Colors.MAIN_BG_COLOR);

		contentPane.add(bottomPanel);
		setContentPane(contentPane);

		// view create
		initChatListView();
		// initFriendView();
		
		//repaint();
		
		friendViewPanel = new FriendViewPanel(this, UserName);
		userViewPanel = new UserViewPanel(this, UserName);
		settingViewPanel = new SettingViewPanel(this, UserName);
		//initUserListView();
		// initSettingView();
		
		contentPane.add(friendViewPanel);
		contentPane.add(userViewPanel);
		
		validate();
		repaint();
		
		
		
		try {
			socket = new Socket(ip_addr, Integer.parseInt(port_no));

			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());

			ChatMsg obcm = new ChatMsg(UserName, "100", "Hello(Login)");
			SendObject(obcm);

			ListenNetwork net = new ListenNetwork();
			net.start();

		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(UserName + " : connect error");
		}

	}

	// Server Message를 수신해서 화면에 표시
	class ListenNetwork extends Thread {

		public void run() {
			while (true) {
				try {
					Object obcm = null;
					String msg = null;
					ChatMsg cm;
					ChatMsg up;
					int idx;

					try {
						obcm = ois.readObject();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						break;
					}
					if (obcm == null)
						break;
					if (obcm instanceof ChatMsg) {
						cm = (ChatMsg) obcm;
						msg = String.format("[%s] %s", cm.getId(), cm.getData());

					} else
						continue;

					switch (cm.getCode()) {
					case "100": // 로그인 성공 시 처리 -> 친구 화면 요청
						//friendViewPanel = new FriendViewPanel(ChatClientMain.this, UserName, UserProfileImg_ori);
						//contentPane.add(friendViewPanel);
						// initFriendView();
						friendViewPanel.setMyProfile(cm.getId(), cm.getProfileImg_ori());
						
						// 친구 리스트 요청 (all : 다른 유저들도 내가 로그인한 사실을 알도록 바로 화면 업데이트)
						SendObject(new ChatMsg(UserName, "700", "all")); // 친구 화면 리스트
						SendObject(new ChatMsg(UserName, "600", "all")); // 사용자 화면 리스트
						// ??
						SendObject(new ChatMsg(UserName, "830", "room list"));//?
						
						break;
					case "200": // chat message
						check++;
						// cm.msg를 cm.roomId의 room을 찾아 appendText
						for (int i = 0; i < chatRoomViewVec.size(); i++) {
							ChatClientChat cv = chatRoomViewVec.elementAt(i);
							if (cv.RoomId == cm.getRoomId()) {
								if (cm.getId().matches(UserName)) { // 내가 보낸 msg는 내 화면에서는 오른쪽에 출력
									cv.AppendTextR_name("[" + cm.getId() + "]/" + cm.getTime());
									cv.AppendTextR_msg(cm.getData());
								} else {
									cv.AppendText_name("[" + cm.getId() + "]/" + cm.getTime());
									cv.AppendIcon_text(cm.getProfileImg_resized());
									cv.AppendText_msg(cm.getData());
								}

							}
						}
						break;
					case "300": // Image 첨부
						for (int i = 0; i < chatRoomViewVec.size(); i++) {
							ChatClientChat cv = chatRoomViewVec.elementAt(i);
							if (cv.RoomId == cm.getRoomId()) {
								if (cm.getId().matches(UserName)) {
									cv.AppendTextR_name("[" + cm.getId() + "]/" + cm.getTime());
									cv.AppendImage(cm.getChatImg(), cm.getChatImg_ori());
								} else {
									cv.AppendText_name("[" + cm.getId() + "]/" + cm.getTime());
									cv.AppendIcon_img(cm.getProfileImg_resized());
									cv.AppendImage(cm.getChatImg(), cm.getChatImg_ori());

								}
							}
						}
						break;
					case "400": // Emoticon 보내기
						for (int i = 0; i < chatRoomViewVec.size(); i++) {
							ChatClientChat cv = chatRoomViewVec.elementAt(i);
							if (cv.RoomId == cm.getRoomId()) {
								if (cm.getId().matches(UserName)) {
									cv.AppendTextR_name("[" + cm.getId() + "]/" + cm.getTime());
									cv.AppendImage(cm.getEmoticon(), cm.getEmoticon());
								} else {
									cv.AppendText_name("[" + cm.getId() + "]/" + cm.getTime());
									cv.AppendIcon_img(cm.getProfileImg_resized());
									cv.AppendImage(cm.getEmoticon(), cm.getEmoticon());

								}

							}
						}
						break;
					case "500": // 프로필 사진 변경
						UserProfileImg_ori = cm.getProfileImg_ori();
						UserProfileImg_resized = cm.getProfileImg_resized();

						
						break;

					case "510": // original image
						ImgFrame imgF = new ImgFrame(cm.getProfileImg_ori());
						imgF.setVisible(true);
						break;

					case "600": // 사용자 목록에 출력할 모든 사용자들의 profile 정보를 하나씩 받음
						
						if(cm.getData().equals("refresh")) { // 사용자 리스트 초기화 후 다시 사용자 데이터 요청
							userViewPanel.initUserList();
							SendObject(new ChatMsg(UserName, "600", ""));
							
						}else {
							userViewPanel.addUser(cm);
						}
						
						break;

//					case "610": // 접속자 목록 새로고침
//						//initUserList();
//						SendObject(new ChatMsg(UserName, "600", "User list"));
//						break;

					case "700": // 친구 목록에 출력할 관련 접속자들의 profile 정보를 하나씩 받음
						
						if(cm.getData().equals("refresh")) {
							friendViewPanel.initFriendList();
							SendObject(new ChatMsg(UserName, "700", ""));
						}else {
							friendViewPanel.addFriend(cm);
						}
						
						//((FriendViewPanel) friendViewPanel).addFriend(new FriendProfileItem(friendViewPanel, cm));

						break;

					case "710": // 친구 목록 새로고침 -> 이거 700으로 대체 해도 될 듯..
						// initFriendList();
						friendViewPanel.initFriendList();
						SendObject(new ChatMsg(UserName, "700", "friend list"));
						break;

					case "800": // 채팅방 추가
						// JavaObjClientChat cv = new JavaObjClientChat(myFrame, UserName,
						// cm.getRoomId(), cm.getUserList());
						// chatRoomViewVec.add(cv);
						ChatRoom cr = new ChatRoom(cm.getRoomId(), cm.getUserList());
						chatRoomVec.add(cr);
						ChatClientChat roomView = new ChatClientChat(clientView, UserName, cm.getRoomId(),
								cm.getUserList());
						chatRoomViewVec.add(roomView);

						lastTime = " ";
						lastMsg = "/대화 없음";

						ChatMsg cm2 = new ChatMsg(UserName, "810", lastTime + lastMsg);
						cm2.setRoomId(cm.getRoomId());
						SendObject(cm2);

						break;

					case "810": // 채팅방 추가 또는 미리보기/시간 변경을 위한 채팅방 리스트 새로고침

						String[] lastData = cm.getData().split("/");

						for (int i = 0; i < chatRoomVec.size(); i++) {
							ChatRoom room = chatRoomVec.elementAt(i);
							if (room.getRoomId() == cm.getRoomId()) {
								room.setLastTime(lastData[0]);
								room.setLastMsg(lastData[1]);
							}
						}
						printChatRoomList();
						break;

					case "820": // 채팅방 만들기 위한 친구 리스트
						if (!cm.getData().equals("")) {
							String[] userList = cm.getData().split(" ");
							for (int i = 0; i < userList.length; i++) {
								JCheckBox cb = new JCheckBox(userList[i], false);
								cb.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
								cb.setBounds(5, checkBoxPosY, 80, 25);
								checkBoxListPanel.add(cb);
								checkBoxVec.add(cb);
								checkBoxPosY += 30;
								checkBoxListPanel.revalidate();
								checkBoxListPanel.repaint();
							}
						}
						break;

					case "830": // 채팅 리스트 불러오기
						ChatRoom room = new ChatRoom(cm.getRoomId(), cm.getUserList());
						String[] data = cm.getData().split("/");
						room.setLastTime(data[0]);
						room.setLastMsg(data[1]);
						chatRoomVec.add(room);
						break;

					case "840": // 채팅방 채팅 내역 가져오기
						for (int i = 0; i < chatRoomVec.size(); i++) {
							ChatRoom r = chatRoomVec.elementAt(i);
							ChatClientChat rv = new ChatClientChat(clientView, UserName, r.getRoomId(),
									r.getUserList());
							rv.setBounds(clientView.getLocation().x, clientView.getLocation().y, 350, 500);
							rv.lastMsg = r.getLastMsg();
							rv.lastTime = r.getLastTime();
							chatRoomViewVec.add(rv);

							ChatMsg chat = new ChatMsg(UserName, "840", "chatMsgs"); // 채팅 내역 불러오기
							chat.setRoomId(rv.RoomId);
							SendObject(chat);
						}

						printChatRoomList();
						break;
					}

				} catch (IOException e) {
					System.out.println("ois.readObject() error");
					try {
						ois.close();
						oos.close();
						socket.close();

						break;
					} catch (Exception ee) {
						break;
					} // catch문 끝
				} // 바깥 catch문끝

			}
		}
	}

	public void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
		try {
			oos.writeObject(ob);
			oos.reset();
		} catch (IOException e) {
			// textArea.append("메세지 송신 에러!!\n");
			System.out.println(UserName + " : SendObject Error");
		}
	}

//	private void initFriendView() {
//		friendViewPanel = new JPanel();
//		friendViewPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
//		friendViewPanel.setBounds(0, 0, 335, 400);
//		friendViewPanel.setBackground(backColor);
//		friendViewPanel.setLayout(null);
//		friendViewPanel.setVisible(true);
//		contentPane.add(friendViewPanel);
//
//		lblmetxt = new JLabel(" 나");
//		lblmetxt.setFont(new Font("맑은 고딕", Font.BOLD, 16));
//		lblmetxt.setForeground(Color.white);
//		lblmetxt.setBackground(blueColor);
//		lblmetxt.setOpaque(true);
//		lblmetxt.setBounds(10, 10, 315, 25);
//		friendViewPanel.add(lblmetxt);
//
//		// 나의 프로필 사진, 버튼에 이미지를 삽입
//		profileImgBtn = new JButton(); // vector(0)에 저장해보기
//		profileImgBtn.setBounds(10, 45, 50, 50); // 38
//		profileImgBtn.setIcon(UserProfileImg_resized);
//		friendViewPanel.add(profileImgBtn);
//
//		profileImgBtn.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				frame = new Frame("프로필 사진 설정"); // 프레임을 하나 만들고
//				fd = new FileDialog(frame, "이미지 선택", FileDialog.LOAD); // 이미지 파일 선택하면
//				fd.setVisible(true);
//				UserProfileImg_ori = new ImageIcon(fd.getDirectory() + fd.getFile());
//				UserProfileImg_resized = profileImgResize(UserProfileImg_ori, 50);
//				profileImgBtn.setIcon(UserProfileImg_resized);
//				// ChatProfileIcon_resized = profileImgResize(UserProfileImg_ori, 40);
//				ChatMsg obcm = new ChatMsg(UserName, "500", "change profile img"); // 프로필 사진 변경
//				obcm.setProfileImg_ori(UserProfileImg_ori);
//				obcm.setProfileImg_resized(UserProfileImg_resized);
//				SendObject(obcm);
//			}
//		});
//
//		lblUserName = new JLabel(this.UserName);
//		lblUserName.setFont(new Font("맑은 고딕", Font.BOLD, 16));
//		lblUserName.setForeground(darkBlueColor);
//		lblUserName.setBounds(75, 55, 100, 30);
//		friendViewPanel.add(lblUserName);
//
//		lblfriendtxt = new JLabel(" 친구");
//		lblfriendtxt.setFont(new Font("맑은 고딕", Font.BOLD, 16));
//		lblfriendtxt.setForeground(Color.white);
//		lblfriendtxt.setBackground(blueColor);
//		lblfriendtxt.setOpaque(true);
//		lblfriendtxt.setBounds(10, 105, 315, 25);
//		friendViewPanel.add(lblfriendtxt);
//
//		friendListScrollPanel = new JPanel(new GridBagLayout());
//		friendListScrollPanel.setBackground(backColor);
//		GridBagConstraints gbc = new GridBagConstraints();
//		gbc.gridwidth = GridBagConstraints.REMAINDER;
//		gbc.weightx = 1;
//		gbc.weighty = 1;
//		JPanel p = new JPanel();
//		p.setBackground(backColor);
//		friendListScrollPanel.add(p, gbc);
//
//		friendListScrollPane = new JScrollPane(friendListScrollPanel);
//		friendListScrollPane.setBounds(10, 140, 320, 260);
//		friendListScrollPane.setBackground(backColor);
//		// userListScrollPane.setPreferredSize(new Dimension(300, 330));
//		friendViewPanel.add(friendListScrollPane);
//
//		SendObject(new ChatMsg(UserName, "600", "all"));
//		SendObject(new ChatMsg(UserName, "700", "all"));
//	}

//	private void initFriendList() {
//		friendListScrollPanel = new JPanel(new GridBagLayout());
//		friendListScrollPanel.setBackground(backColor);
//		GridBagConstraints gbc = new GridBagConstraints();
//		gbc.gridwidth = GridBagConstraints.REMAINDER;
//		gbc.weightx = 1;
//		gbc.weighty = 1;
//		JPanel p = new JPanel();
//		p.setBackground(backColor);
//		friendListScrollPanel.add(p, gbc);
//		friendListScrollPane.setViewportView(friendListScrollPanel);
//
//		friendListIdx = 0;
//	}

	private void initChatListView() {
		chatListViewPanel = new JPanel();
		chatListViewPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		chatListViewPanel.setBounds(0, 0, 335, 400);
		chatListViewPanel.setBackground(backColor);
		chatListViewPanel.setLayout(null);
		chatListViewPanel.setVisible(false);
		contentPane.add(chatListViewPanel);

		JLabel lblchatList = new JLabel(" 채팅방 목록");
		lblchatList.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lblchatList.setForeground(Color.white);
		lblchatList.setBackground(blueColor);
		lblchatList.setOpaque(true);
		lblchatList.setBounds(10, 10, 235, 25);
		chatListViewPanel.add(lblchatList);

		JButton makeRoomBtn = new JButton("<HTML><body style='text-align:center;'>채팅방<br>만들기</body></HTML>");
		makeRoomBtn.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		makeRoomBtn.setBackground(Color.WHITE);
		makeRoomBtn.setForeground(blueColor);
		makeRoomBtn.setOpaque(true);
		makeRoomBtn.setBounds(247, 5, 72, 43);
		makeRoomBtn.addActionListener(new ActionListener() { // 채팅방 만들기
			public void actionPerformed(ActionEvent e) {

				CheckBoxFrame cbView = new CheckBoxFrame();
				cbView.setVisible(true);

				checkBoxVec.removeAllElements();
				checkBoxPosY = 5;
				ChatMsg fl = new ChatMsg(UserName, "820", "friend list"); // 체크 박스로 리스트 출력할 친구 리스트 요청
				SendObject(fl);
			}
		});
		chatListViewPanel.add(makeRoomBtn);

		chatListScrollPanel = new JPanel(new GridBagLayout());
		chatListScrollPane = new JScrollPane(chatListScrollPanel);
		chatListScrollPane.setBounds(10, 50, 320, 350);
		chatListScrollPane.setBackground(backColor);
		chatListViewPanel.add(chatListScrollPane);
	}

//	private void initUserListView() {
//		userViewPanel = new JPanel();
//		userViewPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
//		userViewPanel.setBounds(0, 0, 335, 400);
//		userViewPanel.setBackground(backColor);
//		userViewPanel.setLayout(null);
//		userViewPanel.setVisible(false);
//		contentPane.add(userViewPanel);
//
//		JLabel lbluserList = new JLabel(" 접속자 목록");
//		lbluserList.setFont(new Font("맑은 고딕", Font.BOLD, 16));
//		lbluserList.setForeground(Color.white);
//		lbluserList.setBackground(blueColor);
//		lbluserList.setOpaque(true);
//		lbluserList.setBounds(10, 10, 315, 25);
//		userViewPanel.add(lbluserList);
//
//		userListScrollPanel = new JPanel(new GridBagLayout());
//		// userListScrollPanel.setBackground(backColor);
//		// GridBagConstraints gbc = new GridBagConstraints();
//		// gbc.gridwidth = GridBagConstraints.REMAINDER;
//		// gbc.weightx = 1;
//		// gbc.weighty = 1;
//		// userListScrollPanel.add(new JPanel(), gbc);
//
//		userListScrollPane = new JScrollPane(userListScrollPanel);
//		userListScrollPane.setBounds(10, 50, 320, 350);
//		userListScrollPane.setBackground(backColor);
//		// userListScrollPane.setPreferredSize(new Dimension(300, 330));
//		userViewPanel.add(userListScrollPane);
//	}

//	private void initUserList() {
//		userListScrollPanel = new JPanel(new GridBagLayout());
//		userListScrollPanel.setBackground(backColor);
//		GridBagConstraints gbc = new GridBagConstraints();
//		gbc.gridwidth = GridBagConstraints.REMAINDER;
//		gbc.weightx = 1;
//		gbc.weighty = 1;
//		JPanel p = new JPanel();
//		p.setBackground(backColor);
//		userListScrollPanel.add(p, gbc);
//		userListScrollPane.setViewportView(userListScrollPanel);
//
//		userListIdx = 0;
//	}

	/*
	 * private void initSettingView() { settingView = new JPanel();
	 * settingView.setBorder(new EmptyBorder(5, 5, 5, 5)); settingView.setBounds(0,
	 * 0, 335, 400); settingView.setBackground(backColor);
	 * settingView.setLayout(null); settingView.setVisible(false);
	 * contentPane.add(settingView);
	 * 
	 * JLabel lbltxt = new JLabel(" 설정"); lbltxt.setFont(new Font("맑은 고딕",
	 * Font.BOLD, 16)); lbltxt.setForeground(Color.white);
	 * lbltxt.setBackground(blueColor); lbltxt.setOpaque(true); lbltxt.setBounds(10,
	 * 10, 315, 25); settingView.add(lbltxt);
	 * 
	 * changeBackColorBtn = new JButton("화면 색상 변경"); changeBackColorBtn.setFont(new
	 * Font("맑은 고딕", Font.BOLD, 15)); changeBackColorBtn.setForeground(blueColor);
	 * changeBackColorBtn.setBackground(Color.WHITE);
	 * changeBackColorBtn.setBounds(10, 50, 150, 25);
	 * settingView.add(changeBackColorBtn); }
	 */

	// 화면 전환
	private class MyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();

			friendViewBtn.setForeground(resources.Colors.MAIN_WHITE_COLOR);
			chatListViewBtn.setForeground(resources.Colors.MAIN_WHITE_COLOR);
			userListViewBtn.setForeground(resources.Colors.MAIN_WHITE_COLOR);
			settingViewBtn.setForeground(resources.Colors.MAIN_WHITE_COLOR);
			
			if (b.getText().equals(resources.Strings.FRIEND)) {
				
				
				friendViewPanel.initFriendList();

				ChatMsg cm = new ChatMsg(UserName, "700", "refresh"); // 친구 list 출력 요청
				SendObject(cm);
				//friendViewPanel.initFriendList();
				viewPanel = friendViewPanel;
				
			} else if (b.getText().equals(resources.Strings.CHATTING)) {

				// 채팅 list 출력
				printChatRoomList();

				viewPanel = chatListViewPanel;

			} else if (b.getText().equals(resources.Strings.USER)) {


				// user list 요청
				userViewPanel.initUserList();

				ChatMsg cm = new ChatMsg(UserName, "600", "refresh");
				SendObject(cm);

				viewPanel = userViewPanel;

			} else if (b.getText().equals(resources.Strings.SETTING)) {

				viewPanel = settingViewPanel;
			}

			
			b.setForeground(resources.Colors.MAIN_DARK_BLUE_COLOR);
			bottomPanel.repaint();
			
			friendViewPanel.setVisible(false);
			chatListViewPanel.setVisible(false);
			userViewPanel.setVisible(false);
			settingViewPanel.setVisible(false);
			viewPanel.setVisible(true);

			contentPane.add(viewPanel);
			viewPanel.revalidate();
			viewPanel.repaint();

		}
	}

//	private void printFriendProfile(ChatMsg user) { // friend view에 친구 프로필 출력하기
//
//		JPanel panel = new JPanel(new FlowLayout());
//		panel.setBackground(backColor);
//
//		JButton profileImgBtn = new JButton();
//		profileImgBtn.setPreferredSize(new Dimension(50, 50));
//		profileImgBtn.setIcon(user.getProfileImg_resized());
//		profileImgBtn.setBackground(Color.white);
//		profileImgBtn.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) { // 친구 프로필 사진의 원본을 보여줌
//				ImgFrame imgFrame = new ImgFrame(user.getProfileImg_ori());
//				imgFrame.setVisible(true);
//			}
//
//		});
//		panel.add(profileImgBtn);
//
//		JLabel lbluserName = new JLabel("  " + user.getId());
//		lbluserName.setFont(new Font("맑은 고딕", Font.BOLD, 16));
//		lbluserName.setForeground(darkBlueColor);
//		lbluserName.setPreferredSize(new Dimension(115, 30));
//		panel.add(lbluserName);
//
//		JButton friendBtn = new JButton();
//		friendBtn.setPreferredSize(new Dimension(125, 25));
//		friendBtn.setFont(new Font("맑은 고딕", Font.BOLD, 10));
//		friendBtn.setForeground(darkBlueColor);
//
//		if (user.getData().matches("friend")) { // 친구
//			if (user.getStatus().equals("O")) {
//				friendBtn.setText("접속중");
//			} else if (user.getStatus().equals("S")) {
//				friendBtn.setText("미접속중");
//			}
//			friendBtn.setBackground(backColor);
//			panel.add(friendBtn);
//		} else if (user.getData().matches("recv")) { // 친구x, 요청 받음
//			friendBtn.setText("친구 요청 받음");
//			friendBtn.setBackground(Color.white);
//			friendBtn.setEnabled(true);
//
//			friendBtn.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//
//					// 확인 다이얼로그 창 띄움
//					int result = JOptionPane.showConfirmDialog(null, user.getId() + "님의 친구 요청을 승낙하겠습니까?", "Confirm",
//							JOptionPane.YES_NO_CANCEL_OPTION);
//
//					if (result == JOptionPane.YES_OPTION) { // 친구 요청 승낙
//						ChatMsg yes = new ChatMsg(UserName, "702", user.getId());
//						SendObject(yes);
//						// friendBtn.setText("친구");
//						friendBtn.setBackground(backColor);
//					} else if (result == JOptionPane.NO_OPTION) { // 친구 요청 거절
//						ChatMsg no = new ChatMsg(UserName, "703", user.getId());
//						SendObject(no);
//						friendListScrollPanel.remove(panel);
//
//					}
//					friendListScrollPanel.revalidate();
//					friendListScrollPanel.repaint();
//				}
//			});
//			panel.add(friendBtn);
//		}
//
//		GridBagConstraints gbc = new GridBagConstraints();
//		gbc.gridwidth = GridBagConstraints.REMAINDER;
//		gbc.weightx = 1;
//		gbc.fill = GridBagConstraints.HORIZONTAL;
//		friendListScrollPanel.add(panel, gbc, friendListIdx);
//		friendListIdx++;
//		friendListScrollPanel.revalidate();
//		friendListScrollPanel.repaint();
//
//	}

//	private void printUserProfile(ChatMsg user) {
//
//		JPanel panel = new JPanel(new FlowLayout());
//		panel.setBackground(backColor);
//
//		JButton profileImgBtn = new JButton();
//		profileImgBtn.setIcon(user.getProfileImg_resized());
//		profileImgBtn.setPreferredSize(new Dimension(50, 50));
//		panel.add(profileImgBtn);
//
//		JLabel lbluserName = new JLabel(" " + user.getId());
//		lbluserName.setFont(new Font("맑은 고딕", Font.BOLD, 16));
//		lbluserName.setForeground(darkBlueColor);
//		lbluserName.setPreferredSize(new Dimension(115, 30));
//		panel.add(lbluserName);
//
//		if (user.getId().matches(this.UserName)) {
//			JButton btn = new JButton("");
//			btn.setBackground(backColor);
//			btn.setBorderPainted(false);
//			btn.setPreferredSize(new Dimension(120, 25));
//			panel.add(btn);
//		} else if (!user.getId().matches(this.UserName)) {
//			JButton friendBtn = new JButton();
//			friendBtn.setFont(new Font("맑은 고딕", Font.BOLD, 10));
//			friendBtn.setForeground(darkBlueColor);
//			friendBtn.setBackground(Color.WHITE);
//			friendBtn.setPreferredSize(new Dimension(120, 25));
//
//			if (user.getData().matches("friend")) { // 친구
//				friendBtn.setText("친구");
//				friendBtn.setBackground(backColor);
//			} else if (user.getData().matches("wait")) { // 친구x, 요청o
//				friendBtn.setText("친구 요청 보냄");
//				friendBtn.setBackground(backColor);
//			} else if (user.getData().matches("none")) { // 친구x, 요청x
//				friendBtn.setText("친구 요청 보내기");
//				friendBtn.setEnabled(true);
//				friendBtn.addActionListener(new ActionListener() {
//					public void actionPerformed(ActionEvent e) {
//						ChatMsg f = new ChatMsg(UserName, "701", user.getId());
//						SendObject(f);
//						friendBtn.setText("친구 요청 보냄");
//						friendBtn.setBackground(backColor);
//					}
//				});
//			}
//			panel.add(friendBtn);
//		}
//
//		GridBagConstraints gbc = new GridBagConstraints();
//		gbc.gridwidth = GridBagConstraints.REMAINDER;
//		gbc.weightx = 1;
//		gbc.fill = GridBagConstraints.HORIZONTAL;
//		userListScrollPanel.add(panel, gbc, userListIdx);
//		userListIdx++;
//		userListScrollPanel.revalidate();
//		userListScrollPanel.repaint();
//
//	}

	public void printChatRoomList() { // 채팅방 리스트 출력

		chatListIdx = 0;
		chatListScrollPanel = new JPanel(new GridBagLayout());
		chatListScrollPanel.setBackground(backColor);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.weightx = 1;
		gbc.weighty = 1;
		JPanel p = new JPanel();
		p.setBackground(backColor);
		chatListScrollPanel.add(p, gbc);
		chatListScrollPane.setViewportView(chatListScrollPanel);

		for (int i = 0; i < chatRoomVec.size(); i++) {
			ChatRoom room = chatRoomVec.elementAt(i);
			for (int j = 0; j < chatRoomViewVec.size(); j++) {
				ChatClientChat roomView = chatRoomViewVec.elementAt(j);
				if (room.getRoomId() == roomView.RoomId) {
					if (room.getLastMsg().equals("null") || room.getLastMsg() == null) {
						roomView.lastMsg = "대화 없음";
					} else {
						roomView.lastMsg = room.getLastMsg();
						roomView.lastTime = room.getLastTime();
					}

					JPanel roomPanel = new JPanel(new GridBagLayout());
					roomPanel.setBorder(new LineBorder(backColor, 3));
					GridBagConstraints gbc1 = new GridBagConstraints();

					roomPanel.setPreferredSize(new Dimension(298, 60));
					roomPanel.setBackground(Color.white);
					roomPanel.addMouseListener(new MouseListener() {

						@Override
						public void mouseClicked(MouseEvent e) {
							// TODO Auto-generated method stub
							roomView.setBounds(clientView.getLocation().x, clientView.getLocation().y, 350, 500);
							roomView.setVisible(true);
						}

						@Override
						public void mousePressed(MouseEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void mouseReleased(MouseEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void mouseEntered(MouseEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void mouseExited(MouseEvent e) {
							// TODO Auto-generated method stub

						}

					});

					JLabel lblRoomName = new JLabel(roomView.RoomName, SwingConstants.LEFT);
					lblRoomName.setBackground(backColor);
					lblRoomName.setPreferredSize(new Dimension(200, 20));
					lblRoomName.setFont(new Font("맑은 고딕", Font.BOLD, 15));
					gbc1.gridx = 0;
					gbc1.gridy = 0;
					gbc1.gridwidth = 5;
					gbc1.gridheight = 1;
					roomPanel.add(lblRoomName, gbc1);

					JLabel lastTime = new JLabel(roomView.lastTime, SwingConstants.RIGHT);
					lastTime.setForeground(Color.GRAY);
					lastTime.setBounds(210, 10, 80, 20);
					lastTime.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
					gbc1.gridx = 5;
					gbc1.gridy = 0;
					gbc1.gridwidth = 1;
					gbc1.gridheight = 1;
					roomPanel.add(lastTime, gbc1);

					JLabel lastMsg = new JLabel(roomView.lastMsg, SwingConstants.LEFT);
					lastMsg.setBounds(20, 30, 200, 20);
					lastMsg.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
					lastMsg.setForeground(Color.GRAY);
					gbc1.gridx = 0;
					gbc1.gridy = 1;
					gbc1.gridwidth = 4;
					gbc1.gridheight = 1;
					roomPanel.add(lastMsg, gbc1);

					GridBagConstraints gbc2 = new GridBagConstraints();
					gbc2.gridwidth = GridBagConstraints.REMAINDER;
					gbc2.weightx = 1;
					gbc2.fill = GridBagConstraints.HORIZONTAL;
					chatListScrollPanel.add(roomPanel, gbc2, chatListIdx);
					chatListIdx++;

					break;
				}

			}

		}
		chatListScrollPanel.revalidate();
		chatListScrollPanel.repaint();
	}

	public class CheckBoxFrame extends JFrame {
		private static final long serialVersionUID = 1L;

		private JLabel lbltxt;
		private JButton makeRoom;

		CheckBoxFrame() {
			setBounds(clientView.getLocation().x + 350, clientView.getLocation().y, 240, 350);
			setTitle("대화 상대 초대");

			checkBoxContentPane = new JPanel(null);
			checkBoxContentPane.setBackground(backColor);
			setContentPane(checkBoxContentPane);

			lbltxt = new JLabel("대화 상대 초대");
			lbltxt.setFont(new Font("맑은 고딕", Font.BOLD, 14));
			lbltxt.setForeground(darkBlueColor);
			lbltxt.setBounds(65, 5, 100, 25);
			checkBoxContentPane.add(lbltxt);

			checkBoxListPanel = new JPanel(null);
			checkBoxListPanel.setBackground(Color.white);
			checkBoxScrollPane = new JScrollPane(checkBoxListPanel);
			checkBoxScrollPane.setBounds(10, 35, 205, 240);
			checkBoxScrollPane.setBackground(Color.white);
			checkBoxScrollPane.setVisible(true);
			checkBoxContentPane.add(checkBoxScrollPane);

			makeRoom = new JButton("만들기");
			makeRoom.setFont(new Font("맑은 고딕", Font.BOLD, 13));
			makeRoom.setBackground(Color.white);
			makeRoom.setForeground(blueColor);
			makeRoom.setBounds(76, 280, 75, 25);
			makeRoom.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String userList = UserName + " ";
					for (int i = 0; i < checkBoxVec.size(); i++) {
						JCheckBox cb = checkBoxVec.elementAt(i);
						if (cb.isSelected()) { // 선택된 유저들만
							userList = userList + cb.getText() + " ";
						}
					}
					ChatMsg r = new ChatMsg(UserName, "800", userList); // 채팅방 만들기
					SendObject(r);
					setVisible(false);
				}
			});
			checkBoxContentPane.add(makeRoom);

		}
	}

	// 프로필에 적용할 사진 resize
	private ImageIcon profileImgResize(ImageIcon ori_icon, int size) {
		Image img = ori_icon.getImage();
		int width = ori_icon.getIconWidth();
		int height = ori_icon.getIconHeight();
		if (width == size && height == size) {
			return ori_icon;
		} else {
			return (new ImageIcon(img.getScaledInstance(size, size, Image.SCALE_SMOOTH)));
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
}
