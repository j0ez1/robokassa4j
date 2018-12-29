package ml.jhub.robokassa.data.impl;

import ml.jhub.robokassa.data.interfaces.SuccessInfo;

public class SuccessInfoImpl extends ResultInfoImpl implements SuccessInfo {

    private CultureInfoImpl cultureInfo = new CultureInfoImpl();

    @Override
    public String getCulture() {
        return cultureInfo.getCulture();
    }

    public void setCulture(String sCulture) {
        cultureInfo.setCulture(sCulture);
    }
}