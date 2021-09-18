package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Iterator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactRemoveFromGroupTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.contact().list().size() == 0) {
            app.goTo().groupPage();
            if (app.group().list().size() == 0) {
                app.group().create(new GroupData().withName("test1"));
            }
            app.goTo().homePage();
            app.contact().create(
                    new ContactData().withFirstname("Ivan").withFirstname("Ivanov").withCompany("My Company")
                            .withAddress("My Address").withHomePhone("My home telephone")
                            .withEmail("my_email@gmail.com").withGroup("[none]"));
        }
    }

    @Test
    public void testContactRemoveFromGroup() throws InterruptedException {
        app.goTo().groupPage();
        Iterator<GroupData> groups = app.group().all().iterator();
        app.goTo().homePage();
        while (groups.hasNext()) {
            GroupData group = groups.next();
            app.goTo().groupContacts(group.id());
            if (app.contact().all().size() > 0) {
                Contacts contactsBefore = app.contact().all();
                ContactData contact = contactsBefore.iterator().next();
                app.contact().selectContactById(contact.id());
                app.contact().submitContactRemove();
                app.goTo().homePage();
                app.goTo().groupContacts(group.id());
                Contacts contactsAfter = app.contact().all();

                assertThat(contactsAfter.size(), equalTo(contactsBefore.size() - 1));
                assertThat(contactsAfter, equalTo(contactsBefore.without(contact)));
                break;
            }
        }
    }
}
