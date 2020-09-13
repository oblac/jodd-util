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
		boolean flag = inExType == InExType.BLACKLIST;

		if (inExType == InExType.BLACKLIST) {
			flag = processExcludes(value, flag);
			flag = processIncludes(value, flag);
		}
		else {
			flag = processIncludes(value, flag);
			flag = processExcludes(value, flag);
		}
		return flag;
	}

	public boolean apply(final V value, boolean flag) {
		if (inExType == InExType.BLACKLIST) {
			flag = processExcludes(value, flag);
			flag = processIncludes(value, flag);
		}
		else {
			flag = processIncludes(value, flag);
			flag = processExcludes(value, flag);
		}
		return flag;
	}

	protected boolean processIncludes(final V value, boolean include) {
		if (includePatterns.isEmpty()) {
			return include;
		}
		if (include) {
			return include;
		}
		for (final Predicate<V> includePredicate : includePatterns) {
			if (includePredicate.test(value)) {
				include = true;
				break;
			}
		}
		return include;
	}

	protected boolean processExcludes(final V value, boolean include) {
		if (excludePatterns.isEmpty()) {
			return include;
		}
		if (!include) {
			return include;
		}
		for (final Predicate<V> excludePredicate : excludePatterns) {
			if (excludePredicate.test(value)) {
				include = false;
				break;
			}
		}
		return include;
	}

}
