package org.zachtaylor.emissary;

public abstract class MiddlemanEmissary extends Emissary {
  protected Emissary nextEmissary;

  public Emissary getNextEmissary() {
    return nextEmissary;
  }

  public void setNextEmissary(Emissary nextEmissary) {
    this.nextEmissary = nextEmissary;
  }
}
