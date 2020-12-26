package jodd.bean;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HasTest {

	public static class Foo {
		String foo;
		Bar bar;
		List<Bar> bars;
		Map<String, Bar> map;
		public String getFoo() {
			return foo;
		}

		public void setFoo(final String foo) {
			this.foo = foo;
		}

		public Bar getBar() {
			return bar;
		}

		public void setBar(final Bar bar) {
			this.bar = bar;
		}

		public List<Bar> getBars() {
			return bars;
		}

		public void setBars(final List<Bar> bars) {
			this.bars = bars;
		}

		public Map<String, Bar> getMap() {
			return map;
		}

		public void setMap(final Map<String, Bar> map) {
			this.map = map;
		}
	}
	public static class Bar {
		Foo foo;
		MyEnum myEnum;

		public Foo getFoo() {
			return foo;
		}

		public void setFoo(final Foo foo) {
			this.foo = foo;
		}

		public MyEnum getMyEnum() {
			return myEnum;
		}

		public void setMyEnum(final MyEnum myEnum) {
			this.myEnum = myEnum;
		}
	}
	public enum MyEnum {
		VAL1,VAL2,VAL3;
	}

	@Test
	void testEnum() {
		final Foo bean = new Foo();
		assertTrue(BeanUtil.declared.hasProperty(bean, "bar.myEnum"));
		assertTrue(BeanUtil.declared.hasProperty(bean, "bar.foo"));
		assertTrue(BeanUtil.declared.hasProperty(bean, "bar.foo.foo"));
		assertTrue(BeanUtil.declared.hasProperty(bean, "bar.foo.bar"));
		assertFalse(BeanUtil.declared.hasProperty(bean, "bar.foo.nope"));
		assertTrue(BeanUtil.declared.hasProperty(bean, "bar.foo.bar.myEnum"));

		bean.setBar(new Bar());
		assertTrue(BeanUtil.declared.hasProperty(bean, "bar.myEnum"));
		assertTrue(BeanUtil.declared.hasProperty(bean, "bar.foo"));
		assertTrue(BeanUtil.declared.hasProperty(bean, "bar.foo.foo"));
		assertTrue(BeanUtil.declared.hasProperty(bean, "bar.foo.bar"));
		assertFalse(BeanUtil.declared.hasProperty(bean, "bar.foo.nope"));
		assertTrue(BeanUtil.declared.hasProperty(bean, "bar.foo.bar.myEnum"));
	}

	@Test
	void testEnum_withList() {
		final Foo bean = new Foo();
		assertTrue(BeanUtil.declared.hasProperty(bean, "bars"));
		assertFalse(BeanUtil.declared.hasProperty(bean, "bars[1]"));
		assertTrue(BeanUtil.declared.hasProperty(bean, "bar.foo.bars"));
		assertFalse(BeanUtil.declared.hasProperty(bean, "bar.foo.bars[1]"));
	}

	@Test
	void testEnum_withMap() {
		final Foo bean = new Foo();
		assertTrue(BeanUtil.declared.hasProperty(bean, "map"));
		assertFalse(BeanUtil.declared.hasProperty(bean, "map[a]"));
		assertTrue(BeanUtil.declared.hasProperty(bean, "bar.foo.map"));
		assertFalse(BeanUtil.declared.hasProperty(bean, "bar.foo.map[b]"));
	}
}
