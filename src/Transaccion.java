//import org.apache.derby.client.am.Decimal;


import java.io.Serializable;
import java.math.BigDecimal;

public class Transaccion implements Serializable
{
    public Integer getTransaccion_id() {
        return transaccion_id;
    }

    public void setTransaccion_id(Integer transaccion_id) {
        this.transaccion_id = transaccion_id;
    }

    public Integer getCuenta_id() {
        return cuenta_id;
    }

    public void setCuenta_id(Integer cuenta_id) {
        this.cuenta_id = cuenta_id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    private Integer transaccion_id;
    private Integer cuenta_id;
    private String  tipo;
    private BigDecimal valor;
    private String  fecha;


    public Transaccion() {
    }

    public Transaccion(Integer transaccion_id, Integer cuenta_id, String tipo, BigDecimal valor, String fecha) {
        this.transaccion_id = transaccion_id;
        this.cuenta_id = cuenta_id;
        this.tipo = tipo;
        this.valor = valor;
        this.fecha = fecha;
    }
}
