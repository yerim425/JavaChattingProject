package main.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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

import data.ChatMsg;

public class CreateRoomFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JLabel lbltxt;
	private JButton makeRoom;
	
	private JScrollPane checkBoxScrollPane = new JScrollPane();
	private JPanel checkBoxContentPane = new JPanel();
	private JPanel checkBoxListPanel = new JPanel();
	
	private ArrayList<JCheckBox> checkBoxList = new ArrayList<JCheckBox>();
	//private int checkBoxPosY = 5;
	private int checkBoxIdx = 0;

	// 현재 유저의 친구 이름 리스트를 함께 가져와 체크 박스 리스트를 만들어 화면에 display
	// 만들기 버튼을 누르면 채팅방 생성 요청을 보냄
	public CreateRoomFrame(ChatRoomViewPanel parent, String userName, String[] friendNameList) {
		setBounds(getLocation().x + 350, getLocation().y, 240, 350);
		setTitle("대화 상대 초대");

		checkBoxContentPane = new JPanel(new FlowLayout());
		checkBoxContentPane.setBackground(resources.Colors.MAIN_BG_COLOR);
		setContentPane(checkBoxContentPane);

		lbltxt = new JLabel("대화 상대 초대");
		lbltxt.setFont(resources.Fonts.MAIN_BOLD_12);
		lbltxt.setForeground(resources.Colors.MAIN_DARK_BLUE_COLOR);
		//lbltxt.setBounds(65, 5, 100, 25);
		lbltxt.setSize(100, 25);
		checkBoxContentPane.add(lbltxt);

		checkBoxListPanel = new JPanel(new GridBagLayout());
		checkBoxListPanel.setBackground(resources.Colors.MAIN_WHITE_COLOR);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.weightx = 1;
		gbc.weighty = 0; 
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JPanel p = new JPanel();
		p.setBackground(resources.Colors.MAIN_BG_COLOR);
		checkBoxListPanel.add(p, gbc);
		
		
		checkBoxScrollPane = new JScrollPane(checkBoxListPanel);
		checkBoxScrollPane.setSize(205, 240);
		//checkBoxScrollPane.setBounds(10, 35, 205, 240);
		checkBoxScrollPane.setBackground(resources.Colors.MAIN_WHITE_COLOR);
		checkBoxScrollPane.setVisible(true);
		checkBoxContentPane.add(checkBoxScrollPane);
		
		
		// 체크박스 객체 추가
		for (String name : friendNameList) {
			JCheckBox cb = new JCheckBox(name, false);
			cb.setFont(resources.Fonts.MAIN_PLAIN_15);
			//cb.setBounds(5, checkBoxPosY, 80, 25);
			cb.setSize(80, 25);
			checkBoxList.add(cb); // 리스트에 추가
			
			gbc = new GridBagConstraints();
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.weightx = 1;
			gbc.weightx = 0;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			checkBoxListPanel.add(cb, gbc, checkBoxIdx); // 화면에 추가
			//checkBoxPosY += 30;
			checkBoxIdx++;
			checkBoxListPanel.revalidate();
			checkBoxListPanel.repaint();
		}

		makeRoom = new JButton("만들기");
		makeRoom.setFont(resources.Fonts.MAIN_BOLD_12);
		makeRoom.setBackground(resources.Colors.MAIN_WHITE_COLOR);
		makeRoom.setForeground(resources.Colors.MAIN_BLUE_COLOR);
		makeRoom.setSize(75, 25);
		//makeRoom.setBounds(76, 280, 75, 25);
		makeRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userList = userName + " ";
				for (JCheckBox cb : checkBoxList) {
					if (cb.isSelected()) { // 선택된 유저들만
						userList = userList + cb.getText() + " ";
						//userList += cb.getText() + " ";
					}
				}
				ChatMsg r = new ChatMsg(userName, "800", userList); // 채팅방 만들기
				parent.SendObject(r);
				setVisible(false);
			}
		});
		checkBoxContentPane.add(makeRoom);

	}
}
