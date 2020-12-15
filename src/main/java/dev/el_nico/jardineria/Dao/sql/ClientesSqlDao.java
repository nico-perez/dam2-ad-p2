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
import dev.el_nico.jardineria.excepciones.ExcepcionFormatoIncorrecto;
import dev.el_nico.jardineria.modelo.Cliente;

public class ClientesSqlDao implements IDao<Cliente> {

    // package access para que pueda acceder PedidosSQLDao
    Connection conn;

    public ClientesSqlDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Optional<Cliente> uno(Object id) {
        try (PreparedStatement stat = conn.prepareStatement("select * from cliente where codigo_cliente=?;")) {
            stat.setInt(1, (int)id);
            ResultSet res = stat.executeQuery();
            if (res.next()) {
                return sacarClienteDeResultSet(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Cliente> todos() {
        try (PreparedStatement stat = conn.prepareStatement("select * from cliente;")) {
            ResultSet resultados = stat.executeQuery();
            List<Cliente> lista = new ArrayList<>();
            while (resultados.next()) {
                sacarClienteDeResultSet(resultados).ifPresent(c -> lista.add(c));
            }
            return lista;

        } catch (SQLException e) {
            e.printStackTrace();    
            return new ArrayList<Cliente>(0);
        }
    }

    @Override
    public void guardar(Cliente t) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void modificar(Cliente t, Object[] params) {
        // TODO Auto-generated method stub

    }

    @Override
    public void eliminar(Cliente t) {
        // TODO Auto-generated method stub

    }

    /**
     * Saca el cliente al que está apuntando el ResultSet, y si existe y todos los datos son correctos, 
     * devuelve un Optional de ese cliente. Si el ResultSet no está apuntando a ningún Cliente, lanza una
     * excepción. Si el ResultSet es null o alguno de los datos en la base de datos es incorrecto, devuelve
     * un Optional vacío.
     * @param sqlQuery
     * @return Un Optional con un cliente válido, o un Optional vacío.
     * @throws SQLException Si no se ha llamado a next() antes de invocar a esta función, o nosequé otras
     * movidas le puedan disgustar a esto.
     */
    private static Optional<Cliente> sacarClienteDeResultSet(ResultSet sqlQuery) throws SQLException {

        if (sqlQuery != null) {
            int codigo = sqlQuery.getInt("codigo_cliente");
            String nombre = sqlQuery.getString("nombre_cliente");
            String cto_nombre = sqlQuery.getString("nombre_contacto");
            String cto_apellido = sqlQuery.getString("apellido_contacto");
            String telefono = sqlQuery.getString("telefono");
            String fax = sqlQuery.getString("fax");
            String linea_dir1 = sqlQuery.getString("linea_direccion1");
            String linea_dir2 = sqlQuery.getString("linea_direccion2");
            String ciudad = sqlQuery.getString("ciudad");
            String region = sqlQuery.getString("region");
            String pais = sqlQuery.getString("pais");
            String codigo_postal = sqlQuery.getString("codigo_postal");
            int rep_ventas = sqlQuery.getInt("codigo_empleado_rep_ventas");
            double lim_cred = sqlQuery.getDouble("limite_credito");

            Cliente cliente;
            try {
                cliente = new Cliente.Builder(codigo, nombre, telefono, fax, linea_dir1, ciudad)
                        .con_nombre_de_contacto(cto_nombre).con_apellido_de_contacto(cto_apellido)
                        .con_linea_direccion2(linea_dir2).con_region(region).con_pais(pais)
                        .con_codigo_postal(codigo_postal).con_cod_empl_rep_ventas(rep_ventas)
                        .con_limite_credito(lim_cred).build();
            } catch (ExcepcionDatoNoValido | ExcepcionFormatoIncorrecto e) {
                cliente = null;
            }

            return Optional.ofNullable(cliente);
        } else {
            return Optional.empty();
        }
    }
}
