package contenido;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.Assert;
import org.junit.Test;

//COMPROBACIÓN DE CONEXIÓN CON BASE DE DATOS MYSQL.
public class JUnit4Test{
    
  private Ventana ventana = new Ventana(new Dialogo(),"root","abc123");
	
    @Test
    public void parametrosSQL() throws SQLException{
      Connection conexion = this.ventana.crearConexion("jdbc:mysql://localhost:3306/liceo?useSSL=false&serverTimezone=Europe/Madrid");
      Assert.assertNotNull(conexion);
    }

}
