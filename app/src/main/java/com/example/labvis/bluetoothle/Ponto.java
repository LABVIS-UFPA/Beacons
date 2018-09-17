package com.example.labvis.bluetoothle;

/*
 * @param double x, double y, double dist, String MAC
 * @see Os valores passados devem estar em cm.
 */
public class Ponto {
    private String MAC;
    private String nome;
    private double x;
    private double y;
    private double dist;
    private double rssi;

    public Ponto(String N, String M, double X, double Y){
        this.nome = N;
        this.MAC = M;
        this.x = X;
        this.y = Y;
        this.rssi = 0;
    }

    public String getNome() {
        return nome;
    }


    public String getMAC() {
        return MAC;
    }

    public double getX() {
        return x;
    }


    public double getY() {
        return y;
    }


    public double  getdist(){
        return dist;
    }

    public void setdist(double D){
        this.dist = D;
    }

    public double getRssi() {
        return rssi;
    }

    /*
    * Simple Low-Pass Filter
    * */
    public void setRssi(double RSSI) {
        if(this.rssi != 0){
            int smoothing = 53; //53
            this.rssi = this.rssi + (RSSI - this.rssi) / smoothing;
        }else  this.rssi = RSSI;
    }
}