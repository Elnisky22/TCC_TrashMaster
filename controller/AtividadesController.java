package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXTextField;
import application.Main;
import dao.gerenciarDao.GerenciarAtividadesJDBCDAO;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import model.Atividade;
import application.GeradorPDF;

/**
 * Classe controladora da tela Atividades.
 * @author Leonardo Elnisky.
 *
 */
public class AtividadesController {
    @FXML private BorderPane bPaneAtividades;
    @FXML private VBox vBoxMeio;
    @FXML private JFXButton btnAtividade;
    @FXML private FontAwesomeIconView openIcon;
    @FXML private FontAwesomeIconView closeIcon;
    @FXML private JFXTextField tfBusca;
    @FXML private TableView<Atividade> tbAtividades;
    @FXML private TableColumn<Atividade, String> dataColumn;
    @FXML private TableColumn<Atividade, String> atividadeColumn;
    @FXML private TableColumn<Atividade, String> voluntarioColumn;
    @FXML private JFXDrawer drawer;
    @FXML private JFXButton btnGerarPDF;
    
    @FXML private AddAtividadeController addAtvController;
	
	/**
	 * Método para carregar tudo que é necessário para iniciar a tela Atividades.
	 * @throws IOException
	 * @throws SQLException 
	 */
    @FXML void initialize() throws IOException, SQLException {
    	FXMLLoader fxml = new FXMLLoader(getClass().getResource("/view/addAtividade.fxml"));
    	addAtvController = new AddAtividadeController();
    	addAtvController.setControllerPai(this);
    	fxml.setController(addAtvController);
    	VBox box = fxml.load();
    	drawer.setSidePane(box);
    	
    	openIcon = new FontAwesomeIconView();
		openIcon.setGlyphName("PLUS"); openIcon.setGlyphSize(16); openIcon.setFill(Paint.valueOf("WHITE"));
    	closeIcon = new FontAwesomeIconView();
		closeIcon.setGlyphName("CLOSE"); closeIcon.setGlyphSize(16); closeIcon.setFill(Paint.valueOf("WHITE"));
    	
    	atualizaTabela();
    	
    	tbAtividades.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	dataColumn.setMaxWidth(1f * Integer.MAX_VALUE * 7);
    	atividadeColumn.setMaxWidth(1f * Integer.MAX_VALUE * 70);
    	voluntarioColumn.setMaxWidth(1f * Integer.MAX_VALUE * 20);
    }

    /**
     * Método para abrir/fechar o Drawer de Adicionar Nova Atividade.
     * @param event recebe o evento ocorrido.
     * @throws SQLException 
     */
    @FXML void btnAtividadeOnAction(ActionEvent event) {
    	if (drawer.isOpened()) {
    		drawer.close();
    		vBoxMeio.toFront();
    		drawer.toBack();
    		btnAtividade.setGraphic(openIcon);
    		btnAtividade.setStyle("-fx-background-color: GREEN");
    	} else {
    		drawer.open();
    		vBoxMeio.toBack();
    		drawer.toFront();
    		btnAtividade.setGraphic(closeIcon);
    		btnAtividade.setStyle("-fx-background-color: #70b870");
    	}
    }
    
