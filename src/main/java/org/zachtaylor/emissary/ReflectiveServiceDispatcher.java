package org.zachtaylor.emissary;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class ReflectiveServiceDispatcher implements Dispatcher {
  protected Map<String, Object> services = new HashMap<String, Object>();

  @Override
  public void dispatch(WebsocketConnection wsc, JSONObject json) {
    Object service = services.get(json.getString("service"));
    Method method = getMethod(service.getClass(), json.getString("method"));
    invokeMethod(new Callback(service, method), wsc, json);
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
      if (!WebsocketConnection.class.equals(parameters[0])) {
        continue;
      }
      if (!(JSONObject.class.equals(parameters[1]))) {
        continue;
      }

      return m;
    }

    return null;
  }

  protected void invokeMethod(Callback callback, WebsocketConnection connection, JSONObject json) {
    callback.call(connection, json);
  }

  public ReflectiveServiceDispatcher putService(String name, Object service) {
    services.put(name, service);
    return this;
  }

  public ReflectiveServiceDispatcher removeService(String name) {
    services.remove(name);
    return this;
  }

  public Object getService(String name) {
    return services.get(name);
  }

  public Map<String, Object> getServices() {
    return services;
  }

  public ReflectiveServiceDispatcher setServices(Map<String, Object> s) {
    services = s;
    return this;
  }
}