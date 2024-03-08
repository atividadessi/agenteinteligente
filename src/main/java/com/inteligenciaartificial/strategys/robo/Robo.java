package com.inteligenciaartificial.strategys.robo;

import com.inteligenciaartificial.strategys.ambiente.Ambiente;
import java.util.Random;
import java.util.logging.Logger;

import com.inteligenciaartificial.strategys.enums.AcaoAgente;
import lombok.Getter;
import lombok.Setter;

public class Robo {
    @Getter @Setter private int posX;
    @Getter @Setter private int posY;

    @Getter @Setter private int direcaoX;
    @Getter @Setter private int direcaoY;
    @Getter @Setter private AcaoAgente direcao;
    private Ambiente ambiente;
    private static final Logger logger = Logger.getLogger(Robo.class.getName());

    public Robo(Ambiente ambiente) {
        this.ambiente = ambiente;
        initPositionRobo();
        this.ambiente.setPosicaoRoboAtual(posX, posY);
    }

    public void initPositionRobo(){
        int tamanhoAmbienteX = ambiente.getSizeX();
        int tamanhoAmbienteY = ambiente.getSizeY();

        Random posX = new Random();
        this.posX = posX.nextInt(tamanhoAmbienteX);

        Random posY = new Random();
        this.posY = posY.nextInt(tamanhoAmbienteY);
    }

    public void AtualizaPosicao(){
        posX = direcaoX;
        posY = direcaoY;
    }

    public void moverFrente(Ambiente ambiente) {
        ambiente.setPosicaoRoboAtual(direcaoX, direcaoY);
        ambiente.setPosicao(getPosX(), getPosY());
        AtualizaPosicao();
        logger.info("Movendo robo para frente na posição x:" + direcaoX + "e posição y:" + direcaoY);
    }

    public void moverTras(Ambiente ambiente) {
        ambiente.setPosicaoRoboAtual(direcaoX, direcaoY);
        ambiente.setPosicao(getPosX(), getPosY());
        AtualizaPosicao();
        logger.info("Movendo robo para tras na posição x:" + direcaoX + "e posição y:" + direcaoY);
    }

    public void girarDireita() {
        ambiente.setPosicaoRoboAtual(direcaoX, direcaoY);
        ambiente.setPosicao(getPosX(), getPosY());
        AtualizaPosicao();
        logger.info("Movendo robo para direita na posição x:" + direcaoX + "e posição y:" + direcaoY);
    }

    public void girarEsquerda() {
        ambiente.setPosicaoRoboAtual(direcaoX, direcaoY);
        ambiente.setPosicao(getPosX(), getPosY());
        AtualizaPosicao();
        logger.info("Movendo robo para esquerda na posição x:" + direcaoX + "e posição y:" + direcaoY);
    }
}
