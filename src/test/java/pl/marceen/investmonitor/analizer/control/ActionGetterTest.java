package pl.marceen.investmonitor.analizer.control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.marceen.investmonitor.analizer.entity.Action;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Marcin Zaremba
 */
class ActionGetterTest {
    private static final BigDecimal EXIT = new BigDecimal("-2");
    private static final BigDecimal ENTRY = new BigDecimal("3");

    private ActionGetter sut;

    @BeforeEach
    void setUp() {
        sut = new ActionGetter();
    }

    @ParameterizedTest
    @MethodSource("provider")
    void get(BigDecimal deviation, BigDecimal exit, BigDecimal entry, Action expected) {
        // given

        // when
        Action result = sut.get(deviation, exit, entry);

        // then
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> provider() {
        return Stream.of(
                Arguments.of(new BigDecimal("2.5"), EXIT, ENTRY, Action.STAY),
                Arguments.of(new BigDecimal("-2.5"), EXIT, ENTRY, Action.SELL),
                Arguments.of(new BigDecimal("3.2"), EXIT, ENTRY, Action.BUY)
        );
    }
}