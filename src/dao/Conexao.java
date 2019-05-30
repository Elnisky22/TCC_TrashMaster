package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe para gerenciar a conex�o com o banco de dados.
 * @author Leonardo Elnisky.
 *
 */
public class Conexao {
	
	/**
	 * M�todo est�tico para carregar a conex�o com o banco.
	 * @return uma conex�o com o banco pronta para uso.
	 * @throws IOException
	 */
	public static Connection getConexao() throws IOException {
		try{
			return DriverManager.getConnection(ConexaoGetPropertyValues.getEndereco(), ConexaoGetPropertyValues.getUsuario(), ConexaoGetPropertyValues.getSenha());
		} catch (SQLException e){		
			throw new RuntimeException(e);
		}
	}
}