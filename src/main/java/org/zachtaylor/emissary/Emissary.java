package org.zachtaylor.emissary;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Emissary {
  private static Map<Class<?>, List<Callback>> callbacks = new HashMap<Class<?>, List<Callback>>();

  public static void register(Class<?> eventClass, Object object) {
    Callback callback = getCallback(object, eventClass);
    if (callback == null) {
      return;
    }

    List<Callback> callbackList;
    synchronized (Emissary.class) {
      callbackList = callbacks.get(eventClass);
      if (callbackList == null) {
        callbacks.put(eventClass, callbackList = new ArrayList<Callback>());
      }
    }

    synchronized (callbackList) {
      callbackList.add(callback);
    }
  }

  public static void unregister(Class<?> eventClass, Object object) {
    Callback callback = getCallback(object, eventClass);
    if (callback == null) {
      return;
    }

    List<Callback> callbackList;
    synchronized (Emissary.class) {
      callbackList = callbacks.get(eventClass);
    }
    if (callbackList == null) {
      return;
    }

    synchronized (callbackList) {
      callbackList.remove(callback);
    }
  }

  public static void post(Object event) {
    Class<?> eventClass = event.getClass();

    List<Callback> callbackList;
    synchronized (Emissary.class) {
      callbackList = callbacks.get(eventClass);
    }
    if (callbackList == null) {
      return;
    }

    synchronized (callbackList) {
      for (Callback callback : callbackList) {
        callback.call(event);
      }
    }
  }

  private static Callback getCallback(Object context, Class<?> eventClass) {
    Callback callback = null;

    try {
      Method method = context.getClass().getMethod("call", eventClass);
      callback = new Callback(context, method);
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return callback;
  }
}