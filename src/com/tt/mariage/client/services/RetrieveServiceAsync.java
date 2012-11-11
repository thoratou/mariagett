package com.tt.mariage.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RetrieveServiceAsync {
  void retrieve(String mail, AsyncCallback<RetrieveData> async);
}
