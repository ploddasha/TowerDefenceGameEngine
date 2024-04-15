package viewModel

import model.SettingsConfigModel

class SettingsController {
    // Пока в настройках только громкость
    private var settingsConfig = SettingsConfigModel()

    fun changeVolume(newVolume: Int) {
        this.settingsConfig.setVolume(newVolume)
        return
    }
}