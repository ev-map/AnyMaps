/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.maplibre.adapter;

import com.car2go.maps.CameraUpdate;

/**
 * Adapts Google CameraUpdate to AnyMap CameraUpdate
 */
public class CameraUpdateAdapter implements CameraUpdate {

	public final com.mapbox.mapboxsdk.camera.CameraUpdate wrappedCameraUpdate;

	public CameraUpdateAdapter(com.mapbox.mapboxsdk.camera.CameraUpdate wrappedCameraUpdate) {
		this.wrappedCameraUpdate = wrappedCameraUpdate;
	}

}


