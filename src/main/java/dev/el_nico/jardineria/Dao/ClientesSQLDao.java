package dev.el_nico.jardineria.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dev.el_nico.jardineria.modelo.Cliente;
import dev.el_nico.jardineria.modelo.TipoDocumento;

public class ClientesSQLDao implements IDataAccessObject<Cliente> {

    private Connection conn;

    public ClientesSQLDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Optional<Cliente> uno(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Cliente> todos() {
        try {
            Statement todos = conn.createStatement();
            listaDeClientesDesdeResultSet(todos.executeQuery("select * from cliente;"));
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void guardar(Cliente t) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void modificar(Cliente t, String params) {
        // TODO Auto-generated method stub

    }

    @Override
    public void eliminar(Cliente t) {
        // TODO Auto-generated method stub

    }
    
    private List<Cliente> listaDeClientesDesdeResultSet(ResultSet sqlQuery) throws SQLException {
        
        if (sqlQuery != null) {

            ArrayList<Cliente> lista = new ArrayList<Cliente>();

            int codigo = sqlQuery.getInt("codigo_cliente");
            String nombre = sqlQuery.getString("nombre_cliente");
            String cto_nombre = sqlQuery.getString("nombre_contacto");
            String cto_apellido = sqlQuery.getString("nombre_apellido");

            return lista;
        } else {
            return new ArrayList<Cliente>(0);
        }

        int codigo;
        String nombre;
        
        // Contacto
        Optional<String> cto_nombre;
        Optional<String> cto_apellido;
        String cto_telefono;
        String cto_fax;

        // Domicilio
        String dmc_direccion1;
        String dmc_ciudad;
        Optional<String> dmc_direccion2;
        Optional<String> dmc_region;
        Optional<String> dmc_pais;
        Optional<String> dmc_cp;
    
        Optional<Integer> cod_empl_rep_ventas;
        Optional<Double> limite_credito;
        Optional<TipoDocumento> tipo_doc;
        Optional<String> dni;
        Optional<String> email;
        Optional<String> contrasena;


    }
}
