package sistemacontable.sistemaContable.front.Login.Controllers;

import java.util.List;
import sistemacontable.back.Models.LibroMayor;

public class LibroMayorController {

    // Usamos el nombre completo del servicio para evitar conflicto
    private final sistemacontable.back.Services.LibroMayor libroMayorService = new sistemacontable.back.Services.LibroMayor();

    public List<LibroMayor> obtenerLibroMayor(int idCuenta) {
        return libroMayorService.getLibroMayor(idCuenta);
    }

    public boolean cuentaExiste(int id_cuenta) {
        return libroMayorService.existeLaCuenta(id_cuenta);
    }
}
