package dev.hbrown;

import dev.hbrown.pdu.SubmitSM;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.out;

public class StringDeliveryInfo extends AbstractDeliveryInfo<StringDeliveryInfo.StringDeliveryEntry> {

  private final AtomicInteger stringDeliveryCounter = new AtomicInteger();

  @Override
  protected int deliver(StringDeliveryEntry entry) {
    final int currentCount = stringDeliveryCounter.incrementAndGet();
    out.printf("%s.currentCount=[%s]%n", getClass().getSimpleName(), currentCount);
    return currentCount;
  }

  public int getDeliveryCount() {
    return stringDeliveryCounter.get();
  }

  public static class StringDeliveryEntry extends AbstractDeliveryEntry {
    public final SubmitSM submitSM;

    public StringDeliveryEntry(int statusCode, int errorCode, SubmitSM submitSM) {
      super(statusCode, errorCode);
      this.submitSM = submitSM;
    }
  }
}
