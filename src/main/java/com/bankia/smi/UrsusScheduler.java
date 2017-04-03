package com.bankia.smi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created by A169897 on 19/10/2016.
 */
@Component
public class UrsusScheduler {


    final Logger LOGGER = LoggerFactory.getLogger(UrsusScheduler.class);

    @Value("${ursus.script}")
    private String bashCommand;

    @Scheduled(cron="0 5 0 * * *")
    private void lanzarUrsus() {
        String command[] = {bashCommand};
        if (ejecutarComando(command) == -1)
            LOGGER.error("Error al ejecutar comando {}" , Arrays.toString(command));
        else
            LOGGER.debug("Comando ejecutado correctametne {}" , Arrays.toString(command));
    }

    /**
     * Método encargado de lanzar el commando que se le manda como cadena de entrada dentro de la máquina.
     * En caso de exisitir una excepciópn en la llamada al comando se logara en la salida de error la traza existente.
     *
     * @param command          comando a ejecutar
     * @return cadena con el resultado de la salida del comando ejecutado
     */
    private int ejecutarComando(String[] command) {

        int exitval;
        StringBuilder salidaComando = new StringBuilder();
        try {
            ProcessBuilder p = new ProcessBuilder(command);
            p.environment();
            Process process = p.start();
            InputStreamReader isOut = new InputStreamReader(process.getInputStream());
            BufferedReader br = new BufferedReader(isOut);
            String line;
            while ( (line = br.readLine()) != null)
                salidaComando.append(line);
            InputStreamReader isrError = new InputStreamReader(process.getErrorStream());
            BufferedReader brError = new BufferedReader(isrError);
            while ( (line = brError.readLine()) != null)
                LOGGER.error(line);
            exitval = process.waitFor();
        } catch (Throwable t) {
            exitval=-1;
            LOGGER.error("error al ejecutar comando {}  ; Descripción : {}" , Arrays.toString(command), t.getMessage());
        }
        return exitval;

    }
}
