package com.example.labvis.bluetoothle;

import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Localization {

    protected double calcularDistancia(double RSSItx, double RSSI){
       return RCD(0.89976D * (Math.pow(RSSI/RSSItx, 7.7095D)) + 0.111D);
       //return RCD(0.42093D * (Math.pow(RSSI/RSSItx, 6.9476D)) + 0.54992D);
       //return RCD(Math.pow(10d, ((double) RSSItx - RSSI)/20d));
    }

    //Descobrir coordenadas x e y de um ponto desconhecido
    protected double[] descobrirCoordenadas(Ponto P1, Ponto P2, Ponto P3){
        double coordenada[] = new double[2];
        double T[] = new double[2];
        T[0] = (P3.getX() - P2.getX())/100; // Distância entre as coordenadas x e y dos pontos conhecidos
        T[1] = (P1.getY() - P2.getY())/100; // Dividir por 100 p/ converter de cm p/ metros

        // Verificar se x, y estão na ordem correta, ou se estão trocados.
        coordenada[0] = ((P1.getdist()*P1.getdist()) - (P2.getdist()*P2.getdist()) - (T[0]*T[0])) / -(2*T[0]);
        coordenada[1] = ((P3.getdist()*P3.getdist()) - (P2.getdist()*P2.getdist()) - (T[1]*T[1])) / -(2*T[1]);
        return coordenada;
    }


    public double[] coordenadasLinha(Ponto P1, Ponto P2){
        double coordenada[] = new double[2];
        coordenada[0] = 0; // valor do x (canvas)
        coordenada[1] = ((P1.getdist() - (P1.getX()/100)) + ((P2.getX()/100) - P2.getdist()))/2; // valor do y (canvas)
        return coordenada;
    }

    public boolean containsDevice(List<Ponto> L, String deviceMAC){
        for(int i = 0; i < L.size(); i++) {
            if(L.get(i).getMAC().equals(deviceMAC)){
                return TRUE;
            }
        }
        return FALSE;
    }

    public double RCD(double valor){
        int aux = (int)(valor*100);
        valor = (double)aux/100;
        return valor;
    }
}