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
	 * M�todo para setar o ID do Outros.
	 * @param outrosId
	 */
	public void setOutrosId(int outrosId) {
		this.outrosId.set(outrosId);
	}
	
	/**
	 * M�todo para pegar o ID do Outros.
	 * @return outrosId
	 */
	public int getOutrosId() {
		return outrosId.get();
	}

	/**
	 * M�todo para setar a descri��o do Outros.
	 * @param descricao
	 */
	public void setDescricao(String descricao) {
		this.descricao.set(descricao);
	}
	
	/**
	 * M�todo para pegar a descri��o do Outros.
	 * @return descricao
	 */
	public String getDescricao() {
		return descricao.get();
	}
	
	/**
	 * M�todo para setar o doador do Outros.
	 * @param doador
	 */
	public void setDoador(String doador){
	    this.doador.set(doador);
	}
	
	/**
	 * M�todo para pegar o doador do Outros.
	 * @return doador
	 */
	public String getDoador(){
	    return doador.get();
	}
}
