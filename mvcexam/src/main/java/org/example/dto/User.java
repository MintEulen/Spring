package org.example.dto;

public class User {
    private String name;
    private String eamil;
    private int age;

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setEamil(String eamil) {
        this.eamil = eamil;
    }

    public String getEamil() {
        return eamil;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", eamil='" + eamil + '\'' +
                ", age=" + age +
                '}';
    }
}
