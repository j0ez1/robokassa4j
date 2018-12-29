package ml.jhub.robokassa.data.impl;

import ml.jhub.robokassa.data.interfaces.CultureInfo;

public class CultureInfoImpl implements CultureInfo {

    private String culture;

    @Override
    public String getCulture() {
        return culture;
    }

    public void setCulture(String sCulture) {
        this.culture = sCulture;
    }
}