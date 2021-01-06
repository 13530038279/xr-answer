package com.xr.question3;

/**
 * 学生类
 * @author Administrator
 */
public class Student {

	 /** 学生姓名 */
    private String name;
    /** 学生性别 */
    private String gender;
    /** 学生年龄 */
    private int age;
    
    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                '}';
    }
}
