package com.uniovi.sdi2324entrega1ext205;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PO_View {
    protected static PO_Properties p = new PO_Properties("messages");
    protected static int timeout = 2;

    public static int getTimeout() {
        return timeout;
    }
    public static void setTimeout(int timeout) {
        PO_View.timeout = timeout;
    }
    public static PO_Properties getP() {
        return p;
    }
    public static void setP(PO_Properties p) {
        PO_View.p = p;
    }
    static public List<WebElement> checkElementByKey(WebDriver driver, String key, int locale) {
        return SeleniumUtils.waitLoadElementsBy(driver, "text", p.getString(key, locale), getTimeout());
    }
    static public List<WebElement> checkElementBy(WebDriver driver, String type, String text) {
        return  SeleniumUtils.waitLoadElementsBy(driver, type, text, getTimeout());
    }
    static public List<WebElement> checkElementByAndClick(WebDriver driver, String type, String text) {
        List<WebElement> elements = SeleniumUtils.waitLoadElementsBy(driver, type, text, getTimeout());
        Assertions.assertEquals(1, elements.size());
        elements.get(0).click();
        return  elements;
    }
}
