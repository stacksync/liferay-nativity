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

package com.liferay.nativity.modules.fileicon;

import com.liferay.nativity.control.NativityControl;
import com.liferay.nativity.modules.fileicon.unix.AppleFileIconControlImpl;
import com.liferay.nativity.modules.fileicon.unix.LinuxFileIconControlImpl;
import com.liferay.nativity.modules.fileicon.win.WindowsFileIconControlImpl;
import com.liferay.nativity.util.OSDetector;

/**
 * @author Dennis Ju
 */
public class FileIconControlUtil {

	public static FileIconControl getFileIconControl(
		NativityControl nativityControl,
		FileIconControlCallback fileIconControlCallback) {

		FileIconControlUtil fileIconControlUtil = new FileIconControlUtil(
			nativityControl, fileIconControlCallback);

		if (OSDetector.isApple()) {
			return fileIconControlUtil.createAppleFileIconControl();
		}
		else if (OSDetector.isWindows()) {
			return fileIconControlUtil.createWindowsFileIconControl();
		}
		else if (OSDetector.isLinux()) {
			return fileIconControlUtil.createLinuxFileIconControl();
		}

		return null;
	}

	protected FileIconControl createAppleFileIconControl() {
		return new AppleFileIconControlImpl(
			_nativityControl, _fileIconControlCallback);
	}

	protected FileIconControl createLinuxFileIconControl() {
		return new LinuxFileIconControlImpl(
			_nativityControl, _fileIconControlCallback);
	}

	protected FileIconControl createWindowsFileIconControl() {
		return new WindowsFileIconControlImpl(
			_nativityControl, _fileIconControlCallback);
	}

	private FileIconControlUtil(
		NativityControl nativityControl,
		FileIconControlCallback fileIconControlCallback) {

		_nativityControl = nativityControl;
		_fileIconControlCallback = fileIconControlCallback;
	}

	private FileIconControlCallback _fileIconControlCallback;
	private NativityControl _nativityControl;

}