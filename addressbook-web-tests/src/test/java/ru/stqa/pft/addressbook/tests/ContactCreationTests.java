package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.getContactHelper().createContact(new ContactData("Ivan", "Ivanov", "My Company", "My Address",
            "My home telephone", "my_email@gmail.com", "test1"));
    app.getNavigationHelper().gotoHomePage();
  }

}
