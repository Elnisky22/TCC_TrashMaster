package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import application.GeradorPDF;
import application.Main;
import dao.gerenciarDao.GerenciarDoacoesJDBCDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import model.Doacao;

/**
 * Classe controladora da da tela Doacoes.
 * @author Leonardo Elnisky
 */
public class DoacoesController {
    @FXML private BorderPane bPaneDoar;
    @FXML private JFXTextField tfBusca;
    @FXML private TableView<Doacao> tbDoacoes;
    @FXML private TableColumn<Doacao, String> dataColumn;
    @FXML private TableColumn<Doacao, String> itensColumn;
    @FXML private TableColumn<Doacao, String> recebedorColumn;
    @FXML private JFXButton btnGerarPDF;
    
    /**
     * Método para carregar tudo que é necessário para iniciar a tela Doações.
     * @throws SQLException
     * @throws IOException 
     */
    @FXML void initialize() throws SQLException, IOException {
    	carregarTabela(filtrarLista());
    	
    	tbDoacoes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	dataColumn.setMaxWidth(1f * Integer.MAX_VALUE * 15);
    	itensColumn.setMaxWidth(1f * Integer.MAX_VALUE * 70);
    	recebedorColumn.setMaxWidth(1f * Integer.MAX_VALUE * 20);
    }
    
    /**
     * Método para gerar um Arquivo PDF com todas as Doações armazenadas.
     * @param event recebe o evento ocorrido.
     * @throws Exception
     */
    @FXML  void btnGerarPDFOnAction(ActionEvent event) throws Exception {
    	GerenciarDoacoesJDBCDAO daoDoacoes = new GerenciarDoacoesJDBCDAO();
    	List<Doacao> doacoes = daoDoacoes.load();
    	ObservableList<Doacao> listDoacoes = FXCollections.observableArrayList(doacoes);
    	
    	if (!listDoacoes.isEmpty()) {
    		PdfPTable table = new PdfPTable(3);
    		table.setWidths(new float[] {1, 3, 1});
    		
    		PdfPCell dataC = new PdfPCell(new Phrase("Data"));
    		dataC.setHorizontalAlignment(Element.ALIGN_CENTER);
    		PdfPCell itemC = new PdfPCell(new Phrase("Itens"));
    		itemC.setHorizontalAlignment(Element.ALIGN_CENTER);
    		PdfPCell recebedorC = new PdfPCell(new Phrase("Recebedor"));
    		recebedorC.setHorizontalAlignment(Element.ALIGN_CENTER);
	    		
	    	table.addCell(dataC);
	    	table.addCell(itemC);
	    	table.addCell(recebedorC);
	    	table.setHeaderRows(1);
	    		
    		for (Doacao i : listDoacoes) {
    			PdfPCell dataColumn = new PdfPCell(new Phrase(i.getData()));
    			dataColumn.setHorizontalAlignment(Element.ALIGN_CENTER);
    			table.addCell(dataColumn);
    			table.addCell(i.getItens());
    			table.addCell(i.getRecebedor());
    		}
    		Boolean resp = GeradorPDF.gerarPDF("Doação", "E-Lixo: Relatório de Doações", "Relatório de todas as doações datadas do dia "
    		+ listDoacoes.get(listDoacoes.size() - 1).getData() + " até o dia " + listDoacoes.get(0).getData() + ".", table);
	    	
    		if (resp) Main.infoDialog("Aviso", "O PDF foi gerado e está no caminho\nDocumentos\\TrashMaster\\Doações\\");
    		else Main.infoDialog("Erro", "Ocorreu um erro ao gerar o PDF.");
    	} else Main.infoDialog("Erro", "Não é possível gerar um Relatório em branco.");
    }
    
    
    /**
     * Método para carregar uma lista filtrável de todas as doações salvas no banco de dados.
     * @return uma lista filtrável contendo todas as doações.
     * @throws SQLException
     * @throws IOException 
     */
    private FilteredList<Doacao> filtrarLista() throws SQLException, IOException {
    	GerenciarDoacoesJDBCDAO daoDoacoes = new GerenciarDoacoesJDBCDAO();
    	List<Doacao> doacoes = daoDoacoes.load();
    	ObservableList<Doacao> listDoacoes = FXCollections.observableArrayList(doacoes);
    	FilteredList<Doacao> filteredDoacoes = new FilteredList<Doacao>(listDoacoes, p -> true);
    	
    	tfBusca.textProperty().addListener((observable, oldValue, newValue) -> {
    		filteredDoacoes.setPredicate(doacao -> {
    			if (newValue == null || newValue.isEmpty()) return true;
    			
    			String text = newValue.toLowerCase();
    			if (doacao.getData().contains(text)) return true;
    			if (doacao.getItens().toLowerCase().contains(text)) return true;
    			if (doacao.getRecebedor().toLowerCase().contains(text)) return true;
    			
    			return false;
    		});
    	});
    	return filteredDoacoes;
    }
    
    /**
     * Carrega a tabela com a lista na tela.
     * @param listDoacoes recebe uma lista de doações a serem exibidas.
     * @throws SQLException
     */
    private void carregarTabela(ObservableList<Doacao> listDoacoes) throws SQLException {
    	dataColumn.setCellValueFactory(new PropertyValueFactory<Doacao, String>("data"));
    	itensColumn.setCellValueFactory(new PropertyValueFactory<Doacao,String>("itens"));
    	recebedorColumn.setCellValueFactory(new PropertyValueFactory<Doacao,String>("recebedor"));
    	tbDoacoes.setItems(listDoacoes);
    }
}
