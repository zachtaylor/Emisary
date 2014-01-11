package org.zachtaylor.emissary;

import junit.framework.TestCase;

import org.zachtaylor.emissary.Emissary;
import org.zachtaylor.emissary.testclasses.CallCounter;
import org.zachtaylor.emissary.testclasses.Event1;
import org.zachtaylor.emissary.testclasses.Event2;

public class TU_Emissary extends TestCase {
  public void testSimpleCallback() {
    CallCounter counter = new CallCounter();
    Emissary.register(Event1.class, counter);

    assertEquals(0, counter.getParentCalls());

    Emissary.post(new Event1("hello world"));

    assertEquals(1, counter.getParentCalls());

    Emissary.unregister(Event1.class, counter);
  }

  public void testUnregistration() {
    CallCounter counter = new CallCounter();
    Emissary.register(Event1.class, counter);

    assertEquals(0, counter.getParentCalls());

    Emissary.post(new Event1("hello world"));

    assertEquals(1, counter.getParentCalls());

    Emissary.unregister(Event1.class, counter);

    Emissary.post(new Event1("hello world"));

    assertEquals(1, counter.getParentCalls());
  }

  public void testUniqueCallback() {
    CallCounter counter = new CallCounter();
    Emissary.register(Event1.class, counter);
    Emissary.register(Event2.class, counter);

    assertEquals(0, counter.getParentCalls());
    assertEquals(0, counter.getChildCalls());

    Emissary.post(new Event1("hello world"));

    assertEquals(1, counter.getParentCalls());
    assertEquals(0, counter.getChildCalls());

    Emissary.post(new Event2("foo bar"));

    assertEquals(1, counter.getParentCalls());
    assertEquals(1, counter.getChildCalls());

    Emissary.unregister(Event1.class, counter);
    Emissary.unregister(Event2.class, counter);
  }
}