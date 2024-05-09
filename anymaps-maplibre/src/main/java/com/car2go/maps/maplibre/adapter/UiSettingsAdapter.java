/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.maplibre.adapter;

import com.car2go.maps.UiSettings;

/**
 * Adapts Google Map UiSettings to AnyMap UiSettings
 */
public class UiSettingsAdapter implements UiSettings {

	private final com.mapbox.mapboxsdk.maps.UiSettings uiSettings;

	public UiSettingsAdapter(com.mapbox.mapboxsdk.maps.UiSettings uiSettings) {
		this.uiSettings = uiSettings;
	}

	@Override
	public void setAllGesturesEnabled(boolean enabled) {
		uiSettings.setAllGesturesEnabled(enabled);
	}

	@Override
	public void setTiltGesturesEnabled(boolean enabled) {
		uiSettings.setTiltGesturesEnabled(enabled);
	}

	@Override
	public void setRotateGesturesEnabled(boolean enabled) {
		uiSettings.setRotateGesturesEnabled(enabled);
	}

	@Override
	public void setMyLocationButtonEnabled(boolean enabled) {
		// do nothing
	}

	@Override
	public void setMapToolbarEnabled(boolean enabled) {
		// do nothing
	}

	@Override
	public void setIndoorLevelPickerEnabled(boolean enabled) {
		// do nothing
	}
}
