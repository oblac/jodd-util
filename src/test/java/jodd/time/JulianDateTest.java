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

package jodd.time;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JulianDateTest {

	@Test
	void testSet() {
		JulianDate jdt = JulianDate.of(2008, 12, 20, 10, 44, 55, 0);
		JulianDate jdt2 = JulianDate.of(jdt.integer - 1, jdt.fraction);

		assertEquals(jdt.toLocalDateTime().getYear(), jdt2.toLocalDateTime().getYear());
		assertEquals(jdt.toLocalDateTime().getMonth(), jdt2.toLocalDateTime().getMonth());
		assertEquals(jdt.toLocalDateTime().getDayOfMonth() - 1, jdt2.toLocalDateTime().getDayOfMonth());
		assertEquals(jdt.toLocalDateTime().getHour(), jdt2.toLocalDateTime().getHour());
		assertEquals(jdt.toLocalDateTime().getMinute(), jdt2.toLocalDateTime().getMinute());
		assertEquals(jdt.toLocalDateTime().getSecond(), jdt2.toLocalDateTime().getSecond(), 0.0001);
	}

	@Test
	void testDecimalFloating() {
		LocalDateTime ldt = LocalDateTime.of(1970, 1, 13, 14, 24, 0, 0);
		JulianDate jdt = new JulianDate(2440600, 0.1);

		assertTrue(ldt.isEqual(jdt.toLocalDateTime()));

		JulianDate jdt2 = new JulianDate(2440600, 0.09999999991);
		assertTrue(ldt.isEqual(jdt2.toLocalDateTime()));

		JulianDate jdt3 = new JulianDate(2440600, 0.100000001);
		assertTrue(ldt.isEqual(jdt3.toLocalDateTime()));
	}

	@Test
	void testSetGetMillis() {
		for (int i = 0; i < 1000; i++) {
			JulianDate jdt = JulianDate.of(2003, 2, 28, 23, 59, 59, i);
			assertEquals(i, jdt.toLocalDateTime().getNano()/1000000);
		}
	}


	@Test
	void testSet999Millis() {
		JulianDate jdt = JulianDate.of(2003, 2, 28, 23, 59, 59, 999);
		assertEquals("2003-02-28T23:59:59.999", jdt.toLocalDateTime().format(ISO_LOCAL_DATE_TIME));

		jdt = JulianDate.of(2003, 2, 28, 23, 59, 60, 0);
		assertEquals("2003-03-01T00:00:00", jdt.toLocalDateTime().format(ISO_LOCAL_DATE_TIME));

		// this used to be a problem
		jdt = JulianDate.of(2003, 2, 28, 23, 59, 59, 999);        // 12 fraction digits  - last working
		assertEquals("2003-02-28T23:59:59.999", jdt.toLocalDateTime().format(ISO_LOCAL_DATE_TIME));
	}

	@Test
	void testRoundTripDouble() {
		double doubleValue = 2457754.4;
		JulianDate jdt = JulianDate.of(doubleValue);
		assertEquals(doubleValue, jdt.doubleValue());

		doubleValue = 2457754.5;
		jdt = JulianDate.of(doubleValue);
		assertEquals(doubleValue, jdt.doubleValue());

		doubleValue = 2457754.6;
		jdt = JulianDate.of(doubleValue);
		assertEquals(doubleValue, jdt.doubleValue());

		doubleValue = 2457754.7;
		jdt = JulianDate.of(doubleValue);
		assertEquals(doubleValue, jdt.doubleValue());

		doubleValue = 2457754.8;
		jdt = JulianDate.of(doubleValue);
		assertEquals(doubleValue, jdt.doubleValue());

		doubleValue = 2457448.43219907;
		jdt = JulianDate.of(doubleValue);
		assertEquals(doubleValue, jdt.doubleValue());
	}

	@Test
	void testRoundTripLocalDateTime() {
		LocalDateTime ldt = LocalDateTime.of(2016, 12, 31, 21, 36, 0, 0);
		JulianDate jdt = JulianDate.of(ldt);
		assertEquals(ldt, jdt.toLocalDateTime());

		ldt = LocalDateTime.of(2017, 1, 1, 0, 0, 0, 0);
		jdt = JulianDate.of(ldt);
		assertEquals(ldt, jdt.toLocalDateTime());

		ldt = LocalDateTime.of(2017, 1, 1, 2, 24, 0, 0);
		jdt = JulianDate.of(ldt);
		assertEquals(ldt, jdt.toLocalDateTime());

		ldt = LocalDateTime.of(2017, 1, 1, 4, 48, 0, 0);
		jdt = JulianDate.of(ldt);
		assertEquals(ldt, jdt.toLocalDateTime());

		ldt = LocalDateTime.of(2017, 1, 1, 7, 12, 0, 0);
		jdt = JulianDate.of(ldt);
		assertEquals(ldt, jdt.toLocalDateTime());

		ldt = LocalDateTime.of(2016, 2, 29, 22, 22, 22, 0);
		jdt = JulianDate.of(ldt);
		assertEquals(ldt, jdt.toLocalDateTime());
	}

	@Test
	void testRoundTripMilliseconds() {
		long milliseconds = 1483220160000L;
		JulianDate jdt = JulianDate.of(milliseconds);
		assertEquals(milliseconds, jdt.toMilliseconds());

		milliseconds = 1483228800000L;
		jdt = JulianDate.of(milliseconds);
		assertEquals(milliseconds, jdt.toMilliseconds());

		milliseconds = 1483237440000L;
		jdt = JulianDate.of(milliseconds);
		assertEquals(milliseconds, jdt.toMilliseconds());

		milliseconds = 1483246080000L;
		jdt = JulianDate.of(milliseconds);
		assertEquals(milliseconds, jdt.toMilliseconds());

		milliseconds = 1483254720000L;
		jdt = JulianDate.of(milliseconds);
		assertEquals(milliseconds, jdt.toMilliseconds());

		milliseconds = 1456784542000L;
		jdt = JulianDate.of(milliseconds);
		assertEquals(milliseconds, jdt.toMilliseconds());
	}

	@Test
	void testDoubleToLocalDateTime() {
		JulianDate jdt = JulianDate.of(2457754.4);
		assertEquals(LocalDateTime.of(2016, 12, 31, 21, 36, 0, 0), jdt.toLocalDateTime());

		jdt = JulianDate.of(2457754.5);
		assertEquals(LocalDateTime.of(2017, 1, 1, 0, 0, 0, 0), jdt.toLocalDateTime());

		jdt = JulianDate.of(2457754.6);
		assertEquals(LocalDateTime.of(2017, 1, 1, 2, 24, 0, 0), jdt.toLocalDateTime());

		jdt = JulianDate.of(2457754.7);
		assertEquals(LocalDateTime.of(2017, 1, 1, 4, 48, 0, 0), jdt.toLocalDateTime());

		jdt = JulianDate.of(2457754.8);
		assertEquals(LocalDateTime.of(2017, 1, 1, 7, 12, 0, 0), jdt.toLocalDateTime());

		jdt = JulianDate.of(2457448.43219907);
		assertEquals(LocalDateTime.of(2016, 2, 29, 22, 22, 22, 0), jdt.toLocalDateTime());
	}

	@Test
	void testDoubleToMilliseconds() {
		JulianDate jdt = JulianDate.of(2457754.4);
		assertEquals(1483220160000L, jdt.toMilliseconds());

		jdt = JulianDate.of(2457754.5);
		assertEquals(1483228800000L, jdt.toMilliseconds());

		jdt = JulianDate.of(2457754.6);
		assertEquals(1483237440000L, jdt.toMilliseconds());

		jdt = JulianDate.of(2457754.7);
		assertEquals(1483246080000L, jdt.toMilliseconds());

		jdt = JulianDate.of(2457754.8);
		assertEquals(1483254720000L, jdt.toMilliseconds());

		jdt = JulianDate.of(2457448.43219907);
		assertEquals(1456784542000L, jdt.toMilliseconds());
	}

	@Test
	void testLocalDateTimeToDouble() {
		JulianDate jdt = JulianDate.of(LocalDateTime.of(2016, 12, 31, 21, 36, 0, 0));
		assertEquals(2457754.4, jdt.doubleValue());

		jdt = JulianDate.of(LocalDateTime.of(2017, 1, 1, 0, 0, 0, 0));
		assertEquals(2457754.5, jdt.doubleValue());

		jdt = JulianDate.of(LocalDateTime.of(2017, 1, 1, 2, 24, 0, 0));
		assertEquals(2457754.6, jdt.doubleValue());

		jdt = JulianDate.of(LocalDateTime.of(2017, 1, 1, 4, 48, 0, 0));
		assertEquals(2457754.7, jdt.doubleValue());

		jdt = JulianDate.of(LocalDateTime.of(2017, 1, 1, 7, 12, 0, 0));
		assertEquals(2457754.8, jdt.doubleValue());

		jdt = JulianDate.of(LocalDateTime.of(2016, 2, 29, 22, 22, 22, 0));
		assertEquals(2457448.4321990740, jdt.doubleValue(), 1.0e-10);
	}

	@Test
	void testLocalDateTimeToMilliseconds() {
		JulianDate jdt = JulianDate.of(LocalDateTime.of(2016, 12, 31, 21, 36, 0, 0));
		assertEquals(1483220160000L, jdt.toMilliseconds());

		jdt = JulianDate.of(LocalDateTime.of(2017, 1, 1, 0, 0, 0, 0));
		assertEquals(1483228800000L, jdt.toMilliseconds());

		jdt = JulianDate.of(LocalDateTime.of(2017, 1, 1, 2, 24, 0, 0));
		assertEquals(1483237440000L, jdt.toMilliseconds());

		jdt = JulianDate.of(LocalDateTime.of(2017, 1, 1, 4, 48, 0, 0));
		assertEquals(1483246080000L, jdt.toMilliseconds());

		jdt = JulianDate.of(LocalDateTime.of(2017, 1, 1, 7, 12, 0, 0));
		assertEquals(1483254720000L, jdt.toMilliseconds());

		jdt = JulianDate.of(LocalDateTime.of(2016, 2, 29, 22, 22, 22, 0));
		assertEquals(1456784542000L, jdt.toMilliseconds());
	}

	@Test
	void testMillisecondsToDouble() {
		JulianDate jdt = JulianDate.of(1483220160000L);
		assertEquals(2457754.4, jdt.doubleValue());

		jdt = JulianDate.of(1483228800000L);
		assertEquals(2457754.5, jdt.doubleValue());

		jdt = JulianDate.of(1483237440000L);
		assertEquals(2457754.6, jdt.doubleValue());

		jdt = JulianDate.of(1483246080000L);
		assertEquals(2457754.7, jdt.doubleValue());

		jdt = JulianDate.of(1483254720000L);
		assertEquals(2457754.8, jdt.doubleValue());

		jdt = JulianDate.of(1456784542000L);
		assertEquals(2457448.432199074, jdt.doubleValue(), 1.0e-10);
	}

	@Test
	void testMillisecondsToLocalDateTime() {
		JulianDate jdt = JulianDate.of(1483220160000L);
		assertEquals(LocalDateTime.of(2016, 12, 31, 21, 36, 0, 0), jdt.toLocalDateTime());

		jdt = JulianDate.of(1483228800000L);
		assertEquals(LocalDateTime.of(2017, 1, 1, 0, 0, 0, 0), jdt.toLocalDateTime());

		jdt = JulianDate.of(1483237440000L);
		assertEquals(LocalDateTime.of(2017, 1, 1, 2, 24, 0, 0), jdt.toLocalDateTime());

		jdt = JulianDate.of(1483246080000L);
		assertEquals(LocalDateTime.of(2017, 1, 1, 4, 48, 0, 0), jdt.toLocalDateTime());

		jdt = JulianDate.of(1483254720000L);
		assertEquals(LocalDateTime.of(2017, 1, 1, 7, 12, 0, 0), jdt.toLocalDateTime());

		jdt = JulianDate.of(1456784542000L);
		assertEquals(LocalDateTime.of(2016, 2, 29, 22, 22, 22, 0), jdt.toLocalDateTime());
	}
}
