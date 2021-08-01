package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    initContactCreation();
    fillContactForm(new ContactData("Ivan", "Ivanov", "My Company", "My Address", "My home telephone", "my_email@gmail.com"));
    submitContactCreation();
    returnToContactPage();
  }

}
