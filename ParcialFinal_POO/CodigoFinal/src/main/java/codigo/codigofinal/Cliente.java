package codigo.codigofinal; // 00043823 Paquete donde se encuentra la clase Cliente.

public class Cliente { // 00043823 Declaracion de la clase Cliente.
    private int idCliente; // 00043823 Variable de tipo int para guardar los id de cada cliente.
    private String nombre; // 00043823 Variable de tipo String para guardar el nombre del cliente.
    private String direccion; // 00043823 Variable de tipo String para guardar la direccion del cliente.
    private String telefono; // 00043823 Variable de tipo String para guardar el telefono del cliente.

    public Cliente() { // 00043823 Contructor vacio de la clase Cliente.
    }

    public Cliente(int idCliente, String nombre, String direccion, String telefono) { // 00043823 Constructor con parametros de la clase Cliente.
        this.idCliente = idCliente; // 00043823 El idCliente de la clase tomara el valor del idCliente que le pasen en el parametro.
        this.nombre = nombre; // 00043823 El nombre de la clase tomara el valor del nombre que le pasen en el parametro.
        this.direccion = direccion; // 00043823 La direccion de la clase tomara el valor de la direccion que le pasen en el parametro.
        this.telefono = telefono; // 00043823 El telefono de la clase tomara el valor del telefono que le pasen en el parametro.
    }

    public int getIdCliente() { // 00043823 Metodo getter de la variable idCliente.
        return idCliente; // 00043834 Este retorna la varible idCliente.
    }

    public void setIdCliente(int idCliente) { // 00043823 Metodo setter de la variable idCliente con el parametro idCliente.
        this.idCliente = idCliente; // 00043823 En este la varible idCliente de la clase tomara el valor del parametro que le pasen.
    }

    public String getNombre() { // 00043823 Metodo getter de la variable nombre.
        return nombre; // 00043834 Este retorna la varible nombre.
    }

    public void setNombre(String nombre) { // 00043823 Metodo setter de la variable nombre con el parametro nombre.
        this.nombre = nombre;  // 00043823 En este la varible nombre de la clase tomara el valor del parametro que le pasen.
    }

    public String getDireccion() { // 00043823 Metodo getter de la variable direccion.
        return direccion; // 00043834 Este retorna la varible direccion.
    }

    public void setDireccion(String direccion) { // 00043823 Metodo setter de la variable direccion con el parametro direccion.
        this.direccion = direccion; // 00043823 En este la varible direccion de la clase tomara el valor del parametro que le pasen.
    }

    public String getTelefono() { // 00043823 Metodo getter de la variable telefono.
        return telefono; // 00043834 Este retorna la varible telefono.
    }

    public void setTelefono(String telefono) { // 00043823 Metodo setter de la variable telefono con el parametro telefono.
        this.telefono = telefono; // 00043823 En este la varible telefono de la clase tomara el valor del parametro que le pasen.
    }
}
