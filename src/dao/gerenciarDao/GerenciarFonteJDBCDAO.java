package dao.gerenciarDao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import model.Fonte;

/**
 * Classe para gerenciar a comunicação relacionada ao modelo Fonte com o banco de dados.
 * @author Leonardo Elnisky
 */
public class GerenciarFonteJDBCDAO implements GerenciarFonteDAO {
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
	 * @throws SQLException
	 */
	@Override
	public void close() throws SQLException {
		conexao.close();
	}
	
	/**
	 * Método para adicionar uma nova fonte ao banco de dados.
	 * @param fonte modelo de fonte com os dados a serem inseridos.
	 * @param qtd quantidade de fontes iguais a serem inseridas.
	 * @return x a quantia de linhas alteradas no banco.
	 * @throws IOException 
	 */
	@Override
	public int adicionar(Fonte fonte, int qtd) throws SQLException, IOException {
		int x = 0;
		String sql = "INSERT INTO Fonte (fonteId, marca, modelo, potencia, voltagem, doador) VALUES (DEFAULT, ?, ?, ?, ?, ?)";
		
		open();
		stmt = conexao.prepareStatement(sql);
		stmt.setString(1, fonte.getMarca());
		stmt.setString(2, fonte.getModelo());
		stmt.setString(3, fonte.getPotencia());
		stmt.setString(4, fonte.getVoltagem());
		stmt.setString(5, fonte.getDoador());
		
		int i=0;
		while (i < qtd) {
			x += stmt.executeUpdate();
			i++;
		}
		close();
		return x;
	}

	/**
	 * Método para carregar todas as fontes existentes no banco de dados.
	 * @return listFontes lista contendo todas as fontes no banco de dados.
	 * @throws IOException 
	 */
	@Override
	public List<Fonte> load() throws SQLException, IOException {
		open();
		String sql = "SELECT * FROM Fonte ORDER BY 1";
		
		try (PreparedStatement stmt = conexao.prepareStatement(sql)){
			try (ResultSet rs = stmt.executeQuery()){
				List<Fonte> listFontes = new ArrayList<>();
				while(rs.next()) {
					Fonte fonte = new Fonte();
					fonte.setFonteId(rs.getInt("fonteId"));
					fonte.setGabineteId(rs.getInt("gabineteId"));
					fonte.setNotebookId(rs.getInt("notebookId"));
					fonte.setMarca(rs.getString("marca"));
					fonte.setModelo(rs.getString("modelo"));
					fonte.setPotencia(rs.getString("potencia"));
					fonte.setVoltagem(rs.getString("voltagem"));
					fonte.setDoador(rs.getString("doador"));
					listFontes.add(fonte);
				}
				close();
				return listFontes;
			}
		}
	}

	/**
	 * Método para remover (doar) uma lista de fontes do banco de dados.
	 * @param selecionados lista contendo as fontes a serem removidas.
	 * @return x a quantia de linhas alteradas na tabela.
	 * @throws IOException 
	 */
	@Override
	public int remover(ObservableList<Fonte> selecionados) throws SQLException, IOException {
		int x = 0;
		String sql = "DELETE FROM Fonte WHERE fonteId = ?;";
		
		open();
		stmt = conexao.prepareStatement(sql);
		for (Fonte i : selecionados) {
			stmt.setInt(1, i.getFonteId());
			x += stmt.executeUpdate();
		}
		close();
		return x;
	}

}
