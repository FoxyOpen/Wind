package br.com.wind.converter;

import java.sql.Time;
import java.time.LocalTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalTimePersistenceConverter implements AttributeConverter<LocalTime, Time> {

	@Override
	public Time convertToDatabaseColumn(LocalTime attribute) {
		if (attribute == null) return null;
		return Time.valueOf(attribute);
	}

	@Override
	public LocalTime convertToEntityAttribute(Time dbData) {
		if (dbData == null) return null;
		return dbData.toLocalTime();
	}
}
