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

import jodd.io.IOUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * Utilities to manipulate class path, define and find classes etc.
 */
public class ClassLoaderUtil {

	public static ClassLoaderStrategy classLoaderStrategy = new ClassLoaderStrategy.DefaultClassLoaderStrategy();

	// ---------------------------------------------------------------- default class loader

	/**
	 * Returns default class loader. By default, it is {@link #getContextClassLoader() threads context class loader}.
	 * If this one is <code>null</code>, then class loader of the <b>caller class</b> is returned.
	 */
	public static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = getContextClassLoader();
		if (cl == null) {
			final Class callerClass = ClassUtil.getCallerClass(2);
			cl = callerClass.getClassLoader();
		}
		return cl;
	}

	/**
	 * Returns thread context class loader.
	 */
	public static ClassLoader getContextClassLoader() {
		if (System.getSecurityManager() == null) {
			return Thread.currentThread().getContextClassLoader();
		}
		else {
			return AccessController.doPrivileged(
				(PrivilegedAction<ClassLoader>) () -> Thread.currentThread().getContextClassLoader());
		}
	}

	/**
	 * Returns system class loader.
	 */
	public static ClassLoader getSystemClassLoader() {
		if (System.getSecurityManager() == null) {
			return ClassLoader.getSystemClassLoader();
		}
		else {
			return AccessController.doPrivileged(
				(PrivilegedAction<ClassLoader>) ClassLoader::getSystemClassLoader);
		}
	}

	// ---------------------------------------------------------------- classpath

	private static final String[] MANIFESTS = {"Manifest.mf", "manifest.mf", "MANIFEST.MF"};

	/**
	 * Returns classpath item manifest or <code>null</code> if not found.
	 */
	public static Manifest getClasspathItemManifest(final File classpathItem) {
		Manifest manifest = null;

		if (classpathItem.isFile()) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(classpathItem);
				final JarFile jar = new JarFile(classpathItem);
				manifest = jar.getManifest();
			} catch (final IOException ignore) {
			}
			finally {
				IOUtil.close(fis);
			}
		} else {
			final File metaDir = new File(classpathItem, "META-INF");
			File manifestFile = null;
			if (metaDir.isDirectory()) {
				for (final String m : MANIFESTS) {
					final File mFile = new File(metaDir, m);
					if (mFile.isFile()) {
						manifestFile = mFile;
						break;
					}
				}
			}
			if (manifestFile != null) {
				FileInputStream fis = null;
				try {
					fis = new FileInputStream(manifestFile);
					manifest = new Manifest(fis);
				} catch (final IOException ignore) {
				}
				finally {
					IOUtil.close(fis);
				}
			}
		}

		return manifest;
	}

	/**
	 * Returns base folder for classpath item. If item is a (jar) file,
	 * its parent is returned. If item is a directory, its name is returned.
	 */
	public static String getClasspathItemBaseDir(final File classpathItem) {
		final String base;
		if (classpathItem.isFile()) {
			base = classpathItem.getParent();
		} else {
			base = classpathItem.toString();
		}
		return base;
	}

	// ---------------------------------------------------------------- class stream

	/**
	 * Opens a class of the specified name for reading using class classloader.
	 */
	public static InputStream getClassAsStream(final Class clazz) throws IOException {
		return ResourcesUtil.getResourceAsStream(ClassUtil.convertClassNameToFileName(clazz), clazz.getClassLoader());
	}

	/**
	 * Opens a class of the specified name for reading. No specific classloader is used
	 * for loading class.
	 */
	public static InputStream getClassAsStream(final String className) throws IOException {
		return ResourcesUtil.getResourceAsStream(ClassUtil.convertClassNameToFileName(className));
	}

	/**
	 * Opens a class of the specified name for reading using provided class loader.
	 */
	public static InputStream getClassAsStream(final String className, final ClassLoader classLoader) throws IOException {
		return ResourcesUtil.getResourceAsStream(ClassUtil.convertClassNameToFileName(className), classLoader);
	}

	// ---------------------------------------------------------------- load class

	/**
	 * Loads a class using default class loader strategy.
	 * @see ClassLoaderStrategy.DefaultClassLoaderStrategy
	 */
	public static Class loadClass(final String className) throws ClassNotFoundException {
		return classLoaderStrategy.loadClass(className, null);
	}
	
	/**
	 * Loads a class using default class loader strategy.
	 * @see ClassLoaderStrategy.DefaultClassLoaderStrategy
	 */
	public static Class loadClass(final String className, final ClassLoader classLoader) throws ClassNotFoundException {
		return classLoaderStrategy.loadClass(className, classLoader);
	}

	// ---------------------------------------------------------------- class location

	/**
	 * Returns location of the class. If class is not in a jar, it's classpath
	 * is returned; otherwise the jar location.
	 */
	public static String classLocation(final Class clazz) {
		return clazz.getProtectionDomain().getCodeSource().getLocation().getPath();
	}

}
