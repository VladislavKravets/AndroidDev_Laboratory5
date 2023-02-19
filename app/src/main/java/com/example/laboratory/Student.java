package com.example.laboratory;

import java.util.List;

public class Student {
    private final String name;
    private final String surname;
    private List<Integer> estimate;

    public Student(String name, String surname, List<Integer> estimate) {
        this.name = name;
        this.surname = surname;
        this.estimate = estimate;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public List<Integer> getEstimate() {
        return estimate;
    }

    public void setEstimate(List<Integer> estimate) {
        this.estimate = estimate;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", estimate=" + estimate;
    }
}
