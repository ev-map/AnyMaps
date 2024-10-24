/*
 * Copyright (c) 2017 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps;

/**
 * Mimics Google UiSettings
 */
public interface UiSettings {

	void setAllGesturesEnabled(boolean enabled);

	void setTiltGesturesEnabled(boolean enabled);

	void setRotateGesturesEnabled(boolean enabled);

	void setMyLocationButtonEnabled(boolean enabled);

	void setMapToolbarEnabled(boolean enabled);

	void setIndoorLevelPickerEnabled(boolean enabled);

}
