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

import java.math.BigDecimal;
import java.time.Instant;
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

	/*
	 This test just verifies that the BigDecimal values
	 are "close", e.g. within double precision.
	 In theory, they should be even closer than that,
	 but that would be more of a pain to test.
	 */
	@Test
	void testToAndFromBigDecimal() {
		JulianDate jdt = JulianDate.of(2457754, 0.4);
		BigDecimal bigDecimal = BigDecimal.valueOf(2457754.4);
		assertEquals(bigDecimal.doubleValue(), jdt.toBigDecimal().doubleValue());
		assertEquals(bigDecimal.doubleValue(), JulianDate.of(bigDecimal).toBigDecimal().doubleValue());

		jdt = JulianDate.of(2457754, 0.5);
		bigDecimal = BigDecimal.valueOf(2457754.5);
		assertEquals(bigDecimal.doubleValue(), jdt.toBigDecimal().doubleValue());
		assertEquals(bigDecimal.doubleValue(), JulianDate.of(bigDecimal).toBigDecimal().doubleValue());

		jdt = JulianDate.of(2457754, 0.6);
		bigDecimal = BigDecimal.valueOf(2457754.6);
		assertEquals(bigDecimal.doubleValue(), jdt.toBigDecimal().doubleValue());
		assertEquals(bigDecimal.doubleValue(), JulianDate.of(bigDecimal).toBigDecimal().doubleValue());

		jdt = JulianDate.of(2457754, 0.7);
		bigDecimal = BigDecimal.valueOf(2457754.7);
		assertEquals(bigDecimal.doubleValue(), jdt.toBigDecimal().doubleValue());
		assertEquals(bigDecimal.doubleValue(), JulianDate.of(bigDecimal).toBigDecimal().doubleValue());

		jdt = JulianDate.of(2457754, 0.8);
		bigDecimal = BigDecimal.valueOf(2457754.8);
		assertEquals(bigDecimal.doubleValue(), jdt.toBigDecimal().doubleValue());
		assertEquals(bigDecimal.doubleValue(), JulianDate.of(bigDecimal).toBigDecimal().doubleValue());

		jdt = JulianDate.of(2457448, 0.43219907);
		bigDecimal = BigDecimal.valueOf(2457448.43219907);
		assertEquals(bigDecimal.doubleValue(), jdt.toBigDecimal().doubleValue());
		assertEquals(bigDecimal.doubleValue(), JulianDate.of(bigDecimal).toBigDecimal().doubleValue());
	}

	@Test
	void testToAndFromDouble() {
		JulianDate jdt = JulianDate.of(2457754, 0.4);
		double doubleValue = 2457754.4;
		assertEquals(doubleValue, jdt.toDouble());
		assertEquals(doubleValue, JulianDate.of(doubleValue).toDouble());

		jdt = JulianDate.of(2457754, 0.5);
		doubleValue = 2457754.5;
		assertEquals(doubleValue, jdt.toDouble());
		assertEquals(doubleValue, JulianDate.of(doubleValue).toDouble());

		jdt = JulianDate.of(2457754, 0.6);
		doubleValue = 2457754.6;
		assertEquals(doubleValue, jdt.toDouble());
		assertEquals(doubleValue, JulianDate.of(doubleValue).toDouble());

		jdt = JulianDate.of(2457754, 0.7);
		doubleValue = 2457754.7;
		assertEquals(doubleValue, jdt.toDouble());
		assertEquals(doubleValue, JulianDate.of(doubleValue).toDouble());

		jdt = JulianDate.of(2457754, 0.8);
		doubleValue = 2457754.8;
		assertEquals(doubleValue, jdt.toDouble());
		assertEquals(doubleValue, JulianDate.of(doubleValue).toDouble());

		jdt = JulianDate.of(2457448, 0.43219907);
		doubleValue = 2457448.43219907;
		assertEquals(doubleValue, jdt.toDouble());
		assertEquals(doubleValue, JulianDate.of(doubleValue).toDouble());
	}

	@Test
	void testToAndFromLocalDateTime() {
		JulianDate jdt = JulianDate.of(2016, 12, 31, 21, 36, 0, 0);
		LocalDateTime ldt = LocalDateTime.of(2016, 12, 31, 21, 36, 0, 0);
		assertEquals(jdt, JulianDate.of(ldt));
		assertEquals(ldt, jdt.toLocalDateTime());
		assertEquals(ldt, JulianDate.of(ldt).toLocalDateTime());

		jdt = JulianDate.of(2017, 1, 1, 0, 0, 0, 0);
		ldt = LocalDateTime.of(2017, 1, 1, 0, 0, 0, 0);
		assertEquals(jdt, JulianDate.of(ldt));
		assertEquals(ldt, jdt.toLocalDateTime());
		assertEquals(ldt, JulianDate.of(ldt).toLocalDateTime());

		jdt = JulianDate.of(2017, 1, 1, 2, 24, 0, 0);
		ldt = LocalDateTime.of(2017, 1, 1, 2, 24, 0, 0);
		assertEquals(jdt, JulianDate.of(ldt));
		assertEquals(ldt, jdt.toLocalDateTime());
		assertEquals(ldt, JulianDate.of(ldt).toLocalDateTime());

		jdt = JulianDate.of(2017, 1, 1, 4, 48, 0, 0);
		ldt = LocalDateTime.of(2017, 1, 1, 4, 48, 0, 0);
		assertEquals(jdt, JulianDate.of(ldt));
		assertEquals(ldt, jdt.toLocalDateTime());
		assertEquals(ldt, JulianDate.of(ldt).toLocalDateTime());

		jdt = JulianDate.of(2017, 1, 1, 7, 12, 0, 0);
		ldt = LocalDateTime.of(2017, 1, 1, 7, 12, 0, 0);
		assertEquals(jdt, JulianDate.of(ldt));
		assertEquals(ldt, jdt.toLocalDateTime());
		assertEquals(ldt, JulianDate.of(ldt).toLocalDateTime());

		jdt = JulianDate.of(2016, 2, 29, 22, 22, 22, 0);
		ldt = LocalDateTime.of(2016, 2, 29, 22, 22, 22, 0);
		assertEquals(jdt, JulianDate.of(ldt));
		assertEquals(ldt, jdt.toLocalDateTime());
		assertEquals(ldt, JulianDate.of(ldt).toLocalDateTime());
	}

	@Test
	void testToAndFromInstant() {
		JulianDate jdt = JulianDate.of(2016, 12, 31, 21, 36, 0, 0);
		Instant instant = Instant.parse("2016-12-31T21:36:00Z");
		assertEquals(jdt, JulianDate.of(instant));
		assertEquals(instant, jdt.toInstant());
		assertEquals(instant, JulianDate.of(instant).toInstant());

		jdt = JulianDate.of(2017, 1, 1, 0, 0, 0, 0);
		instant = Instant.parse("2017-01-01T00:00:00Z");
		assertEquals(jdt, JulianDate.of(instant));
		assertEquals(instant, jdt.toInstant());
		assertEquals(instant, JulianDate.of(instant).toInstant());

		jdt = JulianDate.of(2017, 1, 1, 2, 24, 0, 0);
		instant = Instant.parse("2017-01-01T02:24:00Z");
		assertEquals(jdt, JulianDate.of(instant));
		assertEquals(instant, jdt.toInstant());
		assertEquals(instant, JulianDate.of(instant).toInstant());

		jdt = JulianDate.of(2017, 1, 1, 4, 48, 0, 0);
		instant = Instant.parse("2017-01-01T04:48:00Z");
		assertEquals(jdt, JulianDate.of(instant));
		assertEquals(instant, jdt.toInstant());
		assertEquals(instant, JulianDate.of(instant).toInstant());

		jdt = JulianDate.of(2017, 1, 1, 7, 12, 0, 0);
		instant = Instant.parse("2017-01-01T07:12:00Z");
		assertEquals(jdt, JulianDate.of(instant));
		assertEquals(instant, jdt.toInstant());
		assertEquals(instant, JulianDate.of(instant).toInstant());

		jdt = JulianDate.of(2016, 2, 29, 22, 22, 22, 0);
		instant = Instant.parse("2016-02-29T22:22:22Z");
		assertEquals(jdt, JulianDate.of(instant));
		assertEquals(instant, jdt.toInstant());
		assertEquals(instant, JulianDate.of(instant).toInstant());
	}

	@Test
	void testToAndFromMilliseconds() {
		JulianDate jdt = JulianDate.of(2016, 12, 31, 21, 36, 0, 0);
		long milliseconds = 1483220160000L;
		assertEquals(jdt, JulianDate.of(milliseconds));
		assertEquals(milliseconds, jdt.toMilliseconds());
		assertEquals(milliseconds, JulianDate.of(milliseconds).toMilliseconds());

		jdt = JulianDate.of(2017, 1, 1, 0, 0, 0, 0);
		milliseconds = 1483228800000L;
		assertEquals(jdt, JulianDate.of(milliseconds));
		assertEquals(milliseconds, jdt.toMilliseconds());
		assertEquals(milliseconds, JulianDate.of(milliseconds).toMilliseconds());

		jdt = JulianDate.of(2017, 1, 1, 2, 24, 0, 0);
		milliseconds = 1483237440000L;
		assertEquals(jdt, JulianDate.of(milliseconds));
		assertEquals(milliseconds, jdt.toMilliseconds());
		assertEquals(milliseconds, JulianDate.of(milliseconds).toMilliseconds());

		jdt = JulianDate.of(2017, 1, 1, 4, 48, 0, 0);
		milliseconds = 1483246080000L;
		assertEquals(jdt, JulianDate.of(milliseconds));
		assertEquals(milliseconds, jdt.toMilliseconds());
		assertEquals(milliseconds, JulianDate.of(milliseconds).toMilliseconds());

		jdt = JulianDate.of(2017, 1, 1, 7, 12, 0, 0);
		milliseconds = 1483254720000L;
		assertEquals(jdt, JulianDate.of(milliseconds));
		assertEquals(milliseconds, jdt.toMilliseconds());
		assertEquals(milliseconds, JulianDate.of(milliseconds).toMilliseconds());

		jdt = JulianDate.of(2016, 2, 29, 22, 22, 22, 0);
		milliseconds = 1456784542000L;
		assertEquals(jdt, JulianDate.of(milliseconds));
		assertEquals(milliseconds, jdt.toMilliseconds());
		assertEquals(milliseconds, JulianDate.of(milliseconds).toMilliseconds());
	}

	@Test
	void testCalendarDateTimeCloseToJulianDateTime() {
		JulianDate jdt1 = JulianDate.of(2016, 12, 31, 21, 36, 0, 0);
		JulianDate jdt2 = JulianDate.of(2457754, 0.4);
		assertEquals(jdt1.toDouble(), jdt2.toDouble());
		assertEquals(jdt1.toMilliseconds(), jdt2.toMilliseconds());

		jdt1 = JulianDate.of(2017, 1, 1, 0, 0, 0, 0);
		jdt2 = JulianDate.of(2457754, 0.5);
		assertEquals(jdt1.toDouble(), jdt2.toDouble());
		assertEquals(jdt1.toMilliseconds(), jdt2.toMilliseconds());

		jdt1 = JulianDate.of(2017, 1, 1, 2, 24, 0, 0);
		jdt2 = JulianDate.of(2457754, 0.6);
		assertEquals(jdt1.toDouble(), jdt2.toDouble());
		assertEquals(jdt1.toMilliseconds(), jdt2.toMilliseconds());

		jdt1 = JulianDate.of(2017, 1, 1, 4, 48, 0, 0);
		jdt2 = JulianDate.of(2457754, 0.7);
		assertEquals(jdt1.toDouble(), jdt2.toDouble());
		assertEquals(jdt1.toMilliseconds(), jdt2.toMilliseconds());

		jdt1 = JulianDate.of(2017, 1, 1, 7, 12, 0, 0);
		jdt2 = JulianDate.of(2457754, 0.8);
		assertEquals(jdt1.toDouble(), jdt2.toDouble());
		assertEquals(jdt1.toMilliseconds(), jdt2.toMilliseconds());

		jdt1 = JulianDate.of(2016, 2, 29, 22, 22, 22, 0);
		jdt2 = JulianDate.of(2457448, 0.43219907);
		assertEquals(jdt1.toDouble(), jdt2.toDouble(), 10e-9);
		assertEquals(jdt1.toMilliseconds(), jdt2.toMilliseconds());
	}

	@Test
	void testDeltaRounding() {
		testDeltaRounding(JulianDate.of(2457754, 0.4));
		testDeltaRounding(JulianDate.of(2457754, 0.6));
		testDeltaRounding(JulianDate.of(2457754, 0.7));
		testDeltaRounding(JulianDate.of(2457754, 0.8));
	}

	/**
	 * Tests that more than 0.5 ms to rounds up and less than 0.5 ms rounds down
	 * @param jdt a JulianDate equal to a whole number of milliseconds
	 */
	private void testDeltaRounding(JulianDate jdt) {
		long milliseconds = jdt.toMilliseconds();
		double lessThanHalfMillisecond = 0.00000000578;
		double moreThanHalfMillisecond = 0.00000000579;
		assertEquals(milliseconds, jdt.sub(lessThanHalfMillisecond).toMilliseconds());
		assertEquals(milliseconds, jdt.add(lessThanHalfMillisecond).toMilliseconds());
		assertEquals(milliseconds - 1, jdt.sub(moreThanHalfMillisecond).toMilliseconds());
		assertEquals(milliseconds + 1, jdt.add(moreThanHalfMillisecond).toMilliseconds());
	}
}
