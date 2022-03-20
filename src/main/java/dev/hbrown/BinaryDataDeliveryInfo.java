package dev.hbrown;

import dev.hbrown.pdu.DataSM;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.out;

public class BinaryDataDeliveryInfo extends AbstractDeliveryInfo<BinaryDataDeliveryInfo.BinaryDataDeliveryEntry> {

  private final AtomicInteger binaryDataDeliveryCounter = new AtomicInteger();

  @Override
  protected int deliver(BinaryDataDeliveryEntry entry) {
    final int currentCount = binaryDataDeliveryCounter.incrementAndGet();
    out.printf("%s.currentCount=[%s]%n", getClass().getSimpleName(), currentCount);
    return currentCount;
  }

  public int getDeliveryCount() {
    return binaryDataDeliveryCounter.get();
  }

  public static class BinaryDataDeliveryEntry extends AbstractDeliveryEntry {
    public final DataSM dataSM;

    public BinaryDataDeliveryEntry(int statusCode, int errorCode, DataSM dataSM) {
      super(statusCode, errorCode);
      this.dataSM = dataSM;
    }
  }
}
