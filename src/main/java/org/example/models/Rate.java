package org.example.models;
import java.util.List;

public class Rate {

    private String iso;
    private List<ForwardRate> forwardRates;

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public List<ForwardRate> getForwardRates() {
        return forwardRates;
    }

    public void setForwardRates(List<ForwardRate> forwardRates) {
        this.forwardRates = forwardRates;
    }
}