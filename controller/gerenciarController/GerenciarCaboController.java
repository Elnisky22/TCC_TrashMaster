package controller.gerenciarController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.function.UnaryOperator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import application.Main;
import dao.gerenciarDao.GerenciarCaboJDBCDAO;
import dao.gerenciarDao.GerenciarDescartesJDBCDAO;
import dao.gerenciarDao.GerenciarDoacoesJDBCDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import model.Cabo;

/**
 * Classe controladora da tela GerenciarCabo.
 * @author Leonardo Elnisky.
 */
public class GerenciarCaboController {
	@FXML private BorderPane bPaneCabos;
	@FXML private JFXButton btnDescartar;
    @FXML private TableView<Cabo> tbCabos;
    @FXML private TableColumn<Cabo, Integer> IDcolumn;
    @FXML private TableColumn<Cabo, String> tipoColumn;
    @FXML private TableColumn<Cabo, Integer> quantidadeColumn;
    @FXML private JFXTextField tfQuantidade;
    @FXML private JFXTextField tfRecebedor;
    @FXML private JFXButton btnDoar;
    
    // Formatar tf quantidade
    TextFormatter<String> quantidadeTF = new TextFormatter<>((UnaryOperator<TextFormatter.Change>) change -> {
	    String concatenado = tfQuantidade.getText() + change.getText();
	    return (concatenado.matches("\\d*") && concatenado.length() < 4) ? change : null;
	});
    // Formatar tf recebedor
    TextFormatter<String> recebedorTF = new TextFormatter<>((UnaryOperator<TextFormatter.Change>) change -> {
	    String concatenado = tfRecebedor.getText() + change.getText();
	    return (concatenado.length() < 51) ? change : null;
	});
    
    /**
     * Método para carregar tudo que é necessário para iniciar a tela.
     * @throws SQLException
     * @throws IOException 
     */
    @FXML void initialize() throws SQLException, IOException {
    	tbCabos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	carregarTabela();
    	
    	tfQuantidade.setTextFormatter(quantidadeTF);
    	tfRecebedor.setTextFormatter(recebedorTF);
    }
    
