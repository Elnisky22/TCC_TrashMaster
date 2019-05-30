package controller.adicionarController;

import java.util.function.UnaryOperator;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;

/**
 * Classe controladora da tela AdicioanrDisco.
 * @author Leonardo Elnisky.
 */
public class AdicionarDiscoController {
    @FXML private GridPane gPaneInserir;
    @FXML private JFXTextField tfMarca;
    @FXML private JFXTextField tfModelo;
    @FXML private JFXCheckBox cbDesconhecido;
    @FXML private JFXTextField tfCapacidade;
    @FXML private Label labelGb;

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
    
    // Formatador tf capacidade
    TextFormatter<String> capacidadeTF = new TextFormatter<>((UnaryOperator<TextFormatter.Change>) change -> {
	    String concatenado = tfCapacidade.getText() + change.getText();
	    return (concatenado.matches("[0-9]*(\\.?[0-9]*)?") && concatenado.length() < 7) ? change: null;
	});
    
    /**
     * Método para carregar tudo que é necessário para iniciar a tela Adicionar Disco.
     */
    @FXML private void initialize() {
    	tfCapacidade.setTextFormatter(capacidadeTF);
    	tfMarca.setTextFormatter(marcaTF);
    	tfModelo.setTextFormatter(modeloTF);
    }
    
    /**
     * Método para ativar ou desativar o TextField Modelo.
     * @param event
     */
    @FXML void cbDesconhecido(ActionEvent event) {
    	if (!tfModelo.isDisabled())
    		tfModelo.setDisable(true);
    	else tfModelo.setDisable(false);
    }
    
    /**
     * Método para saber se o CheckBox de Desconhecido foi marcado.
     * @return true se está marcado, false se está desmarcado.
     */
    public Boolean getChecked() {
    	return cbDesconhecido.isSelected();
    }
    
    /**
     * Método para limpar os campos da tela.
     */
    public void limparCampos() {
    	tfMarca.clear(); tfModelo.clear(); tfCapacidade.clear(); cbDesconhecido.setSelected(false);
    }
    
    /**
     * Método para pegar a Marca informada no TextField.
     * @return o texto informado.
     */
    public String getMarca() {
    	return tfMarca.getText();
    }
    
    /**
     * Método para pegar o Modelo informado no TextField.
     * @return o texto informado.
     */
    public String getModelo() {
    	return tfModelo.getText();
    }
    
    /**
     * Método para pegar a Capacidade informada no TextField.
     * @return o texto informado.
     */
    public double getCapacidade() {
    	if (tfCapacidade.getText() == null || tfCapacidade.getText().trim().isEmpty()) return -1.0;
    	else return Double.parseDouble(tfCapacidade.getText());
    }
}