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

import java.lang.reflect.Array;

import static jodd.util.StringPool.NULL;

/**
 * Array utilities.
 */
public class ArraysUtil {


	// ---------------------------------------------------------------- wrap

	/**
	 * Wraps elements into an array.
	 */
	public static <T> T[] array(final T... elements) {
		return elements;
	}

	/**
	 * Wraps elements into an array.
	 */
	public static byte[] bytes(final byte... elements) {
		return elements;
	}

	/**
	 * Wraps elements into an array.
	 */
	public static char[] chars(final char... elements) {
		return elements;
	}

	/**
	 * Wraps elements into an array.
	 */
	public static short[] shorts(final short... elements) {
		return elements;
	}

	/**
	 * Wraps elements into an array.
	 */
	public static int[] ints(final int... elements) {
		return elements;
	}

	/**
	 * Wraps elements into an array.
	 */
	public static long[] longs(final long... elements) {
		return elements;
	}

	/**
	 * Wraps elements into an array.
	 */
	public static float[] floats(final float... elements) {
		return elements;
	}

	/**
	 * Wraps elements into an array.
	 */
	public static double[] doubles(final double... elements) {
		return elements;
	}

	/**
	 * Wraps elements into an array.
	 */
	public static boolean[] booleans(final boolean... elements) {
		return elements;
	}


	// ---------------------------------------------------------------- join

	/**
	 * Joins arrays. Component type is resolved from the array argument.
	 */
	@SuppressWarnings({"unchecked"})
	public static <T> T[] join(final T[]... arrays) {
		final Class<T> componentType = (Class<T>) arrays.getClass().getComponentType().getComponentType();
		return join(componentType, arrays);
	}

	/**
	 * Joins arrays using provided component type.
	 */
	@SuppressWarnings({"unchecked"})
	public static <T> T[] join(final Class<T> componentType, final T[][] arrays) {
		if (arrays.length == 1) {
			return arrays[0];
		}
		int length = 0;
		for (final T[] array : arrays) {
			length += array.length;
		}
		final T[] result = (T[]) Array.newInstance(componentType, length);

		length = 0;
		for (final T[] array : arrays) {
			System.arraycopy(array, 0, result, length, array.length);
			length += array.length;
		}
		return result;
	}


	/**
	 * Join <code>String</code> arrays.
	 */
	public static String[] join(final String[]... arrays) {
		if (arrays.length == 0) {
			return new String[0];
		}
		if (arrays.length == 1) {
			return arrays[0];
		}
		int length = 0;
		for (final String[] array : arrays) {
			length += array.length;
		}
		final String[] result = new String[length];
		length = 0;
		for (final String[] array : arrays) {
			System.arraycopy(array, 0, result, length, array.length);
			length += array.length;
		}
		return result;
	}

	/**
	 * Join <code>byte</code> arrays.
	 */
	public static byte[] join(final byte[]... arrays) {
		if (arrays.length == 0) {
			return new byte[0];
		}
		if (arrays.length == 1) {
			return arrays[0];
		}
		int length = 0;
		for (final byte[] array : arrays) {
			length += array.length;
		}
		final byte[] result = new byte[length];
		length = 0;
		for (final byte[] array : arrays) {
			System.arraycopy(array, 0, result, length, array.length);
			length += array.length;
		}
		return result;
	}

	/**
	 * Join <code>char</code> arrays.
	 */
	public static char[] join(final char[]... arrays) {
		if (arrays.length == 0) {
			return new char[0];
		}
		if (arrays.length == 1) {
			return arrays[0];
		}
		int length = 0;
		for (final char[] array : arrays) {
			length += array.length;
		}
		final char[] result = new char[length];
		length = 0;
		for (final char[] array : arrays) {
			System.arraycopy(array, 0, result, length, array.length);
			length += array.length;
		}
		return result;
	}

	/**
	 * Join <code>short</code> arrays.
	 */
	public static short[] join(final short[]... arrays) {
		if (arrays.length == 0) {
			return new short[0];
		}
		if (arrays.length == 1) {
			return arrays[0];
		}
		int length = 0;
		for (final short[] array : arrays) {
			length += array.length;
		}
		final short[] result = new short[length];
		length = 0;
		for (final short[] array : arrays) {
			System.arraycopy(array, 0, result, length, array.length);
			length += array.length;
		}
		return result;
	}

	/**
	 * Join <code>int</code> arrays.
	 */
	public static int[] join(final int[]... arrays) {
		if (arrays.length == 0) {
			return new int[0];
		}
		if (arrays.length == 1) {
			return arrays[0];
		}
		int length = 0;
		for (final int[] array : arrays) {
			length += array.length;
		}
		final int[] result = new int[length];
		length = 0;
		for (final int[] array : arrays) {
			System.arraycopy(array, 0, result, length, array.length);
			length += array.length;
		}
		return result;
	}

	/**
	 * Join <code>long</code> arrays.
	 */
	public static long[] join(final long[]... arrays) {
		if (arrays.length == 0) {
			return new long[0];
		}
		if (arrays.length == 1) {
			return arrays[0];
		}
		int length = 0;
		for (final long[] array : arrays) {
			length += array.length;
		}
		final long[] result = new long[length];
		length = 0;
		for (final long[] array : arrays) {
			System.arraycopy(array, 0, result, length, array.length);
			length += array.length;
		}
		return result;
	}

	/**
	 * Join <code>float</code> arrays.
	 */
	public static float[] join(final float[]... arrays) {
		if (arrays.length == 0) {
			return new float[0];
		}
		if (arrays.length == 1) {
			return arrays[0];
		}
		int length = 0;
		for (final float[] array : arrays) {
			length += array.length;
		}
		final float[] result = new float[length];
		length = 0;
		for (final float[] array : arrays) {
			System.arraycopy(array, 0, result, length, array.length);
			length += array.length;
		}
		return result;
	}

	/**
	 * Join <code>double</code> arrays.
	 */
	public static double[] join(final double[]... arrays) {
		if (arrays.length == 0) {
			return new double[0];
		}
		if (arrays.length == 1) {
			return arrays[0];
		}
		int length = 0;
		for (final double[] array : arrays) {
			length += array.length;
		}
		final double[] result = new double[length];
		length = 0;
		for (final double[] array : arrays) {
			System.arraycopy(array, 0, result, length, array.length);
			length += array.length;
		}
		return result;
	}

	/**
	 * Join <code>boolean</code> arrays.
	 */
	public static boolean[] join(final boolean[]... arrays) {
		if (arrays.length == 0) {
			return new boolean[0];
		}
		if (arrays.length == 1) {
			return arrays[0];
		}
		int length = 0;
		for (final boolean[] array : arrays) {
			length += array.length;
		}
		final boolean[] result = new boolean[length];
		length = 0;
		for (final boolean[] array : arrays) {
			System.arraycopy(array, 0, result, length, array.length);
			length += array.length;
		}
		return result;
	}


	// ---------------------------------------------------------------- resize

	/**
	 * Resizes an array.
	 */
	public static <T> T[] resize(final T[] buffer, final int newSize) {
		final Class<T> componentType = (Class<T>) buffer.getClass().getComponentType();
		final T[] temp = (T[]) Array.newInstance(componentType, newSize);
		System.arraycopy(buffer, 0, temp, 0, buffer.length >= newSize ? newSize : buffer.length);
		return temp;
	}


	/**
	 * Resizes a <code>String</code> array.
	 */
	public static String[] resize(final String[] buffer, final int newSize) {
		final String[] temp = new String[newSize];
		System.arraycopy(buffer, 0, temp, 0, buffer.length >= newSize ? newSize : buffer.length);
		return temp;
	}

