package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.function.UnaryOperator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import application.Main;
import controller.adicionarController.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.*;

/**
 * Classe para controlar a tela Adicionar
 * @author Leonardo Elnisky
 */
public class AdicionarController {
	@FXML private VBox box;
    @FXML private HBox hBoxTop;
    @FXML private JFXTextField tfQuantidade;
    @FXML private BorderPane bPaneEscolhido;
    @FXML private HBox hBoxBot;
    @FXML private JFXTextField tfDoador;
    @FXML private JFXButton btnVoltar;
    @FXML private JFXButton btnConfirmar;
    @FXML private GridPane gPaneEscolhido;
    
    private VisualizarController visuController;
    private FXMLLoader fxml;
    
    // Formatar tf quantidade
    TextFormatter<String> quantidadeTF = new TextFormatter<>((UnaryOperator<TextFormatter.Change>) change -> {
	    String concatenado = tfQuantidade.getText() + change.getText();
	    return (concatenado.matches("\\d*") && concatenado.length() < 4) ? change : null;
	});
    // Formatar tf doador
    TextFormatter<String> doadorTF = new TextFormatter<>((UnaryOperator<TextFormatter.Change>) change -> {
	    String concatenado = tfDoador.getText() + change.getText();
	    return (concatenado.length() < 51) ? change : null;
	});
    
    /**
     * Método para carregar o que é necessário para inicializar a tela Adicionar.
     */
    @FXML private void initialize() {
    	tfQuantidade.setTextFormatter(quantidadeTF);
    	tfDoador.setTextFormatter(doadorTF);
    	tfDoador.setVisible(false);
    }
    
