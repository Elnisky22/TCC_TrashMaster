package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe modeladora do objeto Outros.
 * @author Leonardo Elnisky.
 */
public class Outros {
	private IntegerProperty outrosId = new SimpleIntegerProperty();
	private StringProperty descricao = new SimpleStringProperty();
	private StringProperty doador = new SimpleStringProperty();
	
	/**
	 * Método para setar o ID do Outros.
	 * @param outrosId
	 */
	public void setOutrosId(int outrosId) {
		this.outrosId.set(outrosId);
	}
	
	/**
	 * Método para pegar o ID do Outros.
	 * @return outrosId
	 */
	public int getOutrosId() {
		return outrosId.get();
	}

	/**
	 * Método para setar a descrição do Outros.
	 * @param descricao
	 */
	public void setDescricao(String descricao) {
		this.descricao.set(descricao);
	}
	
	/**
	 * Método para pegar a descrição do Outros.
	 * @return descricao
	 */
	public String getDescricao() {
		return descricao.get();
	}
	
	/**
	 * Método para setar o doador do Outros.
	 * @param doador
	 */
	public void setDoador(String doador){
	    this.doador.set(doador);
	}
	
	/**
	 * Método para pegar o doador do Outros.
	 * @return doador
	 */
	public String getDoador(){
	    return doador.get();
	}
}
