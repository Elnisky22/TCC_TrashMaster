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
	     * Método para sair da aplicação.
	     * @param event
	     */
	    @FXML void sair(ActionEvent event) {
	    	if (Main.confirmDialog("Confirmação", "Tem certeza que deseja sair?")) Platform.exit();
	    }

	    /**
	     * Método que exibe a tela Sobre da aplicação.
	     * @param event
	     */
	    @FXML void miSobreOnAction(ActionEvent event) {
	    	Main.infoDialog("Sistema de Gestão de Estoque: E-Lixo", "Sistema desenvolvido como Trabalho de Conclusão de Curso para o gerenciamento de estoque do Programa de Extensão E-Lixo.\n\n © 2019, Leonardo Elnisky");
	    }
	    
	}
