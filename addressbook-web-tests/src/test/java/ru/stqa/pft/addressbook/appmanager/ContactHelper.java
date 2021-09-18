package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void submitContactCreation() {
        click(By.xpath("(//input[@name='submit'])[2]"));
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("lastname"), contactData.getLastname());
        type(By.name("company"), contactData.getCompany());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("email"), contactData.getEmail());

        if (creation) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void deleteSelectedContacts() {
        click(By.xpath("//input[@value='Delete']"));
        wd.switchTo().alert().accept();
    }

    public void selectContact(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    public void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[id ='" + id + "']")).click();
    }

    public void initContactModification(int index) {
        wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
    }

    public void initContactModificationById(int id) {
        wd.findElement(By.cssSelector("a[href='edit.php?id=" + id + "']")).click();
    }

    public void submitContactModification() {
        click(By.name("update"));
    }

    public void create(ContactData contact) {
        initContactCreation();
        fillContactForm(contact, true);
        submitContactCreation();
    }

    public void modify(int index, ContactData contact) {
        initContactModification(index);
        fillContactForm(contact, false);
        submitContactModification();
        returnToContactPage();
    }

    public void modify(ContactData contact) {
        initContactModificationById(contact.id());
        fillContactForm(contact, false);
        submitContactModification();
        returnToContactPage();
    }

    public void delete(int index) {
        selectContact(index);
        deleteSelectedContacts();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.id());
        deleteSelectedContacts();
    }

    public List<ContactData> list() {
        List<ContactData> contacts = new ArrayList<ContactData>();
        List<WebElement> rows = wd.findElements(By.name("entry"));
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
            String lastName = row.findElement(By.xpath("td[2]")).getText();
            String firstName = row.findElement(By.xpath("td[3]")).getText();
            ContactData contact = new ContactData().withId(id).withFirstname(firstName).withLastname(lastName);
            contacts.add(contact);
        }
        return contacts;
    }

    public Contacts all() {
        Contacts contacts = new Contacts();
        List<WebElement> rows = wd.findElements(By.name("entry"));
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
            String lastName = row.findElement(By.xpath("td[2]")).getText();
            String firstName = row.findElement(By.xpath("td[3]")).getText();
            ContactData contact = new ContactData().withId(id).withFirstname(firstName).withLastname(lastName);
            contacts.add(contact);
        }
        return contacts;
    }

    public void submitContactAddToGroup() {
        click(By.name("add"));
    }

    public void returnToContactPage() {
        click(By.linkText("home page"));
    }

    public void selectGroupToAdd(String groupId) {
        new Select(wd.findElement(By.name("to_group"))).selectByValue(groupId);
    }

    public void selectGroupToAdd(int groupId) {
        new Select(wd.findElement(By.name("to_group"))).selectByValue(Integer.toString(groupId));
    }

    public void submitContactRemove() {
        click(By.name("remove"));
    }

    public void addToGroup(int contactId, int groupId) {
        selectContactById(contactId);
        selectGroupToAdd(groupId);
        submitContactAddToGroup();
    }

    public int getContactCount() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }
}
