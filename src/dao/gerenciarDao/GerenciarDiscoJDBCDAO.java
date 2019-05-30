package dao.gerenciarDao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import model.DiscoRigido;

/**
 * Classe para gerenciar a comunicação relacionada ao modelo Disco Rígido com o banco de dados.
 * @author Leonardo Elnisky
 */
public class GerenciarDiscoJDBCDAO implements GerenciarDiscoDAO {
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
	 * Método para adicionar um novo disco ao banco de dados.
	 * @param disco modelo de disco com os dados a serem inseridos.
	 * @param qtd quantidade de discos iguais a serem adicionados.
	 * @return x a quantia de linhas alteradas na tabela.
	 * @throws IOException 
	 */
	@Override
	public int adicionar(DiscoRigido disco, int qtd) throws SQLException, IOException {
		int x = 0;
		String sql = "INSERT INTO DiscoRigido (discoId, marca, modelo, capacidade, doador) VALUES (DEFAULT, ?, ?, ?, ?)";
		
		open();
		stmt = conexao.prepareStatement(sql);	
		stmt.setString(1, disco.getMarca());
		stmt.setString(2, disco.getModelo());
		stmt.setDouble(3, disco.getCapacidade());
		stmt.setString(4, disco.getDoador());
		
		int i=0;
		while (i < qtd) {
			x += stmt.executeUpdate();
			i++;
		}
		close();
		return x;
	}
	
	/**
	 * Método para carregar todos os discos do banco de dados.
	 * @return discos lista com todos os discos contidos no banco de dados.
	 * @throws IOException 
	 */
	@Override
	public List<DiscoRigido> load() throws SQLException, IOException {
		open();
		String sql = "SELECT * FROM DiscoRigido ORDER BY 1";
		
		try (PreparedStatement stmt = conexao.prepareStatement(sql)){
			try (ResultSet rs = stmt.executeQuery()) {
				List<DiscoRigido> discos = new ArrayList<>();
				while (rs.next()) {
					DiscoRigido disco = new DiscoRigido();
					disco.setDiscoId(rs.getInt("discoId"));
					disco.setGabineteId(rs.getInt("gabineteId"));
					disco.setNotebookId(rs.getInt("notebookId"));
					disco.setMarca(rs.getString("marca"));
					disco.setModelo(rs.getString("modelo"));
					disco.setCapacidade(rs.getDouble("capacidade"));
					disco.setDoador(rs.getString("doador"));
					discos.add(disco);
				}
				close();
				return discos;
			}
		}
	}

	/**
	 * Método para remover (doar) uma lista de discos do banco de dados.
	 * @param selecionados lista contendo os discos a serem removidos.
	 * @return x a quantia de linhas alteradas na tabela.
	 * @throws IOException 
	 */
	@Override
	public int remover(ObservableList<DiscoRigido> selecionados) throws SQLException, IOException {
		int x = 0;
		String sql = "DELETE FROM DiscoRigido WHERE discoId = ?;";
		
		open();
		stmt = conexao.prepareStatement(sql);
		for (DiscoRigido i : selecionados) {
			stmt.setInt(1, i.getDiscoId());
			x += stmt.executeUpdate();
		}
		close();
		return x;
	}

}
