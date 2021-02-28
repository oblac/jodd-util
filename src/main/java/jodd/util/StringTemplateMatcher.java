package jodd.util;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

public class StringTemplateMatcher {

	public static StringTemplateMatcher of(final String pattern) {
		return new StringTemplateMatcher(pattern);
	}

	private static final Match[] NO_MATCH = new Match[0];

	public static final String DEFAULT_MACRO_PREFIX = "{";
	public static final String DEFAULT_MACRO_SPLIT = ":";
	public static final String DEFAULT_MACRO_END = "}";

	private String prefix = DEFAULT_MACRO_PREFIX;
	private String split = DEFAULT_MACRO_SPLIT;
	private String suffix = DEFAULT_MACRO_END;

	private final String pattern;

	public StringTemplateMatcher(final String pattern) {
		this.pattern = pattern;
	}

	public static class Match {
		private final String name;		// macros names
		private final String pattern;	// macros patterns, if defined, elements may be null
		private final String value;

		public Match(final String name, final String pattern, final String value) {
			this.name = name;
			this.pattern = pattern;
			this.value = value;
		}

		/**
		 * Returns macro name.
		 */
		public String name() {
			return name;
		}

		/**
		 * Returns macro pattern, if any. Otherwise returns {@code null}.
		 */
		public String pattern() {
			return pattern;
		}

		/**
		 * Returns resolved value.
		 */
		public String value() {
			return value;
		}
	}

	// ---------------------------------------------------------------- bifn

	private final BiFunction<Integer, String, Boolean> REGEX = new BiFunction<Integer, String, Boolean>() {
		private Pattern[] regexpPattern;

		@Override
		public Boolean apply(final Integer macroIndex, final String value) {
			if (regexpPattern == null) {
				regexpPattern = new Pattern[compiled.macrosCount];
			}
			if (regexpPattern[macroIndex] == null) {
				regexpPattern[macroIndex] = Pattern.compile(compiled.patterns[macroIndex]);
			}

			return regexpPattern[macroIndex].matcher(value).matches();
		}
	};

	private final BiFunction<Integer, String, Boolean> WILDCARD = new BiFunction<Integer, String, Boolean>() {
		@Override
		public Boolean apply(final Integer macroIndex, final String value) {
			return Wildcard.matchPath(value, compiled.patterns[macroIndex]);
		}
	};

	// ---------------------------------------------------------------- set

	public StringTemplateMatcher setMacroPrefix(final String prefix) {
		this.prefix = Objects.requireNonNull(prefix);
		compiled = null;
		return this;
	}
	public StringTemplateMatcher setMacroSuffix(final String suffix) {
		this.suffix = Objects.requireNonNull(suffix);
		compiled = null;
		return this;
	}
	public StringTemplateMatcher setMacroSplit(final String split) {
		this.split = Objects.requireNonNull(split);
		compiled = null;
		return this;
	}

	private BiFunction<Integer, String, Boolean> matchValue = REGEX;

	/**
	 * Uses wildcard matching patterns.
	 * @see Wildcard
	 */
	public StringTemplateMatcher useWildcardMatch() {
		this.matchValue = WILDCARD;
		return this;
	}

	/**
	 * Uses regex matching patterns.
	 */
	public StringTemplateMatcher useRegexMatch() {
		this.matchValue = REGEX;
		return this;
	}

	private StringTemplateMatcherCompiled compiled;

	/**
	 * Compiles the given pattern so it can be used for matching.
	 * It is invoked by {@link #match(String)} and {@link #matches(String)}
	 * methods, so need to do it manually. You can call it any number of times,
	 * the pattern is compiled only once.
	 */
	public StringTemplateMatcherCompiled compile() {
		if (compiled != null) {
			return compiled;
		}

		final int macrosCount = StringUtil.count(pattern, prefix);

		if (macrosCount == 0) {
			return new StringTemplateMatcherCompiled();
		}

		compiled = new StringTemplateMatcherCompiled(macrosCount);

		int offset = 0;
		int i = 0;

		while (true) {
			final int[] ndx = StringUtil.indexOfRegion(pattern, prefix, suffix, offset);

			if (ndx == null) {
				break;
			}

			compiled.fixed[i] = pattern.substring(offset, ndx[0]);

			String name = pattern.substring(ndx[1], ndx[2]);

			// name:pattern
			String pattern = null;

			final int colonNdx = name.indexOf(split);
			if (colonNdx != -1) {
				pattern = name.substring(colonNdx + 1).trim();

				name = name.substring(0, colonNdx).trim();
			}

			compiled.patterns[i] = pattern;
			compiled.names[i] = name;

			// iterate
			offset = ndx[3];
			i++;
		}

		if (offset < pattern.length()) {
			compiled.fixed[i] = pattern.substring(offset);
		} else {
			compiled.fixed[i] = StringPool.EMPTY;
		}

		return compiled;
	}

