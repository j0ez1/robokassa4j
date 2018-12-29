package ml.jhub.robokassa.data.impl;

import ml.jhub.robokassa.data.interfaces.FailedInfo;

public class FailedInfoImpl extends PaymentInfoImpl implements FailedInfo {

    private CultureInfoImpl culture = new CultureInfoImpl();

    @Override
    public String getCulture() {
        return culture.getCulture();
    }

    public void setCulture(String sCulture) {
        culture.setCulture(sCulture);
    }
}
