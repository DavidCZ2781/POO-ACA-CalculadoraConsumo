package Utilidades;

public class ValidadorEntradas {

   
    private ValidadorEntradas() {
    }
    
    public static boolean esTextoValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }
   
    public static boolean esConsumoValido(double consumo) {
        return consumo >= 0;
    }
    
    public static boolean esValorKwhValido(double valorKwh) {
        return valorKwh > 0;
    }
    
    public static boolean esNumeroHabitantesValido(int habitantes) {
        return habitantes >= 1;
    }
}
