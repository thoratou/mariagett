package com.tt.mariage.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RegisterServiceAsync {
  public void register(String mail, AsyncCallback<RegisterInfo> async);
}
