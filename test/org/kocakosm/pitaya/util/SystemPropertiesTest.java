/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012-2014 Osman KOCAK <kocakosm@gmail.com>                   *
 *                                                                            *
 * This program is free software: you can redistribute it and/or modify it    *
 * under the terms of the GNU Lesser General Public License as published by   *
 * the Free Software Foundation, either version 3 of the License, or (at your *
 * option) any later version.                                                 *
 * This program is distributed in the hope that it will be useful, but        *
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY *
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public     *
 * License for more details.                                                  *
 * You should have received a copy of the GNU Lesser General Public License   *
 * along with this program. If not, see <http://www.gnu.org/licenses/>.       *
 *----------------------------------------------------------------------------*/

package org.kocakosm.pitaya.util;

import static org.kocakosm.pitaya.util.SystemProperties.*;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link SystemProperties}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class SystemPropertiesTest
{
	@Test
	public void testOSProperties()
	{
		assertPropertyEquals("os.name", OS_NAME);
		assertPropertyEquals("os.arch", OS_ARCH);
		assertPropertyEquals("os.version", OS_VERSION);
	}

	@Test
	public void testUserProperties()
	{
		assertPropertyEquals("user.dir", USER_DIR);
		assertPropertyEquals("user.name", USER_NAME);
		assertPropertyEquals("user.home", USER_HOME);
	}

	@Test
	public void testFileProperties()
	{
		assertPropertyEquals("file.separator", FILE_SEPARATOR);
	}

	@Test
	public void testPathProperties()
	{
		assertPropertyEquals("path.separator", PATH_SEPARATOR);
	}

	@Test
	public void testLineProperties()
	{
		assertPropertyEquals("line.separator", LINE_SEPARATOR);
	}

	@Test
	public void testJavaProperties()
	{
		assertPropertyEquals("java.home", JAVA_HOME);
		assertPropertyEquals("java.version", JAVA_VERSION);
		assertPropertyEquals("java.compiler", JAVA_COMPILER);
	}

	@Test
	public void testJavaVendorProperties() throws Exception
	{
		assertPropertyEquals("java.vendor", JAVA_VENDOR);
		assertPropertyEquals("java.vendor.url", JAVA_VENDOR_URL);
	}

	@Test
	public void testJavaVMProperties()
	{
		assertPropertyEquals("java.vm.name", JAVA_VM_NAME);
		assertPropertyEquals("java.vm.vendor", JAVA_VM_VENDOR);
		assertPropertyEquals("java.vm.version", JAVA_VM_VERSION);
	}

	@Test
	public void testJavaVMSpecificationProperties()
	{
		assertPropertyEquals("java.vm.specification.name", JAVA_VM_SPECIFICATION_NAME);
		assertPropertyEquals("java.vm.specification.vendor", JAVA_VM_SPECIFICATION_VENDOR);
		assertPropertyEquals("java.vm.specification.version", JAVA_VM_SPECIFICATION_VERSION);
	}

	@Test
	public void testJavaSpecificationProperties()
	{
		assertPropertyEquals("java.specification.name", JAVA_SPECIFICATION_NAME);
		assertPropertyEquals("java.specification.vendor", JAVA_SPECIFICATION_VENDOR);
		assertPropertyEquals("java.specification.version", JAVA_SPECIFICATION_VERSION);
	}

	@Test
	public void testJavaClassProperties()
	{
		assertPropertyEquals("java.class.path", JAVA_CLASS_PATH);
		assertPropertyEquals("java.class.version", JAVA_CLASS_VERSION);
	}

	@Test
	public void testJavaLibraryProperties()
	{
		assertPropertyEquals("java.library.path", JAVA_LIBRARY_PATH);
	}

	@Test
	public void testJavaIOProperties()
	{
		assertPropertyEquals("java.io.tmpdir", JAVA_IO_TMP_DIR);
	}

	@Test
	public void testJavaExtensionProperties()
	{
		assertPropertyEquals("java.ext.dirs", JAVA_EXT_DIRS);
	}

	private void assertPropertyEquals(String key, String value)
	{
		Assert.assertEquals(System.getProperty(key), value);
	}

	private void assertPropertyEquals(String key, URL value) throws Exception
	{
		Assert.assertEquals(new URL(System.getProperty(key)), value);
	}

	private void assertPropertyEquals(String key, File value)
	{
		Assert.assertEquals(new File(System.getProperty(key)), value);
	}

	private void assertPropertyEquals(String key, List<File> values)
	{
		List<File> files = new ArrayList<File>();
		for (String path : System.getProperty(key).split(PATH_SEPARATOR)) {
			files.add(new File(path));
		}
		Assert.assertEquals(files, values);
	}
}
