package org.fit.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;

@Entity
@NamedQueries({@NamedQuery(name = Student.GET_ALL_STUDENTS, query = "Select s From Student s")})
public class Student {

	public static final String GET_ALL_STUDENTS = "getAllStudents";
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq")
	private Long id;
	
	private String name;
	private String lastName;
	
	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
	private Set<Predmet> predmeti = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<Predmet> getPredmeti() {
		return predmeti;
	}

	public void setPredmeti(Set<Predmet> predmeti) {
		this.predmeti = predmeti;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, lastName, name, predmeti);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Objects.equals(id, other.id) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(name, other.name) && Objects.equals(predmeti, other.predmeti);
	}
	
	
	
}
