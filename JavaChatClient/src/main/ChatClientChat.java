/*
 * java chatting view frame
 */
package main;

import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import data.ChatMsg;
import data.ChatRoom;
import main.view.ChatRoomListView;
import main.view.item.ChatItem;
import main.view.item.ChatRoomItem;



public class ChatClientChat extends JFrame{
	/**
	 *  ä�ù� frame
	 */
	private static final long serialVersionUID = 1L;
	private ChatClientMain mainView; 
	private JFrame myFrame = this;
	
	
	
	public int RoomId;
	private static final int BUF_LEN = 128; // Windows ó�� BUF_LEN �� ����
	
	private JLabel lblRoomName;
	
	
	private Frame frame;
	private FileDialog fd;
	
	
	
	
	private Color backColor = new Color(115, 175, 255);
	
	public String userList;
	public String[] userNameArr;
	public String RoomName;
	
	public String lastMsg;
	public String lastTime;
	private String oldTime; 
	private String oldUser; 
	private String newUser;
	private JButton[] emoticonbtns = new JButton[12];
	private ImageIcon[] emoticons = new ImageIcon[12];
	private Vector<Emoticon> emoticonVec = new Vector<Emoticon>();
	
	
	
//------------------------------------
	private ChatRoomListView parent;
	private ChatRoom roomData;
	private String UserName;
	
	private JPanel contentPane;
	private JButton btnBack; // 뒤로가기 버튼
	private JButton btnPlus; // 이미지 보내기 버튼
	private JButton btnSmile; // 이모티콘 보내기 버튼
	private JButton btnSend; // 텍스트 보내기 버튼
	
	private JScrollPane scrollPane; // 채팅 스크롤 공간
	private JPanel chatPanel; // 채팅 화면
	
	private EmoticonFrame emoticonView; // 이모티콘 선택 창
	
	//public JTextPane textArea; // 메시지 텍스트 
	private JTextField txtInput; // 메시지 입력 칸
	
	
	
	public ChatClientChat(ChatRoomListView parent, ChatRoom roomData) {
		this.parent = parent;
		this.roomData = roomData;
		this.UserName = roomData.getUserNameList()[0];
		setResizable(false);
		setLayout(new BorderLayout());
		setTitle(resources.Strings.BUGI_TALK);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		contentPane = new JPanel(null);
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setBounds(0,0,350, 550);
		contentPane.setBackground(resources.Colors.MAIN_BG2_COLOR);
		contentPane.setOpaque(true);
		setContentPane(contentPane);
		
		// 뒤로 가기 버튼
		btnBack = new JButton();
		btnBack.setBounds(10, 10, 25, 25);
		btnBack.setIcon(imageResized(new ImageIcon("src/btnIcons/back.png"), 25));
		btnBack.setBorder(new EmptyBorder(0,0,0,0));
		btnBack.setOpaque(false);
		
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		contentPane.add(btnBack);

		// 채팅방 이름
		lblRoomName = new JLabel();
		lblRoomName.setText(roomData.getRoomName());
		lblRoomName.setBounds(10, 10, 330, 25);
		lblRoomName.setHorizontalAlignment(JLabel.CENTER);
		lblRoomName.setFont(resources.Fonts.MAIN_BOLD_15);
		lblRoomName.setForeground(resources.Colors.MAIN_DARK_BLUE_COLOR);
		lblRoomName.setPreferredSize(new Dimension(290, 25));
		lblRoomName.setOpaque(false);
		contentPane.add(lblRoomName);
		
		
		// 채팅 화면
		chatPanel = new JPanel();
		chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
		chatPanel.setPreferredSize(new Dimension(330, calculatePanelHeight()));
		chatPanel.setAlignmentY(TOP_ALIGNMENT);
		chatPanel.setOpaque(false);
		
		// 채팅 스크롤 공간
		scrollPane = new JScrollPane(chatPanel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setOpaque(true);
		scrollPane.setBorder(null);
		scrollPane.setBounds(10, 45, 330, 430);
		scrollPane.setBackground(resources.Colors.MAIN_BLUE_COLOR);
		contentPane.add(scrollPane);
		
		// 이모티콘 선택 버튼
		btnSmile = new JButton();
		btnSmile.setBounds(10, 485, 25, 25);
		btnSmile.setIcon(imageResized(new ImageIcon("src/btnIcons/smile.png"), 25));
		btnBack.setBorder(new EmptyBorder(0,0,0,0));
		btnBack.setOpaque(false);
		btnSmile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				emoticonView = new EmoticonFrame();
				emoticonView.setVisible(true);
			}
			
		});
		contentPane.add(btnSmile);
		
		// 이미지 선택 버튼
		btnPlus = new JButton();
		btnPlus.setBounds(40, 485, 25, 25);
		btnPlus.setIcon(imageResized(new ImageIcon("src/btnIcons/plus.png"), 25));
		btnPlus.setBorder(new EmptyBorder(0,0,0,0));
		btnPlus.setOpaque(false);
		btnPlus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//frame = new Frame("이미지 선택"); 
				// 이미지 선택 다이얼로그 띄우기
				fd = new FileDialog(frame, "이미지 선택", FileDialog.LOAD); 
				fd.setVisible(true);
				
				
				
				
				ImageIcon selectIcon = new ImageIcon(fd.getDirectory() + fd.getFile());
				
				
				ChatMsg cm = new ChatMsg(UserName, "300", "image");
				ChatRoom cr = new ChatRoom(roomData.getRoomId(), roomData.getUserNames());
				cr.setChatImg(selectIcon);
				cm.setRoomData(cr);
				//cm.setTime(calcTime()); // 이건 서버에서 계산해서 저장
				
				// 이미지 사이즈 조정 -- 나중에 실행해보고 안되면 수정
