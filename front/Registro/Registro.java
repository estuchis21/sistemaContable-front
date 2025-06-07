/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemacontable.sistemaContable.front.Registro;

import javax.swing.*;
import java.awt.*;

public class Registro extends JFrame {
    private JTextField txtUsername, txtNombres, txtApellido, txtDNI, txtMail;
    private JPasswordField txtContrasena;
    private JComboBox<String> comboRol;
    private JButton btnRegistrar;

    public Registro() {
        setTitle("Registro de Usuario");
        setSize(500, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel con GridBagLayout
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Componentes
        JLabel lblUsername = new JLabel("Username:");
        txtUsername = new JTextField();
        
        JLabel lblMail = new JLabel("Email:");
        txtMail = new JTextField();
        
        JLabel lblContrasena = new JLabel("Contraseña:");
        txtContrasena = new JPasswordField();

        JLabel lblNombres = new JLabel("Nombres:");
        txtNombres = new JTextField();

        JLabel lblApellido = new JLabel("Apellido:");
        txtApellido = new JTextField();

        JLabel lblDNI = new JLabel("DNI:");
        txtDNI = new JTextField();

        JLabel lblRol = new JLabel("Rol:");
        comboRol = new JComboBox<>(new String[]{"Administrador", "Contador", "Usuario Corriente"});

        btnRegistrar = new JButton("Registrar");

        // Agregar al panel
        int y = 0;

        gbc.gridx = 0; gbc.gridy = y; formPanel.add(lblUsername, gbc);
        gbc.gridx = 1; gbc.gridy = y++; formPanel.add(txtUsername, gbc);
        
        gbc.gridx = 0; gbc.gridy = y; formPanel.add(lblMail, gbc);
        gbc.gridx = 1; gbc.gridy = y++; formPanel.add(txtMail, gbc);

        gbc.gridx = 0; gbc.gridy = y; formPanel.add(lblContrasena, gbc);
        gbc.gridx = 1; gbc.gridy = y++; formPanel.add(txtContrasena, gbc);

        gbc.gridx = 0; gbc.gridy = y; formPanel.add(lblNombres, gbc);
        gbc.gridx = 1; gbc.gridy = y++; formPanel.add(txtNombres, gbc);

        gbc.gridx = 0; gbc.gridy = y; formPanel.add(lblApellido, gbc);
        gbc.gridx = 1; gbc.gridy = y++; formPanel.add(txtApellido, gbc);

        gbc.gridx = 0; gbc.gridy = y; formPanel.add(lblDNI, gbc);
        gbc.gridx = 1; gbc.gridy = y++; formPanel.add(txtDNI, gbc);

        gbc.gridx = 0; gbc.gridy = y; formPanel.add(lblRol, gbc);
        gbc.gridx = 1; gbc.gridy = y++; formPanel.add(comboRol, gbc);

        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(btnRegistrar, gbc);

        // Panel contenedor centrado
        JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.add(formPanel); // Centramos el panel con el formulario

        add(outerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Registro::new);
    }
}