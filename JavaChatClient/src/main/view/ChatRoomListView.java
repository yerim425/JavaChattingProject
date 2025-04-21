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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import data.ChatMsg;
import data.ChatRoom;
import main.ChatClientRoom;
import main.ChatClientMain;
import main.view.item.ChatRoomItem;

// Main 뷰에서 "채팅" 바텀 버튼을 누른 경우 띄워지는 view

public class ChatRoomListView extends BaseView{

	private static final long serialVersionUID = 1L;
	
	private JLabel lblroomList; // "채팅방 목록"
	private JButton btnMakeRoom; // 채팅방 추가 버튼
	
	private JScrollPane chatRoomListScrollPane; // 채팅방 리스트 스크롤
	private JPanel chatRoomListPanel; // 채팅방 리스트 패널
	
	CreateRoomFrame createRoomView; // 채팅방 만들기 뷰
	private int chatRoomListIdx = 0;
	
	private String[] friendNameList = null;
	
	// 채팅방 추가 체크박스
	//private int checkBoxPosY = 5;
	//private Vector<JCheckBox> checkBoxVec = new Vector<JCheckBox>();
	//private ArrayList<ChatClientChat> chatRoomViewVec = new ArrayList<ChatClientChat>(); // 채팅방 뷰 벡터
	//private ArrayList<ChatRoom> chatRoomVec = new ArrayList<ChatRoom>(); // 각 채팅방에서의 메시지들을 보관하기 위한 벡터

	public ChatRoomListView(ChatClientMain parent, String name) {
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
		lblroomList.setBounds(10, 10, 300, 25);
		add(lblroomList);
		
		// "채팅방 추가 버튼"
		btnMakeRoom = new JButton("+");
		btnMakeRoom.setBounds(310, 10, 25, 25); // 38
		btnMakeRoom.setBorder(new EmptyBorder(0,0,0,0));
		btnMakeRoom.setPreferredSize(new Dimension(25, 25));
		btnMakeRoom.setFont(resources.Fonts.MAIN_BOLD_24);
		btnMakeRoom.setForeground(resources.Colors.MAIN_WHITE_COLOR);
		btnMakeRoom.setBackground(resources.Colors.MAIN_BLUE2_COLOR);
		btnMakeRoom.setOpaque(true);
		add(btnMakeRoom);

		
		
		btnMakeRoom.addActionListener(new ActionListener() { // 버튼 리스너
			public void actionPerformed(ActionEvent e) {
				
				// 나의 친구 리스트 요청
				ChatMsg fl = new ChatMsg(userName, "830", "friend name list"); 
				SendObject(fl);
				try {
					Thread.currentThread();
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(friendNameList != null && friendNameList.length > 0) { 
					createRoomView = new CreateRoomFrame(ChatRoomListView.this, userName, friendNameList);
					createRoomView.setVisible(true);
				}else {
					System.out.println("no friend");
				}
			}
		});


		// 스크롤 화면 설정
		chatRoomListPanel = new JPanel();
		chatRoomListPanel.setLayout(new BoxLayout(chatRoomListPanel, BoxLayout.Y_AXIS));
		chatRoomListPanel.setAlignmentY(TOP_ALIGNMENT);
		chatRoomListPanel.setPreferredSize(new Dimension(325, calculatePanelHeight()));
		chatRoomListPanel.setBackground(resources.Colors.MAIN_BG_COLOR);
		chatRoomListPanel.setOpaque(true);
	
		chatRoomListScrollPane = new JScrollPane(chatRoomListPanel);
		chatRoomListScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		chatRoomListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		chatRoomListScrollPane.getViewport().setOpaque(false);
		chatRoomListScrollPane.setOpaque(false);
		chatRoomListScrollPane.setBorder(null);
		chatRoomListScrollPane.setBounds(10, 50, 325, 460);
		chatRoomListScrollPane.setBackground(resources.Colors.MAIN_BG_COLOR);
		add(chatRoomListScrollPane);
		
		
	}

	public void initChatRoomList() { // 목록 재세팅을 위해 전부 삭제

		chatRoomListPanel.removeAll();
		
		chatRoomListPanel.revalidate();
		chatRoomListPanel.repaint();
		
		//chatRoomListIdx = 0;
	}
	
	public void setFriendList(String[] list) { // for 채팅방 추가 화면
		this.friendNameList = list;
	}
	
	public void addRoom(ChatRoom roomData) {
		ChatRoomItem room = new ChatRoomItem(this, roomData);
		chatRoomListPanel.add(room);
		chatRoomListPanel.add(Box.createVerticalStrut(10));
		
		chatRoomListPanel.setPreferredSize(new Dimension(325, calculatePanelHeight()));
		
		chatRoomListPanel.revalidate();
		chatRoomListPanel.repaint();
		
		
	}
	
	public void setRoomVec(Vector<ChatRoom> rooms) {
		initChatRoomList();
		for(ChatRoom room : rooms) {
			addRoom(room);
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {}
		
		chatRoomListPanel.validate();
		chatRoomListPanel.repaint();
		
		
	}
	
	@Override
	public void SendObject(ChatMsg cm) {
		parent.SendObject(cm);
		
	}
	
	public int calculatePanelHeight() {
		int itemCount = 0;
		
	    for (int i = 0; i < chatRoomListPanel.getComponentCount(); i++) {
	        if (chatRoomListPanel.getComponent(i) instanceof ChatRoomItem) {
	            itemCount++;
	        }
	    }
	    int itemHeight = 60; 
	    int spacing = 10;
	    return itemCount * (itemHeight + spacing);
	}
	
	public void addOpenChatView(ChatClientRoom view) {
		parent.addOpenChatView(view);
	}
	

}
