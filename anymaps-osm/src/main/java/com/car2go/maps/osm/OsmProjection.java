/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.osm;

import android.graphics.Point;

import com.car2go.maps.Projection;
import com.car2go.maps.model.LatLng;
import com.car2go.maps.model.LatLngBounds;
import com.car2go.maps.model.VisibleRegion;
import com.car2go.maps.osm.util.OsmUtils;

/**
 * Adapts OpenStreetMap projection to AnyMap projection
 */
public class OsmProjection implements Projection {

	private final org.osmdroid.views.Projection projection;

	public OsmProjection(org.osmdroid.views.Projection projection) {
		this.projection = projection;
	}

	@Override
	public VisibleRegion getVisibleRegion() {
		return new VisibleRegion(
				new LatLngBounds(
						new LatLng(
								projection.getSouthWest().getLatitude(),
								projection.getSouthWest().getLongitude()
						),
						new LatLng(
								projection.getNorthEast().getLatitude(),
								projection.getNorthEast().getLongitude()
						)
				)
		);
	}

	@Override
	public LatLng fromScreenLocation(Point point) {
		return OsmUtils.toLatLng(projection.fromPixels(point.x, point.y));
	}

	@Override
	public Point toScreenLocation(LatLng latLng) {
		return projection.toPixels(OsmUtils.toGeoPoint(latLng), null);
	}

}
