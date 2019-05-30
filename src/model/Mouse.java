package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe modeladora do objeto Mouse.
 * @author Leonardo Elnisky.
 */
public class Mouse {
	private IntegerProperty mouseId = new SimpleIntegerProperty();
	private StringProperty marca = new SimpleStringProperty();
	private StringProperty modelo = new SimpleStringProperty();
	private StringProperty doador = new SimpleStringProperty();
	
	/**
	 * Método para setar o ID do Mouse.
	 * @param mouseId
	 */
	public void setMouseId(int mouseId) {
		this.mouseId.set(mouseId);
	}
	
	/**
	 * Método para pegar o ID do Mouse.
	 * @return mouseId
	 */
	public int getMouseId() {
		return mouseId.get();
	}

	/**
	 * Método para setar a marca do Mouse.
	 * @param marca
	 */
	public void setMarca(String marca) {
		this.marca.set(marca);
	}
	
	/**
	 * Método para pegar a marca do Mouse.
	 * @return marca
	 */
	public String getMarca() {
		return marca.get();
	}
	
	/**
	 * Método para setar o modelo do Mouse.
	 * @param modelo
	 */
	public void setModelo(String modelo) {
		this.modelo.set(modelo);
	}
	
	/**
	 * Método para pegar o modelo do Mouse.
	 * @return modelo
	 */
	public String getModelo() {
		return modelo.get();
	}
	
	/**
	 * Método para setar o doador do Mouse.
	 * @param doador
	 */
	public void setDoador(String doador){
	    this.doador.set(doador);
	}
	
	/**
	 * Método para pegar o doador do Mouse.
	 * @return doador
	 */
	public String getDoador(){
	    return doador.get();
	}
}
