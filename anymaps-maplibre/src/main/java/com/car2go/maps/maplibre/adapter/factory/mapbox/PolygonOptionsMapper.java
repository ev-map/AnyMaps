/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.maplibre.adapter.factory.mapbox;

import com.car2go.maps.maplibre.adapter.AnyMapAdapter;
import com.car2go.maps.maplibre.adapter.ColorUtils;
import com.car2go.maps.maplibre.adapter.factory.Mapper;
import com.car2go.maps.model.PolygonOptions;

import org.maplibre.android.geometry.LatLng;
import org.maplibre.android.plugins.annotation.FillOptions;

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
