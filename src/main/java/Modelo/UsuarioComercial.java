package Modelo;


public class UsuarioComercial extends Usuario {

    /** A partir de cuantos kWh el negocio recibe la tarifa preferencial. */
    private static final double CONSUMO_PARA_DESCUENTO = 300;

    /** Porcentaje de descuento comercial: 0.05 es el 5%. */
    private static final double PORCENTAJE_DESCUENTO = 0.05;

    private String rubroNegocio;
    private String nit;
    private boolean tieneDescuento;

    public UsuarioComercial(String identificacion, String nombre,
                            double consumoKWh, double valorKWh,
                            String rubroNegocio, String nit) {
        super(identificacion, nombre, "Comercial", consumoKWh, valorKWh);
        this.rubroNegocio = rubroNegocio;
        this.nit = nit;
        this.tieneDescuento = false;
    }

    public String getRubroNegocio() {
        return rubroNegocio;
    }

    public void setRubroNegocio(String rubroNegocio) {
        this.rubroNegocio = rubroNegocio;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public boolean isTieneDescuento() {
        return tieneDescuento;
    }

    
    @Override
    public void calcularValorTotal() {
        super.calcularValorTotal();

        if (getConsumoKWh() > CONSUMO_PARA_DESCUENTO) {
            tieneDescuento = true;
            double descuento = getValorConsumo() * PORCENTAJE_DESCUENTO;
            setDescuento(descuento);
            setValorTotal(getValorConsumo() - descuento);
        } else {
            tieneDescuento = false;
        }
    }

    @Override
    public String obtenerInformacionAdicional() {
        return "Rubro del negocio: " + rubroNegocio
                + "\nNIT: " + nit
                + "\nTiene descuento: " + (tieneDescuento ? "Si (5%)" : "No");
    }

    @Override
    public String obtenerDetalles() {
        return super.obtenerDetalles() + "\n" + obtenerInformacionAdicional();
    }
}
