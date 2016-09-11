package io.egen.app.repository;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;





//import org.eclipse.persistence.internal.jpa.rs.metadata.model.Query;
import org.springframework.stereotype.Repository;

import io.egen.app.entity.Rating;

@Repository
public class RatingRepositoryImp implements RatingRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public double findByArguments(Map<String, String> params) {
		TypedQuery<Double> query;
		if(params.get("movieId")!=null)
		{
			query = em.createNamedQuery("Rating.findAvgByMovieId", Double.class);
			query.setParameter("pMovieId", params.get("movieId"));
		}	
		else	
			query = em.createNamedQuery("Rating.findAll", Double.class);
			query.setParameter("pUserId",params.get("userId"));
		
			List<Double> avg = query.getResultList();
			if (avg.size() == 1) {
				return avg.get(0);
			} else {
				return 0;
			}
		}
			
			//double avg = query.getSingleResult();
		   //return avg;
	

	@Override
	public List<Rating> findByUserIdMovieId(String userId, String movieId) {
		TypedQuery<Rating> query = em.createNamedQuery("Rating.findByUserIdMovieId", Rating.class);
		query.setParameter("pUserId", userId); 
		query.setParameter("pMovieId", movieId);
		return query.getResultList();
	}
	
	@Override
	public Rating findById(String id) {
		return em.find(Rating.class, id);
	}
	
	@Override
	public Rating create(Rating emp) {
		em.persist(emp);
		return emp;
	}

	@Override
	public Rating update(Rating emp) {
		return em.merge(emp);
	}

	@Override
	public void delete(Rating emp) {
		em.remove(emp);
	}
}
