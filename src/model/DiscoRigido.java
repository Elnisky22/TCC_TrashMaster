package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe modeladora do objeto DiscoRigido.
 * @author Leonardo Elnisky.
 */
public class DiscoRigido {
	private IntegerProperty discoId = new SimpleIntegerProperty();
	private IntegerProperty gabineteId = new SimpleIntegerProperty();
	private IntegerProperty notebookId = new SimpleIntegerProperty();
	private StringProperty marca = new SimpleStringProperty();
	private StringProperty modelo = new SimpleStringProperty();
	private DoubleProperty capacidade = new SimpleDoubleProperty();
	private StringProperty doador = new SimpleStringProperty();
	
	/**
	 * M�todo para setar o ID do DiscoRigido.
	 * @param discoId o ID do DiscoRigido.
	 */
	public void setDiscoId(int discoId) {
		this.discoId.set(discoId);
	}
	
	/**
	 * M�todo para pegar o ID do DiscoRigido.
	 * @return discoId o ID do DiscoRigido.
	 */
	public int getDiscoId() {
		return discoId.get();
	}
	
	/**
	 * M�todo para setar o ID do Gabinete do DiscoRigido.
	 * @param gabineteId o ID do Gabinete do DiscoRigido.
	 */
	public void setGabineteId(int gabineteId) {
		this.gabineteId.set(gabineteId);
	}
	
	/**
	 * M�todo para pegar o ID do Gabinete do DiscoRigido.
	 * @return gabineteId o ID do Gabinete do DiscoRigido.
	 */
	public int getGabineteId() {
		return gabineteId.get();
	}
	
	/**
	 * M�todo para setar o ID do Notebook do DiscoRigido.
	 * @param notebookId o ID do Notebook do DiscoRigido.
	 */
	public void setNotebookId(int notebookId) {
		this.notebookId.set(notebookId);
	}
	
	/**
	 * M�todo para pegar o ID do Notebook do DiscoRigido.
	 * @return notebookId o ID do Notebook do DiscoRigido.
	 */
	public int getNotebookId() {
		return notebookId.get();
	}
	
	/**
	 * M�todo para setar a marca do DiscoRigido.
	 * @param marca
	 */
	public void setMarca(String marca) {
		this.marca.set(marca);
	}
	
	/**
	 * M�todo para pegar a marca do DiscoRigido.
	 * @return marca
	 */
	public String getMarca() {
		return marca.get();
	}
	
	/**
	 * M�todo para setar o modelo do DiscoRigido.
	 * @param modelo
	 */
	public void setModelo(String modelo) {
		this.modelo.set(modelo);
	}
	
	/**
	 * M�todo para pegar o modelo do DiscoRigido.
	 * @return modelo
	 */
	public String getModelo() {
		return modelo.get();
	}
	
	/**
	 * M�todo para setar a capacidade do DiscoRigido.
	 * @param capacidade
	 */
	public void setCapacidade(double capacidade) {
		this.capacidade.set(capacidade);
	}
	
	/**
	 * M�todo para pegar a capacidade do DiscoRigido.
	 * @return capacidade
	 */
	public double getCapacidade() {
		return capacidade.get();
	}
	
	/**
	 * M�todo para setar o doador do DiscoRigido.
	 * @param doador
	 */
	public void setDoador(String doador) {
		this.doador.set(doador);
	}
	
	/**
	 * M�todo para pegar o doador do DiscoRigido.
	 * @return doador
	 */
	public String getDoador() {
		return doador.get();
	}
}