	/**
	 * Resizes a <code>byte</code> array.
	 */
	public static byte[] resize(final byte[] buffer, final int newSize) {
		final byte[] temp = new byte[newSize];
		System.arraycopy(buffer, 0, temp, 0, buffer.length >= newSize ? newSize : buffer.length);
		return temp;
	}

	/**
	 * Resizes a <code>char</code> array.
	 */
	public static char[] resize(final char[] buffer, final int newSize) {
		final char[] temp = new char[newSize];
		System.arraycopy(buffer, 0, temp, 0, buffer.length >= newSize ? newSize : buffer.length);
		return temp;
	}

	/**
	 * Resizes a <code>short</code> array.
	 */
	public static short[] resize(final short[] buffer, final int newSize) {
		final short[] temp = new short[newSize];
		System.arraycopy(buffer, 0, temp, 0, buffer.length >= newSize ? newSize : buffer.length);
		return temp;
	}

	/**
	 * Resizes a <code>int</code> array.
	 */
	public static int[] resize(final int[] buffer, final int newSize) {
		final int[] temp = new int[newSize];
		System.arraycopy(buffer, 0, temp, 0, buffer.length >= newSize ? newSize : buffer.length);
		return temp;
	}

	/**
	 * Resizes a <code>long</code> array.
	 */
	public static long[] resize(final long[] buffer, final int newSize) {
		final long[] temp = new long[newSize];
		System.arraycopy(buffer, 0, temp, 0, buffer.length >= newSize ? newSize : buffer.length);
		return temp;
	}

	/**
	 * Resizes a <code>float</code> array.
	 */
	public static float[] resize(final float[] buffer, final int newSize) {
		final float[] temp = new float[newSize];
		System.arraycopy(buffer, 0, temp, 0, buffer.length >= newSize ? newSize : buffer.length);
		return temp;
	}

	/**
	 * Resizes a <code>double</code> array.
	 */
	public static double[] resize(final double[] buffer, final int newSize) {
		final double[] temp = new double[newSize];
		System.arraycopy(buffer, 0, temp, 0, buffer.length >= newSize ? newSize : buffer.length);
		return temp;
	}

	/**
	 * Resizes a <code>boolean</code> array.
	 */
	public static boolean[] resize(final boolean[] buffer, final int newSize) {
		final boolean[] temp = new boolean[newSize];
		System.arraycopy(buffer, 0, temp, 0, buffer.length >= newSize ? newSize : buffer.length);
		return temp;
	}


	// ---------------------------------------------------------------- append

	/**
	 * Appends an element to array.
	 */
	public static <T> T[] append(final T[] buffer, final T newElement) {
		final T[] t = resize(buffer, buffer.length + 1);
		t[buffer.length] = newElement;
		return t;
	}

	/**
	 * Appends an element to <code>String</code> array.
	 */
	public static String[] append(final String[] buffer, final String newElement) {
		final String[] t = resize(buffer, buffer.length + 1);
		t[buffer.length] = newElement;
		return t;
	}

	/**
	 * Appends an element to <code>byte</code> array.
	 */
	public static byte[] append(final byte[] buffer, final byte newElement) {
		final byte[] t = resize(buffer, buffer.length + 1);
		t[buffer.length] = newElement;
		return t;
	}

	/**
	 * Appends an element to <code>char</code> array.
	 */
	public static char[] append(final char[] buffer, final char newElement) {
		final char[] t = resize(buffer, buffer.length + 1);
		t[buffer.length] = newElement;
		return t;
	}

	/**
	 * Appends an element to <code>short</code> array.
	 */
	public static short[] append(final short[] buffer, final short newElement) {
		final short[] t = resize(buffer, buffer.length + 1);
		t[buffer.length] = newElement;
		return t;
	}

	/**
	 * Appends an element to <code>int</code> array.
	 */
	public static int[] append(final int[] buffer, final int newElement) {
		final int[] t = resize(buffer, buffer.length + 1);
		t[buffer.length] = newElement;
		return t;
	}

	/**
	 * Appends an element to <code>long</code> array.
	 */
	public static long[] append(final long[] buffer, final long newElement) {
		final long[] t = resize(buffer, buffer.length + 1);
		t[buffer.length] = newElement;
		return t;
	}

	/**
	 * Appends an element to <code>float</code> array.
	 */
	public static float[] append(final float[] buffer, final float newElement) {
		final float[] t = resize(buffer, buffer.length + 1);
		t[buffer.length] = newElement;
		return t;
	}

	/**
	 * Appends an element to <code>double</code> array.
	 */
	public static double[] append(final double[] buffer, final double newElement) {
		final double[] t = resize(buffer, buffer.length + 1);
		t[buffer.length] = newElement;
		return t;
	}

	/**
	 * Appends an element to <code>boolean</code> array.
	 */
	public static boolean[] append(final boolean[] buffer, final boolean newElement) {
		final boolean[] t = resize(buffer, buffer.length + 1);
		t[buffer.length] = newElement;
		return t;
	}


	// ---------------------------------------------------------------- remove

	/**
	 * Removes sub-array.
	 */
	public static <T> T[] remove(final T[] buffer, final int offset, final int length) {
		final Class<T> componentType = (Class<T>) buffer.getClass().getComponentType();
		return remove(buffer, offset, length, componentType);
	}

	/**
	 * Removes sub-array.
	 */
	@SuppressWarnings({"unchecked"})
	public static <T> T[] remove(final T[] buffer, final int offset, final int length, final Class<T> componentType) {
		final int len2 = buffer.length - length;
		final T[] temp = (T[]) Array.newInstance(componentType, len2);
		System.arraycopy(buffer, 0, temp, 0, offset);
		System.arraycopy(buffer, offset + length, temp, offset, len2 - offset);
		return temp;
	}

	/**
	 * Removes sub-array from <code>String</code> array.
	 */
	public static String[] remove(final String[] buffer, final int offset, final int length) {
		final int len2 = buffer.length - length;
		final String[] temp = new String[len2];
		System.arraycopy(buffer, 0, temp, 0, offset);
		System.arraycopy(buffer, offset + length, temp, offset, len2 - offset);
		return temp;
	}

	/**
	 * Removes sub-array from <code>byte</code> array.
	 */
	public static byte[] remove(final byte[] buffer, final int offset, final int length) {
		final int len2 = buffer.length - length;
		final byte[] temp = new byte[len2];
		System.arraycopy(buffer, 0, temp, 0, offset);
		System.arraycopy(buffer, offset + length, temp, offset, len2 - offset);
		return temp;
	}

	/**
	 * Removes sub-array from <code>char</code> array.
	 */
	public static char[] remove(final char[] buffer, final int offset, final int length) {
		final int len2 = buffer.length - length;
		final char[] temp = new char[len2];
		System.arraycopy(buffer, 0, temp, 0, offset);
		System.arraycopy(buffer, offset + length, temp, offset, len2 - offset);
		return temp;
	}

	/**
	 * Removes sub-array from <code>short</code> array.
	 */
	public static short[] remove(final short[] buffer, final int offset, final int length) {
		final int len2 = buffer.length - length;
		final short[] temp = new short[len2];
		System.arraycopy(buffer, 0, temp, 0, offset);
		System.arraycopy(buffer, offset + length, temp, offset, len2 - offset);
		return temp;
	}

	/**
	 * Removes sub-array from <code>int</code> array.
	 */
	public static int[] remove(final int[] buffer, final int offset, final int length) {
		final int len2 = buffer.length - length;
		final int[] temp = new int[len2];
		System.arraycopy(buffer, 0, temp, 0, offset);
		System.arraycopy(buffer, offset + length, temp, offset, len2 - offset);
		return temp;
	}

	/**
	 * Removes sub-array from <code>long</code> array.
	 */
	public static long[] remove(final long[] buffer, final int offset, final int length) {
		final int len2 = buffer.length - length;
		final long[] temp = new long[len2];
		System.arraycopy(buffer, 0, temp, 0, offset);
		System.arraycopy(buffer, offset + length, temp, offset, len2 - offset);
		return temp;
	}

