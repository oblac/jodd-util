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

import javax.activation.DataSource;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Optimized byte and character stream utilities.
 */
public class IOUtil {

	/**
	 * Buffer size for various I/O operations.
	 */
	public static int ioBufferSize = 16384;

	private static final int ZERO = 0;
	private static final int NEGATIVE_ONE = -1;
	private static final int ALL = -1;

	// ---------------------------------------------------------------- silent close

	/**
	 * Closes silently the closable object. If it is {@link Flushable}, it
	 * will be flushed first. No exception will be thrown if an I/O error occurs.
	 */
	public static void close(final Closeable closeable) {
		if (closeable != null) {
			if (closeable instanceof Flushable) {
				try {
					((Flushable) closeable).flush();
				} catch (final IOException ignored) {
				}
			}

			try {
				closeable.close();
			} catch (final IOException ignored) {
			}
		}
	}

	// ---------------------------------------------------------------- copy

	/**
	 * Copies bytes from {@link Reader} to {@link Writer} using buffer.
	 * {@link Reader} and {@link Writer} don't have to be wrapped to buffered, since copying is already optimized.
	 *
	 * @param input  {@link Reader} to read.
	 * @param output {@link Writer} to write to.
	 * @return The total number of characters read.
	 * @throws IOException if there is an error reading or writing.
	 */
	public static int copy(final Reader input, final Writer output) throws IOException {
		final int numToRead = bufferSize();
		final char[] buffer = new char[numToRead];

		int totalRead = ZERO;
		int read;

		while ((read = input.read(buffer, ZERO, numToRead)) >= ZERO) {
			output.write(buffer, ZERO, read);
			totalRead = totalRead + read;
		}

		output.flush();
		return totalRead;
	}

	/**
	 * Copies bytes from {@link InputStream} to {@link OutputStream} using buffer.
	 * {@link InputStream} and {@link OutputStream} don't have to be wrapped to buffered,
	 * since copying is already optimized.
	 *
	 * @param input  {@link InputStream} to read.
	 * @param output {@link OutputStream} to write to.
	 * @return The total number of bytes read.
	 * @throws IOException if there is an error reading or writing.
	 */
	public static int copy(final InputStream input, final OutputStream output) throws IOException {
		final int numToRead = bufferSize();
		final byte[] buffer = new byte[numToRead];

		int totalRead = ZERO;
		int read;

		while ((read = input.read(buffer, ZERO, numToRead)) >= ZERO) {
			output.write(buffer, ZERO, read);
			totalRead = totalRead + read;
		}

		output.flush();
		return totalRead;
	}

	/**
	 * Copies specified number of characters from {@link Reader} to {@link Writer} using buffer.
	 * {@link Reader} and {@link Writer} don't have to be wrapped to buffered, since copying is already optimized.
	 *
	 * @param input  {@link Reader} to read.
	 * @param output {@link Writer} to write to.
	 * @param count  The number of characters to read.
	 * @return The total number of characters read.
	 * @throws IOException if there is an error reading or writing.
	 */
	public static int copy(final Reader input, final Writer output, final int count) throws IOException {
		if (count == ALL) {
			return copy(input, output);
		}

		int numToRead = count;
		final char[] buffer = new char[numToRead];

		int totalRead = ZERO;
		int read;

		while (numToRead > ZERO) {
			read = input.read(buffer, ZERO, bufferSize(numToRead));
			if (read == NEGATIVE_ONE) {
				break;
			}
			output.write(buffer, ZERO, read);

			numToRead = numToRead - read;
			totalRead = totalRead + read;
		}

		output.flush();
		return totalRead;
	}

	/**
	 * Copies specified number of bytes from {@link InputStream} to {@link OutputStream} using buffer.
	 * {@link InputStream} and {@link OutputStream} don't have to be wrapped to buffered, since copying is already optimized.
	 *
	 * @param input  {@link InputStream} to read.
	 * @param output {@link OutputStream} to write to.
	 * @param count  The number of bytes to read.
	 * @return The total number of bytes read.
	 * @throws IOException if there is an error reading or writing.
	 */
	public static int copy(final InputStream input, final OutputStream output, final int count) throws IOException {
		if (count == ALL) {
			return copy(input, output);
		}

		int numToRead = count;
		final byte[] buffer = new byte[numToRead];

		int totalRead = ZERO;
		int read;

		while (numToRead > ZERO) {
			read = input.read(buffer, ZERO, bufferSize(numToRead));
			if (read == NEGATIVE_ONE) {
				break;
			}
			output.write(buffer, ZERO, read);

			numToRead = numToRead - read;
			totalRead = totalRead + read;
		}

		output.flush();
		return totalRead;
	}

	// ---------------------------------------------------------------- read bytes

