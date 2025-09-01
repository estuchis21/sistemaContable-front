package sistemacontable.sistemaContable.front.Login;

import sistemacontable.back.Models.Asientos;
import sistemacontable.back.Models.Operaciones;
import sistemacontable.back.Services.LibroDiario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;

public class VentanaLibroDiario extends JFrame {

    private final LibroDiario controller;
    private JTextField txtDescripcionAsiento;
    private JButton btnCrearAsiento;
    private JTable tablaOperaciones;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregarFila, btnGuardarOperaciones;
    private JComboBox<String> cbTipoLibro;

    private Asientos asientoCreado;

    public VentanaLibroDiario() {
        controller = new LibroDiario();
        setTitle("Libro Diario");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10,10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // --- Panel Asiento ---
        JPanel panelAsiento = new JPanel(new FlowLayout());
        JLabel lblDescripcion = new JLabel("Descripción del Asiento:");
        txtDescripcionAsiento = new JTextField(30);
        btnCrearAsiento = new JButton("Crear Asiento");
        panelAsiento.add(lblDescripcion);
        panelAsiento.add(txtDescripcionAsiento);
        panelAsiento.add(btnCrearAsiento);

        btnCrearAsiento.addActionListener(e -> crearAsiento());

        // --- Tabla Operaciones ---
        modeloTabla = new DefaultTableModel(new Object[]{"Cuenta","Tipo Movimiento","Monto","Tipo Libro","IVA"},0);
        tablaOperaciones = new JTable(modeloTabla);
        tablaOperaciones.setEnabled(false);
        JScrollPane scrollTabla = new JScrollPane(tablaOperaciones);

        // --- Panel Operaciones ---
        JPanel panelOperaciones = new JPanel(new FlowLayout());
        btnAgregarFila = new JButton("Agregar Fila");
        btnGuardarOperaciones = new JButton("Guardar Operaciones");

        btnAgregarFila.setEnabled(false);
        btnGuardarOperaciones.setEnabled(false);

        cbTipoLibro = new JComboBox<>(new String[]{"Compras","Ventas"});
        panelOperaciones.add(cbTipoLibro);
        panelOperaciones.add(btnAgregarFila);
        panelOperaciones.add(btnGuardarOperaciones);

        // Agregar fila
        btnAgregarFila.addActionListener(e -> {
            String iva = preguntarIVA();
            modeloTabla.addRow(new Object[]{"", "", "", cbTipoLibro.getSelectedItem(), iva});
        });

        btnGuardarOperaciones.addActionListener(e -> guardarOperaciones());

        // --- Panel Principal ---
        panelPrincipal.add(panelAsiento, BorderLayout.NORTH);
        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);
        panelPrincipal.add(panelOperaciones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private String preguntarIVA() {
        String[] opciones = {"Ninguno","IVA 21%","IVA 10,5%","Ambos"};
        int seleccion = JOptionPane.showOptionDialog(this,
                "Seleccione el tipo de IVA para esta operación:",
                "Seleccionar IVA",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]);
        return seleccion >= 0 ? opciones[seleccion] : "Ninguno";
    }

    private void crearAsiento() {
        String descripcion = txtDescripcionAsiento.getText().trim();
        if(descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese una descripción para el asiento.");
            return;
        }

        asientoCreado = new Asientos();
        asientoCreado.setDescripcion(descripcion);
        asientoCreado.setFecha_asiento(LocalDateTime.now());

        boolean exito = controller.generarAsiento(asientoCreado);

        if(exito) {
            JOptionPane.showMessageDialog(this, "Asiento creado con ID: " + asientoCreado.getId_asiento());
            txtDescripcionAsiento.setEnabled(false);
            btnCrearAsiento.setEnabled(false);
            tablaOperaciones.setEnabled(true);
            btnAgregarFila.setEnabled(true);
            btnGuardarOperaciones.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(this, "Error al crear asiento.");
        }
    }

    private void guardarOperaciones() {
        if(asientoCreado == null || asientoCreado.getId_asiento() <= 0) {
            JOptionPane.showMessageDialog(this,"Primero cree un asiento.");
            return;
        }

        boolean todasExitosas = true;

        for(int i=0;i<modeloTabla.getRowCount();i++) {
            try {
                Object objCuenta = modeloTabla.getValueAt(i,0);
                Object objTipo = modeloTabla.getValueAt(i,1);
                Object objMonto = modeloTabla.getValueAt(i,2);
                Object objLibro = modeloTabla.getValueAt(i,3);
                Object objIVA = modeloTabla.getValueAt(i,4);

                if(objCuenta==null || objTipo==null || objMonto==null || objLibro==null || objIVA==null) {
                    todasExitosas = false;
                    System.err.println("Fila " + i + " incompleta, saltando.");
                    continue;
                }

                String montoStr = objMonto.toString().trim();
                if(montoStr.isEmpty()) {
                    todasExitosas = false;
                    System.err.println("Fila " + i + " tiene monto vacío, saltando.");
                    continue;
                }

                int idCuenta = Integer.parseInt(objCuenta.toString());
                int tipoMov = Integer.parseInt(objTipo.toString());
                double monto = Double.parseDouble(montoStr);
                String tipoLibro = objLibro.toString();

                boolean iva21 = objIVA.toString().contains("21");
                boolean iva105 = objIVA.toString().contains("10");

                Operaciones op = new Operaciones(
                        asientoCreado.getId_asiento(),
                        idCuenta,
                        tipoMov,
                        monto,
                        tipoLibro,
                        iva21,
                        iva105
                );

                boolean exito = controller.generarOperacionPorAsiento(op);
                if(!exito) todasExitosas = false;

            } catch (NumberFormatException nfe) {
                todasExitosas = false;
                System.err.println("Error al parsear números en fila " + i + ": " + nfe.getMessage());
            } catch (Exception e) {
                todasExitosas = false;
                System.err.println("Error inesperado en fila " + i + ": " + e.getMessage());
                e.printStackTrace();
            }
        }

        if(todasExitosas) {
            JOptionPane.showMessageDialog(this,"Todas las operaciones fueron agregadas correctamente.");
            modeloTabla.setRowCount(0);
        } else {
            JOptionPane.showMessageDialog(this,"Hubo errores al guardar algunas operaciones. Revise la consola.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaLibroDiario().setVisible(true));
    }
}
