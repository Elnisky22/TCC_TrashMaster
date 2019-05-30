package dao.gerenciarDao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import model.Memoria;

/**
 * Classe para gerenciar a comunica��o relacionada ao modelo Mem�ria com o banco de dados.
 * @author Leonardo Elnisky.
 */
public class GerenciarMemoriaJDBCDAO implements GerenciarMemoriaDAO {
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
	 * M�todo para adicionar uma nova mem�ria ao banco de dados.
	 * @param memoria modelo de mem�ria com os dados a serem inseridos.
	 * @param qtd quantidade de mem�rias iguais a serem adicionados.
	 * @return x a quantia de linhas alteradas no banco.
	 * @throws IOException 
	 */
	@Override
	public int adicionar(Memoria memoria, int qtd) throws SQLException, IOException {
		int x = 0;
		String sql = "INSERT INTO Memoria (memoriaId, marca, modelo, tipo, memoria, doador) VALUES (DEFAULT, ?, ?, ?, ?, ?)";
		
		open();
		stmt = conexao.prepareStatement(sql);
		stmt.setString(1, memoria.getMarca());
		stmt.setString(2, memoria.getModelo());
		stmt.setString(3, memoria.getTipo());
		stmt.setDouble(4, memoria.getMemoria());
		stmt.setString(5, memoria.getDoador());
		
		int i=0;
		while (i < qtd) {
			x += stmt.executeUpdate();
			i++;
		}
		close();
		return x;
	}

	/**
	 * M�todo para carregar todas as mem�rias do banco de dados.
	 * @return listMem lista com todas as mem�rias contidas no banco de dados.
	 * @throws IOException 
	 */
	@Override
	public List<Memoria> load() throws SQLException, IOException {
		open();
		String sql = "SELECT * FROM Memoria ORDER BY 1";
		
		try (PreparedStatement stmt = conexao.prepareStatement(sql)){
			try (ResultSet rs = stmt.executeQuery()){
				List<Memoria> listMem = new ArrayList<>();
				while(rs.next()) {
					Memoria mem = new Memoria();
					mem.setMemoriaId(rs.getInt("memoriaId"));
					mem.setGabineteId(rs.getInt("gabineteId"));
					mem.setNotebookId(rs.getInt("notebookId"));
					mem.setMarca(rs.getString("marca"));
					mem.setModelo(rs.getString("modelo"));
					mem.setTipo(rs.getString("tipo"));
					mem.setMemoria(rs.getFloat("memoria"));
					mem.setDoador(rs.getString("doador"));
					listMem.add(mem);
				}
				close();
				return listMem;
			}
		}
	}

	/**
	 * M�todo para remover (doar) uma lista de mem�rias do banco de dados.
	 * @param selecionados lista contendo as mem�rias a serem removidas.
	 * @return x a quantia de linhas alteradas no banco.
	 * @throws IOException 
	 */
	@Override
	public int remover(ObservableList<Memoria> selecionados) throws SQLException, IOException {
		int x = 0;
		String sql = "DELETE FROM Memoria WHERE memoriaId = ?;";
		
		open();
		stmt = conexao.prepareStatement(sql);
		for (Memoria i : selecionados) {
			stmt.setInt(1, i.getMemoriaId());
			x +=stmt.executeUpdate();
		}
		close();
		return x;
	}
}
