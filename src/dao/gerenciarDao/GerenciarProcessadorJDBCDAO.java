package dao.gerenciarDao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import model.Processador;

/**
 * Classe para gerenciar a comunicação relacionada ao modelo Processador com o banco de dados.
 * @author Leonardo Elnisky
 */
public class GerenciarProcessadorJDBCDAO implements GerenciarProcessadorDAO {
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
	 * Método para adicionar um novo processador ao banco de dados.
	 * @param processador modelo de processador com os dados a serem inseridos.
	 * @param qtd quantidade de processadores iguais a serem adicionados.
	 * @return x a quantia de linhas alteradas no banco.
	 * @throws IOException 
	 */
	@Override
	public int adicionar(Processador processador, int qtd) throws SQLException, IOException {
		int x = 0;
		String sql = "INSERT INTO Processador (processadorId, marca, modelo, socket, doador) VALUES (DEFAULT, ?, ?, ?, ?)";
		
		open();
		stmt = conexao.prepareStatement(sql);
		stmt.setString(1, processador.getMarca());
		stmt.setString(2, processador.getModelo());
		stmt.setString(3, processador.getSocket());
		stmt.setString(4, processador.getDoador());
		
		int i=0;
		while (i < qtd) {
			x += stmt.executeUpdate();
			i++;
		}
		close();
		return x;
	}
	
	/**
	 * Método para carregar todos os processadores do banco de dados.
	 * @return listProces lista com todos os processadores contidos no banco de dados.
	 * @throws IOException 
	 */
	@Override
	public List<Processador> load() throws SQLException, IOException {
		open();
		String sql = "SELECT * FROM Processador ORDER BY 1";
		
		try (PreparedStatement stmt = conexao.prepareStatement(sql)){
			try (ResultSet rs = stmt.executeQuery()){
				List<Processador> listProces = new ArrayList<>();
				while (rs.next()) {
					Processador proc = new Processador();
					proc.setProcessadorId(rs.getInt("processadorId"));
					proc.setGabineteId(rs.getInt("processadorId"));
					proc.setNotebookId(rs.getInt("notebookId"));
					proc.setMarca(rs.getString("marca"));
					proc.setModelo(rs.getString("modelo"));
					proc.setSocket(rs.getString("socket"));
					proc.setDoador(rs.getString("doador"));
					listProces.add(proc);
				}
				close();
				return listProces;
			}
		}
	}

	/**
	 * Método para remover (doar) uma lista de processadores do banco de dados.
	 * @param selecionados lista contendo os processadores a serem removidos.
	 * @return x a quantia de linhas alteradas no banco.
	 * @throws IOException 
	 */
	@Override
	public int remover(ObservableList<Processador> selecionados) throws SQLException, IOException {
		int x = 0;
		String sql = "DELETE FROM Processador WHERE processadorId = ?;";
		
		open();
		stmt = conexao.prepareStatement(sql);
		for (Processador i : selecionados) {
			stmt.setInt(1, i.getProcessadorId());
			x += stmt.executeUpdate();
		}
		close();
		return x;
	}
}
