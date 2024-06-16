package com.uniovi.sdi2324entrega1ext205.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PO_PrivateView extends PO_NavView {

    static public void clickElement(WebDriver driver, int position, String text)
    {
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", text);
        elements.get(position).click();

    }
    static public void logoutUser(WebDriver driver, int language)
    {
        String loginText = PO_HomeView.getP().getString("signup.message",language);
        PO_PrivateView.clickOption(driver, "logout", "text", loginText);

    }
    public static void pageOption(WebDriver driver,String xpath,int option ){
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", xpath);
        elements.get(option).click();
    }

    public static boolean pageOptionChecked(WebDriver driver,String xpath,int option ){
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", xpath);
        if(elements.size() ==0 || elements.size()-1<option || option < 0 ){
            return false;
        }
        else {
            elements.get(option).click();
            return true;
        }
    }

}

