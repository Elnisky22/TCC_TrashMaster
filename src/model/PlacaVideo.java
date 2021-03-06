package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe modeladora do objeto PlacaVideo.
 * @author Leonardo Elnisky.
 */
public class PlacaVideo {
	private IntegerProperty placaId = new SimpleIntegerProperty();
	private IntegerProperty gabineteId = new SimpleIntegerProperty();
	private IntegerProperty notebookId = new SimpleIntegerProperty();
	private StringProperty marca = new SimpleStringProperty();
	private StringProperty modelo = new SimpleStringProperty();
	private StringProperty doador = new SimpleStringProperty();
	
	/**
	 * M�todo para setar o ID da PlacaVideo.
	 * @param placaId
	 */
	public void setPlacaId(int placaId) {
		this.placaId.set(placaId);
	}
	
	/**
	 * M�todo para pegar o ID da PlacaVideo.
	 * @return placaId
	 */
	public int getPlacaId() {
		return placaId.get();
	}
	
	/**
	 * M�todo para setar o ID do Gabinete da PlacaVideo.
	 * @param gabineteId
	 */
	public void setGabineteId(int gabineteId) {
		this.gabineteId.set(gabineteId);
	}
	
	/**
	 * M�todo para pegar o ID do Gabinete da PlacaVideo.
	 * @return gabineteId
	 */
	public int getGabineteId() {
		return gabineteId.get();
	}
	
	/**
	 * M�todo para setar o ID do Notebook da PlacaVideo.
	 * @param notebookId
	 */
	public void setNotebookId(int notebookId) {
		this.notebookId.set(notebookId);
	}
	
	/**
	 * M�todo para pegar o ID do Notebook da PlacaVideo.
	 * @return notebookId
	 */
	public int getNotebookId() {
		return notebookId.get();
	}
	
	/**
	 * M�todo para setar a marca da PlacaVideo.
	 * @param marca
	 */
	public void setMarca(String marca) {
		this.marca.set(marca);
	}
	
	/**
	 * M�todo para pegar a marca da PlacaVideo.
	 * @return marca
	 */
	public String getMarca() {
		return marca.get();
	}
	
	/**
	 * M�todo para setar o modelo da PlacaVideo.
	 * @param modelo
	 */
	public void setModelo(String modelo) {
		this.modelo.set(modelo);
	}
	
	/**
	 * M�todo para pegar o modelo da PlacaVideo.
	 * @return modelo
	 */
	public String getModelo() {
		return modelo.get();
	}
	
	/**
	 * M�todo para setar o doador da PlacaVideo.
	 * @param doador
	 */
	public void setDoador(String doador){
	    this.doador.set(doador);
	}
	
	/**
	 * M�todo para pegar o doador da PlacaVideo.
	 * @return doador
	 */
	public String getDoador(){
	    return doador.get();
	}
}
