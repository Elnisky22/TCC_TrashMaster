package controller.adicionarController;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

/**
 * Classe controladora da tela AdicionarCabo.
 * @author Leonardo Elnisky.
 */
public class AdicionarCaboController {
	@FXML private GridPane gridPaneInserir;
    @FXML private JFXComboBox<String> cBoxTipo;
    
    ObservableList<String> tipos = FXCollections.
    		observableArrayList("Adaptadores", "DVI", "Energia", "HDMI", "Outros", "PS2", "USB", "USB-B", "VGA (D-SUB)");
    
    /**
     * M�todo para carregar tudo que � necess�rio para iniciar a tela AdicionarCabo.
     */
    @FXML private void initialize() {
    	cBoxTipo.setItems(tipos);
    }
    
    /**
     * M�todo para pegar qual tipo foi selecionado na ComboBox Tipo.
     * @return o tipo selecionado.
     */
    public String getTipo() {
    	return cBoxTipo.getValue();
    }
    
    /**
     * M�todo para limpar os campos da tela.
     */
    public void limparCampos() {
    	cBoxTipo.valueProperty().set(null);
    }
}
