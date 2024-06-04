/*
 * Copyright (c) 2020 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Fragment containing an {@link AnyMap}. Can be used in the same fashion as Google Maps'
 * MapFragment and automatically takes care of the map lifecycle. It
 */
public class MapFragment extends Fragment {
	private MapContainerView map;
	public static final String GOOGLE = "com.car2go.maps.google";
	public static final String BAIDU = "com.car2go.maps.baidu";
	public static final String OSM = "com.car2go.maps.osm";
	public static final String MAPLIBRE = "com.car2go.maps.maplibre";

	private String[] priority = {GOOGLE, BAIDU, OSM, MAPLIBRE};
	private Set<AnyMap.Feature> supportedFeatures = new HashSet<>();

	private Queue<OnMapReadyCallback> waitingCallbacks = new LinkedList<>();

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (map == null) {
			map = createMap();
		}
		map.onCreate(savedInstanceState);

		while (!waitingCallbacks.isEmpty()) {
			// handle getMapAsync calls done before onCreate was called
			map.getMapAsync(waitingCallbacks.remove());
		}
		return map;
	}

	public void getMapAsync(@NonNull OnMapReadyCallback callback) {
		if (map != null) {
			map.getMapAsync(callback);
		} else {
			// onCreate was not called yet, add callback to queue
			waitingCallbacks.add(callback);
		}
	}

	public Set<AnyMap.Feature> getSupportedFeatures() {
		return supportedFeatures;
	}

	/**
	 * Sets the priority order in which to use the AnyMap backends. Supported backends are:
	 * <p><ul>
	 * <li>Google Maps ({@link #GOOGLE})
	 * <li>Baidu Maps ({@link #BAIDU})
	 * <li>OpenStreetMap (OSMDroid) ({@link #OSM})
	 * <li>OpenStreetMap (MapLibre) ({@link #MAPLIBRE})
	 * </ul><p>
	 * Only backends for which the corresponding AnyMaps library (e.g. anymaps-google) is available
	 * will be used.
	 *
	 * @param priority Array of backend identifiers
	 */
	public void setPriority(String[] priority) {
		if (map != null) {
			throw new IllegalStateException("setPriority has to be called before the " +
					"MapFragment's onCreate() is called");
		}

		this.priority = priority;
	}

	/**
	 * Gets the current map backend priority.
	 *
	 * @return priority Array of backend identifiers
	 */
	public String[] getPriority() {
		return this.priority;
	}

	private MapContainerView createMap() {
		for (String name : priority) {
			Class<MapsConfiguration> clazz = getConfigClass(name);
			if (clazz != null) {
				try {
					MapsConfiguration config = (MapsConfiguration) clazz.getMethod("getInstance").invoke(null);
					config.initialize(requireContext());
					supportedFeatures = config.getSupportedFeatures();

					Class<MapContainerView> mapClass = getMapClass(name);
					return mapClass.getConstructor(Context.class).newInstance(getContext());
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

	private Class<MapContainerView> getMapClass(String pkg) {
		try {
			return (Class<MapContainerView>) Class.forName(pkg + ".MapView");
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	private Class<MapsConfiguration> getConfigClass(String pkg) {
		try {
			return (Class<MapsConfiguration>) Class.forName(pkg + ".MapsConfiguration");
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (map != null) {
			map.onResume();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (map != null) {
			map.onPause();
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		if (map != null) {
			map.onStart();
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		if (map != null) {
			map.onStop();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (map != null) {
			map.onDestroy();
		}
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		if (map != null) {
			map.onLowMemory();
		}
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		if (map != null) {
			map.onSaveInstanceState(outState);
		}
	}
}
