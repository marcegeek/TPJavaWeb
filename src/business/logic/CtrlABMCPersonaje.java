package business.logic;

import java.util.List;

import util.ApplicationException;
import business.entities.Personaje;
import data.DataPersonaje;

public class CtrlABMCPersonaje {
	private DataPersonaje dataPer;

	public CtrlABMCPersonaje() {
		dataPer = new DataPersonaje();
	}

	public void save(Personaje per) throws ApplicationException {
		if (!validarPersonaje(per)) {
			throw new ApplicationException("Los datos del personaje no son válidos");
		}
		dataPer.save(per);
	}

	public List<Personaje> getAll() throws ApplicationException {
		return dataPer.getAll();
	}

	public Personaje getByCod(Personaje per) throws ApplicationException {
		return dataPer.getById(per);
	}

	public Personaje getByNombre(Personaje per) throws ApplicationException {
		return dataPer.getByNombre(per);
	}

	private boolean validarPersonaje(Personaje per) {
		return per.getDefensa() >= 0 && per.getDefensa() <= Personaje.MAX_DEFENSA &&
				per.getEvasion() >= 0 && per.getEvasion() <= Personaje.MAX_EVASION &&
				per.getVida() + per.getDefensa() + per.getEnergia() + per.getEvasion() <= per.getPuntosTotales();
	}
}
