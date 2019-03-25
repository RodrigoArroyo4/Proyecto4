public class Cliente {


    private Integer cliente_id;
    private String nombre_cliente;

    public Cliente(){}
    public Cliente(Integer cliente_id, String nombre_cliente) {
        this.cliente_id = cliente_id;
        this.nombre_cliente = nombre_cliente;
    }

    public Integer getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(Integer cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }



}
