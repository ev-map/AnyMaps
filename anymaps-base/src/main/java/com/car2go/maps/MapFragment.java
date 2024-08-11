/*
 * Copyright (c) 2020 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.car2go.maps.MapFactory.BAIDU;
import static com.car2go.maps.MapFactory.GOOGLE;
import static com.car2go.maps.MapFactory.MAPLIBRE;
import static com.car2go.maps.MapFactory.OSM;

/**
 * Fragment containing an {@link AnyMap}. Can be used in the same fashion as Google Maps'
 * MapFragment and automatically takes care of the map lifecycle. It
 */
public class MapFragment extends Fragment {
	private MapContainerView map;

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
		MapFactory.Result result = MapFactory.createMap(requireContext(), priority);
		supportedFeatures = result.supportedFeatures;
		return result.view;
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
