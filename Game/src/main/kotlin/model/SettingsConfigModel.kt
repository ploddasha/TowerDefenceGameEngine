package model

class SettingsConfigModel {
    // Мб в будущем придумаем еще настройки

    // Имя в настройки не стал добавлять.
    // Думаю, что нужно просто в конце игры,
    // при сохранении результата, спрашивать имя для рейтинга
    private var volume: Int = 0

    fun setVolume(volume: Int) {
        this.volume = volume
        return
    }

    fun getVolume(): Int {
        return this.volume
    }
}