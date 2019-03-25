public class Transaccion
{

    private String idCliente;
    private String tipo;
    private String valor;
    private String fecha;

    public Transaccion()
    {

    }

    public Transaccion (String IdCliente, String Tipo, String Valor, String Fecha)
    {
        idCliente = IdCliente;
        tipo = Tipo;
        valor = Valor;
        fecha = Fecha;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
