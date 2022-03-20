package dev.hbrown;

import dev.hbrown.pdu.DataSM;
import dev.hbrown.pdu.SubmitSM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static dev.hbrown.BinaryDataDeliveryInfo.BinaryDataDeliveryEntry;
import static dev.hbrown.StringDeliveryInfo.StringDeliveryEntry;

public class DeliveryInfoTest {

  @Test
  void shouldInvokeEachDeliverMethodTheExpectedNumberOfTimes() {
    final StringDeliveryInfo stringDeliveryInfo = new StringDeliveryInfo();
    final BinaryDataDeliveryInfo binaryDataDeliveryInfo = new BinaryDataDeliveryInfo();

    System.out.println("Starting threads....");
    final List<Thread> threads = Arrays.asList(
            new Thread(stringDeliveryInfo, "t1"),
            new Thread(binaryDataDeliveryInfo, "t2")
    );
    threads.forEach(Thread::start);

    IntStream.range(0, 20).forEach(i -> {
      if (i % 2 == 0 || i % 3 == 0) {
        stringDeliveryInfo.submit(new StringDeliveryEntry(i, i, new SubmitSM()));
      } else {
        binaryDataDeliveryInfo.submit(new BinaryDataDeliveryEntry(i, i, new DataSM()));
      }
    });

    threads.forEach(this::join);
    threads.forEach(Thread::interrupt);

    Assertions.assertEquals(13, stringDeliveryInfo.getDeliveryCount());
    Assertions.assertEquals(7, binaryDataDeliveryInfo.getDeliveryCount());
  }

  private void join(Thread t) {
    try {
      t.join(2_000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

}
