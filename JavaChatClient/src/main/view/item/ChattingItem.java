package main.view.item;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import data.ChatMsg;
import main.view.UserListView;

public class ChattingItem extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private String userName;
	private ImageIcon profileImg;

	private String textMsg;
	private ImageIcon imageMsg;
	private ImageIcon imoticonMsg;

	private JLabel lblUsername;
	private JLabel lblProfileImg;

	private JLabel lblTextMsg;
	private JLabel lblImageMsg;
	private JLabel lblEmoticonMsg;

	private JLabel lblTime;

	JPanel myProfilePanel;
	JPanel msgPanel;

	public ChattingItem(ChatMsg chatMsg, boolean isMine, int msgType) {// 0:text, 1:image

		setLayout(new FlowLayout(FlowLayout.LEFT));
		setPreferredSize(new Dimension(300, 100));
		setMaximumSize(new Dimension(300, 100));
		setBackground(resources.Colors.MAIN_BLUE2_COLOR);
		setOpaque(true);

		// 프로필 패널 - [유저 프로필사진, 유저 이름]
		myProfilePanel = new JPanel();
		myProfilePanel.setOpaque(false);
		myProfilePanel.setPreferredSize(new Dimension(300, 50));
		myProfilePanel.setMaximumSize(new Dimension(300, 50));

		// 유저 이름
		lblUsername = new JLabel(chatMsg.getId());
		lblUsername.setSize(100, 25);
		lblUsername.setBorder(new EmptyBorder(0, 5, 0, 0));
		lblUsername.setFont(resources.Fonts.MAIN_BOLD_12);
		myProfilePanel.add(lblUsername);

		// 보내는 시간
		lblTime = new JLabel(chatMsg.getRoomData().getLastTime());
		lblTime.setSize(80, 25);
		lblTime.setFont(resources.Fonts.MAIN_PLAIN_10);
		lblTime.setBorder(new EmptyBorder(0, 5, 0, 0));
		lblTime.setForeground(Color.LIGHT_GRAY);
		myProfilePanel.add(lblTime);
		add(myProfilePanel);

		// 채팅 메시지 (text, image, imoticon)
		msgPanel = new JPanel();
		// msgPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		msgPanel.setPreferredSize(new Dimension(250, 50));
		msgPanel.setMaximumSize(new Dimension(250, 50));
		msgPanel.setOpaque(false);
		msgPanel.setBorder(new EmptyBorder(0, 40, 0, 0));
		add(msgPanel);

		if (msgType == 0) { // text
			lblTextMsg = new JLabel(chatMsg.getRoomData().getLastMsg());
			lblTextMsg.setText(chatMsg.getData());
			lblTextMsg.setAlignmentY(JLabel.TOP);
			msgPanel.add(lblTextMsg);
		} else if (msgType == 1) { // image
			lblImageMsg = new JLabel();
			lblImageMsg.setIcon(chatImgResized(chatMsg.getRoomData().getChatImg()));
			msgPanel.add(lblImageMsg);
		} else if (msgType == 2) { // emoticon
			lblEmoticonMsg = new JLabel();
			lblEmoticonMsg.setIcon(chatMsg.getRoomData().getEmoticon());
			msgPanel.add(lblEmoticonMsg);
		}

		if (isMine) { // 내가 보낸 메시지
			setAlignmentX(JPanel.RIGHT_ALIGNMENT);
			myProfilePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			myProfilePanel.setAlignmentX(JPanel.RIGHT_ALIGNMENT);
			msgPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		} else { // 친구가 보낸 메시지
			setAlignmentX(JPanel.LEFT_ALIGNMENT);
			myProfilePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			myProfilePanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);

			// 친구의 프로필 사진
			lblProfileImg = new JLabel();
			lblProfileImg.setSize(30, 30);
			lblProfileImg.setIcon(profileImgResized(chatMsg.getProfileImg_ori(), 30));
			myProfilePanel.add(lblProfileImg);

			msgPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

			if (msgType == 0) { // text
				lblTextMsg = new JLabel(chatMsg.getRoomData().getLastMsg());
				lblTextMsg.setText(chatMsg.getData());
				lblTextMsg.setAlignmentY(JLabel.TOP);
				msgPanel.add(lblTextMsg);
			} else if (msgType == 1) { // image

			} else if (msgType == 2) { // emoticon

			}
		}

		revalidate();
		repaint();

		setVisible(true);

	}

	// 프로필에 적용할 사진 resize
	public ImageIcon profileImgResized(ImageIcon ori_icon, int size) {
		Image img = ori_icon.getImage();
		return new ImageIcon(img.getScaledInstance(size, size, Image.SCALE_SMOOTH));

	}

	public ImageIcon chatImgResized(ImageIcon ori_icon) {
		Image ori_img = ori_icon.getImage();
		double ratio;
		int width = ori_icon.getIconWidth();
		int height = ori_icon.getIconHeight();
		if (width > 300 || height > 300) {
			if (width > height) { //
				ratio = (double) height / width;
				width = 300;
				height = (int) (width * ratio);
			} else { //
				ratio = (double) width / height;
				height = 300;
				width = (int) (height * ratio);
			}
			Image new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			ImageIcon new_icon = new ImageIcon(new_img);
			ori_icon = new_icon;
		}
		return ori_icon;
	}

}
