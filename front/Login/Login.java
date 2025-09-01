package sistemacontable.sistemaContable.front.Login;

import sistemacontable.back.Models.Usuario;
import sistemacontable.sistemaContable.front.Login.Controllers.LoginController;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;

    private final LoginController controller;

    public Login() {
        setTitle("Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        controller = new LoginController();

        txtUsername = new JTextField(15);
        txtContrasena = new JPasswordField(15);
        btnIngresar = new JButton("Ingresar");

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Usuario:"), gbc);
        gbc.gridx = 1;
        panel.add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1;
        panel.add(txtContrasena, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(btnIngresar, gbc);

        add(panel);

        btnIngresar.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtContrasena.getPassword());

            if(username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese usuario y contraseña.");
                return;
            }

            Usuario usuarioLogueado = controller.login(username, password);
            if(usuarioLogueado != null) {
                // Abrimos ventana de bienvenida
                VentanaBienvenida bienv = new VentanaBienvenida(
                        usuarioLogueado.getId_usuario(),
                        usuarioLogueado.getNombres(),
                        usuarioLogueado.getIdRol()
                );
                bienv.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
}