//				Image selectImg = selectIcon.getImage();
//				int width, height;
//				double ratio;
//				width = selectIcon.getIconWidth();
//				height = selectIcon.getIconHeight();
//				if (width > 300 || height > 300) {
//					if (width > height) { // ���� ����
//						ratio = (double) height / width;
//						width = 300;
//						height = (int) (width * ratio);
//					} else { // ���� ����
//						ratio = (double) width / height;
//						height = 300;
//						width = (int) (height * ratio);
//					}
//					selectImg = selectImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
//					cm.setChatImg(new ImageIcon(selectImg));
//				} else
//					cm.setChatImg(selectIcon);
				
				parent.SendObject(cm);
			}
		});
		contentPane.add(btnPlus);

		// 메시지 텍스트 입력 칸
		txtInput = new JTextField(15);
		txtInput.setBounds(70, 485, 240, 25);
		txtInput.setFont(resources.Fonts.MAIN_BOLD_15);
		txtInput.setOpaque(true);
		txtInput.setBackground(resources.Colors.MAIN_WHITE_COLOR);
		contentPane.add(txtInput);
		
		// 보내기 버튼
		btnSend = new JButton();
		btnSend.setBounds(315, 485, 22, 22);
		btnSend.setIcon(imageResized(new ImageIcon("src/btnIcons/send.png"), 22));
		btnSend.setBorder(new EmptyBorder(0,0,0,0));
		btnSend.setOpaque(false);
		btnSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ChatMsg cm = new ChatMsg(UserName, "200", "message");
				ChatRoom cr = new ChatRoom(roomData.getRoomId(), roomData.getUserNames());
				cr.setLastMsg(txtInput.getText().trim());
				cm.setRoomData(cr);
				parent.SendObject(cm);
				
				txtInput.setText("");
			}
		});
		contentPane.add(btnSend);
	
	}


	
	
	/**
	 * Create the frame.
	 */
