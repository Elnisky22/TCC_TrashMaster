package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe modeladora do objeto Monitor.
 * @author Leonardo Elnisky.
 */
public class Monitor {
	private IntegerProperty monitorId = new SimpleIntegerProperty();
	private StringProperty marca = new SimpleStringProperty();
	private StringProperty modelo = new SimpleStringProperty();
	private StringProperty doador = new SimpleStringProperty();

	/**
	 * Método para setar o ID do Monitor.
	 * @param monitorId
	 */
	public void setMonitorId(int monitorId) {
		this.monitorId.set(monitorId);
	}
	
	/**
	 * Método para pegar o ID do Monitor.
	 * @return monitorId
	 */
	public int getMonitorId() {
		return monitorId.get();
	}

	/**
	 * Método para setar a marca do Monitor.
	 * @param marca
	 */
	public void setMarca(String marca) {
		this.marca.set(marca);
	}
	
	/**
	 * Método para pegar a marca do Monitor.
	 * @return marca
	 */
	public String getMarca() {
		return marca.get();
	}
	
	/**
	 * Método para setar o modelo do Monitor.
	 * @param modelo
	 */
	public void setModelo(String modelo) {
		this.modelo.set(modelo);
	}
	
	/**
	 * Método para pegar o modelo do Monitor.
	 * @return modelo
	 */
	public String getModelo() {
		return modelo.get();
	}

	/**
	 * Método para setar o doador do Monitor.
	 * @param doador
	 */
	public void setDoador(String doador){
	    this.doador.set(doador);
	}
	
	/**
	 * Método para pegar o doador do Monitor.
	 * @return doador
	 */
	public String getDoador(){
	    return doador.get();
	}
}
