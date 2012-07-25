/**
 * Created 24.07.2012
 * This code is copyright (c) 2004 PAYONE Gmbh & Co. KG.
 */
package de.gzockoll.tools.currency;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * @author Guido Zockoll
 * 
 */
public interface ExchangeRateProvider {
    BigDecimal getExchangeRate(Currency from, Currency to);
}
