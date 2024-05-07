/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.maplibre.adapter.factory.mapbox;

import com.car2go.maps.maplibre.adapter.AnyMapAdapter;
import com.car2go.maps.maplibre.adapter.ColorUtils;
import com.car2go.maps.maplibre.adapter.factory.Mapper;
import com.car2go.maps.model.PolylineOptions;

import org.maplibre.android.geometry.LatLng;

import java.util.List;

/**
 * Maps AnyMap PolylineOptions to Google PolylineOptions
 */
public class PolylineOptionsMapper implements Mapper<PolylineOptions, org.maplibre.android.plugins.annotation.LineOptions> {

	private final AnyMapAdapter anyMapAdapter;

	public PolylineOptionsMapper(AnyMapAdapter anyMapAdapter) {
		this.anyMapAdapter = anyMapAdapter;
	}

	@Override
	public org.maplibre.android.plugins.annotation.LineOptions map(PolylineOptions input) {
		List<LatLng> points = anyMapAdapter.mapList(com.car2go.maps.model.LatLng.class, input.getPoints());

		return new org.maplibre.android.plugins.annotation.LineOptions()
				.withLineColor(ColorUtils.toHex(input.getColor()))
				.withLineWidth(input.getWidth())
				.withLatLngs(points);
	}

}
