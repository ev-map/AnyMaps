/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.mapbox.adapter.factory.mapbox;

import com.car2go.maps.mapbox.adapter.AnyMapAdapter;
import com.car2go.maps.mapbox.adapter.ColorUtils;
import com.car2go.maps.mapbox.adapter.factory.Mapper;
import com.car2go.maps.model.PolygonOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.plugins.annotation.FillOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Maps AnyMap PolygonOptions to Google PolygonOptions
 */
public class PolygonOptionsMapper implements Mapper<PolygonOptions, FillOptions> {

	private final AnyMapAdapter anyMapAdapter;

	public PolygonOptionsMapper(AnyMapAdapter anyMapAdapter) {

		this.anyMapAdapter = anyMapAdapter;
	}

	@Override
	public FillOptions map(PolygonOptions input) {
		List<LatLng> points = anyMapAdapter.mapList(com.car2go.maps.model.LatLng.class, input.getPoints());
		List<List<LatLng>> latLngs = new ArrayList<>();
		latLngs.add(points);

		return new FillOptions()
				.withFillColor(ColorUtils.toHex(input.getFillColor()))
				.withFillOutlineColor(ColorUtils.toHex(input.getStrokeColor()))
				//.strokeWidth(input.getStrokeWidth())
				.withLatLngs(latLngs);
	}

}
