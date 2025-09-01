package sistemacontable.sistemaContable.front.Login.Controllers;

import sistemacontable.back.Models.Cuentas;
import sistemacontable.back.Services.PlanDeCuentasService;

public class PlanDeCuentasController {

    private final PlanDeCuentasService planService;
    
    
    public PlanDeCuentasController() {
        this.planService = new PlanDeCuentasService();
    }

    public boolean insertarCuenta(Cuentas cuenta) {
        return planService.insertarCuenta(cuenta);
    }

    public Integer obtenerCuentaPorId(int idCuenta) {
        return planService.mostrarCuentaPorId(idCuenta);
    }
}
