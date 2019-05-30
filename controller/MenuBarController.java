package controller;

import application.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * Classe controladora da tela MenuBar.
 * @author Leonardo Elnisky
 */
public class MenuBarController{
	    @FXML private MenuBar menubar;
	    @FXML private Menu menuArquivo;
	    @FXML private Menu menuAjuda;
	    @FXML private MenuItem miSair;
	    @FXML private MenuItem miSobre;

	    /**
	     * M�todo para sair da aplica��o.
	     * @param event
	     */
	    @FXML void sair(ActionEvent event) {
	    	if (Main.confirmDialog("Confirma��o", "Tem certeza que deseja sair?")) Platform.exit();
	    }

	    /**
	     * M�todo que exibe a tela Sobre da aplica��o.
	     * @param event
	     */
	    @FXML void miSobreOnAction(ActionEvent event) {
	    	Main.infoDialog("Sistema de Gest�o de Estoque: E-Lixo", "Sistema desenvolvido como Trabalho de Conclus�o de Curso para o gerenciamento de estoque do Programa de Extens�o E-Lixo.\n\n � 2019, Leonardo Elnisky");
	    }
	    
	}
