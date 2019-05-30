package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe modeladora do objeto Memoria.
 * @author Leonardo Elnisky.
 */
public class Memoria {
	private IntegerProperty memoriaId = new SimpleIntegerProperty();
	private IntegerProperty gabineteId = new SimpleIntegerProperty();
	private IntegerProperty notebookId = new SimpleIntegerProperty();
	private StringProperty marca = new SimpleStringProperty();
	private StringProperty modelo = new SimpleStringProperty();
	private StringProperty tipo = new SimpleStringProperty();
	private DoubleProperty memoria = new SimpleDoubleProperty();
	private StringProperty doador = new SimpleStringProperty();
	
	/**
	 * Método para setar o ID da Memoria.
	 * @param memoriaId
	 */
	public void setMemoriaId(int memoriaId) {
		this.memoriaId.set(memoriaId);
	}
	
	/**
	 * Método para pegar o ID da Memoria.
	 * @return memoriaId
	 */
	public int getMemoriaId() {
		return memoriaId.get();
	}
	
	/**
	 * Método para setar o ID do Gabinete da Memoria.
	 * @param gabineteId
	 */
	public void setGabineteId(int gabineteId) {
		this.gabineteId.set(gabineteId);
	}
	
	/**
	 * Método para pegar o ID do Gabinete da Memoria.
	 * @return gabineteId
	 */
	public int getGabineteId() {
		return gabineteId.get();
	}
	
	/**
	 * Método para setar o ID do Notebook da Memoria.
	 * @param notebookId
	 */
	public void setNotebookId(int notebookId) {
		this.notebookId.set(notebookId);
	}
	
	/**
	 * Método para pegar o ID do Notebook da Memoria.
	 * @return notebookId
	 */
	public int getNotebookId() {
		return notebookId.get();
	}
	
	/**
	 * Método para setar a marca da Memoria.
	 * @param marca
	 */
	public void setMarca(String marca) {
		this.marca.set(marca);
	}
	
	/**
	 * Método para pegar a marca da Memoria.
	 * @return marca
	 */
	public String getMarca() {
		return marca.get();
	}
	
	/**
	 * Método para setar o modelo da Memoria.
	 * @param modelo
	 */
	public void setModelo(String modelo) {
		this.modelo.set(modelo);
	}
	
	/**
	 * Método para pegar o modelo da Memoria.
	 * @return modelo
	 */
	public String getModelo() {
		return modelo.get();
	}
	
	/**
	 * Método para setar o tipo da Memoria.
	 * @param tipo
	 */
	public void setTipo(String tipo) {
		this.tipo.set(tipo);
	}
	
	/**
	 * Método para pegar o tipo da Memoria.
	 * @return tipo
	 */
	public String getTipo() {
		return tipo.get();
	}
	
	/**
	 * Método para setar a capacidade da Memoria.
	 * @param memoria
	 */
	public void setMemoria(double memoria) {
		this.memoria.set(memoria);
	}
	
	/**
	 * Método para pegar a capacidade da Memoria.
	 * @return memoria
	 */
	public double getMemoria() {
		return memoria.get();
	}
	
	/**
	 * Método para setar o doador da Memoria.
	 * @param doador
	 */
	public void setDoador(String doador){
	    this.doador.set(doador);
	}
	
	/**
	 * Método para pegar o doador da Memoria.
	 * @return doador
	 */
	public String getDoador(){
	    return doador.get();
	}
}
