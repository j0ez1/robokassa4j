package ml.jhub.robokassa.service;

import ml.jhub.robokassa.data.RoboSettings;
import ml.jhub.robokassa.data.interfaces.FailedInfo;
import ml.jhub.robokassa.data.interfaces.RequestData;
import ml.jhub.robokassa.data.interfaces.ResultInfo;
import ml.jhub.robokassa.data.interfaces.SuccessInfo;
import ml.jhub.robokassa.factory.RequestFactory;
import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Сервис работы с робокассой
 *
 * @author igor.ch
 *
 */
public class RoboService {

    public String mrchLogin;
    private OperationServiceProvider provider;
    private RoboSettings settings;
    private RequestFactory requestFactory;

    public RoboService(OperationServiceProvider provider, RoboSettings settings, RequestFactory requestFactory) {
        this.provider = provider;
        this.settings = settings;
        this.requestFactory = requestFactory;
    }

    public boolean failed(FailedInfo data) {
        try {
            return provider.setFailed(data);
        } catch (Exception ex) {
            provider.processException(ex, "failed");
            return false;
        }

    }

    /**
     * @return культура для пользователя
     */
    private String getCulture() {
        return settings.getCulture();
    }

    /**
     * Валюта по умолчанию
     *
     * @return
     */
    private String getCurrency() {
        return settings.getCurrency();
    }

    /**
     * @return Описание товара
     */
    private String getDescription() {
        return settings.getDescription();
    }

    private String getHash(StringBuilder builder)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.reset();
        messageDigest.update(builder.toString().getBytes("UTF8"));
        final byte[] resultByte = messageDigest.digest();
        final String result = new String(Hex.encodeHex(resultByte));
        return result.toUpperCase();
    }

    /**
     * @return Возвращает логин мерчента
     */
    private String getMrchLogin() {
        return settings.getLogin();
    }

    /**
     * Получить данные для запроса на сервер Робокассы.
     *
     * @return Данные для построения запроса инициализации оплаты к робокассе
     * @throws NoSuchAlgorithmException
     */
    public RequestData getRequestData(String sum) {
        try {
            int invoceId = provider.createOperation(Double.parseDouble(sum));
            RequestData data = requestFactory.create(invoceId, getMrchLogin(), sum, getCulture(), getCurrency(), getDescription());
            data.setSignatureValue(getRequestSignature(sum, invoceId));
            //new RequestDataImpl();
			/*
			 * 
			 
			data.setInvId(String.valueOf(invoceId));
			data.setMrchLogin(getMrchLogin());
			data.setOutSum(sum);
			data.setCulture(getCulture());
			data.setIncCurrLabel(getCurrency());
			data.setIncDesc(getDescription());
			data.setSignatureValue(getRequestSignature(sum, invoceId));
			
			*/
            return data;
        } catch (Exception ex) {
            provider.processException(ex, "request");
        }
        return null;
    }

    /**
     * @return MD5 подпись при инициализации оплаты в UpperCase
     * @throws NoSuchAlgorithmException
     */
    private String getRequestSignature(String sum, int invoiceId)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // sMerchantLogin:nOutSum:nInvId:sMerchantPass1
        StringBuilder builder = new StringBuilder();
        builder.append(settings.getLogin());
        builder.append(":");
        builder.append(sum);
        builder.append(":");
        builder.append(String.valueOf(invoiceId));
        builder.append(":");
        builder.append(settings.getPass1());
        return getHash(builder);
    }

    /**
     * Хэш данные который получается при проверке resultUrl
     *
     * @param sum    сумма платежа
     * @param invoce номер платежа внутри системы
     * @return Хэш строка в UpperCase
     * @throws NoSuchAlgorithmException
     */
    private String getResultHash(String sum, String invoce)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // nOutSum:nInvId:sMerchantPass2
        StringBuilder builder = new StringBuilder();
        builder.append(sum);
        builder.append(":");
        builder.append(invoce);
        builder.append(":");
        builder.append(settings.getPass2());
        return getHash(builder);
    }

    /**
     * Получить хэш для обработки успешного завершения операции оплаты
     *
     * @param nInvId
     * @param sum
     * @return
     * @throws NoSuchAlgorithmException, UnsupportedEncodingException
     */
    private String getSuccessHash(String nInvId, String sum)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // nOutSum:nInvId:sMerchantPass1
        StringBuilder builder = new StringBuilder();
        builder.append(sum);
        builder.append(":");
        builder.append(nInvId);
        builder.append(":");
        builder.append(settings.getPass1());
        return getHash(builder);
    }

    /**
     * Запрос обрабатывает успешное получение данных на счет фирмы продовца
     *
     * @param data данные об успешном завершении операции
     * @return Успешна ли была операция
     */
    public boolean success(SuccessInfo data) {

        try {
            String hash = getSuccessHash(data.getInvId(),
                    provider.getSum(data.getInvId()));
            String roboHash = data.getSignatureValue().toUpperCase();
            provider.processInfo("success sociomHash: " + hash);
            provider.processInfo("robo incomeHash: " + roboHash);
            if (hash.equals(roboHash)) {
                provider.complete(data.getInvId());
                return true;
            }
            provider.processSuccessError(data.getInvId());

        } catch (Exception ex) {
            provider.processException(ex, "success");
        }
        return false;

    }

    /**
     * Метод должен быть использован на resultUrl для подтверждения оплаты
     *
     * @param data Данные которые пришли с сервера Robokassa
     * @throws NoSuchAlgorithmException
     * @return строку подтверждения или ошибку
     */
    public String сonfirm(ResultInfo data) {

        String result = "FAILED";
        try {

            provider.processInfo("Confirming: " + data.getInvId());
            boolean exist = provider.exist(Integer.parseInt(data.getInvId()));
            provider.processInfo("Exist: " + exist);
            String sociomHash = getResultHash(data.getOutSum(), data.getInvId());
            String incomeHash = data.getSignatureValue().toUpperCase();
            provider.processInfo("sociomHash: " + sociomHash);
            provider.processInfo("incomeHash: " + incomeHash);
            if (exist && sociomHash.equals(incomeHash)) {
                result = "OK" + data.getInvId();
            }

        } catch (Exception ex) {
            provider.processException(ex, "confirm");
        }
        return result;
    }

}
