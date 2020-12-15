package dev.el_nico.jardineria.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            try (PreparedStatement stat = conn.prepareStatement("update producto ? where codigo_producto=?")) {
                stat.setString(2, t.getCodigo_producto());

                String sets = "";
                int i = 0;
                for (Object o : params) {
                    /*
                     * 0 - codigo_producto, 1 - nombre, 2 - gama, 3 - dimensiones, 4 - proveedor,
                     * 5 - descripcion, 6 - cantidad_en_stock, 7 - precio_venta, 8 - precio_proveedor
                     */
                    if (o != null) {
                        String set = "";
                        if (i > 0)
                            set += ", ";
                        set += "set ";
                        switch (i) {
                            case 1: set += "codigo_producto"; break;
                            case 2: set += "nombre"; break;
                            case 3: set += "gama"; break;
                            case 4: set += "proveedor"; break;
                            case 5: set += "descripcion"; break;
                            case 6: set += "cantidad_en_stock"; break;
                            case 7: set += "precio_venta"; break;
                            case 8: set += "precio_proveedor"; break;
                        }
                        set += "=" + o;
                        sets += set;
                    }
                }
                stat.setString(1, sets);

                stat.executeUpdate();

            } catch (SQLException e) {

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