//	public ChatClientChat(ChatClientMain parent, String username, int room_id, String userlist) {
//		
//		lastTime = ""; oldTime = "";
//		newUser = ""; oldUser = "";
//	
//			//???
//		textArea = new JTextPane();
//		textArea.setEditable(false);
//		textArea.setFont(new Font("���� ���", Font.PLAIN, 13));
//		scrollPane.setViewportView(textArea);
//		
//		
//		// ä�� �޽��� �Է� 
//		txtInput = new JTextField();
//		txtInput.setBounds(50, 415, 200, 30);
//		txtInput.setFont(new Font("���� ���", Font.PLAIN, 13));
//		contentPane.add(txtInput);
//		txtInput.setColumns(10);
//		
//		
//		
//		// ä�� �޽��� ������ ��ư
//		btnSend = new JButton(new ImageIcon("src/btnIcons/send.png"));
//		btnSend.setBounds(293, 414, 32, 32);
//		btnSend.setFont(new Font("���� ���", Font.BOLD, 13));
//		btnSend.setBackground(backColor);
//		btnSend.setBorderPainted(false);
//		contentPane.add(btnSend);
//
//		// �̸�Ƽ�� �̹��� ��������
//		emoticonbtns[0] = new JButton(emoticons[0] = new ImageIcon("src/emoticons/����.png"));
//		emoticonbtns[0].setPreferredSize(new Dimension(100, 100));
//		emoticonVec.add(new Emoticon(emoticons[0], "(����)"));
//		emoticonbtns[1] = new JButton(emoticons[1] = new ImageIcon("src/emoticons/����.png"));
//		emoticonbtns[1].setPreferredSize(new Dimension(100, 100));
//		emoticonVec.add(new Emoticon(emoticons[1], "(����)"));
//		emoticonbtns[2] = new JButton(emoticons[2] = new ImageIcon("src/emoticons/�ȳ�.png"));
//		emoticonbtns[2].setPreferredSize(new Dimension(100, 100));
//		emoticonVec.add(new Emoticon(emoticons[2], "(�ȳ�)"));
//		emoticonbtns[3] = new JButton(emoticons[3] = new ImageIcon("src/emoticons/�߰�.png"));
//		emoticonbtns[3].setPreferredSize(new Dimension(100, 100));
//		emoticonVec.add(new Emoticon(emoticons[3], "(�߰�)"));
//		emoticonbtns[4] = new JButton(emoticons[4] = new ImageIcon("src/emoticons/����.png"));
//		emoticonbtns[4].setPreferredSize(new Dimension(100, 100));
//		emoticonVec.add(new Emoticon(emoticons[4], "(����)"));
//		emoticonbtns[5] = new JButton(emoticons[5] = new ImageIcon("src/emoticons/�Ϳ���.png"));
//		emoticonbtns[5].setPreferredSize(new Dimension(100, 100));
//		emoticonVec.add(new Emoticon(emoticons[5], "(�Ϳ���)"));
//		emoticonbtns[6] = new JButton(emoticons[6] = new ImageIcon("src/emoticons/����.png"));
//		emoticonbtns[6].setPreferredSize(new Dimension(100, 100));
//		emoticonVec.add(new Emoticon(emoticons[6], "(����)"));
//		emoticonbtns[7] = new JButton(emoticons[7] = new ImageIcon("src/emoticons/����.png"));
//		emoticonbtns[7].setPreferredSize(new Dimension(100, 100));
//		emoticonVec.add(new Emoticon(emoticons[7], "(����)"));
//		emoticonbtns[8] = new JButton(emoticons[8] = new ImageIcon("src/emoticons/��ǳ����.png"));
//		emoticonbtns[8].setPreferredSize(new Dimension(100, 100));
//		emoticonVec.add(new Emoticon(emoticons[8], "(��ǳ����)"));
//		emoticonbtns[9] = new JButton(emoticons[9] = new ImageIcon("src/emoticons/ȭ��.png"));
//		emoticonbtns[9].setPreferredSize(new Dimension(100, 100));
//		emoticonVec.add(new Emoticon(emoticons[9], "(ȭ��)"));
//		emoticonbtns[10] = new JButton(emoticons[10] = new ImageIcon("src/emoticons/���.png"));
//		emoticonbtns[10].setPreferredSize(new Dimension(100, 100));
//		emoticonVec.add(new Emoticon(emoticons[10], "(���)"));
//		emoticonbtns[11] = new JButton(emoticons[11] = new ImageIcon("src/emoticons/�ñ�.png"));
//		emoticonbtns[11].setPreferredSize(new Dimension(100, 100));
//		emoticonVec.add(new Emoticon(emoticons[11], "(�ñ�)"));
//
//		for(int i=0;i<emoticonbtns.length;i++) {
//			int j=i;
//			emoticonbtns[i].addActionListener(new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					ImageIcon icon = (ImageIcon)((JButton)e.getSource()).getIcon();
//					// TODO Auto-generated method stub
//					ChatMsg emo = new ChatMsg(UserName, "400", emoticonVec.elementAt(j).name);
//					emo.setEmoticon(icon);
//					emo.setRoomId(RoomId);
//					emo.setTime(calcTime());
//					mainView.SendObject(emo);
//					emoView.setVisible(false);
//					
//					
//				}
//			});
//		}
//
//	
//		addWindowListener(new WindowListener() {
//
//            @Override
//            public void windowOpened(WindowEvent e) {
//            }
//
//            @Override
//            public void windowIconified(WindowEvent e) {
//            }
//
//            @Override
//            public void windowDeiconified(WindowEvent e) {
//            }
//
//            @Override
//            public void windowDeactivated(WindowEvent e) {
//            }
//
//            @Override
//            public void windowClosing(WindowEvent e) {
//				setVisible(false);
//            }
//
//            @Override
//            public void windowClosed(WindowEvent e) {
//            }
//
//            @Override
//            public void windowActivated(WindowEvent e) {
//            }
//        });
//		
//			
//		try {
//			
//			TextSendAction action = new TextSendAction(); 
//			btnSend.addActionListener(action);
//			txtInput.addActionListener(action);
//			txtInput.requestFocus();
//			ImageSendAction action2 = new ImageSendAction(); // �̹��� ������
//			btnPlus.addActionListener(action2);
//
//
//		} catch (NumberFormatException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			//AppendText("connect error", "");
//		}	
//	}

		// keyboard enter key ġ�� ������ ����
