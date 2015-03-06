package br.com.wind.producer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import br.com.wind.podam.strategy.RandomDataProviderStrategy;

@ApplicationScoped
public class DummyFactoryProducer {

	@Produces
	@RequestScoped
	public PodamFactory createDummyFactory() {
		return new PodamFactoryImpl(new RandomDataProviderStrategy());
	}
	
}
