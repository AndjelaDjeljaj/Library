package org.fit.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.fit.enums.UserStatus;
import org.fit.exception.UserException;
import org.fit.model.PhoneNumber;
import org.fit.model.Users;

import io.quarkus.security.User;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Dependent
public class UserService {

	@Inject
	private EntityManager em;

	@Transactional
	public Users createUser(Users u) throws UserException{
		List<Users> users = getAllUsers();
		
		if (users.contains(u)) {
			throw new UserException(UserStatus.EXISTS.getLabel());
		}
		return em.merge(u);
	}

	@Transactional
	public List<Users> getAllUsers(){
		List<Users> users = em.createNamedQuery(Users.GET_ALL_USERS, Users.class).getResultList();
		
		for (Users user : users) {
			List<PhoneNumber> phoneNumbers = getAllForUsers(user);
			user.setPhoneNumbers(new HashSet<>(phoneNumbers));
		}
		return users;
	}

	@Transactional
	public List<PhoneNumber> getAllForUsers(Users u) {
		return em.createNamedQuery(PhoneNumber.GET_ALL_FOR_USER, PhoneNumber.class).setParameter("id", u.getId())
				.getResultList();
	}
	
	@Transactional
	public List<Users> getUsersByName(String name){
		List<Users> users = em.createNamedQuery(Users.GET_USER_BY_NAME, Users.class).setParameter("name", name).getResultList();
		
		for (Users user : users) {
			List<PhoneNumber> phoneNumbers = getAllForUsers(user);
			user.setPhoneNumbers(new HashSet<>(phoneNumbers));
		}
		return users;
	}
	
}
