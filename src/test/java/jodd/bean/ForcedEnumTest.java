package jodd.bean;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ForcedEnumTest {

	@Test
	public void testForcedGetEnum() {
		final Dummy dummy = new Dummy();
		final String key = "myEnum";

		assertTrue(BeanUtil.forcedSilent.hasProperty(dummy, key));

		MyEnum myEnum = dummy.getMyEnum();
		assertNull(myEnum);

		assertNull(BeanUtil.forcedSilent.getProperty(dummy, key));

		myEnum = dummy.getMyEnum();
		assertNull(myEnum);
	}

	public static class Dummy {
		String foo;
		String bar;
		MyEnum myEnum;

		public String getFoo() {
			return foo;
		}

		public void setFoo(final String foo) {
			this.foo = foo;
		}

		public String getBar() {
			return bar;
		}

		public void setBar(final String bar) {
			this.bar = bar;
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
}
