package entity;

import column.Column;

public class Clazz {
    @Column(label = "班级号", isPrimeKey = true, max = 10, min = 1)
    private String id;

    @Column(label = "班级名称")
    private String name;

    @Column(label = "班级所在系")
    private String department;

    public Clazz() {
    }

    public Clazz(String id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
