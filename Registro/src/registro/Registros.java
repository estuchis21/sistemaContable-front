package registro;

import javax.swing.*;

public class Registros extends JFrame {
    private JTextField txtUsername, txtNombres, txtApellido, txtDNI;
    private JPasswordField txtContrasena;
    private JComboBox<String> comboRol;
    private JButton btnRegistrar;

    public Registros() {
        setTitle("Registro de Usuario");
        setSize(400, 400);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar ventana

        // Campos de entrada
        txtUsername = new JTextField(); txtUsername.setBounds(150, 30, 200, 25);
        txtContrasena = new JPasswordField(); txtContrasena.setBounds(150, 70, 200, 25);
        txtNombres = new JTextField(); txtNombres.setBounds(150, 110, 200, 25);
        txtApellido = new JTextField(); txtApellido.setBounds(150, 150, 200, 25);
        txtDNI = new JTextField(); txtDNI.setBounds(150, 190, 200, 25);
        comboRol = new JComboBox<>(new String[]{"Administrador", "Contador", "Usuario Corriente"});
        comboRol.setBounds(150, 230, 200, 25);

        // Botón
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(150, 280, 100, 30);

        // Etiquetas
        JLabel lblUsername = new JLabel("Username:"); lblUsername.setBounds(30, 30, 100, 25);
        JLabel lblContrasena = new JLabel("Contraseña:"); lblContrasena.setBounds(30, 70, 100, 25);
        JLabel lblNombres = new JLabel("Nombres:"); lblNombres.setBounds(30, 110, 100, 25);
        JLabel lblApellido = new JLabel("Apellido:"); lblApellido.setBounds(30, 150, 100, 25);
        JLabel lblDNI = new JLabel("DNI:"); lblDNI.setBounds(30, 190, 100, 25);
        JLabel lblRol = new JLabel("Rol:"); lblRol.setBounds(30, 230, 100, 25);

        // Agregar componentes
        add(lblUsername); add(txtUsername);
        add(lblContrasena); add(txtContrasena);
        add(lblNombres); add(txtNombres);
        add(lblApellido); add(txtApellido);
        add(lblDNI); add(txtDNI);
        add(lblRol); add(comboRol);
        add(btnRegistrar);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Registros();
    }
}

