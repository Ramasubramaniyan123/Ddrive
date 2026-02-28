package comparable;

public class Student implements Comparable<Student> {
    int roll;
    String name;

    public Student(int roll, String name) {
        this.roll = roll;
        this.name = name;
    }

    @Override
    public int compareTo(Student o) {
        return Integer.compare(this.roll, o.roll);
    }

    @Override
    public String toString() {
        return roll  +" "+ name ;
    }
}
