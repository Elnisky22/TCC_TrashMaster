package controller.adicionarController;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

/**
 * Classe controladora da tela AdicionarOutros.
 * @author Leonardo Elnisky
 */
public class AdicionarOutrosController {
    @FXML private GridPane gPaneInserir;
    @FXML private JFXTextArea taDescricao;

    /**
     * Método para limpar todos os campos da tela.
     */
    public void limparCampos() {
    	taDescricao.clear();
    }
    
    /**
     * Método para pegar o texto informado no TextArea Descrição.
     * @return o texto.
     */
    public String getArea() {
    	return taDescricao.getText();
    }
}
