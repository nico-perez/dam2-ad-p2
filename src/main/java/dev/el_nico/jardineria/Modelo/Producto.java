package dev.el_nico.jardineria.modelo;

import java.util.Optional;

import dev.el_nico.jardineria.excepciones.ExcepcionDatoNoValido;
import dev.el_nico.jardineria.util.IBuilder;

public class Producto {
    private String codigo_producto;
    private String nombre;
    private String gama;
    private Optional<String> dimensiones;
    private Optional<String> proveedor;
    private Optional<String> descripcion;
    private int cantidad_en_stock;
    private double precio_venta;
    private Optional<Double> precio_proveedor;

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

    public String getGama() {
        return gama;
    }

    public Optional<String> getDimensiones() {
        return dimensiones;
    }

    public Optional<String> getProveedor() {
        return proveedor;
    }

    public Optional<String> getDescripcion() {
        return descripcion;
    }

    public int getCantidad_en_stock() {
        return cantidad_en_stock;
    }

    public double getPrecio_venta() {
        return precio_venta;
    }

    public Optional<Double> getPrecio_proveedor() {
        return precio_proveedor;
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
            producto.dimensiones = Optional.ofNullable(dimensiones);
            return this;
        }

        public Builder con_proveedor(String proveedor) {
            producto.proveedor = Optional.ofNullable(proveedor);
            return this;
        }

        public Builder con_descripcion(String descripcion) {
            producto.descripcion = Optional.ofNullable(descripcion);
            return this;
        }

        public Builder con_precio_proveedor(double precio_proveedor) {
            producto.precio_proveedor = Optional.of(precio_proveedor);
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
