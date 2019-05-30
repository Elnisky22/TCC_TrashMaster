package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe modeladora do objeto Computador.
 * @author Leonardo Elnisky.
 */
public abstract class Computador {
	private IntegerProperty computadorId = new SimpleIntegerProperty();
	private StringProperty marca = new SimpleStringProperty();
	private StringProperty doador = new SimpleStringProperty();
	
	/**
	 * M�todo para setar o ID do Computador.
	 * @param computadorId o ID do Computador.
	 */
	public void setComputadorId(int computadorId) {
		this.computadorId.set(computadorId);
	}
	
	/**
	 * M�todo para pegar o ID do Computador.
	 * @return computadorId o ID do Computador.
	 */
	public int getComputadorId() {
		return computadorId.get();
	}
	
	/**
	 * M�todo para setar a marca do Computador.
	 * @param marca
	 */
	public void setMarca(String marca) {
		this.marca.set(marca);
	}
	
	/**
	 * M�todo para pegar a marca do Computador.
	 * @return marca a marca do computador.
	 */
	public String getMarca() {
		return marca.get();
	}
	
	/**
	 * M�todo para setar o doador do Computador.
	 * @param doador
	 */
	public void setDoador(String doador){
	    this.doador.set(doador);
	}
	
	/**
	 * M�todo para pegar o doador do Computador.
	 * @return doador o doador do Computador.
	 */
	public String getDoador(){
	    return doador.get();
	}
}
