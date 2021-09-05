package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactRemoveFromGroupTests extends TestBase {

    @Test
    public void testContactRemoveFromGroup() {
        if (!app.getContactHelper().isThereAContact()) {
            app.getNavigationHelper().gotoGroupPage();
            if (!app.getGroupHelper().isThereAGroup()) {
                app.getGroupHelper().createGroup(new GroupData("test1", "test2", "test3"));
            }
            app.getNavigationHelper().gotoHomePage();
            app.getContactHelper().createContact(
                    new ContactData(
                            "Ivan",
                            "Ivanov",
                            "My Company",
                            "My Address",
                            "My home telephone",
                            "my_email@gmail.com",
                            "[none]"));
        }
        app.getNavigationHelper().gotoGroupPage();
        List<GroupData> groups = app.getGroupHelper().getGroupList();
        app.getNavigationHelper().gotoHomePage();
        for (GroupData group : groups) {
            app.getNavigationHelper().gotoGroupContacts(group.getId());
            if (app.getContactHelper().isThereAContact()) {
                List<ContactData> contactsInGroupBefore = app.getContactHelper().getContactList();
                app.getContactHelper().selectContact(contactsInGroupBefore.size() - 1);
                app.getContactHelper().submitContactRemove();
                app.getNavigationHelper().gotoHomePage();
                app.getNavigationHelper().gotoGroupContacts(group.getId());
                List<ContactData> contactsInGroupAfter = app.getContactHelper().getContactList();

                Assert.assertEquals(contactsInGroupAfter.size(), contactsInGroupBefore.size() - 1);
                contactsInGroupBefore.remove(contactsInGroupBefore.size() - 1);
                Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
                contactsInGroupBefore.sort(byId);
                contactsInGroupAfter.sort(byId);
                Assert.assertEquals(contactsInGroupBefore, contactsInGroupAfter);
                break;
            }
        }
    }
}
