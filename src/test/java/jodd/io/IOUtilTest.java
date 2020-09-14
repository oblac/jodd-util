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

package jodd.io;

import jodd.util.ArraysUtil;
import jodd.util.MathUtil;
import jodd.util.SystemUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link IOUtil}.
 * <p>
 * Tests are grouped in nested classes.
 */
class IOUtilTest {

    static final File BASE_DIR = new File(SystemUtil.info().getTempDir(), "jodd/StreamUtilTest");

    @BeforeAll
    static void beforeAll() throws Exception {
        if (BASE_DIR.exists()) {
            // clean up all subdirs & files
            Files.walk(BASE_DIR.toPath(), FileVisitOption.FOLLOW_LINKS)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .peek(System.out::println)
                    .forEach(File::delete);
        }
        // created directory is needed for tests
        BASE_DIR.mkdirs();
    }

    @Nested
    @DisplayName("tests for StreamUtil#close - method")
    class Close {

        private class MyCloseable implements Closeable {

            boolean closed = false;
            boolean flushed = false;

            @Override
            public void close() throws IOException {
                closed = true;
            }
        }

        private class MyFlushable extends MyCloseable implements Flushable {
            @Override
            public void flush() throws IOException {
                flushed = true;
            }
        }

        @Test
        void close_with_null() {
            IOUtil.close(null);
        }

        @Test
        void close_with_closeable_instance() {
            final MyCloseable input = new MyCloseable();

            IOUtil.close(input);

            // asserts
            assertTrue(input.closed);
            assertFalse(input.flushed);
        }

        @Test
        void close_with_closeable_and_flushable_instance() {
            final MyFlushable input = new MyFlushable();

            IOUtil.close(input);

            // asserts
            assertTrue(input.closed);
            assertTrue(input.flushed);
        }

    }


    @Nested
    @DisplayName("tests for StreamUtil#compare - methods")
    class Compare {

        @Test
        void testCompareWithReaderInstances_ExpectedSuccessfulCompare() throws Exception {

            final String text = new String("jodd and german umlauts öäü".getBytes(),Charset.forName("ISO-8859-1"));

            final boolean actual;
            try (final StringReader reader_1 = new StringReader(text); final StringReader reader_2 = new StringReader(text)) {
                actual = IOUtil.compare(reader_1, reader_2);
            }

            // asserts
            assertTrue(actual);
        }

        @Test
        void testCompareWithReaderInstances_ExpectedNoSuccessfulCompare() throws Exception {

            final String text_1 = "jodd and german umlauts öäü";
            final String text_2 = new String(text_1.getBytes(),Charset.forName("ISO-8859-1"));

            final boolean actual;

            try (final StringReader reader_1 = new StringReader(text_1); final StringReader reader_2 = new StringReader(text_2)) {
                actual = IOUtil.compare(reader_1, reader_2);
            }

            // asserts
            assertFalse(actual);
        }

        @Test
        void testCompareWithInputStreams_ExpectedSuccessfulCompare(final TestInfo testInfo) throws Exception {

            final String text = "jodd makes fun!" + System.lineSeparator();
            final File file = new File(IOUtilTest.BASE_DIR, testInfo.getTestMethod().get().getName() + ".txt");
            FileUtil.writeString(file, text, StandardCharsets.UTF_8);

            final boolean actual;

            try (final ByteArrayInputStream in1 = new ByteArrayInputStream(text.getBytes());
                 final FileInputStream in2 = new FileInputStream(file)) {
                actual = IOUtil.compare(in1, in2);
            }

            // asserts
            assertTrue(actual);
        }

        @Test
        void testCompareWithInputStreams_ExpectedNoSuccessfulCompare(final TestInfo testInfo) throws Exception {

            final String text = "jodd makes fun!";
            final File file = new File(IOUtilTest.BASE_DIR, testInfo.getTestMethod().get().getName() + ".txt");
            FileUtil.writeString(file, " " + text, StandardCharsets.UTF_8);

            final boolean actual;

            try (final ByteArrayInputStream in1 = new ByteArrayInputStream(text.getBytes());
                 final FileInputStream in2 = new FileInputStream(file)) {
                actual = IOUtil.compare(in1, in2);
            }

            // asserts
            assertFalse(actual);
        }

    }


    @Nested
    @DisplayName("tests for StreamUtil#readAvailableBytes - method")
    class ReadAvailableBytes {

        @Test
        void testReadAvailableBytes_with_null() throws Exception {
            assertThrows(NullPointerException.class, () -> {
               IOUtil.readAvailableBytes(null);
            });
        }

