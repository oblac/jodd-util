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

import java.io.File;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Various system information.
 */
public final class SystemInfo {

	// ---------------------------------------------------------------- host

	/**
	 * Delegate host info to be resolved lazy.
	 * Android detection will initialize this class too and since {@code InetAddress.getLocalHost()}
	 * is forbidden in Android, we will get an exception.
	 */
	private static class HostInfoLazy {
		private final String HOST_NAME;
		private final String HOST_ADDRESS;

		public HostInfoLazy() {
			String hostName;
			String hostAddress;

			try {
				final InetAddress localhost = InetAddress.getLocalHost();

				hostName = localhost.getHostName();
				hostAddress = localhost.getHostAddress();
			} catch (final UnknownHostException uhex) {
				hostName = "localhost";
				hostAddress = "127.0.0.1";
			}

			this.HOST_NAME = hostName;
			this.HOST_ADDRESS = hostAddress;
		}
	}

	private static HostInfoLazy hostInfoLazy;

	/**
	 * Returns host name.
	 */
	public final String getHostName() {
		if (hostInfoLazy == null) {
			hostInfoLazy = new HostInfoLazy();
		}
		return hostInfoLazy.HOST_NAME;
	}

	/**
	 * Returns host IP address.
	 */
	public final String getHostAddress() {
		if (hostInfoLazy == null) {
			hostInfoLazy = new HostInfoLazy();
		}
		return hostInfoLazy.HOST_ADDRESS;
	}

	// ---------------------------------------------------------------- JVM

	private final String JAVA_VM_NAME = SystemUtil.get("java.vm.name");
	private final String JAVA_VM_VERSION = SystemUtil.get("java.vm.version");
	private final String JAVA_VM_VENDOR = SystemUtil.get("java.vm.vendor");
	private final String JAVA_VM_INFO = SystemUtil.get("java.vm.info");
	private final String JAVA_VM_SPECIFICATION_NAME = SystemUtil.get("java.vm.specification.name");
	private final String JAVA_VM_SPECIFICATION_VERSION = SystemUtil.get("java.vm.specification.version");
	private final String JAVA_VM_SPECIFICATION_VENDOR = SystemUtil.get("java.vm.specification.vendor");

	/**
	 * Returns JVM name.
	 */
	public final String getJvmName() {
		return JAVA_VM_NAME;
	}

	/**
	 * Returns JVM version.
	 */
	public final String getJvmVersion() {
		return JAVA_VM_VERSION;
	}

	/**
	 * Returns VM vendor.
	 */
	public final String getJvmVendor() {
		return JAVA_VM_VENDOR;
	}

	/**
	 * Returns additional VM information.
	 */
	public final String getJvmInfo() {
		return JAVA_VM_INFO;
	}

	public final String getJvmSpecificationName() {
		return JAVA_VM_SPECIFICATION_NAME;
	}

	public final String getJvmSpecificationVersion() {
		return JAVA_VM_SPECIFICATION_VERSION;
	}

	public final String getJvmSpecificationVendor() {
		return JAVA_VM_SPECIFICATION_VENDOR;
	}

	// ---------------------------------------------------------------- JAVA

	private final String JAVA_VERSION = SystemUtil.get("java.version");
	private final int JAVA_VERSION_NUMBER = detectJavaVersionNumber();
	private final String JAVA_VENDOR = SystemUtil.get("java.vendor");
	private final String JAVA_VENDOR_URL = SystemUtil.get("java.vendor.url");
	private final String JAVA_SPECIFICATION_VERSION = SystemUtil.get("java.specification.version");
	private final String JAVA_SPECIFICATION_NAME = SystemUtil.get("java.specification.name");
	private final String JAVA_SPECIFICATION_VENDOR = SystemUtil.get("java.specification.vendor");
	private final String[] JRE_PACKAGES = buildJrePackages(JAVA_VERSION_NUMBER);

	/**
	 * Returns Java version string, as specified in system property.
	 * Returned string contain major version, minor version and revision.
	 */
	public String getJavaVersion() {
		return JAVA_VERSION;
	}

	/**
	 * Returns unified Java version as an integer.
	 */
	public int getJavaVersionNumber() {
		return JAVA_VERSION_NUMBER;
	}

	/**
	 * Returns Java vendor.
	 */
	public String getJavaVendor() {
		return JAVA_VENDOR;
	}

	/**
	 * Returns Java vendor URL.
	 */
	public String getJavaVendorURL() {
		return JAVA_VENDOR_URL;
	}

	/**
	 * Retrieves the version of the currently running JVM.
	 */
	public String getJavaSpecificationVersion() {
		return JAVA_SPECIFICATION_VERSION;
	}

	public final String getJavaSpecificationName() {
		return JAVA_SPECIFICATION_NAME;
	}

	public final String getJavaSpecificationVendor() {
		return JAVA_SPECIFICATION_VENDOR;
	}

	// ---------------------------------------------------------------- packages

