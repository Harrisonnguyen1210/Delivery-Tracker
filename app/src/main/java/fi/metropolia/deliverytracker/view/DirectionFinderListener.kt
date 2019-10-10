package fi.metropolia.deliverytracker.view

import fi.metropolia.deliverytracker.model.Route

/**
 * Direction interface to start and finish direction
 */
interface DirectionFinderListener {
    fun onDirectionFinderStart()
    fun onDirectionFinderSuccess(routeList: List<Route>)
}