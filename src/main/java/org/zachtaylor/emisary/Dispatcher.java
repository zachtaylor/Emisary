package org.zachtaylor.emisary;

import org.json.JSONObject;

public interface Dispatcher {
  public void dispatch(WebsocketConnection wsc, JSONObject json);
}