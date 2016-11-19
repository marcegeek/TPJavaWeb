package business.entities;

import java.io.Serializable;
import java.util.Date;

public class Combate extends BusinessEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private Personaje ganador, perdedor;
	private int puntos;
	private Date fechaHora;

	public Personaje getGanador() {
		return ganador;
	}

	public void setGanador(Personaje ganador) {
		this.ganador = ganador;
	}

	public Personaje getPerdedor() {
		return perdedor;
	}

	public void setPerdedor(Personaje perdedor) {
		this.perdedor = perdedor;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}
}
