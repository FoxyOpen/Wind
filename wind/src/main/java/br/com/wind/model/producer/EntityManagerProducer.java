package br.com.wind.model.producer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class EntityManagerProducer {
	
	@PersistenceContext(unitName = "persistenceUnit")
	private EntityManager entityManager;
	
	@Produces
	@RequestScoped
	public EntityManager createEntityManager() {
	    return entityManager;
	}
}
