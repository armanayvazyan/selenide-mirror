package com.codeborne.selenide.commands;

import com.codeborne.selenide.Command;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.impl.WebElementSource;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.google.common.base.MoreObjects.firstNonNull;
import static java.util.Locale.ROOT;
import static org.openqa.selenium.Keys.BACK_SPACE;
import static org.openqa.selenium.Keys.COMMAND;
import static org.openqa.selenium.Keys.CONTROL;
import static org.openqa.selenium.Keys.TAB;
import static org.openqa.selenium.Keys.chord;

@ParametersAreNonnullByDefault
public class Clear implements Command<SelenideElement> {
  private static final Logger log = LoggerFactory.getLogger(Clear.class);

  @Nonnull
  @CheckReturnValue
  @Override
  public SelenideElement execute(SelenideElement proxy, WebElementSource locator, @Nullable Object[] args) {
    WebElement input = locator.findAndAssertElementIsEditable();
    execute(locator.driver(), input);
    return proxy;
  }

  public void execute(Driver driver, WebElement input) {
    String platform = getPlatform(driver).toLowerCase(ROOT);
    if ("?".equals(platform)) {
      input.sendKeys(chord(CONTROL, "a"), chord(COMMAND, "a"), BACK_SPACE, TAB);
    }
    else if (platform.contains("mac") || platform.contains("iphone")) {
      input.sendKeys(chord(COMMAND, "a"), BACK_SPACE, TAB);
    }
    else {
      input.sendKeys(chord(CONTROL, "a"), BACK_SPACE, TAB);
    }
  }

  private String getPlatform(Driver driver) {
    if (!(driver.getWebDriver() instanceof JavascriptExecutor)) {
      return "?";
    }
    try {
      return firstNonNull(driver.executeJavaScript("return navigator.platform"), "?");
    }
    catch (WebDriverException cannotExecuteJavascript) {
      log.debug("Cannot get navigator.platform", cannotExecuteJavascript);
      return "?";
    }
  }
}
