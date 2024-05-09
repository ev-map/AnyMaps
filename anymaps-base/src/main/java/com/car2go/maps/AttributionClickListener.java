/*
 * Copyright (c) 2017 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps;

import java.util.List;
import java.util.Objects;

public interface AttributionClickListener {

	void onClick(List<Attribution> attributions);

	public class Attribution {
		private final String title;
		private final String url;

		public Attribution(String title, String url) {
			this.title = title;
			this.url = url;
		}

		public String getTitle() {
			return this.title;
		}

		public String getUrl() {
			return this.url;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Attribution that = (Attribution) o;
			return Objects.equals(title, that.title) && Objects.equals(url, that.url);
		}

		@Override
		public int hashCode() {
			return Objects.hash(title, url);
		}
	}

}
