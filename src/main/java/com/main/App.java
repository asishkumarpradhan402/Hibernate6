package com.main;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import com.main.config.AppConfiguration;
import com.main.model.Course;
import com.main.model.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class App {
	public static void main(String[] args) {
//		getStudentWithCriteriaWithoutXML(10, "Asish", null);
//		getStudentWithCriteriaWithoutXML(10, null, null);
//		getStudentWithCriteriaWithoutXML(null, "Asish", null);
//		getStudentWithCriteriaWithoutXML(null, "MyName", "address");
		getStudentWithCriteriaWithoutXML(72, "MyName", "address");
	}

	public static void getStudentbyEntityManager() {
		EntityManager entityManager = AppConfiguration.getEntityManager();
		Student student = entityManager.find(Student.class, 1);
		System.out.println(student);
	}

	public static void getStudent() {
		Session session = AppConfiguration.getSession();
		Student student = session.get(Student.class, 1);
		System.out.println(student);
		session.close();
	}

	public static void getStudentWithCriteria() {
		Session session = AppConfiguration.getSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Student> createQuery = criteriaBuilder.createQuery(Student.class);
		Root<Student> student = createQuery.from(Student.class);
		createQuery.select(student).where(student.get("studentId").in(1));
		Query<Student> createQuery2 = session.createQuery(createQuery);
		List<Student> resultList = createQuery2.getResultList();
		System.out.println(resultList);
		session.close();
	}

	public static void getStudentWithCriteria2(Integer studentId, String studentName, String studentAddress) {
		Session session = AppConfiguration.getSession();
		HibernateCriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaQuery<Student> createQuery = criteriaBuilder.createQuery(Student.class);
		Root<Student> studentFrom = createQuery.from(Student.class);
		createQuery.select(studentFrom);
		List<Predicate> whereCluse = new ArrayList<Predicate>();
		if (studentId != null) {
			Predicate predicateStudentId = studentFrom.get("studentId").in(studentId);
			whereCluse.add(predicateStudentId);
		}
		if (studentName != null) {
			Predicate predicateStudentName = studentFrom.get("studentName").in(studentName);
			whereCluse.add(predicateStudentName);
		}
		if (studentAddress != null) {
			Predicate predicateStudentAddress = studentFrom.get("studentAddress").in(studentAddress);
			whereCluse.add(predicateStudentAddress);
		}
		if (!whereCluse.isEmpty()) {
			Predicate[] predicateArray = new Predicate[whereCluse.size()];
			for (int i = 0; i < predicateArray.length; i++) {
				predicateArray[i] = whereCluse.get(i);
			}
			createQuery.where(predicateArray);
		}
		Query<Student> query = session.createQuery(createQuery);
		List<Student> resultList = query.getResultList();
		System.out.println(resultList);
		session.close();
	}

	public static void getStudentWithCriteriaWithoutXML(Integer studentId, String studentName, String studentAddress) {
		Session session = AppConfiguration.getSessionWithoutXML();
		HibernateCriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaQuery<Student> createQuery = criteriaBuilder.createQuery(Student.class);
		Root<Student> studentFrom = createQuery.from(Student.class);
		createQuery.select(studentFrom);
		List<Predicate> whereCluse = new ArrayList<Predicate>();
		if (studentId != null) {
			Predicate predicateStudentId = studentFrom.get("studentId").in(studentId);
			whereCluse.add(predicateStudentId);
		}
		if (studentName != null) {
			Predicate predicateStudentName = studentFrom.get("studentName").in(studentName);
			whereCluse.add(predicateStudentName);
		}
		if (studentAddress != null) {
			Predicate predicateStudentAddress = studentFrom.get("studentAddress").in(studentAddress);
			whereCluse.add(predicateStudentAddress);
		}
		if (!whereCluse.isEmpty()) {
			Predicate[] predicateArray = new Predicate[whereCluse.size()];
			for (int i = 0; i < predicateArray.length; i++) {
				predicateArray[i] = whereCluse.get(i);
			}
			createQuery.where(predicateArray);
		}
		Query<Student> query = session.createQuery(createQuery);
		List<Student> resultList = query.getResultList();
		System.out.println(resultList);
		session.close();
	}

	public static void save() {
		Session session = AppConfiguration.getSession();
		Transaction transaction = session.beginTransaction();
		Student student = new Student();
		student.setStudentName("Asish");
		student.setStudentAddress("address");
		Course course = new Course();
		course.setCourseId(1);
		course.setCourseName("cnane");
		student.setCourse(course);
		session.persist(student);
		transaction.commit();
		session.close();
	}

	public static void saveEM() {
		EntityManager entityManager = AppConfiguration.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		if (!entityTransaction.isActive()) {
			entityTransaction.begin();
		}
		Student student = new Student();
		student.setStudentName("MyName");
		student.setStudentAddress("address");
		Course course = new Course();
		course.setCourseId(1);
		course.setCourseName("cnane");
		student.setCourse(course);
		entityManager.persist(student);
		entityTransaction.commit();
	}
}
