package view

import tornadofx.*

class LoadingView : View("Loading") {
    override val root = stackpane {
        progressindicator {
            maxWidth = 100.0
            maxHeight = 100.0
        }
    }
}
