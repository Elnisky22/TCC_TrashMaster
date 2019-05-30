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
import dao.gerenciarDao.GerenciarGabineteJDBCDAO;
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
import model.Gabinete;

/**
 * Classe controladora da tela GerenciarGabinete.
 * @author Leonardo Elnisky.
 */
public class GerenciarGabineteController {
    @FXML private BorderPane bPaneTabelas;
    @FXML private JFXTextField tfBusca;
    @FXML private JFXButton btnDescarte;
    @FXML private TableView<Gabinete> tbGabinetes;
    @FXML private TableColumn<Gabinete, Integer> IDColumn;
    @FXML private TableColumn<Gabinete, String> marcaColumn;
    @FXML private TableColumn<Gabinete, String> tamanhoColumn;
    @FXML private TableColumn<Gabinete, String> doadorColumn;
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
    	tbGabinetes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	carregarTabela(filtrarLista());
    }
    
    /**
     * Método para descartar todos os Gabinetes selecionados.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDescarteOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<Gabinete> selecionados = tbGabinetes.getSelectionModel().getSelectedItems();
    	
    	if (selecionados.isEmpty()) {
    		Main.infoDialog("Erro", "Selecione ao menos um Gabinete a ser descartado.");
    	} else {
    		String descarte = "Gabinetes (" + selecionados.size() + "):\n";
    		for (Gabinete i : selecionados) {
    			descarte += "- " + i.getMarca() + "\n";
    		}
    		
    		if (Main.confirmDialog("Confirmação", "Tem certeza que deseja descartar\n\n" + descarte)) {
        		GerenciarGabineteJDBCDAO daoGabs = new GerenciarGabineteJDBCDAO();
        		GerenciarDescartesJDBCDAO daoDesc = new GerenciarDescartesJDBCDAO();
        		 			
        		int resp = daoGabs.remover(selecionados);
        		if (resp > 0) {
        			Main.infoDialog("Aviso", resp + " Gabinete(s) foi(ram) descartado(s).");
        			daoDesc.adicionar(descarte);
        		} else Main.infoDialog("Erro", "Ocorreu um erro ao descartar " + resp + " Gabinete(s).\nVerifique os valores e tente novamente.");
        		carregarTabela(filtrarLista());
    		}
    	}
    }
    
    /**
     * Método para doar todos os Gabinetes selecionados.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDoarOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<Gabinete> selecionados = tbGabinetes.getSelectionModel().getSelectedItems();
    	
    	if (tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty() || selecionados.isEmpty()) {
    		String erro = "";
    		if (selecionados.isEmpty()) erro += "Escolha ao menos um gabinete.\n";
    		if (tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty()) erro += "Informe o recebedor.\n";

    		Main.infoDialog("Erro", erro);
    	} else {
    		String doacao = "Gabinetes (" + selecionados.size() + "):\n";
	    	for (Gabinete i : selecionados) {
	    		doacao += "- " + i.getMarca() + "\n";
	    	}
	    	if (Main.confirmDialog("Confirmação", "Tem certeza que deseja doar\n\n" + doacao + "\nPara: " + tfRecebedor.getText())) {
			    GerenciarGabineteJDBCDAO daoGabs = new GerenciarGabineteJDBCDAO();
			    GerenciarDoacoesJDBCDAO daoDoacao = new GerenciarDoacoesJDBCDAO();
			    
			    int resp = daoGabs.remover(selecionados); 
			    if (resp > 0) {
			    	Main.infoDialog("Aviso", resp + " Gabinete(s) foi(ram) doado(s).");
			    	daoDoacao.adicionar(doacao, tfRecebedor.getText());
			    } else Main.infoDialog("Erro", "Ocorreu um erro ao doar " + resp + " Gabinete(s).\nVerifique os valores e tente novamente.");
				carregarTabela(filtrarLista());
	    	}
    	}
    }

    /**
     * Método para carregar e converter a lista de gabinetes do banco em uma lista filtrável.
     * @return filteredDiscos lista filtrável de todos os gabinetes do banco de dados.
     * @throws SQLException
     * @throws IOException 
     */
    private ObservableList<Gabinete> filtrarLista() throws SQLException, IOException {
    	GerenciarGabineteJDBCDAO daoGabs = new GerenciarGabineteJDBCDAO();
    	List<Gabinete> gabs = daoGabs.load();
    	ObservableList<Gabinete> listGabs = FXCollections.observableArrayList(gabs);
    	FilteredList<Gabinete> filteredGabs = new FilteredList<Gabinete>(listGabs, p -> true);
    	
    	tfBusca.textProperty().addListener((observable, oldValue, newValue) -> {
    		filteredGabs.setPredicate(gab -> {
    			if (newValue == null || newValue.isEmpty()) return true;
    			
    			String text = newValue.toLowerCase();
    			if (gab.getMarca().toLowerCase().contains(text)) return true;
    			if (gab.getTamanho().toLowerCase().contains(text)) return true;
    			if (gab.getDoador().toLowerCase().contains(text)) return true;
    			
    			return false;
    		});
    	});
    	
    	return filteredGabs;
    }
    
    /**
     * Método para carregar/atualizar a tabela de gabinetes.
     * @param listDiscos recebe a lista com os gabinetes para serem exibidos na tabela.
     * @throws SQLException
     */
    private void carregarTabela(ObservableList<Gabinete> listGabs) throws SQLException {
    	IDColumn.setCellValueFactory(new PropertyValueFactory<Gabinete, Integer>("computadorId"));
    	marcaColumn.setCellValueFactory(new PropertyValueFactory<Gabinete, String>("marca"));
    	tamanhoColumn.setCellValueFactory(new PropertyValueFactory<Gabinete, String>("tamanho"));
    	doadorColumn.setCellValueFactory(new PropertyValueFactory<Gabinete, String>("doador"));
    	tbGabinetes.setItems(listGabs);
    }
}
