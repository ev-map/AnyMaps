/*
 * Copyright (c) 2024 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class MapFactory {
	public static final String GOOGLE = "com.car2go.maps.google";
	public static final String BAIDU = "com.car2go.maps.baidu";
	public static final String OSM = "com.car2go.maps.osm";
	public static final String MAPLIBRE = "com.car2go.maps.maplibre";

	public static class Result {
		public final MapContainerView view;
		public final Set<AnyMap.Feature> supportedFeatures;

		public Result(MapContainerView view, Set<AnyMap.Feature> supportedFeatures) {
			this.view = view;
			this.supportedFeatures = supportedFeatures;
		}
	}

	public static Result createMap(Context context, String[] priority) {
		for (String name : priority) {
			Class<MapsConfiguration> clazz = getConfigClass(name);
			if (clazz != null) {
				try {
					MapsConfiguration config = (MapsConfiguration) clazz.getMethod("getInstance").invoke(null);
					config.initialize(context);
					Set<AnyMap.Feature> supportedFeatures = config.getSupportedFeatures();

					Class<MapContainerView> mapClass = getMapClass(name);
					MapContainerView view = mapClass.getConstructor(Context.class).newInstance(context);
					return new Result(view, supportedFeatures);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				} catch (java.lang.InstantiationException e) {
					throw new RuntimeException(e);
				} catch (NoSuchMethodException e) {
					throw new RuntimeException(e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return null;
	}

	private static Class<MapContainerView> getMapClass(String pkg) {
		try {
			return (Class<MapContainerView>) Class.forName(pkg + ".MapView");
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	private static Class<MapsConfiguration> getConfigClass(String pkg) {
		try {
			return (Class<MapsConfiguration>) Class.forName(pkg + ".MapsConfiguration");
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
}
