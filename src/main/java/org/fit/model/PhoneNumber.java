package org.fit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = PhoneNumber.GET_ALL_FOR_USER, query = "SELECT n FROM PhoneNumber n WHERE n.users.id = :id")
})
public class PhoneNumber {

    public static final String GET_ALL_FOR_USER = "getAllPhonesForUser";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phoneNumber_seq")
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Users users;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "PhoneNumber {id=" + id + ", users=" + users + "}";
	}
	
}
