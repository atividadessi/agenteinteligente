package com.inteligenciaartificial.strategys.motor;

import com.inteligenciaartificial.strategys.robo.Robo;
import com.inteligenciaartificial.strategys.ambiente.Ambiente;
import com.inteligenciaartificial.strategys.aspirador.Aspirador;
import com.inteligenciaartificial.strategys.enums.AcaoAgente;
import java.util.logging.Logger;

public class Motor {
    private static final Logger logger = Logger.getLogger(Motor.class.getName());

    public void executarAcao(boolean sujo,Ambiente ambiente, Robo robo, Aspirador aspirador) {
        AcaoAgente acao = robo.getDirecao();

        switch (acao) {
            case MOVER_FRENTE:
                if (sujo) aspirador.aspirar(robo);
                robo.moverFrente(ambiente);
                break;
            case MOVER_TRAS:
                if (sujo) aspirador.aspirar(robo);
                robo.moverTras(ambiente);
                break;
            case GIRAR_DIREITA:
                if (sujo) aspirador.aspirar(robo);
                robo.girarDireita();
                break;
            case GIRAR_ESQUERDA:
                if (sujo) aspirador.aspirar(robo);
                robo.girarEsquerda();
                break;
            default:
                logger.info("Ação não reconhecida.");
        }

    }

}

