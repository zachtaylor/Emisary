package org.zachtaylor.emissary;

import java.io.IOException;

import org.eclipse.jetty.websocket.WebSocket;
import org.json.JSONObject;
import org.zachtaylor.emissary.event.WebsocketConnectionClose;
import org.zachtaylor.emissary.event.WebsocketConnectionOpen;

public class WebsocketConnection implements WebSocket.OnTextMessage {
  private Connection connection;
  private Dispatcher dispatcher;

  public void onClose(int arg0, String arg1) {
    Emissary.post(new WebsocketConnectionClose(this));
  }

  public void onOpen(Connection c) {
    connection = c;
    Emissary.post(new WebsocketConnectionOpen(this));
  }

  public void onMessage(String message) {
    JSONObject json = new JSONObject(message);
    getDispatcher().dispatch(this, json);
  }

  public Dispatcher getDispatcher() {
    return dispatcher;
  }

  public WebsocketConnection setDispatcher(Dispatcher d) {
    dispatcher = d;
    return this;
  }

  public void call(String controller, String methodName) {
    send("{action:" + controller + "" + methodName + "}");
  }

  public void call(String controller, String methodName, JSONObject options) {
    options.put("action", controller + ':' + methodName);
    send(options);
  }

  public void send(JSONObject json) {
    send(json.toString());
  }

  public void send(String string) {
    try {
      connection.sendMessage(string);
    }
    catch (IOException e) { // TODO - log
      e.printStackTrace();
    }
  }
}