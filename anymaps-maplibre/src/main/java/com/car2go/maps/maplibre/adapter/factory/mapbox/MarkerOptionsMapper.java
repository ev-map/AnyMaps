/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.maplibre.adapter.factory.mapbox;

import android.util.DisplayMetrics;

import com.car2go.maps.maplibre.adapter.AnyMapAdapter;
import com.car2go.maps.maplibre.adapter.BitmapDescriptorAdapter;
import com.car2go.maps.maplibre.adapter.factory.Mapper;
import com.car2go.maps.model.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;

/**
 * Maps AnyMap MarkerOptions to Google MarkerOptions
 */
public class MarkerOptionsMapper implements Mapper<MarkerOptions, com.mapbox.mapboxsdk.annotations.MarkerOptions> {

	private final AnyMapAdapter anyMapAdapter;

	public MarkerOptionsMapper(AnyMapAdapter anyMapAdapter) {
		this.anyMapAdapter = anyMapAdapter;
	}

	@Override
	public com.mapbox.mapboxsdk.annotations.MarkerOptions map(MarkerOptions input) {
		LatLng mapboxLatLng = anyMapAdapter.map(input.getPosition());

		DisplayMetrics dm = anyMapAdapter.context.getResources().getDisplayMetrics();
		BitmapDescriptorAdapter icon = (BitmapDescriptorAdapter) input.getIcon();
		return new com.mapbox.mapboxsdk.annotations.MarkerOptions()
				.position(mapboxLatLng)
				.icon(icon.icon);
	}

}
