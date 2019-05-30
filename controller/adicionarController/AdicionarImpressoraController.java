package controller.adicionarController;

import java.util.function.UnaryOperator;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;

/**
 * Classe controladora da tela AdicionarImpressora.
 * @author Leonardo Elnisky
 */
public class AdicionarImpressoraController {
    @FXML private GridPane gPaneInserir;
    @FXML private JFXTextField tfMarca;
    @FXML private JFXTextField tfModelo;
    @FXML private JFXCheckBox cbDesconhecido;

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
    
    /**
     * M�todo para carregar tudo que � necess�rio para iniciar a tela.
     */
    @FXML private void initialize() {
    	tfMarca.setTextFormatter(marcaTF);
    	tfModelo.setTextFormatter(modeloTF);
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
     * M�todo para verificar se o CheckBox Desconhecido foi marcado.
     * @return true se sim, false se n�o.
     */
    public Boolean getChecked() {
    	return cbDesconhecido.isSelected();
    }
    
    /**
     * M�todo para limpar todos os campos da tela.
     */
    public void limparCampos() {
    	tfMarca.clear(); tfModelo.clear(); cbDesconhecido.setSelected(false);
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
}
