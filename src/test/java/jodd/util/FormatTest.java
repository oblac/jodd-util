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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class FormatTest {

	@Test
	void testByteSizes_noSi() {
		assertEquals("10 B", MathUtil.humanReadableByteCount(10, false));
		assertEquals("1.0 KiB", MathUtil.humanReadableByteCount(1024, false));
		assertEquals("1.5 KiB", MathUtil.humanReadableByteCount(1024 + 512, false));
	}

	@Test
	void testToPrettyString() {
		assertEquals(StringPool.NULL, Util.toPrettyString(null));

		assertEquals("[A,B]", Util.toPrettyString(new String[]{"A", "B"}));
		assertEquals("[1,2]", Util.toPrettyString(new int[]{1,2}));
		assertEquals("[1,2]", Util.toPrettyString(new long[]{1,2}));
		assertEquals("[1,2]", Util.toPrettyString(new short[]{1,2}));
		assertEquals("[1,2]", Util.toPrettyString(new byte[]{1,2}));
		assertEquals("[1.0,2.0]", Util.toPrettyString(new double[]{1,2}));
		assertEquals("[1.0,2.0]", Util.toPrettyString(new float[]{1,2}));
		assertEquals("[true,false]", Util.toPrettyString(new boolean[] {true, false}));

		try {
			Util.toPrettyString(new char[]{'a','b'});
			fail("error");
		} catch (final IllegalArgumentException e) {
			// ignore
		}

		assertEquals("[[1,2],[3,4]]", Util.toPrettyString(new int[][] {{1, 2}, {3, 4}}));

		final List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(4);

		assertEquals("{1,4}", Util.toPrettyString(list));
	}

	@Test
	void testToCamelCase() {
		assertEquals("oneTwoThree", StringUtil.toCamelCase("one two   three", false, ' '));
		assertEquals("OneTwo.Three", StringUtil.toCamelCase("one two. three", true, ' '));
		assertEquals("OneTwoThree", StringUtil.toCamelCase("One-two-three", true, '-'));

		assertEquals("userName", StringUtil.toCamelCase("user_name", false, '_'));
		assertEquals("UserName", StringUtil.toCamelCase("user_name", true, '_'));
		assertEquals("user", StringUtil.toCamelCase("user", false, '_'));
		assertEquals("User", StringUtil.toCamelCase("user", true, '_'));
	}

	@Test
	void testFromCamelCase() {
		assertEquals("one two three", StringUtil.fromCamelCase("oneTwoThree", ' '));
		assertEquals("one-two-three", StringUtil.fromCamelCase("oneTwoThree", '-'));
		assertEquals("one. two. three", StringUtil.fromCamelCase("one.Two.Three", ' '));

		assertEquals("user_name", StringUtil.fromCamelCase("userName", '_'));
		assertEquals("user_name", StringUtil.fromCamelCase("UserName", '_'));
		assertEquals("user_name", StringUtil.fromCamelCase("USER_NAME", '_'));
		assertEquals("user_name", StringUtil.fromCamelCase("user_name", '_'));
		assertEquals("user", StringUtil.fromCamelCase("user", '_'));
		assertEquals("user", StringUtil.fromCamelCase("User", '_'));
		assertEquals("user", StringUtil.fromCamelCase("USER", '_'));
		assertEquals("user", StringUtil.fromCamelCase("_user", '_'));
		assertEquals("user", StringUtil.fromCamelCase("_User", '_'));
		assertEquals("_user", StringUtil.fromCamelCase("__user", '_'));
		assertEquals("user__name", StringUtil.fromCamelCase("user__name", '_'));
	}

	@Test
	void testTabsToSpaces() {
		String s = StringUtil.convertTabsToSpaces("q\tqa\t", 3);
		assertEquals("q  qa ", s);

		s = StringUtil.convertTabsToSpaces("q\tqa\t", 0);
		assertEquals("qqa", s);
	}


	@Test
	void testJavaEscapes() {
		final String from = "\r\t\b\f\n\\\"asd\u0111q\u0173aa\u0ABC\u0abc";
		final String to = "\\r\\t\\b\\f\\n\\\\\\\"asd\\u0111q\\u0173aa\\u0abc\\u0abc";

		assertEquals(to, StringUtil.escapeJava(from));
		assertEquals(from, StringUtil.unescapeJava(to));

		try {
			StringUtil.unescapeJava("\\r\\t\\b\\f\\q");
			fail("error");
		} catch (final IllegalArgumentException e) {
			// ignore
		}
	}

}
