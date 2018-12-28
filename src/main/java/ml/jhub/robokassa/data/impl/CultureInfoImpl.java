package ml.jhub.robokassa.data.impl;

import ml.jhub.robokassa.data.interfaces.CultureInfo;

public class CultureInfoImpl implements CultureInfo {

    private String сulture;

    @Override
    public String getCulture() {
        return сulture;
    }

    public void setCulture(String sCulture) {
        this.сulture = sCulture;
    }
}