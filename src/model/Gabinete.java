package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe modeladora do objeto Gabinete.
 * @author Leonardo Elnisky.
 */
public class Gabinete extends Computador {
	private StringProperty tamanho = new SimpleStringProperty();
	
	/**
	 * Método para setar o tamanho do Gabinete.
	 * @param tamanho
	 */
	public void setTamanho(String tamanho) {
		this.tamanho.set(tamanho);
	}
	
	/**
	 * Método para pegar o tamanho do Gabinete.
	 * @return tamanho
	 */
	public String getTamanho() {
		return tamanho.get();
	}
	
}
