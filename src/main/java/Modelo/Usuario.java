package Modelo;

import Utilidades.Formato;


/* @author David Camargo*/
public abstract class Usuario {

    
    public static final double LIMITE_CONSUMO_BAJO      = 100;  // menos de 100 kWh
    public static final double LIMITE_CONSUMO_MODERADO  = 200;  // de 100 a 199 kWh
    public static final double LIMITE_CONSUMO_ALTO      = 300;  // de 200 a 299 kWh 300 o mas = muy alto
                                                                
    
    private String identificacion;   
    private String nombre;           
    private String tipoUsuario;      
    private double consumoKWh;       
    private double valorKWh;         

    // Estos tres se CALCULAN, no los escribe el usuario:
    private double valorConsumo;     // consumoKWh * valorKWh  
    private double descuento;        // Descuento que aplique cada tipo de usuario
    private double valorTotal;       // valorConsumo - descuento 
    
    // Consumo bajp, medio, alto etc...
    private String clasificacion;    

    
    public Usuario(String identificacion, String nombre, String tipoUsuario,
                   double consumoKWh, double valorKWh) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.tipoUsuario = tipoUsuario;
        this.consumoKWh = consumoKWh;
        this.valorKWh = valorKWh;

        // Los valores calculados arrancan en cero y en "Sin clasificar":
        this.valorConsumo = 0.0;
        this.descuento = 0.0;
        this.valorTotal = 0.0;
        this.clasificacion = "Sin clasificar";
    }


    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public double getConsumoKWh() {
        return consumoKWh;
    }

    public void setConsumoKWh(double consumoKWh) {
        this.consumoKWh = consumoKWh;
    }

    public double getValorKWh() {
        return valorKWh;
    }

    public void setValorKWh(double valorKWh) {
        this.valorKWh = valorKWh;
    }

    public double getValorConsumo() {
        return valorConsumo;
    }

    
    protected void setValorConsumo(double valorConsumo) {
        this.valorConsumo = valorConsumo;
    }

    public double getDescuento() {
        return descuento;
    }

    protected void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    
    public double getValorTotal() {
        return valorTotal;
    }

    protected void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    
    public void calcularValorTotal() {
        this.valorConsumo = this.consumoKWh * this.valorKWh;
        this.descuento = 0.0;
        this.valorTotal = this.valorConsumo;
    }

    // Metodo para la clasificacion del usuario segun su consumo:
    public void calcularClasificacion() {
        if (consumoKWh < LIMITE_CONSUMO_BAJO) {              // menos de 100
            clasificacion = "Consumo bajo";
        } else if (consumoKWh < LIMITE_CONSUMO_MODERADO) {   // de 100 a 199
            clasificacion = "Consumo moderado";
        } else if (consumoKWh < LIMITE_CONSUMO_ALTO) {       // de 200 a 299
            clasificacion = "Consumo alto";
        } else {                                             // 300 o mas
            clasificacion = "Consumo muy alto";
        }
    }

    
    public abstract String obtenerInformacionAdicional();

    
    // TEXTO PARA MOSTRAR EN PANTALLA
    
    public String obtenerDetalles() {
        StringBuilder detalles = new StringBuilder();
        detalles.append("Identificacion: ").append(identificacion).append("\n");
        detalles.append("Nombre: ").append(nombre).append("\n");
        detalles.append("Tipo de usuario: ").append(tipoUsuario).append("\n");
        detalles.append("Consumo mensual: ").append(Formato.numero(consumoKWh)).append(" kWh\n");
        detalles.append("Valor del kWh: ").append(Formato.moneda(valorKWh)).append("\n");
        detalles.append("Valor del consumo: ").append(Formato.moneda(valorConsumo)).append("\n");

        
        if (descuento > 0) {
            detalles.append("Descuento aplicado: -").append(Formato.moneda(descuento)).append("\n");
            detalles.append("Total a pagar: ").append(Formato.moneda(valorTotal)).append("\n");
        }

        detalles.append("Clasificacion: ").append(clasificacion.toUpperCase());
        return detalles.toString();
    }

    
    @Override
    public String toString() {
        return identificacion + " | " + nombre + " | " + tipoUsuario
                + " | " + Formato.numero(consumoKWh) + " kWh"
                + " | " + Formato.moneda(valorKWh)
                + " | " + Formato.moneda(valorTotal)
                + " | " + clasificacion;
    }
}
