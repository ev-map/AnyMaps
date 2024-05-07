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

import org.maplibre.android.maps.MapLibreMap;
import org.maplibre.android.maps.MapView;
import org.maplibre.android.maps.Style;
import org.maplibre.android.plugins.annotation.CircleManager;
import org.maplibre.android.plugins.annotation.Fill;
import org.maplibre.android.plugins.annotation.FillManager;
import org.maplibre.android.plugins.annotation.FillOptions;
import org.maplibre.android.plugins.annotation.Line;
import org.maplibre.android.plugins.annotation.LineManager;
import org.maplibre.android.plugins.annotation.LineOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory of {@link com.car2go.maps.model.DrawableComponent}. Created components are bound
 * to associated {@link MapLibreMap}
 */
public class DrawableComponentFactory {

	private final MapLibreMap map;
	private final AnyMapAdapter anyMapAdapter;
	public final CircleManager circleManager;
	public final LineManager lineManager;
	public final FillManager fillManager;
	public final Map<Long, MarkerAdapter> markers;

	public DrawableComponentFactory(AnyMapAdapter anyMapAdapter, MapLibreMap map, MapView mapView, Style style) {
		this.anyMapAdapter = anyMapAdapter;
		this.map = map;

		circleManager = new CircleManager(mapView, map, style);
		lineManager = new LineManager(mapView, map, style);
		fillManager = new FillManager(mapView, map, style);
		markers = new HashMap<>();
	}

	/**
	 * Adds marker to the map
	 *
	 * @return added {@link Marker} which is bound to the map.
	 */
	public Marker addMarker(MarkerOptions options) {
		org.maplibre.android.annotations.MarkerOptions mapboxOptions = anyMapAdapter.map(options);
		org.maplibre.android.annotations.Marker marker = map.addMarker(mapboxOptions);
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
		org.maplibre.android.plugins.annotation.CircleOptions mapboxOptions = anyMapAdapter.map(options);
		org.maplibre.android.plugins.annotation.Circle circle = circleManager.create(mapboxOptions);
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
