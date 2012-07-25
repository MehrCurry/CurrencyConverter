/**
 * Created 24.07.2012
 * This code is copyright (c) 2004 PAYONE Gmbh & Co. KG.
 */
package de.gzockoll.tools.currency;

import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import de.gzockoll.tools.currency.ExchangeRateProvider;
import de.gzockoll.tools.currency.TabularExchangeRateProvider;
import de.payone.payment.gpc.Money;
import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.core.IsNull.*;

/**
 * @author Guido Zockoll
 * 
 */
public class TabularExchangeRateProviderTest {
    private static final Currency EUR = Currency.getInstance(Locale.GERMANY);
    private static final Currency USD = Currency.getInstance(Locale.US);
    private TabularExchangeRateProvider cut;

    @Before
    public void setUp() {
        this.cut = new TabularExchangeRateProvider();
    }

    /**
     * Test method for
     * {@link de.gzockoll.tools.currency.TabularExchangeRateProvider#setExchangeRate(java.util.Currency, java.util.Currency, java.math.BigDecimal)}
     * .
     */
    @Test
    public void testSetAndGetExchangeRate() {
        cut.setExchangeRate(EUR, USD, new BigDecimal(4l));
        assertThat(cut.getExchangeRate(EUR, USD), is(new BigDecimal((4l))));
        assertThat(cut.getExchangeRate(USD, EUR), is(nullValue()));
    }

    /**
     * Test method for
     * {@link de.gzockoll.tools.currency.TabularExchangeRateProvider#convertTo(java.util.Currency, de.payone.payment.gpc.Money)}
     * .
     */
    @Test
    public void testConvertToCurrencyMoney() {
        cut.setExchangeRate(EUR, USD, new BigDecimal(4l));
        Money m1 = Money.fromMinor(199, EUR);
        Money result = cut.convertTo(USD, m1, cut);
        assertThat(result, is(Money.fromMinor(796, USD)));
    }

    /**
     * Test method for
     * {@link de.gzockoll.tools.currency.TabularExchangeRateProvider#convertTo(java.util.Currency, de.payone.payment.gpc.Money, de.gzockoll.tools.currency.ExchangeRateProvider)}
     * .
     */
    @Test
    public void testConvertToCurrencyMoneyExchangeRateProvider() {
        ExchangeRateProvider erp = mock(ExchangeRateProvider.class);
        when(erp.getExchangeRate((Currency) any(), (Currency) any())).thenReturn(new BigDecimal(3));
        Money m1 = Money.fromMinor(199, EUR);
        Money result = cut.convertTo(USD, m1, erp);
        assertThat(result, is(Money.fromMinor(597, USD)));
    }

    /**
     * Test method for
     * {@link de.gzockoll.tools.currency.TabularExchangeRateProvider#convertTo(java.util.Currency, de.payone.payment.gpc.Money, java.math.BigDecimal)}
     * .
     */
    @Test
    public void testConvertToCurrencyMoneyBigDecimal() {
        Money m1 = Money.fromMinor(199, EUR);
        Money result = cut.convertTo(USD, m1, new BigDecimal(2l));
        assertThat(result, is(Money.fromMinor(398, USD)));
    }
}
