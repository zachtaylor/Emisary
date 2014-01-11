package org.zachtaylor.emissary;

import java.lang.reflect.Method;

public class Callback {
  public Callback(Object context, Method method) {
    this.context = context;
    this.method = method;
  }

  public void call(Object... arguments) {
    try {
      method.invoke(context, arguments);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Method getMethod() {
    return method;
  }

  public Object getContext() {
    return context;
  }

  @Override
  public int hashCode() {
    return context.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Callback)) {
      return false;
    }

    Callback cb = (Callback) o;
    return context == cb.context && method.equals(cb.method);
  }

  private final Method method;
  private final Object context;
}