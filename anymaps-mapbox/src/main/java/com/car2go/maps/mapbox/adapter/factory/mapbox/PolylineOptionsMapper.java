/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.mapbox.adapter.factory.mapbox;

import com.car2go.maps.mapbox.adapter.AnyMapAdapter;
import com.car2go.maps.mapbox.adapter.ColorUtils;
import com.car2go.maps.mapbox.adapter.factory.Mapper;
import com.car2go.maps.model.PolylineOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.List;

/**
 * Maps AnyMap PolylineOptions to Google PolylineOptions
 */
public class PolylineOptionsMapper implements Mapper<PolylineOptions, com.mapbox.mapboxsdk.plugins.annotation.LineOptions> {

	private final AnyMapAdapter anyMapAdapter;

	public PolylineOptionsMapper(AnyMapAdapter anyMapAdapter) {
		this.anyMapAdapter = anyMapAdapter;
	}

	@Override
	public com.mapbox.mapboxsdk.plugins.annotation.LineOptions map(PolylineOptions input) {
		List<LatLng> points = anyMapAdapter.mapList(com.car2go.maps.model.LatLng.class, input.getPoints());

		return new com.mapbox.mapboxsdk.plugins.annotation.LineOptions()
				.withLineColor(ColorUtils.toHex(input.getColor()))
				.withLineWidth(input.getWidth())
				.withLatLngs(points);
	}

}
