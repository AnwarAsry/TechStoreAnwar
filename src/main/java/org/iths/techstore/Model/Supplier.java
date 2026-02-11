package org.iths.techstore.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "companyname")
    private String companyName;
    @Column(nullable = false)
    private String country;
    @Column(nullable = false, name = "contactperson")
    private String contactPerson;
    @Column(nullable = false)
    private String email;

    // Constructors
    public Supplier() {
    }

    public Supplier(String companyName, String country, String contactPerson, String email) {
        this.companyName = companyName;
        this.country = country;
        this.contactPerson = contactPerson;
        this.email = email;
    }

    public Supplier(Long id, String companyName, String country, String contactPerson, String email) {
        this.id = id;
        this.companyName = companyName;
        this.country = country;
        this.contactPerson = contactPerson;
        this.email = email;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
