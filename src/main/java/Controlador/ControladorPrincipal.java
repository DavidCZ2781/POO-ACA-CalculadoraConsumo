package Controlador;

import Modelo.GestorUsuarios;
import Modelo.Usuario;
import Modelo.UsuarioComercial;
import Modelo.UsuarioResidencial;
import Utilidades.ValidadorEntradas;
import Vista.VentanaPrincipal;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class ControladorPrincipal {

    
    private GestorUsuarios gestor;    
    private VentanaPrincipal vista;   

    public ControladorPrincipal(GestorUsuarios gestor, VentanaPrincipal vista) {
        this.gestor = gestor;
        this.vista = vista;
    }

    
    public void registrarUsuario() {
        
        String identificacion = vista.getTxtIdentificacion().getText().trim();
        String nombre         = vista.getTxtNombre().getText().trim();
        String tipo           = vista.getCboTipoUsuario().getSelectedItem().toString();
        String textoConsumo   = vista.getTxtConsumo().getText().trim();
        String textoValorKWh  = vista.getTxtValorKWh().getText().trim();

       
        if (!ValidadorEntradas.esTextoValido(identificacion)) {
            mostrarAdvertencia("La identificacion es obligatoria.");
            vista.getTxtIdentificacion().requestFocus(); 
            return;   
        }
        if (!ValidadorEntradas.esTextoValido(nombre)) {
            mostrarAdvertencia("El nombre del titular es obligatorio.");
            vista.getTxtNombre().requestFocus();
            return;
        }
        if (!ValidadorEntradas.esTextoValido(textoConsumo)) {
            mostrarAdvertencia("Debe ingresar el consumo mensual en kWh.");
            vista.getTxtConsumo().requestFocus();
            return;
        }
        if (!ValidadorEntradas.esTextoValido(textoValorKWh)) {
            mostrarAdvertencia("Debe ingresar el valor del kWh.");
            vista.getTxtValorKWh().requestFocus();
            return;
        }

      
        if (gestor.buscarPorIdentificacion(identificacion) != null) {
            mostrarAdvertencia("Ya existe un usuario registrado con la identificacion "
                    + identificacion + ".");
            return;
        }

       
        double consumo;
        double valorKWh;
        try {
            
            consumo  = Double.parseDouble(textoConsumo.replace(',', '.'));
            valorKWh = Double.parseDouble(textoValorKWh.replace(',', '.'));
        } catch (NumberFormatException e) {
            // Este bloque se ejecuta si el texto no era un numero (por ejemplo "abc").
            mostrarError("El consumo y el valor del kWh deben ser numeros.\n"
                    + "Ejemplo: 215 y 850");
            return;
        }

        
        if (!ValidadorEntradas.esConsumoValido(consumo)) {
            mostrarAdvertencia("El consumo no puede ser negativo.");
            vista.getTxtConsumo().requestFocus();
            return;
        }
        if (!ValidadorEntradas.esValorKwhValido(valorKWh)) {
            mostrarAdvertencia("El valor del kWh debe ser mayor que cero.");
            vista.getTxtValorKWh().requestFocus();
            return;
        }

        
        Usuario nuevoUsuario;

        if (tipo.equals("Residencial")) {
            String textoHabitantes = vista.getTxtHabitantes().getText().trim();
            int habitantes;
            try {
                habitantes = Integer.parseInt(textoHabitantes);
            } catch (NumberFormatException e) {
                mostrarError("El numero de habitantes debe ser un numero entero.");
                vista.getTxtHabitantes().requestFocus();
                return;
            }
            if (!ValidadorEntradas.esNumeroHabitantesValido(habitantes)) {
                mostrarAdvertencia("La vivienda debe tener al menos 1 habitante.");
                vista.getTxtHabitantes().requestFocus();
                return;
            }
            nuevoUsuario = new UsuarioResidencial(identificacion, nombre,
                    consumo, valorKWh, habitantes);

        } else {
            String rubro = vista.getTxtRubro().getText().trim();
            String nit   = vista.getTxtNit().getText().trim();
            if (!ValidadorEntradas.esTextoValido(rubro)) {
                mostrarAdvertencia("Debe indicar el rubro del negocio.");
                vista.getTxtRubro().requestFocus();
                return;
            }
            if (!ValidadorEntradas.esTextoValido(nit)) {
                mostrarAdvertencia("Debe indicar el NIT del negocio.");
                vista.getTxtNit().requestFocus();
                return;
            }
            nuevoUsuario = new UsuarioComercial(identificacion, nombre,
                    consumo, valorKWh, rubro, nit);
        }

    
        nuevoUsuario.calcularValorTotal();     
        nuevoUsuario.calcularClasificacion();
        gestor.agregarUsuario(nuevoUsuario);
        vista.setUsuarioActual(nuevoUsuario);
        mostrarResultado(nuevoUsuario);
        actualizarTabla();
        vista.limpiarCampos();
    }

    
    public void mostrarResultado(Usuario usuario) {
        String mensaje = "=== REGISTRO EXITOSO ===\n\n" + usuario.obtenerDetalles();

       
        vista.getTxtResultado().setText(mensaje);

      
        JOptionPane.showMessageDialog(vista, mensaje,
                "Resultado del registro", JOptionPane.INFORMATION_MESSAGE);
    }

   
    // GENERAR EL REPORTE
   
    public void generarReporte() {
        vista.getTxtResultado().setText(gestor.generarReporte());
        actualizarTabla();
    }

  
    public void actualizarTabla() {
        DefaultTableModel modelo = new DefaultTableModel(
                gestor.obtenerDatosParaTabla(),
                GestorUsuarios.COLUMNAS_REPORTE);
        vista.getTablaUsuarios().setModel(modelo);
    }

    
    // BUSCAR UN USUARIO
    
    public void buscarUsuario(String identificacion) {
        if (!ValidadorEntradas.esTextoValido(identificacion)) {
            mostrarAdvertencia("Escriba una identificacion en el campo "
                    + "\"Identificacion\" para poder buscar.");
            return;
        }

        Usuario encontrado = gestor.buscarPorIdentificacion(identificacion.trim());

        if (encontrado != null) {
            vista.setUsuarioActual(encontrado);
            vista.mostrarUsuarioEncontrado(encontrado);
        } else {
            mostrarAdvertencia("No se encontro ningun usuario con la identificacion "
                    + identificacion.trim() + ".");
        }
    }

    

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje,
                "Datos incompletos", JOptionPane.WARNING_MESSAGE);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje,
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
