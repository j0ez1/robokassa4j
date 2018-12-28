package ml.jhub.robokassa.service;

import ml.jhub.robokassa.data.interfaces.FailedInfo;


/**
 * Интерфейс который предоставляет модулю данные об текущем номере платежа
 * 
 * @author igor.ch
 * 
 */
public interface OperationServiceProvider {

	/**
	 * При реализации функция должна инициализировать процесс создания операции
	 * по оплате услуги на стороне клиента
	 * 
	 * @param amount
	 *            сумма платежа
	 * @return уникальный идентфикатор платежа магазина.
	 */
	int createOperation(double amount);

	/**
	 * Установить операции статус провалена
	 * 
	 * @param model
	 *            модель данных с информацие об невыполнении операции
	 * @return истина если удалось установить статус операции, иначе ложь.
	 */
	boolean setFailed(FailedInfo model);

	/**
	 * Проверяет существует ли платеж с заданным идентификатором
	 * 
	 * @param nInvId
	 *            идентфикатор платежа внутри системы
	 * @return Истина если операция существует
	 */
	boolean exist(int nInvId);

	/**
	 * Возвращет сумму платежа по его номеру
	 * 
	 * @param nInvId
	 *            идентфикатор платежа внутри системы
	 * @return Сумма платежа
	 */
	String getSum(String nInvId);

	/**
	 * Метод вызвается когда платеж подтверждет
	 * 
	 * @param nInvId
	 *            идентфикатор платежа внутри системы
	 * @return Истина если завершение прошло успешно
	 */
	boolean complete(String nInvId);

	/**
	 * Обработать ошику которая призошла при завершении платежа
	 * 
	 * @param nInvId
	 *            Идентификатор платежа
	 */
	void processSuccessError(String nInvId);

	/**
	 * Обработчик ошибок которые произошли за время совершения плтежа
	 * 
	 * @param ex
	 *            Ошибка которая произошла
	 * @param string
	 *            Место в которйо произошла ошибка(success, confirm, failed)
	 */
	void processException(Exception ex, String string);

	void processInfo(String info);

}
