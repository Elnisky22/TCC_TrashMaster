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
import dao.gerenciarDao.GerenciarPlacaRedeJDBCDAO;
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
import model.PlacaRede;

/**
 * Classe controladora da tela GerenciarPlacaRede.
 * @author Leonardo Elnisky.
 */
public class GerenciarPlacaRedeController {
    @FXML private BorderPane bPanePlacas;
    @FXML private JFXTextField tfBusca;
    @FXML private JFXButton btnDescartar;
    @FXML private TableView<PlacaRede> tbPlacas;
    @FXML private TableColumn<PlacaRede, Integer> IDColumn;
    @FXML private TableColumn<PlacaRede, Integer> gabineteColumn;
    @FXML private TableColumn<PlacaRede, Integer> notebookColumn;
    @FXML private TableColumn<PlacaRede, String> marcaColumn;
    @FXML private TableColumn<PlacaRede, String> modeloColumn;
    @FXML private TableColumn<PlacaRede, Boolean> wifiColumn;
    @FXML private TableColumn<PlacaRede, String> doadorColumn;
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
    	tbPlacas.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	carregarTabela(filtrarLista());
    }
    
    /**
     * Método para descartar todas as Placas de Rede selecionadas na tabela.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDescartarOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<PlacaRede> selecionados = tbPlacas.getSelectionModel().getSelectedItems();
    	
    	if (selecionados.isEmpty()) {
    		Main.infoDialog("Erro", "Selecione ao menos uma Placa de Rede a ser descartada.");
    	} else {
    		String descarte = "Placa(s) de Rede (" + selecionados.size() + "):\n";
    		for (PlacaRede i : selecionados) {
    			descarte += "- " + i.getMarca() + " " + i.getModelo() + "\n";
    		}
    		
    		if (Main.confirmDialog("Confirmação", "Tem certeza que deseja descartar\n\n" + descarte)) {
        		GerenciarPlacaRedeJDBCDAO daoRedes = new GerenciarPlacaRedeJDBCDAO();
        		GerenciarDescartesJDBCDAO daoDesc = new GerenciarDescartesJDBCDAO();
        		
        		int resp = daoRedes.remover(selecionados);
        		if (resp > 0) {
        			Main.infoDialog("Aviso", resp + " Placa(s) de Rede foi(ram) descartada(s).");
        			daoDesc.adicionar(descarte);
        		} else Main.infoDialog("Erro", "Ocorreu um erro ao descartar " + resp + " Placa(s) de Rede.\nVerifique os valores e tente novamente.");
        		carregarTabela(filtrarLista());
    		}
    	}
    }
    
    /**
     * Método para doar todas as Placas de Rede selecionadas na tabela.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDoarOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<PlacaRede> selecionados = tbPlacas.getSelectionModel().getSelectedItems();
    	
    	if (tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty() || selecionados.isEmpty()) {
    		String erro = "";
    		if (selecionados.isEmpty()) erro += "Escolha ao menos uma Placa de Rede.\n";
    		if (tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty()) erro += "Informe o recebedor.\n";

    		Main.infoDialog("Erro", erro);
    	} else {
	    	String doacao = "Placa(s) de Rede (" + selecionados.size() + "):\n";
	    	for (PlacaRede i : selecionados) {
	    		doacao += "- " + i.getMarca() + " " + i.getModelo() + "\n";
	    	}
            
	    	if (Main.confirmDialog("Confirmação", "Tem certeza que deseja doar\n\n" + doacao + "\nPara: " + tfRecebedor.getText())) {
			    GerenciarPlacaRedeJDBCDAO daoRedes = new GerenciarPlacaRedeJDBCDAO();
			    GerenciarDoacoesJDBCDAO daoDoacao = new GerenciarDoacoesJDBCDAO();
			    	
				int resp = daoRedes.remover(selecionados);
				if (resp > 0) {
					Main.infoDialog("Aviso", resp + " Placa(s) de Rede foi(ram) doada(s).");
					daoDoacao.adicionar(doacao, tfRecebedor.getText());
				} else Main.infoDialog("Erro", "Ocorreu um erro ao doar " + resp + " Placa(s) de Rede.\nVerifique os valores e tente novamente.");
				carregarTabela(filtrarLista());
	    	}
    	}
    }
    
    /**
     * Método para carregar e converter a lista de placas de rede do banco em uma lista filtrável.
     * @return filteredRedes lista filtrável de todas as placas de rede do banco de dados.
     * @throws SQLException
     * @throws IOException 
     */
    private FilteredList<PlacaRede> filtrarLista() throws SQLException, IOException {
    	GerenciarPlacaRedeJDBCDAO daoRedes = new GerenciarPlacaRedeJDBCDAO();
    	List<PlacaRede> redes = daoRedes.load();
    	ObservableList<PlacaRede> listRedes = FXCollections.observableArrayList(redes);
    	FilteredList<PlacaRede> filteredRedes = new FilteredList<PlacaRede>(listRedes, p -> true);
    	
    	tfBusca.textProperty().addListener((observable, oldValue, newValue) -> {
    		filteredRedes.setPredicate(rede -> {
    			if (newValue == null || newValue.isEmpty()) return true;
    			
    			String text = newValue.toLowerCase();
    			if (rede.getMarca().toLowerCase().contains(text)) return true;
    			if (rede.getModelo().toLowerCase().contains(text)) return true;
    			if (rede.getDoador().toLowerCase().contains(text)) return true;
    			
    			return false;
    		});
    	});
    	return filteredRedes;
    }
    
    /**
     * Método para carregar/atualizar a tabela de discos.
     * @param listRedes recebe a lista com as placas de rede para serem exibidos na tabela.
     * @throws SQLException
     */
    private void carregarTabela(ObservableList<PlacaRede> listRedes) throws SQLException {
    	IDColumn.setCellValueFactory(new PropertyValueFactory<PlacaRede, Integer>("placaId"));
    	gabineteColumn.setCellValueFactory(new PropertyValueFactory<PlacaRede, Integer>("gabineteId"));
    	notebookColumn.setCellValueFactory(new PropertyValueFactory<PlacaRede, Integer>("notebookId"));
    	marcaColumn.setCellValueFactory(new PropertyValueFactory<PlacaRede, String>("marca"));
    	modeloColumn.setCellValueFactory(new PropertyValueFactory<PlacaRede, String>("modelo"));
    	wifiColumn.setCellValueFactory(new PropertyValueFactory<PlacaRede, Boolean>("wifi"));
    	doadorColumn.setCellValueFactory(new PropertyValueFactory<PlacaRede, String>("doador"));
    	tbPlacas.setItems(listRedes);
    }

}
