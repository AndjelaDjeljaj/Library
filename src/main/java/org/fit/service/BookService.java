package org.fit.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@Dependent
public class BookService {

	@Inject
	private EntityManager em;
	
	//public Book
}
