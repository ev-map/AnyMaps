/*
 * Copyright (c) 2017 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps;

import android.content.DialogInterface;

public interface AttributionClickListener {

	void onClick(String[] attributionTitles, DialogInterface.OnClickListener clickListener);
}
