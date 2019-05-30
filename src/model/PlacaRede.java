package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe modeladora do objeto PlacaRede.
 * @author Leonardo Elnisky.
 */
public class PlacaRede {
	private IntegerProperty placaId = new SimpleIntegerProperty();
	private IntegerProperty gabineteId = new SimpleIntegerProperty();
	private IntegerProperty notebookId = new SimpleIntegerProperty();
	private StringProperty marca = new SimpleStringProperty();
	private StringProperty modelo = new SimpleStringProperty();
	private BooleanProperty wifi = new SimpleBooleanProperty();
	private StringProperty doador = new SimpleStringProperty();
	
	/**
	 * Método para setar o ID da PlacaRede.
	 * @param placaId
	 */
	public void setPlacaId(int placaId) {
		this.placaId.set(placaId);
	}
	
	/**
	 * Método para pegar o ID da PlacaRede.
	 * @return placaId
	 */
	public int getPlacaId() {
		return placaId.get();
	}
	
	/**
	 * Método para setar o ID do Gabinete da PlacaRede.
	 * @param gabineteId
	 */
	public void setGabineteId(int gabineteId) {
		this.gabineteId.set(gabineteId);
	}
	
	/**
	 * Método para pegar o ID do Gabinete da PlacaRede.
	 * @return gabineteId
	 */
	public int getGabineteId() {
		return gabineteId.get();
	}
	
	/**
	 * Método para setar o ID do Notebook da PlacaRede.
	 * @param notebookId
	 */
	public void setNotebookId(int notebookId) {
		this.notebookId.set(notebookId);
	}
	
	/**
	 * Método para pegar o ID do Notebook da PlacaRede.
	 * @return notebookId
	 */
	public int getNotebookId() {
		return notebookId.get();
	}
	
	/**
	 * Método para setar a marca da PlacaRede.
	 * @param marca
	 */
	public void setMarca(String marca) {
		this.marca.set(marca);
	}
	
	/**
	 * Método para pegar a marca da PlacaRede.
	 * @return marca
	 */
	public String getMarca() {
		return marca.get();
	}
	
	/**
	 * Método para setar o modelo da PlacaRede.
	 * @param modelo
	 */
	public void setModelo(String modelo) {
		this.modelo.set(modelo);
	}
	
	/**
	 * Método para pegar o modelo da PlacaRede.
	 * @return modelo
	 */
	public String getModelo() {
		return modelo.get();
	}
	
	/**
	 * Método para setar se há Wifi na PlacaRede.
	 * @param wifi
	 */
	public void setWifi(Boolean wifi) {
		this.wifi.set(wifi);
	}
	
	/**
	 * Método para pegar se há Wifi na PlacaRede.
	 * @return wifi
	 */
	public Boolean getWifi() {
		return wifi.getValue();
	}
	
	/**
	 * Método para setar o doador da PlacaRede.
	 * @param doador
	 */
	public void setDoador(String doador){
	    this.doador.set(doador);
	}
	
	/**
	 * Método para pegar o doador da PlacaRede.
	 * @return doador
	 */
	public String getDoador(){
	    return doador.get();
	}
}
