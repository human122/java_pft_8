package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(WebDriver wd) {
        super(wd);
    }

    public void gotoGroupPage() {
        if (isElementPresent(By.tagName("h1"))
                && wd.findElement(By.tagName("h1")).getText().equals("Group")
                && isElementPresent(By.name("new"))) {
            return;
        }
        click(By.linkText("groups"));
    }

    public void gotoHomePage() {
        if (isElementPresent(By.id("maintable"))){
            return;
        }
        click(By.linkText("home"));
    }

    public void gotoGroupContacts(int groupId) {
        new Select(wd.findElement(By.name("group"))).selectByValue(Integer.toString(groupId));
    }

    public void gotoGroupContacts(String groupId) {
        new Select(wd.findElement(By.name("group"))).selectByValue(groupId);
    }



}
