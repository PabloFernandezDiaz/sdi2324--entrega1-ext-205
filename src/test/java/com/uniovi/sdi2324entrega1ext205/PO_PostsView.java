package com.uniovi.sdi2324entrega1ext205;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_PostsView extends PO_NavView {
    static public void fillFormAddPost(WebDriver driver, String titlep, String descriptionp) {
        SeleniumUtils.waitSeconds(driver, 5);

        //Rellenemos el campo de title
        WebElement title = driver.findElement(By.name("title"));
        title.clear();
        title.sendKeys(titlep);
        //Rellenemos el campo de descripción
        WebElement description = driver.findElement(By.name("description"));
        description.clear();
        description.sendKeys(descriptionp);

        By boton = By.className("btn");
        driver.findElement(boton).click();
    }
}
