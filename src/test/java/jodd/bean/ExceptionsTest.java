package jodd.bean;

import jodd.bean.exception.ForcedBeanException;
import jodd.bean.exception.InvalidPropertyBeanException;
import jodd.bean.exception.InvokePropertyBeanException;
import jodd.bean.exception.PropertyNotFoundBeanException;
import jodd.bean.exception.NullPropertyBeanException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExceptionsTest {

	static class X {
		Y nested;

		// private
		private String priv = "private";
		public String getPriv() {throw new RuntimeException();}

		private String bad = "bad";
		public void setBad(String bad) {throw new RuntimeException();}

		// array
		public Y[] ys = null;

		public List<Iterable> strings = new ArrayList<>();

		public Map<String, Iterable> map = new HashMap<>();
	}
	static class Y {
		int a;
	}

	@Test
	void testHasExistingExisting() {
		assertTrue(BeanUtil.declared.hasProperty(new X(), "nested.a"));
		assertFalse(BeanUtil.declared.hasProperty(new X(), "nested.na"));
	}

	@Test
	void testGetInvalidSimplePropertyEmpty() {
		assertThrows(InvalidPropertyBeanException.class,
				() -> BeanUtil.declared.getSimpleProperty(new X(), ""));
		assertThrows(InvalidPropertyBeanException.class,
				() -> BeanUtil.declared.getProperty(new X(), ""));
	}

	@Test
	void testInvokeGetterPropertyFailed() {
		assertThrows(InvokePropertyBeanException.class,
				() -> BeanUtil.declared.getProperty(new X(), "priv"));
	}

	@Test
	void testMissingMapProperty() {
		Map<String, String> map = new HashMap<>();
		assertThrows(PropertyNotFoundBeanException.class,
				() -> BeanUtil.declared.getProperty(map, "priv"));
	}

	@Test
	void testMissingGetProperty() {
		assertThrows(PropertyNotFoundBeanException.class,
				() -> BeanUtil.declared.getProperty(new X(), "na"));
	}

	@Test
	void testMissingSetProperty() {
		assertThrows(PropertyNotFoundBeanException.class,
				() -> BeanUtil.declared.setProperty(new X(), "na", "value"));
	}

	@Test
	void testIndexBookIsNull() {
		assertThrows(NullPropertyBeanException.class,
				() -> BeanUtil.declared.getProperty(new X(), "ys[0].foo"));
	}

	@Test
	void testInvalidForcedListElement() {
		assertThrows(ForcedBeanException.class,
				() -> BeanUtil.declaredForced.setProperty(new X(), "strings[2].nope", "xxx"));
	}

	@Test
	void testInvalidForcedMapElement() {
		assertThrows(ForcedBeanException.class,
				() -> BeanUtil.declaredForced.setProperty(new X(), "map[foo].nope", "xxx"));
	}

	@Test
	void testInvalidIndexProperty() {
		assertThrows(InvalidPropertyBeanException.class,
				() -> BeanUtil.declaredForced.setProperty(new X(), "nested[foo].nope", "xxx"));
	}

	@Test
	void testSetNullProperty() {
		assertThrows(NullPropertyBeanException.class,
				() -> BeanUtil.declared.setProperty(new X(), "ys[1]", "xxx"));

	}

	@Test
	void testSetInvalidIndexProperty() {
		X x = new X();
		x.nested = new Y();
		assertThrows(InvalidPropertyBeanException.class,
				() -> BeanUtil.declared.setProperty(x, "nested[1]", "xxx"));
	}

	@Test
	void testInvalidSetProperty() {
		assertThrows(InvokePropertyBeanException.class,
				() -> BeanUtil.pojo.setProperty(new X(), "bad", "xxx"));
	}

	@Test
	void testIssue11() {
		assertThrows(NullPropertyBeanException.class,
				() -> BeanUtil.declared.getProperty(new X(), "nested.a"));
		assertThrows(NullPropertyBeanException.class,
				() -> BeanUtil.declared.getProperty(new X(), "nested.a.na"));
		assertThrows(PropertyNotFoundBeanException.class,
				() -> BeanUtil.declared.getProperty(new X(), "nested.na"));
	}

}
