package ml.jhub.robokassa.data.impl;

import ml.jhub.robokassa.data.interfaces.PaymentInfo;

public class PaymentInfoImpl implements PaymentInfo {

    private String outSum;

    private String invId;

    @Override
    public String getOutSum() {
        return outSum;
    }

    @Override
    public String getInvId() {
        return invId;
    }

    public void setOutSum(String nOutSum) {
        this.outSum = nOutSum;
    }

    public void setInvId(String nInvId) {
        this.invId = nInvId;
    }
}