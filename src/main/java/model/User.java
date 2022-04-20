package model;

public class User {
	
	private String nickname;
	private boolean isOwner;
	private int chooseNum;

	public int getChooseNum() {
		return chooseNum;
	}

	public void setChooseNum(int chooseNum) {
		this.chooseNum = chooseNum;
	}

	public User(String nickname, boolean isOwner) {
		this.nickname = nickname;
		this.isOwner = isOwner;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public boolean isOwner() {
		return isOwner;
	}

	public void setOwner(boolean isOwner) {
		this.isOwner = isOwner;
	}
	
}
