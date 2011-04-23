package com.softwaremosaic.river;

/**
 *
 */
public enum ScanDirection {
    ASC, DESC;

    public boolean isAscending() {
        return this == ASC;
    }

    public boolean isDescending() {
        return this == DESC;
    }
}
