package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe modeladora do objeto Descarte.
 * @author Leonardo Elnisky.
 */
public class Descarte {
	StringProperty data = new SimpleStringProperty();
	StringProperty itens = new SimpleStringProperty();
	
	/**
	 * M�todo para setar a data do Descarte.
	 * @param data
	 */
	public void setData(String data) {
		this.data.set(data);
	}
	
	/**
	 * M�todo para pegar a data do Descarte.
	 * @return data a data do descarte.
	 */
	public String getData() {
		return data.get();
	}
	
	/**
	 * M�todo para setar os itens do Descarte.
	 * @param itens
	 */
	public void setItens(String itens) {
		this.itens.set(itens);
	}
	
	/**
	 * M�todo para pegar os itens  do Descarte.
	 * @return itens os itens do descarte.
	 */
	public String getItens() {
		return itens.get();
	}
}
