package br.com.wind.podam.strategy;

import java.time.LocalDate;

import uk.co.jemos.podam.common.AttributeStrategy;

public class PastDateStrategy implements AttributeStrategy<LocalDate>{

	@Override
	public LocalDate getValue() {
		
		return LocalDate.of(2014, 1, 1);
		
//		LocalDate inicio = LocalDate.of(1990, 1, 1);
//		ZonedDateTime zndInicio = inicio.atStartOfDay(ZoneId.systemDefault());
//		long inicioLong = zndInicio.toInstant().toEpochMilli();
//		
//		LocalDate fim = LocalDate.now();
//		ZonedDateTime zndFinal = fim.atStartOfDay(ZoneId.systemDefault());
//		long finalLong = zndFinal.toInstant().toEpochMilli();
//		
//		Instant instant = Instant.ofEpochSecond(getRandomTimeBetweenTwoDates(inicioLong, finalLong));
//		LocalDateTime random = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
//		
//		return LocalDate.of(random.getYear(), random.getMonth(), random.getDayOfMonth());
	}
	
//	private long getRandomTimeBetweenTwoDates(long inicio, long fim) {
//		long diff = fim - inicio + 1;
//	    return inicio + (long) (Math.random() * diff);
//	}

}
