package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe modeladora do objeto Processador.
 * @author Leonardo Elnisky.
 */
public class Processador {
	private IntegerProperty processadorId = new SimpleIntegerProperty();
	private IntegerProperty gabineteId = new SimpleIntegerProperty();
	private IntegerProperty notebookId = new SimpleIntegerProperty();
	private StringProperty marca = new SimpleStringProperty();
	private StringProperty modelo = new SimpleStringProperty();
	private StringProperty socket = new SimpleStringProperty();
	private StringProperty doador = new SimpleStringProperty();
	
	/**
	 * Método para setar o ID do Processador.
	 * @param processadorId
	 */
	public void setProcessadorId(int processadorId) {
		this.processadorId.set(processadorId);
	}
	
	/**
	 * Método para pegar o ID do Processador.
	 * @return processadorId
	 */
	public int getProcessadorId() {
		return processadorId.get();
	}
	
	/**
	 * Método para setar o ID do Gabinete do Processador.
	 * @param gabineteId
	 */
	public void setGabineteId(int gabineteId) {
		this.gabineteId.set(gabineteId);
	}
	
	/**
	 * Método para pegar o ID do Gabinete do Processador.
	 * @return gabineteId
	 */
	public int getGabineteId() {
		return gabineteId.get();
	}
	
	/**
	 * Método para setar o ID do Notebook do Processador.
	 * @param notebookId
	 */
	public void setNotebookId(int notebookId) {
		this.notebookId.set(notebookId);
	}
	
	/**
	 * Método para pegar o ID do Notebook do Processador.
	 * @return notebookId
	 */
	public int getNotebookId() {
		return notebookId.get();
	}
	
	/**
	 * Método para setar a marca do Processador.
	 * @param marca
	 */
	public void setMarca(String marca) {
		this.marca.set(marca);
	}
	
	/**
	 * Método para pegar a marca do Processador.
	 * @return marca
	 */
	public String getMarca() {
		return marca.get();
	}
	
	/**
	 * Método para setar o modelo do Processador.
	 * @param modelo
	 */
	public void setModelo(String modelo) {
		this.modelo.set(modelo);
	}
	
	/**
	 * Método para pegar o modelo do Processador.
	 * @return modelo
	 */
	public String getModelo() {
		return modelo.get();
	}
	
	/**
	 * Método para setar o tipo de socket do Processador.
	 * @param socket
	 */
	public void setSocket(String socket) {
		this.socket.set(socket);
	}
	
	/**
	 * Metodo para pegar o tipo de socket do Processador.
	 * @return socket
	 */
	public String getSocket() {
		return socket.get();
	}
	
	/**
	 * Método para setar o doador do Processador.
	 * @param doador
	 */
	public void setDoador(String doador){
	    this.doador.set(doador);
	}
	
	/**
	 * Método para pegar o doador do Processador.
	 * @return doador
	 */
	public String getDoador(){
	    return doador.get();
	}
}
