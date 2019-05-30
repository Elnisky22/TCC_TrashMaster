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
import dao.gerenciarDao.GerenciarImpressoraJDBCDAO;
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
import model.Impressora;

/**
 * Classe controladora da tela GerenciarImpressora.
 * @author Leonardo Elnisky.
 */
public class GerenciarImpressoraController {
    @FXML private BorderPane bPaneImpressoras;
    @FXML private JFXTextField tfBusca;
    @FXML private JFXButton btnDescarte;
    @FXML private TableView<Impressora> tbImpressoras;
    @FXML private TableColumn<Impressora, Integer> IDColumn;
    @FXML private TableColumn<Impressora, String> marcaColumn;
    @FXML private TableColumn<Impressora, String> modeloColumn;
    @FXML private TableColumn<Impressora, String> doadorColumn;
    @FXML private JFXTextField tfRecebedor;
    @FXML private JFXButton btnDoador;
    
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
    	tbImpressoras.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	carregarTabela(filtrarLista());
    }

    /**
     * Método para descartar todas as Impressoras selecioandas na lista.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDescarteOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<Impressora> selecionados = tbImpressoras.getSelectionModel().getSelectedItems();
    	
    	if (selecionados.isEmpty()) {
    		Main.infoDialog("Erro", "Selecione ao menos uma Impressora a ser descartada.");
    	} else {
    		String descarte = "Impressora(s) (" + selecionados.size() + "):\n";
    		for (Impressora i : selecionados) {
    			descarte += "- " + i.getMarca() + " " + i.getModelo() + "\n";
    		}
    		
    		if (Main.confirmDialog("Confirmação", "Tem certeza que deseja descartar\n\n" + descarte)) {
        		GerenciarImpressoraJDBCDAO daoImp = new GerenciarImpressoraJDBCDAO();
        		GerenciarDescartesJDBCDAO daoDesc = new GerenciarDescartesJDBCDAO();
        		
        		int resp = daoImp.remover(selecionados);
        		if (resp > 0) {
        			Main.infoDialog("Aviso", resp + " Impressora(s) foi(ram) descartado(s).");
        			daoDesc.adicionar(descarte);
        		} else Main.infoDialog("Erro", "Ocorreu um erro ao descartar " + resp + " Impressora(s).\nVerifique os valores e tente novamente.");
        		carregarTabela(filtrarLista());
    		}
    	}
    }
    
    /**
     * Método para doar todas as Impressoras selecionadas na tabela.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDoadorOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<Impressora> selecionados = tbImpressoras.getSelectionModel().getSelectedItems();
    	
    	if (tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty() || selecionados.isEmpty()) {
    		String erro = "";
    		if (selecionados.isEmpty()) erro += "Escolha ao menos uma impressora.\n";
    		if (tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty()) erro += "Informe o recebedor.\n";

    		Main.infoDialog("Erro", erro);
    	} else {
    		String doacao = "Impressora(s) (" + selecionados.size() + "):\n";
	    	for (Impressora i : selecionados) {
	    		doacao += "- " + i.getMarca() + " " + i.getModelo() + "\n";
	    	}
	    	if (Main.confirmDialog("Confirmação", "Tem certeza que deseja doar\n\n" + doacao + "\nPara: " + tfRecebedor.getText())) {
			    GerenciarImpressoraJDBCDAO daoImp = new GerenciarImpressoraJDBCDAO();
			    GerenciarDoacoesJDBCDAO daoDoacao = new GerenciarDoacoesJDBCDAO();
			    
				int resp = daoImp.remover(selecionados);
				if (resp > 0) {
					Main.infoDialog("Aviso", resp + " Impressora(s) foi(ram) doada(s).");
			    	daoDoacao.adicionar(doacao, tfRecebedor.getText());
				} else Main.infoDialog("Erro", "Ocorreu um erro ao doar " + resp + " Impressora(s).\nVerifique os valores e tente novamente.");
				carregarTabela(filtrarLista());
	    	}
    	}
    }

    /**
     * Método para carregar e converter a lista de impressoras do banco em uma lista filtrável.
     * @return filteredImps lista filtrável de todos as impressoras do banco de dados.
     * @throws SQLException
     * @throws IOException 
     */
    private FilteredList<Impressora> filtrarLista() throws SQLException, IOException {
    	GerenciarImpressoraJDBCDAO daoImp = new GerenciarImpressoraJDBCDAO();
    	List<Impressora> imps = daoImp.load();
    	ObservableList<Impressora> listImps = FXCollections.observableArrayList(imps);
    	FilteredList<Impressora> filteredImps = new FilteredList<Impressora>(listImps, p -> true);
    	
    	tfBusca.textProperty().addListener((observable, oldValue, newValue) -> {
    		filteredImps.setPredicate(imp -> {
    			if (newValue == null || newValue.isEmpty()) return true;
    			
    			String text = newValue.toLowerCase();
    			if (imp.getMarca().toLowerCase().contains(text)) return true;
    			if (imp.getModelo().toLowerCase().contains(text)) return true;
    			if (imp.getDoador().toLowerCase().contains(text)) return true;
    			
    			return false;
    		});	
    	});
    	
    	return filteredImps;
    }
    
    /**
     * Método para carregar/atualizar a tabela de impressoras.
     * @param listImps recebe a lista com as impressoras para serem exibidos na tabela.
     * @throws SQLException
     */
    private void carregarTabela(ObservableList<Impressora> listImps) throws SQLException{
    	IDColumn.setCellValueFactory(new PropertyValueFactory<Impressora, Integer>("impressoraId"));
    	marcaColumn.setCellValueFactory(new PropertyValueFactory<Impressora, String>("marca"));
    	modeloColumn.setCellValueFactory(new PropertyValueFactory<Impressora, String>("modelo"));
    	doadorColumn.setCellValueFactory(new PropertyValueFactory<Impressora, String>("doador"));
    	tbImpressoras.setItems(listImps);
    }
}
