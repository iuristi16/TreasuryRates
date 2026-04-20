package org.example.models;

public class ForwardRate {

    private String iso1;
    private String iso2;
    private String period;
    private int day;

    private double bidForwardPoint;
    private double bidForwardInterest;
    private double bidForwardRate;

    private double askForwardPoint;
    private double askForwardInterest;
    private double askForwardRate;

    public String getIso1() {
        return iso1;
    }

    public void setIso1(String iso1) {
        this.iso1 = iso1;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public double getBidForwardPoint() {
        return bidForwardPoint;
    }

    public void setBidForwardPoint(double bidForwardPoint) {
        this.bidForwardPoint = bidForwardPoint;
    }

    public double getBidForwardInterest() {
        return bidForwardInterest;
    }

    public void setBidForwardInterest(double bidForwardInterest) {
        this.bidForwardInterest = bidForwardInterest;
    }

    public double getBidForwardRate() {
        return bidForwardRate;
    }

    public void setBidForwardRate(double bidForwardRate) {
        this.bidForwardRate = bidForwardRate;
    }

    public double getAskForwardPoint() {
        return askForwardPoint;
    }

    public void setAskForwardPoint(double askForwardPoint) {
        this.askForwardPoint = askForwardPoint;
    }

    public double getAskForwardInterest() {
        return askForwardInterest;
    }

    public void setAskForwardInterest(double askForwardInterest) {
        this.askForwardInterest = askForwardInterest;
    }

    public double getAskForwardRate() {
        return askForwardRate;
    }

    public void setAskForwardRate(double askForwardRate) {
        this.askForwardRate = askForwardRate;
    }
}