package sistemacontable.sistemaContable.front.Login;

import sistemacontable.back.Models.Asientos;
import sistemacontable.back.Models.Operaciones;
import sistemacontable.back.Models.Cuentas;
import sistemacontable.back.Services.LibroDiario;
import sistemacontable.sistemaContable.front.Login.Controllers.PlanDeCuentasController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

public class VentanaLibroDiario extends JFrame {

    private final LibroDiario controller;
    private final PlanDeCuentasController cuentasController;
    private JTextField txtDescripcionAsiento;
    private JButton btnCrearAsiento, btnAgregarFila, btnGuardarOperaciones, btnVolverMenu;
    private JTable tablaOperaciones;
    private DefaultTableModel modeloTabla;
    private Asientos asientoCreado;
    private List<Cuentas> listaCuentas;

    private int idUsuario;
    private String nombres;
    private int idRol;

    // Constructor con parámetros del usuario
    public VentanaLibroDiario(int idUsuario, String nombres, int idRol) {
        this.idUsuario = idUsuario;
        this.nombres = nombres;
        this.idRol = idRol;

        controller = new LibroDiario();
        cuentasController = new PlanDeCuentasController();

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
        JPanel panelAsiento = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnVolverMenu = new JButton("Volver al Menú");
        JLabel lblDescripcion = new JLabel("Descripción del Asiento:");
        txtDescripcionAsiento = new JTextField(30);
        btnCrearAsiento = new JButton("Crear Asiento");

        panelAsiento.add(btnVolverMenu);
        panelAsiento.add(lblDescripcion);
        panelAsiento.add(txtDescripcionAsiento);
        panelAsiento.add(btnCrearAsiento);

        // Acción Volver al Menú
        btnVolverMenu.addActionListener(e -> {
            VentanaBienvenida ventana = new VentanaBienvenida(idUsuario, nombres, idRol);
            ventana.setVisible(true);
            dispose();
        });

        btnCrearAsiento.addActionListener(e -> crearAsiento());

        // --- Tabla Operaciones ---
        modeloTabla = new DefaultTableModel(new Object[]{"Cuenta","Tipo Movimiento","Monto","Tipo Libro","IVA"},0);
        tablaOperaciones = new JTable(modeloTabla);

        // --- Cargar cuentas ---
        listaCuentas = cuentasController.mostrarCuentas();
        if (listaCuentas == null || listaCuentas.isEmpty()) {
            JOptionPane.showMessageDialog(this,"No se pudieron cargar las cuentas.");
            listaCuentas = List.of(); // lista vacía para evitar NPE
        }

        // Combo de cuentas dentro de la tabla
        JComboBox<Cuentas> comboCuentasTabla = new JComboBox<>();
        for(Cuentas c : listaCuentas) comboCuentasTabla.addItem(c);
        comboCuentasTabla.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
                if(value instanceof Cuentas) setText(((Cuentas)value).getNombre());
                return this;
            }
        });
        tablaOperaciones.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(comboCuentasTabla));

        // Combo Tipo Movimiento
        JComboBox<String> comboTipo = new JComboBox<>(new String[]{"Debe","Haber"});
        tablaOperaciones.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(comboTipo));

        JScrollPane scrollTabla = new JScrollPane(tablaOperaciones);

        // --- Panel Botones ---
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnAgregarFila = new JButton("Agregar Fila");
        btnGuardarOperaciones = new JButton("Guardar Asiento");

        btnAgregarFila.setEnabled(false);
        btnGuardarOperaciones.setEnabled(false);

        btnAgregarFila.addActionListener(e -> {
            modeloTabla.addRow(new Object[]{
                    listaCuentas.isEmpty() ? null : listaCuentas.get(0),
                    "Debe",
                    0.0,
                    "Compras",
                    "Ninguno"
            });
        });

        btnGuardarOperaciones.addActionListener(e -> guardarOperaciones());

        panelBotones.add(btnAgregarFila);
        panelBotones.add(btnGuardarOperaciones);

        // --- Panel Principal ---
        panelPrincipal.add(panelAsiento, BorderLayout.NORTH);
        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
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
            btnAgregarFila.setEnabled(true);
            btnGuardarOperaciones.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(this, "Error al crear asiento.");
        }
    }

    // Validar que Debe = Haber y que montos sean mayores a 0
    private boolean validarDebeHaberIguales() {
        double totalDebe = 0;
        double totalHaber = 0;
        for(int i=0; i<modeloTabla.getRowCount(); i++) {
            Object objTipo = modeloTabla.getValueAt(i,1);
            Object objMonto = modeloTabla.getValueAt(i,2);

            if (objTipo == null || objMonto == null) return false;

            double monto;
            try {
                monto = Double.parseDouble(objMonto.toString());
            } catch (NumberFormatException e) {
                return false; // monto no es válido
            }

            if (monto <= 0) return false;

            if (objTipo.toString().equalsIgnoreCase("Debe")) totalDebe += monto;
            else if (objTipo.toString().equalsIgnoreCase("Haber")) totalHaber += monto;
        }
        return Math.abs(totalDebe - totalHaber) < 0.0001; // deben ser iguales
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

    private void guardarOperaciones() {
        if(asientoCreado == null || asientoCreado.getId_asiento() <= 0) {
            JOptionPane.showMessageDialog(this,"Primero cree un asiento.");
            return;
        }

        if(!validarDebeHaberIguales()) {
            JOptionPane.showMessageDialog(this,"El total Debe y total Haber no coinciden o hay montos inválidos. No se guarda ninguna operación.");
            return;
        }

        boolean todasExitosas = true;

        for(int i=0;i<modeloTabla.getRowCount();i++) {
            try {
                Cuentas c = (Cuentas) modeloTabla.getValueAt(i,0);
                String tipoStr = modeloTabla.getValueAt(i,1).toString();
                double monto = Double.parseDouble(modeloTabla.getValueAt(i,2).toString());
                String tipoLibro = modeloTabla.getValueAt(i,3).toString();
                String ivaStr = preguntarIVA();

                int tipoMov = tipoStr.equalsIgnoreCase("Debe") ? 1 : 2;
                boolean iva21 = ivaStr.contains("21");
                boolean iva105 = ivaStr.contains("10");

                Operaciones op = new Operaciones(
                        asientoCreado.getId_asiento(),
                        c.getId_cuenta(),
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
                System.err.println("Error al parsear número en fila " + i + ": " + nfe.getMessage());
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
        // Para pruebas rápidas con usuario ficticio
        SwingUtilities.invokeLater(() -> new VentanaLibroDiario(1, "Esteban", 2).setVisible(true));
    }
}
