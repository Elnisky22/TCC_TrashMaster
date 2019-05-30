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
import dao.gerenciarDao.GerenciarMouseJDBCDAO;
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
import model.Mouse;

/**
 * Classe controladora da tela GerenciarMouse.
 * @author Leonardo Elnisky.
 */
public class GerenciarMouseController {
    @FXML private BorderPane bPaneMouses;
    @FXML private JFXTextField tfBusca;
    @FXML private JFXButton btnDescartar;
    @FXML private TableView<Mouse> tbMouses;
    @FXML private TableColumn<Mouse, Integer> IDColumn;
    @FXML private TableColumn<Mouse, String> marcaColumn;
    @FXML private TableColumn<Mouse, String> modeloColumn;
    @FXML private TableColumn<Mouse, String> doadorColumn;
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
    	tbMouses.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	carregarTabela(filtrarLista());
    }
    
    /**
     * Método para descartar todas os Mouses selecionados na tabela.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDescartarOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<Mouse> selecionados = tbMouses.getSelectionModel().getSelectedItems();
    	
    	if (selecionados.isEmpty()) {
    		Main.infoDialog("Erro", "Selecione ao menos um Mouse a ser descartado.");
    	} else {
    		String descarte = "Mouse(s) (" + selecionados.size() + "):\n";
    		for (Mouse i : selecionados) {
    			descarte += "- " + i.getMarca() + " " + i.getModelo() + "\n";
    		}
    		
    		if (Main.confirmDialog("Confirmação", "Tem certeza que deseja descartar\n\n" + descarte)) {
        		GerenciarMouseJDBCDAO daoMouse = new GerenciarMouseJDBCDAO();
        		GerenciarDescartesJDBCDAO daoDesc = new GerenciarDescartesJDBCDAO();
        			
        		int resp = daoMouse.remover(selecionados);
        		if (resp > 0) {
        			Main.infoDialog("Aviso", resp + " Mouse(s) foi(ram) descartado(s).");
        			daoDesc.adicionar(descarte);
        		} else Main.infoDialog("Erro", "Ocorreu um erro ao descartar " + resp + " Mouse(s).\nVerifique os valores e tente novamente.");
        		carregarTabela(filtrarLista());
    		}
    	}
    }
    
    /**
     * Método para doar todos os Mouses selecionados na tabela.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDoarOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<Mouse> selecionados = tbMouses.getSelectionModel().getSelectedItems();
    	
    	if (tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty() || selecionados.isEmpty()) {
    		String erro = "";
    		if (selecionados.isEmpty()) erro += "Escolha ao menos um mouse.\n";
    		if (tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty()) erro += "Informe o recebedor.\n";

    		Main.infoDialog("Erro", erro);
    	} else {
    		String doacao = "Mouse(s) (" + selecionados.size() + "):\n";
	    	for (Mouse i : selecionados) {
	    		doacao += "- " + i.getMarca() + " " + i.getModelo() + "\n";
	    	}
	    	if (Main.confirmDialog("Confirmação", "Tem certeza que deseja doar\n\n" + doacao + "\nPara: " + tfRecebedor.getText())) {
		    	GerenciarMouseJDBCDAO daoMouse = new GerenciarMouseJDBCDAO();
			    GerenciarDoacoesJDBCDAO daoDoacao = new GerenciarDoacoesJDBCDAO();
			    	
			    int resp = daoMouse.remover(selecionados);
			    if (resp > 0) {
			    	Main.infoDialog("Aviso", resp + " Mouse(s) foi(ram) doado(s) com sucesso.");
			    	daoDoacao.adicionar(doacao, tfRecebedor.getText());
			    } else Main.infoDialog("Erro", "Ocorreu um erro ao doar " + resp + " Mouse(s).\nVerifique os valores e tente novamente.");
				carregarTabela(filtrarLista());
	    	}
    	}
    }
    
    /**
     * Método para carregar e converter a lista de mouses do banco em uma lista filtrável.
     * @return filteredMouses lista filtrável de todos os mouses do banco de dados.
     * @throws SQLException
     * @throws IOException 
     */
    private FilteredList<Mouse> filtrarLista() throws SQLException, IOException {
    	GerenciarMouseJDBCDAO daoMouse = new GerenciarMouseJDBCDAO();
    	List<Mouse> mouses = daoMouse.load();
    	ObservableList<Mouse> listMouses = FXCollections.observableArrayList(mouses);
    	FilteredList<Mouse> filteredMouses = new FilteredList<Mouse>(listMouses, p -> true);
    	
    	tfBusca.textProperty().addListener((observable, oldValue, newValue) -> {
    		filteredMouses.setPredicate(mouse -> {
    			if (newValue == null || newValue.isEmpty()) return true;
    			
    			String text = newValue.toLowerCase();
    			if (mouse.getMarca().toLowerCase().contains(text)) return true;
    			if (mouse.getModelo().toLowerCase().contains(text)) return true;
    			if (mouse.getDoador().toLowerCase().contains(text)) return true;
    			
    			return false;
    		});
    	});
    	return filteredMouses;
    }
    
    /**
     * Método para carregar/atualizar a tabela de mouses.
     * @param listMouses recebe a lista com os mouses para serem exibidos na tabela.
     * @throws SQLException
     */
    private void carregarTabela(ObservableList<Mouse> listMouses) throws SQLException {
    	IDColumn.setCellValueFactory(new PropertyValueFactory<Mouse, Integer>("mouseId"));
    	marcaColumn.setCellValueFactory(new PropertyValueFactory<Mouse, String>("marca"));
    	modeloColumn.setCellValueFactory(new PropertyValueFactory<Mouse, String>("modelo"));
    	doadorColumn.setCellValueFactory(new PropertyValueFactory<Mouse, String>("doador"));
    	tbMouses.setItems(listMouses);
    }

}
