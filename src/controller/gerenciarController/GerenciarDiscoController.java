package controller.gerenciarController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.function.UnaryOperator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import application.Main;
import dao.gerenciarDao.GerenciarDescartesJDBCDAO;
import dao.gerenciarDao.GerenciarDiscoJDBCDAO;
import dao.gerenciarDao.GerenciarDoacoesJDBCDAO;
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
import model.DiscoRigido;

/**
 * Classe controladora da tela GerenciarDisco.
 * @author Leonardo Elnisky.
 */
public class GerenciarDiscoController {
    @FXML private BorderPane bPaneTabela;
    @FXML private JFXTextField tfBusca;
    @FXML private JFXButton btnDescarte;
    @FXML private TableView<DiscoRigido> tableDiscos;
    @FXML private TableColumn<DiscoRigido, Integer> IDColumn;
    @FXML private TableColumn<DiscoRigido, Integer> gabineteColumn;
    @FXML private TableColumn<DiscoRigido, Integer> notebookColumn;
    @FXML private TableColumn<DiscoRigido, String> marcaColumn;
    @FXML private TableColumn<DiscoRigido, String> ModeloColum;
    @FXML private TableColumn<DiscoRigido, Double> CapacidadeColumn;
    @FXML private TableColumn<DiscoRigido, String> DoadorComumn;
    @FXML private JFXTextField tfRecebedor;
    @FXML private JFXButton btnDoar;
    
    // Formatar tf recebedor
    TextFormatter<String> recebedorTF = new TextFormatter<>((UnaryOperator<TextFormatter.Change>) change -> {
	    String concatenado = tfRecebedor.getText() + change.getText();
	    return (concatenado.length() < 51) ? change : null;
	});

    /**
     * M�todo para carregar tudo que � necess�rio para iniciar a tela.
     * @throws SQLException
     * @throws IOException 
     */
    @FXML void initialize() throws SQLException, IOException {
    	tfRecebedor.setTextFormatter(recebedorTF);
    	tableDiscos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	carregarTabela(filtrarLista());
    }
    
