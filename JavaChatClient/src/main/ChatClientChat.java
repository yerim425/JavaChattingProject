/*
 * java chatting view frame
 */
package main;

import java.awt.FileDialog;
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



public class ChatClientChat extends JFrame{
	/**
	 *  ä�ù� frame
	 */
	private static final long serialVersionUID = 1L;
	private ChatClientMain mainView; 
	private JFrame myFrame = this;
	private JPanel contentPane;
	public JScrollPane scrollPane;
	private JTextField txtInput;
	public String UserName;
	public int RoomId;
	private JButton btnSend;
	private static final int BUF_LEN = 128; // Windows ó�� BUF_LEN �� ����
	
	private JLabel lblRoomName;
	public JTextPane textArea;
	
	private Frame frame;
	private FileDialog fd;
	
	private JButton btnBack; // ä�ù� ������
	private JButton btnPlus; // �̹��� ������
	private JButton btnSmile; // �̸�Ƽ�� ������
	
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
	private EmoticonFrame emoView;
//------------------------------------
	private ChatRoomListView parent;
	private ChatRoom roomData;
	
	public ChatClientChat(ChatRoomListView parent, ChatRoom roomData) {
		this.parent = parent;
		this.roomData = roomData;
	}

	/**
	 * Create the frame.
	 */
//	public ChatClientChat(ChatClientMain parent, String username, int room_id, String userlist) {
//		setResizable(false);
//		mainView = parent;
//		setTitle(username + "�� ä��ȭ��");
//		
//		UserName = username;
//		RoomId = room_id;
//		userList = userlist;
//		userNameArr = userlist.split(" ");
//		lastMsg = "";
//		lastTime = ""; oldTime = "";
//		newUser = ""; oldUser = "";
//	
//		
//		if(userNameArr.length == 1) {
//			RoomName = username;
//		}else if(userNameArr.length == 2) {
//			for(int i=0;i<2;i++) {
//				if(!userNameArr[i].matches(username)) {
//					RoomName = userNameArr[i];
//				}
//			}	
//		}else {
//			String str = "";
//			for(int i=0;i<userNameArr.length;i++) {
//				if(!userNameArr[i].matches(username)) {
//					if(i+1 == userNameArr.length-1 && userNameArr[i+1].matches(username))
//						str = str + userNameArr[i];
//					else {
//						if(i == userNameArr.length-1)
//							str = str + userNameArr[i];
//						else {
//							str = str + userNameArr[i] + ", ";
//						}
//					}
//				}
//			}
//			
//			RoomName = str;
//		}
//		
//		
//		contentPane = new JPanel();
//		contentPane.setBackground(backColor);
//		setContentPane(contentPane);
//		contentPane.setLayout(null);
//		
//		// ä�ù� ������ ��ư
//		btnBack = new JButton(new ImageIcon("src/btnIcons/back.png"));
//		btnBack.setBackground(backColor);
//		btnBack.setBorderPainted(false);
//		btnBack.setBounds(10, 5, 30, 30);
//		btnBack.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				setVisible(false);
//			}
//			
//		});
//		contentPane.add(btnBack);
//		
//		// ä�ù� �̸� ��
//		lblRoomName = new JLabel(RoomName);
//		lblRoomName.setBounds(55, 5, 300, 30);
//		lblRoomName.setFont(new Font("���� ���", Font.BOLD, 15));
//		contentPane.add(lblRoomName);
//		
//		
//		scrollPane = new JScrollPane();
//		scrollPane.setBounds(10, 40, 315, 360);
//		scrollPane.setBackground(Color.white);
//		contentPane.add(scrollPane);
//
//		textArea = new JTextPane();
//		textArea.setEditable(false);
//		textArea.setFont(new Font("���� ���", Font.PLAIN, 13));
//		scrollPane.setViewportView(textArea);
//		
//		// �̹��� ������ ��ư
//		btnPlus = new JButton(new ImageIcon("src/btnIcons/plus.png"));
//		//btnPlus.setFont(new Font("���� ���", Font.BOLD, 13));
//		btnPlus.setBackground(backColor);
//		btnPlus.setBorderPainted(false);
//		btnPlus.setBounds(10, 415, 30, 30);
//		contentPane.add(btnPlus);
//		
//		// ä�� �޽��� �Է� 
//		txtInput = new JTextField();
//		txtInput.setBounds(50, 415, 200, 30);
//		txtInput.setFont(new Font("���� ���", Font.PLAIN, 13));
//		contentPane.add(txtInput);
//		txtInput.setColumns(10);
//		
//		// �̸�Ƽ�� ������ ��ư
//		btnSmile = new JButton(new ImageIcon("src/btnIcons/smile.png"));
//		btnSmile.setBounds(257, 415, 30, 30);
//		btnSmile.setFont(new Font("���� ���", Font.BOLD, 13));
//		btnSmile.setBackground(backColor);
//		btnSmile.setBorderPainted(false);
//		btnSmile.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				emoView = new EmoticonFrame();
//				emoView.setVisible(true);
//			}
//			
//		});
//		contentPane.add(btnSmile);
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


		
		public void AppendIcon_text(ImageIcon icon_resized) {
			int len = textArea.getDocument().getLength();
			textArea.setCaretPosition(len);
			
			String user = newUser;
			JLabel lbl = new JLabel();
			lbl.setIcon(icon_resized);
			lbl.addMouseListener(new MouseAdapter() {
				 @Override
		            public void mouseReleased(MouseEvent e) {
					 	ChatMsg cm = new ChatMsg(user, "510", "origical image");
					 	mainView.SendObject(cm);
		            }
			});
			textArea.insertComponent(lbl);
			
			StyledDocument doc = textArea.getStyledDocument();
			SimpleAttributeSet left = new SimpleAttributeSet();
			StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
		    doc.setParagraphAttributes(doc.getLength(), 1, left, false);
			try {
				doc.insertString(doc.getLength(), " ", left );
				textArea.setCaretPosition(textArea.getDocument().getLength());
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void AppendIcon_img(ImageIcon icon_resized) {
			int len = textArea.getDocument().getLength();
			textArea.setCaretPosition(len);
			
			String user = newUser;
			JLabel lbl = new JLabel();
			lbl.setIcon(icon_resized);
			lbl.addMouseListener(new MouseAdapter() {
				 @Override
		            public void mouseReleased(MouseEvent e) {
					 ChatMsg cm = new ChatMsg(user, "510", "origical image");
					 	mainView.SendObject(cm);
		            }
			});
			textArea.insertComponent(lbl);

			StyledDocument doc = textArea.getStyledDocument();
			SimpleAttributeSet left = new SimpleAttributeSet();
			StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
		    doc.setParagraphAttributes(doc.getLength(), 1, left, false);
			try {
				doc.insertString(doc.getLength(), " ", left );
				textArea.setCaretPosition(textArea.getDocument().getLength());
				
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// ȭ�鿡 ���
		public void AppendText_name(String msg) { 

			msg = msg.trim(); // �յ� blank�� \n�� �����Ѵ�.
			int len = textArea.getDocument().getLength();
			textArea.setCaretPosition(len); 
			// ������ �̵�
			
			StyledDocument doc = textArea.getStyledDocument();
			SimpleAttributeSet left = new SimpleAttributeSet();
			StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
			StyleConstants.setForeground(left, Color.BLACK);
			StyleConstants.setFontSize(left, 14);
		    doc.setParagraphAttributes(doc.getLength(), 1, left, false);
		    SimpleAttributeSet time = new SimpleAttributeSet();
		    StyleConstants.setFontSize(time, 11);
			try {
				String[] data = msg.split("/");
				newUser = data[0];
				if(!data[1].equals(oldTime) || !newUser.equals(oldUser)) { //�ð��� �ٲ���ų�, �޽����� ���� ������ �ٸ���
					doc.insertString(doc.getLength(), " "+newUser+ "  ", left );
					doc.insertString(doc.getLength(), (oldTime = data[1]) + "\n", time );
					oldUser = newUser;
				}
				else {
					doc.insertString(doc.getLength(), " "+newUser+ "\n", left );
				}

			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void AppendText_msg(String msg) { 

			msg = msg.trim(); // �յ� blank�� \n�� �����Ѵ�.
			// ������ �̵�
			int len = textArea.getDocument().getLength();
			textArea.setCaretPosition(len); 
			
			StyledDocument doc = textArea.getStyledDocument();
			SimpleAttributeSet left = new SimpleAttributeSet();
			StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
			StyleConstants.setForeground(left, Color.BLACK);
		    StyleConstants.setFontSize(left, 15);
			doc.setParagraphAttributes(doc.getLength(), 1, left, false);
		   
			try {
				doc.insertString(doc.getLength(), msg+"\n\n", left );
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// ȭ�� ������ ���
		public void AppendTextR_name(String msg) {
			msg = msg.trim(); // �յ� blank�� \n�� �����Ѵ�.	
			int len = textArea.getDocument().getLength();
			textArea.setCaretPosition(len); 
			
			StyledDocument doc = textArea.getStyledDocument();
			SimpleAttributeSet right = new SimpleAttributeSet();
			StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
			StyleConstants.setForeground(right, Color.BLACK); 
			StyleConstants.setFontSize(right, 14);
		    doc.setParagraphAttributes(doc.getLength(), 1, right, false);
		    SimpleAttributeSet time = new SimpleAttributeSet();
		    StyleConstants.setFontSize(time, 11);
			try {
				String[] data = msg.split("/");
				newUser = data[0];
				if(!data[1].equals(oldTime) || !newUser.equals(oldUser)) {//�ð��� �ٲ���ų�, �޽����� ���� ������ �ٸ���
					doc.insertString(doc.getLength(), (oldTime = data[1]), time );
					doc.insertString(doc.getLength(), "  "+newUser+ " \n", right );
					oldUser = newUser;
				}
				else {
					doc.insertString(doc.getLength(), newUser+ " \n", right );
				}
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void AppendTextR_msg(String msg) {
			msg = msg.trim(); // �յ� blank�� \n�� �����Ѵ�.	
			
			int len = textArea.getDocument().getLength();
			textArea.setCaretPosition(len); 
			
			StyledDocument doc = textArea.getStyledDocument();
			SimpleAttributeSet right = new SimpleAttributeSet();
			StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
			StyleConstants.setForeground(right, Color.BLACK);
			StyleConstants.setFontSize(right, 15);
		    doc.setParagraphAttributes(doc.getLength(), 1, right, false);
			try {
				doc.insertString(doc.getLength(),msg+" \n\n", right );
				textArea.setCaretPosition(textArea.getDocument().getLength()); 
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void AppendImage(ImageIcon img_resized, ImageIcon img_ori) {
			
			int len = textArea.getDocument().getLength();
			textArea.setCaretPosition(len); // place caret at the end (with no selection)	
			
			JLabel lbl = new JLabel();
			lbl.setIcon(img_resized);
			lbl.addMouseListener(new MouseAdapter() {
				 @Override
		            public void mouseReleased(MouseEvent e) {
					 	ImgFrame imgFrame = new ImgFrame(img_ori);
					 	
					 	imgFrame.setBounds(myFrame.getLocation().x+100, myFrame.getLocation().y, 350, 350);
					 	
					 	imgFrame.setVisible(true);
		            }
			});
			textArea.insertComponent(lbl);

			StyledDocument doc = textArea.getStyledDocument();
			try {
				doc.insertString(doc.getLength(), "\n\n", null);
				textArea.setCaretPosition(len = textArea.getDocument().getLength());
				
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		
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
}




