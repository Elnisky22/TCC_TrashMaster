package controller;

import java.io.IOException;
import java.sql.SQLException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

/**
 * Classe controladora da tela Visualizar.
 * @author Leonardo Elnisky
 */
public class VisualizarController {
    @FXML private BorderPane bPaneVisualizar;
    @FXML private JFXButton btnAdicionar;
    @FXML private FontAwesomeIconView openIcon;
    @FXML private FontAwesomeIconView closeIcon;
    @FXML private VBox vBoxMeio;
    @FXML private HBox hBoxVisualizar;
    @FXML private JFXComboBox<String> cBoxTipoItem;
    @FXML private BorderPane bPaneEscolhido;
    @FXML private BorderPane bPaneTabelas;
    @FXML private JFXDrawer drawer;
    @FXML private Label lblDica;
    
    @FXML private AdicionarController addController;
    
    private FXMLLoader fxml;
    
    ObservableList<String> tipos = FXCollections.
    		observableArrayList("Cabo", "Disco Rígido", "Fonte", "Gabinete", "Impressora", "Leitor de Disco", "Memória", "Monitor", "Mouse",
    				"Notebook", "Outros", "Placa Mãe", "Placa de Rede", "Placa de Vídeo", "Processador", "Teclado");
    
    /**
     * Método para carregar os requisitos necessários para iniciar a tela Visualizar.
     * @throws IOException
     * @throws SQLException 
     */
    @FXML private void initialize() throws IOException, SQLException {
    	FXMLLoader fxml = new FXMLLoader(getClass().getResource("/view/adicionar.fxml"));
    	addController = new AdicionarController();
    	addController.setPaiController(this);
    	fxml.setController(addController);
    	VBox box = fxml.load();	
    	drawer.setSidePane(box);
    	
    	openIcon = new FontAwesomeIconView();
		openIcon.setGlyphName("PLUS"); openIcon.setGlyphSize(16); openIcon.setFill(Paint.valueOf("WHITE"));
    	closeIcon = new FontAwesomeIconView();
		closeIcon.setGlyphName("CLOSE"); closeIcon.setGlyphSize(16); closeIcon.setFill(Paint.valueOf("WHITE"));
    	
    	cBoxTipoItem.setItems(tipos);
    	cBoxTipoItem.getSelectionModel().selectFirst();
    	
    	setTabela("Cabo.fxml");
    	addController.setCentro("Cabo.fxml");
    }    
    
    /**
     * Método para carregar a tela correspondente ao tipo selecionado no ComboBox,
     * também envia para o AdicionarController.
     * @param event recebe o evento ocorrido.
     * @throws IOException 
     */
    @FXML void cBoxTipoOnAction(ActionEvent event) throws IOException {
    	String selecionado = cBoxTipoItem.getValue();
    	String caminho;
    	
    	switch(selecionado) {
    		case "Cabo":			caminho = "Cabo.fxml";			break;
    		case "Disco Rígido": 	caminho = "Disco.fxml"; 		break;
    		case "Fonte":			caminho = "Fonte.fxml";			break;
    		case "Gabinete":		caminho = "Gabinete.fxml";		break;
    		case "Impressora":		caminho = "Impressora.fxml";	break;
    		case "Leitor de Disco":	caminho = "Leitor.fxml";		break;
    		case "Memória":			caminho = "Memoria.fxml";		break;
    		case "Monitor":			caminho = "Monitor.fxml";		break;
    		case "Mouse":			caminho = "Mouse.fxml";			break;
    		case "Notebook":		caminho = "Notebook.fxml";		break;
    		case "Outros":			caminho = "Outros.fxml";		break;
    		case "Placa Mãe":		caminho = "PlacaMae.fxml";		break;
    		case "Placa de Rede":	caminho = "PlacaRede.fxml";		break;
    		case "Placa de Vídeo":	caminho = "PlacaVideo.fxml";	break;
    		case "Processador":		caminho = "Processador.fxml";	break;
    		case "Teclado":			caminho = "Teclado.fxml";		break;
    		default: return;
    	}
    	setTabela(caminho);
    	addController.setCentro(caminho);
    }
    
    /**
     * Método para gerenciamento do botão Adicionar, abrindo e fechando o drawer.
     * @param event recebe o evento ocorrido.
     */
    @FXML void btnAdicionarOnAction(ActionEvent event) {
    	if (drawer.isOpened()) {
    		drawer.close();
    		vBoxMeio.toFront();
    		drawer.toBack();
    		btnAdicionar.setGraphic(openIcon);
    		btnAdicionar.setStyle("-fx-background-color: GREEN");
    	} else {
    		drawer.open();
    		vBoxMeio.toBack();
    		drawer.toFront();
    		btnAdicionar.setGraphic(closeIcon);
    		btnAdicionar.setStyle("-fx-background-color: #70b870");
    	}
    }
    
    /**
     * Método para pegar o tipo selecionado no comboBox Tipo.
     * @return o valor do tipo selecionado.
     */
    public String getTipo() {
    	return cBoxTipoItem.getValue();
    }
    
    /**
     * Método para carregar uma tela de tabela de itens no centro de Visualizar.
     * @param caminho recebe o caminho da tela a ser carregada.
     * @throws IOException
     */
    public void setTabela(String tela) throws IOException {
    	String caminho = "/view/gerenciarView/gerenciar" + tela;
    	
    	fxml = new FXMLLoader(getClass().getResource(caminho));
		bPaneEscolhido = fxml.load();
		bPaneTabelas.setCenter(bPaneEscolhido);
    }
}
