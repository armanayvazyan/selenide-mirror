package com.codeborne.selenide.ex;

import com.codeborne.selenide.impl.CollectionSource;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

import static java.lang.System.lineSeparator;

@ParametersAreNonnullByDefault
public class TextsMismatch extends UIAssertionError {
  public TextsMismatch(CollectionSource collection,
                       List<String> expectedTexts, List<String> actualTexts,
                       @Nullable String explanation, long timeoutMs,
                       @Nullable Throwable cause) {
    super(
      collection.driver(),
      "Texts mismatch" +
        lineSeparator() + "Actual: " + actualTexts +
        lineSeparator() + "Expected: " + expectedTexts +
        (explanation == null ? "" : lineSeparator() + "Because: " + explanation) +
        lineSeparator() + "Collection: " + collection.description(),
      expectedTexts, actualTexts,
      cause,
      timeoutMs);
  }
}
