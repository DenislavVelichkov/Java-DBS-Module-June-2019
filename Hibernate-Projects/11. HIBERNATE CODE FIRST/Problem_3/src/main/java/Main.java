import models.Course;
import models.Student;
import models.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("university_system");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Teacher teacher = new Teacher(
            "Ivan", "ionkov", "+359878 221 214 421", "aa@aa.com", 240d);
        Teacher teacher1 = new Teacher(
            "Gatso", "Batsov", "+359878 001 999 421", "bb@bb.bg", 300d);
        Teacher teacher2 = new Teacher(
            "Shefa", "Shefov", "+359898 221 111 222", "cc@cc.com", 140.50);
        Course course1 = new Course(
            "Java_Advanced", "Java Advanced and Java OOP", LocalDateTime.now(), LocalDateTime.of(2020, 07, 21, 23, 59), 24, teacher);
        Course course2 = new Course(
            "Java_DB", "Java Databases", LocalDateTime.now(), LocalDateTime.of(2020, 03, 21, 23, 59), 24, teacher1);
        Course course3 = new Course(
            "Java_WEB", "Java WEB FTW", LocalDateTime.now(), LocalDateTime.of(2020, 05, 21, 23, 59), 85, teacher2);
        Student student = new Student(
            "Zaprqn", "Moshev", "+359878 001 999 421", 5.50, "online");
        Student student2 = new Student(
            "Stamat", "Toshev", "+359878 001 779 421", 4.89, "offline");
        Student student3 = new Student(
            "Meto", "Zahariev", "+359878 001 559 421", 6.00, "online");
        course1.getStudents().add(student);
        course1.getStudents().add(student2);
        course1.getStudents().add(student3);
        course2.getStudents().add(student);
        course2.getStudents().add(student2);
        course2.getStudents().add(student3);
        student.getCourses().add(course1);
        student.getCourses().add(course2);
        student.getCourses().add(course3);

        entityManager.getTransaction().begin();
        entityManager.persist(teacher);
        entityManager.persist(teacher1);
        entityManager.persist(teacher2);
        entityManager.persist(student);
        entityManager.persist(student2);
        entityManager.persist(student3);
        entityManager.persist(course1);
        entityManager.persist(course2);
        entityManager.persist(course3);

        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
