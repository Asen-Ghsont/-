package entity;

import column.Column;

public class Student {
    @Column(label = "学号", isPrimeKey = true, max = 4, min = 1)
    private String id;

    @Column(label = "姓名", max = 5, min = 2)
    private String name;

    @Column(label = "性别", max = 2, min = 1)
    private String gender;

    @Column(label = "年龄", max = 120, min = 1)
    private Integer age;

    @Column(label = "所在系")
    private String department;

    public Student() {
    }

    public Student(String id, String name, String gender, Integer age, String department) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.department = department;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", department='" + department + '\'' +
                '}';
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
