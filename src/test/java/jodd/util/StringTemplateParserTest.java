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

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringTemplateParserTest {

	@Test
	void testMap() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("key1", "value1");

		assertEquals("---value1---", StringTemplateParser.ofMap(map).apply("---${key1}---"));
	}


	@Test
	void testMissing() {
		final HashMap<String, String> map = new HashMap<>();

		map.put("key1", "value1");

		final StringTemplateParser stp = StringTemplateParser.ofMap(map);
		assertEquals("------", stp.apply("---${key2}---"));

		stp.setReplaceMissingKey(false);
		assertEquals("---${key2}---", stp.apply("---${key2}---"));

		stp.setReplaceMissingKey(true);
		stp.setMissingKeyReplacement("");

		assertEquals("------", stp.apply("---${key2}---"));

		stp.setMissingKeyReplacement("<>");
		assertEquals("---<>---", stp.apply("---${key2}---"));
	}

	@Test
	void testInner() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("key0", "1");
		map.put("key1", "2");
		map.put("key2", "value");

		final StringTemplateParser stp = StringTemplateParser.ofMap(map);

		assertEquals("---value---", stp.apply("---${key${key1}}---"));

		assertEquals("---value---", stp.apply("---${key${key${key0}}}---"));
	}

	@Test
	void testInner2() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("foo", "foo");
		map.put("boo.foo", "*${foo}*");
		map.put("zoo", "${boo.${foo}}");

		final StringTemplateParser stp = StringTemplateParser.ofMap(map);

		assertEquals("-*${foo}*-", stp.apply("-${boo.${foo}}-"));
		assertEquals("-${boo.${foo}}-", stp.apply("-${zoo}-"));

		stp.setParseValues(true);
		assertEquals("-*foo*-", stp.apply("-${boo.${foo}}-"));
		assertEquals("-*foo*-", stp.apply("-${zoo}-"));

	}

	@Test
	void testResolver() {
		assertEquals("xxxSMALLxxx", StringTemplateParser.of(String::toUpperCase).apply("xxx${small}xxx"));
	}

	@Test
	void testReplaceMissingKey() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("key0", "1");
		map.put("key1", "2");

		final StringTemplateParser stp = StringTemplateParser.ofMap(map);

		assertEquals(".1.", stp.apply(".${key0}."));

		assertEquals("..", stp.apply(".${key2}."));

		stp.setMissingKeyReplacement("x");
		assertEquals(".x.", stp.apply(".${key2}."));

		stp.setReplaceMissingKey(false);
		assertEquals(".${key2}.", stp.apply(".${key2}."));

		stp.setMissingKeyReplacement(null);
		assertEquals(".${key2}.", stp.apply(".${key2}."));
	}

	@Test
	void testResolveEscapes() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("fooProp", "abean_value");

		final StringTemplateParser stp = StringTemplateParser.ofMap(map);

		stp.setResolveEscapes(false);

		assertEquals("...abean_value...", stp.apply("...${fooProp}..."));

		assertEquals("...\\${fooProp}...", stp.apply("...\\${fooProp}..."));
		assertEquals("...\\\\abean_value...", stp.apply("...\\\\${fooProp}..."));
		assertEquals("...\\\\\\${fooProp}...", stp.apply("...\\\\\\${fooProp}..."));
		assertEquals("...\\\\\\\\abean_value...", stp.apply("...\\\\\\\\${fooProp}..."));
		assertEquals("...\\\\\\\\\\${fooProp}...", stp.apply("...\\\\\\\\\\${fooProp}..."));
	}

	@Test
	void testCustomMacrosEnds() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("foo", "bar");
		map.put("bar", "zap");

		final StringTemplateParser stp = StringTemplateParser.ofMap(map);

		assertEquals("...bar...<%=foo%>...", stp.apply("...${foo}...<%=foo%>..."));

		stp.setMacroStart("<%=");
		stp.setMacroEnd("%>");
		stp.setMacroPrefix(null);

		assertEquals("...${foo}...bar...", stp.apply("...${foo}...<%=foo%>..."));

		assertEquals("z<%=foo%>z", stp.apply("z\\<%=foo%>z"));
		assertEquals("xzapx", stp.apply("x<%=<%=foo%>%>x"));
	}

	@Test
	void testNonScript() {
		final HashMap<String, Object> map = new HashMap<>();
		map.put("foo", "bar");
		map.put("bar", "zap");
		map.put("inner.man", "jo");

		final StringTemplateParser stp = StringTemplateParser.ofMap(map);

		assertEquals("...bar...", stp.apply("...$foo..."));
		assertEquals("xx bar xx", stp.apply("xx $foo xx"));
		assertEquals("bar", stp.apply("$foo"));

		assertEquals("jo", stp.apply("$inner.man"));
		assertEquals("jo.", stp.apply("$inner.man."));
		assertEquals("jo x", stp.apply("$inner.man x"));
		assertEquals("jo bar", stp.apply("$inner.man ${foo}"));

		stp.setStrictFormat();
		assertEquals("$inner.man bar", stp.apply("$inner.man ${foo}"));
	}

	@Test
	void test601_IndexOutOfBounds() {
		final StringTemplateParser stp = new StringTemplateParser(null);
		stp.setReplaceMissingKey(false);

		assertEquals("$foo", stp.apply("$foo"));
		assertEquals("$foo bar", stp.apply("$foo bar"));
		assertEquals("foo $bar", stp.apply("foo $bar"));
		assertEquals("$foo", StringTemplateParser.of((s) -> {throw new RuntimeException();}).setReplaceMissingKey(false).apply("$foo"));
	}

	@Test
	void test601_DuplicatedChar() {
		StringTemplateParser stp = new StringTemplateParser(null);
		stp.setReplaceMissingKey(false);
		assertEquals("bar$foo baz", stp.apply("bar$foo baz"));

		stp = new StringTemplateParser((s) -> {throw new RuntimeException();});
		stp.setReplaceMissingKey(false);
		assertEquals("bar$foo baz", stp.apply("bar$foo baz"));
	}

}
