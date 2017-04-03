package com.bankia.smi;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ContactoTelefonicoUtils {

    private static final String FORMATO_FECHA = "yyyy-MM-dd-HH.mm.ss.SSSSSS";
    private static final String ENTIDAD = "0000";
    private static final String SEPARADOR = "|";

    private static final int LARGO_CENTRO = 4;
    private static final int LARGO_PREFIJO = 4;


    public static String getFechaActual(String formatoFecha) {

        Date fechaActual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat(formatoFecha);
        return formato.format(fechaActual);
    }


    public static String construyeTrama(String centro, String prefijo, String telefono) {

        String fechaActual = ContactoTelefonicoUtils.getFechaActual(FORMATO_FECHA);

        return ENTIDAD + centro + SEPARADOR + prefijo + SEPARADOR + telefono + SEPARADOR + fechaActual;
    }


    public static void validaTrama(String centro, String prefijo, String telefono) throws Exception {


        if (!ContactoTelefonicoUtils.validaCentro(centro)) {
            throw new Exception("Centro no válido. Debe ser un valor numérico de 4 dígitos.");
        }
        if (!ContactoTelefonicoUtils.validaPrefijo(prefijo)) {
            throw new Exception("Prefijo no válido. Debe ser un valor numérico de 4 dígitos.");
        }
        if (!ContactoTelefonicoUtils.validaTelefono(telefono)) {
            throw new Exception("Telefono no válido. Debe ser un valor numérico.");
        }


    }


    public static boolean validaCentro(String centro) {

        return validaNumerico(centro) && validaLargo(LARGO_CENTRO, centro);

    }

    public static boolean validaPrefijo(String prefijo) {

        return validaNumerico(prefijo) && validaLargo(LARGO_PREFIJO, prefijo);
    }

    public static boolean validaTelefono(String telefono) {

        return validaNumerico(telefono);
    }


    public static boolean validaNumerico(String campo) {

        try {
            Integer.parseInt(campo);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }

    }


    public static boolean validaLargo(int largo, String campo) {

        if (campo.length() == largo)
            return true;
        else
            return false;

    }
}
