package fi.metropolia.deliverytracker.view

import fi.metropolia.deliverytracker.model.Route

interface DirectionFinderListener {
    fun onDirectionFinderStart()
    fun onDirectionFinderSuccess(routeList: List<Route>)
}