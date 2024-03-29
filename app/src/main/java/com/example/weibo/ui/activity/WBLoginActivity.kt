package com.example.weibo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weibo.R
import com.example.weibo.WBApplication
import com.sina.weibo.sdk.auth.*
import com.sina.weibo.sdk.auth.sso.SsoHandler
import kotlinx.android.synthetic.main.activity_login.*

/**
 * 登录界面
 * @author koala.k
 */
class WBLoginActivity : AppCompatActivity() {
    private val TAG = "WEIBOTOKEN"
    private var ssoHandler: SsoHandler? = null
    private var mAccessToken : Oauth2AccessToken? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        ssoHandler = SsoHandler(this@WBLoginActivity)
        login_weibo.setOnClickListener {
            ssoHandler?.authorize(SelfWbAuthListener())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        ssoHandler?.authorizeCallBack(requestCode,resultCode,data)
    }

    private inner class SelfWbAuthListener : WbAuthListener{
        override fun onSuccess(p0: Oauth2AccessToken?) {
            this@WBLoginActivity.runOnUiThread {
                if (p0 != null) {
                    mAccessToken = p0
                    if (mAccessToken?.isSessionValid!!){
                        AccessTokenKeeper.writeAccessToken(WBApplication.getContext(),mAccessToken)
                        val intent = Intent(this@WBLoginActivity, HomePageActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
            Toast.makeText(this@WBLoginActivity,"success",Toast.LENGTH_SHORT).show()
        }

        override fun onFailure(p0: WbConnectErrorMessage?) {
            Toast.makeText(this@WBLoginActivity,"failure",Toast.LENGTH_SHORT).show()
        }

        override fun cancel() {
            Toast.makeText(this@WBLoginActivity,"cancel",Toast.LENGTH_SHORT).show()
        }
    }
}
