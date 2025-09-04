package sistemacontable.sistemaContable.front.Login;

import sistemacontable.back.Models.LibroMayor;
import sistemacontable.back.Models.Cuentas;
import sistemacontable.sistemaContable.front.Login.Controllers.LibroMayorController;
import sistemacontable.sistemaContable.front.Login.Controllers.PlanDeCuentasController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaLibroMayor extends JFrame {

    private JTable tablaMayor;
    private DefaultTableModel model;
    private JComboBox<String> comboCuentas;
    private LibroMayorController controller;
    private PlanDeCuentasController cuentasC;
    private JButton btnCrearAsiento, btnAgregarFila, btnGuardarOperaciones, btnVolverMenu;
    private int idUsuario;
    private String nombres;
    private int idRol;

    public VentanaLibroMayor() {
        controller = new LibroMayorController();
        cuentasC = new PlanDeCuentasController();
        setTitle("Libro Mayor");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initComponents();
        setVisible(true);
    }

    public VentanaLibroMayor(int idUsuario, String nombres, int idRol) {
        this.idUsuario = idUsuario;
        this.nombres = nombres;
        this.idRol = idRol;
    }

    
    
    private void initComponents() {
        btnVolverMenu = new JButton("Volver al Menú");
        model = new DefaultTableModel(
                new Object[]{"Cuenta", "Fecha", "Descripción", "Debe", "Haber", "Saldo"}, 0);
        tablaMayor = new JTable(model);
        JScrollPane scroll = new JScrollPane(tablaMayor);

        comboCuentas = new JComboBox<>();
        comboCuentas.addItem("Todas las cuentas");
        cargarCuentas();
        comboCuentas.addActionListener(e -> cargarLibroMayor());

        // Acción Volver al Menú
        btnVolverMenu.addActionListener(e -> {
            VentanaBienvenida ventana = new VentanaBienvenida(idUsuario, nombres, idRol);
            ventana.setVisible(true);
            dispose();
        });

        // Panel superior: combo + botón
        JPanel panelTop = new JPanel();
        panelTop.add(new JLabel("Cuenta:"));
        panelTop.add(comboCuentas);
        panelTop.add(btnVolverMenu); // <-- agregamos el botón al panel

        add(panelTop, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // Cargar automáticamente todas las cuentas al inicio
        cargarLibroMayor();
    }


    private void cargarCuentas() {
        List<Cuentas> cuentas = cuentasC.mostrarCuentas();
        if (cuentas != null) {
            for (Cuentas c : cuentas) {
                comboCuentas.addItem(c.getId_cuenta() + " - " + c.getNombre());
            }
        }
    }

    private void cargarLibroMayor() {
        model.setRowCount(0);

        List<LibroMayor> movimientos;
        String seleccionado = comboCuentas.getSelectedItem() != null ? comboCuentas.getSelectedItem().toString() : "";

        if (seleccionado.equals("Todas las cuentas")) {
            movimientos = controller.obtenerLibroMayor(0); // 0 = todas las cuentas
        } else {
            int idCuenta = Integer.parseInt(seleccionado.split(" - ")[0]);
            movimientos = controller.obtenerLibroMayor(idCuenta);
        }

        for (LibroMayor lm : movimientos) {
            double debe = lm.getIdTipoMovimiento() == 1 ? lm.getSaldoAcumulado() : 0;
            double haber = lm.getIdTipoMovimiento() == 2 ? lm.getSaldoAcumulado() : 0;

            model.addRow(new Object[]{
                    lm.getTipoSaldo(),          // Cuenta o tipoSaldo
                    lm.getFechaAsiento(),
                    lm.getDescripcion(),
                    debe,
                    haber,
                    lm.getSaldoAcumulado()
            });
        }
    }
}
