package com.wuc.downloader.download.engin

import java.io.InputStream
import java.net.URL
import java.net.URLConnection

/**
 *
 * @date 2018/6/4
 * @author wuc
 */
class HttpUrlConnectProxy constructor(val url: String) : IDownload {

    private val conn: URLConnection = URL(url).openConnection()

    init {
        conn.doInput = true
        conn.connectTimeout = 30 * 1000
    }

    override fun getDownloadUrl(): String {
        return url
    }

    override fun getInputStream(): InputStream {
        return conn.getInputStream()
    }

    override fun getTotalSize(): Long {
        return conn.contentLength.toLong()
    }

}
