package dao.gerenciarDao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import model.Mouse;

/**
 * Classe para gerenciar a comunicação relacionada ao modelo Mouse com o banco de dados.
 * @author Leonardo Elnisky
 */
public class GerenciarMouseJDBCDAO implements GerenciarMouseDAO {
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
	 * Método para adicionar um novo mouse ao banco de dados.
	 * @param mouse modelo de mouse com os dados a serem inseridos.
	 * @param qtd quantidade de mouses iguais a serem adicionados.
	 * @return x a quantia de linhas alteradas no banco.
	 * @throws IOException 
	 */
	@Override
	public int adicionar(Mouse mouse, int qtd) throws SQLException, IOException {
		int x = 0;
		String sql = "INSERT INTO Mouse (mouseId, marca, modelo, doador) VALUES (DEFAULT, ?, ?, ?)";
		
		open();
		stmt = conexao.prepareStatement(sql);
		stmt.setString(1, mouse.getMarca());
		stmt.setString(2, mouse.getModelo());
		stmt.setString(3, mouse.getDoador());
		
		int i=0;
		while (i < qtd) {
			x += stmt.executeUpdate();
			i++;
		}
		close();
		return x;
	}
	
	/**
	 * Método para carregar todos os mouses do banco de dados.
	 * @return listMouses lista com todos os mouses contidos no banco de dados.
	 * @throws IOException 
	 */
	@Override
	public List<Mouse> load() throws SQLException, IOException {
		open();
		String sql = "SELECT * FROM Mouse ORDER BY 1";
		
		try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
			try (ResultSet rs = stmt.executeQuery()){
				List<Mouse> listMouses = new ArrayList<>();
				while (rs.next()) {
					Mouse mouse = new Mouse();
					mouse.setMouseId(rs.getInt("mouseId"));
					mouse.setMarca(rs.getString("marca"));
					mouse.setModelo(rs.getString("modelo"));
					mouse.setDoador(rs.getString("doador"));
					listMouses.add(mouse);
				}
				close();
				return listMouses;
			}
		}
	}

	/**
	 * Método para remover (doar) uma lista de mouses do banco de dados.
	 * @param selecionados lista contendo os mouses a serem removidos.
	 * @return x a quantia de linhas alteradas no banco.
	 * @throws IOException 
	 */
	@Override
	public int remover(ObservableList<Mouse> selecionados) throws SQLException, IOException {
		int x = 0;
		String sql = "DELETE FROM Mouse WHERE mouseId = ?;";
		
		open();
		stmt = conexao.prepareStatement(sql);
		for (Mouse i : selecionados) {
			stmt.setInt(1, i.getMouseId());
			x += stmt.executeUpdate();
		}
		close();
		return x;
	}
}
