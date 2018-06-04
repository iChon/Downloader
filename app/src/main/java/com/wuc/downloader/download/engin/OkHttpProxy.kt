package com.wuc.downloader.download.engin

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.InputStream

/**
 *
 * @date 2018/6/4
 * @author wuc
 */
class OkHttpProxy constructor(val url: String, okHttpClient: OkHttpClient) : IDownload {

    private val response: Response

    init {
        val request = Request.Builder().url(url).build()
        response = okHttpClient.newCall(request).execute()
    }

    constructor(url: String) : this(url, OkHttpClient())

    override fun getDownloadUrl(): String {
        return url
    }

    override fun getInputStream(): InputStream? {
        return response.body()?.byteStream()
    }

    override fun getTotalSize(): Long? {
        return response.body()?.contentLength()
    }

}
