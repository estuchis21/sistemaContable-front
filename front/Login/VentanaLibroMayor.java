package sistemacontable.sistemaContable.front.Login;

import sistemacontable.back.Models.LibroMayor;
import sistemacontable.sistemaContable.front.Login.Controllers.LibroMayorController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaLibroMayor extends JFrame {

    private JTable tablaMayor;
    private DefaultTableModel model;
    private JComboBox<String> comboCuentas;
    private LibroMayorController controller;

    public VentanaLibroMayor() {
        controller = new LibroMayorController();

        setTitle("Libro Mayor");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        model = new DefaultTableModel(new Object[]{"Fecha", "Descripción", "Debe", "Haber"}, 0);
        tablaMayor = new JTable(model);
        JScrollPane scroll = new JScrollPane(tablaMayor);

        comboCuentas = new JComboBox<>();
        cargarCuentas();
        comboCuentas.addActionListener(e -> cargarLibroMayor());

        JPanel panelTop = new JPanel();
        panelTop.add(new JLabel("Cuenta:"));
        panelTop.add(comboCuentas);

        add(panelTop, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void cargarCuentas(){
        // Aquí deberías cargar desde la base de datos las cuentas
        // Ejemplo simple:
        comboCuentas.addItem("1 - Caja");
        comboCuentas.addItem("2 - Banco");
        comboCuentas.addItem("3 - Ventas");
    }

    private void cargarLibroMayor(){
        model.setRowCount(0);
        if(comboCuentas.getSelectedItem() == null) return;

        int idCuenta = Integer.parseInt(comboCuentas.getSelectedItem().toString().split(" - ")[0]);
        List<LibroMayor> movimientos = controller.obtenerLibroMayor(idCuenta);

        for(LibroMayor lm : movimientos){
            double debe = lm.getIdTipoMovimiento()== 1 ? lm.getSaldoAcumulado(): 0;
            double haber = lm.getIdTipoMovimiento() == 2 ? lm.getSaldoAcumulado() : 0;
            model.addRow(new Object[]{lm.getFechaAsiento(), lm.getDescripcion(), debe, haber});
        }
    }
}
