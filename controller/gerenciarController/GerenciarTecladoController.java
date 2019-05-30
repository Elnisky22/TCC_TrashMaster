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
import dao.gerenciarDao.GerenciarTecladoJDBCDAO;
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
import model.Teclado;

/**
 * Classe controladora da tela GerenciarTeclado.
 * @author Leonardo Elnisky.
 */
public class GerenciarTecladoController {
    @FXML private BorderPane bPaneTeclados;
    @FXML private JFXTextField tfBusca;
    @FXML private JFXButton btnDescartar;
    @FXML private TableView<Teclado> tbTeclados;
    @FXML private TableColumn<Teclado, Integer> IDColumn;
    @FXML private TableColumn<Teclado, String> marcaColumn;
    @FXML private TableColumn<Teclado, String> modeloColumn;
    @FXML private TableColumn<Teclado, String> doadorColumn;
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
    	tbTeclados.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	carregarTabela(filtrarLista());
    }
    
    /**
     * Método para descartar todos os Teclados selecionados na tabela.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDescartarOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<Teclado> selecionados = tbTeclados.getSelectionModel().getSelectedItems();
    	
    	if (selecionados.isEmpty()) {
    		Main.infoDialog("Erro", "Selecione ao menos um Teclado a ser descartado.");
    	} else {
    		String descarte = "Teclado(s) (" + selecionados.size() + "):\n";
			for (Teclado i : selecionados) {
				descarte += "- " + i.getMarca() + " " + i.getModelo() + "\n";
			}
        	
			if (Main.confirmDialog("Confirmação", "Tem certeza que deseja descartar\n\n" + descarte)) {
				GerenciarTecladoJDBCDAO daoTeclado = new GerenciarTecladoJDBCDAO();
        		GerenciarDescartesJDBCDAO daoDesc = new GerenciarDescartesJDBCDAO();

        		int resp = daoTeclado.remover(selecionados);
        		if (resp > 0) {
        			Main.infoDialog("Aviso", resp + " Teclado(s) foi(ram) descartado(s).");
        			daoDesc.adicionar(descarte);
        		} else Main.infoDialog("Erro", "Ocorreu um erro ao descartar " + resp + " Teclado(s).\nVerifique os valores e tente novamente.");
        		carregarTabela(filtrarLista());
			}
    	}
    }
    
    /**
     * Método para doar todos os Teclados selecionados na tabela.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDoarOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<Teclado> selecionados = tbTeclados.getSelectionModel().getSelectedItems();
    	
    	if (tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty() || selecionados.isEmpty()) {
    		String erro = "";
    		if (selecionados.isEmpty()) erro += "Escolha ao menos um disco rígido.\n";
    		if (tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty()) erro += "Informe o recebedor.\n";
    		
    		Main.infoDialog("Erro", erro);
    	} else {
    		String doacao = "Teclados (" + selecionados.size() + "):\n";
	    	for (Teclado i : selecionados) {
	    		doacao += "- " + i.getMarca() + " " + i.getModelo() + "\n";
	    	}
	    	
	    	if (Main.confirmDialog("Confirmação", "Tem certeza que deseja doar\n\n" + doacao + "\nPara: " + tfRecebedor.getText())) {
		    	GerenciarTecladoJDBCDAO daoTeclado = new GerenciarTecladoJDBCDAO();
			    GerenciarDoacoesJDBCDAO daoDoacao = new GerenciarDoacoesJDBCDAO();
			    	
				int resp = daoTeclado.remover(selecionados);
				if (resp > 0) {
					Main.infoDialog("Aviso", resp + " Teclado(s) foi(ram) doado(s).");
					daoDoacao.adicionar(doacao, tfRecebedor.getText());
				} else Main.infoDialog("Erro", "Ocorreu um erro ao doar " + resp + " Teclado(s).\nVerifique os valores e tente novamente.");
				carregarTabela(filtrarLista());
	    	}
    	}
    }
    
    /**
     * Método para carregar e converter a lista de teclados do banco em uma lista filtrável.
     * @return filteredTeclados lista filtrável de todos os teclados do banco de dados.
     * @throws SQLException
     * @throws IOException 
     */
    private FilteredList<Teclado> filtrarLista() throws SQLException, IOException {
    	GerenciarTecladoJDBCDAO daoTeclado = new GerenciarTecladoJDBCDAO();
    	List<Teclado> teclados = daoTeclado.load();
    	ObservableList<Teclado> listTeclados = FXCollections.observableArrayList(teclados);
    	FilteredList<Teclado> filteredTeclados = new FilteredList<Teclado>(listTeclados, p -> true);
    	
    	tfBusca.textProperty().addListener((observable, oldValue, newValue) -> {
    		filteredTeclados.setPredicate(tec -> {
    			if (newValue == null || newValue.isEmpty()) return true;
    			
    			String text = newValue.toLowerCase();
    			if (tec.getMarca().toLowerCase().contains(text)) return true;
    			if (tec.getModelo().toLowerCase().contains(text)) return true;
    			if (tec.getDoador().toLowerCase().contains(text)) return true;
    			
    			return false;
    		});
    	});
    	return filteredTeclados;
    }
    
    /**
     * Método para carregar/atualizar a tabela de teclados.
     * @param listTeclados recebe a lista com os teclados para serem exibidos na tabela.
     * @throws SQLException
     */
    private void carregarTabela(ObservableList<Teclado> listTeclados) throws SQLException {
    	IDColumn.setCellValueFactory(new PropertyValueFactory<Teclado, Integer>("tecladoId"));
    	marcaColumn.setCellValueFactory(new PropertyValueFactory<Teclado, String>("marca"));
    	modeloColumn.setCellValueFactory(new PropertyValueFactory<Teclado, String>("modelo"));
    	doadorColumn.setCellValueFactory(new PropertyValueFactory<Teclado, String>("doador"));
    	tbTeclados.setItems(listTeclados);
    }

}
