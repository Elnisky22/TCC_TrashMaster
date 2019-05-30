package dao.gerenciarDao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import model.Cabo;

/**
 * Classe para gerenciar a comunica��o relacionada ao modelo Cabo com o banco de dados.
 * @author Leonardo Elnisky
 */
public class GerenciarCaboJDBCDAO implements GerenciarCaboDAO {
	private Connection conexao;
	private PreparedStatement stmt;
	
	/**
	 * M�todo para abrir a conex�o com o banco de dados.
	 * @throws IOException 
	 */
	@Override
	public void open() throws SQLException, IOException {
		conexao = dao.Conexao.getConexao();
	}

	/**
	 * M�todo para fechar a conex�o com o banco de dados.
	 */
	@Override
	public void close() throws SQLException {
		conexao.close();
	}
	
	/**
	 * M�todo para adicionar/incrementar a quantidade de cabos no banco.
	 * @param cabo modelo de cabo com os dados � serem inseridos.
	 * @return x quantia de linhas da tabela alteradas.
	 * @throws IOException 
	 */
	@Override
	public int adicionar(Cabo cabo) throws SQLException, IOException {
		int x;
		open();
		String sql = "UPDATE Cabo SET quantidade = quantidade + ? WHERE tipo = ?;";
		stmt = conexao.prepareStatement(sql);
		stmt.setInt(1, cabo.getQuantidade());
		stmt.setString(2, cabo.getTipo());
		x = stmt.executeUpdate();
		close();
		
		return x;
	}

	/**
	 * M�todo para carregar todos os cabos do banco de dados.
	 * @return listCabos lista contendo todos os cabos armazenados no banco de dados.
	 * @throws IOException 
	 */
	@Override
	public List<Cabo> load() throws SQLException, IOException {
		open();
		String sql = "SELECT * FROM Cabo ORDER BY 1;";
		
		try (PreparedStatement stmt = conexao.prepareStatement(sql)){
			try (ResultSet rs = stmt.executeQuery()){
				List<Cabo> listCabos = new ArrayList<>();
				while (rs.next()) {
					Cabo cabo = new Cabo();
					cabo.setCaboId(rs.getInt("caboId"));
					cabo.setTipo(rs.getString("tipo"));
					cabo.setQuantidade(rs.getInt("quantidade"));
					listCabos.add(cabo);
				}
				close();
				return listCabos;
			}
		}
	}

	/**
	 * M�todo para buscar um �nico cabo no banco e retorn�-lo.
	 * @param tipo o tipo de cabo a ser buscado no banco.
	 * @return cabo o cabo encontrado no banco.
	 */
	@Override
	public Cabo buscar(String tipo) throws SQLException, IOException {
		String sql = "SELECT * FROM Cabo WHERE Tipo = '" + tipo + "';";
		Cabo cabo = new Cabo();
		
		open();
		try (PreparedStatement stmt = conexao.prepareStatement(sql)){
			try (ResultSet rs = stmt.executeQuery()){
				if (rs.next()) {
					cabo.setTipo(rs.getString("tipo"));
					cabo.setQuantidade(rs.getInt("quantidade"));
				}
			}
			close();
			return cabo;
		}
	}
	
	/**
	 * M�todo para remover/decrementar um cabo do banco de dados.
	 * @param selecionados uma lista contento os cabos a serem decrementados.
	 * @return x a quantia de linhas alteradas na tabela.
	 * @throws IOException 
	 */
	@Override
	public int remover(ObservableList<Cabo> selecionados, int qtd) throws SQLException, IOException {
		int x = 0;
		String sql = "UPDATE Cabo SET quantidade = quantidade - ? WHERE tipo = ?;";
		
		open();
		stmt = conexao.prepareStatement(sql);
		for (Cabo i : selecionados) {
			stmt.setInt(1, qtd);
			stmt.setString(2, i.getTipo());
			x += stmt.executeUpdate();
		}
		close();
		return x;
	}
}
