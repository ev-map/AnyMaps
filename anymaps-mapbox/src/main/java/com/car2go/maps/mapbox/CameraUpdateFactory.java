/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.mapbox;

import android.graphics.Point;

import com.car2go.maps.CameraUpdate;
import com.car2go.maps.mapbox.adapter.AnyMapAdapter;
import com.car2go.maps.mapbox.adapter.CameraUpdateAdapter;
import com.car2go.maps.mapbox.adapter.ScrollByCameraUpdateAdapter;
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
		com.mapbox.mapboxsdk.geometry.LatLng googleLatLng = anyMapAdapter.map(latLng);

		return new CameraUpdateAdapter(
				com.mapbox.mapboxsdk.camera.CameraUpdateFactory.newLatLngZoom(
						googleLatLng,
						zoomLevel - 1  // mapbox zoom levels are shifted by one
				)
		);
	}

	@Override
	public CameraUpdate newLatLng(LatLng latLng) {
		com.mapbox.mapboxsdk.geometry.LatLng googleLatLng = anyMapAdapter.map(latLng);

		return new CameraUpdateAdapter(
				com.mapbox.mapboxsdk.camera.CameraUpdateFactory.newLatLng(
						googleLatLng
				)
		);
	}

	@Override
	public CameraUpdate newLatLngBounds(LatLngBounds bounds, int padding) {
		com.mapbox.mapboxsdk.geometry.LatLngBounds googleBounds = anyMapAdapter.map(bounds);

		return new CameraUpdateAdapter(
				com.mapbox.mapboxsdk.camera.CameraUpdateFactory.newLatLngBounds(
						googleBounds,
						padding
				)
		);
	}

	@Override
	public CameraUpdate zoomTo(float zoomLevel) {
		return new CameraUpdateAdapter(
				// mapbox zoom levels are shifted by one
				com.mapbox.mapboxsdk.camera.CameraUpdateFactory.zoomTo(zoomLevel - 1)
		);
	}

	@Override
	public CameraUpdate scrollBy(float distanceX, float distanceY) {
		return new ScrollByCameraUpdateAdapter(distanceX, distanceY);
	}

	@Override
	public CameraUpdate zoomBy(float amount) {
		return new CameraUpdateAdapter(com.mapbox.mapboxsdk.camera.CameraUpdateFactory.zoomBy(amount));
	}

	@Override
	public CameraUpdate zoomBy(float amount, Point focus) {
		return new CameraUpdateAdapter(com.mapbox.mapboxsdk.camera.CameraUpdateFactory.zoomBy(amount, focus));
	}

}
