package ml.jhub.robokassa.data.interfaces;

public interface PaymentInfo {

    /**
     * @return требуемая к получению сумма.
     */
    String getOutSum();

    /**
     * @returnномер счета в магазине (должен быть уникальным для магазина)
     */
    String getInvId();

}