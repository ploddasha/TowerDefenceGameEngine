package viewModel.scope

import tornadofx.*
import tornadofx.Scope
import viewModel.MusicController

class MusicScope : Scope() {
    val musicController: MusicController = MusicController()
}