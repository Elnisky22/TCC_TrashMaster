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
import dao.gerenciarDao.GerenciarOutrosJDBCDAO;
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
import model.Outros;

/**
 * Classe controladora da tela GerenciarOutros.
 * @author Leonardo Elnisky.
 */
public class GerenciarOutrosController {
    @FXML private BorderPane bPaneOutros;
    @FXML private JFXTextField tfBusca;
    @FXML private JFXButton btnDescartar;
    @FXML private TableView<Outros> tbOutros;
    @FXML private TableColumn<Outros, Integer> IDColumn;
    @FXML private TableColumn<Outros, String> descColumn;
    @FXML private TableColumn<Outros, String> doadorColumn;
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
    	tbOutros.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	carregarTabela(filtrarLista());
    }

    /**
     * Método para descartar todos os "Outros" selecionados.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDescartarOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<Outros> selecionados = tbOutros.getSelectionModel().getSelectedItems();
    	
    	if (selecionados.isEmpty()) {
    		Main.infoDialog("Erro", "Selecione ao menos um \"Outros\" a ser descartado.");
    	} else {
    		String descarte = "Outros (" + selecionados.size() + "):\n";
    		for (Outros i : selecionados) {
    			descarte += "- " + i.getDescricao() + " - Doador: " + i.getDoador() + "\n";
    		}
    		
    		if (Main.confirmDialog("Confirmação", "Tem certeza que deseja descartar\n\n" + descarte)) {
        		GerenciarOutrosJDBCDAO daoOutros = new GerenciarOutrosJDBCDAO();
        		GerenciarDescartesJDBCDAO daoDesc = new GerenciarDescartesJDBCDAO();
        		
        		int resp = daoOutros.remover(selecionados);
        		if (resp > 0) {
        			Main.infoDialog("Aviso", resp + " \"Outros\" foi(ram) descartado(s).");
        			daoDesc.adicionar(descarte);
        		} else Main.infoDialog("Erro", "Ocorreu um erro ao descartar " + resp + " \"Outros\".\nVerifique os valores e tente novamente.");
        		carregarTabela(filtrarLista());
    		}
    	}
    }
    
    /**
     * Método para doar todos os "Outros" selecionados.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDoarOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<Outros> selecionados = tbOutros.getSelectionModel().getSelectedItems();
    	
    	if (tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty() || selecionados.isEmpty()) {
    		String erro = "";
    		if (selecionados.isEmpty()) erro += "Escolha ao menos um \"Outros\".\n";
    		if (tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty()) erro += "Informe o recebedor.\n";

    		Main.infoDialog("Erro", erro);
    	} else {
    		String doacao = "Outros: (" + selecionados.size() + "):\n";
	    	for (Outros i : selecionados) {
	    		doacao += "- " + i.getDescricao() + " - Doador: " + i.getDoador() + "\n";
	    	}
	    	
	    	if (Main.confirmDialog("Confirmação", "Tem certeza que deseja doar\n\n" + doacao + "\nPara: " + tfRecebedor.getText())) {
		    	GerenciarOutrosJDBCDAO daoOutros = new GerenciarOutrosJDBCDAO();
			    GerenciarDoacoesJDBCDAO daoDoacao = new GerenciarDoacoesJDBCDAO();
			    	
				int resp = daoOutros.remover(selecionados);
				if (resp > 0) {
					Main.infoDialog("Aviso", resp + " \"Outros\" foi(ram) doado(s).");
					daoDoacao.adicionar(doacao, tfRecebedor.getText());
				} else Main.infoDialog("Erro", "Ocorreu um erro ao doar " + resp + " \"Outros\".\nVerifique os valores e tente novamente.");
				carregarTabela(filtrarLista());
	    	}
    	}
    }
    
    /**
     * Método para carregar e converter a lista de outros do banco em uma lista filtrável.
     * @return filteredOutros lista filtrável de todos os outros do banco de dados.
     * @throws SQLException
     * @throws IOException 
     */
    private FilteredList<Outros> filtrarLista() throws SQLException, IOException {
    	GerenciarOutrosJDBCDAO daoOutros = new GerenciarOutrosJDBCDAO();
    	List<Outros> outros = daoOutros.load();
    	ObservableList<Outros> listOutros = FXCollections.observableArrayList(outros);
    	FilteredList<Outros> filteredOutros = new FilteredList<Outros>(listOutros, p -> true);
    	
    	tfBusca.textProperty().addListener((observable, oldValue, newValue) -> {
    		filteredOutros.setPredicate(outro -> {
    			if (newValue == null || newValue.isEmpty()) return true;
    			
    			String text = newValue.toLowerCase();
    			if (outro.getDescricao().toLowerCase().contains(text)) return true;
    			if (outro.getDoador().toLowerCase().contains(text)) return true;
    			
    			return false;
    		});
    	});
    	return filteredOutros;
    }

    /**
     * Método para carregar/atualizar a tabela de discos.
     * @param listOutros recebe a lista com os outros para serem exibidos na tabela.
     * @throws SQLException
     */
    private void carregarTabela(ObservableList<Outros> listOutros) throws SQLException {
    	IDColumn.setCellValueFactory(new PropertyValueFactory<Outros, Integer>("outrosId"));
    	descColumn.setCellValueFactory(new PropertyValueFactory<Outros, String>("descricao"));
    	doadorColumn.setCellValueFactory(new PropertyValueFactory<Outros, String>("doador"));
    	tbOutros.setItems(listOutros);
    }
}
