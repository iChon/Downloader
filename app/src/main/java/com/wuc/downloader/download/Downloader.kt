package com.wuc.downloader.download

import okhttp3.*
import java.io.*
import java.net.URL

object Downloader {

    fun downloadByUrlConnect(url: String, desDir: File, l: DownloadListener) {
        val conn = URL(url).openConnection();
        conn.doInput = true;
        conn.connectTimeout = 30 * 1000;
        val total = conn.contentLength;

        saveFile(url, desDir, conn.getInputStream(), total.toLong(), l)
    }

    fun downloadByOkHttp(url: String, desDir: File, l: DownloadListener) {
        val request = Request.Builder().url(url).build()
        val okHttpClient = OkHttpClient()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()
                val input = body?.byteStream()
                val total = body?.contentLength()
                saveFile(url, desDir, input, total, l)
            }

            override fun onFailure(call: Call?, e: IOException?) {
                l.onFailure(e);
            }
        })
    }

    private fun saveFile(url: String, desDir: File, input: InputStream?, total: Long?, l: DownloadListener) {
        if (!desDir.exists() || desDir.isFile) {
            desDir.mkdirs();
        }
        val fileName = url.substring(url.lastIndexOf(File.separatorChar) + 1);
        val desFile = File(desDir, fileName);

        var bis: BufferedInputStream? = null;
        var fos: FileOutputStream? = null;
        try {
            bis = BufferedInputStream(input);
            fos = FileOutputStream(desFile);
            var bytesCopied: Long = 0;
            val buffer = ByteArray(8 * 1024);
            var bytes = bis.read(buffer);
            while (bytes >= 0) {
                fos.write(buffer, 0, bytes);
                bytesCopied += bytes;
                l.onDownloading(total, bytesCopied)
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