    /**
     * Método para descartar os Cabos selecionados na tabela.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDescartarOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<Cabo> selecionados = tbCabos.getSelectionModel().getSelectedItems();
    	
    	if (tfQuantidade.getText() == null || tfQuantidade.getText().trim().isEmpty() || selecionados.isEmpty() || testeNegatividade()) {
    		String erro = "";
    		if (tfQuantidade.getText() == null || tfQuantidade.getText().trim().isEmpty())
    			erro += "Insira uma quantidade de cabos a serem descartados no campo \"Quantidade\".\n";
    		if (selecionados.isEmpty()) erro += "Selecione ao menos um tipo de cabo a ser descartado.\n";
    		if (testeNegatividade()) erro += "A quantidade de Cabos não pode ficar negativa.";
    		
    		Main.infoDialog("Erro", erro);
    		return;
    	} else {
    		String descarte = "Cabos:\n";
	    	for (Cabo i : selecionados) {
	    		descarte += "- (" + tfQuantidade.getText() + ") " + i.getTipo() + "\n";
	    	}
	    	
    		if (Main.confirmDialog("Confirmação", "Tem certeza que deseja descartar\n\n" + descarte)) {
    			GerenciarCaboJDBCDAO daoCabo = new GerenciarCaboJDBCDAO();
    			GerenciarDescartesJDBCDAO daoDesc = new GerenciarDescartesJDBCDAO();
    			
    	    	int resp = daoCabo.remover(selecionados, Integer.parseInt(tfQuantidade.getText()));
    		    if (resp > 0) {
    		    	Main.infoDialog("Aviso", resp + " Cabo(s) foi(ram) descartado(s).");
    		    	daoDesc.adicionar(descarte);
    		    }
    		    else Main.infoDialog("Erro", "Ocorreu um erro ao descartar " + resp + " Cabo(s).\nVerifique os valores e tente novamente.");
    		    tfQuantidade.clear();
    		    carregarTabela();
    		}
    	}
    }
    
    /**
     * Método para doar os cabos selecionados na tabela.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDoarOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<Cabo> selecionados = tbCabos.getSelectionModel().getSelectedItems();
    	
    	if (tfQuantidade.getText() == null || tfQuantidade.getText().trim().isEmpty() ||
    	tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty() ||
    	selecionados.isEmpty() || testeNegatividade()) {
    		String erro = "";
    		if (selecionados.isEmpty()) erro += "Escolha ao menos um tipo de cabo.\n";
    		if (tfQuantidade.getText() == null || tfQuantidade.getText().trim().isEmpty()) erro += "Informe a quantidade.\n";
    		if (tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty()) erro += "Informe o recebedor.\n";
    		if (testeNegatividade()) erro += "A quantidade de Cabos não pode ficar negativa.";
    		
    		Main.infoDialog("Erro", erro);
    		return;
    	} else {
    		String doacao = "Cabos:\n";
	    	for (Cabo i : selecionados) {
	    		doacao += "- (" + tfQuantidade.getText() + ") " + i.getTipo() + "\n";
	    	}
	    	
	    	if (Main.confirmDialog("Confirmação", "Tem certeza que deseja doar\n\n" + doacao + "\nPara: " + tfRecebedor.getText())) {
	    		GerenciarCaboJDBCDAO daoCabo = new GerenciarCaboJDBCDAO();
    	    	GerenciarDoacoesJDBCDAO daoDoacao = new GerenciarDoacoesJDBCDAO();
    	    	
    	    	int resp = daoCabo.remover(selecionados, Integer.parseInt(tfQuantidade.getText()));
    	    	if (resp > 0) {
    	    		Main.infoDialog("Aviso", resp + " Cabo(s) foi(ram) doado(s).");
    	    		daoDoacao.adicionar(doacao, tfRecebedor.getText());
    	    	}
    	    	else Main.infoDialog("Erro", "Ocorreu um erro ao doar " + resp + " Cabo(s).\nVerifique os valores e tente novamente.");
        	    tfQuantidade.clear();
        	    tfRecebedor.clear();
        	    carregarTabela();
	    	}
    	}
    }

    /**
     * Método para carregar/atualizar a tabela de cabos.
     * @throws SQLException
     * @throws IOException 
     */
    private void carregarTabela() throws SQLException, IOException {
    	GerenciarCaboJDBCDAO daoCabo = new GerenciarCaboJDBCDAO();
    	List<Cabo> cabos = daoCabo.load();
    	ObservableList<Cabo> listCabo = FXCollections.observableArrayList(cabos);
    		
    	tbCabos.setItems(listCabo);
    	IDcolumn.setCellValueFactory(new PropertyValueFactory<Cabo, Integer>("caboId"));
    	tipoColumn.setCellValueFactory(new PropertyValueFactory<Cabo, String>("tipo"));
    	quantidadeColumn.setCellValueFactory(new PropertyValueFactory<Cabo, Integer>("quantidade"));
    }
    
    /**
     * Método para testar se a quantidade inserida viola a condição de não negatividade na quantia de cabos armazenados.
     * @return true se violar, false se não violar.
     * @throws SQLException
     * @throws IOException
     */
    private Boolean testeNegatividade() throws SQLException, IOException {
    	ObservableList<Cabo> selecionados = tbCabos.getSelectionModel().getSelectedItems();
    	GerenciarCaboJDBCDAO daoCabo = new GerenciarCaboJDBCDAO();
    	
    	if (selecionados.isEmpty() || (tfQuantidade.getText() == null || tfQuantidade.getText().trim().isEmpty())) return true;
    	else {
			int x = 0;
			for (Cabo i : selecionados) {
				if ((daoCabo.buscar(i.getTipo()).getQuantidade() - Integer.parseInt(tfQuantidade.getText())) < 0) {
					x++;
				}
			}
			return (x > 0) ? true : false;
    	}
    }
}
