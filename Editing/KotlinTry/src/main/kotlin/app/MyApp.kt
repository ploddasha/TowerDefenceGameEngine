import app.Styles
import javafx.stage.Stage
import tornadofx.App
import view.MyView


class MyApp: App(MyView::class, Styles::class) {
    override fun start(stage: Stage) {
        super.start(stage)

        // Задать размеры окна
        stage.minWidth = 800.0
        stage.minHeight = 500.0
        stage.maxWidth = 900.0
        stage.maxHeight = 600.0
    }
}