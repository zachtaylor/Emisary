package org.zachtaylor.emissary;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class ReflectiveDispatchEmissary extends Emissary {
	protected Map<String, Object> emissaries = new HashMap<String, Object>();

	@Override
	public void serve(WebsocketConnection connection, JSONObject json) {
		Object service = getEmissary(json.getString("service"));
		Method method = getMethod(service.getClass(), json.getString("method"));
		invokeMethod(new Callback(service, method), connection, json);
	}

	protected Method getMethod(Class<?> serviceClass, String methodName) {
		Method[] methods = serviceClass.getMethods();

		for (Method m : methods) {
			Class<?>[] parameters = m.getParameterTypes();

			if (!m.getName().equals(methodName)) {
				continue;
			}
			if (parameters.length != 2) {
				continue;
			}
			if (!WebsocketConnection.class.isAssignableFrom(parameters[0])) {
				continue;
			}
			if (!(JSONObject.class.isAssignableFrom(parameters[1]))) {
				continue;
			}

			return m;
		}

		return null;
	}

	protected void invokeMethod(Callback callback, WebsocketConnection connection, JSONObject json) {
		callback.call(connection, json);
	}

	public ReflectiveDispatchEmissary putService(String name, Object service) {
		emissaries.put(name, service);
		return this;
	}

	public ReflectiveDispatchEmissary removeEmissary(String name) {
		emissaries.remove(name);
		return this;
	}

	public Object getEmissary(String name) {
		return emissaries.get(name);
	}

	public Map<String, Object> getEmissaries() {
		return emissaries;
	}

	public ReflectiveDispatchEmissary setEmissaries(Map<String, Object> s) {
		emissaries = s;
		return this;
	}
}
