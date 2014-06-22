package org.zachtaylor.emissary;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class DispatchEmissary extends Emissary {
	protected Map<String, Emissary> emissaries = new HashMap<String, Emissary>();

	@Override
	public void serve(WebsocketConnection connection, JSONObject json) {
		Emissary service = getEmissary(json.getString("event"));

		if (service != null) {
			service.serve(connection, json);
		}
	}

	public DispatchEmissary putEmissary(String name, Emissary service) {
		emissaries.put(name, service);
		return this;
	}

	public DispatchEmissary removeEmissary(String name) {
		emissaries.remove(name);
		return this;
	}

	public Emissary getEmissary(String name) {
		return emissaries.get(name);
	}

	public Map<String, Emissary> getEmissaries() {
		return emissaries;
	}

	public DispatchEmissary setEmissaries(Map<String, Emissary> e) {
		emissaries = e;
		return this;
	}
}
