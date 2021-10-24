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
        attach(By.name("photo"), contactData.getPhoto());

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
        wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
    }

    public void submitContactModification() {
        click(By.name("update"));
    }

    public void create(ContactData contact) {
        initContactCreation();
        fillContactForm(contact, true);
        submitContactCreation();
        contactCache = null;
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
        contactCache = null;
        returnToContactPage();
    }

    public void delete(int index) {
        selectContact(index);
        deleteSelectedContacts();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.id());
        deleteSelectedContacts();
        contactCache = null;
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

    private Contacts contactCache = null;

    public Contacts all(boolean clearCache) {
        if (contactCache != null && clearCache != false) {
            return new Contacts(contactCache);
        }
        contactCache = new Contacts();
        List<WebElement> rows = wd.findElements(By.name("entry"));
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
            String lastName = row.findElement(By.xpath("td[2]")).getText();
            String firstName = row.findElement(By.xpath("td[3]")).getText();
            ContactData contact = new ContactData().withId(id).withFirstname(firstName).withLastname(lastName);
            contactCache.add(contact);
        }
        return new Contacts(contactCache);
    }

    public Contacts all() {
        Contacts contacts = new Contacts();
        List<WebElement> rows = wd.findElements(By.name("entry"));
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
            String lastName = row.findElement(By.xpath("td[2]")).getText();
            String firstName = row.findElement(By.xpath("td[3]")).getText();
            String address = row.findElement(By.xpath("td[4]")).getText();
            String allEmails = row.findElement(By.xpath("td[5]")).getText();
            String allPhones = row.findElement(By.xpath("td[6]")).getText();
            ContactData contact = new ContactData().withId(id).withFirstname(firstName)
                    .withLastname(lastName).withAddress(address).withAllEmails(allEmails)
                    .withAllPhones(allPhones);
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

    public int count() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public ContactData infoFromEditForm(ContactData contact) {
        initContactModificationById(contact.id());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withId(contact.id()).withFirstname(firstname).withLastname(lastname)
                .withAddress(address).withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work)
                .withEmail(email).withEmail2(email2).withEmail3(email3);
    }
}
