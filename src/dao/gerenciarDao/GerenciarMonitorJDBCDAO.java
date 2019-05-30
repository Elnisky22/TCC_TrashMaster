package dao.gerenciarDao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import model.Monitor;

/**
 * Classe para gerenciar a comunicação relacionada ao modelo Monitor com o banco de dados.
 * @author Leonardo Elnisky
 */
public class GerenciarMonitorJDBCDAO implements GerenciarMonitorDAO {
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
	 * Método para adicionar um novo monitor ao banco de dados.
	 * @param monitor modelo de monitor com os dados a serem inseridos.
	 * @param qtd quantidade de monitores iguais a serem adicionados.
	 * @return x a quantia de linhas alteradas no banco.
	 * @throws IOException 
	 */
	@Override
	public int adicionar(Monitor monitor, int qtd) throws SQLException, IOException {
		int x = 0;
		String sql = "INSERT INTO Monitor (monitorId, marca, modelo, doador) VALUES (DEFAULT, ?, ?, ?)";
		
		open();
		stmt = conexao.prepareStatement(sql);
		stmt.setString(1, monitor.getMarca());
		stmt.setString(2, monitor.getModelo());
		stmt.setString(3, monitor.getDoador());
		
		int i=0;
		while (i < qtd) {
			x += stmt.executeUpdate();
			i++;
		}
		close();
		return x;
	}
	
	/**
	 * Método para carregar todos os monitores do banco de dados.
	 * @return listMon lista com todos os monitores contidos no banco de dados.
	 * @throws IOException 
	 */
	@Override
	public List<Monitor> load() throws SQLException, IOException {
		open();
		String sql = "SELECT * FROM Monitor ORDER BY 1";
		
		try (PreparedStatement stmt = conexao.prepareStatement(sql)){
			try (ResultSet rs = stmt.executeQuery()){
				List<Monitor> listMon = new ArrayList<>();
				while (rs.next()) {
					Monitor mon = new Monitor();
					mon.setMonitorId(rs.getInt("monitorId"));
					mon.setMarca(rs.getString("marca"));
					mon.setModelo(rs.getString("modelo"));
					mon.setDoador(rs.getString("doador"));
					listMon.add(mon);
				}
				close();
				return listMon;
			}
		}
	}

	/**
	 * Método para remover (doar) uma lista de monitores do banco de dados.
	 * @param selecionados lista contendo os monitores a serem removidos.
	 * @return x a quantia de linhas alteradas no banco.
	 * @throws IOException 
	 */
	@Override
	public int remover(ObservableList<Monitor> selecionados) throws SQLException, IOException {
		int x = 0;
		String sql = "DELETE FROM Monitor WHERE monitorId = ?;";
		
		open();
		stmt = conexao.prepareStatement(sql);
		for (Monitor i : selecionados) {
			stmt.setInt(1, i.getMonitorId());
			x += stmt.executeUpdate();
		}
		close();
		return x;
	}
}
