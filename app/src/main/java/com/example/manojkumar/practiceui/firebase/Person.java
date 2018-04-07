package com.example.manojkumar.practiceui.firebase;

/**
 * Created by mbreath on 16/12/17.
 */

public class Person {
    private String Name;
    private Integer Age;

    public Person() {
    }

    public Person(String name, Integer age) {
        Name = name;
        Age = age;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getAge() {
        return Age;
    }

    public void setAge(Integer age) {
        Age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "Name='" + Name + '\'' +
                ", Age=" + Age +
                '}';
    }
}
