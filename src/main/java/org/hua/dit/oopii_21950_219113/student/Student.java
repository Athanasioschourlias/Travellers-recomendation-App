package org.hua.dit.oopii_21950_219113.student;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Locale;

@Entity
@Table
public class Student {

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )

    private Long id;
    private String name;
    private String email;
    private LocalDate dob;

    @Transient
    private Integer age;

    public Student(Long id,String name, String email, LocalDate dob){
        this.id=id;
        this.name=name;
        this.email=email;
        this.dob=dob;
    }

    public Student(String name, String email, LocalDate dob){
        this.name=name;
        this.email=email;
        this.dob=dob;
    }

    public Student() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public Integer getAge() {
        return Period.between(dob, LocalDate.now()).getYears();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                ", age=" + age +
                '}';
    }
}
