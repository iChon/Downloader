package com.wuc.downloader.download.engin

import java.io.InputStream

/**
 *
 * @date 2018/6/4
 * @author wuc
 */
interface IDownload {

    /**
     * 获取下载url
     */
    fun getDownloadUrl(): String

    /**
     * 获取响应流
     */
    fun getInputStream(): InputStream?

    /**
     * 获取响应总长度
     */
    fun getTotalSize(): Long?

}