package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.UnaryOperator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import application.Main;
import dao.gerenciarDao.GerenciarAtividadesJDBCDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Atividade;

/**
 * Classe controladora da tela Adicionar Atividade.
 * @author Leonardo Elnisky.
 */
public class AddAtividadeController {
    @FXML private VBox box;
    @FXML private JFXTextArea taAtividade;
    @FXML private HBox hBoxBot;
    @FXML private JFXTextField tfVoluntario;
    @FXML private JFXButton btnConfirmar;
    @FXML private Label lblData;
    
    private AtividadesController atvCtrl;
    
    private static int resultAdd;
    private DateFormat dtFormat;
    private Date data;

    // Formatar tf doador
    TextFormatter<String> voluntarioTF = new TextFormatter<>((UnaryOperator<TextFormatter.Change>) change -> {
	    String concatenado = tfVoluntario.getText() + change.getText();
	    return (concatenado.length() < 51) ? change : null;
	});
    
    /**
     * Método para carregar o que é necessário para inicializar a tela.
     */
    @FXML void initialize() {
    	resultAdd = 0;
    	tfVoluntario.setTextFormatter(voluntarioTF);
    	
    	dtFormat = new SimpleDateFormat("dd/MM/yyyy"); data = new Date();
    	lblData.setText(lblData.getText() + dtFormat.format(data));
    }
    
    /**
     * Coleta os dados inseridos nas entradas e envia para o banco de dados.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnConfirmarOnAction(ActionEvent event) throws SQLException, IOException {
    	if (taAtividade.getText() == null || taAtividade.getText().trim().isEmpty() || tfVoluntario.getText() == null || tfVoluntario.getText().trim().isEmpty()) {
    		String erro = "";
    		if (taAtividade.getText() == null || taAtividade.getText().trim().isEmpty())
    			erro += "Insira a atividade no campo \"Atividade\".\n";
    		if (tfVoluntario.getText() == null || tfVoluntario.getText().trim().isEmpty())
    			erro += "Informe o(s) voluntário(s).\n";
    		
    		Main.infoDialog("Erro", erro);
    		return;
    	} else {
    		if (Main.confirmDialog("Confirmação", "Tem certeza que deseja adicionar\n\nAtividade:\n" + taAtividade.getText() + "\n\nVoluntário(s):\n" + tfVoluntario.getText())) {
    			GerenciarAtividadesJDBCDAO daoAtividade = new GerenciarAtividadesJDBCDAO();
		    	Atividade atv = new Atividade();
		    	atv.setAtividade(taAtividade.getText());
		    	atv.setVoluntario(tfVoluntario.getText());
		    	
		    	resultAdd = daoAtividade.adicionar(atv);
		    	atvCtrl.atualizaTabela();
		    		
		    	if (resultAdd != 0) {
		    		Main.infoDialog("Informação", "Atividade:\n" + taAtividade.getText() + "\n\nVoluntário(s):\n" + tfVoluntario.getText() + "\n\nadicionada com sucesso.");
		    	} else Main.infoDialog("Erro", "Ocorreu um erro ao adicionar a atividade,\nverifique os valores e tente novamente.");
		    		
		    	atvCtrl.manageDrawer();
		    	resultAdd = 0;
		    	taAtividade.clear();
		    	tfVoluntario.clear();
    		}
    	}
    }

    /*
     * Recebe o controlador do pai para comunicação.
     * @param pai controlador da tela pai na hierarquia.
     */
    public void setControllerPai(AtividadesController pai) {
    	this.atvCtrl = pai;
    }
}
