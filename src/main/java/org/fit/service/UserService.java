package org.fit.service;


import java.util.HashSet;
import java.util.List;

import org.fit.enums.UserStatus;
import org.fit.exception.UserException;
import org.fit.model.IPLog;
import org.fit.model.PhoneNumber;
import org.fit.model.Users;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Dependent
public class UserService {

	@Inject
	private EntityManager em;

	
	//
	@Transactional
	public Users createUser(Users u, IPLog ipLog) throws UserException{
		List<Users> users = getAllUsers();
		
		if (users.contains(u)) {
			throw new UserException(UserStatus.EXISTS.getLabel());
		}

		u.setIpLog(ipLog);
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
		return em.createNamedQuery(PhoneNumber.GET_ALL_PHONES_FOR_USER, PhoneNumber.class).setParameter("id", u.getId())
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
	
	@Transactional
	public void deleteUserById(Long userId) throws UserException {
	    Users user = em.find(Users.class, userId);
	    if (user == null) {
	        throw new UserException("User with id " + userId + " not found.");
	    }
	    em.remove(user);
	}
	
	@Transactional
	public Users updateUserEmail(Long userId, String newEmail) throws UserException{
		Users user = em.find(Users.class, userId);
		if(user == null) {
			throw new UserException("user with id " + userId + " not found.");
		}
		user.setEmail(newEmail);
		return em.merge(user);
	}
	
	
//	@Transactional
//	public Users createUserWithPicture(MultipartBody multipartBody) throws UserException{
//		try {
//			Users user = multipartBody.getUser();
//			byte[] picture = multipartBody.getPicture();
//			
//			if (user == null || picture == null) {
//				throw new IllegalArgumentException("User or picture data is missing.");
//			}
//			
//			
//			user.setPicture(picture);
//			em.merge(user);
//			
//			return user;
//		} catch (Exception e) {
//			throw new UserException("Failed to create user with picture");
//		}
//	}
	

	
}
