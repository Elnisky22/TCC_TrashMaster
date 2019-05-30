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
import dao.gerenciarDao.GerenciarFonteJDBCDAO;
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
import model.Fonte;

/**
 * Classe controladora da tela GerenciarFonte.
 * @author Leonardo Elnisky.
 */
public class GerenciarFonteController {
    @FXML private BorderPane bPaneGerenciar;
    @FXML private JFXTextField tfBusca;
    @FXML private JFXButton btnDescarte;
    @FXML private TableView<Fonte> tbFontes;
    @FXML private TableColumn<Fonte, Integer> IDColumn;
    @FXML private TableColumn<Fonte, Integer> gabineteColumn;
    @FXML private TableColumn<Fonte, Integer> notebookColumn;
    @FXML private TableColumn<Fonte, String> marcaColumn;
    @FXML private TableColumn<Fonte, String> modeloColumn;
    @FXML private TableColumn<Fonte, String> potenciaColumn;
    @FXML private TableColumn<Fonte, String> voltagemColumn;
    @FXML private TableColumn<Fonte, String> doadorColumn;
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
    	tbFontes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	carregarTabela(filtrarLista());
    }

    /**
     * Método para descartar todas as Fontes selecionadas.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDescarteOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<Fonte> selecionados = tbFontes.getSelectionModel().getSelectedItems();
    	
    	if (selecionados.isEmpty()) {
    		Main.infoDialog("Erro", "Selecione ao menos uma Fonte a ser descartada.");
    	} else {
    		String descarte = "Fontes (" + selecionados.size() + "):\n";
    		for (Fonte i : selecionados) {
    			descarte += "- " + i.getMarca() + " " + i.getModelo() + " " + i.getPotencia() + "W " + i.getVoltagem() + "\n";
    		}
    		
    		if (Main.confirmDialog("Confirmação", "Tem certeza que deseja descartar\n\n" + descarte)) {
        		GerenciarFonteJDBCDAO daoFonte = new GerenciarFonteJDBCDAO();
        		GerenciarDescartesJDBCDAO daoDesc = new GerenciarDescartesJDBCDAO();
        		
        		int resp = daoFonte.remover(selecionados);
        		if (resp > 0) {
        			Main.infoDialog("Aviso", resp + " Fonte(s) foi(ram) descartada(s).");
        			daoDesc.adicionar(descarte);
        		} else Main.infoDialog("Erro", "Ocorreu um erro ao descartar " + resp + " Fonte(s).\nVerifique os valores e tente novmanete");
        		carregarTabela(filtrarLista());
    		}
    	}
    }
    
    /**
     * Método para doar todas as Fontes selecionadas.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDoarOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<Fonte> selecionados = tbFontes.getSelectionModel().getSelectedItems();
    	
    	if (tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty() || selecionados.isEmpty()) {
    		String erro = "";
    		if (selecionados.isEmpty()) erro += "Escolha ao menos uma fonte.\n";
    		if (tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty()) erro += "Informe o recebedor.\n";

    		Main.infoDialog("Erro", erro);
    	} else {
    		String doacao = "Fontes (" + selecionados.size() + "):\n";
	    	for (Fonte i : selecionados) {
	    		doacao += "- " + i.getMarca() + " " + i.getModelo() + " " + i.getPotencia() + "W " + i.getVoltagem() + "\n";
	    	}
	    	if (Main.confirmDialog("Confirmação", "Tem certeza que deseja doar\n\n" + doacao + "\nPara: " + tfRecebedor.getText())) {
			   	GerenciarFonteJDBCDAO daoFonte = new GerenciarFonteJDBCDAO();
			   	GerenciarDoacoesJDBCDAO daoDoacao = new GerenciarDoacoesJDBCDAO();
	    	
			    int resp = daoFonte.remover(selecionados);
			    if (resp > 0) {
			    	Main.infoDialog("Aviso", resp + " Fonte(s) foi(ram) doada(s).");
			    	daoDoacao.adicionar(doacao, tfRecebedor.getText());
			    } else Main.infoDialog("Erro", "Ocorreu um erro ao doar " + resp + " Fonte(s).\nVerifique os valores e tente novamente.");
				carregarTabela(filtrarLista());
			}
    	}
    }

    /**
     * Método para carregar e converter a lista de fontes do banco de dados em uma lista filtrável.
     * @return filteredFontes uma lista filtrável contendo todas as fontes do banco de dados.
     * @throws SQLException
     * @throws IOException 
     */
    private FilteredList<Fonte> filtrarLista() throws SQLException, IOException {
    	GerenciarFonteJDBCDAO daoFonte = new GerenciarFonteJDBCDAO();
    	List<Fonte> fontes = daoFonte.load();
    	ObservableList<Fonte> listFontes = FXCollections.observableArrayList(fontes);
    	FilteredList<Fonte> filteredFontes = new FilteredList<Fonte>(listFontes, p -> true);
    	
    	tfBusca.textProperty().addListener((observable, oldValue, newValue) -> {
    		filteredFontes.setPredicate(fonte -> {
    			if (newValue == null || newValue.isEmpty()) return true;
    			
    			String text = newValue.toLowerCase();
    			if (fonte.getMarca().toLowerCase().contains(text)) return true;
    			if (fonte.getModelo().toLowerCase().contains(text)) return true;
    			if (fonte.getPotencia().toLowerCase().contains(text)) return true;
    			if (fonte.getVoltagem().toLowerCase().contains(text)) return true;
    			if (fonte.getDoador().toLowerCase().contains(text)) return true;
    			
    			return false;
    		});
    	});
    	return filteredFontes;
    }
    
    /**
     * Método para carregar/atualizar a tabela de fontes.
     * @param listFontes recebe a lista com as fontes para serem exibidas na tela.
     * @throws SQLException
     */
    private void carregarTabela(ObservableList<Fonte> listFontes) throws SQLException {
    	IDColumn.setCellValueFactory(new PropertyValueFactory<Fonte, Integer>("fonteId"));
    	gabineteColumn.setCellValueFactory(new PropertyValueFactory<Fonte, Integer>("gabineteId"));
    	notebookColumn.setCellValueFactory(new PropertyValueFactory<Fonte, Integer>("notebookId"));
    	marcaColumn.setCellValueFactory(new PropertyValueFactory<Fonte, String>("marca"));
    	modeloColumn.setCellValueFactory(new PropertyValueFactory<Fonte, String>("modelo"));
    	potenciaColumn.setCellValueFactory(new PropertyValueFactory<Fonte, String>("potencia"));
    	voltagemColumn.setCellValueFactory(new PropertyValueFactory<Fonte, String>("voltagem"));
    	doadorColumn.setCellValueFactory(new PropertyValueFactory<Fonte, String>("doador"));
    	tbFontes.setItems(listFontes);
    }
}
