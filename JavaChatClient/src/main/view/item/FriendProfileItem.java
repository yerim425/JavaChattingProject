package main.view.item;

import java.awt.*;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import data.ChatMsg;
import main.view.FriendViewPanel;

public class FriendProfileItem extends BaseProfileItem {

	private static final long serialVersionUID = 1L;
	private JButton btnFriend;  // 친구 버튼
	private FriendViewPanel parent;
	
	public FriendProfileItem(FriendViewPanel parent, ChatMsg user) {
		//super(parent, user);
		super(user);
		this.parent = parent;
		
		// 친구 버튼 추가
		btnFriend  = new JButton(); 
		btnFriend.setPreferredSize(new Dimension(125, 25));
		btnFriend.setFont(resources.Fonts.MAIN_BOLD_10);
		btnFriend.setForeground(resources.Colors.MAIN_DARK_BLUE_COLOR);
		
		
		if (user.getData().matches("friend")) { // 1) 친구
			if (user.getStatus().equals("O")) {  // 온라인 상태라면
				btnFriend.setText("접속중");
			} else if (user.getStatus().equals("S")) {
				btnFriend.setText("미접속중");
			}
			btnFriend.setBackground(resources.Colors.MAIN_BG_COLOR);
			add(btnFriend);
		} else if (user.getData().matches("recv")) { // 2) 친구는 아니지만, 친구 요청 받음
			btnFriend.setText("친구 요청 받음");
			btnFriend.setBackground(resources.Colors.MAIN_WHITE_COLOR);
			btnFriend.setEnabled(true);

			btnFriend.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					// 친구 요청 수락/거절 다이얼로그 창 띄움
					int result = JOptionPane.showConfirmDialog(null, user.getId() + "님의 친구 요청을 승낙하겠습니까?", "Confirm",
							JOptionPane.YES_NO_CANCEL_OPTION);

					if (result == JOptionPane.YES_OPTION) { // 친구 요청 승낙
						ChatMsg yesMsg = new ChatMsg(user.getId(), "702", user.getId());
						SendObject(yesMsg);
						// friendBtn.setText("친구");
						btnFriend.setBackground(resources.Colors.MAIN_BG_COLOR);
					} else if (result == JOptionPane.NO_OPTION) { // 친구 요청 거절
						ChatMsg noMsg = new ChatMsg(user.getId(), "703", user.getId());
						SendObject(noMsg);
						
						//friendListScrollPanel.remove(panel);
						FriendProfileItem.this.parent.removeFriend(FriendProfileItem.this);

					}
					FriendProfileItem.this.revalidate();
					FriendProfileItem.this.repaint();
				}
			});
			add(btnFriend);
		}

		

	}

	@Override
	public void SendObject(ChatMsg cm) {
		// TODO Auto-generated method stub
		parent.SendObject(cm);
		
	}

	


}
