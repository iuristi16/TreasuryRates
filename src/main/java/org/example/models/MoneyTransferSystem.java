package org.example.models;

import java.util.List;
import java.util.Objects;

public class MoneyTransferSystem {

    private String mtSystem;
    private String name;
    private String imageUrl;
    private List<String> currencies;



    public String getMtSystem() {
        return mtSystem;
    }

    public void setMtSystem(String mtSystem) {
        this.mtSystem = mtSystem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<String> currencies) {
        this.currencies = currencies;
    }



    @Override
    public String toString() {
        return "MoneyTransferSystem{" +
                "mtSystem='" + mtSystem + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", currencies=" + currencies +
                '}';
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoneyTransferSystem that = (MoneyTransferSystem) o;

        return Objects.equals(mtSystem, that.mtSystem) &&
                Objects.equals(name, that.name) &&
                Objects.equals(imageUrl, that.imageUrl) &&
                Objects.equals(currencies, that.currencies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mtSystem, name, currencies, imageUrl);
    }
}