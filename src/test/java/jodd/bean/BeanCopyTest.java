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

import jodd.bean.fixtures.FooBean;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BeanCopyTest {

	@Test
	void testCopy() {
		final FooBean fb = createFooBean();
		final FooBean dest = new FooBean();
		BeanCopy.from(fb).to(dest).copy();

		Integer v = BeanUtil.pojo.getProperty(dest, "fooInteger");
		assertEquals(201, v.intValue());
		v = BeanUtil.pojo.getProperty(dest, "fooint");
		assertEquals(202, v.intValue());
		Long vl = BeanUtil.pojo.getProperty(dest, "fooLong");
		assertEquals(203, vl.longValue());
		vl = BeanUtil.pojo.getProperty(dest, "foolong");
		assertEquals(204, vl.longValue());
		Byte vb = BeanUtil.pojo.getProperty(dest, "fooByte");
		assertEquals(95, vb.intValue());
		vb = BeanUtil.pojo.getProperty(dest, "foobyte");
		assertEquals(96, vb.intValue());
		Character c = BeanUtil.pojo.getProperty(dest, "fooCharacter");
		assertEquals('7', c.charValue());
		c = BeanUtil.pojo.getProperty(dest, "foochar");
		assertEquals('8', c.charValue());
		Boolean b = BeanUtil.pojo.getProperty(dest, "fooBoolean");
		assertTrue(b.booleanValue());
		b = BeanUtil.pojo.getProperty(dest, "fooboolean");
		assertFalse(b.booleanValue());
		Float f = BeanUtil.pojo.getProperty(dest, "fooFloat");
		assertEquals(209.0, f.floatValue(), 0.005);
		f = BeanUtil.pojo.getProperty(dest, "foofloat");
		assertEquals(210.0, f.floatValue(), 0.005);
		Double d = BeanUtil.pojo.getProperty(dest, "fooDouble");
		assertEquals(211.0, d.doubleValue(), 0.005);
		d = BeanUtil.pojo.getProperty(dest, "foodouble");
		assertEquals(212.0, d.doubleValue(), 0.005);
		String s = BeanUtil.pojo.getProperty(dest, "fooString");
		assertEquals("213", s);
		String[] sa = BeanUtil.pojo.getProperty(dest, "fooStringA");
		assertEquals(2, sa.length);
		assertEquals("214", sa[0]);
		assertEquals("215", sa[1]);
		assertSame(dest.getFooStringA(), sa);


		final FooBean empty = new FooBean();
		BeanCopy.from(empty).to(dest).copy();

		v = BeanUtil.pojo.getProperty(dest, "fooInteger");
		assertNull(v);
		v = BeanUtil.pojo.getProperty(dest, "fooint");
		assertEquals(0, v.intValue());
		vl = BeanUtil.pojo.getProperty(dest, "fooLong");
		assertNull(vl);
		vl = BeanUtil.pojo.getProperty(dest, "foolong");
		assertEquals(0, vl.longValue());
		vb = BeanUtil.pojo.getProperty(dest, "fooByte");
		assertNull(vb);
		vb = BeanUtil.pojo.getProperty(dest, "foobyte");
		assertEquals(0, vb.byteValue());
		c = BeanUtil.pojo.getProperty(dest, "fooCharacter");
		assertNull(c);
		c = BeanUtil.pojo.getProperty(dest, "foochar");
		assertEquals(0, c.charValue());
		b = BeanUtil.pojo.getProperty(dest, "fooBoolean");
		assertNull(b);
		b = BeanUtil.pojo.getProperty(dest, "fooboolean");
		assertFalse(b);
		f = BeanUtil.pojo.getProperty(dest, "fooFloat");
		assertNull(f);
		f = BeanUtil.pojo.getProperty(dest, "foofloat");
		assertEquals(0, f, 0.005);
		d = BeanUtil.pojo.getProperty(dest, "fooDouble");
		assertNull(d);
		d = BeanUtil.pojo.getProperty(dest, "foodouble");
		assertEquals(0, d, 0.005);
		s = BeanUtil.pojo.getProperty(dest, "fooString");
		assertNull(s);
		sa = BeanUtil.pojo.getProperty(dest, "fooStringA");
		assertNull(sa);
	}

	@Test
	void testCopyIncludes() {
		final FooBean fb = createFooBean();
		final FooBean dest = new FooBean();
		BeanCopy.from(fb).to(dest).filter(name -> (name.equals("fooInteger") || name.equals("fooLong"))).copy();

		Integer v = BeanUtil.pojo.getProperty(dest, "fooInteger");
		assertEquals(201, v.intValue());
		Long vl = BeanUtil.pojo.getProperty(dest, "fooLong");
		assertEquals(203, vl.longValue());
		v = BeanUtil.pojo.getProperty(dest, "fooint");
		assertEquals(0, v.intValue());
		vl = BeanUtil.pojo.getProperty(dest, "foolong");
		assertEquals(0, vl.longValue());
		Byte vb = BeanUtil.pojo.getProperty(dest, "fooByte");
		assertNull(vb);
		vb = BeanUtil.pojo.getProperty(dest, "foobyte");
		assertEquals(0, vb.byteValue());
		Character c = BeanUtil.pojo.getProperty(dest, "fooCharacter");
		assertNull(c);
		c = BeanUtil.pojo.getProperty(dest, "foochar");
		assertEquals(0, c.charValue());
		Boolean b = BeanUtil.pojo.getProperty(dest, "fooBoolean");
		assertNull(b);
		b = BeanUtil.pojo.getProperty(dest, "fooboolean");
		assertFalse(b);
		Float f = BeanUtil.pojo.getProperty(dest, "fooFloat");
		assertNull(f);
		f = BeanUtil.pojo.getProperty(dest, "foofloat");
		assertEquals(0, f, 0.005);
		Double d = BeanUtil.pojo.getProperty(dest, "fooDouble");
		assertNull(d);
		d = BeanUtil.pojo.getProperty(dest, "foodouble");
		assertEquals(0, d, 0.005);
		final String s = BeanUtil.pojo.getProperty(dest, "fooString");
		assertNull(s);
		final String[] sa = BeanUtil.pojo.getProperty(dest, "fooStringA");
		assertNull(sa);
	}

	@Test
	void testCopyProperties() {
		final Properties properties = new Properties();

		properties.put("fooInteger", Integer.valueOf(1));
		properties.put("fooint", Integer.valueOf(2));

		final FooBean fooBean = new FooBean();

		assertEquals(0, fooBean.getFooint());

		// copy to

		BeanCopy.from(properties).to(fooBean).copy();

		assertEquals(1, fooBean.getFooInteger().intValue());
		assertEquals(2, fooBean.getFooint());


		// copy back

		properties.clear();

		BeanCopy.from(fooBean).to(properties).copy();

		assertEquals(1, properties.get("fooInteger"));
		assertEquals(2, properties.get("fooint"));
	}




	static class Less {
		String data;

		Integer number;

		public String getData() {
			return data;
		}

		public void setData(final String data) {
			this.data = data;
		}

		public Integer getNumber() {
			return number;
		}

		public void setNumber(final Integer number) {
			this.number = number;
		}
	}

	static class More {
		String data;

		String number;

		String boo;

		public String getData() {
			return data;
		}

		public void setData(final String data) {
			this.data = data;
		}


		public String getNumber() {
			return number;
		}

		public void setNumber(final String number) {
			this.number = number;
		}

		public String getBoo() {
			return boo;
		}

		public void setBoo(final String boo) {
			this.boo = boo;
		}
	}

	@Test
	void testLessToMore() {
		final Less less = new Less();
		less.data = "data";
		less.number = new Integer(2);
		final More more = new More();
		BeanCopy.from(less).to(more).declared(true).copy();
		assertEquals("data", more.data);
		assertEquals("2", more.number);

		more.data = "tij";
		more.number = "17";
		BeanCopy.from(more).to(less).declared(true).copy();
		assertEquals("tij", less.data);
		assertEquals(17, less.number.intValue());
	}

	@Test
	void testCopyMap() {
		final Map map = new HashMap();
		map.put("fooint", Integer.valueOf(102));
		map.put("fooString", "mao");

		final FooBean dest = new FooBean();
		BeanCopy.from(map).to(dest).copy();
		assertEquals(102, dest.getFooint());
		assertEquals("mao", dest.getFooString());

		final Map destMap = new HashMap();
		BeanCopy.from(map).to(destMap).copy();
		assertEquals(2, destMap.size());
		assertEquals(Integer.valueOf(102), destMap.get("fooint"));
		assertEquals("mao", destMap.get("fooString"));
	}

	@Test
	void testCopyIgnoreNulls() {
		final FooBean fb = createFooBean();
		final FooBean dest = new FooBean();

		dest.setFooInteger(Integer.valueOf(999));
		fb.setFooInteger(null);
		BeanCopy.from(fb).to(dest).filter((s, value) -> value != null).copy();

		Integer v = BeanUtil.pojo.getProperty(dest, "fooInteger");
		assertEquals(999, v.intValue());
		v = BeanUtil.pojo.getProperty(dest, "fooint");
		assertEquals(202, v.intValue());
		Long vl = BeanUtil.pojo.getProperty(dest, "fooLong");
		assertEquals(203, vl.longValue());
		vl = BeanUtil.pojo.getProperty(dest, "foolong");
		assertEquals(204, vl.longValue());
		Byte vb = BeanUtil.pojo.getProperty(dest, "fooByte");
		assertEquals(95, vb.intValue());
		vb = BeanUtil.pojo.getProperty(dest, "foobyte");
		assertEquals(96, vb.intValue());
		Character c = BeanUtil.pojo.getProperty(dest, "fooCharacter");
		assertEquals('7', c.charValue());
		c = BeanUtil.pojo.getProperty(dest, "foochar");
		assertEquals('8', c.charValue());
		Boolean b = BeanUtil.pojo.getProperty(dest, "fooBoolean");
		assertTrue(b.booleanValue());
		b = BeanUtil.pojo.getProperty(dest, "fooboolean");
		assertFalse(b.booleanValue());
		Float f = BeanUtil.pojo.getProperty(dest, "fooFloat");
		assertEquals(209.0, f.floatValue(), 0.005);
		f = BeanUtil.pojo.getProperty(dest, "foofloat");
		assertEquals(210.0, f.floatValue(), 0.005);
		Double d = BeanUtil.pojo.getProperty(dest, "fooDouble");
		assertEquals(211.0, d.doubleValue(), 0.005);
		d = BeanUtil.pojo.getProperty(dest, "foodouble");
		assertEquals(212.0, d.doubleValue(), 0.005);
		final String s = BeanUtil.pojo.getProperty(dest, "fooString");
		assertEquals("213", s);
		final String[] sa = BeanUtil.pojo.getProperty(dest, "fooStringA");
		assertEquals(2, sa.length);
		assertEquals("214", sa[0]);
		assertEquals("215", sa[1]);
		assertSame(dest.getFooStringA(), sa);
	}

	private static FooBean createFooBean() {
		final FooBean fb = new FooBean();
		fb.setFooInteger(new Integer(201));
		fb.setFooint(202);
		fb.setFooLong(new Long(203));
		fb.setFoolong(204);
		fb.setFooByte(new Byte((byte) 95));
		fb.setFoobyte((byte) 96);
		fb.setFooCharacter(new Character('7'));
		fb.setFoochar('8');
		fb.setFooBoolean(Boolean.TRUE);
		fb.setFooboolean(false);
		fb.setFooFloat(new Float(209.0));
		fb.setFoofloat((float) 210.0);
		fb.setFooDouble(new Double(211.0));
		fb.setFoodouble(212.0);
		fb.setFooString("213");
		fb.setFooStringA(new String[]{"214", "215"});
		return fb;
	}

	public static class Source {
		public String pub = "a1";

		private final int priv = 2;

		public long getGetter() {
			return 5;
		}

		private final int _prop = 8;

		public int getProp() {
			return _prop;
		}

		protected String getMoo() {
			return moo;
		}

		protected String moo = "wof";
	}

	@Test
	void testCopyWithFields() {
		final Source source = new Source();
		final HashMap dest = new HashMap();

		BeanCopy.from(source).to(dest).copy();

		assertEquals(2, dest.size());
		assertEquals("8", dest.get("prop").toString());
		assertEquals("5", dest.get("getter").toString());

		dest.clear();
		BeanCopy.from(source).to(dest).declared(true).copy();

		assertEquals(3, dest.size());
		assertEquals("8", dest.get("prop").toString());
		assertEquals("5", dest.get("getter").toString());
		assertEquals("wof", dest.get("moo").toString());


		dest.clear();
		BeanCopy.from(source).to(dest).declared(false).includeFields(true).copy();

		assertEquals(3, dest.size());
		assertEquals("8", dest.get("prop").toString());
		assertEquals("5", dest.get("getter").toString());
		assertEquals("a1", dest.get("pub").toString());


		dest.clear();
		BeanCopy.from(source).to(dest).declared(true).includeFields(true).copy();

		assertEquals(6, dest.size());
		assertEquals("8", dest.get("prop").toString());
		assertEquals("5", dest.get("getter").toString());
		assertEquals("a1", dest.get("pub").toString());
		assertEquals("2", dest.get("priv").toString());
		assertEquals("8", dest.get("_prop").toString());
		assertEquals("wof", dest.get("moo").toString());
	}

	public static class Moo {

		private String name = "cow";
		private Integer value = Integer.valueOf(7);
		private long nick = 100;

		public String getName() {
			return name;
		}

		public void setName(final String name) {
			this.name = name;
		}

		public Integer getValue() {
			return value;
		}

		public void setValue(final Integer value) {
			this.value = value;
		}

		public long getNick() {
			return nick;
		}

		public void setNick(final long nick) {
			this.nick = nick;
		}
	}

	// ---------------------------------------------------------------- special test

	public static class PropertyBean {
		public int number;
		public PropertyBean child;
	}

	@Test
	void testFromMapToBean() {
		final Properties propsSource = new Properties();

		propsSource.put("number", 42);
		propsSource.put("child.number", 43);
		propsSource.put("nonExistantNumber", 142);
		propsSource.put("nonExistantChild.number", 143);

		final PropertyBean beanDest = new PropertyBean();

		BeanCopy.from(propsSource).to(beanDest).forced(true).copy();

		assertEquals(42, beanDest.number);
		assertEquals(43, beanDest.child.number);
	}

	@Test
	void testFromMapToMap() {
		final Properties propsSource = new Properties();

		propsSource.put("number", 42);
		propsSource.put("child.number", 43);
		propsSource.put("nonExistantNumber", 142);
		propsSource.put("nonExistantChild.number", 143);

		final Properties propsDest = new Properties();

		BeanCopy.from(propsSource).to(propsDest).copy();

		assertEquals(propsSource, propsDest);
	}

	@Test
	void testFromBeanToMap() {
		final PropertyBean beanSource = new PropertyBean();

		beanSource.number = 42;
		beanSource.child = new PropertyBean();
		beanSource.child.number = 43;

		final Properties propsDest = new Properties();

		BeanCopy
			.from(beanSource)
			.to(propsDest)
			.includeFields(true)
			.copy();


		assertEquals(2, propsDest.size());
		assertEquals(42, propsDest.get("number"));
		assertEquals(Integer.valueOf(43), BeanUtil.pojo.getProperty(propsDest, "child.number"));
	}

	@Test
	void testFromBeanToBean() {
		final PropertyBean beanSource = new PropertyBean();

		beanSource.number = 42;
		beanSource.child = new PropertyBean();
		beanSource.child.number = 43;

		final PropertyBean beanDest = new PropertyBean();

		BeanCopy.from(beanSource).to(beanDest).includeFields(true).copy();

		assertEquals(42, beanDest.number);
		assertEquals(43, beanDest.child.number);
	}

}
