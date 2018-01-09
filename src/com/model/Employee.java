package com.model;

import java.sql.Date;

public class Employee {
    private int empNum;
    private String lastName;
    private String name;
    private String job;
    private Date registerDate;
    private float salary;
    private float commission;
    private Department dept;

    public Employee(int empNum, String lastName, String name, String job, Date registerDate, float salary, float commission, Department dept) {
        this.empNum = empNum;
        this.lastName = lastName;
        this.name = name;
        this.job = job;
        this.registerDate = registerDate;
        this.salary = salary;
        this.commission = commission;
        this.dept = dept;
    }

    public int getEmpNum() {
        return empNum;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public float getSalary() {
        return salary;
    }

    public float getCommission() {
        return commission;
    }

    public Department getDept() {
        return dept;
    }

    public void setEmpNum(int empNum) {
        this.empNum = empNum;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public void setCommission(float commission) {
        this.commission = commission;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }
}
