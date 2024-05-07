/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.maplibre.adapter;

import com.car2go.maps.model.LatLng;
import com.car2go.maps.model.Polygon;

import org.maplibre.android.plugins.annotation.Fill;
import org.maplibre.android.plugins.annotation.FillManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapts Google Polygon to AnyMap Polygon
 */
public class PolygonAdapter implements Polygon {

	private final org.maplibre.android.plugins.annotation.Fill polygon;
	private final FillManager manager;
	private final AnyMapAdapter anyMapAdapter;

	public PolygonAdapter(Fill polygon, FillManager manager, AnyMapAdapter anyMapAdapter) {
		this.polygon = polygon;
		this.manager = manager;
		this.anyMapAdapter = anyMapAdapter;
	}

	@Override
	public void setHoles(List<List<LatLng>> holes) {
		List<List<org.maplibre.android.geometry.LatLng>> lst = mapHoles(holes);
		lst.add(0, polygon.getLatLngs().get(0));
		polygon.setLatLngs(lst);
	}

	@Override
	public List<LatLng> getPoints() {
		return anyMapAdapter.mapList(org.maplibre.android.geometry.LatLng.class, polygon.getLatLngs().get(0));
	}

	private List<List<org.maplibre.android.geometry.LatLng>> mapHoles(List<List<LatLng>> holes) {
		ArrayList<List<org.maplibre.android.geometry.LatLng>> result = new ArrayList<>();

		for (List<LatLng> hole : holes) {
			List<org.maplibre.android.geometry.LatLng> mapboxHole = anyMapAdapter.mapList(LatLng.class, hole);

			result.add(mapboxHole);
		}

		return result;
	}

	@Override
	public void setVisible(boolean visible) {
		polygon.setFillOpacity(visible ? 1f : 0f);
	}

	@Override
	public void remove() {
		manager.delete(polygon);
	}

}
