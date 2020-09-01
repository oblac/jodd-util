// Copyright (c) 2003-present, Jodd Team (http://jodd.org)
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// 1. Redistributions of source code must retain the above copyright notice,
// this list of conditions and the following disclaimer.
//
// 2. Redistributions in binary form must reproduce the above copyright
// notice, this list of conditions and the following disclaimer in the
// documentation and/or other materials provided with the distribution.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE.

package jodd.bean;

import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static jodd.util.StringPool.LEFT_SQ_BRACKET;
import static jodd.util.StringPool.RIGHT_SQ_BRACKET;

/**
 * Powerful tool for copying properties from one bean into another.
 * <code>BeanCopy</code> works with POJO beans, but also with <code>Map</code>.
 *
 * @see BeanVisitor
 */
public class BeanCopy {

	private final Object source;
	private Object destination;
	private boolean forced;
	private boolean declared;
	private boolean isTargetMap;
	private Predicate<String> filter;
	private BiPredicate<String, Object> filter2;
	private boolean includeFields;

	// ---------------------------------------------------------------- ctor

	/**
	 * Creates new BeanCopy process between the source and the destination.
	 * Both source and destination can be a POJO object or a <code>Map</code>.
	 */
	public BeanCopy(final Object source, final Object destination) {
		this.source = source;
		this.destination = destination;
		this.isTargetMap = destination instanceof Map;
	}

	private BeanCopy(final Object source) {
		this.source = source;
	}

	/**
	 * Creates <code>BeanCopy</code> with given POJO bean as a source.
	 */
	public static BeanCopy from(final Object source) {
		return new BeanCopy(source);
	}

	/**
	 * Defines destination, detects a map.
	 */
	public BeanCopy to(final Object destination) {
		this.destination = destination;
		this.isTargetMap = destination instanceof Map;
		return this;
	}

	// ---------------------------------------------------------------- properties

	/**
	 * Defines if all properties should be copied (when set to <code>true</code>)
	 * or only public (when set to <code>false</code>, default).
	 */
	public BeanCopy declared(final boolean declared) {
		this.declared = declared;
		return this;
	}

	public BeanCopy forced(final boolean forced) {
		this.forced = forced;
		return this;
	}

	public BeanCopy filter(final Predicate<String> filter) {
		this.filter = filter;
		return this;
	}

	public BeanCopy filter(final BiPredicate<String, Object> filter) {
		this.filter2 = filter;
		return this;
	}

	public BeanCopy includeFields(final boolean includeFields) {
		this.includeFields = includeFields;
		return this;
	}


	// ---------------------------------------------------------------- visitor

	/**
	 * Performs the copying.
	 */
	public void copy() {
		final BeanUtil beanUtil = new BeanUtilBean()
						.declared(declared)
						.forced(forced)
						.silent(true);

		new BeanVisitor(source)
				.declared(declared)
				.includeFields(includeFields)
				.visit((name, value) -> {
					if (isTargetMap) {
						name = LEFT_SQ_BRACKET + name + RIGHT_SQ_BRACKET;
					}

					if (filter != null) {
						if (!filter.test(name)) {
							return;
						}
					}
					if (filter2 != null) {
						if (!filter2.test(name, value)) {
							return;
						}
					}

					beanUtil.setProperty(destination, name, value);
				});
	}

}
