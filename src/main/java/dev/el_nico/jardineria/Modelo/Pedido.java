package dev.el_nico.jardineria.modelo;

import java.util.Calendar;
import java.util.Optional;

import dev.el_nico.jardineria.excepciones.ExcepcionDatoNoValido;
import dev.el_nico.jardineria.util.IBuilder;


/**
 * Objeto que representa a uno de los pedidos
 * según el script de la BBDD «Jardinería». Este
 * script declara los siguientes campos para la
 * tabla «pedido»:
 * 
 * <ul>
 * <li>codigo_pedido INTEGER NOT NULL</li>
 * <li>fecha_pedido date NOT NULL</li>
 * <li>fecha_esperada date NOT NULL</li>
 * <li>fecha_entrega date DEFAULT NULL</li>
 * <li>estado VARCHAR(15) NOT NULL</li>
 * <li>comentarios TEXT</li>
 * <li>codigo_cliente INTEGER NOT NULL</li>
 * </ul>
 */
public class Pedido {
    /** 
     * Demora mínima, en días, que puede tomar un
     * pedido en ser enviado.
     */
    private static final int DEMORA_MINIMA = 3;

    private int codigo;
    private Fecha fecha;
    private String estado;
    private Optional<String> comentarios;
    private int codigo_cliente;

    private Pedido(int codigo, Fecha fecha, String estado, int codigo_cliente) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.estado = estado;
        this.codigo_cliente = codigo_cliente;
    }

    /** Devuelve el código de pedido. */
    public int get_codigo() {
        return codigo;
    }

    /** Devuelve la fecha del pedido. Nunca es null. */
    public Fecha get_fecha() {
        return fecha;
    }

    /** Devuelve el estado del pedido. Nunca es null. */
    public String get_estado() {
        return estado;
    }

    /** Devuelve los comentarios del pedido. */
    public Optional<String> get_comentarios() {
        return comentarios;
    }

    /** Devuelve el código de cliente del pedido. */
    public int get_codigo_cliente() {
        return codigo_cliente;
    }

    /** Agrupa las fechas de un pedido. */
    public static class Fecha {
        private Calendar pedido;
        private Calendar esperada;
        private Optional<Calendar> entrega;

        /**
         * Construye una nueva Fecha asignando a pedido
         * la fecha actual. Asigna a esperada la fecha
         * actual más demora_esperada días. Si demora_esperada
         * es menor que DEMORA_MINIMA, se asigna a la fecha
         * esperada el valor del pedido + DEMORA_MINIMA días.
         * @param demora_esperada Días que se espera que
         * tarde el pedido en llegar.
         */
        private Fecha(int demora_esperada) {
            this.pedido = Calendar.getInstance();
            (this.esperada = (Calendar)pedido.clone()).add(Calendar.DAY_OF_MONTH, demora_esperada);
        }

        private Fecha(Calendar pedido, Calendar esperada) {
            this.pedido = pedido;
            this.esperada = esperada;
        }

        /** La fecha en que se hizo el pedido. Nunca es null. */
        public Calendar pedido() {
            return pedido;
        }

        /** La fecha esperada de entrega del pedido. Nunca es null. */
        public Calendar esperada() {
            return esperada;
        }

        /** La fecha de entrega real del pedido. */
        public Optional<Calendar> entrega() {
            return entrega;
        }
    }

    /** Clase para buildear instancias válidas de Pedido. */
    public static class Builder implements IBuilder<Pedido> {
        private Pedido pedido;

        /** 
         * Para asegurar la validez de un Pedido deserializado,
         * que puede perfectamente contener datos sin sentido.
         * @param otro Un pedido que no se sabe si es válido.
         */
        public Builder(Pedido otro) {
            pedido = otro;
        }

        /**
         * Toma los datos obligatorios para formar un pedido válido.
         * Crea un builder con esos datos. Notar que los parámetros
         * pueden ser null, y que es en la función build donde se
         * comprueba la validez de estos.
         * @param codigo El código del pedido.
         * @param dias_demora Días que se espera que tarde en llegar el pedido.
         * @param estado No sé qué es esto.
         * @param codigo_cliente El código del cliente que realizó el pedido.
         */
        public Builder(int codigo, int dias_demora, String estado, int codigo_cliente) {
            this.pedido = new Pedido(codigo, new Fecha(dias_demora), estado, codigo_cliente);
        }

        public Builder(int codigo, Calendar pedido, Calendar esperada, String estado, int codigo_cliente) {
            this.pedido = new Pedido(codigo, new Fecha(pedido, esperada), estado, codigo_cliente);
        }

        /** Añade fecha de entrega al builder. */
        public Builder con_fecha_de_entrega(Calendar entrega) {
            this.pedido.fecha.entrega = Optional.ofNullable(entrega);
            return this;
        }

        /** Añade comentarios al builder. */
        public Builder con_comentarios(String comentarios) {
            pedido.comentarios = Optional.ofNullable(comentarios);
            return this;
        }

        /** 
         * Se asegura de que ninguno de los campos «NOT NULL» en
         * la tabla «pedido» tienen aquí su valor por defecto (null,
         * 0 o 0.0). Si alguno de estos es así, lanza una excepción y
         * devuelve null; si no, devuelve un pedido válido con los 
         * datos aportados al builder.
         * @return Un pedido válido o null si alguno de los datos es
         * incorrecto.
         */
        public Pedido build() throws ExcepcionDatoNoValido {
            boolean valido = true;
            valido &= (pedido.codigo != 0) && 
                      (pedido.fecha.pedido != null) && 
                      (pedido.fecha.esperada != null) &&
                      (pedido.estado != null) &&
                      (pedido.codigo_cliente != 0);
            if (!valido) {
                throw new ExcepcionDatoNoValido("Ninguno de los siguientes campos puede tener " +
                                                "su valor por defecto: codigo, codigo_cliente, " +
                                                "fecha_pedido, fecha_esperada, estado.");
            }

            // Se asegura de que la fecha de espera es por lo menos
            // tres días posterior a la fecha del pedido.
            Calendar tres_dias_despues_del_pedido = (Calendar)pedido.fecha.pedido.clone();
            tres_dias_despues_del_pedido.add(Calendar.DAY_OF_MONTH, DEMORA_MINIMA);
            if (pedido.fecha.esperada.before(tres_dias_despues_del_pedido)) {
                valido = false;
                throw new ExcepcionDatoNoValido("La fecha esperada debe ser, por lo menos, " +
                                                "tres días posterior a la fecha del pedido");
            }
            
            return valido ? pedido : null;
        }
    }
}
