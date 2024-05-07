/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.maplibre.adapter.factory.mapbox;

import com.car2go.maps.maplibre.adapter.AnyMapAdapter;
import com.car2go.maps.maplibre.adapter.ColorUtils;
import com.car2go.maps.maplibre.adapter.factory.Mapper;
import com.car2go.maps.model.CircleOptions;

import org.maplibre.android.geometry.LatLng;

;

/**
 * Maps AnyMap CircleOptions to Google CircleOptions
 */
public class CircleOptionsMapper implements Mapper<CircleOptions, org.maplibre.android.plugins.annotation.CircleOptions> {

	private final AnyMapAdapter anyMapAdapter;

	public CircleOptionsMapper(AnyMapAdapter anyMapAdapter) {
		this.anyMapAdapter = anyMapAdapter;
	}

	@Override
	public org.maplibre.android.plugins.annotation.CircleOptions map(CircleOptions input) {
		LatLng center = anyMapAdapter.map(input.getCenter());

		return new org.maplibre.android.plugins.annotation.CircleOptions()
				.withLatLng(center)
				.withCircleRadius((float) input.getRadius())
				.withCircleColor(ColorUtils.toHex(input.getFillColor()))
				.withCircleStrokeColor(ColorUtils.toHex(input.getStrokeColor()))
				.withCircleStrokeWidth(input.getStrokeWidth());
	}

}
