/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.mapbox.adapter.factory.anymap;

import com.car2go.maps.mapbox.adapter.AnyMapAdapter;
import com.car2go.maps.mapbox.adapter.MarkerAdapter;
import com.car2go.maps.mapbox.adapter.factory.Mapper;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;

/**
 * Maps Google Marker to AnyMap Marker
 */
public class MarkerMapper implements Mapper<com.mapbox.mapboxsdk.annotations.Marker, com.car2go.maps.model.Marker> {
	private final AnyMapAdapter anyMapAdapter;

	public MarkerMapper(AnyMapAdapter anyMapAdapter) {
		this.anyMapAdapter = anyMapAdapter;
	}

	@Override
	public com.car2go.maps.model.Marker map(com.mapbox.mapboxsdk.annotations.Marker input) {
		return new MarkerAdapter(input, anyMapAdapter);
	}

}
