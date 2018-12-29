package ml.jhub.robokassa.factory;

import ml.jhub.robokassa.data.impl.RequestDataImpl;
import ml.jhub.robokassa.data.interfaces.RequestData;

/**
 * Factory to create request for Robokassa server
 *
 * @author Orphey
 */
public class RequestFactory {

    /**
     * Create request for payment.
     *
     * @param invoceId    Id of invoice
     * @param mrchLogin   Login of merchant in robocassa
     * @param sum        Sum of the payment
     * @param culture     Culture of the payment
     * @param currency    Currency of the payment
     * @param description Description of the payment
     * @return Data that will be sended to the robokassa service
     */
    public RequestData create(int invoceId, String mrchLogin,
                              String sum, String culture, String currency, String description) {
        RequestDataImpl result = new RequestDataImpl();
        result.setInvId(String.valueOf(invoceId));
        result.setMrchLogin(mrchLogin);
        result.setOutSum(sum);
        result.setCulture(culture);
        result.setIncCurrLabel(currency);
        result.setInvDesc(description);
        return result;
    }

}
