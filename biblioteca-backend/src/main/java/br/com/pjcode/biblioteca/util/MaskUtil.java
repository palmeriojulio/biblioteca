package br.com.pjcode.biblioteca.util;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public final class MaskUtil {

    private MaskUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Método que inclui mascara em um CPF/CPNJ.
     * @param cpfCpnj o CPF/CPNJ a ser formatado.
     * @param pattern a mascara a ser aplicada.
     * @return o CPF/CPNJ formatado.
     */
    public static String includeMask(String cpfCpnj, String pattern) {
        try {
            MaskFormatter mask = new MaskFormatter(pattern);
            mask.setValueContainsLiteralCharacters(false);
            return mask.valueToString(cpfCpnj);
        } catch (ParseException e) {
            return null;
        }
    }

}
