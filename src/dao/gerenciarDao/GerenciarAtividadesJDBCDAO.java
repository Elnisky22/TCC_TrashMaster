package dao.gerenciarDao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Atividade;

/**
 * Classe para gerenciar a comunicação do modelo Atividade com o banco de dados.
 * @author Leonardo Elnisky.
 *
 */
public class GerenciarAtividadesJDBCDAO implements GerenciarAtividadesDAO {
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
	 * Método para adicionar uma nova atividade ao banco de dados.
	 * @param atividade modelo de atividade com os dados à serem inseridos.
	 * @return x a quantia de linhas alteradas na tabela.
	 * @throws IOException 
	 */
	@Override
	public int adicionar(Atividade atividade) throws SQLException, IOException {
		int x;
		
		open();
		String sql = "INSERT INTO Atividades (atividadeId, dia, atividade, voluntario) VALUES (DEFAULT, CURRENT_DATE, ?, ?);";
		
		stmt = conexao.prepareStatement(sql);
		stmt.setString(1, atividade.getAtividade());
		stmt.setString(2, atividade.getVoluntario());
		x = stmt.executeUpdate();
		close();
		return x;
	}

	/**
	 * Método para carregar todas as atividades do banco de dados.
	 * @return listAtividades lista contendo todas as atividades armazenadas no banco de dados.
	 * @throws IOException 
	 */
	@Override
	public List<Atividade> load() throws SQLException, IOException {
		open();
		String sql = "SELECT atividadeId, TO_CHAR(dia, 'dd/mm/yyyy') dia, atividade, voluntario FROM Atividades ORDER BY 1 DESC;";
		
		try (PreparedStatement stmt = conexao.prepareStatement(sql)){
			try (ResultSet rs = stmt.executeQuery()){
				List<Atividade> listAtividades = new ArrayList<>();
				while (rs.next()) {
					Atividade atv = new Atividade();
					atv.setData(rs.getString("dia"));
					atv.setAtividade(rs.getString("atividade"));
					atv.setVoluntario(rs.getString("voluntario"));
					listAtividades.add(atv);
				}
				close();
				return listAtividades;
			}
		}
	}
}
