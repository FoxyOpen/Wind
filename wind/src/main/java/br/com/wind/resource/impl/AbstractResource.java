package br.com.wind.resource.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.modelmapper.ModelMapper;

import br.com.mk.qbeasy.core.Example;
import br.com.wind.model.dto.Dto;
import br.com.wind.model.entity.EntidadeBase;
import br.com.wind.model.example.TypedExampleGenerator;
import br.com.wind.model.exception.ResponseWrappedException;
import br.com.wind.model.repository.Repository;
import br.com.wind.resource.Resource;
import br.com.wind.util.ResponseFactory;

/**
 * @author Marcos Kichel
 *
 * Camada de abstração para beans do tipo Resource, fornece implementação padrão para os métodos
 * mais convenientes do estilo CRUD. 
 * 
 * As anotações do tipo <code>java.lang.ElementType.FIELD</code> da especificação JAX-RS 
 * do JEE não são anotadas com <code>@Inherited</code>, ficando assim somente disponíveis em
 * runtime para a classe que as define. Sendo assim os métodos desta classe são anotados 
 * apenas para servir como referência e sua replicação é obrigatório nas subclasses.
 * 
 * Esta classe não será considerada para injeção.
 *
 * @param <T> Tipo parametrizado do qual a classe é responsável.
 */

@Dependent
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public abstract class AbstractResource<T extends EntidadeBase, E extends Dto> implements Resource<T, E> {

	private static final long serialVersionUID = 3723765582492142265L;

	@Inject
	protected Instance<Repository<T>> repositoryInstance;
	
	@Inject
	protected Instance<TypedExampleGenerator<T>> exampleGeneratorInstance; 
	
	@Inject
	protected ModelMapper mapper;
	private Class<T> clazz;
	
	@POST
	@Override
	public Response inserir(@NotNull T entidade) {
		return salvar(entidade);
	}
	
	@PUT
	@Override
	public Response atualizar(@NotNull T entidade) {
		return salvar(entidade);
	}
	
	protected Response salvar(@NotNull T entidade) {
		getRepository().salvar(entidade);
		return ResponseFactory.getOkResponse(entidade);
	}

	@DELETE
	@Path("{id}")
	@Override
	public Response remover(@PathParam("id") @NotNull Long id) {
		Optional<T> opt = getRepository().remover(id);
		
		if (opt.isPresent()) {
			return ResponseFactory.getOkResponse(opt.get());
		} else {
			return ResponseFactory.getNoContentResponse();
		}
	}

	@GET
	@Path("contar")
	@Override
	public Response contar() {
		Long contagem = getRepository().contar();
		return ResponseFactory.getOkResponse(contagem);
	}
	
	@GET
	@Path("consultar")
	@Override
	@SuppressWarnings("unchecked")
	public Response consultar(
			@QueryParam("offset") @DefaultValue(value = "0") Integer offSet, 
			@QueryParam("limit") @DefaultValue(value = "10") Integer limit, 
			@BeanParam E filtro) {
		
		T filtroMaterializado;
		if (filtro == null) {
			filtroMaterializado = newInstanceClassType();
		} else if (filtro.getClass() == getClassType()) {
			filtroMaterializado = (T) filtro;
		} else {
			filtroMaterializado = mapper.map(filtro, getClassType());
		}
		
		Example example = getExampleGenerator().generate(filtroMaterializado);
		List<T> lista = getRepository().listar(offSet, limit, example);
		
		return ResponseFactory.getOkResponse(lista);
	}
	
	protected T newInstanceClassType() {
		try {
			return getClassType().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@GET
	@Path("{id}")
	@Override
	public Response selecionar(@PathParam("id") @NotNull Long id) {
		Optional<T> resultado = getRepository().selecionar(id);
		if (resultado.isPresent()) {
			return ResponseFactory.getOkResponse(resultado.get());
		}
		
		throw new ResponseWrappedException(ResponseFactory.getNoContentResponse());
	}
	
	@SuppressWarnings("unchecked")
    protected Class<T> getClassType() {
        if (clazz == null) {
        	ParameterizedType parameterizedType = (ParameterizedType) getClass().getSuperclass().getGenericSuperclass();
            clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
        }
        return clazz;
    }
	
	protected Repository<T> getRepository() {
		return repositoryInstance.get();
	}
	
	protected TypedExampleGenerator<T> getExampleGenerator() {
		return exampleGeneratorInstance.get();
	}
}
