package jodd.util;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;

/**
 *
 * <pre>
gradlew StringUtil_removeBenchmark

StringUtil_removeBenchmark.removeNew                a  thrpt    5  29089.686 ± 222.621  ops/ms
StringUtil_removeBenchmark.removeNew               ba  thrpt    5  24278.007 ± 145.268  ops/ms
StringUtil_removeBenchmark.removeNew              cab  thrpt    5  22196.510 ± 212.803  ops/ms
StringUtil_removeBenchmark.removeNew            abcde  thrpt    5  23367.545 ± 430.193  ops/ms
StringUtil_removeBenchmark.removeNew  bcdefghaijklman  thrpt    5  17268.509 ± 163.351  ops/ms
StringUtil_removeBenchmark.removeOld                a  thrpt    5  57116.090 ± 881.963  ops/ms
StringUtil_removeBenchmark.removeOld               ba  thrpt    5  40605.504 ± 463.230  ops/ms
StringUtil_removeBenchmark.removeOld              cab  thrpt    5  40103.663 ± 609.040  ops/ms
StringUtil_removeBenchmark.removeOld            abcde  thrpt    5  35670.546 ± 535.558  ops/ms
StringUtil_removeBenchmark.removeOld  bcdefghaijklman  thrpt    5  29990.477 ± 348.026  ops/ms

 * </pre>
 */
@Fork(1)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class StringUtil_removeBenchmark {

	public static String removeOld(final String string, final char ch) {
		final int stringLen = string.length();
		final char[] result = new char[stringLen];
		int offset = 0;

		for (int i = 0; i < stringLen; i++) {
			final char c = string.charAt(i);

			if (c == ch) {
				continue;
			}

			result[offset] = c;
			offset++;
		}

		if (offset == stringLen) {
			return string;
		}

		return new String(result, 0, offset);
	}

	@Param({"a", "ba", "cab", "abcde", "bcdefghaijklman"})
	public String source;

	@Benchmark
	public String removeNew() {
		return StringUtil.remove(source, 'a');
	}

	@Benchmark
	public String removeOld() {
		return removeOld(source, 'a');
	}

}
