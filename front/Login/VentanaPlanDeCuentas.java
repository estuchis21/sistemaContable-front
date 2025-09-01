/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemacontable.sistemaContable.front.Login;

/**
 *
 * @author Esteban
 */


import sistemacontable.back.ConnectionDB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class VentanaPlanDeCuentas extends JFrame {

    private JTable tablaCuentas;
    private DefaultTableModel model;

    public VentanaPlanDeCuentas() {
        setTitle("Plan de Cuentas");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        model = new DefaultTableModel(new Object[]{"ID Cuenta", "Cuenta", "Tipo"}, 0);
        tablaCuentas = new JTable(model);
        JScrollPane scroll = new JScrollPane(tablaCuentas);
        add(scroll, BorderLayout.CENTER);

        cargarCuentas();
        setVisible(true);
    }

    private void cargarCuentas() {
        String sql = "SELECT id_cuenta, cuenta, id_tipo_saldo FROM Cuentas";
        try (Connection conn = ConnectionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id_cuenta"),
                        rs.getString("cuenta"),
                        rs.getString("id_tipo_saldo")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