	/**
	 * Removes sub-array from <code>float</code> array.
	 */
	public static float[] remove(final float[] buffer, final int offset, final int length) {
		final int len2 = buffer.length - length;
		final float[] temp = new float[len2];
		System.arraycopy(buffer, 0, temp, 0, offset);
		System.arraycopy(buffer, offset + length, temp, offset, len2 - offset);
		return temp;
	}

	/**
	 * Removes sub-array from <code>double</code> array.
	 */
	public static double[] remove(final double[] buffer, final int offset, final int length) {
		final int len2 = buffer.length - length;
		final double[] temp = new double[len2];
		System.arraycopy(buffer, 0, temp, 0, offset);
		System.arraycopy(buffer, offset + length, temp, offset, len2 - offset);
		return temp;
	}

	/**
	 * Removes sub-array from <code>boolean</code> array.
	 */
	public static boolean[] remove(final boolean[] buffer, final int offset, final int length) {
		final int len2 = buffer.length - length;
		final boolean[] temp = new boolean[len2];
		System.arraycopy(buffer, 0, temp, 0, offset);
		System.arraycopy(buffer, offset + length, temp, offset, len2 - offset);
		return temp;
	}


	// ---------------------------------------------------------------- subarray

	/**
	 * Returns subarray.
	 */
	public static <T> T[] subarray(final T[] buffer, final int offset, final int length) {
		final Class<T> componentType = (Class<T>) buffer.getClass().getComponentType();
		return subarray(buffer, offset, length, componentType);
	}

	/**
	 * Returns subarray.
	 */
	@SuppressWarnings({"unchecked"})
	public static <T> T[] subarray(final T[] buffer, final int offset, final int length, final Class<T> componentType) {
		final T[] temp = (T[]) Array.newInstance(componentType, length);
		System.arraycopy(buffer, offset, temp, 0, length);
		return temp;
	}

	/**
	 * Returns subarray.
	 */
	public static String[] subarray(final String[] buffer, final int offset, final int length) {
		final String[] temp = new String[length];
		System.arraycopy(buffer, offset, temp, 0, length);
		return temp;
	}

	/**
	 * Returns subarray.
	 */
	public static byte[] subarray(final byte[] buffer, final int offset, final int length) {
		final byte[] temp = new byte[length];
		System.arraycopy(buffer, offset, temp, 0, length);
		return temp;
	}

	/**
	 * Returns subarray.
	 */
	public static char[] subarray(final char[] buffer, final int offset, final int length) {
		final char[] temp = new char[length];
		System.arraycopy(buffer, offset, temp, 0, length);
		return temp;
	}

	/**
	 * Returns subarray.
	 */
	public static short[] subarray(final short[] buffer, final int offset, final int length) {
		final short[] temp = new short[length];
		System.arraycopy(buffer, offset, temp, 0, length);
		return temp;
	}

	/**
	 * Returns subarray.
	 */
	public static int[] subarray(final int[] buffer, final int offset, final int length) {
		final int[] temp = new int[length];
		System.arraycopy(buffer, offset, temp, 0, length);
		return temp;
	}

	/**
	 * Returns subarray.
	 */
	public static long[] subarray(final long[] buffer, final int offset, final int length) {
		final long[] temp = new long[length];
		System.arraycopy(buffer, offset, temp, 0, length);
		return temp;
	}

	/**
	 * Returns subarray.
	 */
	public static float[] subarray(final float[] buffer, final int offset, final int length) {
		final float[] temp = new float[length];
		System.arraycopy(buffer, offset, temp, 0, length);
		return temp;
	}

	/**
	 * Returns subarray.
	 */
	public static double[] subarray(final double[] buffer, final int offset, final int length) {
		final double[] temp = new double[length];
		System.arraycopy(buffer, offset, temp, 0, length);
		return temp;
	}

	/**
	 * Returns subarray.
	 */
	public static boolean[] subarray(final boolean[] buffer, final int offset, final int length) {
		final boolean[] temp = new boolean[length];
		System.arraycopy(buffer, offset, temp, 0, length);
		return temp;
	}


	// ---------------------------------------------------------------- insert

	/**
	 * Inserts one array into another array.
	 */
	public static <T> T[] insert(final T[] dest, final T[] src, final int offset) {
		final Class<T> componentType = (Class<T>) dest.getClass().getComponentType();
		return insert(dest, src, offset, componentType);
	}
	/**
	 * Inserts one element into an array.
	 */
	public static <T> T[] insert(final T[] dest, final T src, final int offset) {
		final Class<T> componentType = (Class<T>) dest.getClass().getComponentType();
		return insert(dest, src, offset, componentType);
	}

	/**
	 * Inserts one array into another array.
	 */
	@SuppressWarnings({"unchecked"})
	public static <T> T[] insert(final T[] dest, final T[] src, final int offset, final Class componentType) {
		final T[] temp = (T[]) Array.newInstance(componentType, dest.length + src.length);
		System.arraycopy(dest, 0, temp, 0, offset);
		System.arraycopy(src, 0, temp, offset, src.length);
		System.arraycopy(dest, offset, temp, src.length + offset, dest.length - offset);
		return temp;
	}
	/**
	 * Inserts one element into another array.
	 */
	@SuppressWarnings({"unchecked"})
	public static <T> T[] insert(final T[] dest, final T src, final int offset, final Class componentType) {
		final T[] temp = (T[]) Array.newInstance(componentType, dest.length + 1);
		System.arraycopy(dest, 0, temp, 0, offset);
		temp[offset] = src;
		System.arraycopy(dest, offset, temp, offset + 1, dest.length - offset);
		return temp;
	}

	/**
	 * Inserts one array into another <code>String</code> array.
	 */
	public static String[] insert(final String[] dest, final String[] src, final int offset) {
		final String[] temp = new String[dest.length + src.length];
		System.arraycopy(dest, 0, temp, 0, offset);
		System.arraycopy(src, 0, temp, offset, src.length);
		System.arraycopy(dest, offset, temp, src.length + offset, dest.length - offset);
		return temp;
	}

	/**
	 * Inserts one element into another <code>String</code> array.
	 */
	public static String[] insert(final String[] dest, final String src, final int offset) {
		final String[] temp = new String[dest.length + 1];
		System.arraycopy(dest, 0, temp, 0, offset);
		temp[offset] = src;
		System.arraycopy(dest, offset, temp, offset + 1, dest.length - offset);
		return temp;
	}

	/**
	 * Inserts one array into another <code>byte</code> array.
	 */
	public static byte[] insert(final byte[] dest, final byte[] src, final int offset) {
		final byte[] temp = new byte[dest.length + src.length];
		System.arraycopy(dest, 0, temp, 0, offset);
		System.arraycopy(src, 0, temp, offset, src.length);
		System.arraycopy(dest, offset, temp, src.length + offset, dest.length - offset);
		return temp;
	}

	/**
	 * Inserts one element into another <code>byte</code> array.
	 */
	public static byte[] insert(final byte[] dest, final byte src, final int offset) {
		final byte[] temp = new byte[dest.length + 1];
		System.arraycopy(dest, 0, temp, 0, offset);
		temp[offset] = src;
		System.arraycopy(dest, offset, temp, offset + 1, dest.length - offset);
		return temp;
	}

	/**
	 * Inserts one array into another <code>char</code> array.
	 */
	public static char[] insert(final char[] dest, final char[] src, final int offset) {
		final char[] temp = new char[dest.length + src.length];
		System.arraycopy(dest, 0, temp, 0, offset);
		System.arraycopy(src, 0, temp, offset, src.length);
		System.arraycopy(dest, offset, temp, src.length + offset, dest.length - offset);
		return temp;
	}

