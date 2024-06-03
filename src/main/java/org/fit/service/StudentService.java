package org.fit.service;

import java.util.HashSet;
import java.util.List;

import org.fit.exception.StudentException;
import org.fit.model.Predmet;
import org.fit.model.Student;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Dependent
public class StudentService {

	@Inject
	private EntityManager em;
	
	@Transactional
	public Student createStudent(Student s) throws StudentException{
		List<Student> students = getAllStudents();
		
		if (students.contains(s)) {
			throw new StudentException("Student already exists");
			
		}
		
		return em.merge(s);
		
	}

	
	@Transactional
	public List<Student> getAllStudents() {
		List<Student> s = em.createNamedQuery(Student.GET_ALL_STUDENTS, Student.class).getResultList();
		
		for (Student student : s) {
			List<Predmet> predmeti = getAllSubjects(student);
			student.setPredmeti(new HashSet<>(predmeti));
		}
		return s;
	}
	
	@Transactional
	public List<Predmet> getAllSubjects(Student s){
		return em.createNamedQuery(Predmet.GET_ALL_SUBJECTS, Predmet.class).setParameter("id", s.getId()).getResultList();
	}
	
	@Transactional
	public void deleteStudentById(Long studentId) throws StudentException{
		Student student = em.find(Student.class, studentId);
		if (student == null) {
	        throw new StudentException("Student with id " + studentId + " not found.");
	    }
		
		em.remove(student);
	}

	@Transactional
	public Student updateStudentNameById(Long studentId, String newName) throws StudentException{
		
		Student student = em.find(Student.class, studentId);
		if (student == null) {
	        throw new StudentException("Student with id " + studentId + " not found.");
	    }
		
		student.setName(newName);
		return em.merge(student);
	}
	
}
