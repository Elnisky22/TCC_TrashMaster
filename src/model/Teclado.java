package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe modeladora do objeto Teclado.
 * @author Leonardo Elnisky.
 */
public class Teclado {
	private IntegerProperty tecladoId = new SimpleIntegerProperty();
	private StringProperty marca = new SimpleStringProperty();
	private StringProperty modelo = new SimpleStringProperty();
	private StringProperty doador = new SimpleStringProperty();
	
	/**
	 * Método para setar o ID do Teclado.
	 * @param tecladoId
	 */
	public void setTecladoId(int tecladoId) {
		this.tecladoId.set(tecladoId);
	}
	
	/**
	 * Método para pegar o ID do Teclado.
	 * @return tecladoId
	 */
	public int getTecladoId() {
		return tecladoId.get();
	}
	
	/**
	 * Método para setar a marca do Teclado.
	 * @param marca
	 */
	public void setMarca(String marca) {
		this.marca.set(marca);
	}
	
	/**
	 * Método para pegar a marca do Teclado.
	 * @return marca
	 */
	public String getMarca() {
		return marca.get();
	}
	
	/**
	 * Método para setar o modelo do Teclado.
	 * @param modelo
	 */
	public void setModelo(String modelo) {
		this.modelo.set(modelo);
	}
	
	/**
	 * Método para pegar o modelo do Teclado.
	 * @return modelo
	 */
	public String getModelo() {
		return modelo.get();
	}
	
	/**
	 * Método para setar o doador do Teclado.
	 * @param doador
	 */
	public void setDoador(String doador){
	    this.doador.set(doador);
	}
	
	/**
	 * Método para pegar o doador do Teclado.
	 * @return doador
	 */
	public String getDoador(){
	    return doador.get();
	}
}
