package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe modeladora do objeto PlacaMae.
 * @author Leonardo Elnisky.
 */
public class PlacaMae {
	private IntegerProperty placaId = new SimpleIntegerProperty();
	private IntegerProperty gabineteId = new SimpleIntegerProperty();
	private IntegerProperty notebookId = new SimpleIntegerProperty();
	private StringProperty marca = new SimpleStringProperty();
	private StringProperty modelo = new SimpleStringProperty();
	private StringProperty socket = new SimpleStringProperty();
	private StringProperty tipoRam = new SimpleStringProperty();
	private StringProperty doador = new SimpleStringProperty();
	
	/**
	 * M�todo para setar o ID da PlacaMae.
	 * @param placaId
	 */
	public void setPlacaId(int placaId) {
		this.placaId.set(placaId);
	}
	
	/**
	 * M�todo para pegar o ID da PlacaMae.
	 * @return placaId
	 */
	public int getPlacaId() {
		return placaId.get();
	}
	
	/**
	 * M�todo para setar o ID do Gabiente da PlacaMae.
	 * @param gabineteId
	 */
	public void setGabineteId(int gabineteId) {
		this.gabineteId.set(gabineteId);
	}
	
	/**
	 * M�todo para pegar o ID do Gabiente da PlacaMae.
	 * @return gabineteId
	 */
	public int getGabineteId() {
		return gabineteId.get();
	}
	
	/**
	 * M�todo para setar o ID do Notebook da PlacaMae.
	 * @param notebookId
	 */
	public void setNotebookId(int notebookId) {
		this.notebookId.set(notebookId);
	}
	
	/**
	 * M�todo para pegar o ID do Notebook da PlacaMae.
	 * @return notebookId
	 */
	public int getNotebookId() {
		return notebookId.get();
	}
	
	/**
	 * M�todo para setar a marca da PlacaMae.
	 * @param marca
	 */
	public void setMarca(String marca) {
		this.marca.set(marca);
	}
	
	/**
	 * M�todo para pegar a marca da PlacaMae.
	 * @return marca
	 */
	public String getMarca() {
		return marca.get();
	}
	
	/**
	 * M�todo para setar o modelo da PlacaMae.
	 * @param modelo
	 */
	public void setModelo(String modelo) {
		this.modelo.set(modelo);
	}
	
	/**
	 * M�todo para pegar o modelo da PlacaMae.
	 * @return modelo
	 */
	public String getModelo() {
		return modelo.get();
	}
	
	/**
	 * M�todo para setar o socket da PlacaMae.
	 * @param socket
	 */
	public void setSocket(String socket) {
		this.socket.set(socket);
	}
	
	/**
	 * M�todo para pegar o socket da PlacaMae.
	 * @return
	 */
	public String getSocket() {
		return socket.get();
	}
	
	/**
	 * M�todo para setar o tipo de RAM da PlacaMae.
	 * @param ram
	 */
	public void setRam(String ram) {
		this.tipoRam.set(ram);
	}
	
	/**
	 * M�todo para pegar o tipo de RAM da PlacaMae.
	 * @return tipoRam
	 */
	public String getRam() {
		return tipoRam.get();
	}
	
	/**
	 * M�todo para setar o doador da PlacaMae.
	 * @param doador
	 */
	public void setDoador(String doador){
	    this.doador.set(doador);
	}
	
	/**
	 * M�todo para pegar o doador da PlacaMae.
	 * @return doador
	 */
	public String getDoador(){
	    return doador.get();
	}
}
