package dev.el_nico.jardineria.modelo;

import java.util.Optional;

import dev.el_nico.jardineria.util.AbstractBuilder;
import dev.el_nico.jardineria.excepciones.ExcepcionDatoNoValido;

/**
 * Objeto que representa a uno de los clientes
 * según el script de la BBDD «Jardinería». Este
 * script declara los siguientes campos para la
 * tabla «clients»:
 * 
 * <ul>
 * <li>codigo_cliente INTEGER NOT NULL</li>
 * <li>nombre_cliente VARCHAR(50) NOT NULL</li>
 * <li>nombre_contacto VARCHAR(30) DEFAULT NULL</li>
 * <li>apellido_contacto VARCHAR(30) DEFAULT NULL</li>
 * <li>telefono VARCHAR(15) NOT NULL</li>
 * <li>fax VARCHAR(15) NOT NULL</li>
 * <li>linea_direccion1 VARCHAR(50) NOT NULL</li>
 * <li>linea_direccion2 VARCHAR(50) DEFAULT NULL</li>
 * <li>ciudad VARCHAR(50) NOT NULL</li>
 * <li>region VARCHAR(50) DEFAULT NULL</li>
 * <li>pais VARCHAR(50) DEFAULT NULL</li>
 * <li>codigo_postal VARCHAR(10) DEFAULT NULL</li>
 * <li>codigo_empleado_rep_ventas INTEGER DEFAULT NULL</li>
 * <li>limite_credito NUMERIC(15,2) DEFAULT NULL</li>
 * </ul>
 */
public class Cliente {
    private int codigo;
    private String nombre;
    private Contacto contacto;
    private Domicilio domicilio;

    private int cod_empl_rep_ventas;
    private double limite_credito;
    
    private Cliente(int codigo, String nombre, Contacto contacto,  Domicilio domicilio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.contacto = contacto;
        this.domicilio = domicilio;
    }

    /** Devuelve el código de cliente. */
    public int get_codigo() {
        return codigo;
    }

    /** 
     * Devuelve el código del empleado representante de 
     * ventas, que será 0 si no existe.
     */
    public int get_cod_empl_rep_ventas() {
        return cod_empl_rep_ventas;
    }

    /**
     * Devuelve el límite de crédito, que será 0.0 si no
     * hay ninguno establecido.
     */
    public double get_limite_credito() {
        return limite_credito;
    }

    /** Devuelve el nombre. Nunca es null. */
    public String get_nombre() {
        return nombre;
    }

    /** Devuelve los datos de contacto. Nunca es null. */
    public Contacto get_contacto() {
        return contacto;
    }

    /** Devuelve los datos de domicilio. Nunca es null. */
    public Domicilio get_domicilio() {
        return domicilio;
    }

    /**
     * Representa los datos de contacto de un cliente.
     * Opcionalmente contendrá nombre y apellido de contacto,
     * pero siempre teléfono y fax.
     */
    public static class Contacto {
        private Optional<String> nombre;
        private Optional<String> apellido;
        private String telefono;
        private String fax;

        private Contacto(String telefono, String fax) {
            this.telefono = telefono;
            this.fax = fax;
            nombre = Optional.empty();
            apellido = Optional.empty();
        }

        /** Nombre del contacto del cliente. */
        public Optional<String> nombre() {
            return nombre;
        }

        /** Apellido del contacto del cliente. */
        public Optional<String> apellido() {
            return apellido;
        }

        /** Teléfono del cliente. Nunca es null. */
        public String telefono() {
            return telefono;
        }

        /** Fax del cliente. Nunca es null. */
        public String fax() {
            return fax;
        }
    }

    /**
     * Representa los datos de domicilio de un cliente.
     * Contienen, obligatoriamente, una línea de dirección
     * y la ciudad. Además, pueden tener una segunda línea
     * de dirección, un código postal (cp), una región y
     * un país.
     */
    public static class Domicilio {
        private String direccion1;
        private String ciudad;

        private Optional<String> direccion2;
        private Optional<String> region;
        private Optional<String> pais;
        private Optional<String> cp;

        private Domicilio(String direccion1, String ciudad) {
            this.direccion1 = direccion1;
            this.ciudad = ciudad;
            direccion2 = Optional.empty();
            region = Optional.empty();
            pais = Optional.empty();
            cp = Optional.empty();
        }

        /** 
         * Devuelve la primera línea de dirección del domicilio 
         * del cliente. Nunca es null.
         */
        public String direccion1() {
            return direccion1;
        }

        /** Devuelve la ciudad del cliente. Nunca es null. */
        public String ciudad() {
            return ciudad;
        }

        /** 
         * Devuelve la segunda línea de dirección del domicilio
         * del cliente.
         */
        public Optional<String> direccion2() {
            return direccion2;
        }

