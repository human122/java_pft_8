package ru.stqa.pft.addressbook.tests;


import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactAddToGroupTests extends TestBase {

    @Test
    public void testContactAddToGroup() throws Exception {
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
        List<ContactData> contacts = app.getContactHelper().getContactList();
        for (GroupData group : groups) {
            app.getNavigationHelper().gotoGroupContacts(group.getId());
            List<ContactData> contactsInGroupBefore = app.getContactHelper().getContactList();
            if (contactsInGroupBefore.size() == 0) {
                app.getNavigationHelper().gotoGroupContacts("");
                app.getContactHelper().addContactToGroup(contacts.size() - 1, Integer.toString(group.getId()));
                app.getNavigationHelper().gotoHomePage();
                app.getNavigationHelper().gotoGroupContacts(group.getId());
                List<ContactData> contactsInGroupAfter = app.getContactHelper().getContactList();

                Assert.assertEquals(contactsInGroupAfter.size(), contactsInGroupBefore.size() + 1);
                ContactData contact = contacts.get(contacts.size() - 1);
                contactsInGroupBefore.add(contact);
                Assert.assertEquals(contactsInGroupBefore, contactsInGroupAfter);
                break;
            } else if (contacts.size() > contactsInGroupBefore.size()) {
                for (ContactData contact : contacts) {
                    if (!contactsInGroupBefore.contains(contact)) {
                        app.getNavigationHelper().gotoGroupContacts("");
                        app.getContactHelper().addContactToGroup(contacts.indexOf(contact), Integer.toString(group.getId()));
                        app.getNavigationHelper().gotoHomePage();
                        app.getNavigationHelper().gotoGroupContacts(group.getId());
                        List<ContactData> contactsInGroupAfter = app.getContactHelper().getContactList();

                        Assert.assertEquals(contactsInGroupAfter.size(), contactsInGroupBefore.size() + 1);
                        contactsInGroupBefore.add(contact);
                        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
                        contactsInGroupBefore.sort(byId);
                        contactsInGroupAfter.sort(byId);
                        Assert.assertEquals(contactsInGroupBefore, contactsInGroupAfter);
                        break;
                    }
                }
                break;
//            } else if (contacts.size() == contactsInGroupBefore.size()
//                    && group.getId() == groups.get(groups.size() - 1).getId()) {
//                System.out.println("All Contacts in all Groups!!!");
//                continue;
            } else if (contacts.size() == contactsInGroupBefore.size()) {
                continue;
            }
        }
        System.out.println("All Contacts in all Groups!!!");
    }

}
