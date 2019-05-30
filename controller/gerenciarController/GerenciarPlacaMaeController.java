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
import dao.gerenciarDao.GerenciarPlacaMaeJDBCDAO;
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
import model.PlacaMae;

/**
 * Classe controladora da tela GerenciarPlacaMae.
 * @author Leonardo Elnisky.
 */
public class GerenciarPlacaMaeController {
    @FXML private BorderPane bPanePlacas;
    @FXML private JFXTextField tfBusca;
    @FXML private JFXButton btnDescartar;
    @FXML private TableView<PlacaMae> tbMaes;
    @FXML private TableColumn<PlacaMae, Integer> IDColumn;
    @FXML private TableColumn<PlacaMae, Integer> gabineteColumn;
    @FXML private TableColumn<PlacaMae, Integer> notebookColumn;
    @FXML private TableColumn<PlacaMae, String> marcaColumn;
    @FXML private TableColumn<PlacaMae, String> modeloColumn;
    @FXML private TableColumn<PlacaMae, String> socketColumn;
    @FXML private TableColumn<PlacaMae, String> tipoRamColumn;
    @FXML private TableColumn<PlacaMae, String> doadorColumn;
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
    	tbMaes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	carregarTabela(filtrarLista());
    }

    /**
     * Método para descartar todas as Placa Mães selecionadas na tabela.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDescartarOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<PlacaMae> selecionados = tbMaes.getSelectionModel().getSelectedItems();
    	
    	if (selecionados.isEmpty()) {
    		Main.infoDialog("Erro", "Selecione ao menos uma Placa Mãe a ser descartada.");
    	} else {
    		String descarte = "Placa(s) mãe (" + selecionados.size() + "):\n";
    		for (PlacaMae i : selecionados) {
    			descarte += "- " + i.getMarca() + " " + i.getModelo() + "\n";
    		}

    		if (Main.confirmDialog("Confirmação", "Tem certeza que deseja descartar\n\n" + descarte)) {
        		GerenciarPlacaMaeJDBCDAO daoMae = new GerenciarPlacaMaeJDBCDAO();
        		GerenciarDescartesJDBCDAO daoDesc = new GerenciarDescartesJDBCDAO();
        		
        		int resp = daoMae.remover(selecionados);
        		if (resp > 0) {
        			Main.infoDialog("Aviso", resp + " Placa(s) Mãe foi(ram) descartada(s).");
        			daoDesc.adicionar(descarte);
        		} else Main.infoDialog("Erro", "Ocorreu um erro ao descartar " + resp + " Placa(s) Mãe.\nVerifique os valores e tente novamente.");
        		carregarTabela(filtrarLista());
    		}
    	}
    }
    
    /**
     * Método para doar todas as Placa Mães selecionadas na tabela.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDoarOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<PlacaMae> selecionados = tbMaes.getSelectionModel().getSelectedItems();
    	
    	if (tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty() || selecionados.isEmpty()) {
    		String erro = "";
    		if (selecionados.isEmpty()) erro += "Escolha ao menos uma Placa Mãe.\n";
    		if (tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty()) erro += "Informe o recebedor.\n";

    		Main.infoDialog("Erro", erro);
    	} else {
	    	String doacao = "Placa(s) Mãe (" + selecionados.size() + "):\n";
	    	for (PlacaMae i : selecionados) {
	    		doacao += "- " + i.getMarca() + " " + i.getModelo() + "\n";
	    	}
	    	if (Main.confirmDialog("Confirmação", "Tem certeza que deseja doar\n\n" + doacao + "\nPara: " + tfRecebedor.getText())) {
			    GerenciarPlacaMaeJDBCDAO daoMae = new GerenciarPlacaMaeJDBCDAO();
			    GerenciarDoacoesJDBCDAO daoDoacao = new GerenciarDoacoesJDBCDAO();
			    	
				int resp = daoMae.remover(selecionados);
				if (resp > 0) {
					Main.infoDialog("Aviso", resp + " Placa(s) Mãe foi(ram) doada(s).");
					daoDoacao.adicionar(doacao, tfRecebedor.getText());
				} else Main.infoDialog("Erro", "Ocorreu um erro ao doar " + resp + " Placa(s) Mãe.\nVerifique os valores e tente novamente.");
				carregarTabela(filtrarLista());
	    	}
    	}
    }

    /**
     * Método para carregar e converter a lista de placa mães do banco em uma lista filtrável.
     * @return filteredMaes lista filtrável de todas as placa mães do banco de dados.
     * @throws SQLException
     * @throws IOException 
     */
    private FilteredList<PlacaMae> filtrarLista() throws SQLException, IOException {
    	GerenciarPlacaMaeJDBCDAO daoMae = new GerenciarPlacaMaeJDBCDAO();
    	List<PlacaMae> maes = daoMae.load();
    	ObservableList<PlacaMae> listMaes = FXCollections.observableArrayList(maes);
    	FilteredList<PlacaMae> filteredMaes = new FilteredList<PlacaMae>(listMaes, p -> true);
    	
    	tfBusca.textProperty().addListener((observable, oldValue, newValue) -> {
    		filteredMaes.setPredicate(mae -> {
    			if (newValue == null || newValue.isEmpty()) return true;
    			
    			String text = newValue.toLowerCase();
    			if (mae.getMarca().toLowerCase().contains(text)) return true;
    			if (mae.getModelo().toLowerCase().contains(text)) return true;
    			if (mae.getRam().toLowerCase().contains(text)) return true;
    			if (mae.getSocket().toLowerCase().contains(text)) return true;
    			if (mae.getDoador().toLowerCase().contains(text)) return true;
    			
    			return false;
    		});
    	});
    	
    	return filteredMaes;
    }
    
    /**
     * Método para carregar/atualizar a tabela de placa mães.
     * @param listMaes recebe a lista com as placa mães para serem exibidas na tabela.
     * @throws SQLException
     */
    private void carregarTabela(ObservableList<PlacaMae> listMaes) throws SQLException {
    	IDColumn.setCellValueFactory(new PropertyValueFactory<PlacaMae, Integer>("placaId"));
    	gabineteColumn.setCellValueFactory(new PropertyValueFactory<PlacaMae, Integer>("gabineteId"));
    	notebookColumn.setCellValueFactory(new PropertyValueFactory<PlacaMae, Integer>("notebookId"));
    	marcaColumn.setCellValueFactory(new PropertyValueFactory<PlacaMae, String>("marca"));
    	modeloColumn.setCellValueFactory(new PropertyValueFactory<PlacaMae, String>("modelo"));
    	socketColumn.setCellValueFactory(new PropertyValueFactory<PlacaMae, String>("socket"));
    	tipoRamColumn.setCellValueFactory(new PropertyValueFactory<PlacaMae, String>("ram"));
    	doadorColumn.setCellValueFactory(new PropertyValueFactory<PlacaMae, String>("doador"));
    	tbMaes.setItems(listMaes);
    }
}
