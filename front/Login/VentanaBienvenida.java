package sistemacontable.sistemaContable.front.Login;

import javax.swing.*;
import java.awt.*;

public class VentanaBienvenida extends JFrame {
    private int idUsuario;
    private String nombres;
    private int idRol;

    public VentanaBienvenida(){}

    public VentanaBienvenida(int idUsuario, String nombres, int idRol) {
        this.idUsuario = idUsuario;
        this.nombres = nombres;
        this.idRol = idRol;

        setTitle("Bienvenido");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        String rolTexto = (idRol == 1) ? "Administrador" :
                          (idRol == 2) ? "Contador" :
                          (idRol == 3) ? "Usuario Corriente" : "Desconocido";

        JLabel lblBienvenida = new JLabel("¡Bienvenido/a, " + nombres + " (" + rolTexto + ")!");
        lblBienvenida.setHorizontalAlignment(SwingConstants.CENTER);

        // Botones
        JButton btnPlanCuentas = new JButton("Plan de Cuentas");
        JButton btnLibroDiario = new JButton("Libro Diario");
        JButton btnLibroMayor = new JButton("Libro Mayor");

        // Eventos con paso de parámetros del usuario
        btnPlanCuentas.addActionListener(e -> abrirVentanaPlanDeCuentas());
        btnLibroDiario.addActionListener(e -> abrirVentanaLibroDiario());
        btnLibroMayor.addActionListener(e -> abrirVentanaLibroMayor());

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(3, 1, 10, 10));
        panelBotones.add(btnPlanCuentas);
        panelBotones.add(btnLibroDiario);
        panelBotones.add(btnLibroMayor);

        // Panel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(lblBienvenida, BorderLayout.NORTH);
        panel.add(panelBotones, BorderLayout.CENTER);

        add(panel);
    }

    private void abrirVentanaPlanDeCuentas() {
        // Pasamos los datos del usuario
        VentanaPlanDeCuentas ventana = new VentanaPlanDeCuentas();
        ventana.setVisible(true);
        dispose();
    }

    private void abrirVentanaLibroDiario() {
        // Pasamos los datos del usuario
        VentanaLibroDiario ventana = new VentanaLibroDiario(idUsuario, nombres, idRol);
        ventana.setVisible(true);
        dispose();
    }

    private void abrirVentanaLibroMayor() {
        // Pasamos los datos del usuario
        VentanaLibroMayor ventana = new VentanaLibroMayor();
        ventana.setVisible(true);
        dispose();
    }
}
