package com.inteligenciaartificial.strategys.ambiente;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;
public class Ambiente {
    private int[][] grid; // Representação do ambiente: 0 - limpo, 1 - sujo, 2 - obstáculo - 3 robo
    @Getter @Setter
    private int sizeX; // TamanhoX do ambiente
    @Getter @Setter
    private int sizeY; // TamanhoY do ambiente

    @Getter @Setter
    private int qtdSujeira;

    public Ambiente() {
        initSizeEnviroment();
        initializeEnvironment();
    }

    public void setPosicaoRoboAtual(int posX, int posY){
        System.out.println("Posição atual do robo - posX: " + posX + " posX: " + posY);
        this.grid[posY][posX] = 3;
        countSujeira();
    }

    public void setPosicao(int posX, int posY){
        this.grid[posY][posX] = 0;
    }

    public void countSujeira(){
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                if (grid[y][x] == 1){
                    qtdSujeira++;
                }
            }
        }
    }

    private void initSizeEnviroment(){
        int max = 5;
        int min = 2;

        Random tamanhoAmbienteX = new Random();
        this.sizeX = tamanhoAmbienteX.nextInt(max - min +1) + min;

        Random tamanhoAmbienteY = new Random();
        this.sizeY = tamanhoAmbienteY.nextInt(max - min +1) + min;

        grid = new int[sizeY][sizeX];
    }

    private void initializeEnvironment() {
        Random random = new Random();
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                grid[y][x] = random.nextInt(3); // 0 - limpo, 1 - sujo, 2 - obstáculo
            }
        }
    }

    public boolean isClean(int x, int y) {
        return isValidPosition(x, y) && grid[y][x] == 0;
    }

    public boolean isSujo(int x, int y) {
        return isValidPosition(x, y) && grid[y][x] == 1;
    }

    public boolean isObstaculo(int x, int y) {
        return isValidPosition(x, y) && grid[y][x] == 2;
    }

    public void limpar(int x, int y) {
        if (isValidPosition(x, y))
            grid[y][x] = 0;
    }

    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x <= sizeX && y >= 0 && y <= sizeY;
    }
}
