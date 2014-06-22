package org.zachtaylor.emissary.event;

import org.zachtaylor.emissary.WebsocketConnection;

public class WebsocketConnectionClose {
	private final WebsocketConnection websocket;

	public WebsocketConnectionClose(WebsocketConnection wc) {
		websocket = wc;
	}

	public WebsocketConnection getWebsocket() {
		return websocket;
	}
}
