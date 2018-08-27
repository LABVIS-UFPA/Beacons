package com.example.labvis.bluetoothle;

/*
 * @param double x, double y, double dist, String MAC
 * @see Os valores passados devem estar em cm.
 */
public class Ponto {
    private String MAC;
    private double x;
    private double y;
    private double dist;

    public Ponto(String M, double X, double Y){
        this.MAC = M;
        this.x = X;
        this.y = Y;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double  getdist(){
        return dist;
    }

    public void setdist(double D){
        this.dist = D;
    }

    //filtroMAC4 = "E6:DD:87:A7:8B:DB"; //BK2
}