	/**
	 * Returns {@code true} if macros are detected in the pattern.
	 */
	public boolean hasMacros() {
		return compile().macrosCount != 0;
	}

	/**
	 * Returns {@code true} if the input matches the pattern.
	 */
	public boolean matches(final String input) {
		return compile().matches(input);
	}

	/**
	 * Returns all the matches for given input. If no matches found, an empty array is returned.
	 */
	public Match[] match(final String input) {
		return compile().match(input);
	}

	// ---------------------------------------------------------------- compiled

	public class StringTemplateMatcherCompiled {
		private final int macrosCount;
		private final String[] names;		// macros names
		private final String[] patterns;	// macros patterns, if defined, elements may be null
		private final String[] fixed;		// array of fixed strings surrounding macros

		private StringTemplateMatcherCompiled(final int macrosCount) {
			this.macrosCount = macrosCount;
			this.names = new String[macrosCount];
			this.patterns = new String[macrosCount];
			this.fixed = new String[macrosCount + 1];
		}
		private StringTemplateMatcherCompiled() {
			this.macrosCount = 0;
			this.names = this.patterns = this.fixed = StringPool.EMPTY_ARRAY;
		}

		public String[] names() {
			return names;
		}

		/**
		 * Returns all patterns. Some elements may be <code>null</code>
		 * 	if some macro does not define a pattern.
		 */
		public String[] patterns() {
			return patterns;
		}

		/**
		 * Returns macros count.
		 */
		public int macrosCount() {
			return macrosCount;
		}

		/**
		 * Returns {@code true} if the input matches the compiled pattern.
		 */
		public boolean matches(final String input) {
			final String[] values = process(input, true);
			return values != null;
		}

		/**
		 * Returns all the matches or the empty array if no matches found.
		 */
		public Match[] match(final String input) {
			final String[] values = process(input, false);
			if (values == null) {
				return NO_MATCH;
			}
			final Match[] matches = new Match[macrosCount];
			for (int i = 0, valuesLength = values.length; i < valuesLength; i++) {
				matches[i] = new Match(names[i], patterns[i], values[i]);
			}
			return matches;
		}

		/**
		 * Process input in two modes: matching mode and extracting mode.
		 * @return string array of extracted macro values (null element is allowed) or null
		 */
		private String[] process(final String input, final boolean match) {
			// first check the first fixed as a prefix
			if (match && !input.startsWith(fixed[0])) {
				return null;
			}

			final String[] values = new String[macrosCount];

			int offset = fixed[0].length();
			int i = 0;

			while (i < macrosCount) {
				int nexti = i;

				// defines next fixed string to match
				String nextFixed;
				while (true) {
					nexti++;
					if (nexti > macrosCount) {
						nextFixed = null;	// match to the end of line
						break;
					}
					nextFixed = fixed[nexti];
					if (nextFixed.length() != 0) {
						break;
					}
					// next fixed is an empty string, so skip the next macro.
				}

				// find next fixed string
				final int ndx;

				if (nextFixed != null) {
					ndx = input.indexOf(nextFixed, offset);
				} else {
					ndx = input.length();
				}

				if (ndx == -1) {
					return null;
				}

				final String macroValue = input.substring(offset, ndx);
				values[i] = macroValue;

				if (match && patterns[i] != null) {
					if (!matchValue.apply(i, macroValue)) {
						return null;
					}
				}

				if (nextFixed == null) {
					offset = ndx;
					break;
				}

				// iterate
				final int nextFixedLength = nextFixed.length();
				offset = ndx + nextFixedLength;

				i = nexti;
			}

			if (offset != input.length()) {
				// input is not consumed fully during this matching
				return null;
			}

			return values;
		}

	}

}
