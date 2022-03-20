package dev.hbrown;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import static java.lang.System.out;

public abstract class AbstractDeliveryInfo<T extends AbstractDeliveryEntry> implements Runnable {

  private final Queue<T> submitRequests = new ArrayBlockingQueue<>(10);
  private final Object lock = new Object();
  private final String simpleClassName = getClass().getSimpleName();

  @Override
  public void run() {
    out.printf("%s.run() size=[%s]%n", simpleClassName, submitRequests.size());
    while(!Thread.currentThread().isInterrupted()) {
      synchronized (lock) {
        if (submitRequests.isEmpty()) {
          try {
            lock.wait();
          } catch (InterruptedException ignored) {
            out.printf("%s interrupted waiting for data!%n", simpleClassName);
          }
        }
        while (!submitRequests.isEmpty()) {
          deliver(submitRequests.remove());
        }
        lock.notify();
      }
    }
  }

  public void submit(T entry) {
    synchronized (lock) {
      final boolean added = submitRequests.offer(entry);
      if (!added) {
        try {
          lock.wait();
        } catch (InterruptedException ignored) {
          out.printf("%s interrupted waiting for space!%n", simpleClassName);
        }
      } else {
        lock.notify();
      }
    }
  }

  protected abstract int deliver(T entry);
}
