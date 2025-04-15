package data;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ChatRoom {
	public int roomId = 0;
	public String userList;
	public String[] userNameList;
	public String roomName;
	public JPanel roomImg;
	public String lastMsg;
	public String lastTime;
	public Vector<ChatMsg> chatMsgs = new Vector<ChatMsg>();
	
	
	public ChatRoom(int id, String nameList){
		roomId = id;
		userList = nameList;
		userNameList = nameList.split(" ");
	}
	
	public int getRoomId() {
		return roomId;
	}
	
	public String getUserList() {
		return userList;
	}
	
	public void addUser(String user) {
		userList = userList + " user";
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

}
