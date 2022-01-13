package pl.marceen.investmonitor.analizer.control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Marcin Zaremba
 */
class BestPriceCalculatorTest {
    private BestPriceCalculator sut;

    @BeforeEach
    void setUp() {
        sut = new BestPriceCalculator();
    }

    @Test
    void calculate() {
        // given
        BigDecimal average = new BigDecimal("195.14");
        BigDecimal threshold = BigDecimal.valueOf(-4.2);

        // when
        BigDecimal result = sut.calculate(average, threshold);

        // then
        assertThat(result).isEqualTo(new BigDecimal("186.94"));
    }
}