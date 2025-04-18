package main.view.item;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import data.ChatMsg;
import main.view.FriendView;

public class FriendProfileItem extends BaseProfileItem {

	private static final long serialVersionUID = 1L;
	private JButton btnFriend;  // 친구 버튼
	private FriendView parent;
	
	public FriendProfileItem(FriendView parent, ChatMsg user) {
		//super(parent, user);
		super(user);
		this.parent = parent;
		
	    
	    // 친구 버튼 추가
		btnFriend  = new JButton(); 
		btnFriend.setPreferredSize(new Dimension(125, 30));
		btnFriend.setFont(resources.Fonts.MAIN_BOLD_12);
		btnFriend.setBackground(resources.Colors.MAIN_BG_COLOR);
		btnFriend.setForeground(resources.Colors.MAIN_DARK_BLUE_COLOR);
		btnFriend.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btnFriend.setUI(new BasicButtonUI()); // LookAndFeel 영향 최소화

		
		if (dataMsg.equals("friend")) { // 1) 친구
			if (user.getStatus().equals("O")) {  // 온라인 상태라면
				btnFriend.setText("접속중");
			} else if (user.getStatus().equals("S")) {
				btnFriend.setText("미접속중");
			}
			contentPane.add(btnFriend);
		} else if (dataMsg.equals("recv")) { // 2) 친구는 아니지만, 친구 요청 받음
			btnFriend.setText("친구 요청 수락");
			btnFriend.setForeground(resources.Colors.MAIN_WHITE_COLOR);
			btnFriend.setBackground(resources.Colors.MAIN_BLUE2_COLOR);

			btnFriend.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					// 친구 요청 수락/거절 다이얼로그 창 띄움
					int result = JOptionPane.showConfirmDialog(null, userName + "님의 친구 요청을 승낙하겠습니까?", "Confirm",
							JOptionPane.YES_NO_CANCEL_OPTION);

					if (result == JOptionPane.YES_OPTION) { // 친구 요청 수락
						ChatMsg yesMsg = new ChatMsg(user.getId(), "720", userName);
						SendObject(yesMsg);
						btnFriend.setText("친구");
						btnFriend.setBackground(resources.Colors.MAIN_BG_COLOR);
						btnFriend.setForeground(resources.Colors.MAIN_DARK_BLUE_COLOR);
					} else if (result == JOptionPane.NO_OPTION) { // 친구 요청 거절
						ChatMsg noMsg = new ChatMsg(user.getId(), "730", userName);
						SendObject(noMsg);
						
						//friendListScrollPanel.remove(panel);
						FriendProfileItem.this.parent.removeFriend(FriendProfileItem.this);

					}
					FriendProfileItem.this.revalidate();
					FriendProfileItem.this.repaint();
				}
			});
			contentPane.add(btnFriend);
		}

		this.setVisible(true);

	}

	@Override
	public void SendObject(ChatMsg cm) {
		// TODO Auto-generated method stub
		parent.SendObject(cm);
		
	}

	


}
