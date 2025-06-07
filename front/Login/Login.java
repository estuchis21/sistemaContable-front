package sistemacontable.sistemaContable.front.Login;

import javax.swing.*;

public class Login extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;

    public Login() {
        setTitle("Login");
        setSize(350, 250);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar ventana

        // Campos de texto
        txtUsername = new JTextField(); txtUsername.setBounds(130, 40, 170, 25);
        txtContrasena = new JPasswordField(); txtContrasena.setBounds(130, 80, 170, 25);

        // Botón
        btnIngresar = new JButton("Ingresar"); btnIngresar.setBounds(130, 130, 100, 30);

        // Etiquetas
        JLabel lblUsuario = new JLabel("Username:"); lblUsuario.setBounds(30, 40, 100, 25);
        JLabel lblContrasena = new JLabel("Contraseña:"); lblContrasena.setBounds(30, 80, 100, 25);

        // Agregar componentes
        add(lblUsuario); add(txtUsername);
        add(lblContrasena); add(txtContrasena);
        add(btnIngresar);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Login();
    }

}