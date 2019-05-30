package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe modeladora do objeto Doacao.
 * @author Leonardo Elnisky.
 */
public class Doacao {
	StringProperty data = new SimpleStringProperty();
	StringProperty itens = new SimpleStringProperty();
	StringProperty recebedor = new SimpleStringProperty();
	
	/**
	 * Método para setar a data da Doacao.
	 * @param data
	 */
	public void setData(String data) {
		this.data.set(data);
	}
	
	/**
	 * Método para pegar a data da Doacao.
	 * @return data
	 */
	public String getData() {
		return data.get();
	}
	
	/**
	 * Método para setar os itens da Doacao.
	 * @param itens
	 */
	public void setItens(String itens) {
		this.itens.set(itens);
	}
	
	/**
	 * Método para pegar os itens da Doacao.
	 * @return itens
	 */
	public String getItens() {
		return itens.get();
	}
	
	/**
	 * Método para setar o recebedor da Doacao.
	 * @param recebedor
	 */
	public void setRecebedor(String recebedor) {
		this.recebedor.set(recebedor);
	}
	
	/**
	 * Método para pegar o doador da Doacao
	 * @return recebedor
	 */
	public String getRecebedor() {
		return recebedor.get();
	}
}
