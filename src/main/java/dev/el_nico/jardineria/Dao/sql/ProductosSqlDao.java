package dev.el_nico.jardineria.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dev.el_nico.jardineria.dao.IDao;
import dev.el_nico.jardineria.excepciones.ExcepcionDatoNoValido;
import dev.el_nico.jardineria.modelo.Producto;

public class ProductosSqlDao implements IDao<Producto> {

    private Connection conn;

    public ProductosSqlDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Optional<Producto> uno(Object id) {
        try (PreparedStatement stat = conn.prepareStatement("select * from producto where codigo_producto=?;")) {
            stat.setString(1, (String)id);
            ResultSet res = stat.executeQuery();
            if (res.next()) {
                return sacarProductoDeResultSet(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Producto> todos() {
        try (PreparedStatement stat = conn.prepareStatement("select * from producto;")) {
            ResultSet resultados = stat.executeQuery();
            List<Producto> lista = new ArrayList<>();
            while (resultados.next()) {
                sacarProductoDeResultSet(resultados).ifPresent(c -> lista.add(c));
            }
            return lista;
        } catch (SQLException e) {
            e.printStackTrace();    
            return new ArrayList<Producto>(0);
        }
    }

    @Override
    public void guardar(Producto t) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void modificar(Producto t, Object[] params) {
        if (t != null && params != null && params.length > 0) {
            try (PreparedStatement stat = conn.prepareStatement(
                    //                                   1         2       3              4            5              6                    7               8                   9                      10
                    "update producto set codigo_producto=?, nombre=?, gama=?, dimensiones=?, proveedor=?, descripcion=?, cantidad_en_stock=?, precio_venta=?, precio_proveedor=? where codigo_producto=?;")) {
                stat.setString(10, t.getCodigo_producto());

                for (int i = 1; i <= 9; ++i) {
                    Object o = i <= params.length ? params[i - 1] : null;
                    if (o != null) {
                        // nonull
                        if (o instanceof String && ((String)o).equals("sqlnull")) {
                            // queremos nullear el campo
                            stat.setObject(i, Types.NULL);
                        } else {
                            stat.setObject(i, o);
                        }
                    } else {
                        // sinull (valor por defecto)
                        Object def;
                        switch (i) {
                            case 1: def = t.getCodigo_producto(); break;
                            case 2: def = t.getNombre(); break;
                            case 3: def = t.getGama(); break;
                            case 4: def = t.getDimensiones(); break;
                            case 5: def = t.getProveedor(); break;
                            case 6: def = t.getDescripcion(); break;
                            case 7: def = t.getCantidad_en_stock(); break;
                            case 8: def = t.getPrecio_venta(); break;
                            case 9: def = t.getPrecio_proveedor(); break;
                            default: def = null;
                        }

                        stat.setObject(i, def);
                    }
                }

                stat.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void eliminar(Producto t) {
        // TODO Auto-generated method stub

    }

    private static Optional<Producto> sacarProductoDeResultSet(ResultSet sqlQuery) throws SQLException {
        if (sqlQuery != null) {
            String codigo_producto = sqlQuery.getString("codigo_producto");
            String nombre = sqlQuery.getString("nombre");
            String gama = sqlQuery.getString("gama");
            String dimensiones = sqlQuery.getString("dimensiones");
            String proveedor = sqlQuery.getString("proveedor");
            String descripcion = sqlQuery.getString("descripcion");
            int cantidad_en_stock = sqlQuery.getInt("cantidad_en_stock");
            double precio_venta = sqlQuery.getDouble("precio_venta");
            double precio_proveedor = sqlQuery.getDouble("precio_proveedor");

            Producto producto;
            try {
                producto = new Producto.Builder(codigo_producto, nombre, gama, cantidad_en_stock, precio_venta)
                        .con_descripcion(descripcion).con_dimensiones(dimensiones).con_proveedor(proveedor)
                        .con_precio_proveedor(precio_proveedor).build();
            } catch (ExcepcionDatoNoValido e) {
                producto = null;
            }
            return Optional.of(producto);
        } else {
            return Optional.empty();
        }
    }
}
