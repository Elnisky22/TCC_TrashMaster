package dao.gerenciarDao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import model.Outros;

/**
 * Classe para gerenciar a comunicação relacionada ao modelo Outros com o banco de dados.
 * @author Leonardo Elnisky
 */
public class GerenciarOutrosJDBCDAO implements GerenciarOutrosDAO {
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
	 * Método para adicionar um novo outros ao banco de dados.
	 * @param outros modelo de outros com os dados a serem inseridos.
	 * @param qtd quantidade de outros iguais a serem adicionados.
	 * @return x a quantia de linhas alteradas no banco.
	 * @throws IOException 
	 */
	@Override
	public int adicionar(Outros outros, int qtd) throws SQLException, IOException {
		int x = 0;
		String sql = "INSERT INTO Outros (outrosId, descricao, doador) VALUES (DEFAULT, ?, ?)";
		
		open();
		stmt = conexao.prepareStatement(sql);
		stmt.setString(1, outros.getDescricao());
		stmt.setString(2, outros.getDoador());
		
		int i=0;
		while (i < qtd) {
			x += stmt.executeUpdate();
			i++;
		}
		close();
		return x;
	}

	/**
	 * Método para carregar todos os outros do banco de dados.
	 * @return listOutros lista com todos os outros contidos no banco de dados.
	 * @throws IOException 
	 */
	@Override
	public List<Outros> load() throws SQLException, IOException {
		open();
		String sql = "SELECT * FROM Outros ORDER BY 1";
		
		try (PreparedStatement stmt = conexao.prepareStatement(sql)){
			try (ResultSet rs = stmt.executeQuery()){
				List<Outros> listOutros = new ArrayList<>();
				while (rs.next()) {
					Outros outro = new Outros();
					outro.setOutrosId(rs.getInt("outrosId"));
					outro.setDescricao(rs.getString("descricao"));
					outro.setDoador(rs.getString("doador"));
					listOutros.add(outro);
				}
				close();
				return listOutros;
			}
		}
	}

	/**
	 * Método para remover (doar) uma lista de outros do banco de dados.
	 * @param selecionados lista contendo os outros a serem removidos.
	 * @return x a quantia de linhas alteradas no banco.
	 * @throws IOException 
	 */
	@Override
	public int remover(ObservableList<Outros> selecionados) throws SQLException, IOException {
		int x = 0;
		String sql = "DELETE FROM Outros WHERE outrosId = ?;";
		
		open();
		stmt = conexao.prepareStatement(sql);
		for (Outros i : selecionados) {
			stmt.setInt(1, i.getOutrosId());
			x += stmt.executeUpdate();
		}
		close();
		return x;
	}
}
