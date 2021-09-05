package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() {
        if (! app.getContactHelper().isThereAContact()
                && ! app.getGroupHelper().isThereAGroup()) {
            app.getNavigationHelper().gotoGroupPage();
            app.getGroupHelper().createGroup(new GroupData("test1", "test2", "test3"));
            app.getNavigationHelper().gotoHomePage();
            app.getContactHelper().createContact(
                    new ContactData(
                            "Ivan",
                            "Ivanov",
                            "My Company",
                            "My Address",
                            "My home telephone",
                            "my_email@gmail.com",
                            "test1"));
        }
        app.getNavigationHelper().gotoHomePage();
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().initContactModification(before.size() - 1);
        ContactData contact = new ContactData(before.get(before.size() - 1).getId(), "Ivan", "Ivanov", "My Company",
                "My Address", "My home telephone", "my_email@gmail.com", null);
        app.getContactHelper().fillContactForm(contact, false);
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().gotoHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());

        before.remove(before.size() - 1);
        before.add(contact);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }
}
