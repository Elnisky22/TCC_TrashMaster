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
import dao.gerenciarDao.GerenciarPlacaVideoJDBCDAO;
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
import model.PlacaVideo;

/**
 * Classe controladora da tela GerenciarPlacaVideo.
 * @author Leonardo Elnisky.
 */
public class GerenciarPlacaVideoController {
    @FXML private BorderPane bPaneVideos;
    @FXML private JFXTextField tfBusca;
    @FXML private JFXButton btnDescartar;
    @FXML private TableView<PlacaVideo> tbVideos;
    @FXML private TableColumn<PlacaVideo, Integer> IDColumn;
    @FXML private TableColumn<PlacaVideo, Integer> gabineteColumn;
    @FXML private TableColumn<PlacaVideo, Integer> notebookColumn;
    @FXML private TableColumn<PlacaVideo, String> marcaColumn;
    @FXML private TableColumn<PlacaVideo, String> modeloColumn;
    @FXML private TableColumn<PlacaVideo, String> doadorColumn;
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
    	tbVideos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	carregarTabela(filtrarLista());
    }
    
    /**
     * Método para descartar todas as Placas de Vídeo selecionadas na tabela.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDescartarOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<PlacaVideo> selecionados = tbVideos.getSelectionModel().getSelectedItems();
    	
    	if (selecionados.isEmpty()) {
    		Main.infoDialog("Erro", "Selecione ao menos uma Placa de Vídeo a ser descartada.");
    	} else {
    		String descarte = "Placa(s) de vídeo (" + selecionados.size() + "):\n";
			for (PlacaVideo i : selecionados) {
				descarte += "- " + i.getMarca() + " " + i.getModelo() + "\n";
			}
			
			if (Main.confirmDialog("Confirmação", "Tem certeza que deseja descartar\n\n" + descarte)) {
        		GerenciarPlacaVideoJDBCDAO daoVideo = new GerenciarPlacaVideoJDBCDAO();
        		GerenciarDescartesJDBCDAO daoDesc = new GerenciarDescartesJDBCDAO();
        			
        		int resp = daoVideo.remover(selecionados);
        		if (resp > 0) {
        			Main.infoDialog("Aviso", resp + " Placa(s) de Vídeo foi(ram) descartada(s).");
        			daoDesc.adicionar(descarte);
        		} else Main.infoDialog("Erro", "Ocorreu um erro ao descartar " + resp + " Placa(s) de Vídeo.\nVerifique os valores e tente novamente.");
        		carregarTabela(filtrarLista());
			}
    	}
    }

    /**
     * Método para doar todas as Placas de Vídeo selecionadas na tabela.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     * @throws SQLException 
     */
    @FXML void btnDoarOnAction(ActionEvent event) throws SQLException, IOException {
    	ObservableList<PlacaVideo> selecionados = tbVideos.getSelectionModel().getSelectedItems();
    	
    	if (tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty() || selecionados.isEmpty()) {
    		String erro = "";
    		if (selecionados.isEmpty()) erro += "Escolha ao menos uma placa de vídeo.\n";
    		if (tfRecebedor.getText() == null || tfRecebedor.getText().trim().isEmpty()) erro += "Informe o recebedor.\n";

    		Main.infoDialog("Erro", erro);
    	} else {
    		String doacao = "Placa(s) de Vídeo (" + selecionados.size() + "):\n";
	    	for (PlacaVideo i : selecionados) {
	    		doacao += "- " + i.getMarca() + " " + i.getModelo() + "\n";
	    	}
	    	
	    	if (Main.confirmDialog("Confirmação", "Tem certeza que deseja doar\n\n" + doacao + "\nPara: " + tfRecebedor.getText())) {
			    GerenciarPlacaVideoJDBCDAO daoVideo = new GerenciarPlacaVideoJDBCDAO();
			    GerenciarDoacoesJDBCDAO daoDoacao = new GerenciarDoacoesJDBCDAO();
			    	
				int resp = daoVideo.remover(selecionados);
				if (resp > 0) {
					Main.infoDialog("Aviso", resp + " Placa(s) de Vídeo foi(ram) doada(s).");
					daoDoacao.adicionar(doacao, tfRecebedor.getText());
				} else Main.infoDialog("Erro", "Ocorreu um erro ao doar " + resp + " Placa(s) de Vídeo.\nVerifique os valores e tente novamente.");
				carregarTabela(filtrarLista());
	    	}
    	}
    }

    /**
     * Método para carregar e converter a lista de placas de vídeo do banco em uma lista filtrável.
     * @return filteredVideos lista filtrável de todas as placas de vídeo do banco de dados.
     * @throws SQLException
     * @throws IOException 
     */
    private FilteredList<PlacaVideo> filtrarLista() throws SQLException, IOException {
    	GerenciarPlacaVideoJDBCDAO daoVideo = new GerenciarPlacaVideoJDBCDAO();
    	List<PlacaVideo> videos = daoVideo.load();
    	ObservableList<PlacaVideo> listVideo = FXCollections.observableArrayList(videos);
    	FilteredList<PlacaVideo> filteredVideos = new FilteredList<PlacaVideo>(listVideo, p -> true);
    	
    	tfBusca.textProperty().addListener((observable, oldValue, newValue) -> {
    		filteredVideos.setPredicate(video -> {
    			if (newValue == null || newValue.isEmpty()) return true;
    			
    			String text = newValue.toLowerCase();
    			if (video.getMarca().toLowerCase().contains(text)) return true;
    			if (video.getModelo().toLowerCase().contains(text)) return true;
    			if (video.getDoador().toLowerCase().contains(text)) return true;
    			
    			return false;
    		});
    	});
    	return filteredVideos;
    }
    
    /**
     * Método para carregar/atualizar a tabela de placas de vídeo.
     * @param listVideo recebe a lista com as placas a serem exibidas na tabela.
     * @throws SQLException
     */
    private void carregarTabela(ObservableList<PlacaVideo> listVideo) throws SQLException {
    	IDColumn.setCellValueFactory(new PropertyValueFactory<PlacaVideo, Integer>("placaId"));
    	gabineteColumn.setCellValueFactory(new PropertyValueFactory<PlacaVideo, Integer>("gabineteId"));
    	notebookColumn.setCellValueFactory(new PropertyValueFactory<PlacaVideo, Integer>("notebookId"));
    	marcaColumn.setCellValueFactory(new PropertyValueFactory<PlacaVideo, String>("marca"));
    	modeloColumn.setCellValueFactory(new PropertyValueFactory<PlacaVideo, String>("modelo"));
    	doadorColumn.setCellValueFactory(new PropertyValueFactory<PlacaVideo, String>("doador"));
    	tbVideos.setItems(listVideo);
    }
}
