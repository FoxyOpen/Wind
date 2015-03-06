package br.com.wind.podam.strategy;

import uk.co.jemos.podam.common.AttributeStrategy;

public class NullStrategy implements AttributeStrategy<Object> {

	@Override
	public Object getValue() {
		return null;
	}

}
