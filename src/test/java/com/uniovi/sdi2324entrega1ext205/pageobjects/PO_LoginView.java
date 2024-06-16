package com.uniovi.sdi2324entrega1ext205.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_LoginView extends PO_NavView {
    static public void fillLoginForm(WebDriver driver, String usernamep, String passwordp) {
        WebElement username = driver.findElement(By.name("username"));
        username.click();
        username.clear();
        username.sendKeys(usernamep);
        WebElement password = driver.findElement(By.name("password"));
        password.click();
        password.clear();
        password.sendKeys(passwordp);
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }
    static public void logout(WebDriver driver) {
        //Ahora nos desconectamos y comprobamos que aparece el menú de registro
        String loginText = PO_NavView.getP().getString("signup.message", PO_Properties.getSPANISH());
        PO_NavView.clickOption(driver, "logout", "text", loginText);
    }
    static public void login(WebDriver driver, String username, String password, String checkText) {
        //Vamos al formulario de login.
        PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
        fillLoginForm(driver, username, password);
        //Cmmprobamos que entramos en la pagina privada del Usuario
        PO_View.checkElementBy(driver, "text", checkText);
    }
}
