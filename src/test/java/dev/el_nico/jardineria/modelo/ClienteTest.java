package dev.el_nico.jardineria.modelo;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import dev.el_nico.jardineria.excepciones.ExcepcionDatoNoValido;
import dev.el_nico.jardineria.excepciones.ExcepcionFormatoIncorrecto;

public class ClienteTest {

    @Test
    public void LanzaExcepcionConEmailMalformado() {
        Cliente.Builder builder = new Cliente.Builder(1, "nombre", "tlf", "fax", "dir", "ciudad");
        try {
            builder.con_email("nombre@hotmailes", "1234").build();
            fail();
        } catch (ExcepcionFormatoIncorrecto e) {
            // oke
        } catch (ExcepcionDatoNoValido e) {
            fail();
        } 
        try {
            builder.con_email("nombrehotmail.es", "1234").build();
            fail();
        } catch (ExcepcionFormatoIncorrecto e) {
            // oke
        } catch (ExcepcionDatoNoValido e) {
            fail();
        }
        try {
            builder.con_email("@hotmail.es", "1234").build();
            fail();
        } catch (ExcepcionFormatoIncorrecto e) {
            // oke
            assertTrue(true);
        } catch (ExcepcionDatoNoValido e) {
            fail();
        } 
    }

    @Test
    public void AceptaClienteConEmailCorrecto() {
        try {
            new Cliente.Builder(1, "nombre", "tlf", "fax", "dir", "ciudad")
                       .con_email("un_email@servicio.com", "1234")
                       .build();
            new Cliente.Builder(2, "nombre2", "tlf2", "fax2", "dir2", "ciudad2")
                       .con_email("djsjfd@fgo.e", "1234")
                       .build();
            new Cliente.Builder(3, "nombre3", "tlf3", "fax3", "dir3", "ciudad3")
                       .con_email("hola12344@hotmail.com", "1234")
                       .build();
            assertTrue(true);
        } catch (ExcepcionDatoNoValido | ExcepcionFormatoIncorrecto e) {
            fail();
        }
    }

    @Test
    public void LanzaExcepcionConDocumentoMalformado() {
        Cliente.Builder builder = new Cliente.Builder(1, "nombre", "tlf", "fax", "dir", "ciudad");
        try {
            builder.con_documento(TipoDocumento.DNI, "123456789a").build();
            fail();
        } catch (ExcepcionFormatoIncorrecto e) {
            // oke
        } catch (ExcepcionDatoNoValido e) {
            fail();
        } 
        try {
            builder.con_documento(TipoDocumento.DNI, "999999999").build();
            fail();
        } catch (ExcepcionFormatoIncorrecto e) {
            // oke
        } catch (ExcepcionDatoNoValido e) {
            fail();
        }
        try {
            builder.con_documento(TipoDocumento.NIE, "23456789Z").build();
            fail();
        } catch (ExcepcionFormatoIncorrecto e) {
            // oke
        } catch (ExcepcionDatoNoValido e) {
            fail();
        }
        try {
            builder.con_documento(TipoDocumento.NIE, "holis").build();
            fail();
        } catch (ExcepcionFormatoIncorrecto e) {
            assertTrue(true); // oke
        } catch (ExcepcionDatoNoValido e) {
            fail();
        } 
    }

    @Test
    public void AceptaDocumentoCorrecto() {
        try {
            new Cliente.Builder(1, "nombre", "tlf", "fax", "dir", "ciudad")
                       .con_documento(TipoDocumento.DNI, "12345678A")
                       .build();
            new Cliente.Builder(2, "nombre2", "tlf2", "fax2", "dir2", "ciudad2")
                       .con_documento(TipoDocumento.DNI, "22228888I")
                       .build();
            new Cliente.Builder(3, "nombre3", "tlf3", "fax3", "dir3", "ciudad3")
                       .con_documento(TipoDocumento.NIE, "I0000000I")
                       .build();
            new Cliente.Builder(3, "nombre4", "tlf4", "fax4", "dir4", "ciudad4")
                       .con_documento(TipoDocumento.NIE, "A1234567B")
                       .build();
            assertTrue(true);
        } catch (ExcepcionDatoNoValido | ExcepcionFormatoIncorrecto e) {
            fail();
        }
    }
}
