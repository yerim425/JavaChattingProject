package main.view.item;

import java.awt.BorderLayout;
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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
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
import main.ChatClientRoom;
import main.ChatClientMain;
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
	
	private boolean isOpen = false;
	ChatClientRoom view;
	
	//private ChatClientChat chatClientView;
	
	public ChatRoomItem(ChatRoomListView parent, ChatRoom roomData) {
		this.parent = parent;
		this.roomData = roomData;
		
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(325, 60));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
		setOpaque(false);
		setAlignmentX(LEFT_ALIGNMENT); 
		
		
		contentPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    contentPane.setBorder(new EmptyBorder(5,5,5,5));
	    contentPane.setBackground(resources.Colors.MAIN_BLUE2_COLOR);
	    contentPane.setOpaque(true);
		//contentPane.setBorder(new LineBorder(resources.Colors.MAIN_BLUE2_COLOR, 5));
		
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(!isOpen) {
					view = new ChatClientRoom(parent, roomData);
					view.setBounds(350, 300, 350, 550);
					view.setVisible(true);
					parent.addOpenChatView(view);
					isOpen = true;
					
					view.addWindowListener(new WindowListener() {

			            @Override
			            public void windowOpened(WindowEvent e) {}
			            @Override
			            public void windowIconified(WindowEvent e) {}
			            @Override
			            public void windowDeiconified(WindowEvent e) {}
			            @Override
			            public void windowDeactivated(WindowEvent e) {}
			            @Override
			            public void windowClosing(WindowEvent e) {
			            	ChatRoomItem.this.isOpen = false;
			            }
			            @Override
			            public void windowClosed(WindowEvent e) {}
			            @Override
			            public void windowActivated(WindowEvent e) {}
			        });
				}
				
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
		
		// 채팅방 이름
		lblRoomName = new JLabel(roomData.getRoomName());
		lblRoomName.setPreferredSize(new Dimension(200, 20));
		lblRoomName.setFont(resources.Fonts.MAIN_BOLD_14);
		contentPane.add(lblRoomName);
		
		// 마지막으로 보낸 채팅 시간
		lastTime = new JLabel(roomData.getLastTime(), SwingConstants.RIGHT);
		lastTime.setForeground(Color.GRAY);
		lastTime.setPreferredSize(new Dimension(80, 20));
		lastTime.setForeground(resources.Colors.MAIN_WHITE_COLOR);
		lastTime.setFont(resources.Fonts.MAIN_PLAIN_12);
		contentPane.add(lastTime);
		
		// 마지막으로 보낸 채팅 메시지
		lastMsg = new JLabel(roomData.getLastMsg(), SwingConstants.LEFT);
		//lastMsg.setBounds(20, 30, 200, 20);
		lastMsg.setPreferredSize(new Dimension(200, 20));
		lastMsg.setFont(resources.Fonts.MAIN_PLAIN_12);
		lastMsg.setForeground(resources.Colors.MAIN_WHITE_COLOR);
		contentPane.add(lastMsg);
		
		add(contentPane, BorderLayout.CENTER);
		
		setVisible(true);
		
		
	}

	public void setRoomData(ChatRoom roomData) {
		lastMsg.setText(roomData.getLastMsg());
		lastTime.setText(roomData.getLastTime());
		
		this.revalidate();
		this.repaint();
	}

	
	public void SendObject(ChatMsg cm) {
		parent.SendObject(cm);
		
	}

}