	/**
	 * Reads all available bytes from {@link InputStream} as a byte array.
	 * Uses {@link InputStream#available()} to determine the size of input stream.
	 * This is the fastest method for reading {@link InputStream} to byte array, but
	 * depends on {@link InputStream} implementation of {@link InputStream#available()}.
	 *
	 * @param input {@link InputStream} to read.
	 * @return byte[]
	 * @throws IOException if total read is less than {@link InputStream#available()};
	 */
	public static byte[] readAvailableBytes(final InputStream input) throws IOException {
		final int numToRead = input.available();
		final byte[] buffer = new byte[numToRead];

		int totalRead = ZERO;
		int read;

		while ((totalRead < numToRead) && (read = input.read(buffer, totalRead, numToRead - totalRead)) >= ZERO) {
			totalRead = totalRead + read;
		}

		if (totalRead < numToRead) {
			throw new IOException("Failed to completely read InputStream");
		}

		return buffer;
	}

	// ---------------------------------------------------------------- copy to OutputStream

	/**
	 * @see #copy(Reader, OutputStream, Charset)
	 */
	public static <T extends OutputStream> T copy(final Reader input, final T output) throws IOException {
		return copy(input, output, encoding());
	}

	/**
	 * @see #copy(Reader, OutputStream, Charset, int)
	 */
	public static <T extends OutputStream> T copy(final Reader input, final T output, final int count) throws IOException {
		return copy(input, output, encoding(), count);
	}

	/**
	 * @see #copy(Reader, OutputStream, Charset, int)
	 */
	public static <T extends OutputStream> T copy(final Reader input, final T output, final Charset encoding) throws IOException {
		return copy(input, output, encoding, ALL);
	}

	/**
	 * Copies {@link Reader} to {@link OutputStream} using buffer and specified encoding.
	 *
	 * @see #copy(Reader, Writer, int)
	 */
	public static <T extends OutputStream> T copy(final Reader input, final T output, final Charset encoding, final int count) throws IOException {
		try (final Writer out = outputStreamWriterOf(output, encoding)) {
			copy(input, out, count);
			return output;
		}
	}

	/**
	 * Copies data from {@link DataSource} to a new {@link ByteArrayOutputStream} and returns this.
	 *
	 * @param input {@link DataSource} to copy from.
	 * @return new {@link ByteArrayOutputStream} with data from input.
	 * @see #copyToOutputStream(InputStream)
	 */
	public static ByteArrayOutputStream copyToOutputStream(final DataSource input) throws IOException {
		return copyToOutputStream(input.getInputStream());
	}

	/**
	 * @see #copyToOutputStream(InputStream, int)
	 */
	public static ByteArrayOutputStream copyToOutputStream(final InputStream input) throws IOException {
		return copyToOutputStream(input, ALL);
	}

	/**
	 * Copies {@link InputStream} to a new {@link ByteArrayOutputStream} using buffer and specified encoding.
	 *
	 * @see #copy(InputStream, OutputStream, int)
	 */
	public static ByteArrayOutputStream copyToOutputStream(final InputStream input, final int count) throws IOException {
		try (final ByteArrayOutputStream output = createFastByteArrayOutputStream()) {
			copy(input, output, count);
			return output;
		}
	}

	/**
	 * @see #copyToOutputStream(Reader, Charset)
	 */
	public static ByteArrayOutputStream copyToOutputStream(final Reader input) throws IOException {
		return copyToOutputStream(input, encoding());
	}

	/**
	 * @see #copyToOutputStream(Reader, Charset, int)
	 */
	public static ByteArrayOutputStream copyToOutputStream(final Reader input, final Charset encoding) throws IOException {
		return copyToOutputStream(input, encoding, ALL);
	}

	/**
	 * @see #copyToOutputStream(Reader, Charset, int)
	 */
	public static ByteArrayOutputStream copyToOutputStream(final Reader input, final int count) throws IOException {
		return copyToOutputStream(input, encoding(), count);
	}

	/**
	 * Copies {@link Reader} to a new {@link ByteArrayOutputStream} using buffer and specified encoding.
	 *
	 * @see #copy(Reader, OutputStream, Charset, int)
	 */
	public static ByteArrayOutputStream copyToOutputStream(final Reader input, final Charset encoding, final int count) throws IOException {
		try (final ByteArrayOutputStream output = createFastByteArrayOutputStream()) {
			copy(input, output, encoding, count);
			return output;
		}
	}

	// ---------------------------------------------------------------- copy to Writer

	/**
	 * @see #copy(InputStream, Writer, Charset)
	 */
	public static <T extends Writer> T copy(final InputStream input, final T output) throws IOException {
		return copy(input, output, encoding());
	}

