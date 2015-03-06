package br.com.wind.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Moeda {
	public static BigDecimal somar(BigDecimal... valores) {
		BigDecimal somatorio = Moeda.valueOf("0");
		for (BigDecimal valor : valores) {
			somatorio = somatorio.add(valor);
		}
		return somatorio.setScale(2, RoundingMode.HALF_UP);
	}
	
	public static BigDecimal somar(Number... valores) {
		List<BigDecimal> bigDecimals = new ArrayList<BigDecimal>();
		for (Number number : valores) {
			bigDecimals.add(new BigDecimal(String.valueOf(number)));
		}
		
		BigDecimal[] array = new BigDecimal[valores.length];
		array = bigDecimals.toArray(array);
 		return somar(array);
	}
	
	public static BigDecimal somar(String... valores) {
		List<BigDecimal> bigDecimals = new ArrayList<BigDecimal>();
		for (String string : valores) {
			bigDecimals.add(new BigDecimal(string));
		}
		
		BigDecimal[] array = new BigDecimal[valores.length];
		array = bigDecimals.toArray(array);
 		return somar(array);
	}
	
	public static BigDecimal subtrair(BigDecimal... valores) {
		BigDecimal resultado = valores[0];
		for (int i = 1; i < valores.length; i++) {
			BigDecimal valor = valores[i];
			resultado = resultado.subtract(valor);
		}
		return resultado.setScale(2, RoundingMode.HALF_UP);
	}
	
	public static BigDecimal subtrair(Number... valores) {
		List<BigDecimal> bigDecimals = new ArrayList<BigDecimal>();
		for (Number number : valores) {
			bigDecimals.add(new BigDecimal(String.valueOf(number)));
		}
		
		BigDecimal[] array = new BigDecimal[valores.length];
		array = bigDecimals.toArray(array);
 		return subtrair(array);
	}
	
	public static BigDecimal subtrair(String... valores) {
		List<BigDecimal> bigDecimals = new ArrayList<BigDecimal>();
		for (String string : valores) {
			bigDecimals.add(new BigDecimal(string));
		}
		
		BigDecimal[] array = new BigDecimal[valores.length];
		array = bigDecimals.toArray(array);
 		return subtrair(array);
	}
	
	public static BigDecimal multiplicar(BigDecimal fator1, BigDecimal fator2) {
		BigDecimal produto = fator1.multiply(fator2);
		return produto.setScale(2, RoundingMode.HALF_UP);
	}
	
	public static BigDecimal multiplicar(BigDecimal fator1, String fator2) {
		BigDecimal fator2new = new BigDecimal(fator2);
		return multiplicar(fator1, fator2new);
	}
	
	public static BigDecimal multiplicar(String fator1, String fator2) {
		BigDecimal fator1new = new BigDecimal(fator1);
		BigDecimal fator2new = new BigDecimal(fator2);
		return multiplicar(fator1new, fator2new);
	}
	
	public static BigDecimal multiplicar(String fator1, BigDecimal fator2) {
		BigDecimal fator1new = new BigDecimal(fator1);
		return multiplicar(fator1new, fator2);
	}
	
	public static BigDecimal dividir(BigDecimal fator1, BigDecimal fator2) {
		BigDecimal resultado = fator1.divide(fator2);
		return resultado.setScale(2, RoundingMode.HALF_UP);
	}
	
	public static BigDecimal dividir(BigDecimal fator1, String fator2) {
		BigDecimal fator2new = new BigDecimal(fator2);
		return dividir(fator1, fator2new);
	}
	
	public static BigDecimal dividir(String fator1, String fator2) {
		BigDecimal fator1new = new BigDecimal(fator1);
		BigDecimal fator2new = new BigDecimal(fator2);
		return dividir(fator1new, fator2new);
	}
	
	public static BigDecimal dividir(String fator1, BigDecimal fator2) {
		BigDecimal fator1new = new BigDecimal(fator1);
		return dividir(fator1new, fator2);
	}
	
	public static BigDecimal valueOf(String valor) {
		return new BigDecimal(valor).setScale(2, RoundingMode.HALF_UP);
	}
	
	public static BigDecimal valueOf(BigDecimal valor) {
		return valor.setScale(2, RoundingMode.HALF_UP);
	}
}