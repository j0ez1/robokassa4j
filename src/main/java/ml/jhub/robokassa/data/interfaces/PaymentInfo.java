package ml.jhub.robokassa.data.interfaces;

public interface PaymentInfo {

    /**
     * @return требуемая к получению сумма.
     */
    public abstract String getOutSum();

    /**
     * @returnномер счета в магазине (должен быть уникальным для магазина)
     */
    public String getInvId();

}