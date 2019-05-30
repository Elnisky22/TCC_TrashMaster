package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe modeladora do objeto Notebook.
 * @author Leonardo Elnisky.
 */
public class Notebook extends Computador {
	private StringProperty modelo = new SimpleStringProperty();
	
	/**
	 * Método para setar o modelo do Notebook.
	 * @param modelo
	 */
	public void setModelo(String modelo) {
		this.modelo.set(modelo);
	}
	
	/**
	 * Método para pegar o modelo do Notebook.
	 * @return modelo
	 */
	public String getModelo() {
		return modelo.get();
	}
	
}
