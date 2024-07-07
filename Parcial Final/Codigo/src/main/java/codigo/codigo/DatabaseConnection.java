package codigo.codigo;

import java.sql.Connection; //00043823 Importo  de la clase sql Connection
import java.sql.DriverManager; //00043823 Importo  de la clase sql DriverManager
import java.sql.SQLException; //00043823 Importo  de la clase sql SQLException

public class DatabaseConection { //00043823 Nombre de la case
    private static final String URL = "jdbc:sqlserver://localhost\\MSSQLSERVER:1433;databaseName=BCN;encrypt=true;trustServerCertificate=true;"; //00043823 Hago el atributo estatico y final porque será la misma URL, aquí hago la conexion de SQL Server a Java con mi BD llamada BCN
    private static final String USER = "ParcialFinal"; //00043823 Hago el atributo estatico y final porque será el mismo USER
    private static final String PASSWORD = "parcialfinalpoo"; //00043823 Hago el atributo estatico y final porque será la misma PASSWORD

    public static Connection getConnection() throws SQLException { //00043823 Agrego el método estatico de tipo Connection llamado getConnection
        return DriverManager.getConnection(URL, USER, PASSWORD); //00043823 Retorna el método DriverManager.getConnection que tiene como parametro URL, USER, PASSWORD que son los antes declarados, ya aquí se hace la conexión
    }

}