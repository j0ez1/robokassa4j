package ml.jhub.robokassa.data.impl;

import ml.jhub.robokassa.data.interfaces.ResultInfo;

public class ResultInfoImpl extends PaymentInfoImpl implements ResultInfo {

    private String signatureValue;

    @Override
    public String getSignatureValue() {
        return signatureValue;
    }

    @Override
    public void setSignatureValue(String sSignatureValue) {
        this.signatureValue = sSignatureValue;
    }

}