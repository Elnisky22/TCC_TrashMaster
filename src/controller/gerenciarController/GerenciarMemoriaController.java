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
import dao.gerenciarDao.GerenciarMemoriaJDBCDAO;
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
import model.Memoria;

/**
 * Classe controladora da tela GerenciarMemoria.
 * @author Leonardo Elnisky.
 */
public class GerenciarMemoriaController {
    @FXML private BorderPane bPaneTabelas;
    @FXML private JFXTextField tfBusca;
    @FXML private JFXButton btnDescarte;
    @FXML private TableView<Memoria> tbMemorias;
    @FXML private TableColumn<Memoria, Integer> IDColumn;
    @FXML private TableColumn<Memoria, Integer> gabineteColumn;
    @FXML private TableColumn<Memoria, Integer> notebookColumn;
    @FXML private TableColumn<Memoria, String> marcaColumn;
    @FXML private TableColumn<Memoria, String> modeloColumn;
    @FXML private TableColumn<Memoria, String> tipoColumn;
    @FXML private TableColumn<Memoria, String> memoriaColumn;
    @FXML private TableColumn<Memoria, String> doadorColumn;
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
    	tbMemorias.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	carregarTabela(filtrarLista());
    }
    
    /**
     * Método para descartar todas as Memórias selecionadas na tabela.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDescarteOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<Memoria> selecionados = tbMemorias.getSelectionModel().getSelectedItems();
    	
    	if (selecionados.isEmpty()) {
    		Main.infoDialog("Erro", "Selecione ao menos uma Memória a ser descartada.");
    	} else {
    		String descarte = "Memória(s) (" + selecionados.size() + "):\n";
    		for (Memoria i : selecionados) {
    			descarte += "- " + i.getMarca() + " " + i.getModelo() + " " + i.getMemoria() + "GBs\n"; 
    		}
    		
    		if (Main.confirmDialog("Confirmação", "Tem certeza que deseja doar\n\n" + descarte)) {
        		GerenciarMemoriaJDBCDAO daoDisco = new GerenciarMemoriaJDBCDAO();
        		GerenciarDescartesJDBCDAO daoDesc = new GerenciarDescartesJDBCDAO();
        			
        		int resp = daoDisco.remover(selecionados);
        		if (resp > 0) {
        			Main.infoDialog("Aviso", resp + " Memória(s) foi(ram) descartada(s).");
        			daoDesc.adicionar(descarte);
        		} else Main.infoDialog("Erro", "Ocorreu um erro ao descartar " + resp + " Memória(s).\nVerifique os valores e tente novamente.");
        		carregarTabela(filtrarLista());
    		}
    	}
    }
    
    /**
     * Método para doar todas as Memórias selecionadas na tabela.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDoarOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<Memoria> selecionados = tbMemorias.getSelectionModel().getSelectedItems();
    	
    	if (tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty() || selecionados.isEmpty()) {
    		String erro = "";
    		if (selecionados.isEmpty()) erro += "Escolha ao menos uma memória.\n";
    		if (tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty()) erro += "Informe o recebedor.\n";

    		Main.infoDialog("Erro", erro);
    	} else {
    		String doacao = "Memória(s) (" + selecionados.size() + "):\n";
        	for (Memoria i : selecionados) {
        		doacao += "- " + i.getMarca() + " " + i.getModelo() + " " + i.getMemoria() + "Gbs\n";
        	}
        	if (Main.confirmDialog("Confirmação", "Tem certeza que deseja doar\n\n" + doacao + "\nPara: " + tfRecebedor.getText())) {
			    GerenciarMemoriaJDBCDAO daoMem = new GerenciarMemoriaJDBCDAO();
			    GerenciarDoacoesJDBCDAO daoDoacao = new GerenciarDoacoesJDBCDAO();
		    	
			    int resp = daoMem.remover(selecionados);
			    if (resp > 0) {
			    	Main.infoDialog("Aviso", resp + " Memória(s) foi(ram) doada(s).");
			    	daoDoacao.adicionar(doacao, tfRecebedor.getText());
			    } else Main.infoDialog("Erro", "Ocorreu um erro ao doar " + resp + " Memória(s).\nVerifique os valores e tente novamente.");
				carregarTabela(filtrarLista());
        	}
    	}
    }

    /**
     * Método para carregar e converter a lista de discos do banco em uma lista filtrável.
     * @return filteredDiscos lista filtrável de todos os discos do banco de dados.
     * @throws SQLException
     * @throws IOException 
     */
    private FilteredList<Memoria> filtrarLista() throws SQLException, IOException {
    	GerenciarMemoriaJDBCDAO daoMem = new GerenciarMemoriaJDBCDAO();
    	List<Memoria> mems = daoMem.load();
    	ObservableList<Memoria> listMem = FXCollections.observableArrayList(mems);
    	FilteredList<Memoria> filteredMem = new FilteredList<Memoria>(listMem, p -> true);
    	
    	tfBusca.textProperty().addListener((observable, oldValue, newValue) -> {
    		filteredMem.setPredicate(mem -> {
    			if (newValue == null || newValue.isEmpty()) return true;
    			
    			String text = newValue.toLowerCase();
    			if (mem.getMarca().toLowerCase().contains(text)) return true;
    			if (mem.getModelo().toLowerCase().contains(text)) return true;
    			if (mem.getTipo().toLowerCase().contains(text)) return true;
    			if (String.valueOf(mem.getMemoria()).toLowerCase().contains(text)) return true;
    			if (mem.getDoador().toLowerCase().contains(text)) return true;
    			
    			return false;
    		});
    	});
    	
    	return filteredMem;
    }
    
    /**
     * Método para carregar/atualizar a tabela de memórias.
     * @param listMem recebe a lista com as memórias a serem exibidas na tabela.
     * @throws SQLException
     */
    private void carregarTabela(ObservableList<Memoria> listMem) throws SQLException {
    	IDColumn.setCellValueFactory(new PropertyValueFactory<Memoria, Integer>("memoriaId"));
    	gabineteColumn.setCellValueFactory(new PropertyValueFactory<Memoria, Integer>("gabineteId"));
    	notebookColumn.setCellValueFactory(new PropertyValueFactory<Memoria, Integer>("notebookId"));
    	marcaColumn.setCellValueFactory(new PropertyValueFactory<Memoria, String>("marca"));
    	modeloColumn.setCellValueFactory(new PropertyValueFactory<Memoria, String>("modelo"));
    	tipoColumn.setCellValueFactory(new PropertyValueFactory<Memoria, String>("tipo"));
    	memoriaColumn.setCellValueFactory(new PropertyValueFactory<Memoria, String>("memoria"));
    	doadorColumn.setCellValueFactory(new PropertyValueFactory<Memoria, String>("doador"));
    	tbMemorias.setItems(listMem);
    }
    
}
