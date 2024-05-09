/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.maplibre;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.car2go.maps.AnyMap;
import com.car2go.maps.MapContainerView;
import com.car2go.maps.OnMapReadyCallback;
import com.car2go.maps.maplibre.adapter.MapLibreMapAdapter;

import org.maplibre.android.maps.MapLibreMap;
import org.maplibre.android.maps.MapLibreMapOptions;
import org.maplibre.android.maps.Style;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

/**
 * @see com.car2go.maps.MapContainerView
 */
public class MapView extends MapContainerView {

	private org.maplibre.android.maps.MapView mapView;

	private AnyMap map;

	public MapView(Context context) {
		super(context);

		initView(context, null);
	}

	public MapView(Context context, AttributeSet attrs) {
		super(context, attrs);

		initView(context, attrs);
	}

	private void initView(Context context, AttributeSet attrs) {
		MapLibreMapOptions options = MapLibreMapOptions.createFromAttributes(context, attrs);

		TypedValue typedValue = new TypedValue();
		context.getTheme().resolveAttribute(android.R.attr.colorBackground, typedValue, true);
		int colorBackground = ContextCompat.getColor(context, typedValue.resourceId);

		options.foregroundLoadColor(colorBackground);
		mapView = new org.maplibre.android.maps.MapView(context, options);

		addView(mapView);
	}

	@Override
	public void getMapAsync(final OnMapReadyCallback callback) {
		if (map != null) {
			callback.onMapReady(map);
			return;
		}

		mapView.getMapAsync(new org.maplibre.android.maps.OnMapReadyCallback() {
			@Override
			public void onMapReady(@NonNull MapLibreMap MapLibreMap) {
				if (map == null) {
					final MapLibreMapAdapter map = new MapLibreMapAdapter(MapLibreMap, mapView, getContext());
					map.callback = new Style.OnStyleLoaded() {
						@Override
						public void onStyleLoaded(@NonNull Style style) {
							MapView.this.map = map;
							callback.onMapReady(map);
						}
					};
				}
			}
		});
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		mapView.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		mapView.onResume();
	}

	@Override
	public void onPause() {
		mapView.onPause();
	}

	@Override
	public void onDestroy() {
		if (map != null) {
			map.setMyLocationEnabled(false);
		}

		mapView.onDestroy();
	}

	@Override
	public void onLowMemory() {
		mapView.onLowMemory();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		mapView.onSaveInstanceState(outState);
	}

}