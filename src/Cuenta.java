public class Cuenta {

    private Integer cuenta_id;
    private Integer cliente_id;
    private double saldo;

    public Cuenta(){}

    public Cuenta(Integer cuenta_id, Integer cliente_id, double saldo) {
        this.cuenta_id = cuenta_id;
        this.cliente_id = cliente_id;
        this.saldo = saldo;
    }

    public Integer getCuenta_id() {
        return cuenta_id;
    }

    public void setCuenta_id(Integer cuenta_id) {
        this.cuenta_id = cuenta_id;
    }

    public Integer getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(Integer cliente_id) {
        this.cliente_id = cliente_id;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }





}
