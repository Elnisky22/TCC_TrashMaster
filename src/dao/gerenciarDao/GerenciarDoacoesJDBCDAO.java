package dao.gerenciarDao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Doacao;

/**
 * Classe para gerenciar a comunicação do modelo Doacao com o banco de dados.
 * @author Leonardo Elnisky
 *
 */
public class GerenciarDoacoesJDBCDAO implements GerenciarDoacoesDAO {
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
	 * Método para gravar uma doação realizada no banco de dados.
	 * @param doacao string contendo todos os itens doados.
	 * @param recebedor o nome do recebedor da doação.
	 * @throws IOException 
	 */
	@Override
	public void adicionar(String doacao, String recebedor) throws SQLException, IOException {
		open();
		String sql = "INSERT INTO Doacoes (doacaoId, dia, itens, recebedor) VALUES (DEFAULT, CURRENT_DATE, ?, ?);";
		
		stmt = conexao.prepareStatement(sql);
		stmt.setString(1, doacao);
		stmt.setString(2, recebedor);
		stmt.executeUpdate();
		
		close();
	}

	/**
	 * Método para carregar todas as doações existentes no banco de dados.
	 * @return doacoes retorna a lista com todas as doações existentes.
	 * @throws IOException 
	 */
	@Override
	public List<Doacao> load() throws SQLException, IOException {
		open();
		String sql = "SELECT TO_CHAR(dia, 'dd/mm/yyyy') dia, itens, recebedor FROM Doacoes ORDER BY doacaoId DESC;";
		
		try (PreparedStatement stmt = conexao.prepareStatement(sql)){
			try (ResultSet rs = stmt.executeQuery()){
				List<Doacao> doacoes = new ArrayList<>();
				while (rs.next()) {
					Doacao doacao = new Doacao();
					doacao.setData(rs.getString("dia"));
					doacao.setItens(rs.getString("itens"));
					doacao.setRecebedor(rs.getString("recebedor"));
					doacoes.add(doacao);
				}
				close();
				return doacoes;
			}
		}
	}
}
