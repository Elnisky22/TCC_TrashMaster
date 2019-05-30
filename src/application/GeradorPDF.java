package application;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.filechooser.FileSystemView;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Classe Geradora de Arquivos PDF
 * @author Leonardo Elnisky
 *
 */
public class GeradorPDF {

	/**
	 * Método estático para Gerar um Arquivo PDF correspondente à quem o chamou.
	 * @param tipo recebe o tipo de Arquivo a ser gerado.
	 * @param titleS recebe o título a ser inserido no Arquivo.
	 * @param bodyS recebe o texto a ser inserido no corpo do Arquivo.
	 * @param table recebe a tabela de objetos a ser inserido no Arquivo.
	 * @return true se o arquivo foi gerado, false se nada foi gerado.
	 * @throws Exception
	 */
	public static Boolean gerarPDF(String tipo, String titleS, String bodyS, PdfPTable table) throws Exception {
		Boolean deubom = false;
		// Data
		DateFormat dtFormat;
		Date data = new Date();
		dtFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		// Documento
		Document doc = new Document();
		String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath(); // Caminho até os Documentos
		
		// Fontes
		Font titleFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD);
		Font normalFont = new Font(Font.FontFamily.HELVETICA, 12);
		
		// Header
		PdfPTable hdTable = new PdfPTable(2);
		hdTable.setWidths(new float[] {1, 3});
		
		Image img = Image.getInstance(GeradorPDF.class.getResource("/images/logo.png"));
		hdTable.addCell(img);
		hdTable.addCell("Setor de Ciências Exatas e de Tecnologia - SEET\n"
				+ "Departamento de Ciência da Computação - DECOMP\n"
				+ "Pró-Reitoria de Extensão e Cultura - PROEC\n"
				+ "Programa de Extensão E-Lixo: Lixo Eletrônico da UNICENTRO");
		
		// Título
		Paragraph title = new Paragraph();
		title.setAlignment(Element.ALIGN_CENTER);
		title.setFont(titleFont);
		title.add(titleS); // A String tem que ser adiciona depois para os Sets terem efeito
		
		// Corpo
		Paragraph body = new Paragraph();
		body.setAlignment(Element.ALIGN_CENTER);
		body.setFont(normalFont);
		body.add(bodyS);
		
		File f;
		switch (tipo) {
			case "Atividade":
				path += "\\TrashMaster\\Atividades"; // Caminho até as Atividades
				f = new File(path); f.mkdirs();
				path += "\\Relatório Atividades - " + dtFormat.format(data) + ".pdf"; // Caminho até o arquivo .pdf
				
				try {
					PdfWriter.getInstance(doc, new FileOutputStream(path));
					doc.open();
					
					doc.addTitle(titleS);
					doc.add(hdTable);
					doc.add(new Paragraph(" "));
					doc.add(title);
					doc.add(new Paragraph(" "));
					doc.add(body);
					doc.add(new Paragraph(" "));
					doc.add(table);
					
					doc.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
				deubom = true;
			break;
			case "Doação":
				path += "\\TrashMaster\\Doações"; // Caminho até Doações
				f = new File(path); f.mkdirs();
				path += "\\Relatório Doações - " + dtFormat.format(data) + ".pdf"; // Caminho até o arquivo .pdf
				
				try {
					PdfWriter.getInstance(doc, new FileOutputStream(path));
					doc.open();
					
					doc.addTitle(titleS);
					doc.add(hdTable);
					doc.add(new Paragraph(" "));
					doc.add(title);
					doc.add(new Paragraph(" "));
					doc.add(body);
					doc.add(new Paragraph(" "));
					doc.add(table);
					
					doc.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				deubom = true;
			break;
			case "Descarte":
				path += "\\TrashMaster\\Descartes"; // Caminho até Descartes
				f = new File(path); f.mkdirs();
				path += "\\Relatório Descartes - " + dtFormat.format(data) + ".pdf"; // Caminho até o arquivo .pdf
				
				try {
					PdfWriter.getInstance(doc, new FileOutputStream(path));
					doc.open();
					
					doc.addTitle(titleS);
					doc.add(hdTable);
					doc.add(new Paragraph(" "));
					doc.add(title);
					doc.add(new Paragraph(" "));
					doc.add(body);
					doc.add(new Paragraph(" "));
					doc.add(table);
					
					doc.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				deubom = true;
			break;
			default: return false;
		}
		return deubom;
	}
	
}
