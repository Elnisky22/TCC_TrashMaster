package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe modeladora do objeto Impressora.
 * @author Leonardo Elnisky.
 */
public class Impressora {
	private IntegerProperty impressoraId = new SimpleIntegerProperty();
	private StringProperty marca = new SimpleStringProperty();
	private StringProperty modelo = new SimpleStringProperty();
	private StringProperty doador = new SimpleStringProperty();
	
	/**
	 * Método para setar o ID da Impressora.
	 * @param impressoraId
	 */
	public void setImpressoraId(int impressoraId) {
		this.impressoraId.set(impressoraId);
	}
	
	/**
	 * Método para pegar o ID da Impressora.
	 * @return impressoraId
	 */
	public int getImpressoraId() {
		return impressoraId.get();
	}

	/**
	 * Método para setar a marca da Impressora.
	 * @param marca
	 */
	public void setMarca(String marca) {
		this.marca.set(marca);
	}
	
	/**
	 * Método para pegar a marca da Impressora.
	 * @return marca
	 */
	public String getMarca() {
		return marca.get();
	}
	
	/**
	 * Método para setar o modelo da Impressora.
	 * @param modelo
	 */
	public void setModelo(String modelo) {
		this.modelo.set(modelo);
	}
	
	/**
	 * Método para pegar o modelo da Impressora.
	 * @return modelo
	 */
	public String getModelo() {
		return modelo.get();
	}
	
	/**
	 * Método para setar o doador da Impressora.
	 * @param doador
	 */
	public void setDoador(String doador){
	    this.doador.set(doador);
	}
	
	/**
	 * Método para pegar o doador da Impressora.
	 * @return doador
	 */
	public String getDoador(){
	    return doador.get();
	}
}
