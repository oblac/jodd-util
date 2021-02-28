package jodd.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringTemplateMatcherTest {

	@Test
	void testSimplePathMacro() {
		assertFalse(StringTemplateMatcher.of("/some/path/no/macros").hasMacros());

		final StringTemplateMatcher stm = StringTemplateMatcher.of("/img-{id}.png");
		assertTrue(stm.hasMacros());
		assertEquals("id", stm.compile().names()[0]);

		final String actionPath = "/img-123.png";
		final StringTemplateMatcher.Match[] matches = stm.match(actionPath);

		assertEquals(1, matches.length);
		assertEquals("123", matches[0].value());
	}

	@Test
	void testSimplePathMacroSep2() {
		final StringTemplateMatcher stm =
				StringTemplateMatcher.of("/img-<id>.png")
				.setMacroPrefix("<")
				.setMacroSuffix(">");

		assertTrue(stm.hasMacros());
		assertEquals("id", stm.compile().names()[0]);

		final String actionPath = "/img-123.png";
		final StringTemplateMatcher.Match[] matches = stm.match(actionPath);

		assertEquals(1, matches.length);
		assertEquals("123", matches[0].value());
	}

	@Test
	void testFirstLastPathMacro() {
		final StringTemplateMatcher stm = StringTemplateMatcher.of("{di}/img/{id}");
		assertEquals("di", stm.compile().names()[0]);
		assertEquals("id", stm.compile().names()[1]);

		final String actionPath = "987/img/123";
		final StringTemplateMatcher.Match[] matches = stm.match(actionPath);

		assertEquals(2, matches.length);
		assertEquals("987", matches[0].value());
		assertEquals("123", matches[1].value());
	}

	@Test
	void testSinglePathMacro() {
		final StringTemplateMatcher stm = StringTemplateMatcher.of("{id}");

		assertEquals("id", stm.compile().names()[0]);

		final String actionPath = "123.jpg";
		final StringTemplateMatcher.Match[] matches = stm.match(actionPath);

		assertEquals(1, matches.length);
		assertEquals("123.jpg", matches[0].value());
	}

	@Test
	void testThreesomePathMacro() {
		final StringTemplateMatcher stm = StringTemplateMatcher.of("/user/{userId}/doc{docId}/v{version}");

		assertTrue(stm.hasMacros());
		assertEquals("userId", stm.compile().names()[0]);
		assertEquals("docId", stm.compile().names()[1]);
		assertEquals("version", stm.compile().names()[2]);

		String actionPath = "/user/173/doc8/";
		assertFalse(stm.matches(actionPath));

		actionPath = "/user/173/doc8/v12";
		assertTrue(stm.matches(actionPath));
		StringTemplateMatcher.Match[] matches = stm.match(actionPath);

		assertEquals(3, matches.length);
		assertEquals("173", matches[0].value());
		assertEquals("8", matches[1].value());
		assertEquals("12", matches[2].value());

		actionPath = "/user/173/doc8/v";
		matches = stm.match(actionPath);

		assertEquals(3, matches.length);
		assertEquals("173", matches[0].value());
		assertEquals("8", matches[1].value());
		assertEquals(StringPool.EMPTY, matches[2].value());

		actionPath = "/user//doc/v";
		matches = stm.match(actionPath);

		assertEquals(3, matches.length);
		assertEquals(StringPool.EMPTY, matches[0].value());
		assertEquals(StringPool.EMPTY, matches[1].value());
		assertEquals(StringPool.EMPTY, matches[2].value());
	}

	@Test
	void testDummyPathMacro() {
		final StringTemplateMatcher stm = StringTemplateMatcher.of("/user/{userId}{version}");
		assertTrue(stm.hasMacros());
		assertEquals("userId", stm.compile().names()[0]);
		assertEquals("version", stm.compile().names()[1]);

		final String actionPath = "/user/jodd";
		final StringTemplateMatcher.Match[] matches = stm.match(actionPath);

		assertEquals("jodd", matches[0].value());
		assertNull(matches[1].value());
	}

	@Test
	void testWildcardMatch() {
		final StringTemplateMatcher stm = StringTemplateMatcher.of("/user-{userId:1*7?3}");
		stm.useWildcardMatch();

		assertEquals("userId", stm.compile().names()[0]);
		assertEquals("1*7?3", stm.compile().patterns()[0]);

		assertTrue(stm.matches("/user-1773"));
		assertTrue(stm.matches("/user-122723"));
		assertFalse(stm.matches("/user-17"));
	}

	@Test
	void testWildcardMatchContaining() {
		final StringTemplateMatcher stm1 = StringTemplateMatcher.of("/{entityName}/dba.delete");

		assertEquals("entityName", stm1.compile().names()[0]);

		final StringTemplateMatcher stm2 = StringTemplateMatcher.of("/{entityName}/dba.delete_multi");
		assertEquals("entityName", stm2.compile().names()[0]);

		assertTrue(stm2.matches("/config/dba.delete_multi"));
		assertFalse(stm1.matches("/config/dba.delete_multi"));
	}

	@Test
	void testWildcardMatchContaining2() {
		final StringTemplateMatcher stm1 = StringTemplateMatcher.of("/dba.delete/{entityName}");
		assertTrue(stm1.hasMacros());
		assertEquals("entityName", stm1.compile().names()[0]);

		final StringTemplateMatcher stm2 = StringTemplateMatcher.of("/dba.delete_multi/{entityName}");
		assertTrue(stm2.hasMacros());
		assertEquals("entityName", stm2.compile().names()[0]);

		assertTrue(stm2.matches("/dba.delete_multi/config"));
		assertFalse(stm1.matches("/dba.delete_multi/config"));
	}

	@Test
	void testWildcardMatchContainingWithTwo() {
		final StringTemplateMatcher stm1 = StringTemplateMatcher.of("/{entityName}/dba.delete{xxx}");
		assertEquals("entityName", stm1.compile().names()[0]);
		assertEquals("xxx", stm1.compile().names()[1]);

		final StringTemplateMatcher stm2 = StringTemplateMatcher.of("/{entityName}/dba.delete_multi{xxx}");
		assertEquals("entityName", stm2.compile().names()[0]);
		assertEquals("xxx", stm2.compile().names()[1]);

		assertTrue(stm2.matches("/config/dba.delete_multiZZZ"));
		// the following is still matching, but the number of non-matching chars is smaller
		assertTrue(stm1.matches("/config/dba.delete_multiZZZ"));
	}

}
