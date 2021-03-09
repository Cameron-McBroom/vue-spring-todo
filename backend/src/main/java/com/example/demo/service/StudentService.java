package com.example.demo.service;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) throws EntityExistsException {
        Optional<Student> optionalStudent = studentRepository.findStudentByEmail(student.getEmail());

        if (optionalStudent.isPresent()) {
            throw new EntityExistsException("email already exists: " + student.getEmail() );
        }

        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) throws EntityNotFoundException {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);

        if (optionalStudent.isEmpty()) {
            throw new EntityNotFoundException("Student id not found: " + studentId);
        }

        studentRepository.delete(optionalStudent.get());
    }

    @Transactional
    public void updateStudent(Long id, String name, String email) {
        // Check to see if the student exists
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student id not found: " + id));

        // Check the name is not null or the same
        if (name != null && name.length() > 0 && !(student.getName().equals(name))) {
            student.setName(name);
        }

        if (email != null && email.length() > 0 && !(student.getEmail().equals(email))) {
            // Make sure email is not taken
            studentRepository.findStudentByEmail(email)
                    .ifPresent(s -> {
                        throw new EntityExistsException("Student with the email " + email + " already exists");
                    });
            student.setEmail(email);
        }
    }
}
