package com.inteligenciaartificial.strategys.sensor;

import com.inteligenciaartificial.strategys.ambiente.Ambiente;
import com.inteligenciaartificial.strategys.aspirador.Aspirador;
import com.inteligenciaartificial.strategys.enums.AcaoAgente;
import com.inteligenciaartificial.strategys.motor.Motor;
import com.inteligenciaartificial.strategys.robo.Robo;
import lombok.Getter;
import lombok.Setter;

import java.util.logging.Logger;

public class Sensor {
    private Ambiente ambiente;
    private Robo robo;

    private Motor motor;
    private Aspirador aspirador;
    private int tamanhoAmbienteX;
    private int tamanhoAmbienteY;

    private int[][] posicoesLimpo;

    @Getter @Setter
    private boolean obstaculo = false, sujo = false, limpo = false;
    private int posX, posY;
    private static final Logger logger = Logger.getLogger(Sensor.class.getName());

    public Sensor(Ambiente ambiente, Robo robo) {
        this.ambiente = ambiente;
        this.robo = robo;
        motor = new Motor();
        aspirador = new Aspirador(ambiente);

        tamanhoAmbienteX = ambiente.getSizeX();
        tamanhoAmbienteY = ambiente.getSizeY();
    }

    private void reset(){
        sujo = false;
        limpo = false;
        obstaculo = false;
    }

    public void obterPercepcao() {
        int helpADM = 0;
        for (int i = 0; i < 10; ++i) {
            reset();
            if(verificaSujeiraFrente()){
                continue;
            } else if (verificaSujeiraDireita()) {
                continue;
            } else if (verificaSujeiraTras()) {
                continue;
            } else if (verificaSujeiraEsquerda()) {
                continue;
            } else if (helpADM == 4) {
                new Exception("Erro desconhecido ao tentar se movimentar pelo ambiente");
            }else {
                helpADM++;
                motor.executarAcao(sujo, ambiente, robo, aspirador);
            }
        }

        logger.info("Percepção: {Obstáculo: " + obstaculo + ", Sujo: " + sujo + "}");
    }

    public boolean verificaSujeiraFrente(){
        posX = robo.getPosX();
        posY = robo.getPosY();

        if (posY > 0) {
            // verifica frente
            sujo = ambiente.isSujo(posX, posY - 1);
            limpo = ambiente.isClean(posX, posY - 1);

            if(ambiente.isObstaculo(posX, posY-1)){return false;}

            if(limpo){
                for (int y = posY-2; y >= 0; y--) {
                    sujo = ambiente.isSujo(posX, y);

                    if (ambiente.isObstaculo(posX, y)){break;}

                    if(sujo){
                        robo.setDirecaoY(y);
                        robo.setDirecaoX(posX);
                        robo.setDirecao(AcaoAgente.MOVER_FRENTE);
                        motor.executarAcao(sujo, ambiente, robo, aspirador);
                    }
                }

                if(!sujo){
                    robo.setDirecaoY(posY-1);
                    robo.setDirecaoX(posX);
                    robo.setDirecao(AcaoAgente.MOVER_FRENTE);
                }

            }

            if(sujo){
                robo.setDirecaoY(posY-1);
                robo.setDirecaoX(posX);
                robo.setDirecao(AcaoAgente.MOVER_FRENTE);
                motor.executarAcao(sujo, ambiente, robo, aspirador);
            }
        }

        return sujo;
    }

    private boolean verificaSujeiraEsquerda() {
        posX = robo.getPosX();
        posY = robo.getPosY();

        if (posX > 0) {
            // verifica esquerda
            sujo = ambiente.isSujo(posX -1, posY );
            limpo = ambiente.isClean(posX-1, posY);

            if(ambiente.isObstaculo(posX -1, posY)){return false; }

            if(limpo){
                for (int x = posX-2; x >= 0; x--) {
                    sujo = ambiente.isSujo(x, posY);

                    if (ambiente.isObstaculo(x, posY)){break;}

                    if(sujo){
                        robo.setDirecaoY(posY);
                        robo.setDirecaoX(x);
                        robo.setDirecao(AcaoAgente.GIRAR_ESQUERDA);
                        motor.executarAcao(sujo, ambiente, robo, aspirador);
                    }
                }

                if(!sujo){
                    robo.setDirecaoY(posY);
                    robo.setDirecaoX(posX -1);
                    robo.setDirecao(AcaoAgente.GIRAR_ESQUERDA);
                }
            }

            if(sujo){
                robo.setDirecaoY(posY);
                robo.setDirecaoX(posX -1);
                robo.setDirecao(AcaoAgente.GIRAR_ESQUERDA);
                motor.executarAcao(sujo, ambiente, robo, aspirador);
            }
        }

        return sujo;
    }

    private boolean verificaSujeiraTras() {
        posX = robo.getPosX();
        posY = robo.getPosY();

        if (posY < tamanhoAmbienteY-1) {
            // verifica trás
            sujo = ambiente.isSujo(posX, posY + 1);
            limpo = ambiente.isClean(posX, posY + 1);

            if(ambiente.isObstaculo(posX, posY + 1)){
                return false;
            }

            if(limpo){
                for (int y = posY+2; y <= tamanhoAmbienteY-1; y++) {
                    sujo = ambiente.isSujo(posX, y);
                    obstaculo = ambiente.isObstaculo(posX, y);
                    if (obstaculo){
                        break;
                    }
                    if(sujo){
                        robo.setDirecaoY(y);
                        robo.setDirecaoX(posX);
                        robo.setDirecao(AcaoAgente.MOVER_TRAS);
                        motor.executarAcao(sujo, ambiente, robo, aspirador);
                    }
                }

                if(!sujo){
                    robo.setDirecaoY(posY + 1);
                    robo.setDirecaoX(posX);
                    robo.setDirecao(AcaoAgente.MOVER_TRAS);
                }
            }

            if(sujo){
                robo.setDirecaoY(posY + 1);
                robo.setDirecaoX(posX);
                robo.setDirecao(AcaoAgente.MOVER_TRAS);
                motor.executarAcao(sujo, ambiente, robo, aspirador);
            }
        }
        return sujo;
    }

    private boolean verificaSujeiraDireita() {
        posX = robo.getPosX();
        posY = robo.getPosY();

        if (posX < tamanhoAmbienteX-1) {
            // verifica direita
            sujo = ambiente.isSujo(posX + 1, posY);
            limpo = ambiente.isClean(posX + 1, posY);

            if(ambiente.isObstaculo(posX + 1, posY)){ return false;}

            if(limpo){
                for (int x = posX+2; x <= tamanhoAmbienteX-1; x++) {
                    sujo = ambiente.isSujo(x, posY);

                    if (ambiente.isObstaculo(x, posY)){break;}

                    if(sujo){
                        robo.setDirecaoY(posY);
                        robo.setDirecaoX(x);
                        robo.setDirecao(AcaoAgente.GIRAR_DIREITA);
                        motor.executarAcao(sujo, ambiente, robo, aspirador);
                    }
                }

                if(!sujo){
                    robo.setDirecaoY(posY);
                    robo.setDirecaoX(posX + 1);
                    robo.setDirecao(AcaoAgente.GIRAR_DIREITA);
                }
            }

            if(sujo){
                robo.setDirecaoY(posY);
                robo.setDirecaoX(posX + 1);
                robo.setDirecao(AcaoAgente.GIRAR_DIREITA);
                motor.executarAcao(sujo, ambiente, robo, aspirador);
            }
        }

        return sujo;
    }

}



