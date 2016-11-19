package business.logic;

import data.DataCombate;
import data.DataPersonaje;
import util.ApplicationException;
import business.entities.BusinessEntity.States;
import business.entities.Combate;
import business.entities.Personaje;

public class CtrlCombate {
	private Combate combate;
	private DataCombate dataComb;
	private Personaje pers1, pers2;
	private Personaje turno;
	private Personaje ganador;
	private boolean combateFinalizado;
	private String sucesosCombate = "";

	public Personaje getPers1() {
		return pers1;
	}

	public void setPers1(Personaje pers1) {
		this.pers1 = pers1;
	}

	public Personaje getPers2() {
		return pers2;
	}

	public void setPers2(Personaje pers2) {
		this.pers2 = pers2;
	}

	public CtrlCombate(Personaje pers1, Personaje pers2) throws ApplicationException {
		dataComb = new DataCombate();
		combateFinalizado = false;
		if (pers1 == null || pers2 == null) {
			throw new ApplicationException("Se requieren dos personajes");
		}
		if (pers1.equals(pers2)) {
			throw new ApplicationException("Los personajes deben ser distintos");
		}
		if (pers1.getVida() <= 0 || pers2.getVida() <= 0 ||
				pers1.getEnergia() <= 0 || pers2.getEnergia() <= 0) {
			throw new ApplicationException("Los personajes deben tener vida y energía suficiente");
		}
		this.pers1 = pers1;
		this.pers2 = pers2;
		this.pers1.setUsoEnergia(0);
		this.pers2.setUsoEnergia(0);
		this.pers1.setDanio(0);
		this.pers2.setDanio(0);
		if (Math.random() < 0.5) {
			turno = this.pers1;
		}
		else {
			turno = this.pers2;
		}
	}

	public Personaje getTurno() {
		return turno;
	}

	public Personaje getOponente() {
		if (turno == pers1) {
			return pers2;
		}
		else {
			return pers1;
		}
	}

	public boolean atacar(int energia) throws ApplicationException {
		Personaje per = getTurno();
		Personaje oponente = getOponente();
		int vidaAnterior = oponente.getVidaActual();
		boolean atacado = per.atacar(oponente,energia);
		if (atacado) {
			sucesosCombate += per.getNombre() + " quitó " + (vidaAnterior - oponente.getVidaActual()) +
					" puntos de vida a " + oponente.getNombre() + "\n";
		}
		else {
			sucesosCombate += oponente.getNombre() + " evadió el ataque de " + per.getNombre() + "\n";
		}
		if (oponente.getVidaActual() <= 0) {
			finalizarCombate();
		}
		else {
			cambiarTurno();
		}
		return atacado;
	}

	public Combate getCombate() {
		return combate;
	}

	public void setCombate(Combate combate) {
		this.combate = combate;
	}

	private void finalizarCombate() throws ApplicationException {
		ganador = getTurno();
		combate = new Combate();
		combate.setState(States.NEW);
		combate.setGanador(getTurno());
		combate.setPerdedor(getOponente());
		combate.setPuntos(10);
		dataComb.save(combate);
		ganador.setPuntosTotales(new DataPersonaje().getById(ganador).getPuntosTotales());
		combateFinalizado = true;
	}

	private void cambiarTurno() {
		turno = getOponente();
	}

	public void defender() {
		int vidaAnterior = getTurno().getVidaActual();
		int energiaAnterior = getTurno().getEnergiaActual();
		getTurno().defender();
		sucesosCombate += getTurno().getNombre() + " recuperó " + (getTurno().getVidaActual() - vidaAnterior) +
				" puntos de vida y " + (getTurno().getEnergia() - energiaAnterior) + " de energía\n";
		cambiarTurno();
	}

	public boolean isCombateFinalizado() {
		return combateFinalizado;
	}

	public DataCombate getDataComb() {
		return dataComb;
	}

	public void setDataComb(DataCombate dataComb) {
		this.dataComb = dataComb;
	}

	public void setTurno(Personaje turno) {
		this.turno = turno;
	}

	public void setGanador(Personaje ganador) {
		this.ganador = ganador;
	}

	public void setCombateFinalizado(boolean combateFinalizado) {
		this.combateFinalizado = combateFinalizado;
	}

	public void setSucesosCombate(String sucesosCombate) {
		this.sucesosCombate = sucesosCombate;
	}

	public Personaje getGanador() {
		return ganador;
	}

	public String getSucesosCombate() {
		return sucesosCombate;
	}
}
