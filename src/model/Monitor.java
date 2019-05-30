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
	 * M�todo para setar o ID do Monitor.
	 * @param monitorId
	 */
	public void setMonitorId(int monitorId) {
		this.monitorId.set(monitorId);
	}
	
	/**
	 * M�todo para pegar o ID do Monitor.
	 * @return monitorId
	 */
	public int getMonitorId() {
		return monitorId.get();
	}

	/**
	 * M�todo para setar a marca do Monitor.
	 * @param marca
	 */
	public void setMarca(String marca) {
		this.marca.set(marca);
	}
	
	/**
	 * M�todo para pegar a marca do Monitor.
	 * @return marca
	 */
	public String getMarca() {
		return marca.get();
	}
	
	/**
	 * M�todo para setar o modelo do Monitor.
	 * @param modelo
	 */
	public void setModelo(String modelo) {
		this.modelo.set(modelo);
	}
	
	/**
	 * M�todo para pegar o modelo do Monitor.
	 * @return modelo
	 */
	public String getModelo() {
		return modelo.get();
	}

	/**
	 * M�todo para setar o doador do Monitor.
	 * @param doador
	 */
	public void setDoador(String doador){
	    this.doador.set(doador);
	}
	
	/**
	 * M�todo para pegar o doador do Monitor.
	 * @return doador
	 */
	public String getDoador(){
	    return doador.get();
	}
}
