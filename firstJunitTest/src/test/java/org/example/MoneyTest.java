package org.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MoneyTest {
    @Test
    public void constructorShouldSetAmountAndCurrency() {
        Money money = new Money(10, "USD"); //An SUT is created.

        //The SUT is put to the test
        assertThat(money.getAmount()).isEqualTo(10);
        assertThat(money.getCurrency()).isEqualTo("USD");
    }

}