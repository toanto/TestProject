package gst.trainingcourse.manylanguage.model;

public class Account {

    private int id;
    private String username;
    private String password;
    private String email;
    private String address;
    private String telephone;
    private byte[] image;

    public Account() {
    }

    public Account(String username, String password, String email, String address, String telephone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.telephone = telephone;
    }

    public Account(int id, String username, String password, String email, String address, String telephone, byte[] image) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.telephone = telephone;
        this.image = image;
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
