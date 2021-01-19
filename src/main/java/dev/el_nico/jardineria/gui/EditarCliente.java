/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.el_nico.jardineria.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang3.StringUtils;

import dev.el_nico.jardineria.excepciones.ExcepcionCodigoYaExistente;
import dev.el_nico.jardineria.excepciones.ExcepcionDatoNoValido;
import dev.el_nico.jardineria.excepciones.ExcepcionFormatoIncorrecto;
import dev.el_nico.jardineria.modelo.Cliente;
import dev.el_nico.jardineria.modelo.TipoDocumento;

/**
 *
 * @author NICO2DAM
 */
public class EditarCliente extends javax.swing.JDialog {

    /**
     *
     */
    private static final long serialVersionUID = 8778139352579750015L;
    private Cliente C = null;
    private Integer rowCliente = null;
    
    private Aplicacion app;
    private ArrayList<JTextField> inputs = new ArrayList<>(17);
    
    private boolean codigoBien, nombreBien, telefonoBien,
                    faxBien, direccion1Bien, ciudadBien,
                    documentoBien, emailBien, contrasenaBien;
    
    /**
     * Creates new form NuevoCliente
     */
    public EditarCliente(Aplicacion app, Integer rowCliente) {
        super(app, true);

        
        
        this.setUndecorated(true);
        this.getRootPane().setBorder(new LineBorder(new Color(204,204,204)));
        initComponents();
        try {
            this.setIconImage(ImageIO.read(new File("src/main/resources/img/icono-jardineria.png")));
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } 
        //panel_datos_contacto.setVisible(false);
        //panel_datos_domicilio.setVisible(false);
        this.setMinimumSize(new Dimension(450, 0));
        this.pack();
        ButtonGroup bg = new ButtonGroup();
        bg.add(inputNA);
        bg.add(inputDNI);
        bg.add(inputNIE);
        
        inputs.addAll(
            Arrays.asList(
                inputCodigo,
                inputNombre,
                inputContactoNombre,
                inputContactoApellido,
                inputContactoTelefono,
                inputContactoFax,
                inputDomicilioDireccion1,
                inputDomicilioDireccion2,
                inputDomicilioCP,
                inputDomicilioCiudad,
                inputDomicilioRegion,
                inputDomicilioPais,
                inputCodRepVentas,
                inputLimiteCredito,
                inputDocumentoContenido,
                inputEmail,
                inputContrasena
            )
        );
        
        setLocationRelativeTo(app);
        
        etiquetaCodigo.setForeground(colorError);
        etiquetaNombre.setForeground(colorError);
        etiquetaTelefono.setForeground(colorError);
        etiquetaFax.setForeground(colorError);
        etiquetaDireccion1.setForeground(colorError);
        etiquetaCiudad.setForeground(colorError);       
        
        botonMinim.setVisible(false);

        
        documentoBien = emailBien = contrasenaBien = true;
        
        this.app = app;
        if (app != null) { 
            app.editarClienteExiste = true;
            this.rowCliente = rowCliente;
            if (rowCliente != null) {
                this.C = app.daos.clientes().uno(rowCliente).orElse(null);
                if (C != null) {
                    inputCodigo.setText(Integer.toString(C.get_codigo()));
                    inputNombre.setText(C.get_nombre());

                    inputContactoNombre.setText(C.get_contacto().nombre().orElse(null));
                    inputContactoApellido.setText(C.get_contacto().apellido().orElse(null));
                    inputContactoTelefono.setText(C.get_contacto().telefono());
                    inputContactoFax.setText(C.get_contacto().fax());

                    inputDomicilioDireccion1.setText(C.get_domicilio().direccion1());
                    inputDomicilioDireccion2.setText(C.get_domicilio().direccion2().orElse(null));
                    inputDomicilioCP.setText(C.get_domicilio().cp().orElse(null));
                    inputDomicilioCiudad.setText(C.get_domicilio().ciudad());
                    inputDomicilioPais.setText(C.get_domicilio().pais().orElse(null));
                    inputDomicilioRegion.setText(C.get_domicilio().region().orElse(null));

                    inputCodRepVentas.setText((C.get_cod_empl_rep_ventas() != null && C.get_cod_empl_rep_ventas().isPresent()) ? Integer.toString(C.get_cod_empl_rep_ventas().get()) : null);
                    inputLimiteCredito.setText((C.get_limite_credito() != null && C.get_limite_credito().isPresent()) ? Double.toString(C.get_limite_credito().get()) : null);

                    if (C.get_tipo_documento() != null && C.get_tipo_documento().isPresent()) {
                        if (C.get_tipo_documento().get() == TipoDocumento.DNI) {
                            inputDNI.setSelected(true);
                            inputNIE.setSelected(false);
                            inputNA.setSelected(false);
                        } else {
                            inputDNI.setSelected(false);
                            inputNIE.setSelected(true);
                            inputNA.setSelected(false);
                        }
                        inputDocumentoContenido.setText(C.get_dni().orElse(null));
                    } else {
                        inputDNI.setSelected(false);
                        inputNIE.setSelected(false);
                        inputNA.setSelected(true);
                    }

                    inputEmail.setText(C.get_email() != null ? C.get_email().orElse(null) : null);
                    inputContrasena.setText(C.get_contrasena() != null ? C.get_contrasena().orElse(null) : null);

                    codigoBien = nombreBien = telefonoBien = faxBien = direccion1Bien = ciudadBien = true;
                    updatearMsjError();
                }
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("all")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        etiquetaCodigo = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        inputCodigo = new javax.swing.JFormattedTextField();
        jPanel3 = new javax.swing.JPanel();
        etiquetaNombre = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        inputNombre = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        panel_datos_contacto = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        etiquetaNombreContacto = new javax.swing.JLabel();
        inputContactoNombre = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        etiquetaApellido = new javax.swing.JLabel();
        inputContactoApellido = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        etiquetaFax = new javax.swing.JLabel();
        inputContactoFax = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        etiquetaTelefono = new javax.swing.JLabel();
        inputContactoTelefono = new javax.swing.JTextField();
        jToggleButton1 = new javax.swing.JToggleButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jToggleButton2 = new javax.swing.JToggleButton();
        panel_datos_domicilio = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        etiquetaDireccion2 = new javax.swing.JLabel();
        inputDomicilioDireccion2 = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        etiquetaDireccion1 = new javax.swing.JLabel();
        inputDomicilioDireccion1 = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        etiquetaCP = new javax.swing.JLabel();
        inputDomicilioCP = new javax.swing.JTextField();
        jPanel20 = new javax.swing.JPanel();
        etiquetaCiudad = new javax.swing.JLabel();
        inputDomicilioCiudad = new javax.swing.JTextField();
        jPanel21 = new javax.swing.JPanel();
        etiquetaPais = new javax.swing.JLabel();
        inputDomicilioPais = new javax.swing.JTextField();
        jPanel23 = new javax.swing.JPanel();
        etiquetaRegion = new javax.swing.JLabel();
        inputDomicilioRegion = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        etiquetaRepVentas = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        inputCodRepVentas = new javax.swing.JFormattedTextField();
        jPanel2 = new javax.swing.JPanel();
        etiquetaLimCredito = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        inputLimiteCredito = new javax.swing.JFormattedTextField();
        jPanel7 = new javax.swing.JPanel();
        etiquetaDocumento = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        etiquetaTipoDocumento = new javax.swing.JLabel();
        inputNA = new javax.swing.JRadioButton();
        inputNIE = new javax.swing.JRadioButton();
        inputDNI = new javax.swing.JRadioButton();
        panelContenidoDocumento = new javax.swing.JPanel();
        etiquetaContenidoDocumento = new javax.swing.JLabel();
        inputDocumentoContenido = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        etiquetaEmail = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        inputEmail = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        etiquetaContrasena = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        inputContrasena = new javax.swing.JPasswordField();
        panelInfo = new javax.swing.JPanel();
        scrollTexto = new javax.swing.JScrollPane();
        infoTexto = new javax.swing.JTextArea();
        botonAceptar = new javax.swing.JButton();
        botonCancelar = new javax.swing.JButton();
        barraTitulo = new javax.swing.JPanel();
        iconoYNombreVentana = new javax.swing.JLabel();
        fondoBotonCerrar = new javax.swing.JPanel();
        botonCerrar = new javax.swing.JButton();
        fondoBotonMinim = new javax.swing.JPanel();
        botonMinim = new javax.swing.JButton();
        botonReset = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Jardinería - Añadir nuevo cliente");
        setBackground(new java.awt.Color(153, 204, 0));
        setForeground(new java.awt.Color(254, 242, 227));
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        etiquetaCodigo.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        etiquetaCodigo.setForeground(new java.awt.Color(102, 102, 102));
        etiquetaCodigo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/hashtag-solid.png"))); // NOI18N
        etiquetaCodigo.setText("Código");

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));

        inputCodigo.setBorder(null);
        inputCodigo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        inputCodigo.setFont(new java.awt.Font("Calibri Light", 0, 12)); // NOI18N
        inputCodigo.setOpaque(false);
        inputCodigo.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                inputCodigoCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(inputCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(inputCodigo, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(etiquetaCodigo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(etiquetaCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        etiquetaNombre.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        etiquetaNombre.setForeground(new java.awt.Color(102, 102, 102));
        etiquetaNombre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/signature-solid.png"))); // NOI18N
        etiquetaNombre.setText("Nombre");

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));

        inputNombre.setFont(new java.awt.Font("Calibri Light", 0, 12)); // NOI18N
        inputNombre.setBorder(null);
        inputNombre.setNextFocusableComponent(inputContactoNombre);
        inputNombre.setOpaque(false);
        inputNombre.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                inputNombreCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(inputNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(inputNombre)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(etiquetaNombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(etiquetaNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
            .addComponent(jPanel24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel4.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/address-book-solid.png"))); // NOI18N
        jLabel4.setText("Contacto [...]");

        panel_datos_contacto.setBackground(new java.awt.Color(204, 255, 255));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        etiquetaNombreContacto.setBackground(new java.awt.Color(184, 201, 217));
        etiquetaNombreContacto.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        etiquetaNombreContacto.setForeground(new java.awt.Color(153, 153, 153));
        etiquetaNombreContacto.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        etiquetaNombreContacto.setText("Nombre");

        inputContactoNombre.setFont(new java.awt.Font("Calibri Light", 0, 12)); // NOI18N
        inputContactoNombre.setBorder(null);
        inputContactoNombre.setOpaque(false);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addComponent(etiquetaNombreContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inputContactoNombre)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(etiquetaNombreContacto, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
            .addComponent(inputContactoNombre)
        );

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        etiquetaApellido.setBackground(new java.awt.Color(221, 229, 237));
        etiquetaApellido.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        etiquetaApellido.setForeground(new java.awt.Color(153, 153, 153));
        etiquetaApellido.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        etiquetaApellido.setText("Apellido");

        inputContactoApellido.setFont(new java.awt.Font("Calibri Light", 0, 12)); // NOI18N
        inputContactoApellido.setBorder(null);
        inputContactoApellido.setOpaque(false);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addComponent(etiquetaApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inputContactoApellido)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(inputContactoApellido)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(etiquetaApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        etiquetaFax.setBackground(new java.awt.Color(221, 229, 237));
        etiquetaFax.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        etiquetaFax.setForeground(new java.awt.Color(153, 153, 153));
        etiquetaFax.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        etiquetaFax.setText("Fax");

        inputContactoFax.setFont(new java.awt.Font("Calibri Light", 0, 12)); // NOI18N
        inputContactoFax.setBorder(null);
        inputContactoFax.setNextFocusableComponent(inputDomicilioDireccion1);
        inputContactoFax.setOpaque(false);
        inputContactoFax.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                inputContactoFaxCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addComponent(etiquetaFax, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inputContactoFax)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(inputContactoFax, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(etiquetaFax, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));

        etiquetaTelefono.setBackground(new java.awt.Color(221, 229, 237));
        etiquetaTelefono.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        etiquetaTelefono.setForeground(new java.awt.Color(153, 153, 153));
        etiquetaTelefono.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        etiquetaTelefono.setText("Teléfono");

        inputContactoTelefono.setFont(new java.awt.Font("Calibri Light", 0, 12)); // NOI18N
        inputContactoTelefono.setBorder(null);
        inputContactoTelefono.setOpaque(false);
        inputContactoTelefono.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                inputContactoTelefonoCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addComponent(etiquetaTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inputContactoTelefono)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(inputContactoTelefono)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(etiquetaTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_datos_contactoLayout = new javax.swing.GroupLayout(panel_datos_contacto);
        panel_datos_contacto.setLayout(panel_datos_contactoLayout);
        panel_datos_contactoLayout.setHorizontalGroup(
            panel_datos_contactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel_datos_contactoLayout.setVerticalGroup(
            panel_datos_contactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_datos_contactoLayout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jToggleButton1.setBackground(new java.awt.Color(82, 131, 143));
        jToggleButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/caret-down-solid-rot90.png"))); // NOI18N
        jToggleButton1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 6, 1, 6));
        jToggleButton1.setBorderPainted(false);
        jToggleButton1.setContentAreaFilled(false);
        jToggleButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jToggleButton1.setFocusPainted(false);
        jToggleButton1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/caret-down-solid.png"))); // NOI18N
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jToggleButton1))
            .addComponent(panel_datos_contacto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToggleButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_datos_contacto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel5.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/house-user-solid.png"))); // NOI18N
        jLabel5.setText("Domicilio [...]");

        jToggleButton2.setBackground(new java.awt.Color(200, 222, 216));
        jToggleButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/caret-down-solid-rot90.png"))); // NOI18N
        jToggleButton2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 6, 1, 6));
        jToggleButton2.setBorderPainted(false);
        jToggleButton2.setContentAreaFilled(false);
        jToggleButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jToggleButton2.setFocusPainted(false);
        jToggleButton2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/caret-down-solid.png"))); // NOI18N
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        panel_datos_domicilio.setBackground(new java.awt.Color(153, 153, 255));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));

        etiquetaDireccion2.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        etiquetaDireccion2.setForeground(new java.awt.Color(153, 153, 153));
        etiquetaDireccion2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        etiquetaDireccion2.setText("Dirección 2");

        inputDomicilioDireccion2.setFont(new java.awt.Font("Calibri Light", 0, 12)); // NOI18N
        inputDomicilioDireccion2.setBorder(null);
        inputDomicilioDireccion2.setOpaque(false);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(etiquetaDireccion2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inputDomicilioDireccion2)
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(etiquetaDireccion2, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
            .addComponent(inputDomicilioDireccion2)
        );

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));

        etiquetaDireccion1.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        etiquetaDireccion1.setForeground(new java.awt.Color(153, 153, 153));
        etiquetaDireccion1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        etiquetaDireccion1.setText("Dirección 1");

        inputDomicilioDireccion1.setFont(new java.awt.Font("Calibri Light", 0, 12)); // NOI18N
        inputDomicilioDireccion1.setBorder(null);
        inputDomicilioDireccion1.setOpaque(false);
        inputDomicilioDireccion1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                inputDomicilioDireccion1CaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(etiquetaDireccion1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inputDomicilioDireccion1)
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(inputDomicilioDireccion1)
            .addComponent(etiquetaDireccion1, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));

        etiquetaCP.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        etiquetaCP.setForeground(new java.awt.Color(153, 153, 153));
        etiquetaCP.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        etiquetaCP.setText("C.P.");

        inputDomicilioCP.setFont(new java.awt.Font("Calibri Light", 0, 12)); // NOI18N
        inputDomicilioCP.setBorder(null);
        inputDomicilioCP.setOpaque(false);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(etiquetaCP, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inputDomicilioCP)
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(etiquetaCP, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
            .addComponent(inputDomicilioCP)
        );

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));

        etiquetaCiudad.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        etiquetaCiudad.setForeground(new java.awt.Color(153, 153, 153));
        etiquetaCiudad.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        etiquetaCiudad.setText("Ciudad");

        inputDomicilioCiudad.setFont(new java.awt.Font("Calibri Light", 0, 12)); // NOI18N
        inputDomicilioCiudad.setBorder(null);
        inputDomicilioCiudad.setOpaque(false);
        inputDomicilioCiudad.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                inputDomicilioCiudadCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(etiquetaCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inputDomicilioCiudad)
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(etiquetaCiudad, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
            .addComponent(inputDomicilioCiudad)
        );

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));

        etiquetaPais.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        etiquetaPais.setForeground(new java.awt.Color(153, 153, 153));
        etiquetaPais.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        etiquetaPais.setText("País");
        etiquetaPais.setToolTipText("");

        inputDomicilioPais.setFont(new java.awt.Font("Calibri Light", 0, 12)); // NOI18N
        inputDomicilioPais.setBorder(null);
        inputDomicilioPais.setOpaque(false);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(etiquetaPais, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inputDomicilioPais)
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(etiquetaPais, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
            .addComponent(inputDomicilioPais)
        );

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));

        etiquetaRegion.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        etiquetaRegion.setForeground(new java.awt.Color(153, 153, 153));
        etiquetaRegion.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        etiquetaRegion.setText("Región");

        inputDomicilioRegion.setFont(new java.awt.Font("Calibri Light", 0, 12)); // NOI18N
        inputDomicilioRegion.setBorder(null);
        inputDomicilioRegion.setOpaque(false);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(etiquetaRegion, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inputDomicilioRegion)
                .addContainerGap())
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(etiquetaRegion, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
            .addComponent(inputDomicilioRegion)
        );

        javax.swing.GroupLayout panel_datos_domicilioLayout = new javax.swing.GroupLayout(panel_datos_domicilio);
        panel_datos_domicilio.setLayout(panel_datos_domicilioLayout);
        panel_datos_domicilioLayout.setHorizontalGroup(
            panel_datos_domicilioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel_datos_domicilioLayout.setVerticalGroup(
            panel_datos_domicilioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_datos_domicilioLayout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jToggleButton2))
            .addComponent(panel_datos_domicilio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jToggleButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel_datos_domicilio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        etiquetaRepVentas.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        etiquetaRepVentas.setForeground(new java.awt.Color(102, 102, 102));
        etiquetaRepVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/user-tie-solid.png"))); // NOI18N
        etiquetaRepVentas.setText("Rep. ventas");

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));

        inputCodRepVentas.setBorder(null);
        inputCodRepVentas.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        inputCodRepVentas.setFont(new java.awt.Font("Calibri Light", 0, 12)); // NOI18N
        inputCodRepVentas.setOpaque(false);

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(inputCodRepVentas, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(inputCodRepVentas, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(etiquetaRepVentas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(etiquetaRepVentas)
            .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        etiquetaLimCredito.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        etiquetaLimCredito.setForeground(new java.awt.Color(102, 102, 102));
        etiquetaLimCredito.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/euro-sign-solid.png"))); // NOI18N
        etiquetaLimCredito.setText("Lím. crédito");

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));

        inputLimiteCredito.setBorder(null);
        inputLimiteCredito.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        inputLimiteCredito.setFont(new java.awt.Font("Calibri Light", 0, 12)); // NOI18N
        inputLimiteCredito.setOpaque(false);
        inputLimiteCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputLimiteCreditoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(inputLimiteCredito)
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(inputLimiteCredito, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(etiquetaLimCredito)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(etiquetaLimCredito)
            .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        etiquetaDocumento.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        etiquetaDocumento.setForeground(new java.awt.Color(102, 102, 102));
        etiquetaDocumento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/id-card-solid.png"))); // NOI18N
        etiquetaDocumento.setText("Documento identificativo");

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        etiquetaTipoDocumento.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        etiquetaTipoDocumento.setForeground(new java.awt.Color(153, 153, 153));
        etiquetaTipoDocumento.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        etiquetaTipoDocumento.setText("Tipo");

        inputNA.setSelected(true);
        inputNA.setText("N/A");
        inputNA.setNextFocusableComponent(inputDNI);
        inputNA.setOpaque(false);
        inputNA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inputNAMouseClicked(evt);
            }
        });
        inputNA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputNAActionPerformed(evt);
            }
        });

        inputNIE.setText("NIE");
        inputNIE.setNextFocusableComponent(inputDocumentoContenido);
        inputNIE.setOpaque(false);
        inputNIE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputNIEActionPerformed(evt);
            }
        });

        inputDNI.setText("DNI");
        inputDNI.setNextFocusableComponent(inputNIE);
        inputDNI.setOpaque(false);
        inputDNI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputDNIActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(etiquetaTipoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(inputNA)
                .addGap(18, 18, 18)
                .addComponent(inputDNI)
                .addGap(18, 18, 18)
                .addComponent(inputNIE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(etiquetaTipoDocumento, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                .addComponent(inputNA)
                .addComponent(inputNIE)
                .addComponent(inputDNI))
        );

        panelContenidoDocumento.setBackground(new java.awt.Color(255, 255, 255));

        etiquetaContenidoDocumento.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        etiquetaContenidoDocumento.setForeground(new java.awt.Color(153, 153, 153));
        etiquetaContenidoDocumento.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        etiquetaContenidoDocumento.setText("Contenido");
        etiquetaContenidoDocumento.setEnabled(false);

        inputDocumentoContenido.setFont(new java.awt.Font("Calibri Light", 0, 12)); // NOI18N
        inputDocumentoContenido.setBorder(null);
        inputDocumentoContenido.setEnabled(false);
        inputDocumentoContenido.setOpaque(false);
        inputDocumentoContenido.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                inputDocumentoContenidoCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout panelContenidoDocumentoLayout = new javax.swing.GroupLayout(panelContenidoDocumento);
        panelContenidoDocumento.setLayout(panelContenidoDocumentoLayout);
        panelContenidoDocumentoLayout.setHorizontalGroup(
            panelContenidoDocumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContenidoDocumentoLayout.createSequentialGroup()
                .addComponent(etiquetaContenidoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inputDocumentoContenido)
                .addContainerGap())
        );
        panelContenidoDocumentoLayout.setVerticalGroup(
            panelContenidoDocumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContenidoDocumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(etiquetaContenidoDocumento, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                .addComponent(inputDocumentoContenido))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelContenidoDocumento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(panelContenidoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(etiquetaDocumento)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(etiquetaDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        etiquetaEmail.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        etiquetaEmail.setForeground(new java.awt.Color(102, 102, 102));
        etiquetaEmail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/at-solid.png"))); // NOI18N
        etiquetaEmail.setText("Email");

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));

        inputEmail.setFont(new java.awt.Font("Calibri Light", 0, 12)); // NOI18N
        inputEmail.setBorder(null);
        inputEmail.setOpaque(false);
        inputEmail.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                inputEmailCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(inputEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(inputEmail, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(etiquetaEmail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(etiquetaEmail)
            .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        etiquetaContrasena.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        etiquetaContrasena.setForeground(new java.awt.Color(102, 102, 102));
        etiquetaContrasena.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/asterisk-solid.png"))); // NOI18N
        etiquetaContrasena.setText("Contraseña");

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));

        inputContrasena.setBorder(null);
        inputContrasena.setNextFocusableComponent(botonAceptar);
        inputContrasena.setOpaque(false);
        inputContrasena.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                inputContrasenaCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(inputContrasena)
                .addContainerGap())
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(inputContrasena)
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(etiquetaContrasena)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(etiquetaContrasena, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelInfo.setBackground(new java.awt.Color(255, 102, 102));
        panelInfo.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        scrollTexto.setBackground(new java.awt.Color(255, 234, 255));
        scrollTexto.setBorder(null);
        scrollTexto.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollTexto.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollTexto.setOpaque(false);
        scrollTexto.setPreferredSize(new java.awt.Dimension(29, 96));
        scrollTexto.setRequestFocusEnabled(false);

        infoTexto.setEditable(false);
        infoTexto.setBackground(new java.awt.Color(255, 102, 102));
        infoTexto.setColumns(20);
        infoTexto.setFont(new java.awt.Font("Calibri Light", 1, 15)); // NOI18N
        infoTexto.setForeground(new java.awt.Color(255, 255, 102));
        infoTexto.setLineWrap(true);
        infoTexto.setRows(2);
        infoTexto.setText("Hay campos obligatorios sin rellenar.");
        infoTexto.setToolTipText("");
        infoTexto.setWrapStyleWord(true);
        infoTexto.setMargin(new java.awt.Insets(3, 2, 2, 2));
        scrollTexto.setViewportView(infoTexto);

        javax.swing.GroupLayout panelInfoLayout = new javax.swing.GroupLayout(panelInfo);
        panelInfo.setLayout(panelInfoLayout);
        panelInfoLayout.setHorizontalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollTexto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelInfoLayout.setVerticalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(scrollTexto, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );

        botonAceptar.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        botonAceptar.setText("Aceptar");
        botonAceptar.setNextFocusableComponent(botonCancelar);
        botonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAceptarActionPerformed(evt);
            }
        });

        botonCancelar.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        botonCancelar.setText("Cancelar");
        botonCancelar.setNextFocusableComponent(inputCodigo);
        botonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarActionPerformed(evt);
            }
        });

        barraTitulo.setBackground(new java.awt.Color(255, 255, 255));
        barraTitulo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        barraTitulo.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                barraTituloMouseDragged(evt);
            }
        });
        barraTitulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                barraTituloMousePressed(evt);
            }
        });

        iconoYNombreVentana.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        iconoYNombreVentana.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/leaf-solid.png"))); // NOI18N
        iconoYNombreVentana.setText("Jardinería - Editar cliente");
        iconoYNombreVentana.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                iconoYNombreVentanaMouseDragged(evt);
            }
        });
        iconoYNombreVentana.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                iconoYNombreVentanaMousePressed(evt);
            }
        });

        fondoBotonCerrar.setBackground(new java.awt.Color(255, 255, 255));

        botonCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/times-solid.png"))); // NOI18N
        botonCerrar.setToolTipText("");
        botonCerrar.setBorderPainted(false);
        botonCerrar.setContentAreaFilled(false);
        botonCerrar.setFocusPainted(false);
        botonCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botonCerrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botonCerrarMouseExited(evt);
            }
        });
        botonCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout fondoBotonCerrarLayout = new javax.swing.GroupLayout(fondoBotonCerrar);
        fondoBotonCerrar.setLayout(fondoBotonCerrarLayout);
        fondoBotonCerrarLayout.setHorizontalGroup(
            fondoBotonCerrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(botonCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, Short.MAX_VALUE)
        );
        fondoBotonCerrarLayout.setVerticalGroup(
            fondoBotonCerrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(botonCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        fondoBotonMinim.setBackground(new java.awt.Color(255, 255, 255));

        botonMinim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/window-minimize-solid.png"))); // NOI18N
        botonMinim.setBorder(null);
        botonMinim.setBorderPainted(false);
        botonMinim.setContentAreaFilled(false);
        botonMinim.setEnabled(false);
        botonMinim.setFocusPainted(false);
        botonMinim.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botonMinimMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botonMinimMouseExited(evt);
            }
        });
        botonMinim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonMinimActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout fondoBotonMinimLayout = new javax.swing.GroupLayout(fondoBotonMinim);
        fondoBotonMinim.setLayout(fondoBotonMinimLayout);
        fondoBotonMinimLayout.setHorizontalGroup(
            fondoBotonMinimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoBotonMinimLayout.createSequentialGroup()
                .addComponent(botonMinim, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        fondoBotonMinimLayout.setVerticalGroup(
            fondoBotonMinimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(botonMinim, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout barraTituloLayout = new javax.swing.GroupLayout(barraTitulo);
        barraTitulo.setLayout(barraTituloLayout);
        barraTituloLayout.setHorizontalGroup(
            barraTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(barraTituloLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iconoYNombreVentana)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(fondoBotonMinim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(fondoBotonCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        barraTituloLayout.setVerticalGroup(
            barraTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(iconoYNombreVentana, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(barraTituloLayout.createSequentialGroup()
                .addGroup(barraTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fondoBotonCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fondoBotonMinim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        botonReset.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        botonReset.setText("Resetear");
        botonReset.setNextFocusableComponent(botonCancelar);
        botonReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(botonReset, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(botonAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)))
                .addContainerGap())
            .addComponent(barraTitulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(barraTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonCancelar)
                    .addComponent(botonAceptar)
                    .addComponent(botonReset))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        panel_datos_contacto.setVisible(!panel_datos_contacto.isVisible());
        //this.setSize(this.getWidth(), this.getHeight() + (panel_datos_contacto_h * (panel_datos_contacto.isVisible() ? 1 : -1)));
        this.pack();
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        panel_datos_domicilio.setVisible(!panel_datos_domicilio.isVisible());
        this.pack();
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void inputNIEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputNIEActionPerformed
        if (!inputDocumentoContenido.isEnabled()) {
            etiquetaContenidoDocumento.setEnabled(true);
            inputDocumentoContenido.setEnabled(true);
        }
        inputDocumentoContenidoCaretUpdate(null);
    }//GEN-LAST:event_inputNIEActionPerformed

    private void inputLimiteCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputLimiteCreditoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputLimiteCreditoActionPerformed

    private void botonCerrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonCerrarMouseEntered
        fondoBotonCerrar.setBackground(new Color(253, 185, 186));
    }//GEN-LAST:event_botonCerrarMouseEntered

    private void botonCerrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonCerrarMouseExited
        fondoBotonCerrar.setBackground(Color.white);
    }//GEN-LAST:event_botonCerrarMouseExited

    private void botonMinimMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonMinimMouseEntered
        fondoBotonMinim.setBackground(new Color(189, 203, 216));
    }//GEN-LAST:event_botonMinimMouseEntered

    private void botonMinimMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonMinimMouseExited
        fondoBotonMinim.setBackground(Color.white);
    }//GEN-LAST:event_botonMinimMouseExited

    Point location;
    int px = 0, 
        py = 0;
    
    private void barraTituloMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barraTituloMouseDragged
        location = this.getLocation(location);
        int x = location.x - px + evt.getX();
        int y = location.y - py + evt.getY();
        this.setLocation(x, y);
    }//GEN-LAST:event_barraTituloMouseDragged

    private void barraTituloMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barraTituloMousePressed
        px = evt.getX();
        py = evt.getY();
    }//GEN-LAST:event_barraTituloMousePressed

    private void iconoYNombreVentanaMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconoYNombreVentanaMouseDragged
        barraTituloMouseDragged(evt);
    }//GEN-LAST:event_iconoYNombreVentanaMouseDragged

    private void iconoYNombreVentanaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconoYNombreVentanaMousePressed
        barraTituloMousePressed(evt);
    }//GEN-LAST:event_iconoYNombreVentanaMousePressed

    private void botonMinimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonMinimActionPerformed
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_ICONIFIED));
        //this.setState(Frame.ICONIFIED);
    }//GEN-LAST:event_botonMinimActionPerformed

    private void botonCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCerrarActionPerformed
        cerrar();
    }//GEN-LAST:event_botonCerrarActionPerformed

    private void cerrar() {
        if (app != null) app.editarClienteExiste = false;
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
    
    private void botonResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonResetActionPerformed
        for (JTextField campo : inputs) {
            campo.setText("");
        }
        etiquetaCodigo.setForeground(colorError);
        //if (botonAceptar.isEnabled()) botonAceptar.setEnabled(false);
        inputNA.setSelected(true);
    }//GEN-LAST:event_botonResetActionPerformed

    private void botonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptarActionPerformed
        if (app != null) {
            try {
                // intentar buildear
                Cliente nuevo = new Cliente.Builder(Integer.parseInt(inputCodigo.getText()), 
                                                    inputNombre.getText(),
                                                    inputContactoTelefono.getText(),
                                                    inputContactoFax.getText(),
                                                    inputDomicilioDireccion1.getText(),
                                                    inputDomicilioCiudad.getText())
                                                    .con_nombre_de_contacto(StringUtils.isBlank(inputContactoNombre.getText()) ? null : inputContactoNombre.getText())
                                                    .con_apellido_de_contacto(StringUtils.isBlank(inputContactoApellido.getText()) ? null : inputContactoApellido.getText())
                                                    .con_linea_direccion2(StringUtils.isBlank(inputDomicilioDireccion2.getText()) ? null : inputDomicilioDireccion2.getText())
                                                    .con_region(StringUtils.isBlank(inputDomicilioRegion.getText()) ? null : inputDomicilioRegion.getText())
                                                    .con_pais(StringUtils.isBlank(inputDomicilioPais.getText()) ? null : inputDomicilioPais.getText())
                                                    .con_codigo_postal(StringUtils.isBlank(inputDomicilioCP.getText()) ? null : inputDomicilioCP.getText())
                                                    .con_cod_empl_rep_ventas(StringUtils.isBlank(inputCodRepVentas.getText()) ? null :
                                                            Integer.parseInt(inputCodRepVentas.getText()))
                                                    .con_limite_credito(StringUtils.isBlank(inputLimiteCredito.getText()) ? null :
                                                            Double.parseDouble(inputLimiteCredito.getText().replace(',', '.')))
                                                    .con_documento(inputDNI.isSelected() ? TipoDocumento.DNI : (inputNIE.isSelected() ? TipoDocumento.NIE : null),
                                                            !inputNA.isSelected() ? inputDocumentoContenido.getText() : null)
                                                    .con_email(StringUtils.isBlank(inputEmail.getText()) ? null : inputEmail.getText(), 
                                                               inputContrasena.getPassword().length == 0 ? null : new String(inputContrasena.getPassword()))
                                                    .build();

                if (C == null) {
                    // añadiendo cliente nuevo
                    app.daos.clientes().guardar(nuevo);

                    ((DefaultTableModel) app.getTablaClientes().getModel()).addRow(nuevo.objArray());
                } else {
                    // editando cliente existente
                    ((DefaultTableModel) app.getTablaClientes().getModel()).removeRow(rowCliente - 1);
                    ((DefaultTableModel) app.getTablaClientes().getModel()).insertRow(rowCliente - 1, nuevo.objArray());
                }
                cerrar();

            } catch (Exception e) {
                
                infoTexto.setBackground(panelErrorBgMal);
                panelInfo.setBackground(panelErrorBgMal);
                
                if (e instanceof ExcepcionFormatoIncorrecto) {
                    // Excepcion de Cliente.Builder
                    infoTexto.setText("Excepción: formato incorrecto");
                } else if (e instanceof ExcepcionDatoNoValido) {
                    // Excepcion de Cliente.Builder
                    infoTexto.setText("Excepción: dato no válido");
                } else if (e instanceof ExcepcionCodigoYaExistente) {
                    // Excepcion de ClientesSqlDao
                    infoTexto.setText("Excepción: código ya existente");
                } else if (e instanceof SQLException) {
                    // Excepcion de ClientesSqlDao
                    infoTexto.setText("Excepción: excepción SQL");
                } else if (e instanceof NumberFormatException) {
                    infoTexto.setText("Exepción: formato de número");
                } else {
                    infoTexto.setText("Excepción de otro tipo");
                }
                e.printStackTrace();
            }
            
        }
        
    }//GEN-LAST:event_botonAceptarActionPerformed

    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarActionPerformed
        cerrar();
    }//GEN-LAST:event_botonCancelarActionPerformed

    private static final Color colorNormal = new Color(102,102,102);
    private static final Color colorError = new Color(228,103,18);
    
    private void inputCodigoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_inputCodigoCaretUpdate
        Object o = inputCodigo.getValue();
        if (o == null || (Long)o <= 0L) {
            etiquetaCodigo.setForeground(colorError);
            codigoBien = false;
        } else {
            etiquetaCodigo.setForeground(colorNormal);
            codigoBien = true;
        }
        updatearMsjError();
    }//GEN-LAST:event_inputCodigoCaretUpdate

    private void inputNombreCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_inputNombreCaretUpdate
        String texto = inputNombre.getText();
        if (texto == null || StringUtils.isBlank(texto)) {
            etiquetaNombre.setForeground(colorError);
            nombreBien = false;
        } else {
            etiquetaNombre.setForeground(colorNormal);
            nombreBien = true;
        }
        updatearMsjError();
    }//GEN-LAST:event_inputNombreCaretUpdate

    private void inputContactoTelefonoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_inputContactoTelefonoCaretUpdate
        String texto = inputContactoTelefono.getText();
        if (texto == null || StringUtils.isBlank(texto)) {
            etiquetaTelefono.setForeground(colorError);
            telefonoBien = false;
        } else {
            etiquetaTelefono.setForeground(colorNormal);
            telefonoBien = true;
        }
    }//GEN-LAST:event_inputContactoTelefonoCaretUpdate

    private void inputDomicilioDireccion1CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_inputDomicilioDireccion1CaretUpdate
        String texto = inputDomicilioDireccion1.getText();
        if (texto == null || StringUtils.isBlank(texto)) {
            etiquetaDireccion1.setForeground(colorError);
            direccion1Bien = false;
        } else {
            etiquetaDireccion1.setForeground(colorNormal);
            direccion1Bien = true;
        }
        updatearMsjError();
    }//GEN-LAST:event_inputDomicilioDireccion1CaretUpdate

    private void inputDomicilioCiudadCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_inputDomicilioCiudadCaretUpdate
        String texto = inputDomicilioCiudad.getText();
        if (texto == null || StringUtils.isBlank(texto)) {
            etiquetaCiudad.setForeground(colorError);
            ciudadBien = false;
        } else {
            etiquetaCiudad.setForeground(colorNormal);
            ciudadBien = true;
        }
        updatearMsjError();
    }//GEN-LAST:event_inputDomicilioCiudadCaretUpdate

    private void inputNAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputNAActionPerformed
        etiquetaContenidoDocumento.setForeground(colorNormal);
        documentoBien = true;
        if (inputDocumentoContenido.isEnabled()) {
            etiquetaContenidoDocumento.setEnabled(false);
            inputDocumentoContenido.setEnabled(false);
        }
        updatearMsjError();
    }//GEN-LAST:event_inputNAActionPerformed

    private void inputDNIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputDNIActionPerformed
        if (!inputDocumentoContenido.isEnabled()) {
            etiquetaContenidoDocumento.setEnabled(true);
            inputDocumentoContenido.setEnabled(true);
        }
        inputDocumentoContenidoCaretUpdate(null);
    }//GEN-LAST:event_inputDNIActionPerformed

    private void inputDocumentoContenidoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_inputDocumentoContenidoCaretUpdate
        if (inputDNI.isSelected()) {
            if (inputDocumentoContenido.getText().matches("\\d{8}\\p{Alpha}")) {
                documentoBien = true;
                etiquetaDocumento.setForeground(colorNormal);
            } else {
                documentoBien = false;
                etiquetaDocumento.setForeground(colorError);
            }
        } else if (inputNIE.isSelected()) { // NIE
            if (inputDocumentoContenido.getText().matches("\\p{Alpha}\\d{7}\\p{Alpha}")) {
                    documentoBien = true;
                etiquetaDocumento.setForeground(colorNormal);
            } else {
                documentoBien = false;
                etiquetaDocumento.setForeground(colorError);
            }
        } else {
            documentoBien = true;
        }
        updatearMsjError();
    }//GEN-LAST:event_inputDocumentoContenidoCaretUpdate

    private void inputContactoFaxCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_inputContactoFaxCaretUpdate
        String texto = inputContactoFax.getText();
        if (texto == null || StringUtils.isBlank(texto)) {
            etiquetaFax.setForeground(colorError);
            faxBien = false;
        } else {
            etiquetaFax.setForeground(colorNormal);
            faxBien = true;
        }
        updatearMsjError();
    }//GEN-LAST:event_inputContactoFaxCaretUpdate

    private void inputEmailCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_inputEmailCaretUpdate
        if (inputEmail.getText().matches("\\w+@\\w+[.]\\w+")) {
            etiquetaEmail.setForeground(colorNormal);
            emailBien = true;
            
            if (new String(inputContrasena.getPassword()).matches("\\h*")) {
                etiquetaContrasena.setForeground(colorError);
                contrasenaBien = false;
            } else {
                etiquetaContrasena.setForeground(colorNormal);
                contrasenaBien = true;
            }
            
        } else if (StringUtils.isBlank(inputEmail.getText())) {
            etiquetaEmail.setForeground(colorNormal);
            emailBien = true;
            etiquetaContrasena.setForeground(colorNormal);
            contrasenaBien = true;
        } else {
            etiquetaEmail.setForeground(colorError);
            emailBien = false;
        }
        updatearMsjError();
    }//GEN-LAST:event_inputEmailCaretUpdate

    private void inputNAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inputNAMouseClicked
    }//GEN-LAST:event_inputNAMouseClicked

    private void inputContrasenaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_inputContrasenaCaretUpdate
        if (inputEmail.getText().matches("\\w+@\\w+[.]\\w+")) {
            etiquetaEmail.setForeground(colorNormal);
            emailBien = true;
            
            if (StringUtils.isBlank(new String(inputContrasena.getPassword()))) {
                etiquetaContrasena.setForeground(colorError);
                contrasenaBien = false;
            } else {
                etiquetaContrasena.setForeground(colorNormal);
                contrasenaBien = true;
            }
            
        } else if (StringUtils.isBlank(inputEmail.getText())) {
            etiquetaEmail.setForeground(colorNormal);
            emailBien = true;
            etiquetaContrasena.setForeground(colorNormal);
            contrasenaBien = true;
        } else {
            etiquetaEmail.setForeground(colorError);
            emailBien = false;

        }   updatearMsjError();
     }//GEN-LAST:event_inputContrasenaCaretUpdate

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //</editor-fold>

        
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditarCliente(null, null).setVisible(true);
            }
        });
    }
    
    private final Color panelErrorBgMal = new Color(255,102,102);
    //private final Color textoPanelError = new Color(255,255,102);
    private final Color panelErrorBgBien = new Color(102,202,102);
    
    private void updatearMsjError() {
        boolean obligatoriosBien = codigoBien && nombreBien && telefonoBien &&
                    faxBien && direccion1Bien && ciudadBien;
        boolean otrosBien = documentoBien && emailBien && contrasenaBien;
        
        if (obligatoriosBien && otrosBien) {
            infoTexto.setText("Bien");
            infoTexto.setBackground(panelErrorBgBien);
            panelInfo.setBackground(panelErrorBgBien);
        } else {
            infoTexto.setBackground(panelErrorBgMal);
            panelInfo.setBackground(panelErrorBgMal);
            if (!obligatoriosBien) {
                infoTexto.setText("Hay campos obligatorios sin rellenar");
            } else {
                infoTexto.setText("no");
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel barraTitulo;
    private javax.swing.JButton botonAceptar;
    private javax.swing.JButton botonCancelar;
    private javax.swing.JButton botonCerrar;
    private javax.swing.JButton botonMinim;
    private javax.swing.JButton botonReset;
    private javax.swing.JLabel etiquetaApellido;
    private javax.swing.JLabel etiquetaCP;
    private javax.swing.JLabel etiquetaCiudad;
    private javax.swing.JLabel etiquetaCodigo;
    private javax.swing.JLabel etiquetaContenidoDocumento;
    private javax.swing.JLabel etiquetaContrasena;
    private javax.swing.JLabel etiquetaDireccion1;
    private javax.swing.JLabel etiquetaDireccion2;
    private javax.swing.JLabel etiquetaDocumento;
    private javax.swing.JLabel etiquetaEmail;
    private javax.swing.JLabel etiquetaFax;
    private javax.swing.JLabel etiquetaLimCredito;
    private javax.swing.JLabel etiquetaNombre;
    private javax.swing.JLabel etiquetaNombreContacto;
    private javax.swing.JLabel etiquetaPais;
    private javax.swing.JLabel etiquetaRegion;
    private javax.swing.JLabel etiquetaRepVentas;
    private javax.swing.JLabel etiquetaTelefono;
    private javax.swing.JLabel etiquetaTipoDocumento;
    private javax.swing.JPanel fondoBotonCerrar;
    private javax.swing.JPanel fondoBotonMinim;
    private javax.swing.JLabel iconoYNombreVentana;
    private javax.swing.JTextArea infoTexto;
    private javax.swing.JFormattedTextField inputCodRepVentas;
    private javax.swing.JFormattedTextField inputCodigo;
    private javax.swing.JTextField inputContactoApellido;
    private javax.swing.JTextField inputContactoFax;
    private javax.swing.JTextField inputContactoNombre;
    private javax.swing.JTextField inputContactoTelefono;
    private javax.swing.JPasswordField inputContrasena;
    private javax.swing.JRadioButton inputDNI;
    private javax.swing.JTextField inputDocumentoContenido;
    private javax.swing.JTextField inputDomicilioCP;
    private javax.swing.JTextField inputDomicilioCiudad;
    private javax.swing.JTextField inputDomicilioDireccion1;
    private javax.swing.JTextField inputDomicilioDireccion2;
    private javax.swing.JTextField inputDomicilioPais;
    private javax.swing.JTextField inputDomicilioRegion;
    private javax.swing.JTextField inputEmail;
    private javax.swing.JFormattedTextField inputLimiteCredito;
    private javax.swing.JRadioButton inputNA;
    private javax.swing.JRadioButton inputNIE;
    private javax.swing.JTextField inputNombre;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JPanel panelContenidoDocumento;
    private javax.swing.JPanel panelInfo;
    private javax.swing.JPanel panel_datos_contacto;
    private javax.swing.JPanel panel_datos_domicilio;
    private javax.swing.JScrollPane scrollTexto;
    // End of variables declaration//GEN-END:variables
}
