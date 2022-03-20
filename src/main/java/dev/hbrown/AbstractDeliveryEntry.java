package dev.hbrown;

import java.util.StringJoiner;

public abstract class AbstractDeliveryEntry {

  public int statusCode;
  public int errorCode;

  protected AbstractDeliveryEntry(int statusCode, int errorCode) {
    this.statusCode = statusCode;
    this.errorCode = errorCode;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", getClass().getSimpleName() + "[", "]")
            .add("statusCode=" + statusCode).add("errorCode=" + errorCode).toString();
  }
}
