package dao.gerenciarDao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import model.Teclado;

/**
 * Classe para gerenciar a comunicação relacionada ao modelo Teclado com o banco de dados.
 * @author Leonardo Elnisky
 */
public class GerenciarTecladoJDBCDAO implements GerenciarTecladoDAO {
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
	 * Método para adicionar um novo teclado ao banco de dados.
	 * @param teclado modelo de teclado com os dados a serem inseridos.
	 * @param qtd quantidade de teclados iguais a serem adicionados.
	 * @return x a quantia de linhas alteradas no banco.
	 * @throws IOException 
	 */
	@Override
	public int adicionar(Teclado teclado, int qtd) throws SQLException, IOException {
		int x = 0;
		String sql = "INSERT INTO Teclado (tecladoId, marca, modelo, doador) VALUES (DEFAULT, ?, ?, ?)";
		
		open();
		stmt = conexao.prepareStatement(sql);
		stmt.setString(1, teclado.getMarca());
		stmt.setString(2, teclado.getModelo());
		stmt.setString(3, teclado.getDoador());
		
		int i=0;
		while (i < qtd) {
			x += stmt.executeUpdate();
			i++;
		}
		close();
		return x;
	}
	
	/**
	 * Método para carregar todos os teclados do banco de dados.
	 * @return listTeclados lista com todos os teclados contidos no banco de dados.
	 * @throws IOException 
	 */
	@Override
	public List<Teclado> load() throws SQLException, IOException {
		open();
		String sql = "SELECT * FROM Teclado ORDER BY 1";
		
		try (PreparedStatement stmt = conexao.prepareStatement(sql)){
			try (ResultSet rs = stmt.executeQuery()){
				List<Teclado> listTeclados = new ArrayList<>();
				while (rs.next()) {
					Teclado tec = new Teclado();
					tec.setTecladoId(rs.getInt("tecladoId"));
					tec.setMarca(rs.getString("marca"));
					tec.setModelo(rs.getString("modelo"));
					tec.setDoador(rs.getString("doador"));
					listTeclados.add(tec);
				}
				close();
				return listTeclados;
			}
		}
	}

	/**
	 * Método para remover (doar) uma lista de teclados do banco de dados.
	 * @param selecionados lista contendo os teclados a serem removidos.
	 * @return x a quantia de linhas alteradas no banco.
	 * @throws IOException 
	 */
	@Override
	public int remover(ObservableList<Teclado> selecionados) throws SQLException, IOException {
		int x = 0;
		String sql = "DELETE FROM Teclado WHERE tecladoId = ?;";
		
		open();
		stmt = conexao.prepareStatement(sql);
		for (Teclado i : selecionados) {
			stmt.setInt(1, i.getTecladoId());
			x += stmt.executeUpdate();
		}
		close();
		return x;
	}
}
