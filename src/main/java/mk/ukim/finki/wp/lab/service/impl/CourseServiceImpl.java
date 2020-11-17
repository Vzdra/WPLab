package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.repository.CourseRepository;
import mk.ukim.finki.wp.lab.repository.StudentRepository;
import mk.ukim.finki.wp.lab.repository.TeacherRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public CourseServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<Student> listStudentsByCourse(Long courseId) {
        return courseRepository.findAllStudentsByCourse(courseId);
    }

    @Override
    public Course addStudentInCourse(String username, Long courseId) {

        if(!courseRepository.studentExists(courseId, username)){
            Student student = studentRepository.findByUsername(username);
            Course course = courseRepository.findById(courseId);

            return courseRepository.addStudentToCourse(student,course);
        }

        return null;
    }

    @Override
    public List<Course> listAll() {
        return courseRepository.findAllCourses();
    }

    @Override
    public Course getById(Long courseId) {
        return courseRepository.findById(courseId);
    }

    @Override
    public List<Student> filterStudentsInCourseByNameOrSurname(Long courseId, String text) {
        List<Student> filtered = new ArrayList<>();
        courseRepository.findAllStudentsByCourse(courseId).forEach(s -> {
            if (s.getName().contains(text) || s.getSurname().contains(text)) {
                filtered.add(s);
            }
        });

        return filtered;
    }

    @Override
    public boolean saveCourse(String name, String description, Long teacherId) {
        Teacher t = teacherRepository.findById(teacherId);
        List<Student> st = new ArrayList<>();
        Course c = new Course((long)123, name, description, st, t);
        courseRepository.save(c);
        return true;
    }

    @Override
    public void removeCourse(Long id) {
        courseRepository.removeCourse(id);
    }
}
