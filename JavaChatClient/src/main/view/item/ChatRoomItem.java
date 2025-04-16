package main.view.item;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import data.ChatMsg;
import data.ChatRoom;
import main.view.ChatRoomViewPanel;
import main.view.UserViewPanel;

public class ChatRoomItem extends JPanel {

	private static final long serialVersionUID = 1L;
	//private JButton btnUser;  // 친구 버튼
	private ChatRoomViewPanel parent;
	
	private ChatMsg roomMsg;
	
	private Vector<ChatRoom> chatRoomVec;
	
	public ChatRoomItem(ChatRoomViewPanel parent, ChatMsg roomMsg) {
		//super(parent, user);
		//super(user);
		this.parent = parent;
		this.roomMsg = roomMsg;
		setBorder(new EmptyBorder(5, 5, 5, 5));
		
		
		

	}

	public void addChatRoom(ChatMsg roomData) {
		
	}

	
	public void SendObject(ChatMsg cm) {
		parent.SendObject(cm);
		
	}

}
