package dao.gerenciarDao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Descarte;

/**
 * Classe para gerenciar a comunicação do modelo Descarte com o banco de dados.
 * @author Leonardo Elnisky.
 */
public class GerenciarDescartesJDBCDAO implements GerenciarDescartesDAO {
	private Connection conexao;
	private PreparedStatement stmt;
	
	/**
	 * Método para abrir a conexão com o banco de dados.
	 * @throws IOException 
	 */
	@Override
	public void open() throws SQLException, IOException {
		conexao = dao.Conexao.getConexao();
	}

	/**
	 * Método para fechar a conexão com o banco de dados.
	 */
	@Override
	public void close() throws SQLException {
		conexao.close();
	}
	
	/**
	 * Método para adicionar um novo descarte ao banco de dados.
	 * @param itens string contendo os itens à serem inseridos.
	 * @throws IOException 
	 */
	@Override
	public void adicionar(String itens) throws SQLException, IOException {
		open();
		String sql = "INSERT INTO Descarte (descarteId, dia, itens) VALUES (DEFAULT, CURRENT_DATE, ?);";
		
		stmt = conexao.prepareStatement(sql);
		stmt.setString(1, itens);
		stmt.executeUpdate();
		
		close();
	}
	
	/**
	 * Método para carregar todos os descartes do banco de dados.
	 * @return listDesc lista contendo todos os descartes armazenados no banco de dados.
	 * @throws IOException 
	 */
	@Override
	public List<Descarte> load() throws SQLException, IOException {
		open();
		String sql = "SELECT TO_CHAR(dia, 'dd/mm/yyyy') dia, itens FROM Descarte ORDER BY descarteId DESC;";
		
		try (PreparedStatement stmt = conexao.prepareStatement(sql)){
			try (ResultSet rs = stmt.executeQuery()){
				List<Descarte> listDesc = new ArrayList<>();
				while (rs.next()) {
					Descarte desc = new Descarte();
					desc.setData(rs.getString("dia"));
					desc.setItens(rs.getString("itens"));
					listDesc.add(desc);
				}
				close();
				return listDesc;
			}
		}
	}
	
	/**
	 * Método para limpar toda a tabela de Descartes no banco.
	 * @throws SQLException
	 * @throws IOException
	 */
	@Override
	public void limpar() throws SQLException, IOException {
		open();
		String sql = "DELETE FROM Descarte;";
		
		stmt = conexao.prepareStatement(sql);
		stmt.executeUpdate();
		
		close();
	}
}
