import java.util.ArrayList;
import java.util.List;

class Person<T> {
    private String name;
    int age;
    float height;
    double money;
    List<T> nums = new ArrayList<>();

    public Person() {
    }

    public Person(String name, int age, float height, double money) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.money = money;
    }

    public void setNums(List<T> nums) {
        this.nums = nums;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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

    public List<T> getNums() {
        return nums;
    }
}