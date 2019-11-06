package com.example.sote;

/**
 * A class which is a representation of all the user details as from the database
 */
public class User extends UserId{

    public String user_id, bloodgroup, image, name, phone, weight;

    //an empty constructor for instanciating
    public User(){}

    public User(String bloodgroup, String image, String name, String phone, String user_id, String weight) {
        this.bloodgroup = bloodgroup;
        this.user_id = user_id;
        this.image = image;
        this.name = name;
        this.phone = phone;
        this.weight = weight;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
