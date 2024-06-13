package com.uniovi.sdi2324entrega1ext205;

import com.uniovi.sdi2324entrega122.entities.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class PO_AdminUserList {
    private static final int emailPos = 0;
    private static final int namePos = 1;
    private static final int lastnamePos = 2;
    private static final int rolPos = 3;
    private static final int modPos = 5;

    private static final int delPos = 6;

/////html/body/div/div/form/table/tbody/tr*/td[3]
    static public User getUserPos(WebDriver driver, int i, String tableBody){ ///html/body/div/div/form/table/tbody
        List<WebElement> elements = PO_View.checkElementBy(driver,"free"
                ,tableBody+"/tr["+i+"]/td");
        if(elements.size()>0){
            return new User(elements.get(emailPos).getText()
                    ,elements.get(namePos).getText()
                    ,elements.get(lastnamePos).getText()
                    ,elements.get(rolPos).getText()
            );
        }
        else
            return null;
    }
    static public void clickModUserPos(WebDriver driver, int i, String tableBody){
        List<WebElement> elements = PO_View.checkElementBy(driver,"free"
                ,tableBody+"/tr["+i+"]/td");
        elements.get(modPos).click();
        //elements = PO_View.checkElementBy(driver, "name", "email");
    }
    static public void deleteUserListPos(WebDriver driver, List<Integer> listId, String tableBody){
        listId.forEach(e->{selectDeleteUser(driver,e,tableBody);});
        List<WebElement> elements = PO_NavView.checkElementBy(driver, "id", "deleteButton");
        elements.get(0).click();
        //elements = PO_View.checkElementBy(driver, "name", "email");
    }
    static private void selectDeleteUser(WebDriver driver, int i, String tableBody){
        List<WebElement> elements = PO_View.checkElementBy(driver,"free"
                ,tableBody+"/tr["+i+"]/td");
        elements.get(delPos).findElement(By.tagName("input")).click();
    }
    static public void modUserForm(WebDriver driver,String nEmail, String nName,String nLastMame,String nRol){
        WebElement email = driver.findElement(By.id("email"));
        email.click();
        email.clear();
        email.sendKeys(nEmail);
        WebElement name = driver.findElement(By.id("name"));
        name.click();
        name.clear();
        name.sendKeys(nName);
        WebElement lastname = driver.findElement(By.id("lastName"));
        lastname.click();
        lastname.clear();
        lastname.sendKeys(nLastMame);
        WebElement rol = driver.findElement(By.id("role"));
        rol.click();
        Select select = new Select(rol);
        select.selectByValue(nRol);

        By boton = By.className("btn");
        driver.findElement(boton).click();
    }
    static public void filter(WebDriver driver,String option,String filter){
        ////*[@id="logType"]
        WebElement logType = driver.findElement(By.id("logType"));
        logType.click();
        Select select = new Select(logType);
        select.selectByValue(option);
    }
    ///html/body/div/div/form/table/tbody
    static public List<WebElement> selectXtypeOfLog(WebDriver driver,String tableBody,String type){

        List<WebElement> elements = PO_View.checkElementBy(driver,"free"
                ,tableBody+"/tr");
        elements = elements.stream().filter(e->e.findElements(By.tagName("td")).get(1).getText().equals(type))
                .collect(Collectors.toList());
        return elements;
    }
}
