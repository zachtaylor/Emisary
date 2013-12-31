package org.zachtaylor.emisary.event;

import org.zachtaylor.emisary.WebsocketConnection;

public class WebsocketConnectionClose {
  private final WebsocketConnection websocket;

  public WebsocketConnectionClose(WebsocketConnection wc) {
    websocket = wc;
  }

  public WebsocketConnection getWebsocket() {
    return websocket;
  }
}