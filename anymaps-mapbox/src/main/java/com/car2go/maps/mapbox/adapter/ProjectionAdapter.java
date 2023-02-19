/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.mapbox.adapter;

import android.graphics.Point;
import android.graphics.PointF;

import com.car2go.maps.Projection;
import com.car2go.maps.model.LatLng;
import com.car2go.maps.model.VisibleRegion;

/**
 * Adapts Google Map projection to AnyMap projection
 */
public class ProjectionAdapter implements Projection {

	private final com.mapbox.mapboxsdk.maps.Projection projection;
	private final AnyMapAdapter anyMapAdapter;

	public ProjectionAdapter(com.mapbox.mapboxsdk.maps.Projection projection, AnyMapAdapter anyMapAdapter) {
		this.projection = projection;
		this.anyMapAdapter = anyMapAdapter;
	}

	@Override
	public VisibleRegion getVisibleRegion() {
		return anyMapAdapter.map(projection.getVisibleRegion());
	}

	@Override
	public LatLng fromScreenLocation(Point point) {
		return anyMapAdapter.map(projection.fromScreenLocation(new PointF(point.x, point.y)));
	}

	@Override
	public Point toScreenLocation(LatLng latLng) {
		com.mapbox.mapboxsdk.geometry.LatLng mapboxLatLng = anyMapAdapter.map(latLng);
		PointF result = projection.toScreenLocation(mapboxLatLng);
		return new Point(Math.round(result.x), Math.round(result.y));
	}

}
