package MiFecha;

/*******************************************************************************/
/**************************************MiFecha**********************************/
/*******************************************************************************/
/***Clase hecha por Francisco J. Caracuel para facilitar el uso de las fechas***/
/*******************************************************************************/

import java.io.File;
import java.io.Serializable;
import java.util.GregorianCalendar;

public class MiFecha implements Serializable {

	private static final long serialVersionUID = 9028279135809056561L;

	// GregorianCalendar no se puede guardar en DB4O por eso se pone transient y
	// se añade la variable millis
	private transient GregorianCalendar fecha;

	// Millis guardara en un string el tiempo en milisegundos
	private String tag, millis;

	public MiFecha() {
		this(new GregorianCalendar(), "", new GregorianCalendar()
				.getTimeInMillis() + "");
	}

	public MiFecha(GregorianCalendar fecha, String tag) {
		this(fecha, tag, "");
	}

	public MiFecha(GregorianCalendar fecha, String tag, String millis) {
		this.fecha = fecha;
		this.tag = tag;
		this.millis = millis;
	}

	public MiFecha(String millis) {
		fecha = new GregorianCalendar();
		fecha.setTimeInMillis(Long.parseLong(millis));
		this.millis = millis;
	}

	public String getDia() {

		if (fecha.get(GregorianCalendar.DAY_OF_MONTH) < 10) {
			return "0" + fecha.get(GregorianCalendar.DAY_OF_MONTH);
		} else {
			return fecha.get(GregorianCalendar.DAY_OF_MONTH) + "";
		}

	}

	public int getDiaInt() {
		return fecha.get(GregorianCalendar.DAY_OF_MONTH);
	}

	public String getMes() {

		if ((fecha.get(GregorianCalendar.MONTH) + 1) < 10) {
			return "0" + (fecha.get(GregorianCalendar.MONTH) + 1);
		} else {
			return fecha.get(GregorianCalendar.MONTH) + 1 + "";
		}

	}

	public int getMesInt() {
		return fecha.get(GregorianCalendar.MONTH);
	}

	public String getAño() {
		return fecha.get(GregorianCalendar.YEAR) + "";
	}

	public String getHora() {

		if (fecha.get(GregorianCalendar.HOUR_OF_DAY) < 10) {
			return "0" + fecha.get(GregorianCalendar.HOUR_OF_DAY);
		} else {
			return fecha.get(GregorianCalendar.HOUR_OF_DAY) + "";
		}

	}

	public String getMinuto() {

		if (fecha.get(GregorianCalendar.MINUTE) < 10) {
			return "0" + fecha.get(GregorianCalendar.MINUTE);
		} else {
			return fecha.get(GregorianCalendar.MINUTE) + "";
		}

	}

	public String getSegundos() {

		if (fecha.get(GregorianCalendar.SECOND) < 10) {
			return "0" + fecha.get(GregorianCalendar.SECOND);
		} else {
			return fecha.get(GregorianCalendar.SECOND) + "";
		}

	}

	public String getFechaFormateada() {
		return getDia() + "/" + getMes() + "/" + getAño();
	}

	public String getHoraFormateada() {
		return getHora() + ":" + getMinuto() + ":" + getSegundos();
	}

	public String getFechaCompletaFormateada() {

		return getFechaFormateada() + "_" + getHoraFormateada();

	}

	public String getFechaFormateadaFromFile(File archivo) {

		fecha.setTimeInMillis(archivo.lastModified());

		return getFechaCompletaFormateada();

	}

	public long getTimeInMillis() {
		return fecha.getTimeInMillis();
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getMillisString() {
		return millis;
	}

	public void setMillisString(String millis) {
		this.millis = millis;
	}

	public int getDiferenciaEntreDias(MiFecha fechaAux) {

		long fechaMax = this.getTimeInMillis();
		long fechaMin = fechaAux.getTimeInMillis();

		if (fechaMax < fechaMin) {
			long aux = fechaMax;
			fechaMax = fechaMin;
			fechaMin = aux;
		}

		long dif = fechaMax - fechaMin;

		return (int) dif / (24 * 60 * 60 * 1000);

	}

}