	/**
	 * Inserts one element into another <code>char</code> array.
	 */
	public static char[] insert(final char[] dest, final char src, final int offset) {
		final char[] temp = new char[dest.length + 1];
		System.arraycopy(dest, 0, temp, 0, offset);
		temp[offset] = src;
		System.arraycopy(dest, offset, temp, offset + 1, dest.length - offset);
		return temp;
	}

	/**
	 * Inserts one array into another <code>short</code> array.
	 */
	public static short[] insert(final short[] dest, final short[] src, final int offset) {
		final short[] temp = new short[dest.length + src.length];
		System.arraycopy(dest, 0, temp, 0, offset);
		System.arraycopy(src, 0, temp, offset, src.length);
		System.arraycopy(dest, offset, temp, src.length + offset, dest.length - offset);
		return temp;
	}

	/**
	 * Inserts one element into another <code>short</code> array.
	 */
	public static short[] insert(final short[] dest, final short src, final int offset) {
		final short[] temp = new short[dest.length + 1];
		System.arraycopy(dest, 0, temp, 0, offset);
		temp[offset] = src;
		System.arraycopy(dest, offset, temp, offset + 1, dest.length - offset);
		return temp;
	}

	/**
	 * Inserts one array into another <code>int</code> array.
	 */
	public static int[] insert(final int[] dest, final int[] src, final int offset) {
		final int[] temp = new int[dest.length + src.length];
		System.arraycopy(dest, 0, temp, 0, offset);
		System.arraycopy(src, 0, temp, offset, src.length);
		System.arraycopy(dest, offset, temp, src.length + offset, dest.length - offset);
		return temp;
	}

	/**
	 * Inserts one element into another <code>int</code> array.
	 */
	public static int[] insert(final int[] dest, final int src, final int offset) {
		final int[] temp = new int[dest.length + 1];
		System.arraycopy(dest, 0, temp, 0, offset);
		temp[offset] = src;
		System.arraycopy(dest, offset, temp, offset + 1, dest.length - offset);
		return temp;
	}

	/**
	 * Inserts one array into another <code>long</code> array.
	 */
	public static long[] insert(final long[] dest, final long[] src, final int offset) {
		final long[] temp = new long[dest.length + src.length];
		System.arraycopy(dest, 0, temp, 0, offset);
		System.arraycopy(src, 0, temp, offset, src.length);
		System.arraycopy(dest, offset, temp, src.length + offset, dest.length - offset);
		return temp;
	}

	/**
	 * Inserts one element into another <code>long</code> array.
	 */
	public static long[] insert(final long[] dest, final long src, final int offset) {
		final long[] temp = new long[dest.length + 1];
		System.arraycopy(dest, 0, temp, 0, offset);
		temp[offset] = src;
		System.arraycopy(dest, offset, temp, offset + 1, dest.length - offset);
		return temp;
	}

	/**
	 * Inserts one array into another <code>float</code> array.
	 */
	public static float[] insert(final float[] dest, final float[] src, final int offset) {
		final float[] temp = new float[dest.length + src.length];
		System.arraycopy(dest, 0, temp, 0, offset);
		System.arraycopy(src, 0, temp, offset, src.length);
		System.arraycopy(dest, offset, temp, src.length + offset, dest.length - offset);
		return temp;
	}

	/**
	 * Inserts one element into another <code>float</code> array.
	 */
	public static float[] insert(final float[] dest, final float src, final int offset) {
		final float[] temp = new float[dest.length + 1];
		System.arraycopy(dest, 0, temp, 0, offset);
		temp[offset] = src;
		System.arraycopy(dest, offset, temp, offset + 1, dest.length - offset);
		return temp;
	}

	/**
	 * Inserts one array into another <code>double</code> array.
	 */
	public static double[] insert(final double[] dest, final double[] src, final int offset) {
		final double[] temp = new double[dest.length + src.length];
		System.arraycopy(dest, 0, temp, 0, offset);
		System.arraycopy(src, 0, temp, offset, src.length);
		System.arraycopy(dest, offset, temp, src.length + offset, dest.length - offset);
		return temp;
	}

	/**
	 * Inserts one element into another <code>double</code> array.
	 */
	public static double[] insert(final double[] dest, final double src, final int offset) {
		final double[] temp = new double[dest.length + 1];
		System.arraycopy(dest, 0, temp, 0, offset);
		temp[offset] = src;
		System.arraycopy(dest, offset, temp, offset + 1, dest.length - offset);
		return temp;
	}

	/**
	 * Inserts one array into another <code>boolean</code> array.
	 */
	public static boolean[] insert(final boolean[] dest, final boolean[] src, final int offset) {
		final boolean[] temp = new boolean[dest.length + src.length];
		System.arraycopy(dest, 0, temp, 0, offset);
		System.arraycopy(src, 0, temp, offset, src.length);
		System.arraycopy(dest, offset, temp, src.length + offset, dest.length - offset);
		return temp;
	}

	/**
	 * Inserts one element into another <code>boolean</code> array.
	 */
	public static boolean[] insert(final boolean[] dest, final boolean src, final int offset) {
		final boolean[] temp = new boolean[dest.length + 1];
		System.arraycopy(dest, 0, temp, 0, offset);
		temp[offset] = src;
		System.arraycopy(dest, offset, temp, offset + 1, dest.length - offset);
		return temp;
	}


	// ---------------------------------------------------------------- insertAt

	/**
	 * Inserts one array into another at given offset.
	 */
	public static <T> T[] insertAt(final T[] dest, final T[] src, final int offset) {
		final Class<T> componentType = (Class<T>) dest.getClass().getComponentType();
		return insertAt(dest, src, offset, componentType);
	}

	/**
	 * Inserts one array into another at given offset.
	 */
	@SuppressWarnings({"unchecked"})
	public static <T> T[] insertAt(final T[] dest, final T[] src, final int offset, final Class componentType) {
		final T[] temp = (T[]) Array.newInstance(componentType, dest.length + src.length - 1);
		System.arraycopy(dest, 0, temp, 0, offset);
		System.arraycopy(src, 0, temp, offset, src.length);
		System.arraycopy(dest, offset + 1, temp, src.length + offset, dest.length - offset - 1);
		return temp;
	}

	/**
	 * Inserts one array into another by replacing specified offset.
	 */
	public static String[] insertAt(final String[] dest, final String[] src, final int offset) {
		final String[] temp = new String[dest.length + src.length - 1];
		System.arraycopy(dest, 0, temp, 0, offset);
		System.arraycopy(src, 0, temp, offset, src.length);
		System.arraycopy(dest, offset + 1, temp, src.length + offset, dest.length - offset - 1);
		return temp;
	}

	/**
	 * Inserts one array into another by replacing specified offset.
	 */
	public static byte[] insertAt(final byte[] dest, final byte[] src, final int offset) {
		final byte[] temp = new byte[dest.length + src.length - 1];
		System.arraycopy(dest, 0, temp, 0, offset);
		System.arraycopy(src, 0, temp, offset, src.length);
		System.arraycopy(dest, offset + 1, temp, src.length + offset, dest.length - offset - 1);
		return temp;
	}

	/**
	 * Inserts one array into another by replacing specified offset.
	 */
	public static char[] insertAt(final char[] dest, final char[] src, final int offset) {
		final char[] temp = new char[dest.length + src.length - 1];
		System.arraycopy(dest, 0, temp, 0, offset);
		System.arraycopy(src, 0, temp, offset, src.length);
		System.arraycopy(dest, offset + 1, temp, src.length + offset, dest.length - offset - 1);
		return temp;
	}

