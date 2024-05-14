package org.fit.service;

import java.util.List;

import org.fit.enums.GenreStatus;
import org.fit.exception.GenreException;
import org.fit.model.Genre;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Dependent
public class GenreService {

	@Inject
	private EntityManager em;
	
	@Transactional
	public Genre createGenre(Genre g) throws GenreException{
		List<Genre> genres = getAllGenres();
		
		if (genres.contains(g)) {
			throw new GenreException(GenreStatus.EXISTS.getLabel());
		}
		return em.merge(g);
	}
	
	@Transactional
	public List<Genre> getAllGenres(){
		List<Genre> genres = em.createNamedQuery(Genre.GET_ALL_GENRES, Genre.class).getResultList();
		return genres;
	}
	
	@Transactional
	public void deleteGenreById(Long genreId) throws GenreException{
		Genre genre = em.find(Genre.class, genreId);
		if(genre == null) {
			throw new GenreException("Genre with id " + genreId + " not found.");
		}
		em.remove(genre);
	}
}
