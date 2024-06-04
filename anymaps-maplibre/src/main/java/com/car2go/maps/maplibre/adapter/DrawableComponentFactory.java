/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.maplibre.adapter;

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

import java.util.HashMap;
import java.util.Map;

/**
 * Factory of {@link com.car2go.maps.model.DrawableComponent}. Created components are bound
 * to associated {@link MapboxMap}
 */
public class DrawableComponentFactory {

	private final MapboxMap map;
	private final MapView mapView;
	private final AnyMapAdapter anyMapAdapter;
	public CircleManager circleManager;
	public LineManager lineManager;
	public FillManager fillManager;
	public final Map<Long, MarkerAdapter> markers;

	public DrawableComponentFactory(AnyMapAdapter anyMapAdapter, MapboxMap map, MapView mapView) {
		this.anyMapAdapter = anyMapAdapter;
		this.map = map;
		this.mapView = mapView;

		markers = new HashMap<>();
	}

	/**
	 * Adds marker to the map
	 *
	 * @return added {@link Marker} which is bound to the map.
	 */
	public Marker addMarker(MarkerOptions options) {
		com.mapbox.mapboxsdk.annotations.MarkerOptions mapboxOptions = anyMapAdapter.map(options);
		com.mapbox.mapboxsdk.annotations.Marker marker = map.addMarker(mapboxOptions);
		MarkerAdapter markerAdapter = new MarkerAdapter(this, marker, map, anyMapAdapter, options.getAnchorU(), options.getAnchorV());
		markers.put(marker.getId(), markerAdapter);
		return markerAdapter;
	}

	/**
	 * Adds circle to the map.
	 *
	 * @return added {@link Circle} which is bound to the map
	 */
	public Circle addCircle(CircleOptions options) {
		if (circleManager == null) throw new IllegalStateException("style has not been loaded yet");

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
		if (fillManager == null) throw new IllegalStateException("style has not been loaded yet");

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
		if (lineManager == null) throw new IllegalStateException("style has not been loaded yet");

		LineOptions mapboxOptions = anyMapAdapter.map(options);
		Line line = lineManager.create(mapboxOptions);
		return anyMapAdapter.map(line);
	}

	public void setStyle(Style style) {
		circleManager = new CircleManager(mapView, map, style);
		lineManager = new LineManager(mapView, map, style);
		fillManager = new FillManager(mapView, map, style);
	}
}
