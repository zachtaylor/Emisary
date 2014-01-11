package org.zachtaylor.emissary.event;

import org.zachtaylor.emissary.WebsocketConnection;

public class WebsocketConnectionOpen {
  private final WebsocketConnection websocket;

  public WebsocketConnectionOpen(WebsocketConnection wc) {
    websocket = wc;
  }

  public WebsocketConnection getWebsocket() {
    return websocket;
  }
}