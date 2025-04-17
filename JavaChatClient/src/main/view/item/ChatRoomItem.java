package main.view.item;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import data.ChatMsg;
import data.ChatRoom;
import main.ChatClientChat;
import main.view.ChatRoomListView;
import main.view.UserListView;

public class ChatRoomItem extends JPanel {

	private static final long serialVersionUID = 1L;
	//private JButton btnUser;  // 친구 버튼
	private ChatRoomListView parent;
	
	private ChatRoom roomData;
	
	private JPanel contentPane;
	private JLabel lblRoomName;
	private JLabel lastTime;
	private JLabel lastMsg;
	
	//private Vector<ChatRoom> chatRoomVec;
	
	private ChatClientChat chatClientView;
	
	public ChatRoomItem(ChatRoomListView parent, ChatRoom roomData) {
		//super(parent, user);
		//super(user);
		this.parent = parent;
		this.roomData = roomData;
		//setLayout(new GridBagLayout());
		setLayout(null);
		setBackground(resources.Colors.MAIN_WHITE_COLOR);
		setOpaque(false);
		setSize(this.getWidth(), 60);
		setBorder(new LineBorder(resources.Colors.MAIN_BLUE2_COLOR, 3));
		
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				chatClientView = new ChatClientChat(parent, roomData);
				chatClientView.setVisible(true);
			}

			@Override
			public void mousePressed(MouseEvent e){}

			@Override
			public void mouseReleased(MouseEvent e){}

			@Override
			public void mouseEntered(MouseEvent e){}

			@Override
			public void mouseExited(MouseEvent e){}
			
		});
		
		//GridBagConstraints gbc1 = new GridBagConstraints();
		
		// 채팅방 이름
		lblRoomName = new JLabel(roomData.getRoomName());
		lblRoomName.setBounds(0,0,200,20);
		lblRoomName.setPreferredSize(new Dimension(200, 20));
		lblRoomName.setFont(resources.Fonts.MAIN_BOLD_14);
//		gbc1.gridx = 0;
//		gbc1.gridy = 0;
//		gbc1.gridwidth = 5;
//		gbc1.gridheight = 1;
//		add(lblRoomName, gbc1);
		add(lblRoomName);
		
		// 마지막으로 보낸 채팅 시간
		lastTime = new JLabel(roomData.getLastTime(), SwingConstants.RIGHT);
		lastTime.setForeground(Color.GRAY);
		lastTime.setBounds(210, 10, 80, 20);
		lastTime.setFont(resources.Fonts.MAIN_BOLD_12);
//		gbc1.gridx = 5;
//		gbc1.gridy = 0;
//		gbc1.gridwidth = 1;
//		gbc1.gridheight = 1;
//		roomPanel.add(lastTime, gbc1);
		add(lastTime);
		
		// 마지막으로 보낸 채팅 메시지
		JLabel lastMsg = new JLabel(roomData.getLastMsg(), SwingConstants.LEFT);
		lastMsg.setBounds(20, 30, 200, 20);
		lastMsg.setFont(resources.Fonts.MAIN_BOLD_12);
		lastMsg.setForeground(Color.GRAY);
//		gbc1.gridx = 0;
//		gbc1.gridy = 1;
//		gbc1.gridwidth = 4;
//		gbc1.gridheight = 1;
//		roomPanel.add(lastMsg, gbc1);
		
		

	}

	public void setroomData(ChatRoom roomData) {
		lastMsg.setText(roomData.getLastMsg());
		lastTime.setText(roomData.getLastTime());
		
		this.revalidate();
		this.repaint();
	}

	
	public void SendObject(ChatMsg cm) {
		parent.SendObject(cm);
		
	}

}
