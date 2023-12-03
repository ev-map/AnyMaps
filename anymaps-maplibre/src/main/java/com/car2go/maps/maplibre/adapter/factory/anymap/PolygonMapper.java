/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.maplibre.adapter.factory.anymap;

import com.car2go.maps.maplibre.adapter.AnyMapAdapter;
import com.car2go.maps.maplibre.adapter.PolygonAdapter;
import com.car2go.maps.maplibre.adapter.factory.Mapper;
import com.mapbox.mapboxsdk.plugins.annotation.Fill;

/**
 * Maps Google Polygon to AnyMap Polygon
 */
public class PolygonMapper implements Mapper<Fill, com.car2go.maps.model.Polygon> {

	private final AnyMapAdapter anyMapAdapter;

	public PolygonMapper(AnyMapAdapter anyMapAdapter) {
		this.anyMapAdapter = anyMapAdapter;
	}

	@Override
	public com.car2go.maps.model.Polygon map(Fill input) {
		return new PolygonAdapter(input, anyMapAdapter.drawableComponentFactory.fillManager, anyMapAdapter);
	}

}