	/**
	 * Inserts one array into another by replacing specified offset.
	 */
	public static short[] insertAt(final short[] dest, final short[] src, final int offset) {
		final short[] temp = new short[dest.length + src.length - 1];
		System.arraycopy(dest, 0, temp, 0, offset);
		System.arraycopy(src, 0, temp, offset, src.length);
		System.arraycopy(dest, offset + 1, temp, src.length + offset, dest.length - offset - 1);
		return temp;
	}

	/**
	 * Inserts one array into another by replacing specified offset.
	 */
	public static int[] insertAt(final int[] dest, final int[] src, final int offset) {
		final int[] temp = new int[dest.length + src.length - 1];
		System.arraycopy(dest, 0, temp, 0, offset);
		System.arraycopy(src, 0, temp, offset, src.length);
		System.arraycopy(dest, offset + 1, temp, src.length + offset, dest.length - offset - 1);
		return temp;
	}

	/**
	 * Inserts one array into another by replacing specified offset.
	 */
	public static long[] insertAt(final long[] dest, final long[] src, final int offset) {
		final long[] temp = new long[dest.length + src.length - 1];
		System.arraycopy(dest, 0, temp, 0, offset);
		System.arraycopy(src, 0, temp, offset, src.length);
		System.arraycopy(dest, offset + 1, temp, src.length + offset, dest.length - offset - 1);
		return temp;
	}

	/**
	 * Inserts one array into another by replacing specified offset.
	 */
	public static float[] insertAt(final float[] dest, final float[] src, final int offset) {
		final float[] temp = new float[dest.length + src.length - 1];
		System.arraycopy(dest, 0, temp, 0, offset);
		System.arraycopy(src, 0, temp, offset, src.length);
		System.arraycopy(dest, offset + 1, temp, src.length + offset, dest.length - offset - 1);
		return temp;
	}

	/**
	 * Inserts one array into another by replacing specified offset.
	 */
	public static double[] insertAt(final double[] dest, final double[] src, final int offset) {
		final double[] temp = new double[dest.length + src.length - 1];
		System.arraycopy(dest, 0, temp, 0, offset);
		System.arraycopy(src, 0, temp, offset, src.length);
		System.arraycopy(dest, offset + 1, temp, src.length + offset, dest.length - offset - 1);
		return temp;
	}

	/**
	 * Inserts one array into another by replacing specified offset.
	 */
	public static boolean[] insertAt(final boolean[] dest, final boolean[] src, final int offset) {
		final boolean[] temp = new boolean[dest.length + src.length - 1];
		System.arraycopy(dest, 0, temp, 0, offset);
		System.arraycopy(src, 0, temp, offset, src.length);
		System.arraycopy(dest, offset + 1, temp, src.length + offset, dest.length - offset - 1);
		return temp;
	}


	// ---------------------------------------------------------------- convert


	/**
	 * Converts to primitive array.
	 */
	public static byte[] values(final Byte[] array) {
		final byte[] dest = new byte[array.length];
		for (int i = 0; i < array.length; i++) {
			final Byte v = array[i];
			if (v != null) {
				dest[i] = v.byteValue();
			}
		}
		return dest;
	}
	/**
	 * Converts to object array.
	 */
	public static Byte[] valuesOf(final byte[] array) {
		final Byte[] dest = new Byte[array.length];
		for (int i = 0; i < array.length; i++) {
			dest[i] = Byte.valueOf(array[i]);
		}
		return dest;
	}


	/**
	 * Converts to primitive array.
	 */
	public static char[] values(final Character[] array) {
		final char[] dest = new char[array.length];
		for (int i = 0; i < array.length; i++) {
			final Character v = array[i];
			if (v != null) {
				dest[i] = v.charValue();
			}
		}
		return dest;
	}
	/**
	 * Converts to object array.
	 */
	public static Character[] valuesOf(final char[] array) {
		final Character[] dest = new Character[array.length];
		for (int i = 0; i < array.length; i++) {
			dest[i] = Character.valueOf(array[i]);
		}
		return dest;
	}


	/**
	 * Converts to primitive array.
	 */
	public static short[] values(final Short[] array) {
		final short[] dest = new short[array.length];
		for (int i = 0; i < array.length; i++) {
			final Short v = array[i];
			if (v != null) {
				dest[i] = v.shortValue();
			}
		}
		return dest;
	}
	/**
	 * Converts to object array.
	 */
	public static Short[] valuesOf(final short[] array) {
		final Short[] dest = new Short[array.length];
		for (int i = 0; i < array.length; i++) {
			dest[i] = Short.valueOf(array[i]);
		}
		return dest;
	}


