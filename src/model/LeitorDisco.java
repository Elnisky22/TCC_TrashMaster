package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe modeladora do objeto LeitorDisco.
 * @author Leonardo Elnisky.
 */
public class LeitorDisco {
	private IntegerProperty leitorId = new SimpleIntegerProperty();
	private IntegerProperty gabineteId = new SimpleIntegerProperty();
	private IntegerProperty notebookId = new SimpleIntegerProperty();
	private StringProperty marca = new SimpleStringProperty();
	private StringProperty modelo = new SimpleStringProperty();
	private StringProperty doador = new SimpleStringProperty();
	
	/**
	 * M�todo para setar o ID do LeitorDisco.
	 * @param leitorId
	 */
	public void setLeitorId(int leitorId) {
		this.leitorId.set(leitorId);
	}
	
	/**
	 * M�todo para pegar o ID do LeitorDisco.
	 * @return leitorId
	 */
	public int getLeitorId() {
		return leitorId.get();
	}
	
	/**
	 * M�todo para setar o ID do Gabinete do LeitorDisco. 
	 * @param gabineteId
	 */
	public void setGabineteId(int gabineteId) {
		this.gabineteId.set(gabineteId);
	}
	
	/**
	 * M�todo para pegar o ID do Gabinete do LeitorDisco.
	 * @return gabineteId
	 */
	public int getGabineteId() {
		return gabineteId.get();
	}
	
	/**
	 * M�todo para setar o ID do Notebook do LeitorDisco.
	 * @param notebookId
	 */
	public void setNotebookId(int notebookId) {
		this.notebookId.set(notebookId);
	}
	
	/**
	 * M�todo para pegar o ID do Notebook do LeitorDisco.
	 * @return notebookId
	 */
	public int getNotebookId() {
		return notebookId.get();
	}
	
	/**
	 * M�todo para setar a marca do LeitorDisco.
	 * @param marca
	 */
	public void setMarca(String marca) {
		this.marca.set(marca);
	}
	
	/**
	 * M�todo para pegar a marca do LeitorDisco.
	 * @return marca
	 */
	public String getMarca() {
		return marca.get();
	}
	
	/**
	 * M�todo para setar o modelo do LeitorDisco.
	 * @param modelo
	 */
	public void setModelo(String modelo) {
		this.modelo.set(modelo);
	}
	
	/**
	 * M�todo para pegar o modelo do LeitorDisco.
	 * @return modelo
	 */
	public String getModelo() {
		return modelo.get();
	}
	
	/**
	 * M�todo para setar o doador do LeitorDisco.
	 * @param doador
	 */
	public void setDoador(String doador){
	    this.doador.set(doador);
	}
	
	/**
	 * M�todo para pegar o doador do LeitorDisco.
	 * @return doador
	 */
	public String getDoador(){
	    return doador.get();
	}
}
