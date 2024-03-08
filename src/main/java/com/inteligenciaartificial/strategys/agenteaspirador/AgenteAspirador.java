package com.inteligenciaartificial.strategys.agenteaspirador;


import com.inteligenciaartificial.strategys.agenteaspirador.utils.InterfaceAgenteAspirador;
import com.inteligenciaartificial.strategys.ambiente.Ambiente;
import com.inteligenciaartificial.strategys.robo.Robo;
import com.inteligenciaartificial.strategys.sensor.Sensor;
public class AgenteAspirador implements InterfaceAgenteAspirador {
    private Sensor sensor;
    private Ambiente ambiente;

    public AgenteAspirador() {
        ambiente = new Ambiente();
        Robo robo = new Robo(ambiente);
        sensor = new Sensor(ambiente, robo);
    }

    @Override
    public void executar() {
        sensor.obterPercepcao();
    }
}
