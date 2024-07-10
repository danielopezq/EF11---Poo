package codigo.codigofinal; // 00043823 Paquete donde se encuentra la clase Tarjeta.

import java.time.LocalDate; // 00043823 Importo la clase LocalDate para poder obtener la fecha y hora actual.

public class Tarjeta { // 00043823 Declaracion de la clase Tarjeta.
    private int idTarjeta; // 00043823 Variable de tipo int para guardar los id de cada tarjeta.
    private String numeroTarjeta; // 00043823 Variable de tipo String para guardar el numero de tarjeta.
    private LocalDate fechaExpiracion; // 00043823 Variable de tipo LocalDate para guardar la fecha de expiracion de la tarjeta.
    private String tipoTarjeta; // 00043823 Variable de tipo String para guardar el tipo de tarjeta.
    private String facilitador; // 00043823 Variable de tipo String para guardar el facilitador de la tarjeta.
    private int idCliente; // 00043823 Variable de tipo int para guardar los id de cada cliente.

    public Tarjeta() { // 00043823 Contructor vacio de la clase Tarjeta.
    }

    public Tarjeta(int idTarjeta, String numeroTarjeta, LocalDate fechaExpiracion, String tipoTarjeta, String facilitador, int idCliente) { // 00043823 Constructor con parametros de la clase Tarjeta.
        this.idTarjeta = idTarjeta; // 00043823 El idTarjeta de la clase tomara el valor del idTarjeta que le pasen en el parametro.
        this.numeroTarjeta = numeroTarjeta; // 00043823 El numeroTarjeta de la clase tomara el valor del numeroTarjeta que le pasen en el parametro.
        this.fechaExpiracion = fechaExpiracion; // 00043823 El fechaExpiracion de la clase tomara el valor del fechaExpiracion que le pasen en el parametro.
        this.tipoTarjeta = tipoTarjeta; // 00043823 El tipoTarjeta de la clase tomara el valor del tipoTarjeta que le pasen en el parametro.
        this.facilitador = facilitador; // 00043823 El facilitador de la clase tomara el valor del facilitador que le pasen en el parametro.
        this.idCliente = idCliente; // 00043823 El idCliente de la clase tomara el valor del idCliente que le pasen en el parametro.
    }

    public int getIdTarjeta() { // 00043823 Metodo getter de la variable idTarjeta.
        return idTarjeta; // 00043834 Este retorna la varible idTarjeta.
    }

    public void setIdTarjeta(int idTarjeta) { // 00043823 Metodo setter de la variable idTarjeta con el parametro idTarjeta.
        this.idTarjeta = idTarjeta; // 00043823 En este la varible idTarjeta de la clase tomara el valor del parametro que le pasen.
    }

    public String getNumeroTarjeta() { // 00043823 Metodo getter de la variable numeroTarjeta.
        return numeroTarjeta; // 00043834 Este retorna la varible numeroTarjeta.
    }

    public void setNumeroTarjeta(String numeroTarjeta) {// 00043823 Metodo setter de la variable numeroTarjeta con el parametro numeroTarjeta.
        this.numeroTarjeta = numeroTarjeta; // 00043823 En este la varible numeroTarjeta de la clase tomara el valor del parametro que le pasen.
    }

    public LocalDate getFechaExpiracion() { // 00043823 Metodo getter de la variable fechaExpiracion.
        return fechaExpiracion; // 00043834 Este retorna la varible fechaExpiracion.
    }

    public void setFechaExpiracion(LocalDate fechaExpiracion) { // 00043823 Metodo setter de la variable fechaExpiracion con el parametro fechaExpiracion.
        this.fechaExpiracion = fechaExpiracion; // 00043823 En este la varible fechaExpiracion de la clase tomara el valor del parametro que le pasen.
    }

    public String getTipoTarjeta() { // 00043823 Metodo getter de la variable tipoTarjeta.
        return tipoTarjeta; // 00043834 Este retorna la varible tipoTarjeta.
    }

    public void setTipoTarjeta(String tipoTarjeta) { // 00043823 Metodo setter de la variable tipoTarjeta con el parametro tipoTarjeta.
        this.tipoTarjeta = tipoTarjeta; // 00043823 En este la varible tipoTarjeta de la clase tomara el valor del parametro que le pasen.
    }

    public String getFacilitador() { // 00043823 Metodo getter de la variable facilitador.
        return facilitador; // 00043834 Este retorna la varible facilitador.
    }

    public void setFacilitador(String facilitador) { // 00043823 Metodo setter de la variable facilitador con el parametro facilitador.
        this.facilitador = facilitador; // 00043823 En este la varible facilitador de la clase tomara el valor del parametro que le pasen.
    }

    public int getIdCliente() { // 00043823 Metodo getter de la variable idCliente.
        return idCliente; // 00043834 Este retorna la varible idCliente.
    }

    public void setIdCliente(int idCliente) { // 00043823 Metodo setter de la variable idCliente con el parametro idCliente.
        this.idCliente = idCliente; // 00043823 En este la varible idCliente de la clase tomara el valor del parametro que le pasen.
    }
}
