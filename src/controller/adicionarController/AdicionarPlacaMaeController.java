package controller.adicionarController;

import java.util.function.UnaryOperator;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;

/**
 * Classe controladora da tela AdicionarPlacaMae.
 * @author Leonardo Elnisky.
 */
public class AdicionarPlacaMaeController {
    @FXML private GridPane gPaneInserir;
    @FXML private JFXTextField tfMarca;
    @FXML private JFXTextField tfModelo;
    @FXML private JFXCheckBox cbDesconhecido;
    @FXML private JFXTextField tfSocket;
    @FXML private JFXTextField tfTipoRam;

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

    // Formatador tf socket
    TextFormatter<String> socketTF = new TextFormatter<>((UnaryOperator<TextFormatter.Change>) change -> {
	    String concatenado = tfSocket.getText() + change.getText();
	    return (concatenado.length() < 21) ? change: null;
	});
    
    // Formatador tf tipo
    TextFormatter<String> tipoTF = new TextFormatter<>((UnaryOperator<TextFormatter.Change>) change -> {
    	String concatenado = tfTipoRam.getText() + change.getText();
    	return (concatenado.length() < 6) ? change: null;
    });
    
    /**
     * M�todo para carregar tudo que � necess�rio para iniciar a tela.
     */
    @FXML private void initialize() {
    	tfMarca.setTextFormatter(marcaTF);
    	tfModelo.setTextFormatter(modeloTF);
    	tfSocket.setTextFormatter(socketTF);
    	tfTipoRam.setTextFormatter(tipoTF);
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
     * M�todo para checar se o CheckBox Desconhecido foi marcado.
     * @return true se sim, false se n�o.
     */
    public Boolean getChecked() {
    	return cbDesconhecido.isSelected();
    }
    
    /**
     * M�todo para limpar todos os campos da tela.
     */
    public void limparCampos() {
    	tfMarca.clear(); tfModelo.clear(); tfSocket.clear(); tfTipoRam.clear(); cbDesconhecido.setSelected(false);
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
     * M�todo para pegar o texto informado no TextField Socket.
     * @return o texto.
     */
    public String getSocket() {
    	return tfSocket.getText();
    }
    
    /**
     * M�todo para pegar o texto informado no TextField TipoRam.
     * @return o texto.
     */
    public String getTipoRam() {
    	return tfTipoRam.getText();
    }
}
