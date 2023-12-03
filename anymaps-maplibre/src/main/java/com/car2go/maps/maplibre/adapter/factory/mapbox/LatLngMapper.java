/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.maplibre.adapter.factory.mapbox;

import com.car2go.maps.maplibre.adapter.factory.Mapper;
import com.car2go.maps.model.LatLng;

/**
 * Maps AnyMap LatLng to Google LatLng
 */
public class LatLngMapper implements Mapper<LatLng, com.mapbox.mapboxsdk.geometry.LatLng> {

	@Override
	public com.mapbox.mapboxsdk.geometry.LatLng map(LatLng input) {
		return new com.mapbox.mapboxsdk.geometry.LatLng(
				input.latitude,
				input.longitude
		);
	}

}
