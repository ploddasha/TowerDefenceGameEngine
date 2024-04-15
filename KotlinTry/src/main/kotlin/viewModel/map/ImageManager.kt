package viewModel.map

import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

class ImageManager {

    fun copyFileToProject(fileToCopy: File, destinationDirectory: File, newFileName: String? = null) {
        if (!destinationDirectory.exists()) {
            destinationDirectory.mkdirs()
        }

        val destinationFileName = newFileName ?: fileToCopy.name
        val destinationPath = destinationDirectory.toPath().resolve(destinationFileName)

        try {
            Files.copy(fileToCopy.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING)
            println("Файл успешно скопирован в проект: $destinationPath")
        } catch (e: Exception) {
            println("Ошибка при копировании файла: ${e.message}")
        }
    }


}