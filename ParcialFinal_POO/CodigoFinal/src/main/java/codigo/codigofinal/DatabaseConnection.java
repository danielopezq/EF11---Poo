package codigo.codigofinal;// 00043823 Paquete donde se encuentra la clase DatabaseConnection.

import java.sql.Connection; //00043823 Importo  de la clase sql Connection.
import java.sql.DriverManager; //00043823 Importo  de la clase sql DriverManager.
import java.sql.SQLException; //00043823 Importo  de la clase sql SQLException.

public class DatabaseConnection { //00043823 Nombre de la clase.
   private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=BCNN;encrypt=true;trustServerCertificate=true;"; //00043823 Hago el atributo estatico y final porque sera la misma URL, aqui hago la conexion de SQL Server a Java con mi BD llamada BCNN.
    private static final String USER = "ParcialFinal"; //00043823 Hago el atributo estatico y final porque sera el mismo USER.
    private static final String PASSWORD = "parcialfinalpoo"; //00043823 Hago el atributo estatico y final porque sera la misma PASSWORD.

    public static Connection getConnection() throws SQLException { //00043823 Agrego el metodo estatico de tipo Connection llamado getConnection.
        return DriverManager.getConnection(URL, USER, PASSWORD); //00043823 Retorna el metodo DriverManager.getConnection que tiene como parametro URL, USER, PASSWORD que son los antes declarados, ya aqui se hace la conexion.
    }
}
