package com.main.config;

import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.main.model.Course;
import com.main.model.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public final class AppConfiguration {

	private static SessionFactory sessionFactory;

	private static Session session;

	private static EntityManager entityManager;

	private AppConfiguration() {
		throw new IllegalArgumentException("Not Required to create the Object for " + this.getClass().getName());
	}

	public static SessionFactory getSessionFactory() {
		if (sessionFactory != null && sessionFactory.isOpen()) {
			return sessionFactory;
		}
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		setAnnotatedClass(configuration);
		sessionFactory = configuration.buildSessionFactory();
		return sessionFactory;
	}

	public static Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put("dialect", "org.hibernate.dialect.PostgreSQLDialect");
		properties.put("jakarta.persistence.jdbc.driver", "org.postgresql.Driver");
		properties.put("jakarta.persistence.jdbc.url", "jdbc:postgresql://localhost:5432/test");
		properties.put("jakarta.persistence.jdbc.user", "postgres");
		properties.put("jakarta.persistence.jdbc.password", "root");
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.show_sql", true);
		properties.put("hibernate.format_sql", true);
		return properties;
	}

	public static SessionFactory getSessionFactoryWithoutXML() {
		if (sessionFactory != null && sessionFactory.isOpen()) {
			return sessionFactory;
		}
		Configuration configuration = new Configuration();
		configuration.addProperties(getHibernateProperties());
		setAnnotatedClass(configuration);
		sessionFactory = configuration.buildSessionFactory();
		return sessionFactory;
	}

	private static void setAnnotatedClass(Configuration configuration) {
		configuration.addAnnotatedClass(Student.class);
		configuration.addAnnotatedClass(Course.class);
	}

	public static Session getSession() {
		if (session != null && session.isOpen()) {
			return session;
		}
		;
		if (sessionFactory == null || !sessionFactory.isOpen()) {
			sessionFactory = getSessionFactory();
		}
		session = sessionFactory.openSession();
		return session;
	}

	public static Session getSessionWithoutXML() {
		if (session != null && session.isOpen()) {
			return session;
		}
		;
		if (sessionFactory == null || !sessionFactory.isOpen()) {
			sessionFactory = getSessionFactoryWithoutXML();
		}
		session = sessionFactory.openSession();
		return session;
	}

	public static EntityManager getEntityManager() {
		if (entityManager != null && entityManager.isOpen()) {
			return entityManager;
		}
		Session session = getSession();
		EntityManagerFactory entityManagerFactory = session.getEntityManagerFactory();
		entityManager = entityManagerFactory.createEntityManager();
		return entityManager;
	}

	public static EntityManager getEntityManagerWithoutXML() {
		if (entityManager != null && entityManager.isOpen()) {
			return entityManager;
		}
		Session session = getSessionWithoutXML();
		EntityManagerFactory entityManagerFactory = session.getEntityManagerFactory();
		entityManager = entityManagerFactory.createEntityManager();
		return entityManager;
	}

	public static boolean closeSession() {
		try {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}
			}
			if (sessionFactory != null) {
				if (sessionFactory.isOpen()) {
					sessionFactory.close();
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean closeEntityManager() {
		try {
			if (entityManager != null) {
				if (entityManager.isOpen()) {
					entityManager.close();
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
