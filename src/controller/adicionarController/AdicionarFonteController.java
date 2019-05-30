package controller.adicionarController;

import java.util.function.UnaryOperator;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;

/**
 * Classe controladora da tela AdicionarFonte.
 * @author Leonardo Elnisky.
 */
public class AdicionarFonteController {
    @FXML private GridPane gPaneInserir;
    @FXML private JFXTextField tfMarca;
    @FXML private JFXTextField tfModelo;
    @FXML private JFXCheckBox cbDesconhecido;
    @FXML private JFXTextField tfPotencia;
    @FXML private JFXComboBox<String> cBoxVoltagem;
    
    ObservableList<String> voltagem = FXCollections.
    		observableArrayList("110v", "220v", "Bivolt");
    
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
    
    // Formatar tf potencia
    TextFormatter<String> potenciaTF = new TextFormatter<>((UnaryOperator<TextFormatter.Change>) change -> {
	    String concatenado = tfPotencia.getText() + change.getText();
	    return (concatenado.matches("\\d*") && concatenado.length() < 5) ? change : null;
	});
    
    /**
     * M�todo para carregar tudo que � necess�rio para iniciar a tela AdicionarFonte.
     */
    @FXML private void initialize() {
    	tfMarca.setTextFormatter(marcaTF);
    	tfModelo.setTextFormatter(modeloTF);
    	tfPotencia.setTextFormatter(potenciaTF);
    	cBoxVoltagem.setItems(voltagem);
    }
    
    /**
     * M�todo para ativar/desativar o TextField Modelo.
     * @param event recebe o evento ocorrido.
     */
    @FXML void cbDesconhecidoOnAction(ActionEvent event) {
    	if (!tfModelo.isDisabled())
    		tfModelo.setDisable(true);
    	else tfModelo.setDisable(false);
    }
    
    /**
     * M�todo para saber se o CheckBox Desconhecido foi marcado.
     * @return true se est� marcado, false se n�o.
     */
    public Boolean getChecked() {
    	return cbDesconhecido.isSelected();
    }
    
    /**
     * M�todo para limpar todos os campos da tela.
     */
    public void limparCampos() {
    	tfMarca.clear(); tfModelo.clear(); tfPotencia.clear(); cBoxVoltagem.valueProperty().set(null); cbDesconhecido.setSelected(false);
    }
    
    /**
     * M�todo para pegar o texto informado no TextField Marca.
     * @return o texto.
     */
    public String getMarca() {
    	return tfMarca.getText();
    }
    
    /**
     * M�todo para pegar o texto informado no TextField Modelo.
     * @return o texto.
     */
    public String getModelo() {
    	return tfModelo.getText();
    }
    
    /**
     * M�todo para pegar o texto informado no TextField Pot�ncia.
     * @return o texto.
     */
    public String getPotencia() {
    	return tfPotencia.getText();
    }
    
    /**
     * M�todo para pegar o texto informado no ComboBox Voltagem.
     * @return o texto selecionado.
     */
    public String getVoltagem() {
    	if (cBoxVoltagem.getValue() == null) return "null";
    	else return cBoxVoltagem.getValue();
    }
}
