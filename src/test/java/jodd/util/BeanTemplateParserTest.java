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

import jodd.bean.fixtures.Abean;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BeanTemplateParserTest {

	@Test
	void testTemplate() {
		final Abean a = new Abean();
		final StringTemplateParser ctp = StringTemplateParser.ofBean(a);

		assertEquals("xxxx", ctp.apply("xxxx"));
		assertEquals("", ctp.apply(""));
		assertEquals("...abean_value...", ctp.apply("...${fooProp}..."));
		assertEquals("abean_value", ctp.apply("${fooProp}"));

		assertEquals("...${fooProp}...", ctp.apply("...\\${fooProp}..."));
		assertEquals("...\\abean_value...", ctp.apply("...\\\\${fooProp}..."));
		assertEquals("...\\${fooProp}...", ctp.apply("...\\\\\\${fooProp}..."));
		assertEquals("...\\\\abean_value...", ctp.apply("...\\\\\\\\${fooProp}..."));
		assertEquals("...\\\\${fooProp}...", ctp.apply("...\\\\\\\\\\${fooProp}..."));

		assertEquals("${fooProp}", ctp.apply("\\${fooProp}"));
		assertEquals("\\abean_value", ctp.apply("\\\\${fooProp}"));
		assertEquals("\\${fooProp}", ctp.apply("\\\\\\${fooProp}"));
		assertEquals("\\\\abean_value", ctp.apply("\\\\\\\\${fooProp}"));
		assertEquals("\\\\${fooProp}", ctp.apply("\\\\\\\\\\${fooProp}"));

		assertEquals("abean_valueabean_value", ctp.apply("${fooProp}${fooProp}"));
		assertEquals("${fooProp}abean_value", ctp.apply("\\${fooProp}${fooProp}"));
	}

	@Test
	void testNoParenthes() {
		final Map<String, Object> ctx = new HashMap<>();
		ctx.put("string", 173);

		assertEquals("173", StringTemplateParser.ofMap(ctx).apply("$string"));
		assertEquals("", StringTemplateParser.ofMap(ctx).apply("$string2"));
	}

	@Test
	void testMap() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("key1", "value1");

		assertEquals("---value1---", StringTemplateParser.ofMap(map).apply("---${key1}---"));
	}

	@Test
	void testMissing() {
		final HashMap<String, String> map = new HashMap<>();
		final StringTemplateParser beanTemplateParser = StringTemplateParser.ofMap(map);
		map.put("key1", "value1");

		assertEquals("------", beanTemplateParser.apply("---${key2}---"));

		beanTemplateParser.setMissingKeyReplacement("");

		assertEquals("------", beanTemplateParser.apply("---${key2}---"));

		beanTemplateParser.setMissingKeyReplacement("<>");
		assertEquals("---<>---", beanTemplateParser.apply("---${key2}---"));
	}

	@Test
	void testInner() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("key0", "1");
		map.put("key1", "2");
		map.put("key2", "value");

		assertEquals("---value---", StringTemplateParser.ofMap(map).apply("---${key${key1}}---"));

		assertEquals("---value---", StringTemplateParser.ofMap(map).apply("---${key${key${key0}}}---"));
	}

	@Test
	void testReplaceMissingKey() {
		final HashMap<String, String> map = new HashMap<>();
		map.put("key0", "1");
		map.put("key1", "2");

		assertEquals(".1.", StringTemplateParser.ofMap(map).apply(".${key0}."));
		assertEquals("..", StringTemplateParser.ofMap(map).apply(".${key2}."));

		assertEquals(".1.", StringTemplateParser.ofMap(map).apply(".${key0}."));
		assertEquals("..", StringTemplateParser.ofMap(map).apply(".${key2}."));

		assertEquals(".x.", StringTemplateParser.ofMap(map).setMissingKeyReplacement("x").apply(".${key2}."));

		assertEquals(".${key2}.", StringTemplateParser.ofMap(map).setMissingKeyReplacement("x").setReplaceMissingKey(false).apply(".${key2}."));
		assertEquals(".${key2}.", StringTemplateParser.ofMap(map).setMissingKeyReplacement(null).setReplaceMissingKey(false).apply(".${key2}."));
	}

	@Test
	void testResolveEscapes() {
		final Abean a = new Abean();
		final StringTemplateParser ctp = StringTemplateParser.ofBean(a);
		ctp.setResolveEscapes(false);

		assertEquals("...abean_value...", ctp.apply("...${fooProp}..."));

		assertEquals("...\\${fooProp}...", ctp.apply("...\\${fooProp}..."));
		assertEquals("...\\\\abean_value...", ctp.apply("...\\\\${fooProp}..."));
		assertEquals("...\\\\\\${fooProp}...", ctp.apply("...\\\\\\${fooProp}..."));
		assertEquals("...\\\\\\\\abean_value...", ctp.apply("...\\\\\\\\${fooProp}..."));
		assertEquals("...\\\\\\\\\\${fooProp}...", ctp.apply("...\\\\\\\\\\${fooProp}..."));
	}
}
