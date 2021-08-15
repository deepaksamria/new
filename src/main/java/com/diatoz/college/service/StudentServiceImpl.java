package com.diatoz.college.service;

import java.util.List;

import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diatoz.college.dao.StudentRepository;
import com.diatoz.college.dao.TeacherRepository;
import com.diatoz.college.dao.UserRepository;
import com.diatoz.college.model.Student;
import com.diatoz.college.model.Subjects;
import com.diatoz.college.model.Teacher;
import com.diatoz.college.model.Users;

@Service
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Student createStudent(Student student) {
		student.setRole("STUDENT");
		Student savedStudent = studentRepository.save(student);
		Users user = new Users();
		user.setPassword(savedStudent.getPassword());
		user.setUserName(savedStudent.getUsername());
		user.setRole(savedStudent.getRole());
		userRepository.save(user);
		return savedStudent;
	}

	@Override
	public List<Student> getAllStudent() {
		return studentRepository.findAll();
	}

	@Override
	public Student getStudentById(Long id) {
		return studentRepository.findByStudentId(id);
	}

	@Override
	public Student updateStudent(Student student, Long id) {
		Student studentFromDataBase = studentRepository.findByStudentId(id);
		if(studentFromDataBase == null) {
			return null;
		}
		student.setStudentID(id);
		student.setRole("STUDENT");
		student.setUsername(studentFromDataBase.getUsername());
		
		Users userFromDatabase = userRepository.findByUserName(studentFromDataBase.getUsername());
		userFromDatabase.setPassword(student.getPassword());
		userRepository.save(userFromDatabase);
		return studentRepository.save(student);	
	}
	
	@Override
	public void deleteStudent(Long id) {
		studentRepository.deleteById(id);
	}

	@Override
	public List<Subjects> updateStudentMarks(Student student, Long id) {
		Student studentFromDataBase = studentRepository.findByStudentId(id);
		if(studentFromDataBase == null) {
			return null;
		}
		studentFromDataBase.setSubjects(student.getSubjects());
		Student updatedStudent = studentRepository.save(studentFromDataBase);
		return updatedStudent.getSubjects();
		}

	@Override
	public List<Subjects> getStudentByusername(String username) {
		Student loggedInStudent = studentRepository.findByUsername(username);
		return loggedInStudent.getSubjects();
		
	}
}
