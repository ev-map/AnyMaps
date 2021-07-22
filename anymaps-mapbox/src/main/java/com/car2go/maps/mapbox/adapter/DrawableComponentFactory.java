/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.mapbox.adapter;

import com.car2go.maps.model.Circle;
import com.car2go.maps.model.CircleOptions;
import com.car2go.maps.model.Marker;
import com.car2go.maps.model.MarkerOptions;
import com.car2go.maps.model.Polygon;
import com.car2go.maps.model.PolygonOptions;
import com.car2go.maps.model.Polyline;
import com.car2go.maps.model.PolylineOptions;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.CircleManager;
import com.mapbox.mapboxsdk.plugins.annotation.Fill;
import com.mapbox.mapboxsdk.plugins.annotation.FillManager;
import com.mapbox.mapboxsdk.plugins.annotation.FillOptions;
import com.mapbox.mapboxsdk.plugins.annotation.Line;
import com.mapbox.mapboxsdk.plugins.annotation.LineManager;
import com.mapbox.mapboxsdk.plugins.annotation.LineOptions;

/**
 * Factory of {@link com.car2go.maps.model.DrawableComponent}. Created components are bound
 * to associated {@link MapboxMap}
 */
public class DrawableComponentFactory {

	private final MapboxMap map;
	private final AnyMapAdapter anyMapAdapter;
	public final CircleManager circleManager;
	public final LineManager lineManager;
	public final FillManager fillManager;

	public DrawableComponentFactory(AnyMapAdapter anyMapAdapter, MapboxMap map, MapView mapView, Style style) {
		this.anyMapAdapter = anyMapAdapter;
		this.map = map;

		circleManager = new CircleManager(mapView, map, style);
		lineManager = new LineManager(mapView, map, style);
		fillManager = new FillManager(mapView, map, style);
	}

	/**
	 * Adds marker to the map
	 *
	 * @return added {@link Marker} which is bound to the map.
	 */
	public Marker addMarker(MarkerOptions options) {
		com.mapbox.mapboxsdk.annotations.MarkerOptions mapboxOptions = anyMapAdapter.map(options);
		return anyMapAdapter.map(map.addMarker(mapboxOptions));
	}

	/**
	 * Adds circle to the map.
	 *
	 * @return added {@link Circle} which is bound to the map
	 */
	public Circle addCircle(CircleOptions options) {
		com.mapbox.mapboxsdk.plugins.annotation.CircleOptions mapboxOptions = anyMapAdapter.map(options);
		com.mapbox.mapboxsdk.plugins.annotation.Circle circle = circleManager.create(mapboxOptions);
		return anyMapAdapter.map(circle);
	}

	/**
	 * Adds polygon to the map.
	 *
	 * @return added {@link Polygon} which is bound to the map
	 */
	public Polygon addPolygon(PolygonOptions options) {
		FillOptions mapboxOptions = anyMapAdapter.map(options);
		Fill polygon = fillManager.create(mapboxOptions);
		return anyMapAdapter.map(polygon);
	}

	/**
	 * Adds polyline to the map.
	 *
	 * @return added {@link Polyline} which is bound to the map
	 */
	public Polyline addPolyline(PolylineOptions options) {
		LineOptions mapboxOptions = anyMapAdapter.map(options);
		Line line = lineManager.create(mapboxOptions);
		return anyMapAdapter.map(line);
	}
}
