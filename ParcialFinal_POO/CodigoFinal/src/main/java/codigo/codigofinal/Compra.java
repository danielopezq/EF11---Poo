package codigo.codigofinal;

public class Compra {
    private int idCompra;
    private String fechaCompra;
    private double montoTotal;
    private String descripcion;

    public Compra() {
    }

    public Compra(int idCompra, String fechaCompra, double montoTotal, String descripcion) {
        this.idCompra = idCompra;
        this.fechaCompra = fechaCompra;
        this.montoTotal = montoTotal;
        this.descripcion = descripcion;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
