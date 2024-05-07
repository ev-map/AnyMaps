/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.maplibre.adapter.factory.anymap;

import com.car2go.maps.maplibre.adapter.AnyMapAdapter;
import com.car2go.maps.maplibre.adapter.factory.Mapper;
import com.car2go.maps.model.LatLng;

import org.maplibre.android.camera.CameraPosition;

/**
 * Maps Google CameraPosition to AnyMap CameraPosition
 */
public class CameraPositionMapper implements Mapper<CameraPosition, com.car2go.maps.model.CameraPosition> {

	private final AnyMapAdapter anyMapAdapter;

	public CameraPositionMapper(AnyMapAdapter anyMapAdapter) {
		this.anyMapAdapter = anyMapAdapter;
	}

	@Override
	public com.car2go.maps.model.CameraPosition map(CameraPosition input) {
		LatLng anyLatLng = anyMapAdapter.map(input.target);

		return new com.car2go.maps.model.CameraPosition(
				anyLatLng,
				(float) input.zoom + 1 // mapbox zoom levels are shifted by one
		);
	}

}
