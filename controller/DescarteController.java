package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import application.GeradorPDF;
import application.Main;
import dao.gerenciarDao.GerenciarDescartesJDBCDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import model.Descarte;

/**
 * Classe controladora da tela Descartes.
 * @author Leonardo Elnisky.
 */
public class DescarteController {
	@FXML private BorderPane bPaneDescarte;
    @FXML private JFXTextField tfBusca;
    @FXML private TableView<Descarte> tbDescarte;
    @FXML private TableColumn<Descarte, String> dataColumn;
    @FXML private TableColumn<Descarte, String> itensColumn;
    @FXML private JFXButton btnLimpar;
    @FXML private JFXButton btnGerarPDF;
    
    /**
     * Método para carregar tudo que é necessário para iniciar a tela Descarte.
     * @throws SQLException
     * @throws IOException 
     */
    @FXML void initialize() throws SQLException, IOException {
    	carregarTabela(filtrarLista());
    	
    	tbDescarte.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	dataColumn.setMaxWidth(1f * Integer.MAX_VALUE * 10);
    	itensColumn.setMaxWidth(1f * Integer.MAX_VALUE * 80);
    }

    /**
     * Método para limpar toda a lista de Descartes no banco.
     * @param event recebe o evento ocorrido.
     * @throws SQLException
     */
    @FXML void btnLimparOnAction(ActionEvent event) throws SQLException {
    	ObservableList<Descarte> listDesc = tbDescarte.getItems();
    	
    	if (listDesc.isEmpty()) {
    		Main.infoDialog("Erro", "A lista já está vazia.");
    	} else {
    		Boolean resposta = Main.confirmDialog("Confirmação", "Tem Certeza que deseja limpar a lista de descartes?");
        	
        	if (resposta == true) {
        		GerenciarDescartesJDBCDAO daoDesc = new GerenciarDescartesJDBCDAO();
    	    	try {
    				daoDesc.limpar();
    				carregarTabela(filtrarLista());
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
        	}
    	}
    }
    
    /**
     * Método para gerar um Arquivo PDF contendo todos os Descartes armazenados.
     * @param event recebe o evento ocorrido.
     * @throws SQLException
     * @throws IOException
     * @throws DocumentException
     */
    @FXML void btnGerarPDFOnAction(ActionEvent event) throws Exception {
    	GerenciarDescartesJDBCDAO daoDesc = new GerenciarDescartesJDBCDAO();
    	List<Descarte> descs = daoDesc.load();
    	ObservableList<Descarte> listDesc = FXCollections.observableArrayList(descs);
    	
    	if (!listDesc.isEmpty()) { 
			PdfPTable table = new PdfPTable(2);
			table.setWidths(new float[] {1, 3});
			
			PdfPCell dataC = new PdfPCell(new Phrase("Data"));
			dataC.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell itemC = new PdfPCell(new Phrase("Itens"));
			itemC.setHorizontalAlignment(Element.ALIGN_CENTER);

    		table.addCell(dataC);
    		table.addCell(itemC);
    		table.setHeaderRows(1);
    		
    		for (Descarte i : listDesc) {
    			PdfPCell dataColumn = new PdfPCell(new Phrase(i.getData()));
    			dataColumn.setHorizontalAlignment(Element.ALIGN_CENTER);
    			table.addCell(dataColumn);
    			table.addCell(i.getItens());
    		}
	    	Boolean resp = GeradorPDF.gerarPDF("Descarte", "E-Lixo: Relatório de Descartes", "Relatório de todos os descartes datados do dia "
	    	+ listDesc.get(listDesc.size() - 1).getData() + " até o dia " + listDesc.get(0).getData() + ".", table);
	    	
	    	if (resp) Main.infoDialog("Aviso", "O PDF foi gerado e está no caminho\nDocumentos\\TrashMaster\\Descartes\\");
	    	else Main.infoDialog("Erro", "Ocorreu um erro ao gerar o PDF.");
    	} else Main.infoDialog("Erro", "Não é possível Gerar um Relatório em branco.");
    }

    /**
     * Método para carregar e converter a lista de descartes do banco em uma lista filtrável.
     * @return filteredDesc lista filtrável de todos os descartes do banco de dados.
     * @throws SQLException
     * @throws IOException 
     */
    private FilteredList<Descarte> filtrarLista() throws SQLException, IOException {
    	GerenciarDescartesJDBCDAO daoDesc = new GerenciarDescartesJDBCDAO();
    	List<Descarte> descartes = daoDesc.load();
    	ObservableList<Descarte> listDesc = FXCollections.observableArrayList(descartes);
    	FilteredList<Descarte> filteredDesc = new FilteredList<Descarte>(listDesc, p -> true);
    	
    	tfBusca.textProperty().addListener((observable, oldValue, newValue) -> {
    		filteredDesc.setPredicate(desc -> {
    			if (newValue == null || newValue.isEmpty()) return true;
    			
    			String text = newValue.toLowerCase();
    			if (desc.getData().toLowerCase().contains(text)) return true;
    			if (desc.getItens().toLowerCase().contains(text)) return true;
    			
    			return false;
    		});
    	});
    	return filteredDesc;
    }
    
    /**
     * Método para carregar/atualizar a tabela de discos.
     * @param listDiscos recebe a lista com os discos para serem exibidos na tabela.
     */
    private void carregarTabela(ObservableList<Descarte> listDesc) {
    	dataColumn.setCellValueFactory(new PropertyValueFactory<Descarte, String>("data"));
    	itensColumn.setCellValueFactory(new PropertyValueFactory<Descarte, String>("itens"));
    	tbDescarte.setItems(listDesc);
    }
}