	/**
	 * Returns list of packages, build into runtime jars.
	 */
	public String[] getJrePackages() {
		return JRE_PACKAGES;
	}

	/**
	 * Builds a set of java core packages.
	 */
	private String[] buildJrePackages(final int javaVersionNumber) {
		final ArrayList<String> packages = new ArrayList<>();

		switch (javaVersionNumber) {
			case 9:
			case 8:
			case 7:
			case 6:
			case 5:
				// in Java1.5, the apache stuff moved
				packages.add("com.sun.org.apache");
				// fall through...
			case 4:
				if (javaVersionNumber == 4) {
					packages.add("org.apache.crimson");
					packages.add("org.apache.xalan");
					packages.add("org.apache.xml");
					packages.add("org.apache.xpath");
				}
				packages.add("org.ietf.jgss");
				packages.add("org.w3c.dom");
				packages.add("org.xml.sax");
				// fall through...
			case 3:
				packages.add("org.omg");
				packages.add("com.sun.corba");
				packages.add("com.sun.jndi");
				packages.add("com.sun.media");
				packages.add("com.sun.naming");
				packages.add("com.sun.org.omg");
				packages.add("com.sun.rmi");
				packages.add("sunw.io");
				packages.add("sunw.util");
				// fall through...
			case 2:
				packages.add("com.sun.java");
				packages.add("com.sun.image");
				// fall through...
			case 1:
			default:
				// core stuff
				packages.add("sun");
				packages.add("java");
				packages.add("javax");
				break;
		}

		return packages.toArray(new String[0]);
	}


	// ---------------------------------------------------------------- java checks

	private int detectJavaVersionNumber() {
		String javaVersion = JAVA_VERSION;

		final int lastDashNdx = javaVersion.lastIndexOf('-');
		if (lastDashNdx != -1) {
			javaVersion = javaVersion.substring(0, lastDashNdx);
		}

		if (javaVersion.startsWith("1.")) {
			// up to java 8
			final int index = javaVersion.indexOf('.', 2);
			return Integer.parseInt(javaVersion.substring(2, index));
		} else {
			final int index = javaVersion.indexOf('.');
			return Integer.parseInt(index == -1 ? javaVersion : javaVersion.substring(0, index));
		}
	}

	/**
	 * Checks if the currently running JVM is at least compliant
	 * with provided JDK version.
	 */
	public boolean isAtLeastJavaVersion(final int version) {
		return JAVA_VERSION_NUMBER >= version;
	}

	/**
	 * Checks if the currently running JVM is equal to provided version.
	 */
	public boolean isJavaVersion(final int version) {
		return JAVA_VERSION_NUMBER == version;
	}


	private final String OS_VERSION = SystemUtil.get("os.version");
	private final String OS_ARCH = SystemUtil.get("os.arch");
	private final String OS_NAME = SystemUtil.get("os.name");

	private final boolean IS_ANDROID = isAndroid0();
	private final boolean IS_OS_AIX = matchOS("AIX");
	private final boolean IS_OS_HP_UX = matchOS("HP-UX");
	private final boolean IS_OS_IRIX = matchOS("Irix");
	private final boolean IS_OS_LINUX = matchOS("Linux") || matchOS("LINUX");
	private final boolean IS_OS_MAC = matchOS("Mac");
	private final boolean IS_OS_MAC_OSX = matchOS("Mac OS X");
	private final boolean IS_OS_OS2 = matchOS("OS/2");
	private final boolean IS_OS_SOLARIS = matchOS("Solaris");
	private final boolean IS_OS_SUN_OS = matchOS("SunOS");
	private final boolean IS_OS_WINDOWS = matchOS("Windows");
	private final boolean IS_OS_WINDOWS_2000 = matchOS("Windows", "5.0");
	private final boolean IS_OS_WINDOWS_95 = matchOS("Windows 9", "4.0");
	private final boolean IS_OS_WINDOWS_98 = matchOS("Windows 9", "4.1");
	private final boolean IS_OS_WINDOWS_ME = matchOS("Windows", "4.9");
	private final boolean IS_OS_WINDOWS_NT = matchOS("Windows NT");
	private final boolean IS_OS_WINDOWS_XP = matchOS("Windows", "5.1");

	private final String FILE_SEPARATOR = SystemUtil.get("file.separator");
	private final String LINE_SEPARATOR = SystemUtil.get("line.separator");
	private final String PATH_SEPARATOR = SystemUtil.get("path.separator");
	private final String FILE_ENCODING = SystemUtil.get("file.encoding");

	public final String getOsArchitecture() {
		return OS_ARCH;
	}

	public final String getOsName() {
		return OS_NAME;
	}

	public final String getOsVersion() {
		return OS_VERSION;
	}

	/**
	 * Returns <code>true</code> if system is android.
	 */
	public boolean isAndroid() {
		return IS_ANDROID;
	}

