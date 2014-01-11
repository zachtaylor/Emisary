package org.zachtaylor.emissary.testclasses;

public class Event1 {
  private String string;

  public Event1(String s) {
    setString(s);
  }

  public String getString() {
    return string;
  }

  public void setString(String string) {
    this.string = string;
  }
}