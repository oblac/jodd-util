package jodd.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Simple ruling engine for includes and excludes rules.
 */
public class InExRules<V, P> {

	public static InExRules<String, String> blacklist() {
		return new InExRules<>(InExType.BLACKLIST, WILDCARD_STRING_MATCHER);
	}
	public static InExRules<String, String> whitelist() {
		return new InExRules<>(InExType.WHITELIST, WILDCARD_STRING_MATCHER);
	}

	public enum InExType {
		WHITELIST,
		BLACKLIST
	}

	public static final Function<String, Predicate<String>> WILDCARD_STRING_MATCHER = pattern -> new Predicate<String>() {
		final String p = pattern;
		@Override
		public boolean test(final String value) {
			return Wildcard.match(value, p);
		}
	};

	public InExRules(final InExType inExType, final Function<P, Predicate<V>> factory) {
		this.inExType = inExType;
		this.factory = factory;
	}

	private final InExType inExType;
	private final Function<P, Predicate<V>> factory;

	private final List<Predicate<V>> includePatterns = new ArrayList<>();
	private final List<Predicate<V>> excludePatterns = new ArrayList<>();

	public void include(final P pattern) {
		final Predicate<V> matcher = factory.apply(pattern);
		this.includePatterns.add(matcher);
	}
	public void exclude(final P pattern) {
		final Predicate<V> matcher = factory.apply(pattern);
		this.excludePatterns.add(matcher);
	}

	public boolean match(final V value) {
		return apply(value, inExType == InExType.BLACKLIST);
	}

	public boolean apply(final V value, final boolean inputValue) {
		boolean matchIncludes = false;
		for (final Predicate<V> includePredicate : includePatterns) {
			if (includePredicate.test(value)) {
				matchIncludes = true;
				break;
			}
		}

		boolean matchExcludes = false;
		for (final Predicate<V> exclude : excludePatterns) {
			if (exclude.test(value)) {
				matchExcludes = true;
				break;
			}
		}

		// resolution

		if (inExType == InExType.BLACKLIST) {
			if (matchExcludes) {
				if (includePatterns.isEmpty()) {
					return false;
				}
				return matchIncludes;
			}
		}
		else {
			if (matchIncludes) {
				if (excludePatterns.isEmpty()) {
					return true;
				}
				return !matchExcludes;
			}
		}
		return inputValue;
	}

}
