package Modelo;


public class UsuarioResidencial extends Usuario {

    /** A partir de cuantos habitantes la empresa otorga el subsidio. */
    private static final int HABITANTES_PARA_SUBSIDIO = 4;

    /** Porcentaje del subsidio: 0.10 es el 10%. */
    private static final double PORCENTAJE_SUBSIDIO = 0.10;

    // Atributos propios de este tipo de usuario (siguen siendo privados).
    private int numeroHabitantes;
    private boolean tieneSubsidio;

    
     
    public UsuarioResidencial(String identificacion, String nombre,double consumoKWh, double valorKWh,int numeroHabitantes) {
        super(identificacion, nombre, "Residencial", consumoKWh, valorKWh);
        this.numeroHabitantes = numeroHabitantes;
        this.tieneSubsidio = false;  // todavia no se sabe: se decide al calcular
    }

    public int getNumeroHabitantes() {
        return numeroHabitantes;
    }

    public void setNumeroHabitantes(int numeroHabitantes) {
        this.numeroHabitantes = numeroHabitantes;
    }

   
    public boolean isTieneSubsidio() {
        return tieneSubsidio;
    }

    
    @Override
    public void calcularValorTotal() {
        super.calcularValorTotal();   // 1. la formula base -> valorConsumo

        // 2. y encima, lo propio de una vivienda
        if (numeroHabitantes > HABITANTES_PARA_SUBSIDIO) {
            tieneSubsidio = true;
            double subsidio = getValorConsumo() * PORCENTAJE_SUBSIDIO;
            setDescuento(subsidio);
            setValorTotal(getValorConsumo() - subsidio);
        } else {
            tieneSubsidio = false;
        }
    }

    
    @Override
    public String obtenerInformacionAdicional() {
        return "Numero de habitantes: " + numeroHabitantes
                + "\nTiene subsidio: " + (tieneSubsidio ? "Si (10%)" : "No");
    }

    
    @Override
    public String obtenerDetalles() {
        return super.obtenerDetalles() + "\n" + obtenerInformacionAdicional();
    }
}
