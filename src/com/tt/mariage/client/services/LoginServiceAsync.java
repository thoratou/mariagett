package com.tt.mariage.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {
  public void login(String mail, String pwd, AsyncCallback<LoginInfo> async);
}
