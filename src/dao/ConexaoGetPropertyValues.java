package dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.filechooser.FileSystemView;

/**
 * Classe para Gerenciar os dados de conexão com o banco.
 * @author Leonardo Elnisky
 */
public class ConexaoGetPropertyValues {
	private static String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\TrashMaster\\config.properties";
	private static InputStream inputStream;
	
	static String endereco;
	static String usuario;
	static String senha;
	
	/**
	 * Método para setar as variáveis da conexão com o banco.
	 * @throws IOException
	 */
	public static void setVars() throws IOException {
		setEndereco(); setUsuario(); setSenha();
	}
	
	/**
	 * Método para coletar do arquivo o endereço do banco e armazenar na variável endereco.
	 * @throws IOException
	 */
	public static void setEndereco() throws IOException {
		try {
			Properties prop = new Properties();
			inputStream = new FileInputStream(path);
			
			if (inputStream != null) prop.load(inputStream);
			else throw new FileNotFoundException("Arquivo " + path + " não encontrado.");
			
			endereco = prop.getProperty("endereco");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			inputStream.close();
		}
	}
	
	/**
	 * Método para coletar do arquivo o usuário do banco e armazenar na variável usuario.
	 * @throws IOException
	 */
	public static void setUsuario() throws IOException {
		try {
			Properties prop = new Properties();
			inputStream = new FileInputStream(path);
			
			if (inputStream != null) prop.load(inputStream);
			else throw new FileNotFoundException("Arquivo " + path + " não encontrado.");
			
			usuario = prop.getProperty("usuario");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			inputStream.close();
		}
	}
	
	/**
	 * Método para coletar do arquivo a senha do banco e armazenar na variável senha.
	 * @throws IOException
	 */
	public static void setSenha() throws IOException {
		try {
			Properties prop = new Properties();
			inputStream = new FileInputStream(path);
			
			if (inputStream != null) prop.load(inputStream);
			else throw new FileNotFoundException("Arquivo " + path + " não encontrado.");
			
			senha = prop.getProperty("senha");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			inputStream.close();
		}
	}
	
	/**
	 * Método para pegar o endereço do banco.
	 * @return o endereço.
	 */
	public static String getEndereco() {
		return endereco;
	}
	
	/**
	 * Método para pegar o usuário do banco.
	 * @return o usuário.
	 */
	public static String getUsuario() {
		return usuario;
	}
	
	/**
	 * Método para pegar a senha do banco.
	 * @return a senha.
	 */
	public static String getSenha() {
		return senha;
	}
}
