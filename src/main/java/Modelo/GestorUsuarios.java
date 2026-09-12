package Modelo;

import Utilidades.Formato;
import java.util.ArrayList;


public class GestorUsuarios {

    
    public static final String[] COLUMNAS_REPORTE = {
        "Identificacion", "Nombre", "Tipo", "Consumo (kWh)",
        "Valor kWh", "Valor total", "Clasificacion"
    };
    
    private ArrayList<Usuario> listaUsuarios;

    public GestorUsuarios() {
        listaUsuarios = new ArrayList<>();
    }

//Validadores de Ingreso o excepciones:    
    public void agregarUsuario(Usuario usuario) {
        if (usuario != null) {
            listaUsuarios.add(usuario);
        }
    }

    public ArrayList<Usuario> obtenerTodosUsuarios() {
        return listaUsuarios;
    }

    public int contarUsuarios() {
        return listaUsuarios.size();
    }

    public boolean estaVacio() {
        return listaUsuarios.isEmpty();
    }
    
    public void limpiar() {
        listaUsuarios.clear();
    }

    
    public Usuario buscarPorIdentificacion(String id) {
        if (id == null) {
            return null;
        }
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getIdentificacion().equals(id.trim())) {
                return usuario;
            }
        }
        return null;  
    }

    
    public Object[][] obtenerDatosParaTabla() {
        
        Object[][] datos = new Object[listaUsuarios.size()][COLUMNAS_REPORTE.length];

        for (int fila = 0; fila < listaUsuarios.size(); fila++) {
            Usuario u = listaUsuarios.get(fila);
            datos[fila][0] = u.getIdentificacion();
            datos[fila][1] = u.getNombre();
            datos[fila][2] = u.getTipoUsuario();
            datos[fila][3] = Formato.numero(u.getConsumoKWh());
            datos[fila][4] = Formato.moneda(u.getValorKWh());
            datos[fila][5] = Formato.moneda(u.getValorTotal());
            datos[fila][6] = u.getClasificacion();
        }
        return datos;
    }

    
    public String generarReporte() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("=========================================================\n");
        reporte.append("          REPORTE DE CONSUMO ELECTRICO\n");
        reporte.append("=========================================================\n\n");

        if (listaUsuarios.isEmpty()) {
            reporte.append("No hay usuarios registrados todavia.\n");
            return reporte.toString();
        }

        // Fila de titulos
        reporte.append(String.format("%-15s | %-20s | %-12s | %-13s | %-10s | %-12s | %-18s%n",
                (Object[]) COLUMNAS_REPORTE));
        reporte.append("-".repeat(115)).append("\n");

        // Una linea por usuario (estructura repetitiva)
        for (Usuario usuario : listaUsuarios) {
            reporte.append(String.format("%-15s | %-20s | %-12s | %-13s | %-10s | %-12s | %-18s%n",
                    usuario.getIdentificacion(),
                    usuario.getNombre(),
                    usuario.getTipoUsuario(),
                    Formato.numero(usuario.getConsumoKWh()),
                    Formato.moneda(usuario.getValorKWh()),
                    Formato.moneda(usuario.getValorTotal()),
                    usuario.getClasificacion()));
        }

        reporte.append("-".repeat(115)).append("\n");
        reporte.append("Total de usuarios registrados: ").append(listaUsuarios.size()).append("\n");
        return reporte.toString();
    }
}
