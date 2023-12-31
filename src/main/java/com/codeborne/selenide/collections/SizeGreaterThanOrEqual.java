package com.codeborne.selenide.collections;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ex.ListSizeMismatch;
import com.codeborne.selenide.impl.CollectionSource;
import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class SizeGreaterThanOrEqual extends CollectionCondition {
  protected final int expectedSize;

  public SizeGreaterThanOrEqual(int expectedSize) {
    this.expectedSize = expectedSize;
  }

  @Override
  public boolean test(List<WebElement> elements) {
    return apply(elements.size());
  }

  @Override
  public void fail(CollectionSource collection,
                   @Nullable List<WebElement> elements,
                   @Nullable Exception cause,
                   long timeoutMs) {
    throw new ListSizeMismatch(">=", expectedSize, explanation, collection, elements, cause, timeoutMs);
  }

  @Override
  public boolean missingElementSatisfiesCondition() {
    return apply(0);
  }

  @Override
  public String toString() {
    return String.format("size >= %s", expectedSize);
  }

  private boolean apply(int size) {
    return size >= expectedSize;
  }
}
