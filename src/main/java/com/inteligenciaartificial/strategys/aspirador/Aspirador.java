package com.inteligenciaartificial.strategys.aspirador;

import com.inteligenciaartificial.strategys.robo.Robo;
import com.inteligenciaartificial.strategys.ambiente.Ambiente;

import java.util.logging.Logger;
public class Aspirador {
    private static final Logger logger = Logger.getLogger(Aspirador.class.getName());
    private Ambiente ambiente;

    public Aspirador(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public void aspirar(Robo robo) {
        int direcaoX = robo.getDirecaoX();
        int direcaoY = robo.getDirecaoY();

        logger.info("Aspirando sujeira em (" + direcaoX + ", " + direcaoY + ")");
        ambiente.limpar(direcaoX, direcaoY);
    }
}


