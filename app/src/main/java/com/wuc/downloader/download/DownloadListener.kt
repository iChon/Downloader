package com.wuc.downloader.download

import java.io.File

interface DownloadListener {

    /**
     * 下载成功
     * @param file 下载的文件
     */
    fun onSuccess(file: File)

    /**
     * 下载中
     * @param total 总大小(单位:B)
     * @param download 已下载大小
     */
    fun onDownloading(total: Long?, download: Long?)

    /**
     * 下载失败
     */
    fun onFailure(e: Exception?)

}