package ru.stqa.pft.addressbook.tests;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddressTests extends TestBase {

    @BeforeMethod()
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0
                || app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1").withHeader("test2").withFooter("test3"));
            app.goTo().homePage();
            app.contact().create(
                    new ContactData()
                            .withFirstname("Ivan").withLastname("Ivanov").withCompany("My Company")
                            .withAddress("My Address").withHomePhone("My home telephone")
                            .withEmail("my_email@gmail.com").withGroup("test1"));
        }
    }

    @Test
    public void testContactAddress() {
        app.goTo().homePage();
        ContactData contact = app.db().contacts().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        System.out.println(contact.getAddress());
        System.out.println(contactInfoFromEditForm.getAddress());

        assertThat(cleaned(contact.getAddress()), equalTo(cleaned(contactInfoFromEditForm.getAddress())));
    }

    public static String cleaned(String address) {
        return address.replaceAll("\\s", "");
    }
}

