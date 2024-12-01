package com.main.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Embeddable
public class Course {

	@Column(name = "course_id")
	Integer courseId;

	@Column(name = "course_name")
	String courseName;
}
