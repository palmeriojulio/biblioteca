package br.com.pjcode.biblioteca.util;

import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateUtil {

    private DateUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Converter data em String (no formato "yyyy-MM-ddTHH:mm:ss.SSSZ") para LocalDate.
     *
     * @param dataString a data em String para converter.
     * @return um objeto LocalDate ou nulo se a conversão falhar.
     */
    public static LocalDate convertDateStringToLocalDate(String dataString) {
        String [] data = dataString.split("T");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(data[0], format);
        } catch (RuntimeException e) {
            return null;
        }
    }

    /**
     * Converte um LocalDate em uma String no formato "aaaa-MM-dd".
     *
     * @param date o LocalDate a ser convertido.
     * @return uma String de data formatada ou nula se a conversão falhar.
     */
    public static String convertLocalDateToStringDate(LocalDate date) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return date.format(format);
        } catch (RuntimeException e) {
            return null;
        }
    }

    /**
     * Converte um LocalDateTime em uma String de acordo com o padrão informado.
     *
     * @param date   o LocalDateTime a ser convertido.
     * @param pattern o padrão desejado para a String de saída.
     * @return uma String de data formatada ou nula se a conversão o falhar.
     */
    public static String convertLocalDateToString(LocalDateTime date, String pattern) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
        try {
            return date.format(format);
        } catch (RuntimeException e) {
            return null;
        }
    }

    /**
     * Converte um LocalDate em uma String de acordo com o padrão informado.
     *
     * @param date   o LocalDate a ser convertido.
     * @param pattern o padrão desejado para a String de saída.
     * @return uma String de data formatada ou nula se a conversão falhar.
     */
    public static String convertLocalDateToString(LocalDate date, String pattern) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
        try {
            return date.format(format);
        } catch (RuntimeException e) {
            return null;
        }
    }

    /**
     * Verifica se a data informada e posterior a data atual.
     * @param date a data a ser validada.
     * @return true se a data for posterior a data atual, false caso contrario.
     */
    public static boolean validate(LocalDate date) {
        try {
            return (date.isEqual(LocalDate.now()) || date.isAfter(LocalDate.now()));
        } catch (RuntimeException e) {
            return false;
        }
    }
}
