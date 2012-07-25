/**
 * Created 24.07.2012
 * This code is copyright (c) 2004 PAYONE Gmbh & Co. KG.
 */
package de.gzockoll.tools.currency;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import de.payone.payment.gpc.Money;

/**
 * @author Guido Zockoll
 * 
 */
public class TabularExchangeRateProvider implements ExchangeRateProvider, CurrencyConverter {
    private Map<CurrencyConversion, BigDecimal> exchangeRates = new HashMap<CurrencyConversion, BigDecimal>();

    /*
     * (non-Javadoc)
     * 
     * @see de.payone.common.currency.ExchangeRateProvider#getExchangeRate(java.util.Currency, java.util.Currency)
     */
    public BigDecimal getExchangeRate(Currency from, Currency to) {
        CurrencyConversion key = new CurrencyConversion(from, to);
        return exchangeRates.get(key);
    }

    public void setExchangeRate(Currency from, Currency to, BigDecimal rate) {
        CurrencyConversion key = new CurrencyConversion(from, to);
        exchangeRates.put(key, rate);
    }

    private static class CurrencyConversion {
        private Currency from;
        private Currency to;

        /**
         * Create a new CurrencyConversion.
         * 
         * @param from
         * @param to
         */
        private CurrencyConversion(Currency from, Currency to) {
            super();
            this.from = from;
            this.to = to;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            return EqualsBuilder.reflectionEquals(this, obj);
        }
    }

    public Money convertTo(Currency to, Money m) {
        return convertTo(to, m, this);
    }

    public Money convertTo(Currency to, Money m, ExchangeRateProvider erp) {
        Validate.notNull(erp.getExchangeRate(m.getUnit(), to), "No exchangerate defined for "
                + m.getUnit().getCurrencyCode() + " -> " + to.getCurrencyCode());
        return convertTo(to, m, erp.getExchangeRate(m.getUnit(), to));
    }

    public Money convertTo(Currency to, Money m, BigDecimal rate) {
        return new Money(m.getAmount().multiply(rate), to);
    }
}
