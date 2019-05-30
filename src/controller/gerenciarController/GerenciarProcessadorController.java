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
import dao.gerenciarDao.GerenciarProcessadorJDBCDAO;
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
import model.Processador;

/**
 * Classe controladora da tela GerenciarProcessador.
 * @author Leonardo Elnisky.
 */
public class GerenciarProcessadorController {
    @FXML private BorderPane bPaneProcessadores;
    @FXML private JFXTextField tfBusca;
    @FXML private JFXButton btnDescartar;
    @FXML private TableView<Processador> tbProcessadores;
    @FXML private TableColumn<Processador, Integer> IDColumn;
    @FXML private TableColumn<Processador, Integer> gabineteColumn;
    @FXML private TableColumn<Processador, Integer> notebookColumn;
    @FXML private TableColumn<Processador, String> marcaColumn;
    @FXML private TableColumn<Processador, String> modeloColumn;
    @FXML private TableColumn<Processador, String> socketColumn;
    @FXML private TableColumn<Processador, String> doadorColumn;
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
    	tbProcessadores.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	carregarTabela(filtrarLista());
    }
    
    /**
     * Método para descartar todos os Processadores selecionados na tabela.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDescartarOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<Processador> selecionados = tbProcessadores.getSelectionModel().getSelectedItems();
    	
    	if (selecionados.isEmpty()) {
    		Main.infoDialog("Erro", "Selecione ao menos um Processador a ser descartado.");
    	} else {
    		String descarte = "Processador(es) (" + selecionados.size() + "):\n";
			for (Processador i : selecionados) {
				descarte += "- " + i.getMarca() + " " + i.getModelo() + " socket " + i.getSocket() +  "\n";
			}
        			
			if (Main.confirmDialog("Confirmação", "Tem certeza que deseja descartar\n\n" + descarte)) {
				GerenciarProcessadorJDBCDAO daoProces = new GerenciarProcessadorJDBCDAO();
        		GerenciarDescartesJDBCDAO daoDesc = new GerenciarDescartesJDBCDAO();
        			
        		int resp = daoProces.remover(selecionados);
        		if (resp > 0) {
        			Main.infoDialog("Aviso", resp + " Processador(es) foi(ram) doado(s).");
        			daoDesc.adicionar(descarte);
        		} else Main.infoDialog("Erro", "Ocorreu um erro ao descartar " + resp + " Processador(es).\nVerifique os valores e tente novamente.");
        		carregarTabela(filtrarLista());
			}
    	}
    }
    
    /**
     * Método para doar todos os Processadores selecionados na tabela.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDoarOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<Processador> selecionados = tbProcessadores.getSelectionModel().getSelectedItems();
    	
    	if (tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty() || selecionados.isEmpty()) {
    		String erro = "";
    		if (selecionados.isEmpty()) erro += "Escolha ao menos um processador.\n";
    		if (tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty()) erro += "Informe o recebedor.\n";
    		
    		Main.infoDialog("Erro", erro);
    	} else {
	    	String doacao = "Processadores (" + selecionados.size() + "):\n";
	    	for (Processador i : selecionados) {
	    		doacao += "- " + i.getMarca() + " " + i.getModelo() + " socket " + i.getSocket() + "\n";
	    	}
	    	
	    	if (Main.confirmDialog("Confirmação", "Tem certeza que deseja doar\n\n" + doacao + "\nPara: " + tfRecebedor.getText())) {
			    GerenciarProcessadorJDBCDAO daoProces = new GerenciarProcessadorJDBCDAO();
			    GerenciarDoacoesJDBCDAO daoDoacao = new GerenciarDoacoesJDBCDAO();
			    	
			    int resp = daoProces.remover(selecionados);
			    if (resp > 0) {
			    	Main.infoDialog("Aviso", resp + " Processador(es) foi(ram) doado(s).");
			    	daoDoacao.adicionar(doacao, tfRecebedor.getText());
			    } else Main.infoDialog("Erro", "Ocorreu um erro ao doar " + resp + " Processador(es).\nVerifique os valores e tente novamente.");
			    carregarTabela(filtrarLista());
	    	}
    	}
    }

    /**
     * Método para carregar e converter a lista de processadores do banco em uma lista filtrável.
     * @return filteredProces lista filtrável de todos os processadores do banco de dados.
     * @throws SQLException
     * @throws IOException 
     */
    private FilteredList<Processador> filtrarLista() throws SQLException, IOException {
    	GerenciarProcessadorJDBCDAO daoProces = new GerenciarProcessadorJDBCDAO();
    	List<Processador> proces = daoProces.load();
    	ObservableList<Processador> listProces = FXCollections.observableArrayList(proces);
    	FilteredList<Processador> filteredProces = new FilteredList<Processador>(listProces, p -> true);
    	
    	tfBusca.textProperty().addListener((observable, oldValue, newValue) -> {
    		filteredProces.setPredicate(proc -> {
    			if (newValue == null || newValue.isEmpty()) return true;
    			
    			String text = newValue.toLowerCase();
    			if (proc.getMarca().toLowerCase().contains(text)) return true;
    			if (proc.getModelo().toLowerCase().contains(text)) return true;
    			if (proc.getSocket().toLowerCase().contains(text)) return true;
    			if (proc.getDoador().toLowerCase().contains(text)) return true;
    			
    			return false;
    		});
    	});
    	return filteredProces;
    }
    
    /**
     * Método para carregar/atualizar a tabela de processadores.
     * @param listProces recebe a lista com os processadores para serem exibidos na tabela.
     * @throws SQLException
     */
    private void carregarTabela(ObservableList<Processador> listProces) throws SQLException {
    	IDColumn.setCellValueFactory(new PropertyValueFactory<Processador, Integer>("processadorId"));
    	gabineteColumn.setCellValueFactory(new PropertyValueFactory<Processador, Integer>("gabineteId"));
    	notebookColumn.setCellValueFactory(new PropertyValueFactory<Processador, Integer>("notebookId"));
    	marcaColumn.setCellValueFactory(new PropertyValueFactory<Processador, String>("marca"));
    	modeloColumn.setCellValueFactory(new PropertyValueFactory<Processador, String>("modelo"));
    	socketColumn.setCellValueFactory(new PropertyValueFactory<Processador, String>("socket"));
    	doadorColumn.setCellValueFactory(new PropertyValueFactory<Processador, String>("doador"));
    	tbProcessadores.setItems(listProces);
    }
}
