package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class MoneyTest {
    @ParameterizedTest
    @CsvSource({"10,USD", "15, EUR","50,CHF"})
    void constructorShouldSetAmountAndCurrency(int amount, String currency) {
        Money money = new Money(amount, currency); //An SUT is created.

        //The SUT is put to the test
        assertThat(money.getAmount()).isEqualTo(amount);
        assertThat(money.getCurrency()).isEqualTo(currency);
    }

    private final static String VALID_CURRENCY = "USD";

    @ParameterizedTest
    @ValueSource( ints = {-12345,-5,-1})
    void constructorSHouldThrowIAEForInvalidAmount(int invalidAmount){
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            new Money(invalidAmount, VALID_CURRENCY);
        });
    }

}