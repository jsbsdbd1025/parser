package bean;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private String name[];
    int age;
    float height;
    double money;
        List<bean.Number> nums = new ArrayList<>();
//    int[] nums;

    public Person() {
    }

    public Person(String name[], int age, float height, double money) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.money = money;
    }

    public String[] getName() {
        return name;
    }

    public void setName(String[] name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public List<bean.Number> getNums() {
        return nums;
    }

    public void setNums(List<bean.Number> nums) {
        this.nums = nums;
    }

//
//    public int[] getNums() {
//        return nums;
//    }
//
//    public void setNums(int[] nums) {
//        this.nums = nums;
//    }
}