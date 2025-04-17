package main.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import data.ChatMsg;

public class CreateRoomFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private JLabel lbltxt;
	private JButton makeRoom;

	private JScrollPane checkBoxScrollPane = new JScrollPane();
	private JPanel contentPane = new JPanel(null);
	private JPanel checkBoxListPanel = new JPanel();

	private ArrayList<JCheckBox> checkBoxList = new ArrayList<JCheckBox>();
	// private int checkBoxPosY = 5;
	private int checkBoxIdx = 0;

	// private String[] friendNameList;

	// 현재 유저의 친구 이름 리스트를 함께 가져와 체크 박스 리스트를 만들어 화면에 display
	// 만들기 버튼을 누르면 채팅방 생성 요청을 보냄
	public CreateRoomFrame(ChatRoomListView parent, String userName, String[] friendList) {
		int weight = 240;
		int height = 350;
		setBounds(parent.getLocation().x + parent.getWidth(), parent.getLocation().y, weight, height);
		setTitle("대화 상대 초대");

		EmptyBorder border = new EmptyBorder(5, 5, 5, 5);

		contentPane.setSize(weight, height);
		contentPane.setBorder(border);
		contentPane.setBackground(resources.Colors.MAIN_BLUE2_COLOR);
		setContentPane(contentPane);

		JLabel lbl = new JLabel();
		lbl.setBounds(0, 0, weight, 30);
		// line.setBackground(resources.Colors.MAIN_DARK_BLUE_COLOR);
		// lbl.setOpaque(false);

		// "나" 체크박스 객체 추가
		JCheckBox cb = new JCheckBox(userName + "(나)", true);
		cb.setBounds(0, 0, weight, 30);
		cb.setBorder(border);
		cb.setFont(resources.Fonts.MAIN_PLAIN_15);
		cb.setBackground(new Color(0, 0, 0, 0));
		cb.setOpaque(false);
		cb.setSelected(true);

		cb.addItemListener(e -> {
			if (!cb.isSelected()) {
				cb.setSelected(true); // 다시 강제로 체크 상태로 되돌림
			}
		});
		contentPane.add(cb);

		lbltxt = new JLabel(resources.Strings.FRIEND);
		lbltxt.setFont(resources.Fonts.MAIN_BOLD_12);
		lbltxt.setForeground(resources.Colors.MAIN_DARK_BLUE_COLOR);
		lbltxt.setBackground(new Color(0, 0, 0, 0));
		lbltxt.setOpaque(false);
		lbltxt.setBounds(0, 30, weight, 20);
		lbltxt.setBorder(border);
		lbltxt.setHorizontalAlignment(SwingConstants.LEFT);
		add(lbltxt);

		// 체크박스 리스트
		checkBoxListPanel = new JPanel(new GridBagLayout());
		checkBoxListPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		checkBoxListPanel.setBackground(new Color(0, 0, 0, 0));
		checkBoxListPanel.setOpaque(false);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		JPanel p = new JPanel();
		// p.setBackground(resources.Colors.MAIN_BLUE2_COLOR);
		checkBoxListPanel.add(p, gbc);

		checkBoxScrollPane = new JScrollPane(checkBoxListPanel);
		checkBoxScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		checkBoxScrollPane.setBounds(0, 50, weight, 240);
		checkBoxScrollPane.setBorder(border);
		checkBoxScrollPane.setVisible(true);
		checkBoxScrollPane.setBackground(resources.Colors.MAIN_BLUE2_COLOR);
		checkBoxScrollPane.setOpaque(false);

		contentPane.add(checkBoxScrollPane);

		// 체크박스 객체 추가
		for (String name : friendList) {
			JCheckBox cb2 = new JCheckBox(name, false);
			cb2.setFont(resources.Fonts.MAIN_PLAIN_15);
			cb2.setSize(weight, 25);
			cb2.setBorder(border);
			checkBoxList.add(cb2); // 리스트에 추가
			checkBoxListPanel.add(cb2, gbc, checkBoxIdx); // 화면에 추가
			checkBoxIdx++;
		}
		checkBoxListPanel.revalidate();
		checkBoxListPanel.repaint();

		// 만들기 버튼
		makeRoom = new JButton("채팅방 만들기");
		makeRoom.setFont(resources.Fonts.MAIN_BOLD_15);
		makeRoom.setForeground(resources.Colors.MAIN_WHITE_COLOR);
		makeRoom.setBounds(0, 290, weight, 30);
		makeRoom.setBorder(new EmptyBorder(5, 5, 5, 5));
		makeRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userList = userName + " ";
				for (JCheckBox cb : checkBoxList) {
					if (cb.isSelected()) { // 선택된 유저들만
						userList = userList + cb.getText().trim() + " ";
						// userList += cb.getText() + " ";
					}
				}
				//System.out.println(userList);
				ChatMsg r = new ChatMsg(userName, "810", userList.trim()); // 채팅방 만들기
				parent.SendObject(r); // 800 -> 810
				setVisible(false);
			}
		});
		contentPane.add(makeRoom);

	}

//	public void setFriendList(String[] list) {
//
//		// 체크박스 객체 추가
//		for (String name : list) {
//			JCheckBox cb = new JCheckBox(name, false);
//			cb.setFont(resources.Fonts.MAIN_PLAIN_15);
//			// cb.setBounds(5, checkBoxPosY, 80, 25);
//			cb.setSize(80, 25);
//			checkBoxList.add(cb); // 리스트에 추가
//
//			GridBagConstraints gbc = new GridBagConstraints();
//			gbc = new GridBagConstraints();
//			gbc.gridwidth = GridBagConstraints.REMAINDER;
//			gbc.weightx = 1;
//			gbc.weightx = 0;
//			gbc.fill = GridBagConstraints.HORIZONTAL;
//			checkBoxListPanel.add(cb, gbc, checkBoxIdx); // 화면에 추가
//			// checkBoxPosY += 30;
//			checkBoxIdx++;
//			checkBoxListPanel.revalidate();
//			checkBoxListPanel.repaint();
//		}
//	}
}