	/**
	 * @see #copy(InputStream, Writer, Charset, int)
	 */
	public static <T extends Writer> T copy(final InputStream input, final T output, final int count) throws IOException {
		return copy(input, output, encoding(), count);
	}

	/**
	 * @see #copy(InputStream, Writer, Charset, int)
	 */
	public static <T extends Writer> T copy(final InputStream input, final T output, final Charset encoding) throws IOException {
		return copy(input, output, encoding, ALL);
	}

	/**
	 * Copies {@link InputStream} to {@link Writer} using buffer and specified encoding.
	 *
	 * @see #copy(Reader, Writer, int)
	 */
	public static <T extends Writer> T copy(final InputStream input, final T output, final Charset encoding, final int count) throws IOException {
		copy(inputStreamReadeOf(input, encoding), output, count);
		return output;
	}

	/**
	 * @see #copy(InputStream, Charset)
	 */
	public static CharArrayWriter copy(final InputStream input) throws IOException {
		return copy(input, encoding());
	}

	/**
	 * @see #copy(InputStream, Charset, int)
	 */
	public static CharArrayWriter copy(final InputStream input, final int count) throws IOException {
		return copy(input, encoding(), count);
	}

	/**
	 * @see #copy(InputStream, Charset, int)
	 */
	public static CharArrayWriter copy(final InputStream input, final Charset encoding) throws IOException {
		return copy(input, encoding, ALL);
	}

	/**
	 * Copies {@link InputStream} to a new {@link CharArrayWriter} using buffer and specified encoding.
	 *
	 * @see #copy(InputStream, Writer, Charset, int)
	 */
	public static CharArrayWriter copy(final InputStream input, final Charset encoding, final int count) throws IOException {
		try (final CharArrayWriter output = createFastCharArrayWriter()) {
			copy(input, output, encoding, count);
			return output;
		}
	}

	/**
	 * @see #copy(Reader, int)
	 */
	public static CharArrayWriter copy(final Reader input) throws IOException {
		return copy(input, ALL);
	}

	/**
	 * Copies {@link Reader} to a new {@link CharArrayWriter} using buffer and specified encoding.
	 *
	 * @see #copy(Reader, Writer, int)
	 */
	public static CharArrayWriter copy(final Reader input, final int count) throws IOException {
		try (final CharArrayWriter output = createFastCharArrayWriter()) {
			copy(input, output, count);
			return output;
		}
	}

	/**
	 * Copies data from {@link DataSource} to a new {@link CharArrayWriter} and returns this.
	 *
	 * @param input {@link DataSource} to copy from.
	 * @return new {@link CharArrayWriter} with data from input.
	 * @see #copy(InputStream)
	 */
	public static CharArrayWriter copy(final DataSource input) throws IOException {
		return copy(input.getInputStream());
	}

	// ---------------------------------------------------------------- read bytes

	/**
	 * @see #readBytes(InputStream, int)
	 */
	public static byte[] readBytes(final InputStream input) throws IOException {
		return readBytes(input, ALL);
	}

	/**
	 * @see #copyToOutputStream(InputStream, int)
	 */
	public static byte[] readBytes(final InputStream input, final int count) throws IOException {
		return copyToOutputStream(input, count).toByteArray();
	}

	/**
	 * @see #readBytes(Reader, Charset)
	 */
	public static byte[] readBytes(final Reader input) throws IOException {
		return readBytes(input, encoding());
	}

	/**
	 * @see #readBytes(Reader, Charset, int)
	 */
	public static byte[] readBytes(final Reader input, final int count) throws IOException {
		return readBytes(input, encoding(), count);
	}

	/**
	 * @see #readBytes(Reader, Charset, int)
	 */
	public static byte[] readBytes(final Reader input, final Charset encoding) throws IOException {
		return readBytes(input, encoding, ALL);
	}

	/**
	 * @see #copyToOutputStream(Reader, Charset, int)
	 */
	public static byte[] readBytes(final Reader input, final Charset encoding, final int count) throws IOException {
		return copyToOutputStream(input, encoding, count).toByteArray();
	}

	// ---------------------------------------------------------------- read chars

	/**
	 * @see #readChars(Reader, int)
	 */
	public static char[] readChars(final Reader input) throws IOException {
		return readChars(input, ALL);
	}

	/**
	 * @see #copy(Reader, int)
	 */
	public static char[] readChars(final Reader input, final int count) throws IOException {
		return copy(input, count).toCharArray();
	}

	/**
	 * @see #readChars(InputStream, int)
	 */
	public static char[] readChars(final InputStream input) throws IOException {
		return readChars(input, ALL);
	}

	/**
	 * @see #readChars(InputStream, Charset, int)
	 */
	public static char[] readChars(final InputStream input, final Charset encoding) throws IOException {
		return readChars(input, encoding, ALL);
	}

