package org.zachtaylor.emisary.event;

import org.zachtaylor.emisary.WebsocketConnection;

public class WebsocketConnectionOpen {
  private final WebsocketConnection websocket;

  public WebsocketConnectionOpen(WebsocketConnection wc) {
    websocket = wc;
  }

  public WebsocketConnection getWebsocket() {
    return websocket;
  }
}