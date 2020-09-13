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

package jodd.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InExRulesTest {

	@Test
	void testExcludeAll() {
		final InExRules<String, String> inExRules = InExRules.blacklist();
		inExRules.exclude("*");

		assertFalse(inExRules.match("foo"));
		assertFalse(inExRules.match("fight"));
		assertFalse(inExRules.match("bar"));

		assertFalse(inExRules.apply("foo", true));
		assertFalse(inExRules.apply("fight", true));
		assertFalse(inExRules.apply("bar", true));

		assertFalse(inExRules.apply("foo", false));
		assertFalse(inExRules.apply("fight", false));
		assertFalse(inExRules.apply("bar", false));
	}

	@Test
	void testExcludeAllAlt() {
		final InExRules<String, String> inExRules = InExRules.whitelist();

		assertFalse(inExRules.match("foo"));
		assertFalse(inExRules.match("fight"));
		assertFalse(inExRules.match("bar"));

		assertTrue(inExRules.apply("foo", true));
		assertTrue(inExRules.apply("fight", true));
		assertTrue(inExRules.apply("bar", true));

		assertFalse(inExRules.apply("foo", false));
		assertFalse(inExRules.apply("fight", false));
		assertFalse(inExRules.apply("bar", false));
	}

	@Test
	void testExcludeAllButOne() {
		final InExRules<String, String> inExRules = InExRules.whitelist();
		inExRules.include("foo");

		assertTrue(inExRules.match("foo"));
		assertFalse(inExRules.match("fight"));
		assertFalse(inExRules.match("bar"));

		assertTrue(inExRules.apply("foo", true));
		assertTrue(inExRules.apply("fight", true));
		assertTrue(inExRules.apply("bar", true));

		assertTrue(inExRules.apply("foo", false));
		assertFalse(inExRules.apply("fight", false));
		assertFalse(inExRules.apply("bar", false));
	}

	@Test
	void testIncludeAllButOne() {
		final InExRules<String, String> inExRules = InExRules.blacklist();
		inExRules.exclude("foo");

		assertFalse(inExRules.match("foo"));
		assertTrue(inExRules.match("fight"));
		assertTrue(inExRules.match("bar"));

		assertFalse(inExRules.apply("foo", true));
		assertTrue(inExRules.apply("fight", true));
		assertTrue(inExRules.apply("bar", true));

		assertFalse(inExRules.apply("foo", false));
		assertFalse(inExRules.apply("fight", false));
		assertFalse(inExRules.apply("bar", false));
	}

	@Test
	void testIncludeAll() {
		final InExRules<String, String> inExRules = InExRules.blacklist();

		assertTrue(inExRules.match("foo"));
		assertTrue(inExRules.match("fight"));

		assertTrue(inExRules.apply("foo", true));
		assertTrue(inExRules.apply("fight", true));

		assertFalse(inExRules.apply("foo", false));
		assertFalse(inExRules.apply("fight", false));
	}

	@Test
	void testIncludeAllAlt() {
		final InExRules<String, String> inExRules = InExRules.whitelist();
		inExRules.include("*");

		assertTrue(inExRules.match("foo"));
		assertTrue(inExRules.match("fight"));

		assertTrue(inExRules.apply("foo", true));
		assertTrue(inExRules.apply("fight", true));

		assertTrue(inExRules.apply("foo", false));
		assertTrue(inExRules.apply("fight", false));
	}

	@Test
	void testExcludeSomeButOne() {
		final InExRules<String, String> inExRules = InExRules.blacklist();
		inExRules.exclude("f*");
		inExRules.include("foo");

		assertTrue(inExRules.match("foo"));
		assertFalse(inExRules.match("fight"));
		assertTrue(inExRules.match("bar"));

		assertTrue(inExRules.apply("foo", true));
		assertFalse(inExRules.apply("fight", true));
		assertTrue(inExRules.apply("bar", true));

		assertTrue(inExRules.apply("foo", false));
		assertFalse(inExRules.apply("fight", false));
		assertFalse(inExRules.apply("bar", false));
	}

	@Test
	void testIncludeOnlySomeButOne() {
		final InExRules<String, String> inExRules = InExRules.whitelist();

		inExRules.include("f*");
		inExRules.exclude("foo");

		assertFalse(inExRules.match("foo"));
		assertTrue(inExRules.match("fight"));
		assertTrue(inExRules.match("fravia"));
		assertFalse(inExRules.match("bar"));

		assertFalse(inExRules.apply("foo", true));
		assertTrue(inExRules.apply("fight", true));
		assertTrue(inExRules.apply("fravia", true));
		assertTrue(inExRules.apply("bar", true));

		assertFalse(inExRules.apply("foo", false));
		assertTrue(inExRules.apply("fight", false));
		assertTrue(inExRules.apply("fravia", false));
		assertFalse(inExRules.apply("bar", false));
	}

	@Test
	void testExcludeOnlySomeButOne() {
		final InExRules<String, String> inExRules = InExRules.blacklist();
		inExRules.exclude("f*");
		inExRules.include("foo");

		assertTrue(inExRules.match("foo"));
		assertFalse(inExRules.match("fight"));
		assertFalse(inExRules.match("fravia"));
		assertTrue(inExRules.match("bar"));

		assertTrue(inExRules.apply("foo", true));
		assertFalse(inExRules.apply("fight", true));
		assertFalse(inExRules.apply("fravia", true));
		assertTrue(inExRules.apply("bar", true));

		assertTrue(inExRules.apply("foo", false));
		assertFalse(inExRules.apply("fight", false));
		assertFalse(inExRules.apply("fravia", false));
		assertFalse(inExRules.apply("bar", false));
	}

}
