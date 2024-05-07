/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.maplibre.adapter;

import android.content.Context;

import com.car2go.maps.maplibre.BitmapDescriptorFactory;
import com.car2go.maps.maplibre.adapter.factory.Mapper;
import com.car2go.maps.maplibre.adapter.factory.anymap.CameraPositionMapper;
import com.car2go.maps.maplibre.adapter.factory.anymap.CircleMapper;
import com.car2go.maps.maplibre.adapter.factory.anymap.LatLngBoundsMapper;
import com.car2go.maps.maplibre.adapter.factory.anymap.LatLngMapper;
import com.car2go.maps.maplibre.adapter.factory.anymap.PolygonMapper;
import com.car2go.maps.maplibre.adapter.factory.anymap.PolylineMapper;
import com.car2go.maps.maplibre.adapter.factory.anymap.ProjectionMapper;
import com.car2go.maps.maplibre.adapter.factory.anymap.UiSettingsMapper;
import com.car2go.maps.maplibre.adapter.factory.anymap.VisibleRegionMapper;
import com.car2go.maps.maplibre.adapter.factory.mapbox.CircleOptionsMapper;
import com.car2go.maps.maplibre.adapter.factory.mapbox.MarkerOptionsMapper;
import com.car2go.maps.maplibre.adapter.factory.mapbox.PolygonOptionsMapper;
import com.car2go.maps.maplibre.adapter.factory.mapbox.PolylineOptionsMapper;
import com.car2go.maps.model.CircleOptions;
import com.car2go.maps.model.MarkerOptions;
import com.car2go.maps.model.PolygonOptions;
import com.car2go.maps.model.PolylineOptions;

import org.maplibre.android.camera.CameraPosition;
import org.maplibre.android.geometry.LatLng;
import org.maplibre.android.geometry.LatLngBounds;
import org.maplibre.android.geometry.VisibleRegion;
import org.maplibre.android.maps.MapLibreMap;
import org.maplibre.android.maps.Projection;
import org.maplibre.android.maps.UiSettings;
import org.maplibre.android.plugins.annotation.Circle;
import org.maplibre.android.plugins.annotation.Fill;
import org.maplibre.android.plugins.annotation.Line;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * Utility for adapting Google entities to AnyMap and vice versa.
 */
public class AnyMapAdapter {

	private final HashMap<Class<?>, Mapper> mappers = new HashMap<>();
	public final Context context;
	public final BitmapDescriptorFactory bitmapDescriptorFactory;
	private MapLibreMap map;
	public DrawableComponentFactory drawableComponentFactory;

	public AnyMapAdapter(Context context, BitmapDescriptorFactory bitmapDescriptorFactory, MapLibreMap map) {
		this.context = context;
		this.bitmapDescriptorFactory = bitmapDescriptorFactory;
		this.map = map;
		registerMapboxToAnyMapMappers();
		registerAnyMapToMapLibreMappers();
	}

	private void registerMapboxToAnyMapMappers() {
		registerMapper(
				LatLng.class,
				new LatLngMapper()
		);
		registerMapper(
				LatLngBounds.class,
				new LatLngBoundsMapper(this)
		);
		registerMapper(
				CameraPosition.class,
				new CameraPositionMapper(this)
		);
		registerMapper(
				Projection.class,
				new ProjectionMapper(this)
		);
		registerMapper(
				VisibleRegion.class,
				new VisibleRegionMapper(this)
		);
		registerMapper(
				UiSettings.class,
				new UiSettingsMapper()
		);
		registerMapper(
				Circle.class,
				new CircleMapper(this)
		);
		registerMapper(
				Fill.class,
				new PolygonMapper(this)
		);
		registerMapper(
				Line.class,
				new PolylineMapper(this)
		);
	}

	private void registerAnyMapToMapLibreMappers() {
		registerMapper(
				com.car2go.maps.model.LatLng.class,
				new com.car2go.maps.maplibre.adapter.factory.mapbox.LatLngMapper()
		);
		registerMapper(
				com.car2go.maps.model.LatLngBounds.class,
				new com.car2go.maps.maplibre.adapter.factory.mapbox.LatLngBoundsMapper(this)
		);
		registerMapper(
				MarkerOptions.class,
				new MarkerOptionsMapper(this)
		);
		registerMapper(
				CircleOptions.class,
				new CircleOptionsMapper(this)
		);
		registerMapper(
				PolygonOptions.class,
				new PolygonOptionsMapper(this)
		);
		registerMapper(
				PolylineOptions.class,
				new PolylineOptionsMapper(this)
		);
	}

	/**
	 * Registers mapper which will map instances of input classes into some other type. Output
	 * type is defined by mapper.
	 */
	public <I> void registerMapper(Class<? extends I> inputClass, Mapper<I, ?> mapper) {
		if (mappers.containsKey(inputClass)) {
			throw new IllegalStateException(
					"Mapper for class " + inputClass
							+ " was already registered: " + mappers.get(inputClass)
			);
		}

		mappers.put(inputClass, mapper);
	}

	/**
	 * Adapts input object to it's respective alternative. That is, adapts Google entities to
	 * AnyMap entities and vice versa. If input is {@code null}, outputs {@code null}.
	 *
	 * @param object object to adapt
	 * @return adapted object
	 */
	public <I, O> O map(I object) {
		if (object == null) {
			return null;
		}

		Mapper<I, O> mapper = findMapper(object.getClass());

		return mapper.map(object);
	}

	/**
	 * Convenience for {@link #map(Object)} which works on a {@link List} of objects.
	 *
	 * @param type type of input objects being adapted
	 * @param input list of objects being adapted
	 * @return list of adapted objects
	 */
	public <I, O> List<O> mapList(Class<? extends I> type, List<I> input) {
		Mapper<I, O> mapper = findMapper(type);

		List<O> result = new ArrayList<>(input.size());

		for (I inputItem : input) {
			result.add(
					mapper.map(inputItem)
			);
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@NonNull
	private <I, O> Mapper<I, O> findMapper(Class<?> type) {
		// We have to do unchecked cast since we don't know the exact type of adapter in our map
		Mapper<I, O> mapper = mappers.get(type);
		if (mapper == null) {
			throw new IllegalStateException("No mapper for " + type);
		}
		return mapper;
	}

}
