package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe modeladora do objeto Cabo.
 * @author Leonardo Elnisky.
 */
public class Cabo {
	private IntegerProperty caboId = new SimpleIntegerProperty();
	private StringProperty tipo = new SimpleStringProperty();
	private IntegerProperty quantidade = new SimpleIntegerProperty();
	
	/**
	 * M�todo para setar o ID do cabo.
	 * @param caboId o ID do cabo.
	 */
	public void setCaboId(int caboId) {
		this.caboId.set(caboId);
	}
	
	/**
	 * M�todo para pegar o ID do cabo.
	 * @return caboId o ID do cabo.
	 */
	public int getCaboId() {
		return caboId.get();
	}
	
	/**
	 * M�todo para setar o Tipo do cabo.
	 * @param tipo o tipo do cabo.
	 */
	public void setTipo(String tipo) {
		this.tipo.set(tipo);
	}
	
	/**
	 * M�todo para pegar o Tipo do cabo.
	 * @return tipo o tipo do cabo.
	 */
	public String getTipo() {
		return tipo.get();
	}
	
	/**
	 * M�todo para pegar a Quantidade do cabo.
	 * @param quantidade a quantidade do cabo.
	 */
	public void setQuantidade(int quantidade) {
		this.quantidade.set(quantidade);
	}
	
	/**
	 * M�todo para pegar a quantidade do cabo.
	 * @return quantidade a quantia de cabo.
	 */
	public int getQuantidade() {
		return quantidade.get();
	}
}
