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
import dao.gerenciarDao.GerenciarMonitorJDBCDAO;
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
import model.Monitor;

/**
 * Classe controladora da tela GerenciarMonitor.
 * @author Leonardo Elnisky.
 */
public class GerenciarMonitorController {
    @FXML private BorderPane bPaneMonitores;
    @FXML private JFXTextField tfBusca;
    @FXML private JFXButton btnDescarte;
    @FXML private TableView<Monitor> tbMonitores;
    @FXML private TableColumn<Monitor, Integer> IDColumn;
    @FXML private TableColumn<Monitor, String> marcaColumn;
    @FXML private TableColumn<Monitor, String> modeloColumn;
    @FXML private TableColumn<Monitor, String> daodorColumn;
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
    	tbMonitores.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	carregarTabela(filtrarLista());
    }
    
    /**
     * Método para descartar todos os Monitores selecionados na tabela.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDescarteOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<Monitor> selecionados = tbMonitores.getSelectionModel().getSelectedItems();
    	
    	if (selecionados.isEmpty()) {
    		Main.infoDialog("Erro", "Selecione ao menos um Monitor a ser descartado.");
    	} else {
    		String descarte = "Monitor(es) (" + selecionados.size() + "):\n";
    		for (Monitor i : selecionados) {
    			descarte += "- " + i.getMarca() + " " + i.getModelo() + "\n"; 
    		}
    		
    		if (Main.confirmDialog("Confirmação", "Tem certeza que deseja descartar\n\n" + descarte)) {
        		GerenciarMonitorJDBCDAO daoMonitor = new GerenciarMonitorJDBCDAO();
        		GerenciarDescartesJDBCDAO daoDesc = new GerenciarDescartesJDBCDAO();
        			
        		int resp = daoMonitor.remover(selecionados);
        		if (resp > 0) {
        			Main.infoDialog("Aviso", resp + " Monitor(es) foi(ram) descartado(s).");
        			daoDesc.adicionar(descarte);
        		} else Main.infoDialog("Erro", "Ocorreu um erro ao descartar " + resp + " Monitor(es).\nVerifique os valores e tente novamente.");
        		carregarTabela(filtrarLista());
        	}
    	}
    }
    
    /**
     * Método para doar todos os Monitores selecionados na tabela.
     * @param event recebe o evento ocorrido. 
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDoarOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<Monitor> selecionados = tbMonitores.getSelectionModel().getSelectedItems();
    	
    	if (tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty() || selecionados.isEmpty()) {
    		String erro = "";
    		if (selecionados.isEmpty()) erro += "Escolha ao menos um monitor.\n";
    		if (tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty()) erro += "Informe o recebedor.\n";

    		Main.infoDialog("Erro", erro);
    	} else {
	    	String doacao = "Monitor(es) (" + selecionados.size() + "):\n";
	    	for (Monitor i : selecionados) {
	    		doacao += "- " + i.getMarca() + " " + i.getModelo() + "\n";
	    	}
	    	if (Main.confirmDialog("Confirmação", "Tem certeza que deseja doar\n\n" + doacao + "\nPara: " + tfRecebedor.getText())) {
	    		GerenciarMonitorJDBCDAO daoMon = new GerenciarMonitorJDBCDAO();
	    		GerenciarDoacoesJDBCDAO daoDoacao = new GerenciarDoacoesJDBCDAO();
			    	
			    int resp = daoMon.remover(selecionados);
			    if (resp > 0) {
			    	Main.infoDialog("Aviso", resp + " Monitor(es) foi(ram) doado(s).");
			    	daoDoacao.adicionar(doacao, tfRecebedor.getText());
			    } else Main.infoDialog("Erro", "Ocorreu um erro ao doar " + resp + " Monitor(es).\nVerifique os valores e tente novamente.");
			    carregarTabela(filtrarLista());
	    	}
    	}
    }
    
    /**
     * Método para carregar e converter a lista de monitores do banco em uma lista filtrável.
     * @return filteredMonitores lista filtrável de todos os monitores do banco de dados.
     * @throws SQLException
     * @throws IOException 
     */
    private FilteredList<Monitor> filtrarLista() throws SQLException, IOException {
    	GerenciarMonitorJDBCDAO daoMon = new GerenciarMonitorJDBCDAO();
    	List<Monitor> mons = daoMon.load();
    	ObservableList<Monitor> listMon = FXCollections.observableArrayList(mons);
    	FilteredList<Monitor> filteredMon = new FilteredList<Monitor>(listMon, p -> true);
    	
    	tfBusca.textProperty().addListener((observable, oldValue, newValue) -> {
    		filteredMon.setPredicate(monitor -> {
    			if (newValue == null || newValue.isEmpty()) return true;
    			
    			String text = newValue.toLowerCase();
    			if (monitor.getMarca().toLowerCase().contains(text)) return true;
    			if (monitor.getModelo().toLowerCase().contains(text)) return true;
    			if (monitor.getDoador().toLowerCase().contains(text)) return true;
    			
    			return false;
    		});
    	});
    	
    	return filteredMon;
    }
    
    /**
     * Método para carregar/atualizar a tabela de monitores.
     * @param listDiscos recebe a lista com os monitores para serem exibidos na tabela.
     * @throws SQLException
     */
    private void carregarTabela(ObservableList<Monitor> listMon) throws SQLException {
    	IDColumn.setCellValueFactory(new PropertyValueFactory<Monitor, Integer>("monitorId"));
    	marcaColumn.setCellValueFactory(new PropertyValueFactory<Monitor, String>("marca"));
    	modeloColumn.setCellValueFactory(new PropertyValueFactory<Monitor, String>("modelo"));
    	daodorColumn.setCellValueFactory(new PropertyValueFactory<Monitor, String>("doador"));
    	tbMonitores.setItems(listMon);
    }

}