	/**
	 * @see #readChars(InputStream, Charset, int)
	 */
	public static char[] readChars(final InputStream input, final int count) throws IOException {
		return readChars(input, encoding(), count);
	}

	/**
	 * @see #copy(InputStream, Charset, int)
	 */
	public static char[] readChars(final InputStream input, final Charset encoding, final int count) throws IOException {
		return copy(input, encoding, count).toCharArray();
	}

	// ---------------------------------------------------------------- compare content

	/**
	 * Compares the content of two byte streams ({@link InputStream}s).
	 *
	 * @return {@code true} if the content of the first {@link InputStream} is equal
	 * to the content of the second {@link InputStream}.
	 */
	public static boolean compare(InputStream input1, InputStream input2) throws IOException {
		if (!(input1 instanceof BufferedInputStream)) {
			input1 = new BufferedInputStream(input1);
		}
		if (!(input2 instanceof BufferedInputStream)) {
			input2 = new BufferedInputStream(input2);
		}
		int ch = input1.read();
		while (ch != NEGATIVE_ONE) {
			final int ch2 = input2.read();
			if (ch != ch2) {
				return false;
			}
			ch = input1.read();
		}
		final int ch2 = input2.read();
		return (ch2 == NEGATIVE_ONE);
	}

	/**
	 * Compares the content of two character streams ({@link Reader}s).
	 *
	 * @return {@code true} if the content of the first {@link Reader} is equal
	 * to the content of the second {@link Reader}.
	 */
	public static boolean compare(Reader input1, Reader input2) throws IOException {
		if (!(input1 instanceof BufferedReader)) {
			input1 = new BufferedReader(input1);
		}
		if (!(input2 instanceof BufferedReader)) {
			input2 = new BufferedReader(input2);
		}

		int ch = input1.read();
		while (ch != NEGATIVE_ONE) {
			final int ch2 = input2.read();
			if (ch != ch2) {
				return false;
			}
			ch = input1.read();
		}
		final int ch2 = input2.read();
		return (ch2 == NEGATIVE_ONE);
	}

	// ---------------------------------------------------------------- defaults

	/**
	 * Returns default IO buffer size.
	 *
	 * @return default IO buffer size.
	 */
	private static int bufferSize() {
		return ioBufferSize;
	}

	/**
	 * Returns either count or default IO buffer size (whichever is smaller).
	 *
	 * @param count Number of characters or bytes to retrieve.
	 * @return buffer size (either count or default IO buffer size, whichever is smaller).
	 */
	private static int bufferSize(final int count) {
		final int ioBufferSize = IOUtil.ioBufferSize;
		return Math.min(count, ioBufferSize);
	}

	/**
	 * Returns default encoding.
	 */
	private static Charset encoding() {
		return StandardCharsets.UTF_8;
	}

	// ---------------------------------------------------------------- wrappers

	/**
	 * Returns new {@link CharArrayWriter} using default IO buffer size.
	 *
	 * @return new {@link CharArrayWriter} using default IO buffer size.
	 */
	private static CharArrayWriter createFastCharArrayWriter() {
		return new CharArrayWriter(bufferSize());
	}

	private static ByteArrayOutputStream createFastByteArrayOutputStream() {
		return new ByteArrayOutputStream(bufferSize());
	}

	/**
	 * @see #inputStreamReadeOf(InputStream, Charset)
	 */
	public static InputStreamReader inputStreamReadeOf(final InputStream input) {
		return inputStreamReadeOf(input, encoding());
	}

	/**
	 * Returns new {@link InputStreamReader} using specified {@link InputStream} and encoding.
	 *
	 * @param input    {@link InputStream}
	 * @param encoding Encoding as {@link String} to use for {@link InputStreamReader}.
	 * @return new {@link InputStreamReader}
	 */
	public static InputStreamReader inputStreamReadeOf(final InputStream input, final Charset encoding) {
		return new InputStreamReader(input, encoding);
	}

	/**
	 * @see #outputStreamWriterOf(OutputStream, Charset)
	 */
	public static OutputStreamWriter outputStreamWriterOf(final OutputStream output) {
		return outputStreamWriterOf(output, encoding());
	}

	/**
	 * Returns new {@link OutputStreamWriter} using specified {@link OutputStream} and encoding.
	 *
	 * @param output   {@link OutputStream}
	 * @param encoding Encoding as {@link String} to use for {@link OutputStreamWriter}.
	 * @return new {@link OutputStreamWriter}
	 */
	public static OutputStreamWriter outputStreamWriterOf(final OutputStream output, final Charset encoding) {
		return new OutputStreamWriter(output, encoding);
	}
}
