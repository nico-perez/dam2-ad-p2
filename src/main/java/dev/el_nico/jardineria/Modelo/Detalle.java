package dev.el_nico.jardineria.modelo;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "detalle_pedido")
public class Detalle {
    
    private @EmbeddedId Detalle.Id id;
    private Integer cantidad;
    private Integer numero_linea;
    private Double precio_unidad;

    private Detalle() {}

    public Integer getCodigo_pedido() {
        return id.getCodigo_pedido();
    }

    public String getCodigo_producto() {
        return id.getCodigo_producto();
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getNumero_linea() {
        return numero_linea;
    }

    public void setNumero_linea(Integer numero_linea) {
        this.numero_linea = numero_linea;
    }

    public Double getPrecio_unidad() {
        return precio_unidad;
    }

    public void setPrecio_unidad(Double precio_unidad) {
        this.precio_unidad = precio_unidad;
    }

    @Override
    public String toString() {
        return numero_linea + " (Cod.Ped: " + id.getCodigo_pedido() + ", Cod.Pro: " + id.getCodigo_producto() + ") -> " + cantidad + " a " + precio_unidad + "/ud.";
    }

    public static @Embeddable class Id implements Serializable {
        
        private static final long serialVersionUID = 9026913262449782076L;
        private Integer codigo_pedido;
        private String codigo_producto;

        public Id(){}
        public Id(Integer codigo_pedido, String codigo_producto) {
            this.codigo_pedido = codigo_pedido;
            this.codigo_producto = codigo_producto;
        }

        public Integer getCodigo_pedido() {
            return codigo_pedido;
        }

        public String getCodigo_producto() {
            return codigo_producto;
        }
    }
}
