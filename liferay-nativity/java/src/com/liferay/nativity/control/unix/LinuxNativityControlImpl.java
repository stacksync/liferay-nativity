/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.nativity.control.unix;

import java.io.File;

/**
 * @author Dennis Ju
 */
public class LinuxNativityControlImpl extends UnixNativityControlBaseImpl {

	@Override
	public boolean load() throws Exception {
		return false;
	}

	@Override
	public boolean loaded() {
		File file1 = new File(
			"/usr/lib/nautilus/extensions-3.0/libliferaynativity.so");

		File file2 = new File(
			"/usr/lib64/nautilus/extensions-3.0/libliferaynativity.so");

		if (file1.exists() || file2.exists()) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void refreshFiles(String[] paths) {
	}

	@Override
	public boolean unload() throws Exception {
		return false;
	}

}