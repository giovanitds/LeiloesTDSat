import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){
        // 1. Abre a conexão com o banco tirando o comentário da linha original
        conn = new conectaDAO().connectDB();
        
        // 2. Define a query SQL para inserção dos dados
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        
        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            
            // 3. Executa a inserção no MySQL
            prep.execute();
            
            // 4. Exibe a mensagem de sucesso exigida no enunciado
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
            
        } catch (SQLException erro) {
            // Exibe mensagem amigável caso ocorra algum erro (ex: banco desligado)
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + erro.getMessage());
        } finally {
            // Fecha os recursos para não travar o banco de dados
            try { if (prep != null) prep.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        // 1. Garante que a lista comece vazia para não duplicar dados ao atualizar a tela
        listagem.clear();
        
        // 2. Define o comando SQL para buscar todos os registros do banco
        String sql = "SELECT * FROM produtos";
        conn = new conectaDAO().connectDB();
        
        try {
            prep = conn.prepareStatement(sql);
            // 3. Executa a consulta e guarda o resultado na variável 'resultset'
            resultset = prep.executeQuery();
            
            // 4. Percorre cada linha que o banco de dados retornou
            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                
                // Mapeia as colunas do banco de dados para o objeto Java
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                
                // Adiciona o produto na lista
                listagem.add(produto);
            }
            
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + erro.getMessage());
        } finally {
            // Fecha as conexões abertas
            try { if (resultset != null) resultset.close(); } catch (Exception e) {}
            try { if (prep != null) prep.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        
        // 5. Retorna a lista devidamente preenchida
        return listagem;
    }
}

