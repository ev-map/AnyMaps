/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.maplibre.adapter.factory.mapbox;

import com.car2go.maps.maplibre.adapter.AnyMapAdapter;
import com.car2go.maps.maplibre.adapter.factory.Mapper;
import com.car2go.maps.model.LatLngBounds;

import org.maplibre.android.geometry.LatLng;

/**
 * Maps AnyMap LatLngBounds to Google LatLngBounds
 */
public class LatLngBoundsMapper implements Mapper<LatLngBounds, org.maplibre.android.geometry.LatLngBounds> {

	private final AnyMapAdapter anyMapAdapter;

	public LatLngBoundsMapper(AnyMapAdapter anyMapAdapter) {
		this.anyMapAdapter = anyMapAdapter;
	}

	@Override
	public org.maplibre.android.geometry.LatLngBounds map(LatLngBounds input) {
		LatLng southWest = anyMapAdapter.map(input.southwest);
		LatLng northEast = anyMapAdapter.map(input.northeast);

		return org.maplibre.android.geometry.LatLngBounds.from(
				northEast.getLatitude(), northEast.getLongitude(),
				southWest.getLatitude(), southWest.getLongitude());
	}

}
