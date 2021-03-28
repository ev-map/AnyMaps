/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.osm;

import com.car2go.maps.CameraUpdate;
import com.car2go.maps.model.LatLng;
import com.car2go.maps.model.LatLngBounds;

/**
 * Creates {@link CameraUpdate} objects which can be used to update map camera
 */
public class CameraUpdateFactory implements com.car2go.maps.CameraUpdateFactory {

	private static final CameraUpdateFactory instance = new CameraUpdateFactory();

	private CameraUpdateFactory() {
	}

	public static CameraUpdateFactory getInstance() {
		return instance;
	}

	@Override
	public CameraUpdate newLatLngZoom(LatLng center, float zoomLevel) {
		return new OsmCameraUpdate.Builder()
				.center(center)
				.zoom(zoomLevel)
				.build();
	}

	@Override
	public CameraUpdate newLatLng(LatLng center) {
		return new OsmCameraUpdate.Builder()
				.center(center)
				.build();
	}

	@Override
	public CameraUpdate newLatLngBounds(LatLngBounds bounds, int padding) {
		return new OsmCameraUpdate.Builder()
				.bounds(bounds)
				.padding(padding)
				.build();
	}

	@Override
	public CameraUpdate zoomTo(float zoomLevel) {
		return new OsmCameraUpdate.Builder()
				.zoom(zoomLevel)
				.build();
	}

}
