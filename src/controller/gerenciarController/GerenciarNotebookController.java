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
import dao.gerenciarDao.GerenciarNotebookJDBCDAO;
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
import model.Notebook;

/**
 * Classe controladora da tela GerenciarNotebook.
 * @author Leonardo Elnisky.
 */
public class GerenciarNotebookController {
    @FXML private BorderPane bPaneNotebook;
    @FXML private JFXTextField tfBusca;
    @FXML private JFXButton btnDescartar;
    @FXML private TableView<Notebook> tfNotebooks;
    @FXML private TableColumn<Notebook, Integer> IDColumn;
    @FXML private TableColumn<Notebook, String> marcaColumn;
    @FXML private TableColumn<Notebook, String> modeloColumn;
    @FXML private TableColumn<Notebook, String> doadorColumn;
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
    	tfNotebooks.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	carregarTabela(filtrarLista());
    }
    
    /**
     * Método para descartar todos os Notebooks selecionados na tabela.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDescartarOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<Notebook> selecionados = tfNotebooks.getSelectionModel().getSelectedItems();
    	
    	if (selecionados.isEmpty()) {
    		Main.infoDialog("Erro", "Selecione ao menos um Notebook a ser descartado.");
    	} else {
    		String descarte = "Notebook(es) (" + selecionados.size() + "):\n";
    		for (Notebook i : selecionados) {
    			descarte += "- " + i.getMarca() + " " + i.getModelo() + "\n";
    		}
    		
    		if (Main.confirmDialog("Confirmação", "Tem certeza que deseja descartar\n\n" + descarte)) {
        		GerenciarNotebookJDBCDAO daoNote = new GerenciarNotebookJDBCDAO();
        		GerenciarDescartesJDBCDAO daoDesc = new GerenciarDescartesJDBCDAO();
        			
        		int resp = daoNote.remover(selecionados);
        		if (resp > 0) {
        			Main.infoDialog("Aviso", resp + " Notebook(s) foi(ram) descartado(s).");
        			daoDesc.adicionar(descarte);
        		} else Main.infoDialog("Erro", "Ocorreu um erro ao descartar " + resp + " Notebook(s).\nVerifique os valores e tente novamente.");
        		carregarTabela(filtrarLista());
    		}
    	}
    }
    
    /**
     * Método para doar todos os Notebooks selecionados na tabela.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDoarOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<Notebook> selecionados = tfNotebooks.getSelectionModel().getSelectedItems();
    	
    	if (tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty() || selecionados.isEmpty()) {
    		String erro = "";
    		if (selecionados.isEmpty()) erro += "Escolha ao menos um Notebook.\n";
    		if (tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty()) erro += "Informe o recebedor.\n";

    		Main.infoDialog("Erro", erro);
    	} else {
    		String doacao = "Notebooks (" + selecionados.size() + "):\n";
        	for (Notebook i : selecionados) {
        		doacao += "- " + i.getMarca() + " " + i.getModelo() + "\n";
        	}
        	
        	if (Main.confirmDialog("Confirmação", "Tem certeza que deseja doar\n\n" + doacao + "\nPara: " + tfRecebedor.getText())) {
		    	GerenciarNotebookJDBCDAO daoNote = new GerenciarNotebookJDBCDAO();
		        GerenciarDoacoesJDBCDAO daoDoacao = new GerenciarDoacoesJDBCDAO();
		        
		        int resp = daoNote.remover(selecionados);
		        if (resp > 0) {
		        	Main.infoDialog("Aviso", resp + " Notebook(s) foi(ram) doado(s)");
		    	    daoDoacao.adicionar(doacao, tfRecebedor.getText());
		        } else Main.infoDialog("Erro", "Ocorreu um erro ao doar " + resp + " Notebook(s).\nVerifique os valores e tente novamente.");
		    	carregarTabela(filtrarLista());
        	}
    	}
    }

    /**
     * Método para carregar e converter a lista de notebooks do banco em uma lista filtrável.
     * @return filteredNotes lista filtrável de todos os notebooks do banco de dados.
     * @throws SQLException
     * @throws IOException 
     */
    private FilteredList<Notebook> filtrarLista() throws SQLException, IOException {
    	GerenciarNotebookJDBCDAO daoNote = new GerenciarNotebookJDBCDAO();
    	List<Notebook> notes = daoNote.load();
    	ObservableList<Notebook> listNotes = FXCollections.observableArrayList(notes);
    	FilteredList<Notebook> filteredNotes = new FilteredList<Notebook>(listNotes, p -> true);
    	
    	tfBusca.textProperty().addListener((observable, oldValue, newValue) -> {
    		filteredNotes.setPredicate(note -> {
    			if (newValue == null || newValue.isEmpty()) return true;
    			
    			String text = newValue.toLowerCase();
    			if (note.getMarca().toLowerCase().contains(text)) return true;
    			if (note.getModelo().toLowerCase().contains(text)) return true;
    			if (note.getDoador().toLowerCase().contains(text)) return true;
    			
    			return false;
    		});
    	});
    	return filteredNotes;
    }
    
    /**
     * Método para carregar/atualizar a tabela de notebooks.
     * @param listNotes recebe a lista com os notebooks para serem exibidos na tabela.
     * @throws SQLException
     */
    private void carregarTabela(ObservableList<Notebook> listNotes) throws SQLException {
    	IDColumn.setCellValueFactory(new PropertyValueFactory<Notebook, Integer>("computadorId"));
    	marcaColumn.setCellValueFactory(new PropertyValueFactory<Notebook, String>("marca"));
    	modeloColumn.setCellValueFactory(new PropertyValueFactory<Notebook, String>("modelo"));
    	doadorColumn.setCellValueFactory(new PropertyValueFactory<Notebook, String>("doador"));
    	tfNotebooks.setItems(listNotes);
    }
}
