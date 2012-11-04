package com.tt.mariage.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RetrieveServiceAsync {
  public void retrieve(String mail, AsyncCallback<UserData> async);
}
