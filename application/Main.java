package application;

import java.io.IOException;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import dao.ConexaoGetPropertyValues;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

/**
 * Classe inicializadora do programa
 * @author Leonardo Elnisky
 */
public class Main extends Application {
	@FXML private static BorderPane root = new BorderPane();
	@FXML private static Boolean confirm;
	@FXML private static JFXDialogLayout content;
	@FXML private static FontAwesomeIconView cancelIcon;
	@FXML private static FontAwesomeIconView checkIcon;
	@FXML private static FontAwesomeIconView checkIcon2;
	@FXML private static JFXButton btnCancel;
	@FXML private static JFXButton btnConfirm;
	@FXML private static JFXButton btnOk;
	@FXML private static JFXAlert<Object> alert;
	
	/**
	 * Método para iniciar o programa
	 * @param args
	 */
	public static void main(String[] args){
		Application.launch(args);
	}
	
	/**
	 * Método para iniciar o programa, configurando o banco e as telas.
	 * @throws IOException
	 */
	@Override
	public void start(Stage stage) throws IOException{
		// Configurar o banco
		ConexaoGetPropertyValues.setVars();
		
		// Carregar as telas inicias
		Scene scene = new Scene(root, 1020, 720);
		scene.getStylesheets().add(getClass().getClassLoader().getResource("view/css/main.css").toExternalForm());
		
		MenuBar menubar = FXMLLoader.load(getClass().getResource("/view/menuBar.fxml"));
		root.setTop(menubar);
		
		VBox inicio = FXMLLoader.load(getClass().getResource("/view/inicio.fxml"));
		root.setLeft(inicio);
		root.setStyle("-fx-background-color: #BEFF93");
		
		
		// Settar o Alert
		content = new JFXDialogLayout();
		content.getStylesheets().add(getClass().getResource("/view/css/main.css").toExternalForm());
		
		cancelIcon = new FontAwesomeIconView();
		cancelIcon.setGlyphName("CLOSE"); cancelIcon.setGlyphSize(16); cancelIcon.setFill(Paint.valueOf("WHITE"));
		checkIcon = new FontAwesomeIconView();
		checkIcon.setGlyphName("CHECK"); checkIcon.setGlyphSize(16); checkIcon.setFill(Paint.valueOf("WHITE"));
		checkIcon2 = new FontAwesomeIconView();
		checkIcon2.setGlyphName("CHECK"); checkIcon2.setGlyphSize(16); checkIcon2.setFill(Paint.valueOf("WHITE"));
		
		btnCancel = new JFXButton("Cancelar");
		btnCancel.getStyleClass().add("botao"); btnCancel.setGraphic(cancelIcon);
		btnConfirm = new JFXButton("Confirmar");
		btnConfirm.getStyleClass().add("botao"); btnConfirm.setGraphic(checkIcon);
		btnOk = new JFXButton("Ok");
		btnOk.getStyleClass().add("botao"); btnOk.setGraphic(checkIcon2);
		
		alert = new JFXAlert<Object>();
		alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
    	alert.initStyle(StageStyle.TRANSPARENT);
    	Stage stg = (Stage) alert.getDialogPane().getScene().getWindow(); stg.getIcons().add(new Image("/images/icon.png"));
		
    	btnCancel.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			confirm = false;
    			alert.close();
    		}
    	});
    	btnConfirm.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			confirm = true;
    			alert.close();
    		}
    	});
    	btnOk.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			alert.close();
    		}
    	});
    	
    	
		// Exibe a cena na tela
		stage.getIcons().add(new Image("/images/icon.png"));
		stage.setTitle("TrashMaster");
		stage.setScene(scene);
		stage.setMinWidth(1020.0);
		stage.setMinHeight(720.0);
		stage.setMaximized(true);
		stage.show();
	}
	
	/**
	 * Método para pegar o painel principal do programa
	 * @return o painel
	 */
	public static BorderPane getRoot(){
		return root;
	}
	
	/**
	 * Método para gerar e apresentar um Alert/Dialog de Confirmação (Cancelar/Confirmar).
	 * @param title recebe o título a ser apresentado no Alert.
	 * @param body recebe o conteúdo a ser apresentado no Alert.
	 * @return confirm qual botão foi clicado, se cancela ou confirma.
	 */
	public static Boolean confirmDialog(String title, String body) {
    	content.setHeading(new Text(title));
    	content.setBody(new Text(body));
    	alert.setTitle(title);

    	content.setActions(btnCancel, btnConfirm);
       	alert.setContent(content);
    	alert.showAndWait();
    	return confirm;
	}
	
	/**
	 * Método para gerar e apresentar um Alert/Dialog de Informação(apenas Ok).
	 * @param title recebe o título a ser apresentado no Alert.
	 * @param body recebe o conteúdo a ser apresentado no Alert.
	 */
	public static void infoDialog(String title, String body) {
    	content.setHeading(new Text(title));
    	content.setBody(new Text(body));
    	alert.setTitle(title);
    	
    	content.setActions(btnOk);
       	alert.setContent(content);
    	alert.showAndWait();
	}
}
