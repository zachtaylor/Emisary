package org.zachtaylor.emissary.testclasses;

public class CallCounter {
  private int event1Calls, event2Calls;

  public void call(Event1 event) {
    event1Calls++;
  }

  public void call(Event2 event) {
    event2Calls++;
  }

  public int getParentCalls() {
    return event1Calls;
  }

  public int getChildCalls() {
    return event2Calls;
  }

  public int hashCode() {
    return event1Calls * 307 + event2Calls;
  }

  public boolean equals(Object o) {
    if (!(o instanceof CallCounter)) {
      return false;
    }

    CallCounter cc = (CallCounter) o;
    return cc.event1Calls == this.event1Calls && cc.event2Calls == this.event2Calls;
  }
}