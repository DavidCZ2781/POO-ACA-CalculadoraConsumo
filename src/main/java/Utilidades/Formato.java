package Utilidades;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Clase de utilidad para dar formato a los numeros que se muestran en pantalla.
 *
 * Sin esta clase, Java imprimiria "164475.0" en vez de "$164.475", que es como
 * se ven los pesos colombianos en el enunciado.
 *
 * Todos los metodos son "static": eso significa que NO hay que crear un objeto
 * para usarlos. Se llaman directamente con el nombre de la clase, asi:
 *     Formato.moneda(182750)  ->  "$182.750"
 */
public class Formato {

    // Los "simbolos" definen que caracter separa los miles y cual los decimales.
    // En Colombia el punto separa los miles (1.000) y la coma los decimales (1,5).
    private static final DecimalFormatSymbols SIMBOLOS = crearSimbolos();

    // Patron "$#,##0" -> signo pesos, separador de miles, sin decimales.
    private static final DecimalFormat FORMATO_PESOS = new DecimalFormat("$#,##0", SIMBOLOS);

    // Patron "#,##0.##" -> separador de miles y hasta 2 decimales, pero solo
    // los muestra si existen (215 se ve "215", no "215,00").
    private static final DecimalFormat FORMATO_NUMERO = new DecimalFormat("#,##0.##", SIMBOLOS);

    /**
     * Constructor privado: impide que alguien escriba "new Formato()".
     * Esta clase es solo una caja de herramientas, no tiene sentido instanciarla.
     */
    private Formato() {
    }

    private static DecimalFormatSymbols crearSimbolos() {
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setGroupingSeparator('.');  // miles:    1.000
        simbolos.setDecimalSeparator(',');   // decimales: 1,5
        return simbolos;
    }

    /**
     * Convierte un numero en un texto con formato de pesos.
     * Ejemplo: moneda(182750) devuelve "$182.750"
     */
    public static String moneda(double valor) {
        return FORMATO_PESOS.format(valor);
    }

    /**
     * Convierte un numero en texto sin el signo de pesos.
     * Ejemplo: numero(215.0) devuelve "215"
     */
    public static String numero(double valor) {
        return FORMATO_NUMERO.format(valor);
    }
}
