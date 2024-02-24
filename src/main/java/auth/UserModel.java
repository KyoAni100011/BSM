package main.java.auth;

public class UserModel {

    private byte[] id;
    private String name;
    private String email;
    private String dob;

    public UserModel(byte[] id, String name, String email, String dob) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dob = dob;
    }

    public byte[] getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDob() {
        return dob;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\''
                +
                ", dob=" + dob +
                '}';
    }
}
