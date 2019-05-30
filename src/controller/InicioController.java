package controller;

import java.io.IOException;
import application.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import com.jfoenix.controls.JFXButton;

/**
 * Classe controladora da tela Inicio.
 * @author Leonardo Elnisky
 */
public class InicioController {
    @FXML private VBox vBoxInicio;
    @FXML private ImageView imgLogo;
    @FXML private JFXButton btnEstoque;
    @FXML private JFXButton btnAtividades;
    @FXML private JFXButton btnDoacoes;
    @FXML private JFXButton btnDescarte;
    @FXML private JFXButton btnSair;
    
    private FXMLLoader fxml;
    
    /**
     * M�todo para carregar os requisitos necess�rios ao iniciar a tela In�cio.
     * @throws IOException
     */
    @FXML private void initialize() throws IOException {
    	load("visualizar.fxml");
    }
    
    /**
     * M�todo para carregar a cena selecionada na tela.
     */
    private void load(String tela) {
    	String caminho = "/view/" + tela;
    	
    	try {
    		fxml = new FXMLLoader(getClass().getResource(caminho));
    		BorderPane root = Main.getRoot();
    		root.setCenter(fxml.load());
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * M�todo para carregar a cena Visualizar na tela.
     * @param event recebe o evento ocorrido.
     */
    @FXML void btnEstoqueOnAction(ActionEvent event) {
    	load("visualizar.fxml");
    }
    
    /**
     * M�todo para carregar a cena Atividades na tela.
     * @param event recebe o evento ocorrido.
     */
    @FXML void btnAtividadesOnAction(ActionEvent event) {
    	load("atividades.fxml");
    }
    
    /**
     * M�todo para carregar a cena Doa��es na tela.
     * @param event recebe o evento ocorrido.
     */
    @FXML void btnDoacoesOnAction(ActionEvent event) {
    	load("doacoes.fxml");
    }

    /**
     * M�todo para carregar a cena Descarte na tela.
     * @param event recebe o evento ocorrido.
     */
    @FXML void btnDescarteOnAction(ActionEvent event) {
    	load("descarte.fxml");
    }
    
    /**
     * M�todo para fechar o programa.
     * @param event recebe o evento ocorrido.
     */
    @FXML void btnSairOnAction(ActionEvent event) {
    	if (Main.confirmDialog("Confirma��o", "Tem certeza que deseja sair?")) Platform.exit();
    }
}
