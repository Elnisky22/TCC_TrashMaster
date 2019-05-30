package controller.gerenciarController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.function.UnaryOperator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import application.Main;
import dao.gerenciarDao.GerenciarDescartesJDBCDAO;
import dao.gerenciarDao.GerenciarDoacoesJDBCDAO;
import dao.gerenciarDao.GerenciarLeitorJDBCDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import model.LeitorDisco;

/**
 * Classe controladora da tela GerenciarLeitor.
 * @author Leonardo Elnisky.
 */
public class GerenciarLeitorController {
    @FXML private BorderPane bPaneLeitores;
    @FXML private JFXTextField tfBusca;
    @FXML private JFXButton btnDescartar;
    @FXML private TableView<LeitorDisco> tbLeitores;
    @FXML private TableColumn<LeitorDisco, Integer> IDColumn;
    @FXML private TableColumn<LeitorDisco, Integer> gabineteColumn;
    @FXML private TableColumn<LeitorDisco, Integer> notebookColumn;
    @FXML private TableColumn<LeitorDisco, String> marcaColumn;
    @FXML private TableColumn<LeitorDisco, String> modeloColumn;
    @FXML private TableColumn<LeitorDisco, String> doadorColumn;
    @FXML private JFXTextField tfRecebedor;
    @FXML private JFXButton btnDoar;
    
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
    	tfRecebedor.setTextFormatter(recebedorTF);
    	tbLeitores.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	carregarTabela(filtrarLista());
    }
    
    /**
     * Método para descartar todos os Leitores selecionados na tabela.
     * @param event recebe o evento ocorrido. 
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDescartarOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<LeitorDisco> selecionados = tbLeitores.getSelectionModel().getSelectedItems();
    	
    	if (selecionados.isEmpty()) {
    		Main.infoDialog("Erro", "Selecione ao menos um Leitor de Disco a ser descartado.");
    	} else {
    		String descarte = "Leitor(es) de disco (" + selecionados.size() + "):\n";
    		for (LeitorDisco i : selecionados) {
    			descarte += "- " + i.getMarca() + " " + i.getModelo() + "\n";
    		}
    		
    		if (Main.confirmDialog("Confirmação", "Tem certeza que deseja descartar\n\n" + descarte)) {
        		GerenciarLeitorJDBCDAO daoLeitor = new GerenciarLeitorJDBCDAO();
        		GerenciarDescartesJDBCDAO daoDesc = new GerenciarDescartesJDBCDAO();
        		
        		int resp = daoLeitor.remover(selecionados);
        		if (resp > 0){
        			Main.infoDialog("Aviso", resp + " Leitor(es) de Disco foi(ram) descartado(s).");
        			daoDesc.adicionar(descarte);
        		} else Main.infoDialog("Erro", "Ocorreu um erro ao descartar " + resp + " Leitor(es) de Disco.\nVerifique os valores e tente novamente.");
        		carregarTabela(filtrarLista());
    		}
    	}
    }
    
    /**
     * Método para doar todos os Leitores selecionados na tabela.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDoarOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<LeitorDisco> selecionados = tbLeitores.getSelectionModel().getSelectedItems();
    	
    	if (tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty() || selecionados.isEmpty()) {
    		String erro = "";
    		if (selecionados.isEmpty()) erro += "Escolha ao menos um Leitor de Disco.\n";
    		if (tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty()) erro += "Informe o recebedor.\n";

    		Main.infoDialog("Erro", erro);
    	} else {
    		String doacao = "Leitor(es) de Disco (" + selecionados.size() + "):\n";
	    	for (LeitorDisco i : selecionados) {
	    		doacao += "- " + i.getMarca() + " " + i.getModelo() + "\n";
	    	}
    		if (Main.confirmDialog("Confirmação", "Tem certeza que deseja doar\n\n" + doacao + "\nPara: " + tfRecebedor.getText())) {
        			GerenciarLeitorJDBCDAO daoLeitor = new GerenciarLeitorJDBCDAO();
        	    	GerenciarDoacoesJDBCDAO daoDoacao = new GerenciarDoacoesJDBCDAO();
        	    	
        	    	int resp = daoLeitor.remover(selecionados);
	        	    if (resp > 0) {
	        	    	Main.infoDialog("Aviso", resp + " Leitor(es) de Disoc foi(ram) doado(s).");
	        	    	daoDoacao.adicionar(doacao, tfRecebedor.getText());
	        	    } else Main.infoDialog("Erro", "Ocorreu um erro ao doar " + resp + " Leitor(es) de Disco.\nVerifique os valores e tente novamente.");
        	    	carregarTabela(filtrarLista());
    		}
    	}
    }

    /**
     * Método para carregar e converter a lista de leitores do banco em uma lista filtrável.
     * @return filteredLeitores lista filtrável de todos os leitores do banco de dados.
     * @throws SQLException
     * @throws IOException 
     */
    private FilteredList<LeitorDisco> filtrarLista() throws SQLException, IOException {
    	GerenciarLeitorJDBCDAO daoLeitor = new GerenciarLeitorJDBCDAO();
    	List<LeitorDisco> leitores = daoLeitor.load();
    	ObservableList<LeitorDisco> listLeitores = FXCollections.observableArrayList(leitores);
    	FilteredList<LeitorDisco> filteredLeitores = new FilteredList<LeitorDisco>(listLeitores, p -> true);
    	
    	tfBusca.textProperty().addListener((observable, oldValue, newValue) -> {
    		filteredLeitores.setPredicate(leitor -> {
    			if (newValue == null || newValue.isEmpty()) return true;
    			
    			String text = newValue.toLowerCase();
    			if (leitor.getMarca().toLowerCase().contains(text)) return true;
    			if (leitor.getModelo().toLowerCase().contains(text)) return true;
    			if (leitor.getDoador().toLowerCase().contains(text)) return true;
    			
    			return false;
    		});
    	});
    	
    	return filteredLeitores;
    }
    
    /**
     * Método para carregar/atualizar a tabela de leitores.
     * @param listLeitores recebe a lista com os leitores para serem exibidos na tabela.
     * @throws SQLException
     */
    private void carregarTabela(ObservableList<LeitorDisco> listLeitores) throws SQLException {
    	IDColumn.setCellValueFactory(new PropertyValueFactory<LeitorDisco, Integer>("leitorId"));
    	gabineteColumn.setCellValueFactory(new PropertyValueFactory<LeitorDisco, Integer>("gabineteId"));
    	notebookColumn.setCellValueFactory(new PropertyValueFactory<LeitorDisco, Integer>("notebookId"));
    	marcaColumn.setCellValueFactory(new PropertyValueFactory<LeitorDisco, String>("marca"));
    	modeloColumn.setCellValueFactory(new PropertyValueFactory<LeitorDisco, String>("modelo"));
    	doadorColumn.setCellValueFactory(new PropertyValueFactory<LeitorDisco, String>("doador"));
    	tbLeitores.setItems(listLeitores);
    }
}
