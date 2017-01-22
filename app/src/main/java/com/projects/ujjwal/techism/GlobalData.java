package com.projects.ujjwal.techism;

/**
 * Created by ujjwal on 22/1/17.
 */public abstract class GlobalData {

	private static final Object lock = new Object();
	private static float bearing = 0;

	/**
	 * Set the bearing.
	 *
	 * @param bearing int representing the current bearing.
	 */
	public static void setBearing(float bearing) {
		synchronized (lock) {
			GlobalData.bearing = bearing;
		}
	}

	/**
	 * Get the bearing.
	 *
	 * @return int representing the bearing.
	 */
	public static float getBearing() {
		synchronized (lock) {
			return bearing;
		}

	}
}