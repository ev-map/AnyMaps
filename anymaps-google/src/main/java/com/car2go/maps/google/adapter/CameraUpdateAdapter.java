/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.google.adapter;

import com.car2go.maps.CameraUpdate;

/**
 * Adapts Google CameraUpdate to AnyMap CameraUpdate
 */
public class CameraUpdateAdapter implements CameraUpdate {

	public final com.google.android.libraries.maps.CameraUpdate wrappedCameraUpdate;

	public CameraUpdateAdapter(com.google.android.libraries.maps.CameraUpdate wrappedCameraUpdate) {
		this.wrappedCameraUpdate = wrappedCameraUpdate;
	}

}
