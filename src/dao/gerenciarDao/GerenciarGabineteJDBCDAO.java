package dao.gerenciarDao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import model.Gabinete;

/**
 * Classe para gerenciar a comunicação relacionada ao modelo Gabinete com o banco de dados.
 * @author Leonardo Elnisky.
 */
public class GerenciarGabineteJDBCDAO implements GerenciarGabineteDAO {
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
	 * Método para adicionar um novo gabinete ao banco de dados.
	 * @param gabinete modelo de gabinete com os dados a serem inseridos.
	 * @param qtd quantidade de gabinetes iguais a serem adicionados.
	 * @return x a quantia de linhas alteradas na tabela.
	 * @throws IOException 
	 */
	@Override
	public int adicionar(Gabinete gabinete, int qtd) throws SQLException, IOException {
		int x = 0;
		String sql = "INSERT INTO Gabinete (gabineteId, marca, tamanho, doador) VALUES (DEFAULT, ?, ?, ?)";
		
		open();
		stmt = conexao.prepareStatement(sql);
		stmt.setString(1, gabinete.getMarca());
		stmt.setString(2, gabinete.getTamanho());
		stmt.setString(3, gabinete.getDoador());
		
		int i=0;
		while (i < qtd) {
			x += stmt.executeUpdate();
			i++;
		}
		close();
		return x;
	}

	/**
	 * Método para carregar todos os gabinetes do banco de dados.
	 * @return gabs lista com todos os gabinetes contidos no banco de dados.
	 * @throws IOException 
	 */
	@Override
	public List<Gabinete> load() throws SQLException, IOException {
		open();
		String sql = "SELECT * FROM Gabinete ORDER BY 1";
		
		try (PreparedStatement stmt = conexao.prepareStatement(sql)){
			try (ResultSet rs = stmt.executeQuery()){
				List<Gabinete> gabs = new ArrayList<>();
				while (rs.next()) {
					Gabinete gab = new Gabinete();
					gab.setComputadorId(rs.getInt("gabineteId"));
					gab.setMarca(rs.getString("marca"));
					gab.setTamanho(rs.getString("tamanho"));
					gab.setDoador(rs.getString("doador"));
					gabs.add(gab);
				}
				close();
				return gabs;
			}
		}
	}

	/**
	 * Método para remover (doar) uma lista de gabinetes do banco de dados.
	 * @param selecionados lista contendo os gabinetes a serem removidos.
	 * @return a quantia de linhas alteradas no banco.
	 * @throws IOException 
	 */
	@Override
	public int remover(ObservableList<Gabinete> selecionados) throws SQLException, IOException {
		int x = 0;
		String sql = "DELETE FROM Gabinete WHERE gabineteId = ?;";
		
		open();
		stmt = conexao.prepareStatement(sql);
		for (Gabinete i : selecionados) {
			stmt.setInt(1, i.getComputadorId());
			x += stmt.executeUpdate();
		}
		close();
		return x;
	}
		
}
