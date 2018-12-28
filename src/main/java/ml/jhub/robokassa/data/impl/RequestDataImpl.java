package ml.jhub.robokassa.data.impl;

import ml.jhub.robokassa.data.interfaces.RequestData;

import java.util.HashMap;
import java.util.Map;

public class RequestDataImpl extends SuccessInfoImpl implements RequestData {

    private String incCurrLabel;
    private String mrchLogin;
    private Map<String, String> userParameters = new HashMap<String, String>();
    private String invDesc;

    @Override
    public String getMrchLogin() {
        return this.mrchLogin;
    }

    @Override
    public String getInvDesc() {
        return invDesc;
    }

    @Override
    public String getIncCurrLabel() {
        return incCurrLabel;
    }

    public void setIncCurrLabel(String sIncCurrLabel) {
        this.incCurrLabel = sIncCurrLabel;
    }

    public void setMrchLogin(String mrchLogin) {
        this.mrchLogin = mrchLogin;
    }

    public Map<String, String> getUserParameters() {
        return userParameters;
    }

    public void setUserParameters(Map<String, String> userParameters) {
        this.userParameters = userParameters;
    }

    public void setInvDesc(String invDesc) {
        this.invDesc = invDesc;
    }

}