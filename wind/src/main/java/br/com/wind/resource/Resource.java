package br.com.wind.resource;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;

import br.com.wind.model.dto.Dto;
import br.com.wind.model.entity.EntidadeBase;

public interface Resource<T extends EntidadeBase, E extends Dto> extends Serializable{

	Response inserir(@NotNull T entidade);
	Response atualizar(@NotNull T entidade);
	Response remover(@NotNull Long id);
	
	Response selecionar(@NotNull Long id);
	Response consultar(Integer offSet, Integer limit, E filtro);
	Response contar();
}
