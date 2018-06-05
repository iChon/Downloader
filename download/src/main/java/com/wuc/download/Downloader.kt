package com.wuc.download

import com.wuc.download.engin.IDownload
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream

object Downloader {

    fun download(desDir: File, download: IDownload, l: DownloadListener) {
        if (!desDir.exists() || desDir.isFile) {
            desDir.mkdirs()
        }

        val url = download.getDownloadUrl()
        val fileName = url.substring(url.lastIndexOf(File.separatorChar) + 1)
        val desFile = File(desDir, fileName)

        var bis: BufferedInputStream? = null
        var fos: FileOutputStream? = null
        try {
            bis = BufferedInputStream(download.getInputStream())
            fos = FileOutputStream(desFile)
            var bytesCopied: Long = 0
            val buffer = ByteArray(8 * 1024)
            var bytes = bis.read(buffer)
            while (bytes >= 0) {
                fos.write(buffer, 0, bytes)
                bytesCopied += bytes
                l.onDownloading(download.getTotalSize(), bytesCopied)
                bytes = bis.read(buffer)
            }
        } catch (e: Exception) {
            l.onFailure(e)
        } finally {
            fos?.close()
            bis?.close()
        }
        l.onSuccess(desFile)
    }

}