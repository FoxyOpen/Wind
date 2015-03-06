package br.com.wind.converter;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDatePersistenceConverter implements AttributeConverter<LocalDate, Date> {
	
	@Override
	public Date convertToDatabaseColumn(LocalDate localDate) {
		if(localDate == null) return null;
		return Date.valueOf(localDate);
	}

	@Override
	public LocalDate convertToEntityAttribute(Date dbData) {
		if(dbData == null) return null;
		return dbData.toLocalDate();
	}
}