        /** Devuelve la región del domicilio del cliente. */
        public Optional<String> region() {
            return region;
        }

        /** Devuelve el país del domicilio del cliente. */
        public Optional<String> pais() {
            return pais;
        }

        /** Devuelve el código postal del domicilio del cliente */
        public Optional<String> cp() {
            return cp;
        }
    }

    /** Clase para buildear instancias válidas de Cliente. */
    public static class Builder extends AbstractBuilder<Cliente> {
        private Cliente cliente;

        /**
         * Constructor para asegurar la validez de un Cliente
         * obtenido por otros medios, como puede ser una 
         * deserialización desde un archivo json.
         * @param otro Un cliente que no se sabe si es válido.
         */
        public Builder(Cliente otro) {
            cliente = otro;
            
            // En este caso, solo hace falta rellenar los Optional
            // con el valor Optional.empty() (porque si no serían 
            // null)
            if (cliente.contacto != null) {
                if (cliente.contacto.nombre == null) {
                    cliente.contacto.nombre = Optional.empty();
                }
                if (cliente.contacto.apellido == null) {
                    cliente.contacto.apellido = Optional.empty();
                }
            }
            if (cliente.domicilio != null) {
                if (cliente.domicilio.direccion2 == null) {
                    cliente.domicilio.direccion2 = Optional.empty();
                }
                if (cliente.domicilio.region == null) {
                    cliente.domicilio.region = Optional.empty();
                }
                if (cliente.domicilio.pais == null) {
                    cliente.domicilio.pais = Optional.empty();
                }
                if (cliente.domicilio.cp == null) {
                    cliente.domicilio.cp = Optional.empty();
                }
            }
        }

        /**
         * Toma los datos obligatorios para formar un cliente válido.
         * Crea un Builder con esos datos. Notar que los parámetros aquí
         * aportados pueden ser null, y será la función «build» quien
         * lance la excepción.
         */
        public Builder(int codigo, String nombre, String telefono, 
                       String fax, String direccion1, String ciudad) {
            cliente = new Cliente(codigo, 
                                  nombre, 
                                  new Contacto(telefono, fax), 
                                  new Domicilio(direccion1, ciudad));
        }

        /** Para aportar un nombre de contacto al builder. */
        public Builder con_nombre_de_contacto(String nombre) {
            cliente.contacto.nombre = Optional.of(nombre);
            return this;
        }

        /** Para aportar un apellido de contacto al builder. */
        public Builder con_apellido_de_contacto(String apellido) {
            cliente.contacto.apellido = Optional.of(apellido);
            return this;
        }

        /** Para aportar un límite de crédito al builder. */
        public Builder con_limite_credito(double limite_credito) {
            cliente.limite_credito = limite_credito;
            return this;
        }

        /** Para aportar un código de empleado rep. ventas al builder. */
        public Builder con_cod_empl_rep_ventas(int cod_empl_rep_ventas) {
            cliente.cod_empl_rep_ventas = cod_empl_rep_ventas;
            return this;
        }

        /** 
         * Para aportar una segunda línea de dirección al domicilio
         * del builder.
         */
        public Builder con_linea_direccion2(String direccion2) {
            cliente.domicilio.direccion2 = Optional.of(direccion2);
            return this;
        }

        /** Para aportar una región al domicilio del builder. */
        public Builder con_region(String region) {
            cliente.domicilio.region = Optional.of(region);
            return this;
        }

        /** Para aportar un país al domicilio del builder. */
        public Builder con_pais(String pais) {
            cliente.domicilio.pais = Optional.of(pais);
            return this;
        }

        /** Para aportar un código postal al domicilio del builder. */
        public Builder con_codigo_postal(String cp) {
            cliente.domicilio.cp = Optional.of(cp);
            return this;
        }

        /**
         * Se asegura de que ninguno de los campos «NOT NULL» del
         * script SQL tienen su valor por defecto (null, 0 o 0.0).
         * Si alguno lo tiene, lanza una excepción y devuelve null.
         * Si no, devuelve un cliente válido con todos los datos 
         * aportados al builder.
         * @return Un cliente válido o null si alguno de los datos
         * es incorrecto.
         */
        public Cliente build() throws ExcepcionDatoNoValido {
            boolean valido = true;
            valido &= (cliente.codigo != 0) &&
                      (cliente.nombre != null) &&
                      (cliente.contacto.telefono != null) &&
                      (cliente.contacto.fax != null) &&
                      (cliente.domicilio.direccion1 != null) &&
                      (cliente.domicilio.ciudad != null);
            if (!valido) {
                throw new ExcepcionDatoNoValido("Ninguno de los siguientes campos puede tener " +
                                                "su valor por defecto: codigo, nombre, telefono, " +
                                                "fax, direccion1, ciudad.");
            }
            return valido ? cliente : null;
        }
    }
}
