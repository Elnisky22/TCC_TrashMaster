package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe modeladora do objeto Atividade.
 * @author Leonardo Elnisky.
 */
public class Atividade {
	StringProperty data = new SimpleStringProperty();
	StringProperty atividade = new SimpleStringProperty();
	StringProperty voluntario = new SimpleStringProperty();
	
	/**
	 * M�todo para setar o valor de data.
	 * @param data a data do objeto.
	 */
	public void setData(String data) {
		this.data.set(data);
	}
	
	/**
	 * M�todo para pegar a data do objeto.
	 * @return data a data do objeto.
	 */
	public String getData() {
		return data.get();
	}
	
	/**
	 * M�todo para setar a atividade.
	 * @param atividade
	 */
	public void setAtividade(String atividade) {
		this.atividade.set(atividade);
	}
	
	/**
	 * M�todo para pegar a atividade do objeto.
	 * @return atividade a atividade.
	 */
	public String getAtividade() {
		return atividade.get();
	}
	
	/**
	 * M�todo para setar o volunt�rio da atividade.
	 * @param voluntario
	 */
	public void setVoluntario(String voluntario) {
		this.voluntario.set(voluntario);
	}
	
	/**
	 * M�todo para pegar o volunt�rio da atividade.
	 * @return voluntario o volunt�rio da atividade.
	 */
	public String getVoluntario() {
		return voluntario.get();
	}
}
