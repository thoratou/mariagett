package com.tt.mariage.client.data;

import com.tt.mariage.client.services.UserData;

public class UserDataHandler {
	private UserData userData;
	
	public UserDataHandler(UserData userData) {
		this.userData = userData;
	}

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}
}
