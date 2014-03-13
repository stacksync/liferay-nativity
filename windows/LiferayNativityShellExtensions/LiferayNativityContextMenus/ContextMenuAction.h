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

#ifndef CONTEXTMENUACTION_H
#define CONTEXTMENUACTION_H

#include "stdafx.h"

class __declspec(dllexport) ContextMenuAction 
{
public:

	std::wstring* GetUuid();
	
	std::vector<std::wstring>* GetFiles();
	
	void SetUuid(std::wstring*);

	void SetFiles(std::vector<std::wstring>*);

private:
	
	std::wstring* _uuid;
	std::vector<std::wstring>* _files;
};

#endif