package business.entities;

import java.io.Serializable;

import util.ApplicationException;

public class Personaje extends BusinessEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final int PUNTOS_INICIALES = 200, MAX_DEFENSA = 20, MAX_EVASION = 80;
	private String nombre;
	private int puntosTotales, vida, energia, defensa, evasion;

	// a utilizar en el combate
	private int usoEnergia, danio;

	public Personaje() {
		this.setPuntosTotales(PUNTOS_INICIALES);
		setState(States.UNMODIFIED);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}

	public int getEnergia() {
		return energia;
	}

	public void setEnergia(int energia) {
		this.energia = energia;
	}

	public int getDefensa() {
		return defensa;
	}

	public void setDefensa(int defensa) {
		this.defensa = defensa;
	}

	public int getEvasion() {
		return evasion;
	}

	public void setEvasion(int evasion) {
		this.evasion = evasion;
	}

	public int getPuntosTotales() {
		return puntosTotales;
	}

	@Override
	public boolean equals(Object per) {
		return per instanceof Personaje && ((Personaje)per).getId() == this.getId();
	}

	boolean recibirAtaque(int energia) {
		// evade el ataque si (numAleatorio * 100) < puntosDeEvasion
		// no lo evade cuando (numAleatorio * 100) >= puntosDeEvasion
		boolean recibeAtaque = Math.random() * 100 >= getEvasion();
		if (recibeAtaque) {
			recibirDanio(energia);
		}
		return recibeAtaque;
	}

	public boolean atacar(Personaje oponente, int energia) throws ApplicationException {
		if (energia > getEnergiaActual()) {
			throw new ApplicationException("Energía insuficiente para realizar el ataque");
		}
		usarEnergia(energia);
		return oponente.recibirAtaque(energia);
	}

	public void defender() {
	    int energiaARecuperar = getEnergia() * getDefensa() / 100;
	    int vidaARecuperar = getVida() * getDefensa() / 250;
	    setUsoEnergia(getUsoEnergia() - energiaARecuperar);
    	setDanio(getDanio() - vidaARecuperar);
    	if (getEnergiaActual() > getEnergia()) {
    		setUsoEnergia(0);
    	}
    	if (getVidaActual() > getVida()) {
    		setDanio(0);
    	}
	}
		
	public void setPuntosTotales(int puntosTotales) {
		this.puntosTotales = puntosTotales;
	}

	private void usarEnergia(int energiaUtilizar) {
		setUsoEnergia(getUsoEnergia() + energiaUtilizar);
	}

	private void recibirDanio(int energia) {
		setDanio(getDanio() + energia);
	}

	public int getUsoEnergia() {
		return usoEnergia;
	}

	public void setUsoEnergia(int usoEnergia) {
		this.usoEnergia = usoEnergia;
	}

	public int getDanio() {
		return danio;
	}

	public void setDanio(int danio) {
		this.danio = danio;
	}

	public int getEnergiaActual() {
		return energia - usoEnergia;
	}

	public int getVidaActual() {
		return vida - danio;
	}
}
