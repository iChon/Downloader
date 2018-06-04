package com.wuc.downloader

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.wuc.downloader.download.DownloadListener
import com.wuc.downloader.download.Downloader
import com.wuc.downloader.download.engin.OkHttpProxy
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url = "http://gdown.baidu.com/data/wisegame/011736e682948d44/yingyongbao_7212130.apk";
        val desDir = File(this.cacheDir, "downloader");

        btn_download.setOnClickListener {
            download(url, desDir);
        }
    }

    private fun download(url: String, desDir: File) {
        object : Thread(){
            override fun run() {
                Downloader.download(desDir, OkHttpProxy(url), object : DownloadListener {
                    override fun onSuccess(file: File) {
                        runOnUiThread {
                            showToast("下载成功")
                        }
                    }

                    override fun onDownloading(total: Long?, download: Long?) {
                        Log.d(this@MainActivity.javaClass.simpleName, "$download / $total")
                    }

                    override fun onFailure(e: Exception?) {
                        showToast("下载失败")
                    }
                })
            }
        }.start()
    }

    private fun showToast(msg: String) {
        Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
    }

}