	/**
	 * Converts to primitive array.
	 */
	public static int[] values(final Integer[] array) {
		final int[] dest = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			final Integer v = array[i];
			if (v != null) {
				dest[i] = v.intValue();
			}
		}
		return dest;
	}
	/**
	 * Converts to object array.
	 */
	public static Integer[] valuesOf(final int[] array) {
		final Integer[] dest = new Integer[array.length];
		for (int i = 0; i < array.length; i++) {
			dest[i] = Integer.valueOf(array[i]);
		}
		return dest;
	}


	/**
	 * Converts to primitive array.
	 */
	public static long[] values(final Long[] array) {
		final long[] dest = new long[array.length];
		for (int i = 0; i < array.length; i++) {
			final Long v = array[i];
			if (v != null) {
				dest[i] = v.longValue();
			}
		}
		return dest;
	}
	/**
	 * Converts to object array.
	 */
	public static Long[] valuesOf(final long[] array) {
		final Long[] dest = new Long[array.length];
		for (int i = 0; i < array.length; i++) {
			dest[i] = Long.valueOf(array[i]);
		}
		return dest;
	}


	/**
	 * Converts to primitive array.
	 */
	public static float[] values(final Float[] array) {
		final float[] dest = new float[array.length];
		for (int i = 0; i < array.length; i++) {
			final Float v = array[i];
			if (v != null) {
				dest[i] = v.floatValue();
			}
		}
		return dest;
	}
	/**
	 * Converts to object array.
	 */
	public static Float[] valuesOf(final float[] array) {
		final Float[] dest = new Float[array.length];
		for (int i = 0; i < array.length; i++) {
			dest[i] = Float.valueOf(array[i]);
		}
		return dest;
	}


	/**
	 * Converts to primitive array.
	 */
	public static double[] values(final Double[] array) {
		final double[] dest = new double[array.length];
		for (int i = 0; i < array.length; i++) {
			final Double v = array[i];
			if (v != null) {
				dest[i] = v.doubleValue();
			}
		}
		return dest;
	}
	/**
	 * Converts to object array.
	 */
	public static Double[] valuesOf(final double[] array) {
		final Double[] dest = new Double[array.length];
		for (int i = 0; i < array.length; i++) {
			dest[i] = Double.valueOf(array[i]);
		}
		return dest;
	}


	/**
	 * Converts to primitive array.
	 */
	public static boolean[] values(final Boolean[] array) {
		final boolean[] dest = new boolean[array.length];
		for (int i = 0; i < array.length; i++) {
			final Boolean v = array[i];
			if (v != null) {
				dest[i] = v.booleanValue();
			}
		}
		return dest;
	}
	/**
	 * Converts to object array.
	 */
	public static Boolean[] valuesOf(final boolean[] array) {
		final Boolean[] dest = new Boolean[array.length];
		for (int i = 0; i < array.length; i++) {
			dest[i] = Boolean.valueOf(array[i]);
		}
		return dest;
	}



	// ---------------------------------------------------------------- indexof


	/**
	 * Finds the first occurrence of an element in an array.
	 */
	public static int indexOf(final byte[] array, final byte value) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Returns <code>true</code> if an array contains given value.
	 */
	public static boolean contains(final byte[] array, final byte value) {
		return indexOf(array, value) != -1;
	}
	/**
	 * Finds the first occurrence of given value in an array from specified given position.
	 */
	public static int indexOf(final byte[] array, final byte value, final int startIndex) {
		for (int i = startIndex; i < array.length; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Finds the first occurrence in an array from specified given position and upto given length.
	 */
	public static int indexOf(final byte[] array, final byte value, final int startIndex, final int endIndex) {
		for (int i = startIndex; i < endIndex; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Finds the first occurrence of an element in an array.
	 */
	public static int indexOf(final char[] array, final char value) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Returns <code>true</code> if an array contains given value.
	 */
	public static boolean contains(final char[] array, final char value) {
		return indexOf(array, value) != -1;
	}
	/**
	 * Finds the first occurrence of given value in an array from specified given position.
	 */
	public static int indexOf(final char[] array, final char value, final int startIndex) {
		for (int i = startIndex; i < array.length; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Finds the first occurrence in an array from specified given position and upto given length.
	 */
	public static int indexOf(final char[] array, final char value, final int startIndex, final int endIndex) {
		for (int i = startIndex; i < endIndex; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Finds the first occurrence of an element in an array.
	 */
	public static int indexOf(final short[] array, final short value) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Returns <code>true</code> if an array contains given value.
	 */
	public static boolean contains(final short[] array, final short value) {
		return indexOf(array, value) != -1;
	}
	/**
	 * Finds the first occurrence of given value in an array from specified given position.
	 */
	public static int indexOf(final short[] array, final short value, final int startIndex) {
		for (int i = startIndex; i < array.length; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Finds the first occurrence in an array from specified given position and upto given length.
	 */
	public static int indexOf(final short[] array, final short value, final int startIndex, final int endIndex) {
		for (int i = startIndex; i < endIndex; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Finds the first occurrence of an element in an array.
	 */
	public static int indexOf(final int[] array, final int value) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Returns <code>true</code> if an array contains given value.
	 */
	public static boolean contains(final int[] array, final int value) {
		return indexOf(array, value) != -1;
	}
	/**
	 * Finds the first occurrence of given value in an array from specified given position.
	 */
	public static int indexOf(final int[] array, final int value, final int startIndex) {
		for (int i = startIndex; i < array.length; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Finds the first occurrence in an array from specified given position and upto given length.
	 */
	public static int indexOf(final int[] array, final int value, final int startIndex, final int endIndex) {
		for (int i = startIndex; i < endIndex; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Finds the first occurrence of an element in an array.
	 */
	public static int indexOf(final long[] array, final long value) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Returns <code>true</code> if an array contains given value.
	 */
	public static boolean contains(final long[] array, final long value) {
		return indexOf(array, value) != -1;
	}
	/**
	 * Finds the first occurrence of given value in an array from specified given position.
	 */
	public static int indexOf(final long[] array, final long value, final int startIndex) {
		for (int i = startIndex; i < array.length; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Finds the first occurrence in an array from specified given position and upto given length.
	 */
	public static int indexOf(final long[] array, final long value, final int startIndex, final int endIndex) {
		for (int i = startIndex; i < endIndex; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Finds the first occurrence of an element in an array.
	 */
	public static int indexOf(final boolean[] array, final boolean value) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Returns <code>true</code> if an array contains given value.
	 */
	public static boolean contains(final boolean[] array, final boolean value) {
		return indexOf(array, value) != -1;
	}
	/**
	 * Finds the first occurrence of given value in an array from specified given position.
	 */
	public static int indexOf(final boolean[] array, final boolean value, final int startIndex) {
		for (int i = startIndex; i < array.length; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Finds the first occurrence in an array from specified given position and upto given length.
	 */
	public static int indexOf(final boolean[] array, final boolean value, final int startIndex, final int endIndex) {
		for (int i = startIndex; i < endIndex; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Finds the first occurrence of value in <code>float</code> array.
	 */
	public static int indexOf(final float[] array, final float value) {
		for (int i = 0; i < array.length; i++) {
			if (Float.compare(array[i], value) == 0) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Returns <code>true</code> if <code>float</code> array contains given value.
	 */
	public static boolean contains(final float[] array, final float value) {
		return indexOf(array, value) != -1;
	}
	/**
	 * Finds the first occurrence of given value in <code>float</code>
	 * array from specified given position.
	 */
	public static int indexOf(final float[] array, final float value, final int startIndex) {
		for (int i = startIndex; i < array.length; i++) {
			if (Float.compare(array[i], value) == 0) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Finds the first occurrence in <code>float</code> array from specified given position and upto given length.
	 */
	public static int indexOf(final float[] array, final float value, final int startIndex, final int endIndex) {
		for (int i = startIndex; i < endIndex; i++) {
			if (Float.compare(array[i], value) == 0) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Finds the first occurrence of value in <code>double</code> array.
	 */
	public static int indexOf(final double[] array, final double value) {
		for (int i = 0; i < array.length; i++) {
			if (Double.compare(array[i], value) == 0) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Returns <code>true</code> if <code>double</code> array contains given value.
	 */
	public static boolean contains(final double[] array, final double value) {
		return indexOf(array, value) != -1;
	}
	/**
	 * Finds the first occurrence of given value in <code>double</code>
	 * array from specified given position.
	 */
	public static int indexOf(final double[] array, final double value, final int startIndex) {
		for (int i = startIndex; i < array.length; i++) {
			if (Double.compare(array[i], value) == 0) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Finds the first occurrence in <code>double</code> array from specified given position and upto given length.
	 */
	public static int indexOf(final double[] array, final double value, final int startIndex, final int endIndex) {
		for (int i = startIndex; i < endIndex; i++) {
			if (Double.compare(array[i], value) == 0) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Finds the first occurrence in an array.
	 */
	public static int indexOf(final Object[] array, final Object value) {
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}
	public static boolean contains(final Object[] array, final Object value) {
		return indexOf(array, value) != -1;
	}

	/**
	 * Finds the first occurrence in an array from specified given position.
	 */
	public static int indexOf(final Object[] array, final Object value, final int startIndex) {
		for (int i = startIndex; i < array.length; i++) {
			if (array[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}
	public static boolean contains(final Object[] array, final Object value, final int startIndex) {
		return indexOf(array, value, startIndex) != -1;
	}




	// ---------------------------------------------------------------- indexof 2


	/**
	 * Finds the first occurrence in an array.
	 */
	public static int indexOf(final byte[] array, final byte[] sub) {
		return indexOf(array, sub, 0, array.length);
	}
	public static boolean contains(final byte[] array, final byte[] sub) {
		return indexOf(array, sub) != -1;
	}


	/**
	 * Finds the first occurrence in an array from specified given position.
	 */
	public static int indexOf(final byte[] array, final byte[] sub, final int startIndex) {
		return indexOf(array, sub, startIndex, array.length);
	}

	/**
	 * Finds the first occurrence in an array from specified given position and upto given length.
	 */
	public static int indexOf(final byte[] array, final byte[] sub, final int startIndex, final int endIndex) {
		final int sublen = sub.length;
		if (sublen == 0) {
			return startIndex;
		}
		final int total = endIndex - sublen + 1;
		final byte c = sub[0];
		mainloop:
		for (int i = startIndex; i < total; i++) {
			if (array[i] != c) {
				continue;
			}
			int j = 1;
			int k = i + 1;
			while (j < sublen) {
				if (sub[j] != array[k]) {
					continue mainloop;
				}
				j++; k++;
			}
			return i;
		}
		return -1;
	}

	/**
	 * Finds the first occurrence in an array.
	 */
	public static int indexOf(final char[] array, final char[] sub) {
		return indexOf(array, sub, 0, array.length);
	}
	public static boolean contains(final char[] array, final char[] sub) {
		return indexOf(array, sub) != -1;
	}


	/**
	 * Finds the first occurrence in an array from specified given position.
	 */
	public static int indexOf(final char[] array, final char[] sub, final int startIndex) {
		return indexOf(array, sub, startIndex, array.length);
	}

	/**
	 * Finds the first occurrence in an array from specified given position and upto given length.
	 */
	public static int indexOf(final char[] array, final char[] sub, final int startIndex, final int endIndex) {
		final int sublen = sub.length;
		if (sublen == 0) {
			return startIndex;
		}
		final int total = endIndex - sublen + 1;
		final char c = sub[0];
		mainloop:
		for (int i = startIndex; i < total; i++) {
			if (array[i] != c) {
				continue;
			}
			int j = 1;
			int k = i + 1;
			while (j < sublen) {
				if (sub[j] != array[k]) {
					continue mainloop;
				}
				j++; k++;
			}
			return i;
		}
		return -1;
	}

	/**
	 * Finds the first occurrence in an array.
	 */
	public static int indexOf(final short[] array, final short[] sub) {
		return indexOf(array, sub, 0, array.length);
	}
	public static boolean contains(final short[] array, final short[] sub) {
		return indexOf(array, sub) != -1;
	}


	/**
	 * Finds the first occurrence in an array from specified given position.
	 */
	public static int indexOf(final short[] array, final short[] sub, final int startIndex) {
		return indexOf(array, sub, startIndex, array.length);
	}

	/**
	 * Finds the first occurrence in an array from specified given position and upto given length.
	 */
	public static int indexOf(final short[] array, final short[] sub, final int startIndex, final int endIndex) {
		final int sublen = sub.length;
		if (sublen == 0) {
			return startIndex;
		}
		final int total = endIndex - sublen + 1;
		final short c = sub[0];
		mainloop:
		for (int i = startIndex; i < total; i++) {
			if (array[i] != c) {
				continue;
			}
			int j = 1;
			int k = i + 1;
			while (j < sublen) {
				if (sub[j] != array[k]) {
					continue mainloop;
				}
				j++; k++;
			}
			return i;
		}
		return -1;
	}

	/**
	 * Finds the first occurrence in an array.
	 */
	public static int indexOf(final int[] array, final int[] sub) {
		return indexOf(array, sub, 0, array.length);
	}
	public static boolean contains(final int[] array, final int[] sub) {
		return indexOf(array, sub) != -1;
	}


	/**
	 * Finds the first occurrence in an array from specified given position.
	 */
	public static int indexOf(final int[] array, final int[] sub, final int startIndex) {
		return indexOf(array, sub, startIndex, array.length);
	}

	/**
	 * Finds the first occurrence in an array from specified given position and upto given length.
	 */
	public static int indexOf(final int[] array, final int[] sub, final int startIndex, final int endIndex) {
		final int sublen = sub.length;
		if (sublen == 0) {
			return startIndex;
		}
		final int total = endIndex - sublen + 1;
		final int c = sub[0];
		mainloop:
		for (int i = startIndex; i < total; i++) {
			if (array[i] != c) {
				continue;
			}
			int j = 1;
			int k = i + 1;
			while (j < sublen) {
				if (sub[j] != array[k]) {
					continue mainloop;
				}
				j++; k++;
			}
			return i;
		}
		return -1;
	}

	/**
	 * Finds the first occurrence in an array.
	 */
	public static int indexOf(final long[] array, final long[] sub) {
		return indexOf(array, sub, 0, array.length);
	}
	public static boolean contains(final long[] array, final long[] sub) {
		return indexOf(array, sub) != -1;
	}


	/**
	 * Finds the first occurrence in an array from specified given position.
	 */
	public static int indexOf(final long[] array, final long[] sub, final int startIndex) {
		return indexOf(array, sub, startIndex, array.length);
	}

	/**
	 * Finds the first occurrence in an array from specified given position and upto given length.
	 */
	public static int indexOf(final long[] array, final long[] sub, final int startIndex, final int endIndex) {
		final int sublen = sub.length;
		if (sublen == 0) {
			return startIndex;
		}
		final int total = endIndex - sublen + 1;
		final long c = sub[0];
		mainloop:
		for (int i = startIndex; i < total; i++) {
			if (array[i] != c) {
				continue;
			}
			int j = 1;
			int k = i + 1;
			while (j < sublen) {
				if (sub[j] != array[k]) {
					continue mainloop;
				}
				j++; k++;
			}
			return i;
		}
		return -1;
	}

	/**
	 * Finds the first occurrence in an array.
	 */
	public static int indexOf(final boolean[] array, final boolean[] sub) {
		return indexOf(array, sub, 0, array.length);
	}
	public static boolean contains(final boolean[] array, final boolean[] sub) {
		return indexOf(array, sub) != -1;
	}


	/**
	 * Finds the first occurrence in an array from specified given position.
	 */
	public static int indexOf(final boolean[] array, final boolean[] sub, final int startIndex) {
		return indexOf(array, sub, startIndex, array.length);
	}

	/**
	 * Finds the first occurrence in an array from specified given position and upto given length.
	 */
	public static int indexOf(final boolean[] array, final boolean[] sub, final int startIndex, final int endIndex) {
		final int sublen = sub.length;
		if (sublen == 0) {
			return startIndex;
		}
		final int total = endIndex - sublen + 1;
		final boolean c = sub[0];
		mainloop:
		for (int i = startIndex; i < total; i++) {
			if (array[i] != c) {
				continue;
			}
			int j = 1;
			int k = i + 1;
			while (j < sublen) {
				if (sub[j] != array[k]) {
					continue mainloop;
				}
				j++; k++;
			}
			return i;
		}
		return -1;
	}

	/**
	 * Finds the first occurrence in an array.
	 */
	public static int indexOf(final float[] array, final float[] sub) {
		return indexOf(array, sub, 0, array.length);
	}
	public static boolean contains(final float[] array, final float[] sub) {
		return indexOf(array, sub) != -1;
	}


	/**
	 * Finds the first occurrence in an array from specified given position.
	 */
	public static int indexOf(final float[] array, final float[] sub, final int startIndex) {
		return indexOf(array, sub, startIndex, array.length);
	}

	/**
	 * Finds the first occurrence in an array from specified given position and upto given length.
	 */
	public static int indexOf(final float[] array, final float[] sub, final int startIndex, final int endIndex) {
		final int sublen = sub.length;
		if (sublen == 0) {
			return startIndex;
		}
		final int total = endIndex - sublen + 1;
		final float c = sub[0];
		mainloop:
		for (int i = startIndex; i < total; i++) {
			if (Float.compare(array[i], c) != 0) {
				continue;
			}
			int j = 1;
			int k = i + 1;
			while (j < sublen) {
				if (Float.compare(sub[j], array[k]) != 0) {
					continue mainloop;
				}
				j++; k++;
			}
			return i;
		}
		return -1;
	}

	/**
	 * Finds the first occurrence in an array.
	 */
	public static int indexOf(final double[] array, final double[] sub) {
		return indexOf(array, sub, 0, array.length);
	}
	public static boolean contains(final double[] array, final double[] sub) {
		return indexOf(array, sub) != -1;
	}


	/**
	 * Finds the first occurrence in an array from specified given position.
	 */
	public static int indexOf(final double[] array, final double[] sub, final int startIndex) {
		return indexOf(array, sub, startIndex, array.length);
	}

	/**
	 * Finds the first occurrence in an array from specified given position and upto given length.
	 */
	public static int indexOf(final double[] array, final double[] sub, final int startIndex, final int endIndex) {
		final int sublen = sub.length;
		if (sublen == 0) {
			return startIndex;
		}
		final int total = endIndex - sublen + 1;
		final double c = sub[0];
		mainloop:
		for (int i = startIndex; i < total; i++) {
			if (Double.compare(array[i], c) != 0) {
				continue;
			}
			int j = 1;
			int k = i + 1;
			while (j < sublen) {
				if (Double.compare(sub[j], array[k]) != 0) {
					continue mainloop;
				}
				j++; k++;
			}
			return i;
		}
		return -1;
	}


	// ---------------------------------------------------------------- toString

	/**
	 * Converts an array to string. Elements are separated by comma.
	 * Returned string contains no brackets.
	 */
	public static String toString(final Object[] array) {
		if (array == null) {
			return NULL;
		}
		if (array.length == 0) {
			return StringPool.EMPTY;
		}
		final StringBand sb = new StringBand((array.length << 1) - 1);
		for (int i = 0; i < array.length; i++) {
			if (i != 0) {
				sb.append(StringPool.COMMA);
			}
			sb.append(array[i]);
		}
		return sb.toString();
	}

	/**
	 * Converts an array to string. Elements are separated by comma.
	 * Returned string contains no brackets.
	 */
	public static String toString(final String[] array) {
		if (array == null) {
			return NULL;
		}
		if (array.length == 0) {
			return StringPool.EMPTY;
		}
		final StringBand sb = new StringBand((array.length << 1) - 1);
		for (int i = 0; i < array.length; i++) {
			if (i != 0) {
				sb.append(StringPool.COMMA);
			}
			sb.append(array[i]);
		}
		return sb.toString();
	}

	/**
	 * Converts an array to string. Elements are separated by comma.
	 * Returned string contains no brackets.
	 */
	public static String toString(final byte[] array) {
		if (array == null) {
			return NULL;
		}
		if (array.length == 0) {
			return StringPool.EMPTY;
		}
		final StringBand sb = new StringBand((array.length << 1) - 1);
		for (int i = 0; i < array.length; i++) {
			if (i != 0) {
				sb.append(StringPool.COMMA);
			}
			sb.append(array[i]);
		}
		return sb.toString();
	}

	/**
	 * Converts an array to string. Elements are separated by comma.
	 * Returned string contains no brackets.
	 */
	public static String toString(final char[] array) {
		if (array == null) {
			return NULL;
		}
		if (array.length == 0) {
			return StringPool.EMPTY;
		}
		final StringBand sb = new StringBand((array.length << 1) - 1);
		for (int i = 0; i < array.length; i++) {
			if (i != 0) {
				sb.append(StringPool.COMMA);
			}
			sb.append(array[i]);
		}
		return sb.toString();
	}

	/**
	 * Converts an array to string. Elements are separated by comma.
	 * Returned string contains no brackets.
	 */
	public static String toString(final short[] array) {
		if (array == null) {
			return NULL;
		}
		if (array.length == 0) {
			return StringPool.EMPTY;
		}
		final StringBand sb = new StringBand((array.length << 1) - 1);
		for (int i = 0; i < array.length; i++) {
			if (i != 0) {
				sb.append(StringPool.COMMA);
			}
			sb.append(array[i]);
		}
		return sb.toString();
	}

	/**
	 * Converts an array to string. Elements are separated by comma.
	 * Returned string contains no brackets.
	 */
	public static String toString(final int[] array) {
		if (array == null) {
			return NULL;
		}
		if (array.length == 0) {
			return StringPool.EMPTY;
		}
		final StringBand sb = new StringBand((array.length << 1) - 1);
		for (int i = 0; i < array.length; i++) {
			if (i != 0) {
				sb.append(StringPool.COMMA);
			}
			sb.append(array[i]);
		}
		return sb.toString();
	}

	/**
	 * Converts an array to string. Elements are separated by comma.
	 * Returned string contains no brackets.
	 */
	public static String toString(final long[] array) {
		if (array == null) {
			return NULL;
		}
		if (array.length == 0) {
			return StringPool.EMPTY;
		}
		final StringBand sb = new StringBand((array.length << 1) - 1);
		for (int i = 0; i < array.length; i++) {
			if (i != 0) {
				sb.append(StringPool.COMMA);
			}
			sb.append(array[i]);
		}
		return sb.toString();
	}

	/**
	 * Converts an array to string. Elements are separated by comma.
	 * Returned string contains no brackets.
	 */
	public static String toString(final float[] array) {
		if (array == null) {
			return NULL;
		}
		if (array.length == 0) {
			return StringPool.EMPTY;
		}
		final StringBand sb = new StringBand((array.length << 1) - 1);
		for (int i = 0; i < array.length; i++) {
			if (i != 0) {
				sb.append(StringPool.COMMA);
			}
			sb.append(array[i]);
		}
		return sb.toString();
	}

	/**
	 * Converts an array to string. Elements are separated by comma.
	 * Returned string contains no brackets.
	 */
	public static String toString(final double[] array) {
		if (array == null) {
			return NULL;
		}
		if (array.length == 0) {
			return StringPool.EMPTY;
		}
		final StringBand sb = new StringBand((array.length << 1) - 1);
		for (int i = 0; i < array.length; i++) {
			if (i != 0) {
				sb.append(StringPool.COMMA);
			}
			sb.append(array[i]);
		}
		return sb.toString();
	}

	/**
	 * Converts an array to string. Elements are separated by comma.
	 * Returned string contains no brackets.
	 */
	public static String toString(final boolean[] array) {
		if (array == null) {
			return NULL;
		}
		if (array.length == 0) {
			return StringPool.EMPTY;
		}
		final StringBand sb = new StringBand((array.length << 1) - 1);
		for (int i = 0; i < array.length; i++) {
			if (i != 0) {
				sb.append(StringPool.COMMA);
			}
			sb.append(array[i]);
		}
		return sb.toString();
	}


	/**
	 * Converts an array to string array.
	 */
	public static String[] toStringArray(final Object[] array) {
		if (array == null) {
			return null;
		}
		final String[] result = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = StringUtil.toString(array[i]);
		}
		return result;
	}

	/**
	 * Converts an array to string array.
	 */
	public static String[] toStringArray(final String[] array) {
		if (array == null) {
			return null;
		}
		final String[] result = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = String.valueOf(array[i]);
		}
		return result;
	}

	/**
	 * Converts an array to string array.
	 */
	public static String[] toStringArray(final byte[] array) {
		if (array == null) {
			return null;
		}
		final String[] result = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = String.valueOf(array[i]);
		}
		return result;
	}

	/**
	 * Converts an array to string array.
	 */
	public static String[] toStringArray(final char[] array) {
		if (array == null) {
			return null;
		}
		final String[] result = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = String.valueOf(array[i]);
		}
		return result;
	}

	/**
	 * Converts an array to string array.
	 */
	public static String[] toStringArray(final short[] array) {
		if (array == null) {
			return null;
		}
		final String[] result = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = String.valueOf(array[i]);
		}
		return result;
	}

	/**
	 * Converts an array to string array.
	 */
	public static String[] toStringArray(final int[] array) {
		if (array == null) {
			return null;
		}
		final String[] result = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = String.valueOf(array[i]);
		}
		return result;
	}

	/**
	 * Converts an array to string array.
	 */
	public static String[] toStringArray(final long[] array) {
		if (array == null) {
			return null;
		}
		final String[] result = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = String.valueOf(array[i]);
		}
		return result;
	}

	/**
	 * Converts an array to string array.
	 */
	public static String[] toStringArray(final float[] array) {
		if (array == null) {
			return null;
		}
		final String[] result = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = String.valueOf(array[i]);
		}
		return result;
	}

	/**
	 * Converts an array to string array.
	 */
	public static String[] toStringArray(final double[] array) {
		if (array == null) {
			return null;
		}
		final String[] result = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = String.valueOf(array[i]);
		}
		return result;
	}

	/**
	 * Converts an array to string array.
	 */
	public static String[] toStringArray(final boolean[] array) {
		if (array == null) {
			return null;
		}
		final String[] result = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = String.valueOf(array[i]);
		}
		return result;
	}
}
