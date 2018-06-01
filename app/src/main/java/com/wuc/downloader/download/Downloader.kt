package com.wuc.downloader.download

import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL

object Downloader {

    fun downloadByUrlConnect(url: String, desDir: File, l: DownloadListener) {
        val conn = URL(url).openConnection();
        conn.doInput = true;
//        conn.connectTimeout = 30 * 1000;
        val fileLength = conn.contentLength;

        if (!desDir.exists() || desDir.isFile) {
            desDir.mkdirs();
        }
        val fileName = url.substring(url.lastIndexOf(File.separatorChar) + 1);
        val desFile = File(desDir, fileName);

        var bis: BufferedInputStream? = null;
        var fos: FileOutputStream? = null;
        try {
            bis = BufferedInputStream(conn.getInputStream());
            fos = FileOutputStream(desFile);
            var bytesCopied: Long = 0;
            val buffer = ByteArray(8 * 1024);
            var bytes = bis.read(buffer);
            while (bytes >= 0) {
                fos.write(buffer, 0, bytes);
                bytesCopied += bytes;
                l.onDownloading(fileLength.toLong(), bytesCopied)
                bytes = bis.read(buffer);
            }
        } catch (e: Exception) {
            l.onFailure(e);
        } finally {
            fos?.close();
            bis?.close();
        }
        l.onSuccess(desFile);
    }

}