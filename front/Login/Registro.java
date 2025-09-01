package sistemacontable.sistemaContable.front.Login;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.*;
import sistemacontable.back.Models.Usuario;
import sistemacontable.sistemaContable.front.Login.Controllers.LoginController;

public class Registro extends JFrame {
    private JTextField txtUsername, txtNombres, txtApellido, txtDNI, txtMail;
    private JPasswordField txtContrasena;
    private JComboBox<String> comboRol;
    private JButton btnRegistrar, btnVolverLogin;

    private LoginController registroController;

    public Registro() {
        setTitle("Registro de Usuario");
        setSize(500, 550);
        setMinimumSize(new Dimension(400, 500));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBackground(new Color(0, 0, 128));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(204, 0, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtUsername = new JTextField(15);
        txtMail = new JTextField(15);
        txtContrasena = new JPasswordField(15);
        txtNombres = new JTextField(15);
        txtApellido = new JTextField(15);
        txtDNI = new JTextField(15);

        comboRol = new JComboBox<>(new String[]{"Administrador", "Contador", "Usuario corriente"});
        btnRegistrar = new JButton("Registrar");
        btnVolverLogin = new JButton("Volver al Login");

        int y = 0;
        formPanel.add(new JLabel("Username:") {{ setForeground(Color.WHITE); }}, gbc); gbc.gridx=1; formPanel.add(txtUsername, gbc); gbc.gridx=0; gbc.gridy=++y;
        formPanel.add(new JLabel("Email:") {{ setForeground(Color.WHITE); }}, gbc); gbc.gridx=1; formPanel.add(txtMail, gbc); gbc.gridx=0; gbc.gridy=++y;
        formPanel.add(new JLabel("Contraseña:") {{ setForeground(Color.WHITE); }}, gbc); gbc.gridx=1; formPanel.add(txtContrasena, gbc); gbc.gridx=0; gbc.gridy=++y;
        formPanel.add(new JLabel("Nombres:") {{ setForeground(Color.WHITE); }}, gbc); gbc.gridx=1; formPanel.add(txtNombres, gbc); gbc.gridx=0; gbc.gridy=++y;
        formPanel.add(new JLabel("Apellido:") {{ setForeground(Color.WHITE); }}, gbc); gbc.gridx=1; formPanel.add(txtApellido, gbc); gbc.gridx=0; gbc.gridy=++y;
        formPanel.add(new JLabel("DNI:") {{ setForeground(Color.WHITE); }}, gbc); gbc.gridx=1; formPanel.add(txtDNI, gbc); gbc.gridx=0; gbc.gridy=++y;
        formPanel.add(new JLabel("Rol:") {{ setForeground(Color.WHITE); }}, gbc); gbc.gridx=1; formPanel.add(comboRol, gbc); gbc.gridx=0; gbc.gridy=++y;

        gbc.gridwidth=2; gbc.anchor=GridBagConstraints.CENTER; gbc.gridx=0; formPanel.add(btnRegistrar, gbc); gbc.gridy=++y;
        formPanel.add(btnVolverLogin, gbc);

        backgroundPanel.add(formPanel);
        setContentPane(backgroundPanel);

        registroController = new LoginController();

        btnRegistrar.addActionListener(e -> {
            try {
                String username = txtUsername.getText().trim();
                String mail = txtMail.getText().trim();
                String contrasena = new String(txtContrasena.getPassword());
                String nombres = txtNombres.getText().trim();
                String apellido = txtApellido.getText().trim();
                int dni = Integer.parseInt(txtDNI.getText().trim());
                int idRol = comboRol.getSelectedIndex() + 1;

                Usuario usuario = new Usuario();
                usuario.setUsername(username);
                usuario.setMail(mail);
                usuario.setContrasena(contrasena);
                usuario.setNombres(nombres);
                usuario.setApellido(apellido);
                usuario.setDni(dni);
                usuario.setIdRol(idRol);

                boolean exito = registroController.registro(usuario);
                if (exito) mostrarMensaje("Usuario registrado correctamente.");
                else mostrarMensaje("No se pudo registrar. Verifique datos o si ya existe.");

            } catch (NumberFormatException ex) {
                mostrarMensaje("DNI debe ser un número válido.");
            }
        });

        btnVolverLogin.addActionListener(e -> {
            new Login();
            dispose();
        });

        setVisible(true);
        SwingUtilities.invokeLater(() -> txtUsername.requestFocusInWindow());
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Registro::new);
    }
}
