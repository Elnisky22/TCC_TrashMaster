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
 * Classe controladora da tela AdicionarGabinete.
 * @author Leonardo Elnisky.
 */
public class AdicionarGabineteController {
    @FXML private GridPane gPaneInserir;
    @FXML private JFXTextField tfMarca;
    @FXML private JFXCheckBox cbDesconhecido;
    @FXML private JFXComboBox<String> cBoxTamanho;

    ObservableList<String> tamanhos = FXCollections.
    		observableArrayList("Low Tower", "Mid Tower", "High Tower");
    
    // Formatador tf marca
    TextFormatter<String> marcaTF = new TextFormatter<>((UnaryOperator<TextFormatter.Change>) change -> {
	    String concatenado = tfMarca.getText() + change.getText();
	    return (concatenado.length() < 21) ? change: null;
	});
    
    /**
     * M�todo para carregar tudo que � necess�rio para iniciar a tela.
     */
    @FXML private void initialize() {
    	tfMarca.setTextFormatter(marcaTF);
    	cBoxTamanho.setItems(tamanhos);
    }
    
    /**
     * M�todo para ativar/desativar o TextField Modelo.
     * @param event recebe o evento ocorrido.
     */
    @FXML void cbDesconhecidoOnAction(ActionEvent event) {
    	if (!tfMarca.isDisabled())
    		tfMarca.setDisable(true);
    	else tfMarca.setDisable(false);
    }
    
    /**
     * M�todo para checar se o CheckBox Desconhecido est� marcado.
     * @return true se est�, false se n�o.
     */
    public Boolean getChecked() {
    	return cbDesconhecido.isSelected();
    }
    
    /**
     * M�todo para limpar todos os campos da tela.
     */
    public void limparCampos() {
    	tfMarca.clear(); cBoxTamanho.valueProperty().set(null); cbDesconhecido.setSelected(false);
    }
    
    /**
     * M�todo para pegar o texto informado no TextField Marca.
     * @return o texto.
     */
    public String getMarca() {
    	return tfMarca.getText();
    }
    
    /**
     * M�todo para pegar o texto informado no ComboBox Tamanho.
     * @return o texto selecioando.
     */
    public String getTamanho() {
    	if (cBoxTamanho.getValue() == null) return "null";
    	else return cBoxTamanho.getValue();
    }
}
