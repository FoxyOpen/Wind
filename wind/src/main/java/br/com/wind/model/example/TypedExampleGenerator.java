package br.com.wind.model.example;

import br.com.mk.qbeasy.model.interfaces.ExampleGenerator;
import br.com.wind.model.entity.EntidadeBase;

public interface TypedExampleGenerator<T extends EntidadeBase> extends ExampleGenerator { }
