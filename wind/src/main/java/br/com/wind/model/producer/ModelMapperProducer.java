package br.com.wind.model.producer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;

import org.modelmapper.ModelMapper;

@ApplicationScoped
public class ModelMapperProducer {
	
	@Produces
	@RequestScoped
	public ModelMapper createModelMapper() {
		return new ModelMapper();
	}
}
