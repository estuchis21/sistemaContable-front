package sistemacontable.sistemaContable.front.Login.Controllers;

import sistemacontable.back.Models.Asientos;
import sistemacontable.back.Models.Operaciones;
import sistemacontable.back.Services.LibroDiario;

import java.math.BigDecimal;

public class LibroDiarioController {

    private final LibroDiario service = new LibroDiario();

    public boolean generarAsiento(Asientos asiento) {
        return service.generarAsiento(asiento);
    }

    public boolean generarOperacion(Operaciones op) {
        return service.generarOperacionPorAsiento(op);
    }
}
