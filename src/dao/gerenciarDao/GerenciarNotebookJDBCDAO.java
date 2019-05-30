package dao.gerenciarDao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import model.Notebook;

/**
 * Classe para gerenciar a comunicação relacionada ao modelo Notebook com o banco de dados.
 * @author Leonardo Elnisky
 */
public class GerenciarNotebookJDBCDAO implements GerenciarNotebookDAO {
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
	 * Método para adicionar um novo notebook ao banco de dados.
	 * @param notebook modelo de notebook com os dados a serem inseridos.
	 * @param qtd quantidade de notebooks iguais a serem adicionados.
	 * @return x a quantia de linhas alteradas no banco.
	 * @throws IOException 
	 */
	@Override
	public int adicionar(Notebook notebook, int qtd) throws SQLException, IOException {
		int x = 0;
		String sql = "INSERT INTO Notebook (notebookId, marca, modelo, doador) VALUES (DEFAULT, ?, ?, ?)";
		
		open();
		stmt = conexao.prepareStatement(sql);
		stmt.setString(1, notebook.getMarca());
		stmt.setString(2, notebook.getModelo());
		stmt.setString(3, notebook.getDoador());
		
		int i=0;
		while (i < qtd) {
			x += stmt.executeUpdate();
			i++;
		}
		close();
		return x;
	}
	
	/**
	 * Método para carregar todos os notebooks do banco de dados.
	 * @return notebooks lista com todos os notebooks contidos no banco de dados.
	 * @throws IOException 
	 */
	@Override
	public List<Notebook> load() throws SQLException, IOException {
		open();
		String sql = "SELECT * FROM Notebook ORDER BY 1";
		
		try (PreparedStatement stmt = conexao.prepareStatement(sql))	{
			try (ResultSet rs = stmt.executeQuery()) {
				List<Notebook> listNote = new ArrayList<>();
				while (rs.next()) {
					Notebook note = new Notebook();
					note.setComputadorId(rs.getInt("notebookId"));
					note.setMarca(rs.getString("marca"));
					note.setModelo(rs.getString("modelo"));
					note.setDoador(rs.getString("doador"));
					listNote.add(note);
				}
				close();
				return listNote;
			}
		}
	}

	/**
	 * Método para remover (doar) uma lista de notebooks do banco de dados.
	 * @param selecionados lista contendo os notebooks a serem removidos.
	 * @return x a quantia de linhas alteradas no banco.
	 * @throws IOException 
	 */
	@Override
	public int remover(ObservableList<Notebook> selecionados) throws SQLException, IOException {
		int x = 0;
		String sql = "DELETE FROM Notebook WHERE notebookId = ?;";
		
		open();
		stmt = conexao.prepareStatement(sql);
		for (Notebook i : selecionados) {
			stmt.setInt(1, i.getComputadorId());
			x += stmt.executeUpdate();
		}
		close();
		return x;
	}
}
