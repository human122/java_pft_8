package ru.stqa.pft.addressbook;

public class ContactData {
    private final String firstname;
    private final String lastname;
    private final String company;
    private final String address;
    private final String homePhone;
    private final String email;

    public ContactData(String firstname, String lastname, String company, String address, String homePhone, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.company = company;
        this.address = address;
        this.homePhone = homePhone;
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getCompany() {
        return company;
    }

    public String getAddress() {
        return address;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public String getEmail() {
        return email;
    }
}
