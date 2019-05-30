package controller.adicionarController;

import java.util.function.UnaryOperator;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;

/**
 * Classe controladora da tela AdicionarMemoria.
 * @author Leonardo Elnisky.
 */
public class AdicionarMemoriaController {
    @FXML private GridPane gPaneInserir;
    @FXML private JFXTextField tfMarca;
    @FXML private JFXTextField tfModelo;
    @FXML private JFXCheckBox cbDesconhecido;
    @FXML private JFXTextField tfTipo;
    @FXML private JFXTextField tfCapacidade;
    @FXML private Label labelCapacidade;
    
    // Formatador tf marca
    TextFormatter<String> marcaTF = new TextFormatter<>((UnaryOperator<TextFormatter.Change>) change -> {
	    String concatenado = tfMarca.getText() + change.getText();
	    return (concatenado.length() < 21) ? change: null;
	});
    
    // Formatador tf modelo
    TextFormatter<String> modeloTF = new TextFormatter<>((UnaryOperator<TextFormatter.Change>) change -> {
	    String concatenado = tfModelo.getText() + change.getText();
	    return (concatenado.length() < 21) ? change: null;
	});
    
    // Formatador tf tipo
    TextFormatter<String> tipoTF = new TextFormatter<>((UnaryOperator<TextFormatter.Change>) change -> {
    	String concatenado = tfTipo.getText() + change.getText();
    	return (concatenado.length() < 6) ? change: null;
    });
    
    // Formatador tf capacidade
    TextFormatter<String> capacidadeTF = new TextFormatter<>((UnaryOperator<TextFormatter.Change>) change -> {
	    String concatenado = tfCapacidade.getText() + change.getText();
	    return (concatenado.matches("[0-9]*(\\.?[0-9]*)?") && concatenado.length() < 7) ? change: null;
	});
    
    /**
     * Método para carregar tudo que é necessário para iniciar a tela.
     */
    @FXML private void initialize() {
    	tfMarca.setTextFormatter(marcaTF);
    	tfModelo.setTextFormatter(modeloTF);
    	tfTipo.setTextFormatter(tipoTF);
    	tfCapacidade.setTextFormatter(capacidadeTF);
    }
    
    /**
     * Método para ativar/desativar o TextField Modelo.
     * @param event recebe o evento ocorrido.
     */
    @FXML void cbDesconhecidoOnAction(ActionEvent event) {
    	if (!tfModelo.isDisabled())
    		tfModelo.setDisable(true);
    	else tfModelo.setDisable(false);
    }
    
    /**
     * Método para checar se o CheckBox Desconhecido foi marcado.
     * @return true se sim, false se não.
     */
    public Boolean getChecked() {
    	return cbDesconhecido.isSelected();
    }
    
    /**
     * Método para limpar todos os campos da tela.
     */
    public void limparCampos() {
    	tfMarca.clear(); tfModelo.clear(); tfTipo.clear(); tfCapacidade.clear(); cbDesconhecido.setSelected(false);
    }
    
    /**
     * Método para pegar o texto informado no TextField Marca.
     * @return o texto.
     */
    public String getMarca() {
    	return tfMarca.getText();
    }
    
    /**
     * Método para pegar o texto informado no TextField Modelo.
     * @return o texto.
     */
    public String getModelo() {
    	return tfModelo.getText();
    }
    
    /**
     * Método para pegar o texto informado no TextField Tipo.
     * @return tipo.
     */
    public String getTipo() {
    	return tfTipo.getText();
    }
    
    /**
     * Método para pegar o texto informado no TextField Capacidade.
     * @return o texto.
     */
    public double getMemoria() {
    	if (tfCapacidade.getText() == null || tfCapacidade.getText().trim().isEmpty()) return -1.0;
    	return Double.parseDouble(tfCapacidade.getText());
    }
}
