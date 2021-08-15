package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() throws Exception {
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
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteSelectedContacts();
    app.getNavigationHelper().gotoHomePage();
  }

}
