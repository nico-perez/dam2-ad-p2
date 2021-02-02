package dev.el_nico.jardineria.modelo;

import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.Id;

import dev.el_nico.jardineria.excepciones.ExcepcionDatoNoValido;
import dev.el_nico.jardineria.util.IBuilder;

@Entity
public class Producto {

    private @Id String codigo_producto;
    private String nombre;
    private String gama;
    private String dimensiones;
    private String proveedor;
    private String descripcion;
    private Integer cantidad_en_stock;
    private Double precio_venta;
    private Double precio_proveedor;

    private Producto() {} // hibernate
    private Producto(String codigo_producto, String nombre, String gama, int cantidad_en_stock, double precio_venta) {
        this.codigo_producto = codigo_producto;
        this.nombre = nombre;
        this.gama = gama;
        this.cantidad_en_stock = cantidad_en_stock;
        this.precio_venta = precio_venta;
    }

    public String getCodigo_producto() {
        return codigo_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null) {
            this.nombre = nombre;
        }
    }

    public String getGama() {
        return gama;
    }

    public void setGama(String gama) {
        if (gama != null) {
            this.gama = gama;
        }
    }

    public Optional<String> getDimensiones() {
        return Optional.ofNullable(dimensiones);
    }

    public void setDimensiones(String dimensiones) {
        if (dimensiones != null) {
            this.dimensiones = dimensiones;
        }
    }
 
    public Optional<String> getProveedor() {
        return Optional.ofNullable(proveedor);
    }

    public void setProveedor(String proveedor) {
        if (proveedor != null) {
            this.proveedor = proveedor;
        }
    }

    public Optional<String> getDescripcion() {
        return Optional.ofNullable(descripcion);
    }

    public void setDescripcion(String descripcion) {
        if (descripcion != null) {
            this.descripcion = descripcion;
        }
    }

    public Integer getCantidad_en_stock() {
        return cantidad_en_stock;
    }

    public void setCantidad_en_stock(Integer cantidad_en_stock) {
        if (cantidad_en_stock != null) {
            this.cantidad_en_stock = cantidad_en_stock;
        }
    }

    public Double getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(Double precio_venta) {
        if (precio_venta != null) {
            this.precio_venta = precio_venta;
        }
    }

    public Optional<Double> getPrecio_proveedor() {
        return Optional.ofNullable(precio_proveedor);
    }

    public void setPrecio_proveedor(Double precio_proveedor) {
        if (precio_proveedor != null) {
            this.precio_proveedor = precio_proveedor;
        }
    }

    public static class Builder implements IBuilder<Producto> {
        private Producto producto;

        public Builder(String codigo_producto, String nombre, String gama, int cantidad_en_stock, double precio_venta) {
            producto = new Producto(codigo_producto,
                                    nombre,
                                    gama,
                                    cantidad_en_stock,
                                    precio_venta);
        }

        public Builder con_dimensiones(String dimensiones) {
            producto.dimensiones = dimensiones;
            return this;
        }

        public Builder con_proveedor(String proveedor) {
            producto.proveedor = proveedor;
            return this;
        }

        public Builder con_descripcion(String descripcion) {
            producto.descripcion = descripcion;
            return this;
        }

        public Builder con_precio_proveedor(double precio_proveedor) {
            producto.precio_proveedor = precio_proveedor;
            return this;
        }

        @Override
        public Producto build() throws ExcepcionDatoNoValido {
            boolean datos_necesarios_asignados = true;
            datos_necesarios_asignados &= (producto.codigo_producto != null) 
                                       && (producto.nombre != null)
                                       && (producto.gama != null);
            if (!datos_necesarios_asignados) {
                throw new ExcepcionDatoNoValido("Ninguno de los siguientes campos puede ser null: codigo_producto, nombre, gama.");
            }

            return datos_necesarios_asignados ? producto : null;
        }
    }
}
