package dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.filechooser.FileSystemView;

/**
 * Classe para Gerenciar os dados de conex�o com o banco.
 * @author Leonardo Elnisky
 */
public class ConexaoGetPropertyValues {
	private static String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\TrashMaster\\config.properties";
	private static InputStream inputStream;
	
	static String endereco;
	static String usuario;
	static String senha;
	
	/**
	 * M�todo para setar as vari�veis da conex�o com o banco.
	 * @throws IOException
	 */
	public static void setVars() throws IOException {
		setEndereco(); setUsuario(); setSenha();
	}
	
	/**
	 * M�todo para coletar do arquivo o endere�o do banco e armazenar na vari�vel endereco.
	 * @throws IOException
	 */
	public static void setEndereco() throws IOException {
		try {
			Properties prop = new Properties();
			inputStream = new FileInputStream(path);
			
			if (inputStream != null) prop.load(inputStream);
			else throw new FileNotFoundException("Arquivo " + path + " n�o encontrado.");
			
			endereco = prop.getProperty("endereco");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			inputStream.close();
		}
	}
	
	/**
	 * M�todo para coletar do arquivo o usu�rio do banco e armazenar na vari�vel usuario.
	 * @throws IOException
	 */
	public static void setUsuario() throws IOException {
		try {
			Properties prop = new Properties();
			inputStream = new FileInputStream(path);
			
			if (inputStream != null) prop.load(inputStream);
			else throw new FileNotFoundException("Arquivo " + path + " n�o encontrado.");
			
			usuario = prop.getProperty("usuario");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			inputStream.close();
		}
	}
	
	/**
	 * M�todo para coletar do arquivo a senha do banco e armazenar na vari�vel senha.
	 * @throws IOException
	 */
	public static void setSenha() throws IOException {
		try {
			Properties prop = new Properties();
			inputStream = new FileInputStream(path);
			
			if (inputStream != null) prop.load(inputStream);
			else throw new FileNotFoundException("Arquivo " + path + " n�o encontrado.");
			
			senha = prop.getProperty("senha");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			inputStream.close();
		}
	}
	
	/**
	 * M�todo para pegar o endere�o do banco.
	 * @return o endere�o.
	 */
	public static String getEndereco() {
		return endereco;
	}
	
	/**
	 * M�todo para pegar o usu�rio do banco.
	 * @return o usu�rio.
	 */
	public static String getUsuario() {
		return usuario;
	}
	
	/**
	 * M�todo para pegar a senha do banco.
	 * @return a senha.
	 */
	public static String getSenha() {
		return senha;
	}
}