    /**
     * Método para pegar tudo que foi inserido na tela de Adicionar e enviar para o JDBCDAO correspondente.
     * @param event recebe o evento ocorrido.
     * @throws SQLException
     * @throws IOException 
     */
    @FXML void btnConfirmarOnAction(ActionEvent event) throws SQLException, IOException {
    	String selecionado = visuController.getTipo();
    	int resp = 0;
    	
    	if (selecionado == null) {
    		Main.infoDialog("Erro", "Selecione um tipo a ser adicionado.");
    	} else if (tfQuantidade.getText() == null || tfQuantidade.getText().trim().isEmpty() ||
    		((tfDoador.getText() == null || tfDoador.getText().trim().isEmpty()) && !selecionado.equals("Cabo"))) {
    		
    		String erro = "";
    		if (tfQuantidade.getText() == null || tfQuantidade.getText().trim().isEmpty())
    			erro += "Insira uma quantidade de " + selecionado + " a ser adicionado(a).\n";
    		if ((tfDoador.getText() == null || tfDoador.getText().trim().isEmpty()) && !selecionado.equals("Cabo"))
    			erro += "Insira o doador.\n";
    		
    		Main.infoDialog("Erro", erro);
    	} else {
    		String erro = "";
	    	switch(selecionado) {
	    		case "Cabo":
	    			if (fxml.<AdicionarCaboController>getController().getTipo() == null) {
	    				Main.infoDialog("Erro", "Selecione o tipo de cabo a ser adicionado");
	    			} else {
		    			dao.gerenciarDao.GerenciarCaboJDBCDAO daoCabo = new dao.gerenciarDao.GerenciarCaboJDBCDAO();
		    			Cabo cabo = new Cabo();
			    		cabo.setTipo(fxml.<AdicionarCaboController>getController().getTipo());
			    		cabo.setQuantidade(Integer.parseInt(tfQuantidade.getText()));
			    		
			    		if (Main.confirmDialog("Confirmação", "Tem certeza que deseja adicionar\n\nCabo(s) (" + cabo.getQuantidade() + "):\n- " + cabo.getTipo())) {
			    			resp = daoCabo.adicionar(cabo);
			    			
			    			if (resp != 0) Main.infoDialog("Aviso", cabo.getQuantidade() + " cabo(s) do tipo " + cabo.getTipo() + " foi(ram) adicionado(s).");
			    			else Main.infoDialog("Erro", "Ocorreu um erro ao adicionar " + cabo.getQuantidade() + " cabo(s) do tipo " + cabo.getTipo()
			    				+ "\nVerifique os valores e tente novamente.");
			    			fxml.<AdicionarCaboController>getController().limparCampos();
			    			limparCampos(); visuController.setTabela("Cabo.fxml"); resp = 0;
			    		}
	    			}
	    		break;
	    		case "Disco Rígido":
	    			dao.gerenciarDao.GerenciarDiscoJDBCDAO daoDisco = new dao.gerenciarDao.GerenciarDiscoJDBCDAO();
	    			DiscoRigido disco = new DiscoRigido();
	    			disco.setMarca(fxml.<AdicionarDiscoController>getController().getMarca());
	    			if (fxml.<AdicionarDiscoController>getController().getChecked()) disco.setModelo("Desconhecido");
	    			else disco.setModelo(fxml.<AdicionarDiscoController>getController().getModelo());
	    			disco.setCapacidade(fxml.<AdicionarDiscoController>getController().getCapacidade());
	    			disco.setDoador(tfDoador.getText());
	    			
	    			if (disco.getMarca().isEmpty()) erro += "Informe a marca.\n";
	    			if (disco.getModelo().isEmpty()) erro += "Informe o modelo.\n";
	    			if (disco.getCapacidade() < 0.0) erro += "Informe a capacidade.";
	    				
	   				if (!erro.isEmpty()) { 
	    				Main.infoDialog("Erro", erro);
	    			} else {
	    				if (Main.confirmDialog("Confirmação", "Tem certeza que deseja adicionar\n\nDisco(s) Rígido(s) (" + tfQuantidade.getText() + "):\n- "
	    				+ disco.getMarca() + " " + disco.getModelo() + " " + disco.getCapacidade() + "Gbs\n\nDoador: " + tfDoador.getText())) {
	    					resp = daoDisco.adicionar(disco, Integer.parseInt(tfQuantidade.getText()));
		    			
	    					if (resp != 0) Main.infoDialog("Aviso", resp + " Disco(s) Rígido(s) foi(ram) adicionado(s).");
	    					else Main.infoDialog("Erro", "Ocorreu um erro ao adicionar " + tfQuantidade.getText() + "disco(s).\nVerifique os valores e tente novamente.");
		    			
	    					fxml.<AdicionarDiscoController>getController().limparCampos();
	    					limparCampos(); visuController.setTabela("Disco.fxml"); resp = 0;
	    				}
	    			}
	    		break;
	    		case "Fonte":
	    			dao.gerenciarDao.GerenciarFonteJDBCDAO daoFonte = new dao.gerenciarDao.GerenciarFonteJDBCDAO();
	    			Fonte fonte = new Fonte();
	    			fonte.setMarca(fxml.<AdicionarFonteController>getController().getMarca());
	    			if (fxml.<AdicionarFonteController>getController().getChecked()) fonte.setModelo("Desconhecido");
	    			else fonte.setModelo(fxml.<AdicionarFonteController>getController().getModelo());
	    			fonte.setPotencia(fxml.<AdicionarFonteController>getController().getPotencia());
	    			fonte.setVoltagem(fxml.<AdicionarFonteController>getController().getVoltagem());
	    			fonte.setDoador(tfDoador.getText());
	    			
	    			if (fonte.getMarca().isEmpty()) erro += "Informe a marca.\n";
	    			if (fonte.getModelo().isEmpty()) erro += "Informe o modelo.\n";
	    			if (fonte.getPotencia().isEmpty()) erro += "Informe a potência.\n";
	    			if (fonte.getVoltagem().equals("null")) erro += "Informe a voltagem.";
	    			
	    			if (!erro.isEmpty()) {
	    				Main.infoDialog("Erro", erro);
	    			} else {
	    				if (Main.confirmDialog("Confirmação", "Tem certeza que deseja adicionar\n\nFonte(s) (" + tfQuantidade.getText() + "):\n- "
	    				+ fonte.getMarca() + " " + fonte.getModelo() + " " + fonte.getPotencia() + "W " + fonte.getVoltagem() + "\n\nDoador :" + tfDoador.getText())) {
	    					resp = daoFonte.adicionar(fonte, Integer.parseInt(tfQuantidade.getText()));
		    			
	    					if (resp != 0) Main.infoDialog("Aviso", resp + " Fonte(s) foi(ram) adicionada(s).");
	    					else Main.infoDialog("Erro", "Ocorreu um erro ao adicionar " + tfQuantidade.getText() + " fonte(s).\nVerifique os valores e tente novamente.");
	    					fxml.<AdicionarFonteController>getController().limparCampos();
	    					limparCampos(); visuController.setTabela("Fonte.fxml"); resp = 0;
	    				}
	    			}
	    		break;
	    		case "Gabinete":
	    			dao.gerenciarDao.GerenciarGabineteJDBCDAO daoGabinete = new dao.gerenciarDao.GerenciarGabineteJDBCDAO();
	    			Gabinete gab = new Gabinete();
	    			if (fxml.<AdicionarGabineteController>getController().getChecked()) gab.setMarca("Desconhecido");
	    			else gab.setMarca(fxml.<AdicionarGabineteController>getController().getMarca());
	    			gab.setTamanho(fxml.<AdicionarGabineteController>getController().getTamanho());
	    			gab.setDoador(tfDoador.getText());
	    			
	    			if (gab.getMarca().isEmpty()) erro += "Informe a marca.\n";
	    			if (gab.getTamanho().equals("null")) erro += "Informe o tamanho.";
	    			
	    			if (!erro.isEmpty()) {
	    				Main.infoDialog("Erro", erro);
	    			} else {
	    				if (Main.confirmDialog("Confirmação", "Tem certeza que deseja adicionar\n\nGabinete(s) (" + tfQuantidade.getText() + "):\n- "
	    				+ gab.getMarca() + " " + gab.getTamanho() + "\n\nDoador: " + tfDoador.getText())) {
	    					resp = daoGabinete.adicionar(gab, Integer.parseInt(tfQuantidade.getText()));
		    			
	    					if (resp != 0) Main.infoDialog("Aviso", resp + " Gabinete(s) foi(ram) adicionado(s).");
	    					else Main.infoDialog("Erro", "Ocorreu um erro ao adicionar " + tfQuantidade.getText() + " gabinete(s).\nVerifique os valores e tente novamente.");
	    					fxml.<AdicionarGabineteController>getController().limparCampos();
	    					limparCampos(); visuController.setTabela("Gabinete.fxml"); resp = 0;
	    				}
	    			}
	    		break;
	    		case "Impressora":
	    			dao.gerenciarDao.GerenciarImpressoraJDBCDAO daoImpressora = new dao.gerenciarDao.GerenciarImpressoraJDBCDAO();
	    			Impressora imp = new Impressora();
	    			imp.setMarca(fxml.<AdicionarImpressoraController>getController().getMarca());
	    			if (fxml.<AdicionarImpressoraController>getController().getChecked()) imp.setModelo("Desconhecido");
	    			else imp.setModelo(fxml.<AdicionarImpressoraController>getController().getModelo());
	    			imp.setDoador(tfDoador.getText());
	    			
	    			if (imp.getMarca().isEmpty()) erro += "Informe a marca.\n";
	    			if (imp.getModelo().isEmpty()) erro += "Informe o modelo";
	    			
	    			if (!erro.isEmpty()) {
	    				Main.infoDialog("Erro", erro);
	    			} else {
	    				if (Main.confirmDialog("Confirmação", "Tem certeza que deseja adicionar\n\nImpressora(s) (" + tfQuantidade.getText() + "):\n- "
	    				+ imp.getMarca() + " " + imp.getModelo() + "\n\nDoador: " + tfDoador.getText())) {
	    					resp = daoImpressora.adicionar(imp, Integer.parseInt(tfQuantidade.getText()));
	    				
	    					if (resp != 0) Main.infoDialog("Aviso", resp + " Impressora(s) foi(ram) adicionada(s).");
	    					else Main.infoDialog("Erro", "Ocorreu um erro ao adicionar " + tfQuantidade.getText() + " impressoras(s).\nVerifique os valores e tente novamente.");
	    					fxml.<AdicionarImpressoraController>getController().limparCampos();
	    					limparCampos(); visuController.setTabela("Impressora.fxml"); resp = 0;
	    				}
	    			}
	    		break;
	    		case "Leitor de Disco":
	    			dao.gerenciarDao.GerenciarLeitorJDBCDAO daoLeitor = new dao.gerenciarDao.GerenciarLeitorJDBCDAO();
	    			LeitorDisco leitor = new LeitorDisco();
	    			leitor.setMarca(fxml.<AdicionarLeitorController>getController().getMarca());
	    			if (fxml.<AdicionarLeitorController>getController().getChecked()) leitor.setModelo("Desconhecido");
	    			else leitor.setModelo(fxml.<AdicionarLeitorController>getController().getModelo());
	    			leitor.setDoador(tfDoador.getText());
	    			
	    			if (leitor.getMarca().isEmpty()) erro += "Informe a marca.\n";
	    			if (leitor.getModelo().isEmpty()) erro += "Informe o modelo.";
	    			
	    			if (!erro.isEmpty()) {
	    				Main.infoDialog("Erro", erro);
	    			} else {
	    				if (Main.confirmDialog("Confirmação", "Tem certeza que deseja adicionar\n\nLeitor(es) de Disco (" + tfQuantidade.getText() + "):\n- "
	    				+ leitor.getMarca() + " " + leitor.getModelo() + "\n\nDoador: " + tfDoador.getText())) {
	    					resp = daoLeitor.adicionar(leitor, Integer.parseInt(tfQuantidade.getText()));
		    			
	    					if (resp != 0) Main.infoDialog("Aviso", resp + " Leitor(es) de Disco foi(ram) adicionado(s).");
	    					else Main.infoDialog("Erro", "Ocorreu um erro ao adicionar " + tfQuantidade.getText() + " leitor(es) de disco.\nVerifique os valores e tente novamente.");
	    					fxml.<AdicionarLeitorController>getController().limparCampos();
	    					limparCampos(); visuController.setTabela("Leitor.fxml"); resp = 0;
	    				}
	    			}
	    		break;
	    		case "Memória":
	    			dao.gerenciarDao.GerenciarMemoriaJDBCDAO daoMemoria = new dao.gerenciarDao.GerenciarMemoriaJDBCDAO();
	    			Memoria mem = new Memoria();
	    			mem.setMarca(fxml.<AdicionarMemoriaController>getController().getMarca());
	    			if (fxml.<AdicionarMemoriaController>getController().getChecked()) mem.setModelo("Desconhecido");
	    			else mem.setModelo(fxml.<AdicionarMemoriaController>getController().getModelo());
	    			mem.setTipo(fxml.<AdicionarMemoriaController>getController().getTipo());
	    			mem.setMemoria(fxml.<AdicionarMemoriaController>getController().getMemoria());
	    			mem.setDoador(tfDoador.getText());
	    			
	    			if (mem.getMarca().isEmpty()) erro += "Informe a marca.\n";
	    			if (mem.getModelo().isEmpty()) erro += "Informe o modelo.\n";
	    			if (mem.getTipo().isEmpty()) erro += "Informe o tipo.\n";
	    			if (mem.getMemoria() < 0.0) erro += "Informe a capacidade.";
	    			
	    			if (!erro.isEmpty()) {
	    				Main.infoDialog("Erro", erro);
	    			} else {
	    				if (Main.confirmDialog("Confirmação", "Tem certeza que deseja adicionar\n\nMemória(s) (" + tfQuantidade.getText() + "):\n- "
	    				+ mem.getMarca() + " " + mem.getModelo() + " " + mem.getTipo() + " " + mem.getMemoria() + "Gb\n\nDoador: " + tfDoador.getText())) {
	    					resp = daoMemoria.adicionar(mem, Integer.parseInt(tfQuantidade.getText()));
		    			
	    					if (resp != 0) Main.infoDialog("Aviso", resp + " Memória(s) foi(ram) adicionada(s).");
	    					else Main.infoDialog("Erro", "Ocorreu um erro ao adicionar " + tfQuantidade.getText() + " memória(s).\nVerifique os valores e tente novamente.");
	    					fxml.<AdicionarMemoriaController>getController().limparCampos();
	    					limparCampos(); visuController.setTabela("Memoria.fxml"); resp = 0;
	    				}
	    			}
	    		break;
	    		case "Monitor":
	    			dao.gerenciarDao.GerenciarMonitorJDBCDAO daoMonitor = new dao.gerenciarDao.GerenciarMonitorJDBCDAO();
	    			Monitor monitor = new Monitor();
	    			monitor.setMarca(fxml.<AdicionarMonitorController>getController().getMarca());
	    			if (fxml.<AdicionarMonitorController>getController().getChecked()) monitor.setModelo("Desconhecido");
	    			else monitor.setModelo(fxml.<AdicionarMonitorController>getController().getModelo());
	    			monitor.setDoador(tfDoador.getText());
	    			
	    			if (monitor.getMarca().isEmpty()) erro += "Informe a marca.\n";
	    			if (monitor.getModelo().isEmpty()) erro += "Informe o modelo.";
	    			
	    			if (!erro.isEmpty()) {
	    				Main.infoDialog("Erro", erro);
	    			} else {
	    				if (Main.confirmDialog("Confirmação", "Tem certeza que deseja adicionar\n\nMonitor(es) (" + tfQuantidade.getText() + "):\n- "
	    				+ monitor.getMarca() + " " + monitor.getModelo() + "\n\nDoador: " + tfDoador.getText())) {
	    					resp = daoMonitor.adicionar(monitor, Integer.parseInt(tfQuantidade.getText()));
		    			
	    					if (resp != 0) Main.infoDialog("Aviso", resp + " Monitor(es) foi(ram) adicionado(s).");
	    					else Main.infoDialog("Erro", "Ocorreu um erro ao adicionar " + tfQuantidade.getText() + " monitor(es).\nVerifique os valores e tente novamente.");
	    					fxml.<AdicionarMonitorController>getController().limparCampos();
	    					limparCampos(); visuController.setTabela("Monitor.fxml"); resp = 0;
	    				}
	    			}
	    		break;
	    		case "Mouse":
	    			dao.gerenciarDao.GerenciarMouseJDBCDAO daoMouse = new dao.gerenciarDao.GerenciarMouseJDBCDAO();
	    			Mouse mouse = new Mouse();
	    			mouse.setMarca(fxml.<AdicionarMouseController>getController().getMarca());
	    			if (fxml.<AdicionarMouseController>getController().getChecked()) mouse.setModelo("Desconhecido");
	    			else mouse.setModelo(fxml.<AdicionarMouseController>getController().getModelo());
	    			mouse.setDoador(tfDoador.getText());
	    			
	    			if (mouse.getMarca().isEmpty()) erro += "Informe a marca.\n";
	    			if (mouse.getModelo().isEmpty()) erro += "Informe o modelo.";
	    			
	    			if (!erro.isEmpty()) {
	    				Main.infoDialog("Erro", erro);
	    			} else {
	    				if (Main.confirmDialog("Confirmação", "Tem certeza que deseja adicionar\n\nMouse(s) (" + tfQuantidade.getText() + "):\n- "
	    				+ mouse.getMarca() + " " + mouse.getModelo() + "\n\nDoador: " + tfDoador.getText())) {
	    					resp = daoMouse.adicionar(mouse, Integer.parseInt(tfQuantidade.getText()));
		    			
	    					if (resp != 0) Main.infoDialog("Aviso", resp + " Mouse(s) foi(ram) adicionado(s).");
	    					else Main.infoDialog("Erro", "Ocorreu um erro ao adicionar " + tfQuantidade.getText() + " mouse(s).\nVerifique os valores e tente novamente.");
	    					fxml.<AdicionarMouseController>getController().limparCampos();
	    					limparCampos(); visuController.setTabela("Mouse.fxml"); resp = 0;
	    				}
	    			}
	    		break;
	    		case "Notebook":
	    			dao.gerenciarDao.GerenciarNotebookJDBCDAO daoNotebook = new dao.gerenciarDao.GerenciarNotebookJDBCDAO();
	    			Notebook note = new Notebook();
	    			note.setMarca(fxml.<AdicionarNotebookController>getController().getMarca());
	    			if (fxml.<AdicionarNotebookController>getController().getChecked()) note.setModelo("Desconhecido");
	    			else note.setModelo(fxml.<AdicionarNotebookController>getController().getModelo());
	    			note.setDoador(tfDoador.getText());
	    			
	    			if (note.getMarca().isEmpty()) erro += "Informe a marca.\n";
	    			if (note.getModelo().isEmpty()) erro += "Informe o modelo.";
	    			
	    			if (!erro.isEmpty()) {
	    				Main.infoDialog("Erro", erro);
	    			} else {
	    				if (Main.confirmDialog("Confirmação", "Tem certeza que deseja adicionar\n\nNotebook(s) (" + tfQuantidade.getText() + "):\n- "
	    				+ note.getMarca() + " " + note.getModelo() + "\n\nDoador: " + tfDoador.getText())) {
	    					resp = daoNotebook.adicionar(note, Integer.parseInt(tfQuantidade.getText()));
		    			
	    					if (resp != 0) Main.infoDialog("Aviso", resp + " Notebook(s) foi(ram) adicionado(s).");
	    					else Main.infoDialog("Erro", "Ocorreu um erro ao adicionar " + tfQuantidade.getText() + " notebook(s).\nVerifique os valores e tente novamente.");
	    					fxml.<AdicionarNotebookController>getController().limparCampos();
	    					limparCampos(); visuController.setTabela("Notebook.fxml"); resp = 0;
	    				}
	    			}
	    		break;
	    		case "Outros":
	    			dao.gerenciarDao.GerenciarOutrosJDBCDAO daoOutros = new dao.gerenciarDao.GerenciarOutrosJDBCDAO();
	    			Outros outros = new Outros();
	    			outros.setDescricao(fxml.<AdicionarOutrosController>getController().getArea());
	    			outros.setDoador(tfDoador.getText());
	    			
	    			if (outros.getDescricao().isEmpty()) erro += "Informe a descrição do item.";
	    			
	    			if (!erro.isEmpty()) {
	    				Main.infoDialog("Erro", erro);
	    			} else {
	    				if (Main.confirmDialog("Confirmação", "Tem certeza que deseja adicionar\n\n\"Outros\" (" + tfQuantidade.getText() + "):\n- "
	    				+ outros.getDescricao() + "\n\nDoador: " + outros.getDoador())) {
	    					resp = daoOutros.adicionar(outros, Integer.parseInt(tfQuantidade.getText()));
		    			
	    					if (resp != 0) Main.infoDialog("Aviso", resp + " \"Outros\" foi(ram) adicionado(s)");
	    					else Main.infoDialog("Erro", "Ocorreu um erro ao adicionar " + tfQuantidade.getText() + " \"Outros\".\nVerifique os valores e tente novamente.");
	    					fxml.<AdicionarOutrosController>getController().limparCampos();
	    					limparCampos(); visuController.setTabela("Outros.fxml"); resp = 0;
	    				}
	    			}
	    		break;
	    		case "Placa Mãe":
	    			dao.gerenciarDao.GerenciarPlacaMaeJDBCDAO daoMae = new dao.gerenciarDao.GerenciarPlacaMaeJDBCDAO();
	    			PlacaMae mae = new PlacaMae();
	    			mae.setMarca(fxml.<AdicionarPlacaMaeController>getController().getMarca());
	    			if (fxml.<AdicionarPlacaMaeController>getController().getChecked()) mae.setModelo("Desconhecido");
	    			else mae.setModelo(fxml.<AdicionarPlacaMaeController>getController().getModelo());
	    			mae.setSocket(fxml.<AdicionarPlacaMaeController>getController().getSocket());
	    			mae.setRam(fxml.<AdicionarPlacaMaeController>getController().getTipoRam());
	    			mae.setDoador(tfDoador.getText());
	    			
	    			if (mae.getMarca().isEmpty()) erro += "Informe a marca.\n";
	    			if (mae.getModelo().isEmpty()) erro += "Informe o modelo.\n";
	    			if (mae.getSocket().isEmpty()) erro += "Informe o tipo de socket.\n";
	    			if (mae.getRam().isEmpty()) erro += "Informe o tipo de RAM.";
	    			
	    			if (!erro.isEmpty()) {
	    				Main.infoDialog("Erro", erro);
	    			} else {
	    				if (Main.confirmDialog("Confirmação", "Tem certeza que deseja adicionar\n\nPlaca(s) Mãe (" + tfQuantidade.getText() + "):\n- "
	    				+ mae.getMarca() + " " + mae.getModelo() + " socket " + mae.getSocket() + " RAM " + mae.getRam() + "\n\nDoador: " + tfDoador.getText())) {
	    					resp = daoMae.adicionar(mae, Integer.parseInt(tfQuantidade.getText()));
		    			
	    					if (resp != 0) Main.infoDialog("Aviso", resp + " Placa(s) Mãe foi(ram) adicionada(s).");
	    					else Main.infoDialog("Erro", "Ocorreu um erro ao adicionar " + tfQuantidade.getText() + " Placa(s) Mãe.\nVerifique os valores e tente novamente.");
	    					fxml.<AdicionarPlacaMaeController>getController().limparCampos();
	    					limparCampos(); visuController.setTabela("PlacaMae.fxml"); resp = 0;
	    				}
	    			}
	    		break;
	    		case "Placa de Rede":
	    			dao.gerenciarDao.GerenciarPlacaRedeJDBCDAO daoRede = new dao.gerenciarDao.GerenciarPlacaRedeJDBCDAO();
	    			PlacaRede rede = new PlacaRede();
	    			rede.setMarca(fxml.<AdicionarPlacaRedeController>getController().getMarca());
	    			if (fxml.<AdicionarPlacaRedeController>getController().getChecked()) rede.setModelo("Desconhecido");
	    			else rede.setModelo(fxml.<AdicionarPlacaRedeController>getController().getModelo());
	    			rede.setWifi(fxml.<AdicionarPlacaRedeController>getController().getWifi());
	    			rede.setDoador(tfDoador.getText());
	    			
	    			if (rede.getMarca().isEmpty()) erro += "Informe a marca.\n";
	    			if (rede.getModelo().isEmpty()) erro += "Informe o modelo.\n";
	    			if (rede.getWifi() == null) erro += "Informe se possui Wi-fi";
	    			
	    			if (!erro.isEmpty()) {
	    				Main.infoDialog("Erro", erro);
	    			} else {
	    				if (Main.confirmDialog("Confirmação", "Tem certeza que deseja adicioanr\n\nPlaca(s) de Rede (" + tfQuantidade.getText() + "):\n- "
	    				+ rede.getMarca() + " " + rede.getModelo() + " Wifi " + rede.getWifi() + "\n\nDoador: " + tfDoador.getText())) {
	    					resp = daoRede.adicionar(rede, Integer.parseInt(tfQuantidade.getText()));
		    			
	    					if (resp != 0) Main.infoDialog("Aviso", resp + " Placa(s) de Rede foi(ram) adicionada(s).");
	    					else Main.infoDialog("Erro", "Ocorreu um erro ao adicionar " + tfQuantidade.getText() + " Placa(s) de Rede.\nVerifique os valores e tente novamente.");
	    					fxml.<AdicionarPlacaRedeController>getController().limparCampos();
	    					limparCampos(); visuController.setTabela("PlacaRede.fxml"); resp = 0;
	    				}
	    			}
	    		break;
	    		case "Placa de Vídeo":
	    			dao.gerenciarDao.GerenciarPlacaVideoJDBCDAO daoVideo = new dao.gerenciarDao.GerenciarPlacaVideoJDBCDAO();
	    			PlacaVideo video = new PlacaVideo();
	    			video.setMarca(fxml.<AdicionarPlacaVideoController>getController().getMarca());
	    			if (fxml.<AdicionarPlacaVideoController>getController().getChecked()) video.setModelo("Desconhecido");
	    			else video.setModelo(fxml.<AdicionarPlacaVideoController>getController().getModelo());
	    			video.setDoador(tfDoador.getText());
	    			
	    			if (video.getMarca().isEmpty()) erro += "Informe a marca.\n";
	    			if (video.getModelo().isEmpty()) erro += "Informe o modelo.";
	    			
	    			if (!erro.isEmpty()) {
	    				Main.infoDialog("Erro", erro);
	    			} else {
	    				if (Main.confirmDialog("Confirmação", "Tem certeza que deseja adicionar\n\nPlaca(s) de Vídeo (" + tfQuantidade.getText() + "):\n- "
	    				+ video.getMarca() + " " + video.getModelo() + "\n\nDoador: " + tfDoador.getText())) { 
	    					resp = daoVideo.adicionar(video, Integer.parseInt(tfQuantidade.getText()));
		    			
	    					if (resp != 0) Main.infoDialog("Aviso", resp + " Placa(s) de Vídeo foi(ram) adicionada(s).");
	    					else Main.infoDialog("Erro", "Ocorreu um erro ao adicionar " + tfQuantidade.getText() + " Placa(s) de Vídeo.\nVerifique os valores e tente novamente.");
	    					fxml.<AdicionarPlacaVideoController>getController().limparCampos();
	    					limparCampos(); visuController.setTabela("PlacaVideo.fxml"); resp = 0;
	    				}
	    			}
	    		break;
	    		case "Processador":
	    			dao.gerenciarDao.GerenciarProcessadorJDBCDAO daoProcessador = new dao.gerenciarDao.GerenciarProcessadorJDBCDAO();
	    			Processador processador = new Processador();
	    			processador.setMarca(fxml.<AdicionarProcessadorController>getController().getMarca());
	    			if (fxml.<AdicionarProcessadorController>getController().getChecked()) processador.setModelo("Desconhecido");
	    			else processador.setModelo(fxml.<AdicionarProcessadorController>getController().getModelo());
	    			processador.setSocket(fxml.<AdicionarProcessadorController>getController().getSocket());
	    			processador.setDoador(tfDoador.getText());
	    			
	    			if (processador.getMarca().isEmpty()) erro += "Informe a marca.\n";
	    			if (processador.getModelo().isEmpty()) erro += "Informe o modelo.\n";
	    			if (processador.getSocket().isEmpty()) erro += "Informe o tipo de socket.";
	    			
	    			if (!erro.isEmpty()) {
	    				Main.infoDialog("Erro", erro);
	    			} else {
	    				if (Main.confirmDialog("Confirmação", "Tem certeza que deseja adicionar\n\nProcessador(es) (" + tfQuantidade.getText() + "):\n- "
	    				+ processador.getMarca() + " " + processador.getModelo() + " socket " + processador.getSocket() + "\n\nDoador: " + tfDoador.getText())) {
	    					resp = daoProcessador.adicionar(processador, Integer.parseInt(tfQuantidade.getText()));

	    					if (resp != 0) Main.infoDialog("Aviso", resp + " Processador(es) foi(ram) adicionada(s).");
	    					else Main.infoDialog("Erro", "Ocorreu um erro ao adicionar " + tfQuantidade.getText() + " Processador(es).\nVerifique os valores e tente novamente.");
	    					fxml.<AdicionarProcessadorController>getController().limparCampos();
	    					limparCampos(); visuController.setTabela("Processador.fxml"); resp = 0;
	    				}
	    			}
	    		break;
	    		case "Teclado":
	    			dao.gerenciarDao.GerenciarTecladoJDBCDAO daoTeclado = new dao.gerenciarDao.GerenciarTecladoJDBCDAO();
	    			Teclado teclado = new Teclado();
	    			teclado.setMarca(fxml.<AdicionarTecladoController>getController().getMarca());
	    			if (fxml.<AdicionarTecladoController>getController().getChecked()) teclado.setModelo("Desconhecido");
	    			else teclado.setModelo(fxml.<AdicionarTecladoController>getController().getModelo());
	    			teclado.setDoador(tfDoador.getText());
	    			
	    			if (teclado.getMarca().isEmpty()) erro += "Informe a marca.\n";
	    			if (teclado.getModelo().isEmpty()) erro += "Informe o modelo.";
	    			
	    			if (!erro.isEmpty()) {
	    				Main.infoDialog("Erro", erro);
	    			} else {
	    				if (Main.confirmDialog("Confirmação", "Tem certeza que deseja adicionar\n\nTeclado(s) (" + tfQuantidade.getText() + "):\n- "
	    				+ teclado.getMarca() + " " + teclado.getModelo() + "\n\nDoador: " + tfDoador.getText())) {
	    					resp = daoTeclado.adicionar(teclado, Integer.parseInt(tfQuantidade.getText()));
		    			
	    					if (resp != 0) Main.infoDialog("Aviso", resp + " Teclado(s) foi(ram) adicionada(s).");
	    					else Main.infoDialog("Erro", "Ocorreu um erro ao adicionar " + tfQuantidade.getText() + " Teclado(s).\nVerifique os valores e tente novamente.");
	    					fxml.<AdicionarTecladoController>getController().limparCampos();
	    					limparCampos(); visuController.setTabela("Teclado.fxml"); resp = 0;
	    				}
	    			}
	    		break;
	    		default: return;
	    	}
    	}
    }

    /**
	 * Método para carregar a tela de adicionarItem correspondente no centro de Adicionar.
     * @param tela recebe o caminho da tela a ser carregada.
     * @throws IOException
     */
    public void setCentro(String tela) throws IOException {
    	String caminho = "/view/adicionarView/adicionar" + tela;
    	
    	fxml = new FXMLLoader(getClass().getResource(caminho));
		gPaneEscolhido = fxml.load();
		bPaneEscolhido.setCenter(gPaneEscolhido);
		
		if (tela.equals("Cabo.fxml")) tfDoador.setVisible(false);
		else tfDoador.setVisible(true);
    }
    
    /**
     * Recebe o controlador do pai para comunicação.
     * @param pai controlador da tela pai na hierarquia.
     */
    public void setPaiController(VisualizarController pai) {
    	this.visuController = pai;
    }
    
    /**
     * Método para limpar os campos da tela Adicionar.
     */
    public void limparCampos() {
    	tfDoador.clear();
    	tfQuantidade.clear();
    }
}
