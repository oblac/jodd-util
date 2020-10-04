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

 StringUtil_removeBenchmark.removeNew                a  thrpt    5  34347.155 ±  973.132  ops/ms
 StringUtil_removeBenchmark.removeNew               ba  thrpt    5  27705.444 ±  373.746  ops/ms
 StringUtil_removeBenchmark.removeNew              cab  thrpt    5  26276.031 ±  176.106  ops/ms
 StringUtil_removeBenchmark.removeNew            abcde  thrpt    5  24189.122 ± 1884.955  ops/ms
 StringUtil_removeBenchmark.removeNew  bcdefghaijklman  thrpt    5  15285.932 ± 1156.311  ops/ms
 StringUtil_removeBenchmark.removeOld                a  thrpt    5  56716.563 ±  561.894  ops/ms
 StringUtil_removeBenchmark.removeOld               ba  thrpt    5  40861.307 ±  459.259  ops/ms
 StringUtil_removeBenchmark.removeOld              cab  thrpt    5  40256.259 ± 1036.259  ops/ms
 StringUtil_removeBenchmark.removeOld            abcde  thrpt    5  38207.019 ±  375.905  ops/ms
 StringUtil_removeBenchmark.removeOld  bcdefghaijklman  thrpt    5  32188.114 ±  379.069  ops/ms

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
