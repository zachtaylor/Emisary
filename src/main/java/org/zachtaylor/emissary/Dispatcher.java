package org.zachtaylor.emissary;

import org.json.JSONObject;

public interface Dispatcher {
  public void dispatch(WebsocketConnection wsc, JSONObject json);
}