//		class TextSendAction implements ActionListener {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// Send button�� �����ų� �޽��� �Է��ϰ� Enter key ġ��
//				if (e.getSource() == btnSend || e.getSource() == txtInput) {
//					String msg = null;
//					msg = txtInput.getText();
//					msg.trim();
//					
//					System.out.println(msg);
//					for(int i=0;i<emoticonVec.size();i++) {
//						Emoticon emo = emoticonVec.elementAt(i);
//						if(emo.name.equals(msg)) { // (����)
//							ChatMsg cm = new ChatMsg(UserName, "400", msg);
//							cm.setEmoticon(emo.emoticon);
//							cm.setRoomId(RoomId);
//							cm.setTime(calcTime());
//							mainView.SendObject(cm);
//							
//							txtInput.setText("");
//							txtInput.requestFocus();
//							return;
//						}
//					}
//					ChatMsg obcm = new ChatMsg(UserName, "200", msg);
//					obcm.setRoomId(RoomId);
//					obcm.setTime(calcTime());
//					mainView.SendObject(obcm);
//					
//					txtInput.setText(""); // �޼����� ������ ���� �޼��� ����â�� ����.
//					txtInput.requestFocus(); // �޼����� ������ Ŀ���� �ٽ� �ؽ�Ʈ �ʵ�� ��ġ��Ų��
//				
//				}
//			}
//		}
//
//		class ImageSendAction implements ActionListener {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// �׼� �̺�Ʈ�� sendBtn�϶� �Ǵ� textField ���� Enter key ġ��
//				if (e.getSource() == btnPlus) {
//					frame = new Frame("�̹���÷��"); // �������� �ϳ� �����
//					fd = new FileDialog(frame, "�̹��� ����", FileDialog.LOAD); // �̹��� ���� �����ϸ�
//					fd.setVisible(true);
//					ImageIcon ori_icon = new ImageIcon(fd.getDirectory() + fd.getFile()); // ������ �о��
//					ChatMsg obcm = new ChatMsg(UserName, "300", "IMG");
//					Image ori_img = ori_icon.getImage();
//					int width, height;
//					double ratio;
//					width = ori_icon.getIconWidth();
//					height = ori_icon.getIconHeight();
//					// Image�� �ʹ� ũ�� �ִ� ���� �Ǵ� ���� 150 �������� ��ҽ�Ų��.
//					if (width > 150 || height > 150) {
//						if (width > height) { // ���� ����
//							ratio = (double) height / width;
//							width = 150;
//							height = (int) (width * ratio);
//						} else { // ���� ����
//							ratio = (double) width / height;
//							height = 150;
//							width = (int) (height * ratio);
//						}
//						Image new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
//						ImageIcon new_icon = new ImageIcon(new_img);
//						obcm.setChatImg(new_icon);
//					} else
//						obcm.setChatImg(ori_icon);
//					
//					obcm.setChatImg_ori(ori_icon);
//					obcm.setRoomId(RoomId);
//					obcm.setTime(calcTime());
//					mainView.SendObject(obcm);
//					
//				}
//			}
//		}


		
//		public void AppendIcon_text(ImageIcon icon_resized) {
//			int len = textArea.getDocument().getLength();
//			textArea.setCaretPosition(len);
//			
//			String user = newUser;
//			JLabel lbl = new JLabel();
//			lbl.setIcon(icon_resized);
//			lbl.addMouseListener(new MouseAdapter() {
//				 @Override
//		            public void mouseReleased(MouseEvent e) {
//					 	ChatMsg cm = new ChatMsg(user, "510", "origical image");
//					 	mainView.SendObject(cm);
//		            }
//			});
//			textArea.insertComponent(lbl);
//			
//			StyledDocument doc = textArea.getStyledDocument();
//			SimpleAttributeSet left = new SimpleAttributeSet();
//			StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
//		    doc.setParagraphAttributes(doc.getLength(), 1, left, false);
//			try {
//				doc.insertString(doc.getLength(), " ", left );
//				textArea.setCaretPosition(textArea.getDocument().getLength());
//			} catch (BadLocationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		public void AppendIcon_img(ImageIcon icon_resized) {
//			int len = textArea.getDocument().getLength();
//			textArea.setCaretPosition(len);
//			
//			String user = newUser;
//			JLabel lbl = new JLabel();
//			lbl.setIcon(icon_resized);
//			lbl.addMouseListener(new MouseAdapter() {
//				 @Override
//		            public void mouseReleased(MouseEvent e) {
//					 ChatMsg cm = new ChatMsg(user, "510", "origical image");
//					 	mainView.SendObject(cm);
//		            }
//			});
//			textArea.insertComponent(lbl);
//
//			StyledDocument doc = textArea.getStyledDocument();
//			SimpleAttributeSet left = new SimpleAttributeSet();
//			StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
//		    doc.setParagraphAttributes(doc.getLength(), 1, left, false);
//			try {
//				doc.insertString(doc.getLength(), " ", left );
//				textArea.setCaretPosition(textArea.getDocument().getLength());
//				
//			} catch (BadLocationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		// ȭ�鿡 ���
//		public void AppendText_name(String msg) { 
//
//			msg = msg.trim(); // �յ� blank�� \n�� �����Ѵ�.
//			int len = textArea.getDocument().getLength();
//			textArea.setCaretPosition(len); 
//			// ������ �̵�
//			
//			StyledDocument doc = textArea.getStyledDocument();
//			SimpleAttributeSet left = new SimpleAttributeSet();
//			StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
//			StyleConstants.setForeground(left, Color.BLACK);
//			StyleConstants.setFontSize(left, 14);
//		    doc.setParagraphAttributes(doc.getLength(), 1, left, false);
//		    SimpleAttributeSet time = new SimpleAttributeSet();
//		    StyleConstants.setFontSize(time, 11);
//			try {
//				String[] data = msg.split("/");
//				newUser = data[0];
//				if(!data[1].equals(oldTime) || !newUser.equals(oldUser)) { //�ð��� �ٲ���ų�, �޽����� ���� ������ �ٸ���
//					doc.insertString(doc.getLength(), " "+newUser+ "  ", left );
//					doc.insertString(doc.getLength(), (oldTime = data[1]) + "\n", time );
//					oldUser = newUser;
//				}
//				else {
//					doc.insertString(doc.getLength(), " "+newUser+ "\n", left );
//				}
//
//			} catch (BadLocationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//		public void AppendText_msg(String msg) { 
//
//			msg = msg.trim(); // �յ� blank�� \n�� �����Ѵ�.
//			// ������ �̵�
//			int len = textArea.getDocument().getLength();
//			textArea.setCaretPosition(len); 
//			
//			StyledDocument doc = textArea.getStyledDocument();
//			SimpleAttributeSet left = new SimpleAttributeSet();
//			StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
//			StyleConstants.setForeground(left, Color.BLACK);
//		    StyleConstants.setFontSize(left, 15);
//			doc.setParagraphAttributes(doc.getLength(), 1, left, false);
//		   
//			try {
//				doc.insertString(doc.getLength(), msg+"\n\n", left );
//			} catch (BadLocationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//
//		// ȭ�� ������ ���
//		public void AppendTextR_name(String msg) {
//			msg = msg.trim(); // �յ� blank�� \n�� �����Ѵ�.	
//			int len = textArea.getDocument().getLength();
//			textArea.setCaretPosition(len); 
//			
//			StyledDocument doc = textArea.getStyledDocument();
//			SimpleAttributeSet right = new SimpleAttributeSet();
//			StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
//			StyleConstants.setForeground(right, Color.BLACK); 
//			StyleConstants.setFontSize(right, 14);
//		    doc.setParagraphAttributes(doc.getLength(), 1, right, false);
//		    SimpleAttributeSet time = new SimpleAttributeSet();
//		    StyleConstants.setFontSize(time, 11);
//			try {
//				String[] data = msg.split("/");
//				newUser = data[0];
//				if(!data[1].equals(oldTime) || !newUser.equals(oldUser)) {//�ð��� �ٲ���ų�, �޽����� ���� ������ �ٸ���
//					doc.insertString(doc.getLength(), (oldTime = data[1]), time );
//					doc.insertString(doc.getLength(), "  "+newUser+ " \n", right );
//					oldUser = newUser;
//				}
//				else {
//					doc.insertString(doc.getLength(), newUser+ " \n", right );
//				}
//			} catch (BadLocationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		public void AppendTextR_msg(String msg) {
//			msg = msg.trim(); // �յ� blank�� \n�� �����Ѵ�.	
//			
//			int len = textArea.getDocument().getLength();
//			textArea.setCaretPosition(len); 
//			
//			StyledDocument doc = textArea.getStyledDocument();
//			SimpleAttributeSet right = new SimpleAttributeSet();
//			StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
//			StyleConstants.setForeground(right, Color.BLACK);
//			StyleConstants.setFontSize(right, 15);
//		    doc.setParagraphAttributes(doc.getLength(), 1, right, false);
//			try {
//				doc.insertString(doc.getLength(),msg+" \n\n", right );
//				textArea.setCaretPosition(textArea.getDocument().getLength()); 
//			} catch (BadLocationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//
//		public void AppendImage(ImageIcon img_resized, ImageIcon img_ori) {
//			
//			int len = textArea.getDocument().getLength();
//			textArea.setCaretPosition(len); // place caret at the end (with no selection)	
//			
//			JLabel lbl = new JLabel();
//			lbl.setIcon(img_resized);
//			lbl.addMouseListener(new MouseAdapter() {
//				 @Override
//		            public void mouseReleased(MouseEvent e) {
//					 	ImgFrame imgFrame = new ImgFrame(img_ori);
//					 	
//					 	imgFrame.setBounds(myFrame.getLocation().x+100, myFrame.getLocation().y, 350, 350);
//					 	
//					 	imgFrame.setVisible(true);
//		            }
//			});
//			textArea.insertComponent(lbl);
//
//			StyledDocument doc = textArea.getStyledDocument();
//			try {
//				doc.insertString(doc.getLength(), "\n\n", null);
//				textArea.setCaretPosition(len = textArea.getDocument().getLength());
//				
//			} catch (BadLocationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}

		
		// Windows ó�� message ������ ������ �κ��� NULL �� ����� ���� �Լ�
		public byte[] MakePacket(String msg) {
			byte[] packet = new byte[BUF_LEN];
			byte[] bb = null;
			int i;
			for (i = 0; i < BUF_LEN; i++)
				packet[i] = 0;
			try {
				bb = msg.getBytes("euc-kr");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(0);
			}
			for (i = 0; i < bb.length; i++)
				packet[i] = bb[i];
			return packet;
		}

		public class EmoticonFrame extends JFrame{
			private static final long serialVersionUID = 1L;
			
			private JLabel lbltxt;
			private JButton makeRoom;
			
			EmoticonFrame(){
				setBounds(myFrame.getLocation().x+350, myFrame.getLocation().y+35, 340, 465);
				setTitle("�̸�Ƽ�� ������");
				
				JPanel contentPane = new JPanel();
				contentPane.setBackground(backColor);
				setContentPane(contentPane);
				
				JPanel emoticonPanel = new JPanel(new GridLayout(4, 3, 5, 5));
				emoticonPanel.setBackground(backColor);
				JScrollPane emoticonScrollPane = new JScrollPane(emoticonPanel);
				contentPane.add(emoticonScrollPane);

				for(int j=0;j<12;j++) {
					emoticonPanel.add(emoticonbtns[j]);
					//emoticonPanel.add(emoticonlbls[j]);
				}
					
			}
		}
		
		public class Emoticon{
			public ImageIcon emoticon;
			public String name;
			
			Emoticon(ImageIcon icon, String name){
				emoticon = icon;
				this.name = name;
			}
			
		}
		
		private class ImgFrame extends JFrame{
			private static final long serialVersionUID = 1L;
			
			ImgFrame(ImageIcon img){
				
				JPanel contentPane = new JPanel(null);
				contentPane.setBackground(Color.WHITE);
				setContentPane(contentPane);
				
				Image ori_img = img.getImage();
				int width, height;
				double ratio;
				width = img.getIconWidth();
				height = img.getIconHeight();
				// Image�� �ʹ� ũ�� �ִ� ���� �Ǵ� ���� 300 �������� ��ҽ�Ų��.
				if (width > 300 || height > 300) {
					if (width > height) { // ���� ����
						ratio = (double) height / width;
						width = 300;
						height = (int) (width * ratio);
					} else { // ���� ����
						ratio = (double) width / height;
						height = 300;
						width = (int) (height * ratio);
					}
					Image new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
					ImageIcon new_icon = new ImageIcon(new_img);
					img =  new_icon;
				} 

				JLabel lblImg = new JLabel();
				lblImg.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
				lblImg.setIcon(img);
				contentPane.add(lblImg);
				
				setVisible(true);
			}
		}
		// ���� �ð� ����Ͽ� ���
		private String calcTime() {
			String t;
			LocalTime nowTime = LocalTime.now();
			int hour = nowTime.getHour(); 
			int minute = nowTime.getMinute();
			String m;
			if(minute<10) {
				m = "0" + minute;
			}else {
				m = ""+ minute;
			}
			if(hour < 12) {
				t = "���� " + hour+ ":" + m;
			}else if(hour == 12){
				t = "���� 12:" + m;
			}
			else {
				t = "���� " + (hour-12) + ":" + m;
			}
			return t;
		}
		// ���� ��¥ ����Ͽ� ���
		private String calcDate() {
			
			LocalDate nowData = LocalDate.now();
			int year = nowData.getYear();
			int month = nowData.getMonthValue();
			int dayOfMonth = nowData.getDayOfMonth();
			int dayOfWeek = nowData.getDayOfWeek().getValue();
			String dayOfWeek_str = "";
			switch(dayOfWeek) {
			case 1: dayOfWeek_str = "������"; break;
			case 2: dayOfWeek_str = "ȭ����"; break;
			case 3: dayOfWeek_str = "������"; break;
			case 4: dayOfWeek_str = "�����"; break;
			case 5: dayOfWeek_str = "�ݿ���"; break;
			case 6: dayOfWeek_str = "�����"; break;
			case 7: dayOfWeek_str = "�Ͽ���"; break;
			}
			return (year+"�� "+month+"�� "+dayOfMonth+"�� "+dayOfWeek_str);
		}	
		
		//-----------
		
		
		public ImageIcon imageResized(ImageIcon ori_icon, int size) {
			Image img = ori_icon.getImage();
			int width = ori_icon.getIconWidth();
			int height = ori_icon.getIconHeight();

			return new ImageIcon(img.getScaledInstance(size, size, Image.SCALE_SMOOTH));

		}
		
		public int calculatePanelHeight() {
			int itemCount = 0;
			
		    for (int i = 0; i < chatPanel.getComponentCount(); i++) {
		        if (chatPanel.getComponent(i) instanceof ChatItem) {
		            itemCount++;
		        }
		    }
		    int itemHeight = 60; 
		    int spacing = 10;
		    return itemCount * (itemHeight + spacing);
		}
}




