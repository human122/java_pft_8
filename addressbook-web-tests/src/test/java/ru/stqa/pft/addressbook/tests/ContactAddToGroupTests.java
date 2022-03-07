package ru.stqa.pft.addressbook.tests;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Iterator;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddToGroupTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0
                || app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
            app.goTo().homePage();
            app.contact().create(
                    new ContactData()
                            .withFirstname("Ivan").withLastname("Ivanov").withCompany("My Company")
                            .withAddress("My Address").withHomePhone("My home telephone")
                            .withEmail("my_email@gmail.com").withGroup("[none]"));
        }
    }

    @Test
    public void testContactAddToGroup() throws Exception {
//        app.goTo().groupPage();
        app.goTo().homePage();
        Iterator<GroupData> groups = app.db().groups().iterator();
        Set<ContactData> contacts = app.db().contacts();
        String allGroups = "";
        while (groups.hasNext()) {
            GroupData group = groups.next();
            app.goTo().groupContacts(group.id());
            Contacts contactsBefore = app.db().contacts();
            if (contactsBefore.size() == 0) {
                app.goTo().homePage(allGroups);
                ContactData contact = contacts.iterator().next();
                app.contact().addToGroup(contact.id(), group.id());
                app.goTo().homePage();
                app.goTo().groupContacts(group.id());
                assertThat(app.contact().count(), equalTo(contactsBefore.size() + 1));
                Contacts contactsAfter = app.db().contacts();

                assertThat(contactsAfter, equalTo(contactsBefore.withAdded(contact)));
                break;
            } else if (contacts.size() > contactsBefore.size()) {
                for (ContactData contact : contacts) {
                    if (!contactsBefore.contains(contact)) {
                        app.goTo().homePage(allGroups);
                        app.contact().addToGroup(contact.id(), group.id());
                        app.goTo().homePage();
                        app.goTo().groupContacts(group.id());
                        assertThat(app.contact().count(), equalTo(contactsBefore.size() + 1));
                        Contacts contactsAfter = app.contact().all(false);

                        assertThat(contactsAfter, equalTo(contactsBefore.withAdded(contact)));
                        break;
                    }
                }
                System.out.println("Last break");
                break;
            } else if (contacts.size() == contactsBefore.size()) {
                continue;
            }
            System.out.println("All Contacts in all Groups!!!");
        }
    }
}
