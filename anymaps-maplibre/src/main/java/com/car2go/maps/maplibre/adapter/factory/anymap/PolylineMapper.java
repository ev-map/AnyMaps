/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.maplibre.adapter.factory.anymap;

import com.car2go.maps.maplibre.adapter.AnyMapAdapter;
import com.car2go.maps.maplibre.adapter.PolylineAdapter;
import com.car2go.maps.maplibre.adapter.factory.Mapper;
import com.mapbox.mapboxsdk.plugins.annotation.Line;

/**
 * Maps Google Polyline to AnyMap Polyline
 */
public class PolylineMapper implements Mapper<Line, com.car2go.maps.model.Polyline> {

	private final AnyMapAdapter anyMapAdapter;

	public PolylineMapper(AnyMapAdapter anyMapAdapter) {
		this.anyMapAdapter = anyMapAdapter;
	}

	@Override
	public com.car2go.maps.model.Polyline map(Line input) {
		return new PolylineAdapter(input, anyMapAdapter.drawableComponentFactory.lineManager);
	}

}
