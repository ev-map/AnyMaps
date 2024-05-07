/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.maplibre.adapter.factory.anymap;

import com.car2go.maps.maplibre.adapter.AnyMapAdapter;
import com.car2go.maps.maplibre.adapter.factory.Mapper;
import com.car2go.maps.model.LatLng;

import org.maplibre.android.geometry.LatLngBounds;

/**
 * Maps Google LatLngBounds to AnyMap LatLngBounds
 */
public class LatLngBoundsMapper implements Mapper<LatLngBounds, com.car2go.maps.model.LatLngBounds> {

	private final AnyMapAdapter anyMapAdapter;

	public LatLngBoundsMapper(AnyMapAdapter anyMapAdapter) {
		this.anyMapAdapter = anyMapAdapter;
	}

	@Override
	public com.car2go.maps.model.LatLngBounds map(LatLngBounds input) {
		LatLng southWest = anyMapAdapter.map(input.getSouthWest());
		LatLng northEast = anyMapAdapter.map(input.getNorthEast());

		return new com.car2go.maps.model.LatLngBounds(southWest, northEast);
	}

}
