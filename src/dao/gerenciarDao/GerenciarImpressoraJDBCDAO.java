package dao.gerenciarDao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import model.Impressora;

/**
 * Classe para gerenciar a comunicação relacionada ao modelo Impressora com o banco de dados.
 * @author Leonardo Elnisky
 */
public class GerenciarImpressoraJDBCDAO implements GerenciarImpressoraDAO {
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
	 * Método para adicionar uma nova impressora ao banco de dados.
	 * @param impressora modelo de impressora com os dados a serem inseridos.
	 * @param qtd quantidade de impressoras iguais a serem adicionados.
	 * @return x a quantia de linhas alteradas no banco.
	 * @throws IOException 
	 */
	@Override
	public int adicionar(Impressora impressora, int qtd) throws SQLException, IOException {
		int x = 0;
		String sql = "INSERT INTO Impressora (impressoraId, marca, modelo, doador) VALUES (DEFAULT, ?, ?, ?)";
		
		open();
		stmt = conexao.prepareStatement(sql);
		stmt.setString(1, impressora.getMarca());
		stmt.setString(2, impressora.getModelo());
		stmt.setString(3, impressora.getDoador());
		
		int i=0;
		while (i < qtd) {
			x += stmt.executeUpdate();
			i++;
		}
		close();
		return x;
	}
	
	/**
	 * Método para carregar todas as impressoras do banco de dados.
	 * @return listImps lista com todas as impressoras contidas no banco de dados.
	 * @throws IOException 
	 */
	@Override
	public List<Impressora> load() throws SQLException, IOException {
		open();
		String sql = "SELECT * FROM Impressora ORDER BY 1";
		
		try (PreparedStatement stmt = conexao.prepareStatement(sql)){
			try (ResultSet rs = stmt.executeQuery()){
				List<Impressora> listImp = new ArrayList<>();
				while (rs.next()) {
					Impressora imp = new Impressora();
					imp.setImpressoraId(rs.getInt("impressoraId"));
					imp.setMarca(rs.getString("marca"));
					imp.setModelo(rs.getString("modelo"));
					imp.setDoador(rs.getString("doador"));
					listImp.add(imp);
				}
				close();
				return listImp;
			}
		}
	}

	/**
	 * Método para remover (doar) uma lista de impressoras do banco de dados.
	 * @param selecionados lista contendo as impressoras a serem removidos.
	 * @return x a quantia de linhas alteradas no banco.
	 * @throws IOException 
	 */
	@Override
	public int remover(ObservableList<Impressora> selecionados) throws SQLException, IOException {
		int x = 0;
		String sql = "DELETE FROM Impressora WHERE impressoraId = ?;";
		open();
		stmt = conexao.prepareStatement(sql);
		for (Impressora i : selecionados) {
			stmt.setInt(1, i.getImpressoraId());
			x += stmt.executeUpdate();
		}
		close();
		return x;
	}

}