    /**
     * Método para gerar um Arquivo PDF contendo todas as Atividades armazenadas.
     * @param event recebe o evento ocorrido.
     * @throws Exception
     */
    @FXML void btGerarPDFOnAction(ActionEvent event) throws Exception {
    	GerenciarAtividadesJDBCDAO daoAtv = new GerenciarAtividadesJDBCDAO();
    	List<Atividade> atvs = daoAtv.load();
    	ObservableList<Atividade> listAtvs = FXCollections.observableArrayList(atvs);
    	
    	if(!listAtvs.isEmpty()) {
			PdfPTable table = new PdfPTable(3);
    		table.setWidths(new float[] {1, 3, 1});
    		
    		PdfPCell dataC = new PdfPCell(new Phrase("Data"));
    		dataC.setHorizontalAlignment(Element.ALIGN_CENTER);
    		PdfPCell atvC = new PdfPCell(new Phrase("Atividade"));
    		atvC.setHorizontalAlignment(Element.ALIGN_CENTER);
    		PdfPCell voluntC = new PdfPCell(new Phrase("Voluntário(s)"));
    		voluntC.setHorizontalAlignment(Element.ALIGN_CENTER);
    		
    		table.addCell(dataC);
    		table.addCell(atvC);
    		table.addCell(voluntC);
    		table.setHeaderRows(1);
    		
    		for (Atividade i : listAtvs) {
    			PdfPCell dataColumn = new PdfPCell(new Phrase(i.getData()));
    			dataColumn.setHorizontalAlignment(Element.ALIGN_CENTER);
    			table.addCell(dataColumn);
    			table.addCell(i.getAtividade());
    			table.addCell(i.getVoluntario());
    		}
    		Boolean resp = GeradorPDF.gerarPDF("Atividade", "E-Lixo: Relatório de Atividades", "Relatório de todas as atividades datadas do dia "
    		+ listAtvs.get(listAtvs.size() - 1).getData() + " até o dia " + listAtvs.get(0).getData() + ".", table);
    		
    		if (resp) Main.infoDialog("Aviso", "O PDF foi gerado e está no caminho\nDocumentos\\TrashMaster\\Atividades\\");
    		else Main.infoDialog("Erro", "Ocorreu um erro ao gerar o PDF.");
    	} else Main.infoDialog("Erro", "Não é possível Gerar um Relatório em branco.");
    }
    
    /**
     * Método para carregar e converter a lista de atividades do banco em uma lista filtrável.
     * @return filteredAtividades lista filtrável de todas as atividades do banco de dados.
     * @throws SQLException
     * @throws IOException 
     */
    private FilteredList<Atividade> filtrarLista() throws SQLException, IOException {
    	GerenciarAtividadesJDBCDAO daoAtividade = new GerenciarAtividadesJDBCDAO();
    	List<Atividade> atvs = daoAtividade.load();
    	ObservableList<Atividade> listAtvs = FXCollections.observableArrayList(atvs);
    	FilteredList<Atividade> filteredAtividades = new FilteredList<Atividade>(listAtvs, p -> true);
    	
    	tfBusca.textProperty().addListener((observable, oldValue, newValue) -> {
    		filteredAtividades.setPredicate(atv -> {
    			if (newValue == null || newValue.isEmpty()) return true;
    			
    			String text = newValue.toLowerCase();
    			if (atv.getData().toLowerCase().contains(text)) return true;
    			if (atv.getAtividade().toLowerCase().contains(text)) return true;
    			if (atv.getVoluntario().toLowerCase().contains(text)) return true;
    			
    			return false;
    		});
    	});
    	return filteredAtividades;
    }
    
    /**
     * Método para carregar/atualizar a tabela de atividades.
     * @param listDiscos recebe a lista com as atividades a serem exibidas na tabela.
     * @throws SQLException
     */
    private void carregarTabela(ObservableList<Atividade> listAtvs) throws SQLException {
    	dataColumn.setCellValueFactory(new PropertyValueFactory<Atividade, String>("data"));
    	atividadeColumn.setCellValueFactory(new PropertyValueFactory<Atividade, String>("atividade"));
    	voluntarioColumn.setCellValueFactory(new PropertyValueFactory<Atividade, String>("voluntario"));
    	tbAtividades.setItems(listAtvs);
    }
    
    /**
     * Método para atualizar a tabela por outras classes.
     * @throws SQLException
     * @throws IOException
     */
    public void atualizaTabela() throws SQLException, IOException {
    	carregarTabela(filtrarLista());
    }
    
    /**
     * Método para gerenciar o Drawer por outras classes.
     */
    public void manageDrawer() {
    	if (drawer.isOpened()) {
    		drawer.close();
    		vBoxMeio.toFront();
    		drawer.toBack();
    		btnAtividade.setGraphic(openIcon);
    		btnAtividade.setStyle("-fx-background-color: GREEN");
    	} else {
    		drawer.open();
    		vBoxMeio.toBack();
    		drawer.toFront();
    		btnAtividade.setGraphic(closeIcon);
    		btnAtividade.setStyle("-fx-background-color: #70b870");
    	}
    }
}
