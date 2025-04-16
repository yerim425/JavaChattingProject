package main.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import data.ChatMsg;
import data.ChatRoom;
import main.ChatClientChat;
import main.ChatClientMain;
import main.ChatClientMain.CheckBoxFrame;

// Main 뷰에서 "채팅" 바텀 버튼을 누른 경우 띄워지는 view

public class ChatRoomViewPanel extends BaseViewPanel{

	private static final long serialVersionUID = 1L;
	
	private JLabel lblroomList; // "채팅방 목록"
	private JButton btnMakeRoom; // 채팅방 추가 버튼
	
	private JScrollPane chatRoomListScrollPane; // 채팅방 리스트 스크롤
	private JPanel chatRoomListPanel; // 채팅방 리스트 패널
	
	CreateRoomFrame createRoomView; // 채팅방 만들기 뷰
	private int chatRoomListIdx = 0;
	
	private String[] friendNameList;
	
	// 채팅방 추가 체크박스
	//private int checkBoxPosY = 5;
	//private Vector<JCheckBox> checkBoxVec = new Vector<JCheckBox>();
	private ArrayList<ChatClientChat> chatRoomViewVec = new ArrayList<ChatClientChat>(); // 채팅방 뷰 벡터
	private ArrayList<ChatRoom> chatRoomVec = new ArrayList<ChatRoom>(); // 각 채팅방에서의 메시지들을 보관하기 위한 벡터

	public ChatRoomViewPanel(ChatClientMain parent, String name) {
		super(parent, name);
		
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setBounds(0, 0, 335, 510);
		setLayout(null);
		setVisible(false);
		setBackground(resources.Colors.MAIN_BG_COLOR);
		

		// "채팅방 목록"
		lblroomList = new JLabel(resources.Strings.CHAT_ROOM_LIST);
		lblroomList.setBorder(new EmptyBorder(5, 5, 5, 5));
		lblroomList.setFont(resources.Fonts.MAIN_BOLD_16);
		lblroomList.setForeground(resources.Colors.MAIN_WHITE_COLOR);
		lblroomList.setBackground(resources.Colors.MAIN_BLUE2_COLOR);
		lblroomList.setOpaque(true);
		lblroomList.setBounds(10, 10, 330, 25);
		add(lblroomList);
		
		// "채팅방 추가 버튼"
		btnMakeRoom = new JButton();
		btnMakeRoom.setBounds(310, 10, 25, 25); // 38
		btnMakeRoom.setPreferredSize(new Dimension(25, 25));
		btnMakeRoom.setBorder(new EmptyBorder(0,0,0,0));
		btnMakeRoom.setBackground(resources.Colors.MAIN_BLUE2_COLOR);
		btnMakeRoom.setIcon(imageResized(new ImageIcon("src/btnIcons/plus.png"), 25));
		add(btnMakeRoom);

		
		
		btnMakeRoom.addActionListener(new ActionListener() { // 버튼 리스너
			public void actionPerformed(ActionEvent e) {
//				if(friendNameList.size() > 0) { // 리스트를 서버로부터 받아왔다면 버튼 클릭 시 생성됨
//					
//				}
//				makeRoomView = new MakeChatRoomFrame(ChatViewPanel.this, userName, friendNameList);
//				makeRoomView.setVisible(false);
//
//				checkBoxVec.removeAllElements();
//				checkBoxPosY = 5;
				
				// 나의 친구 리스트 요청
				ChatMsg fl = new ChatMsg(userName, "820", "friend list"); 
				SendObject(fl);
				
			}
		});


		// 스크롤 화면 설정
		chatRoomListPanel = new JPanel(new GridBagLayout());
		chatRoomListPanel.setBackground(resources.Colors.MAIN_BG_COLOR);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.weightx = 1;
		gbc.weighty = 0;
		JPanel p = new JPanel();
		p.setBackground(resources.Colors.MAIN_BG_COLOR);
		chatRoomListPanel.add(p, gbc);
		
	
		chatRoomListScrollPane = new JScrollPane(chatRoomListPanel);
		chatRoomListScrollPane.setBounds(10, 50, 325, 460);
		chatRoomListScrollPane.setBackground(resources.Colors.MAIN_BG_COLOR);
		add(chatRoomListScrollPane);
		
		
		
	}

	public void initChatRoomList() { // 목록 재세팅을 위해 전부 삭제

		chatRoomListPanel.removeAll();
		
		// 이거 두개 필요한가?
		chatRoomListPanel.revalidate();
		chatRoomListPanel.repaint();
		
		chatRoomListIdx = 0;
	}
	
	public void createMakeRoomView(String[] list) {
		this.friendNameList = list;
		
		createRoomView = new CreateRoomFrame(ChatRoomViewPanel.this, userName, friendNameList);
		createRoomView.setVisible(true);
	}
	
	@Override
	public void SendObject(ChatMsg cm) {
		parent.SendObject(cm);
		
	}
	

}
