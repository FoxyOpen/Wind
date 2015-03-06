package br.com.wind.model.repository.impl;

import java.lang.reflect.ParameterizedType;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import br.com.mk.qbeasy.core.Example;
import br.com.mk.qbeasy.core.QBEasy;
import br.com.wind.model.entity.EntidadeBase;
import br.com.wind.model.repository.Repository;

@Dependent
public abstract class AbstractRepository<T extends EntidadeBase> implements Repository<T> {

	private static final long serialVersionUID = -5462875295451802622L;
	protected Class<T> clazz;
	
	@Inject
    protected EntityManager entityManager;
	
	//**TRANSACTIONS**//
	
	@Override
	@Transactional
	public T salvar(@NotNull T entidade) {
		if (isNew(entidade)) {
			return inserir(entidade);
		}
		return atualizar(entidade);
	}
	
	@Transactional
	protected T inserir(@NotNull T entidade) {
		entidade.setDataCadastro(LocalDate.now());
		entidade.setDataModificacao(LocalDate.now());
		
		entityManager.persist(entidade);
		
		return entidade;
	}
	
	@Transactional
	protected T atualizar(@NotNull T entidade) {
		entidade.setDataModificacao(LocalDate.now());
		
		entityManager.merge(entidade);
		
		return entidade;
	}

	@Override
	@Transactional
	public Optional<T> remover(@NotNull Long id) {
		Optional<T> optional = selecionar(id);
		
		if (optional.isPresent()) {
			T entidade = optional.get();
			entidade.setAtivo(false);
			entidade.setDataModificacao(LocalDate.now());
			
			entityManager.merge(entidade);
			return Optional.of(entidade);
		} else {
			return Optional.ofNullable(null);
		}
		
	}

	//**QUERIES**//
	@Override
	public Long contar() {
		String qlString = "select count(entidade) from " + getClassType().getSimpleName() + " entidade where ativo = true";
		return (Long) entityManager.createQuery(qlString).getSingleResult();
	}
	
	@Override
	public <E extends EntidadeBase> Optional<E> selecionar(@NotNull Class<E> clazz, @NotNull Long id) {
		E entidade = entityManager.find(clazz, id);
		
		if (entidade == null || entidade.getAtivo() == false) {
			return Optional.ofNullable(null);
		}
		
		return Optional.of(entidade);
	}
	
	@Override
	public Optional<T> selecionar(@NotNull Long id) {
		T entidade = entityManager.find(getClassType(), id);
		
		if (entidade == null || entidade.getAtivo() == false) {
			return Optional.ofNullable(null);
		}
		
		return Optional.of(entidade);
	}
	
	@Override
	public List<T> listarTodos(@NotNull Integer offSet, @NotNull Integer limit) {
		String queryString = "from " + getClassType().getSimpleName() + " entidade where entidade.ativo = true";
		return entityManager.createQuery(queryString, getClassType())
				.setFirstResult(offSet)
				.setMaxResults(limit)
				.getResultList();
	}
	
	@Override
	public List<T> listarTodosModificados(@NotNull Integer offSet, @NotNull Integer limit) {
		String queryString = "from " + getClassType().getSimpleName() + " entidade where entidade.ativo = true and modificado = true";
		return entityManager.createQuery(queryString, getClassType())
				.setFirstResult(offSet)
				.setMaxResults(limit)
				.getResultList();
	}
	
	@Override
	public List<T> listar(@NotNull Integer offSet, @NotNull Integer limit, @NotNull Example example) {
		return QBEasy.managedBy(entityManager)
				.setFirstResult(offSet)
				.setMaxResults(limit)
				.getList(example);
	}
	
	@Override
	public List<T> listar(@NotNull Integer offSet, @NotNull Integer limit, @NotNull Map<String, Object> params) {
		String qlString = gerarQlString(params);
		
		Query query = entityManager.createQuery(qlString)
				.setFirstResult(offSet)
				.setMaxResults(limit);
		
		setQueryParameters(query, params);
		return null;
	}

	protected String gerarQlString(@NotNull Map<String, Object> params) {
		StringBuilder builder = new StringBuilder("select entidade from " + getClassType().getSimpleName() + " entidade where ");
		String conversaoIndice;

		for (String key : params.keySet()) {
			conversaoIndice = " and " + key + " = " + ":" + key;
			builder.append(conversaoIndice);
		}
		
		return builder.toString();
	}

	private void setQueryParameters(Query query, Map<String, Object> params) {
		for (String key : params.keySet()) {
			query.setParameter(key, params.get(key));
		}
	}
	
	protected boolean isNew(@NotNull T entidade) {
        return entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entidade) == null;
    }
	
	@SuppressWarnings("unchecked")
    protected Class<T> getClassType() {
        if (clazz == null) {
        	ParameterizedType parameterizedType = (ParameterizedType) getClass().getSuperclass().getGenericSuperclass();
            clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
        }
        return clazz;
    }
}
