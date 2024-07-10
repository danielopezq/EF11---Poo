package codigo.codigofinal; // 00043823 Paquete donde se encuentra la clase Compra.

public class Compra { // 00043823 Declaracion de la clase Compra.
    private int idCompra; // 00043823 Variable de tipo int para guardar los id de cada compra.
    private String fechaCompra; // 00043823 Variable de tipo String para guardar la fecha de la compra.
    private double montoTotal; // 00043823 Variable de tipo double para guardar el monto total de la compra.
    private String descripcion; // 00043823 Variable de tipo String para guardar la descripcion de la compra.

    public Compra() { // 00043823 Contructor vacio de la clase Compra.
    }

    public Compra(int idCompra, String fechaCompra, double montoTotal, String descripcion) { // 00043823 Constructor con parametros de la clase Compra.
        this.idCompra = idCompra; // 00043823 El idCompra de la clase tomará el valor del idCompra que le pasen en el parametro.
        this.fechaCompra = fechaCompra; // 00043823 La fechaCompra de la clase tomará el valor de la fechaCompra que le pasen en el parametro.
        this.montoTotal = montoTotal; // 00043823 El montoTotal de la clase tomará el valor del montoTotal que le pasen en el parametro.
        this.descripcion = descripcion; // 00043823 La descripcion de la clase tomará el valor de la descripcion que le pasen en el parametro.
    }

    public int getIdCompra() { // 00043823 Metodo getter de la variable idCompra.
        return idCompra; // 00043834 Este retorna la varible idCompra.
    }

    public void setIdCompra(int idCompra) { // 00043823 Metodo setter de la variable idCompra con el parametro idCompra.
        this.idCompra = idCompra; // 00043823 En este la varible idCompra de la clase tomará el valor del parametro que le pasen.
    }

    public String getFechaCompra() { // 00043823 Metodo getter de la variable fechaCompra.
        return fechaCompra; // 00043834 Este retorna la varible fechaCompra.
    }

    public void setFechaCompra(String fechaCompra) { // 00043823 Metodo setter de la variable fechaCompra con el parametro fechaCompra.
        this.fechaCompra = fechaCompra; // 00043823 En este la varible fechaCompra de la clase tomará la cadena de texto del parametro que le pasen.
    }

    public double getMontoTotal() { // 00043823 Metodo getter de la variable montoTotal.
        return montoTotal; // 00043834 Este retorna la varible montoTotal.
    }

    public void setMontoTotal(double montoTotal) { // 00043823 Metodo setter de la variable montoTotal con el parametro montoTotal.
        this.montoTotal = montoTotal; // 00043823 En este la varible montoTotal de la clase tomará el valor del parametro que le pasen.
    }

    public String getDescripcion() { // 00043823 Metodo getter de la variable descripcion.
        return descripcion; // 00043834 Este retorna la varible descripcion.
    }

    public void setDescripcion(String descripcion) { // 00043823 Metodo setter de la variable descripcion con el parametro descripcion.
        this.descripcion = descripcion; // 00043823 En este la varible descripcion de la clase tomará la cadena de texto del parametro que le pasen.
    }
}
