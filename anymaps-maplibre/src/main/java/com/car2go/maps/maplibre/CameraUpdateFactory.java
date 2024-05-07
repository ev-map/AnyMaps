/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.maplibre;

import android.graphics.Point;

import com.car2go.maps.CameraUpdate;
import com.car2go.maps.maplibre.adapter.AnyMapAdapter;
import com.car2go.maps.maplibre.adapter.CameraUpdateAdapter;
import com.car2go.maps.maplibre.adapter.ScrollByCameraUpdateAdapter;
import com.car2go.maps.model.LatLng;
import com.car2go.maps.model.LatLngBounds;

/**
 * Creates {@link CameraUpdate} objects which can be used to update map camera
 */
public class CameraUpdateFactory implements com.car2go.maps.CameraUpdateFactory {

	private final AnyMapAdapter anyMapAdapter;

	public CameraUpdateFactory(AnyMapAdapter anyMapAdapter) {
		this.anyMapAdapter = anyMapAdapter;
	}

	@Override
	public CameraUpdate newLatLngZoom(LatLng latLng, float zoomLevel) {
		org.maplibre.android.geometry.LatLng googleLatLng = anyMapAdapter.map(latLng);

		return new CameraUpdateAdapter(
				org.maplibre.android.camera.CameraUpdateFactory.newLatLngZoom(
						googleLatLng,
						zoomLevel - 1  // mapbox zoom levels are shifted by one
				)
		);
	}

	@Override
	public CameraUpdate newLatLng(LatLng latLng) {
		org.maplibre.android.geometry.LatLng googleLatLng = anyMapAdapter.map(latLng);

		return new CameraUpdateAdapter(
				org.maplibre.android.camera.CameraUpdateFactory.newLatLng(
						googleLatLng
				)
		);
	}

	@Override
	public CameraUpdate newLatLngBounds(LatLngBounds bounds, int padding) {
		org.maplibre.android.geometry.LatLngBounds googleBounds = anyMapAdapter.map(bounds);

		return new CameraUpdateAdapter(
				org.maplibre.android.camera.CameraUpdateFactory.newLatLngBounds(
						googleBounds,
						padding
				)
		);
	}

	@Override
	public CameraUpdate zoomTo(float zoomLevel) {
		return new CameraUpdateAdapter(
				// mapbox zoom levels are shifted by one
				org.maplibre.android.camera.CameraUpdateFactory.zoomTo(zoomLevel - 1)
		);
	}

	@Override
	public CameraUpdate scrollBy(float distanceX, float distanceY) {
		return new ScrollByCameraUpdateAdapter(distanceX, distanceY);
	}

	@Override
	public CameraUpdate zoomBy(float amount) {
		return new CameraUpdateAdapter(org.maplibre.android.camera.CameraUpdateFactory.zoomBy(amount));
	}

	@Override
	public CameraUpdate zoomBy(float amount, Point focus) {
		return new CameraUpdateAdapter(org.maplibre.android.camera.CameraUpdateFactory.zoomBy(amount, focus));
	}

}
