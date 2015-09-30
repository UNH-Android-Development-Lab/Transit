package edu.unh.cs.android.transit.myapplication.persistence.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Route {

    @DatabaseField
    private String routeTitle;

    @DatabaseField(id = true, unique = true)
    private String routeTag;

    public Route(String routeTitle, String routeTag) {
        this.routeTitle = routeTitle;
        this.routeTag = routeTag;
    }

    public Route() {

    }

    public String getRouteTitle() {
        return routeTitle;
    }

    public void setRouteTitle(String routeTitle) {
        this.routeTitle = routeTitle;
    }

    public String getRouteTag() {
        return routeTag;
    }

    public void setRouteTag(String routeTag) {
        this.routeTag = routeTag;
    }
}
