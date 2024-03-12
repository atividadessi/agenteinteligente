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

    private int qtdSujeiraLimpa = 0;

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

    private void verificaSeTemObstaculos(){

    }

    public void obterPercepcao() {
        if (ambiente.getQtdSujeira() == 0){
            logger.info("Ambiente já esta limpo, robô sendo desligado");
            return;
        }

        for (int i = 0; i < 10; ++i) {
            if(qtdSujeiraLimpa == ambiente.getQtdSujeira()) {
                break;
            }
            reset();
            if(verificaSujeiraFrente(robo.getPosX(), robo.getPosY())){
                continue;
            } else if (verificaSujeiraDireita(robo.getPosX(), robo.getPosY())) {
                continue;
            } else if (verificaSujeiraTras(robo.getPosX(), robo.getPosY())) {
                continue;
            } else if (verificaSujeiraEsquerda(robo.getPosX(), robo.getPosY())) {
                continue;
            } else if (qtdSujeiraLimpa == ambiente.getQtdSujeira()) {
                System.out.println("Fim.");
                return;
            } else {
                motor.executarAcao(sujo, ambiente, robo, aspirador);
            }
        }

        logger.info("Percepção: {Obstáculo: " + obstaculo + ", Sujo: " + sujo + "}");
    }

    public boolean verificaSujeiraFrente(int posX, int posY){
        posY = posY-1;
        if (posY >= 0 && !ambiente.isObstaculo(posX, posY)) {
            // verifica frente
            sujo = ambiente.isSujo(posX, posY);
            limpo = ambiente.isClean(posX, posY);

            if(limpo){
                for (int y = posY; y >= 0; y--) {
                    sujo = ambiente.isSujo(posX, y);

                    if (ambiente.isObstaculo(posX, y)){break;}

                    if(sujo){
                        robo.setDirecaoY(y);
                        robo.setDirecaoX(posX);
                        robo.setDirecao(AcaoAgente.MOVER_FRENTE);
                        motor.executarAcao(sujo, ambiente, robo, aspirador);
                        qtdSujeiraLimpa++;
                    }
                    verificaSujeiraDireita(posX, y);
                    verificaSujeiraEsquerda(posX, y);
                }

                if(!sujo){
                    robo.setDirecaoY(posY);
                    robo.setDirecaoX(posX);
                    robo.setDirecao(AcaoAgente.MOVER_FRENTE);
                }

            }

            if(sujo){
                robo.setDirecaoY(posY);
                robo.setDirecaoX(posX);
                robo.setDirecao(AcaoAgente.MOVER_FRENTE);
                motor.executarAcao(sujo, ambiente, robo, aspirador);
                qtdSujeiraLimpa++;
            }
        }

        return sujo;
    }

    private boolean verificaSujeiraEsquerda(int posX, int posY) {
        posX = posX -1;
        if (posX >= 0 && !ambiente.isObstaculo(posX, posY)) {
            // verifica esquerda
            sujo = ambiente.isSujo(posX, posY );
            limpo = ambiente.isClean(posX, posY);

            if(limpo){
                for (int x = posX; x >= 0; x--) {
                    sujo = ambiente.isSujo(x, posY);

                    if (ambiente.isObstaculo(x, posY)){break;}

                    if(sujo){
                        robo.setDirecaoY(posY);
                        robo.setDirecaoX(x);
                        robo.setDirecao(AcaoAgente.GIRAR_ESQUERDA);
                        motor.executarAcao(sujo, ambiente, robo, aspirador);
                        qtdSujeiraLimpa++;
                    }
                    verificaSujeiraFrente(x, posY);
                    verificaSujeiraTras(x, posY);
                }

                if(!sujo){
                    robo.setDirecaoY(posY);
                    robo.setDirecaoX(posX);
                    robo.setDirecao(AcaoAgente.GIRAR_ESQUERDA);
                }
            }

            if(sujo){
                robo.setDirecaoY(posY);
                robo.setDirecaoX(posX);
                robo.setDirecao(AcaoAgente.GIRAR_ESQUERDA);
                motor.executarAcao(sujo, ambiente, robo, aspirador);
                qtdSujeiraLimpa++;
            }
        }

        return sujo;
    }

    private boolean verificaSujeiraTras(int posX, int posY) {
//        posX = robo.getPosX();
//        posY = robo.getPosY();

        posY = posY + 1;
        if (posY < tamanhoAmbienteY-1 && !ambiente.isObstaculo(posX, posY)) {
            // verifica trás

            sujo = ambiente.isSujo(posX,posY );
            limpo = ambiente.isClean(posX,posY);

            if(limpo){
                for (int y = posY; y <= tamanhoAmbienteY-1; y++) {
                    sujo = ambiente.isSujo(posX, y);
                    obstaculo = ambiente.isObstaculo(posX, y);

                    if (obstaculo){ break;}

                    if(sujo){
                        robo.setDirecaoY(y);
                        robo.setDirecaoX(posX);
                        robo.setDirecao(AcaoAgente.MOVER_TRAS);
                        motor.executarAcao(sujo, ambiente, robo, aspirador);
                        reset();
                        qtdSujeiraLimpa++;
                    }
                    verificaSujeiraDireita(posX,posY);
                    verificaSujeiraEsquerda(posX,posY);
                }

                if(!sujo){
                    robo.setDirecaoY(posY);
                    robo.setDirecaoX(posX);
                    robo.setDirecao(AcaoAgente.MOVER_TRAS);
                }
            }

            if(sujo){
                robo.setDirecaoY(posY);
                robo.setDirecaoX(posX);
                robo.setDirecao(AcaoAgente.MOVER_TRAS);
                motor.executarAcao(sujo, ambiente, robo, aspirador);
            }
        }
        return sujo;
    }

    private boolean verificaSujeiraDireita(int posX, int posY) {
        posX = posX + 1;
        if (posX <= tamanhoAmbienteX-1 && !ambiente.isObstaculo(posX, posY)) {
            // verifica direita
            sujo = ambiente.isSujo(posX, posY);
            limpo = ambiente.isClean(posX, posY);

            if(limpo){
                for (int x = posX; x <= tamanhoAmbienteX-1; x++) {
                    sujo = ambiente.isSujo(x, posY);

                    if (ambiente.isObstaculo(x, posY)){break;}

                    if(sujo){
                        robo.setDirecaoY(posY);
                        robo.setDirecaoX(x);
                        robo.setDirecao(AcaoAgente.GIRAR_DIREITA);
                        motor.executarAcao(sujo, ambiente, robo, aspirador);
                        qtdSujeiraLimpa++;
                    }
                    verificaSujeiraFrente(x,posY);
                    verificaSujeiraTras(x,posY);
                }

                if(!sujo){
                    robo.setDirecaoY(posY);
                    robo.setDirecaoX(posX);
                    robo.setDirecao(AcaoAgente.GIRAR_DIREITA);
                }
            }

            if(sujo){
                robo.setDirecaoY(posY);
                robo.setDirecaoX(posX);
                robo.setDirecao(AcaoAgente.GIRAR_DIREITA);
                motor.executarAcao(sujo, ambiente, robo, aspirador);
                qtdSujeiraLimpa++;
            }
        }

        return sujo;
    }
}