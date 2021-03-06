package com.lti.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.lti.model.Student;
import com.lti.utils.JpaUtils;

@Repository
public class StudentDaoImpl implements StudentDao {

	private EntityManager entityManager;

	public StudentDaoImpl() {
		entityManager = JpaUtils.getEntityManager();
	}

	@Override
	public int createStudent(Student student) {
		entityManager.getTransaction().begin();
		entityManager.persist(student);
		entityManager.getTransaction().commit();
		return 1;
	}

	@Override
	public Student readStudentByRollNumber(int rollNumber) {

		return entityManager.find(Student.class, rollNumber);
	}

	@Override
	public int updateStudent(Student student) {
		String q = "update Student s set s.studentName='" + student.getStudentName() + "', s.studentScore="
				+ student.getStudentScore() + " where s.rollNumber=" + student.getRollNumber();
		Query query = entityManager.createQuery(q);
		int result = query.executeUpdate();
		entityManager.clear();
		return result;
	}

	@Override
	public int DeleteStudentByRollNumber(int rollNumber) {
		String q = "Delete from Student s where s.rollNumber = :rollNumber";
		Query query = entityManager.createQuery(q);
		query.setParameter("rollNumber", rollNumber);
		int result = query.executeUpdate();
		entityManager.clear();
		return result;
	}

	@Override
	public List<Student> getAllStudents() {
		String jpql = "From Student";
		TypedQuery<Student> typed = entityManager.createQuery(jpql, Student.class);

		return typed.getResultList();
	}

	@Override
	public void beginTransaction() {
		entityManager.getTransaction().begin();

	}

	@Override
	public void commitTransaction() {
		entityManager.getTransaction().commit();

	}

	@Override
	public void rollBackTransaction() {
		entityManager.getTransaction().rollback();
	}
}
