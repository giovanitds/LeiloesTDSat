import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class conectaDAO {
    
    public Connection connectDB(){
        Connection conn = null;
        
        try {
            // Adicionado &useSSL=false no final da string para desativar os alertas de segurança locais
            conn = DriverManager.getConnection("jdbc:mysql://localhost/uc11?user=root&password=05071985Gi!&useSSL=false");
            
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "Erro ConectaDAO: " + erro.getMessage());
        }
        return conn;
    }
}
