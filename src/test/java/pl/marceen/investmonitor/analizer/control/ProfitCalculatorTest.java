package pl.marceen.investmonitor.analizer.control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.marceen.investmonitor.analizer.entity.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * @author Marcin Zaremba
 */
class ProfitCalculatorTest {
    private ProfitCalculator sut;

    @BeforeEach
    void setUp() {
        sut = new ProfitCalculator();
    }

    @Test
    void calculate() {
        // given
        List<Data> dataList = Arrays.asList(
                new Data().setDate(LocalDate.parse("2020-09-07")).setValue(BigDecimal.valueOf(224.56)).setDeviation(BigDecimal.valueOf(0.121)),
                new Data().setDate(LocalDate.parse("2020-09-08")).setValue(BigDecimal.valueOf(225.56)).setDeviation(BigDecimal.valueOf(0.6)),
                new Data().setDate(LocalDate.parse("2020-09-09")).setValue(BigDecimal.valueOf(225.56)).setDeviation(BigDecimal.valueOf(0.7)),
                new Data().setDate(LocalDate.parse("2020-09-10")).setValue(BigDecimal.valueOf(225.56)).setDeviation(BigDecimal.valueOf(-1.7)),
                new Data().setDate(LocalDate.parse("2020-09-11")).setValue(BigDecimal.valueOf(225.56)).setDeviation(BigDecimal.valueOf(-1.2)),
                new Data().setDate(LocalDate.parse("2020-09-12")).setValue(BigDecimal.valueOf(225.56)).setDeviation(BigDecimal.valueOf(-0.4)),
                new Data().setDate(LocalDate.parse("2020-09-13")).setValue(BigDecimal.valueOf(225.56)).setDeviation(BigDecimal.valueOf(0.2)),
                new Data().setDate(LocalDate.parse("2020-09-14")).setValue(BigDecimal.valueOf(225.56)).setDeviation(BigDecimal.valueOf(0.5)),
                new Data().setDate(LocalDate.parse("2020-09-15")).setValue(BigDecimal.valueOf(225.56)).setDeviation(BigDecimal.valueOf(0.9)),
                new Data().setDate(LocalDate.parse("2020-09-16")).setValue(BigDecimal.valueOf(225.56)).setDeviation(BigDecimal.valueOf(1.9))

        );

        // when
        BigDecimal result = sut.calculate(dataList, new BigDecimal(10000L), BigDecimal.valueOf(0.5), BigDecimal.valueOf(-0.9), 3);

        // then
    }
}