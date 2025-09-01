package sistemacontable.sistemaContable.front.Login.Controllers;

import sistemacontable.back.Models.Usuario;
import sistemacontable.back.Services.Auth;
import sistemacontable.sistemaContable.front.Login.Login;

public class LoginController {
    private final Auth userService = new Auth();
    private Login login;

    public LoginController() {}

    public LoginController(Login login) {
        this.login = login;
    }

    public boolean registro(Usuario usuario) {
        try {
            boolean exito = userService.registrar(usuario);
            if (exito) {
                System.out.println("Bienvenido: " + usuario.getNombres());
                return true;
            } else {
                System.err.println("No se pudo registrar. Ya existe usuario.");
                return false;
            }
        } catch(Exception e) {
            System.err.println("Error en registro: " + e.getMessage());
            return false;
        }
    }

    public Usuario login(String username, String contrasena) {
        try {
            Usuario user = userService.login(username, contrasena);
            if (user != null) {
                System.out.println("Login exitoso: " + user.getNombres());
                return user;
            } else {
                System.err.println("Usuario o contraseña incorrectos");
                return null;
            }
        } catch(Exception e) {
            System.err.println("Error en login: " + e.getMessage());
            return null;
        }
    }
}
