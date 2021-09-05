package ru.stqa.pft.addressbook.model;

import java.util.HashSet;
import java.util.Set;

public class ContactData {
    private int id;
    private final String firstname;
    private final String lastname;
    private final String company;
    private final String address;
    private final String homePhone;
    private final String email;
    private String group;
    private Set<GroupData> groups = new HashSet<GroupData>();

    public ContactData(String firstname, String lastname, String company, String address, String homePhone, String email,
                       String group) {
        this.id = 0;
        this.firstname = firstname;
        this.lastname = lastname;
        this.company = company;
        this.address = address;
        this.homePhone = homePhone;
        this.email = email;
        this.group = group;
    }

    public ContactData(int id, String firstname, String lastname, String company, String address, String homePhone, String email,
                       String group) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.company = company;
        this.address = address;
        this.homePhone = homePhone;
        this.email = email;
        this.group = group;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() { return id; }

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

    public String getGroup() {
        return group;
    }

    public Set<GroupData> getGroups() {
        return groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactData that = (ContactData) o;

        if (id != that.id) return false;
        if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
        return lastname != null ? lastname.equals(that.lastname) : that.lastname == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }

}
