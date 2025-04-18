package data;
import java.io.Serializable;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ChatRoom implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int roomId = -1;
	private String userName;
	private String userNames = "";
	private String[] userNameList;
	private String roomName;
	private JPanel roomImg;
	private String lastMsg = "대화없음";
	private String lastTime = " ";
	private Vector<ChatMsg> chatMsgs = new Vector<ChatMsg>();
	
	private ImageIcon emoticon;
	
	
	private ImageIcon chatImg;
	//private ImageIcon chatImg_resized;
	
	public ChatRoom(int id, String nameList){
		roomId = id;
		userNames = nameList;
		userNameList = nameList.split(" ");
		userName = userNameList[0];
		
		// 채팅방 이름 설정
		if(userNameList.length == 1) { // 나만 있는 채팅방
			roomName = userNameList[0];
		}else if(userNameList.length == 2){
			roomName = userNameList[1];
		}else {
			for(int i=1;i<userNameList.length;i++) {
				roomName += userNameList[i] + ", ";
			}
			roomName = roomName.substring(0, roomName.length()-2);
		}
		
	}
	
	public int getRoomId() {
		return roomId;
	}
	
	public String getUserNames() {
		return userNames;
	}
	
	public void addUser(String user) {
		userNames = userNames + " user";
	}
	
	public void exitUser(String user) {
		//userList = 
	}
	public String[] getUserNameList() {
		return userNameList;
	}

	public void setRoomImgIcon(JPanel img) {
		roomImg = img;
	}
	public JPanel getRoomImg() {
		return roomImg;
	}
	
	public void setRoomName(String name) {
		roomName = name;
	}
	
	public String getRoomName() {
		return roomName;
	}
	
	public void setLastMsg(String msg) {
		lastMsg = msg;
	}
	
	public String getLastMsg() {
		return lastMsg;
	}
	
	public void setLastTime(String time) {
		lastTime = time;
	}
	
	public String getLastTime() {
		return lastTime;
	}
	
	public void addChatMsg(ChatMsg cm) {
		chatMsgs.add(cm);
	}
	
	public Vector<ChatMsg> getChatMsgs(){
		return chatMsgs;
	}
	
//	public void setChatImg(ImageIcon img) {
//		this.chatImg_resized = img;
//	}
//	
//	public ImageIcon getChatImg() {
//		return chatImg_resized;
//	}
//	
	public void setChatImg(ImageIcon img) {
		this.chatImg = img;
	}
	
	public ImageIcon getChatImg() {
		return chatImg;
	}
	public void setRoomId(int id) {
		this.roomId = id;
	}
	
	
	public void setUserList(String list) {
		userNames = list;
	}
	
	public void setEmoticon(ImageIcon icon) {
		this.emoticon = icon;
	}
	
	public ImageIcon getEmoticon() {
		return emoticon;
	}
	

}
