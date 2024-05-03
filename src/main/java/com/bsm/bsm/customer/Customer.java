package com.bsm.bsm.customer;

public class Customer {
    private String id;
    private String name;
    private String phone;

    private boolean isMember;

    public Customer() {
        // Default constructor
    }

    public Customer(String id, String name, String phone, boolean isMember) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.isMember =isMember;
    }

    public Customer(String name, String phone, boolean isMember) {
        this(null, name, phone, isMember);
    }

    public  Customer(String id, String name)
    {
        this(id, name, null, false);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isMember() {
        return isMember;
    }

    public void setMember(boolean member) {
        isMember = member;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", isMember=" + isMember +
                '}';
    }
}
