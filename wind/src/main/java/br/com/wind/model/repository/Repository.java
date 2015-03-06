package br.com.wind.model.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import br.com.mk.qbeasy.core.Example;
import br.com.wind.model.entity.EntidadeBase;

public interface Repository<T extends EntidadeBase> extends Serializable {

	T salvar(@NotNull T entidade);
	Optional<T> remover(@NotNull Long id);
	
	Long contar();
	
	List<T> listarTodos(@NotNull Integer offSet, @NotNull Integer limit);
	List<T> listarTodosModificados(@NotNull Integer offSet, @NotNull Integer limit);
	
	List<T> listar(@NotNull Integer offSet, @NotNull Integer limit, @NotNull Example example);
	List<T> listar(@NotNull Integer offSet, @NotNull Integer limit, @NotNull Map<String, Object> params);
	
	Optional<T> selecionar(@NotNull Long id);
	<E extends EntidadeBase> Optional<E> selecionar(@NotNull Class<E> clazz, @NotNull Long id);
}
