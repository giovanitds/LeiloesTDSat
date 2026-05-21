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
        // 1. Abre a conexão com o banco
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

    // ==========================================
    // NOVO: IMPLEMENTAÇÕES DA ATIVIDADE 3
    // ==========================================

    public void venderProduto(int id) {
        // 1. Define a query SQL para atualizar o status do produto baseado no ID fornecido
        String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
        conn = new conectaDAO().connectDB();
        
        try {
            prep = conn.prepareStatement(sql);
            prep.setInt(1, id);
            
            // 2. Executa a atualização (UPDATE) no banco de dados
            prep.executeUpdate();
            
            // 3. Exibe mensagem de confirmação para o usuário
            JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
            
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar status do produto: " + erro.getMessage());
        } finally {
            // Garante o fechamento das conexões para economizar recursos do sistema
            try { if (prep != null) prep.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }

    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        // 1. Limpa a lista existente para evitar dados duplicados ao atualizar as tabelas
        listagem.clear();
        
        // 2. Define o comando SQL filtrando apenas os itens com status "Vendido"
        String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";
        conn = new conectaDAO().connectDB();
        
        try {
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            
            // 3. Percorre as linhas retornadas mapeando para objetos DTO
            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                
                listagem.add(produto);
            }
            
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos vendidos: " + erro.getMessage());
        } finally {
            // Garante a liberação dos recursos abertos no MySQL
            try { if (resultset != null) resultset.close(); } catch (Exception e) {}
            try { if (prep != null) prep.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        
        // 4. Retorna a lista contendo somente os itens vendidos
        return listagem;
    }
}

