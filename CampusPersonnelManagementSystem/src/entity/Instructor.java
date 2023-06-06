package entity;

import column.Column;

public class Instructor {
    @Column(label = "编号", isPrimeKey = true, max = 10, min = 1)
    private String id;

    @Column(label = "姓名", max = 5, min = 2)
    private String name;

    @Column(label = "性别", max = 5, min = 1)
    private String gender;

    @Column(label = "年龄", max = 75, min = 18)
    private Integer age;

    public Instructor() {
    }

    public Instructor(String id, String name, String gender, Integer age) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
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
}
