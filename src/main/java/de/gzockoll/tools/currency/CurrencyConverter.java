/**
 * Created 24.07.2012
 * This code is copyright (c) 2004 PAYONE Gmbh & Co. KG.
 */
package de.gzockoll.tools.currency;

import java.math.BigDecimal;
import java.util.Currency;

import de.payone.payment.gpc.Money;

/**
 * @author Guido Zockoll
 * 
 */
public interface CurrencyConverter {

    Money convertTo(Currency to, Money m, BigDecimal rate);

    Money convertTo(Currency to, Money m, ExchangeRateProvider erp);

    Money convertTo(Currency to, Money m);
}
