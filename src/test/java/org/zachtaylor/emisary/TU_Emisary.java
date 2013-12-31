package org.zachtaylor.emisary;

import junit.framework.TestCase;

import org.zachtaylor.emisary.Emisary;
import org.zachtaylor.emisary.testclasses.CallCounter;
import org.zachtaylor.emisary.testclasses.Event2;
import org.zachtaylor.emisary.testclasses.Event1;

public class TU_Emisary extends TestCase {
  public void testSimpleCallback() {
    CallCounter counter = new CallCounter();
    Emisary.register(Event1.class, counter);

    assertEquals(0, counter.getParentCalls());

    Emisary.post(new Event1("hello world"));

    assertEquals(1, counter.getParentCalls());

    Emisary.unregister(Event1.class, counter);
  }

  public void testUnregistration() {
    CallCounter counter = new CallCounter();
    Emisary.register(Event1.class, counter);

    assertEquals(0, counter.getParentCalls());

    Emisary.post(new Event1("hello world"));

    assertEquals(1, counter.getParentCalls());

    Emisary.unregister(Event1.class, counter);

    Emisary.post(new Event1("hello world"));

    assertEquals(1, counter.getParentCalls());
  }

  public void testUniqueCallback() {
    CallCounter counter = new CallCounter();
    Emisary.register(Event1.class, counter);
    Emisary.register(Event2.class, counter);

    assertEquals(0, counter.getParentCalls());
    assertEquals(0, counter.getChildCalls());

    Emisary.post(new Event1("hello world"));

    assertEquals(1, counter.getParentCalls());
    assertEquals(0, counter.getChildCalls());

    Emisary.post(new Event2("foo bar"));

    assertEquals(1, counter.getParentCalls());
    assertEquals(1, counter.getChildCalls());

    Emisary.unregister(Event1.class, counter);
    Emisary.unregister(Event2.class, counter);
  }
}