    /**
     * M�todo para Descartar os Discos R�gidos selecionados na tabela.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDescarteOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<DiscoRigido> selecionados = tableDiscos.getSelectionModel().getSelectedItems();
    	
    	if (selecionados.isEmpty()) {
    		Main.infoDialog("Erro", "Selecione ao menos um Disco R�gido a ser descartado.");
    	} else {
    		String descarte = "Disco(s) R�gido(s) (" + selecionados.size() + "):\n";
			for (DiscoRigido i : selecionados) {
				descarte += "- " + i.getMarca() + " " + i.getModelo() + " " + i.getCapacidade() + "GBs\n"; 
			}
			
    		if (Main.confirmDialog("Confirma��o", "Tem certeza que deseja descartar\n\n" + descarte)) {
    			GerenciarDiscoJDBCDAO daoDisco = new GerenciarDiscoJDBCDAO();
    			GerenciarDescartesJDBCDAO daoDesc = new GerenciarDescartesJDBCDAO();
    			
   				int resp = daoDisco.remover(selecionados);
   				if (resp > 0) {
   					Main.infoDialog("Aviso", resp + " Disco(s) R�gido(s) foi(ram) descartado(s).");
   					daoDesc.adicionar(descarte);
   				} else Main.infoDialog("Erro", "Ocorreu um erro ao descartar " + resp + " Disco(s).\nVerifique os valores e tente novamente.");
   				carregarTabela(filtrarLista());
    		}
    	}
    }
    
    /**
     * M�todo para doar os Discos selecionados na tabela.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDoarOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<DiscoRigido> selecionados = tableDiscos.getSelectionModel().getSelectedItems();
    	
    	if (tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty() || selecionados.isEmpty()) {
    		String erro = "";
    		if (selecionados.isEmpty()) erro += "Escolha ao menos um disco r�gido.\n";
    		if (tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty()) erro += "Informe o recebedor.\n";
    		
    		Main.infoDialog("Erro", erro);
    	} else {
    		String doacao = "Disco(s) R�gido(s) (" + selecionados.size() + "):\n";
	    	for (DiscoRigido i : selecionados) {
	    		doacao += "- " + i.getMarca() + " " + i.getModelo() + " " + i.getCapacidade() + "GBs\n";
	    	}
    		if (Main.confirmDialog("Confirma��o", "Tem certeza que deseja doar\n\n" + doacao + "\nPara: " + tfRecebedor.getText())) {
    			GerenciarDiscoJDBCDAO daoDisco = new GerenciarDiscoJDBCDAO();
		    	GerenciarDoacoesJDBCDAO daoDoacao = new GerenciarDoacoesJDBCDAO();
		    	
		    	int resp = daoDisco.remover(selecionados);
		    	if (resp > 0) {
		    		Main.infoDialog("Aviso", resp + " Disco(s) foi(ram) doado(s).");
		    		daoDoacao.adicionar(doacao, tfRecebedor.getText());
		    	} else Main.infoDialog("Erro", "Ocorreu um erro ao doar " + resp + " Disco(s).\nVerifique os valores e tente novamente.");
			    carregarTabela(filtrarLista());
    		}
    	}
    }

    /**
     * M�todo para carregar e converter a lista de discos do banco em uma lista filtr�vel.
     * @return filteredDiscos lista filtr�vel de todos os discos do banco de dados.
     * @throws SQLException
     * @throws IOException 
     */
    private FilteredList<DiscoRigido> filtrarLista() throws SQLException, IOException {
    	GerenciarDiscoJDBCDAO daoDisco = new GerenciarDiscoJDBCDAO();
    	List<DiscoRigido> discos = daoDisco.load();
    	ObservableList<DiscoRigido> listDiscos = FXCollections.observableArrayList(discos);
    	FilteredList<DiscoRigido> filteredDiscos = new FilteredList<DiscoRigido>(listDiscos, p -> true);
    	
    	// Adicionar o listener ao textfield para filtrar
    	tfBusca.textProperty().addListener((observable, oldValue, newValue) -> {
    		filteredDiscos.setPredicate(disco -> {
    			if (newValue == null || newValue.isEmpty()) return true;
    			
    			String text = newValue.toLowerCase();
    			if (disco.getMarca().toLowerCase().contains(text)) return true;
    			if (disco.getModelo().toLowerCase().contains(text)) return true;
    			if (disco.getDoador().toLowerCase().contains(text)) return true;
    			if (String.valueOf(disco.getCapacidade()).toLowerCase().contains(text)) return true;
    			
    			return false;
    		});
    	});
    	return filteredDiscos;
    }
    
    /**
     * M�todo para carregar/atualizar a tabela de discos.
     * @param listDiscos recebe a lista com os discos para serem exibidos na tabela.
     * @throws SQLException
     */
    private void carregarTabela(ObservableList<DiscoRigido> listDiscos) throws SQLException {
    	IDColumn.setCellValueFactory(new PropertyValueFactory<DiscoRigido, Integer>("discoId"));
    	gabineteColumn.setCellValueFactory(new PropertyValueFactory<DiscoRigido, Integer>("gabineteId"));
    	notebookColumn.setCellValueFactory(new PropertyValueFactory<DiscoRigido, Integer>("notebookId"));
    	marcaColumn.setCellValueFactory(new PropertyValueFactory<DiscoRigido, String>("marca"));
    	ModeloColum.setCellValueFactory(new PropertyValueFactory<DiscoRigido, String>("modelo"));
    	CapacidadeColumn.setCellValueFactory(new PropertyValueFactory<DiscoRigido, Double>("capacidade"));
    	DoadorComumn.setCellValueFactory(new PropertyValueFactory<DiscoRigido, String>("doador"));
    	tableDiscos.setItems(listDiscos);
    }
    
}
