package core;

import java.io.Serializable;
import java.util.Date;

/**
 * Source Person class
 */
public class Person implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private java.util.Date birthday; //Поле не может быть null
    private int weight; //Значение поля должно быть больше 0
    private String passportID; //Длина строки должна быть не меньше 7, Поле не может быть null
    private static final long serialVersionUID = 23L;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getPassportID() {
        return passportID;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }

    private Color hairColor; //Поле может быть null

    public Person(String n, java.util.Date b, int w, String p, Color c){
        name = n;
        birthday = b;
        weight = w;
        passportID = p;
        hairColor = c;
    }
}