        @Test
        void testReadAvailableBytes_with_inputstream_from_empty_byte_arry() throws Exception {

            final byte[] input = new byte[]{};

            final int expected_length = 0;
            final byte[] expected_array = new byte[]{};


            final byte[] actual = IOUtil.readAvailableBytes(new ByteArrayInputStream(input));

            // asserts
            assertNotNull(actual);
            assertEquals(expected_length, actual.length);
            assertArrayEquals(expected_array, actual);
        }

        @Test
        void testReadAvailableBytes_with_inputstream() throws Exception {

            final byte[] input = "jodd".getBytes();

            final int expected_length = 4;
            final byte[] expected_array = new byte[]{106,111,100,100};

            final byte[] actual = IOUtil.readAvailableBytes(new ByteArrayInputStream(input));

            // asserts
            assertNotNull(actual);
            assertEquals(expected_length, actual.length);
            assertArrayEquals(expected_array, actual);
        }

    }

    @Nested
    @DisplayName("tests for StreamUtil#readChars - methods")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS) // needed because annotation MethodSource requires static method without that
    class ReadChars {

        @Test
        void testReadChars_InputStream(final TestInfo testInfo) throws Exception {

            final String text = "jodd - Get things done!" + System.lineSeparator();
            final char[] expected = text.toCharArray();
            final File file = new File(BASE_DIR, testInfo.getTestMethod().get().getName());

            FileUtil.writeString(file, text, StandardCharsets.UTF_8);

            char[] actual = null;

            try (final FileInputStream inputStream = new FileInputStream(file)) {
                actual = IOUtil.readChars(inputStream);
            }

            // asserts
            assertNotNull(actual);
            assertArrayEquals(expected, actual);
        }


        @ParameterizedTest
        @MethodSource("testdata_testReadChars_InputStream_CharCount")
        void testReadChars_InputStream_CharCount(final char[] expected, final String text, final int charCount, final TestInfo testInfo ) throws Exception {

            final int random = MathUtil.randomInt(1, 2500);
            final File file = new File(BASE_DIR, testInfo.getTestMethod().get().getName() + "." + random);

            FileUtil.writeString(file, text, StandardCharsets.UTF_8);

            char[] actual = null;

            try (final FileInputStream inputStream = new FileInputStream(file)) {
                actual = IOUtil.readChars(inputStream, charCount);
            }

            // asserts
            assertNotNull(actual);
            assertArrayEquals(expected, actual);
        }

        Stream<Arguments> testdata_testReadChars_InputStream_CharCount() {
            return Stream.of(
                    Arguments.of("jodd".toCharArray(),"jodd", 34 ),
                    Arguments.of("jodd".toCharArray(),"jodd", 4 ),
                    Arguments.of("jo".toCharArray(),"jodd", 2 ),
                    Arguments.of("".toCharArray(),"jodd", 0 )
            );
        }

        @ParameterizedTest
        @MethodSource("testdata_testReadChars_InputStream_Encoding")
        void testReadChars_InputStream_Encoding(final char[] expected, final String text, final String encoding) throws Exception {

            char[] actual = null;

            try (final ByteArrayInputStream inputStream = new ByteArrayInputStream(text.getBytes())) {
                actual = IOUtil.readChars(inputStream, Charset.forName(encoding));
            }

            // asserts
            assertNotNull(actual);
            assertArrayEquals(expected, actual);
        }

        Stream<Arguments> testdata_testReadChars_InputStream_Encoding() throws Exception {
            return Stream.of(
                    Arguments.of("äüö".toCharArray(),"äüö", "UTF-8" ),
                    Arguments.of(new String("üöä".getBytes(), StandardCharsets.ISO_8859_1).toCharArray(),"üöä", "ISO-8859-1" )
            );
        }


        @ParameterizedTest
        @MethodSource("testdata_testReadChars_InputStream_Encoding_CharCount")
        void testReadChars_InputStream_Encoding_CharCount(final char[] expected, final String text, final String encoding, final int charCount) throws Exception {

            char[] actual = null;

            try (final ByteArrayInputStream inputStream = new ByteArrayInputStream(text.getBytes())) {
                actual = IOUtil.readChars(inputStream, Charset.forName(encoding), charCount);
            }

            // asserts
            assertNotNull(actual);
            assertArrayEquals(expected, actual);
        }

        Stream<Arguments> testdata_testReadChars_InputStream_Encoding_CharCount() throws Exception {
            return Stream.of(
                    Arguments.of("äüö".toCharArray(),"äüö", "UTF-8", 4 ),
                    Arguments.of("j".toCharArray(), "jodd", "ISO-8859-1", 1 ),
                    Arguments.of(new String("jodd".getBytes(), StandardCharsets.US_ASCII).toCharArray(),"jodd", "US-ASCII", 44 )
            );
        }


        @ParameterizedTest
        @MethodSource("testdata_testReadChars_Reader")
        void testReadChars_Reader(final char[] expected, final String text) throws Exception {

            char[] actual = null;

            try (final StringReader reader = new StringReader(text)) {
                actual = IOUtil.readChars(reader);
            }

            // asserts
            assertNotNull(actual);
            assertArrayEquals(expected, actual);
        }

        Stream<Arguments> testdata_testReadChars_Reader() throws Exception {
            return Stream.of(
                    Arguments.of("äüö".toCharArray(),"äüö" ),
                    Arguments.of("jodd makes fun".toCharArray(), "jodd makes fun")
            );
        }


        @ParameterizedTest
        @MethodSource("testdata_testReadChars_Reader_CharCount")
        void testReadChars_Reader_CharCount(final char[] expected, final String text, final int charCount) throws Exception {

            char[] actual = null;

            try (final StringReader reader = new StringReader(text)) {
                actual = IOUtil.readChars(reader, charCount);
            }

            // asserts
            assertNotNull(actual);
            assertArrayEquals(expected, actual);
        }

        Stream<Arguments> testdata_testReadChars_Reader_CharCount() throws Exception {
            return Stream.of(
                    Arguments.of("ä".toCharArray(),"äüö", 1 ),
                    Arguments.of("jodd makes fun".toCharArray(), "jodd makes fun", "jodd makes fun".length()),
                    Arguments.of("jodd makes fun".toCharArray(), "jodd makes fun", 478)
            );
        }

    }

    @Nested
    @DisplayName("tests for StreamUtil#readBytes - methods")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS) // needed because annotation MethodSource requires static method without that
    class ReadBytes {

        @ParameterizedTest
        @MethodSource("testdata_testReadBytes_InputStream")
        void testReadBytes_InputStream(final byte[] expected, final String text) throws Exception {

            byte[] actual = null;

            try (final ByteArrayInputStream inputStream = new ByteArrayInputStream(text.getBytes())) {
                actual = IOUtil.readBytes(inputStream);
            }

            // asserts
            assertNotNull(actual);
            assertArrayEquals(expected, actual);
        }

        Stream<Arguments> testdata_testReadBytes_InputStream() throws Exception {
            return Stream.of(
                    Arguments.of("äöü".getBytes(),"äöü" ),
                    Arguments.of("".getBytes(),"" )
            );
        }


        @ParameterizedTest
        @MethodSource("testdata_testReadBytes_InputStream_ByteCount")
        void testReadBytes_InputStream_ByteCount(final byte[] expected, final String text, final int byteCount) throws Exception {

            byte[] actual = null;

            try (final ByteArrayInputStream inputStream = new ByteArrayInputStream(text.getBytes())) {
                actual = IOUtil.readBytes(inputStream, byteCount);
            }

            // asserts
            assertNotNull(actual);
            assertArrayEquals(expected, actual);
        }

        Stream<Arguments> testdata_testReadBytes_InputStream_ByteCount() throws Exception {
            return Stream.of(
                    Arguments.of(ArraysUtil.subarray("ä".getBytes(),0,1), "äöü", 1),
                    Arguments.of("jo".getBytes(), "jodd", 2),
                    Arguments.of("".getBytes(), "", 8)
            );
        }


        @ParameterizedTest
        @MethodSource("testdata_testReadBytes_Reader")
        void testReadBytes_Reader(final byte[] expected, final String text) throws Exception {

            byte[] actual = null;

            try (final StringReader reader = new StringReader(text)) {
                actual = IOUtil.readBytes(reader);
            }

            // asserts
            assertNotNull(actual);
            assertArrayEquals(expected, actual);
        }

        Stream<Arguments> testdata_testReadBytes_Reader() throws Exception {
            return Stream.of(
                    Arguments.of("äöü".getBytes(), "äöü"),
                    Arguments.of("".getBytes(), "")
            );
        }


        @ParameterizedTest
        @MethodSource("testdata_testReadBytes_Reader_ByteCount")
        void testReadBytes_Reader_ByteCount(final byte[] expected, final String text, final int byteCount) throws Exception {

            byte[] actual = null;

            try (final StringReader reader = new StringReader(text)) {
                actual = IOUtil.readBytes(reader, byteCount);
            }

            // asserts
            assertNotNull(actual);
            assertArrayEquals(expected, actual);
        }

        Stream<Arguments> testdata_testReadBytes_Reader_ByteCount() throws Exception {
            return Stream.of(
                    Arguments.of("äö".getBytes(), "äöü", 2),
                    Arguments.of("jodd".getBytes(), "jodd", 8),
                    Arguments.of("".getBytes(), "jodd makes fun", 0),
                    Arguments.of("".getBytes(), "", 4)
            );
        }


        @ParameterizedTest
        @MethodSource("testdata_testReadBytes_Reader_Encoding")
        void testReadBytes_Reader_Encoding(final byte[] expected, final String text, final String encoding, final TestInfo testInfo) throws Exception {

            final int random = MathUtil.randomInt(1, 2500);
            final File file = new File(IOUtilTest.BASE_DIR, testInfo.getTestMethod().get().getName() + random);

            FileUtil.writeString(file, text, Charset.forName(encoding));

            byte[] actual = null;

            try (final FileReader reader = new FileReader(file)) {
                actual = IOUtil.readBytes(reader, Charset.forName(encoding));
            }

            // asserts
            assertNotNull(actual);
            assertArrayEquals(expected, actual);
        }

        Stream<Arguments> testdata_testReadBytes_Reader_Encoding() throws Exception {
            return Stream.of(
                    Arguments.of("jodd".getBytes(StandardCharsets.ISO_8859_1), "jodd" , "ISO-8859-1"),
                    Arguments.of("üäö".getBytes(StandardCharsets.UTF_8), "üäö" , "UTF-8")
            );
        }


        @ParameterizedTest
        @MethodSource("testdata_testReadBytes_Reader_Encoding_ByteCount")
        void testReadBytes_Reader_Encoding_ByteCount(final byte[] expected, final String text, final String encoding, final int byteCount, final TestInfo testInfo) throws Exception {

            final int random = MathUtil.randomInt(1, 2500);
            final File file = new File(IOUtilTest.BASE_DIR, testInfo.getTestMethod().get().getName() + random);

            FileUtil.writeString(file, text, Charset.forName(encoding));

            byte[] actual = null;

            try (final FileReader reader = new FileReader(file)) {
                actual = IOUtil.readBytes(reader, Charset.forName(encoding), byteCount);
            }

            // asserts
            assertNotNull(actual);
            assertArrayEquals(expected, actual);
        }

        Stream<Arguments> testdata_testReadBytes_Reader_Encoding_ByteCount() {
            return Stream.of(
                    Arguments.of("jodd".getBytes(StandardCharsets.ISO_8859_1), "jodd" , "ISO-8859-1", 10),
                    Arguments.of("j".getBytes(StandardCharsets.ISO_8859_1), "jodd" , "ISO-8859-1", 1),
                    Arguments.of("üäö".getBytes(StandardCharsets.UTF_8), "üäö" , "UTF-8", 3)
            );
        }

    }

    @Nested
    @DisplayName("tests for StreamUtil#copy - methods")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS) // needed because annotation MethodSource requires static method without that
    class Copy {

        @Test
        void testCopy_Inputstream_Outputstream() throws Exception {

            try (final ByteArrayInputStream in = new ByteArrayInputStream("input".getBytes());
                 final ByteArrayOutputStream out = new ByteArrayOutputStream()) {

                IOUtil.copy(in, out);

                // asserts
                assertEquals("input", out.toString());
            }

        }

        @ParameterizedTest
        @MethodSource("testdata_testCopy_Inputstream_Outputstream_ByteCount")
        void testCopy_Inputstream_Outputstream_ByteCount(final String expected, final String text, final int byteCount) throws Exception {

            try (final ByteArrayInputStream in = new ByteArrayInputStream(text.getBytes());
                 final ByteArrayOutputStream out = new ByteArrayOutputStream()) {

                IOUtil.copy(in, out, byteCount);

                // asserts
                assertEquals(expected, out.toString());
            }

        }

        Stream<Arguments> testdata_testCopy_Inputstream_Outputstream_ByteCount() {
            return Stream.of(
                    Arguments.of("The Unbearable Lightness of Java", "The Unbearable Lightness of Java", IOUtil.ioBufferSize + 250),
                    Arguments.of("j", "jodd" , 1),
                    Arguments.of("jodd makes fun!", "jodd makes fun!",  15),
                    Arguments.of("", "text does not matter",  0)
            );
        }

        @ParameterizedTest
        @MethodSource("testdata_testCopy_Inputstream_Writer_Encoding")
        void testCopy_Inputstream_Writer_Encoding(final String expected, final String text, final String encoding) throws Exception {

            try (final ByteArrayInputStream in = new ByteArrayInputStream(text.getBytes());
                 final StringWriter writer = new StringWriter()) {

                IOUtil.copy(in, writer, Charset.forName(encoding));

                // asserts
                assertEquals(expected, writer.toString());
            }

        }

        Stream<Arguments> testdata_testCopy_Inputstream_Writer_Encoding() {
            return Stream.of(
                    Arguments.of("The Unbearable Lightness of Java", "The Unbearable Lightness of Java", "UTF-8"),
                    Arguments.of("Ã¼Ã¶Ã¤", "üöä", "ISO-8859-1"),
                    Arguments.of("", "", "US-ASCII")
            );
        }

        @ParameterizedTest
        @MethodSource("testdata_testCopy_Inputstream_Writer_Encoding_ByteCount")
        void testCopy_Inputstream_Writer_Encoding_ByteCount(final String expected, final String text, final String encoding, final int byteCount) throws Exception {

            try (final ByteArrayInputStream in = new ByteArrayInputStream(text.getBytes());
                 final StringWriter writer = new StringWriter()) {

                IOUtil.copy(in, writer, Charset.forName(encoding), byteCount );

                // asserts
                assertEquals(expected, writer.toString());
            }

        }

        Stream<Arguments> testdata_testCopy_Inputstream_Writer_Encoding_ByteCount() {
            return Stream.of(
                    Arguments.of("The Unbearable ", "The Unbearable Lightness of Java", "US-ASCII", 15),
                    Arguments.of("AbC", "AbC", "ISO-8859-1", 15)
            );
        }


        @ParameterizedTest
        @MethodSource("testdata_testCopy_Reader_Outpustream_Encoding")
        void testCopy_Reader_Outpustream_Encoding(final byte[] expected, final String text, final String encoding) throws Exception {

            try (final StringReader reader = new StringReader(text);
                 final ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

                IOUtil.copy(reader, outputStream, Charset.forName(encoding));

                // asserts
                assertArrayEquals(expected, outputStream.toByteArray());
            }

        }

        Stream<Arguments> testdata_testCopy_Reader_Outpustream_Encoding() {
            return Stream.of(
                    Arguments.of(new byte[] {63,63,63}, "üöä", "US-ASCII"),
                    Arguments.of(new byte[] {-61,-68,-61,-74,-61,-92}, "üöä", "UTF-8"),
                    Arguments.of(new byte[] {106,111,100,100}, "jodd", "US-ASCII")
            );
        }


        @ParameterizedTest
        @MethodSource("testdata_testCopy_Reader_Outpustream_Encoding_CharCount")
        void testCopy_Reader_Outpustream_Encoding_CharCount(final byte[] expected, final String text, final String encoding, final int charCount) throws Exception {

            try (final StringReader reader = new StringReader(text);
                 final ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

                IOUtil.copy(reader, outputStream, Charset.forName(encoding), charCount);

                // asserts
                assertArrayEquals(expected, outputStream.toByteArray());
            }

        }

        Stream<Arguments> testdata_testCopy_Reader_Outpustream_Encoding_CharCount() {
            return Stream.of(
                    Arguments.of(new byte[] {63,63,63}, "üöä", "US-ASCII", 4),
                    Arguments.of(new byte[] {-61,-68,-61,-74}, "üöä", "UTF-8", 2),
                    Arguments.of(new byte[] {106,111,100,100}, "jodd", "US-ASCII", 8)
            );
        }
    }

    @Test
    void testCopy_all() throws IOException {
    	final byte[] bytes = randomBuffer(128 * 1024*1024);

    	final InputStream inputStream = new ByteArrayInputStream(bytes);
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		IOUtil.copy(inputStream, outputStream);
		final byte[] outBytes = outputStream.toByteArray();

		assertArrayEquals(bytes, outBytes);
    }

    @Test
    void testCopy_withSize() throws IOException {
    	final byte[] bytes = randomBuffer(64 * 1024*1024);

    	final InputStream inputStream = new ByteArrayInputStream(bytes);
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		IOUtil.copy(inputStream, outputStream, bytes.length);
		final byte[] outBytes = outputStream.toByteArray();

		assertArrayEquals(bytes, outBytes);
    }

    private byte[] randomBuffer(final int size) {
    	final byte[] bytes = new byte[size];
		for (int i = 0; i < size; i++) {
			bytes[i] = (byte) MathUtil.randomInt(Byte.MIN_VALUE, Byte.MAX_VALUE);
		}

		return bytes;
	}

}