	private static boolean isAndroid0() {
		try {
			Class.forName("android.app.Application", false, ClassLoaderUtil.getSystemClassLoader());
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	public final boolean isAix() {
		return IS_OS_AIX;
	}

	public final boolean isHpUx() {
		return IS_OS_HP_UX;
	}

	public final boolean isIrix() {
		return IS_OS_IRIX;
	}

	public final boolean isLinux() {
		return IS_OS_LINUX;
	}

	public final boolean isMac() {
		return IS_OS_MAC;
	}

	public final boolean isMacOsX() {
		return IS_OS_MAC_OSX;
	}

	public final boolean isOs2() {
		return IS_OS_OS2;
	}

	public final boolean isSolaris() {
		return IS_OS_SOLARIS;
	}

	public final boolean isSunOS() {
		return IS_OS_SUN_OS;
	}

	public final boolean isWindows() {
		return IS_OS_WINDOWS;
	}

	public final boolean isWindows2000() {
		return IS_OS_WINDOWS_2000;
	}

	public final boolean isWindows95() {
		return IS_OS_WINDOWS_95;
	}

	public final boolean isWindows98() {
		return IS_OS_WINDOWS_98;
	}

	public final boolean isWindowsME() {
		return IS_OS_WINDOWS_ME;
	}

	public final boolean isWindowsNT() {
		return IS_OS_WINDOWS_NT;
	}

	public final boolean isWindowsXP() {
		return IS_OS_WINDOWS_XP;
	}

	// ---------------------------------------------------------------- file

	public final String getFileSeparator() {
		return FILE_SEPARATOR;
	}

	public final String getLineSeparator() {
		return LINE_SEPARATOR;
	}

	public final String getPathSeparator() {
		return PATH_SEPARATOR;
	}

	public final String getFileEncoding() {
		return FILE_ENCODING;
	}

	// ---------------------------------------------------------------- util

	private boolean matchOS(final String osNamePrefix) {
		if (OS_NAME == null) {
			return false;
		}

		return OS_NAME.startsWith(osNamePrefix);
	}

	private boolean matchOS(final String osNamePrefix, final String osVersionPrefix) {
		if ((OS_NAME == null) || (OS_VERSION == null)) {
			return false;
		}

		return OS_NAME.startsWith(osNamePrefix) && OS_VERSION.startsWith(osVersionPrefix);

	}

	// ---------------------------------------------------------------- runtime

	private final Runtime runtime = Runtime.getRuntime();

	/**
	 * Returns MAX memory.
	 */
	public final long getMaxMemory(){
		return runtime.maxMemory();
	}

	/**
	 * Returns TOTAL memory.
	 */
	public final long getTotalMemory(){
		return runtime.totalMemory();
	}

	/**
	 * Returns FREE memory.
	 */
	public final long getFreeMemory(){
		return runtime.freeMemory();
	}

	/**
	 * Returns usable memory.
	 */
	public final long getAvailableMemory(){
		return runtime.maxMemory() - runtime.totalMemory() + runtime.freeMemory();
	}

	/**
	 * Returns used memory.
	 */
	public final long getUsedMemory(){
		return runtime.totalMemory() - runtime.freeMemory();
	}

	/**
	 * Returns PID of current Java process.
	 */
	public final long getCurrentPID() {
		return Long.parseLong(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
	}

	/**
	 * Returns number of CPUs.
	 */
	public final long getCPUs() {
		return runtime.availableProcessors();
	}

	// ---------------------------------------------------------------- user


	private final String USER_NAME = SystemUtil.get("user.name");
	private final String USER_HOME = nosep(SystemUtil.get("user.home"));
	private final String USER_DIR = nosep(SystemUtil.get("user.dir"));
	private final String USER_LANGUAGE = SystemUtil.get("user.language");
	private final String USER_COUNTRY = ((SystemUtil.get("user.country") == null) ? SystemUtil.get("user.region") : SystemUtil.get("user.country"));
	private final String JAVA_IO_TMPDIR = SystemUtil.get("java.io.tmpdir");
	private final String JAVA_HOME = nosep(SystemUtil.get("java.home"));
	private final String[] SYSTEM_CLASS_PATH = StringUtil.splitc(SystemUtil.get("java.class.path"), File.pathSeparator);

	public final String getUserName() {
		return USER_NAME;
	}

	public final String getHomeDir() {
		return USER_HOME;
	}

	public final String getWorkingDir() {
		return USER_DIR;
	}

	public final String getTempDir() {
		return JAVA_IO_TMPDIR;
	}

	public final String getUserLanguage() {
		return USER_LANGUAGE;
	}

	public final String getUserCountry() {
		return USER_COUNTRY;
	}

	public String getJavaHomeDir() {
		return JAVA_HOME;
	}

	public String[] getSystemClasspath() {
		return SYSTEM_CLASS_PATH;
	}

	// ---------------------------------------------------------------- util

	protected String nosep(final String in) {
		if (in.endsWith(File.separator)) {
			return in.substring(0, in.length() - 1);
		}
		return in;
	}

}
