package com.example.labvis.bluetoothle;

import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Localization {

    protected double calcularDistancia(double txPower, double RSSI){
        double rssi = Math.abs(RSSI);
       /*
        double rssiCorrection = 0.96D + Math.pow(Math.abs(rssi), 3.0D) % 10.0D / 150.0D;

        if(rssi == 0) {
            return -1.0D;
        }
        double r = rssi*1.0D/txPower;
        if(r<1.0D){
            return RCD(Math.pow(r,8D) * rssiCorrection);
            //return RCD(Math.pow(r,8D));
        }
        double accuracy = (0.69976D * Math.pow(r,7.7095D) + 0.111D) * rssiCorrection;
        //double accuracy = (0.69976D * Math.pow(r,7.7095D) + 0.111D);
        return RCD(accuracy);
       */
       return RCD(Math.pow(10d, ((double) txPower - RSSI)/20d));
        //return RCD(Math.pow(10d, ((double) txPower - RSSI) / (10 * 1.8d)));
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
        /*double T[] = new double[2];
        T[0] = (P1.getX() - P2.getX())/100;
        T[1] = (P1.getY() - P2.getY())/100;
        double dist = Math.sqrt( (T[0]*T[0]) + (T[1]*T[1]) );
        */
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

