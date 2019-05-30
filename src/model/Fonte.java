package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe modeladora do objeto Fonte.
 * @author Leonardo Elnisky.
 */
public class Fonte {
	private IntegerProperty fonteId = new SimpleIntegerProperty();
	private IntegerProperty gabineteId = new SimpleIntegerProperty();
	private IntegerProperty notebookId = new SimpleIntegerProperty();
	private StringProperty marca = new SimpleStringProperty();
	private StringProperty modelo = new SimpleStringProperty();
	private StringProperty potencia = new SimpleStringProperty();
	private StringProperty voltagem = new SimpleStringProperty();
	private StringProperty doador = new SimpleStringProperty();
	
	/**
	 * Método para setar o ID da Fonte.
	 * @param fonteId
	 */
	public void setFonteId(int fonteId) {
		this.fonteId.set(fonteId);
	}
	
	/**
	 * Método para pegar o ID da Fonte.
	 * @return fonteId
	 */
	public int getFonteId() {
		return fonteId.get();
	}
	
	/**
	 * Método para setar o ID do Gabinete da Fonte.
	 * @param gabineteId
	 */
	public void setGabineteId(int gabineteId) {
		this.gabineteId.set(gabineteId);
	}
	
	/**
	 * Método para pegar o ID do Gabinete da Fonte.
	 * @return gabineteId
	 */
	public int getGabineteId() {
		return gabineteId.get();
	}
	
	/**
	 * Método para setar o ID do Notebook da Fonte.
	 * @param notebookId
	 */
	public void setNotebookId(int notebookId) {
		this.notebookId.set(notebookId);
	}
	
	/**
	 * Método para pegar o ID do Notebook da Fonte.
	 * @return notebookId
	 */
	public int getNotebookId() {
		return notebookId.get();
	}
	
	/**
	 * Método para setar a marca da Fonte.
	 * @param marca
	 */
	public void setMarca(String marca) {
		this.marca.set(marca);
	}
	
	/**
	 * Método para pegar a marca da Fonte.
	 * @return marca
	 */
	public String getMarca() {
		return marca.get();
	}
	
	/**
	 * Método para setar o modelo da Fonte.
	 * @param modelo
	 */
	public void setModelo(String modelo) {
		this.modelo.set(modelo);
	}
	
	/**
	 * Método para pegar o modelo da Fonte.
	 * @return modelo
	 */
	public String getModelo() {
		return modelo.get();
	}
	
	/**
	 * Método para setar a potência da Fonte.
	 * @param potencia
	 */
	public void setPotencia(String potencia) {
		this.potencia.set(potencia);
	}
	
	/**
	 * Método para pegar a potência da Fonte.
	 * @return potencia
	 */
	public String getPotencia() {
		return this.potencia.get();
	}

	/**
	 * Método setar a voltagem da Fonte.
	 * @param voltagem
	 */
	public void setVoltagem(String voltagem) {
		this.voltagem.set(voltagem);
	}
	
	/**
	 * Método para pegar a voltagem da Fonte.
	 * @return voltagem
	 */
	public String getVoltagem() {
		return voltagem.get();
	}
	
	/**
	 * Método para setar o doador da Fonte.
	 * @param doador
	 */
	public void setDoador(String doador){
	    this.doador.set(doador);
	}
	
	/**
	 * Método para pegar o doador da Fonte.
	 * @return doador
	 */
	public String getDoador(){
	    return doador.get();
	}
}
