package br.com.wind.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotNull;

public class DateUtils {
	private static final DateTimeFormatter DEFAULT_LOCAL_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public static String formatarLocalDate(@NotNull LocalDate localDate) {
		return DEFAULT_LOCAL_FORMATTER.format(localDate);
	}
}
