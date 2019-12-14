package com.example.dimensionleague.setting

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.common.code.ErrorCode
import com.example.common.port.IAccountCallBack
import com.example.dimensionleague.R
import com.example.dimensionleague.userbean.UploadBean
import com.example.framework.base.BaseActivity
import com.example.framework.manager.AccountManager
import com.example.framework.manager.ErrorDisposeManager
import com.example.net.AppNetConfig
import com.example.net.RetrofitCreator
import com.wyp.avatarstudio.AvatarStudio
import io.reactivex.Observer
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.activity_user_massage.*
import retrofit2.Retrofit
import java.io.File
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.user_item_set_name.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.MediaType
import okhttp3.ResponseBody
import java.lang.Error
import java.util.*
import kotlin.math.log


//个人用户页面
class UserMassageActivity :  BaseActivity(),IAccountCallBack {
    override fun onRegisterSuccess() {}

    override fun onLogin() {}

    override fun onLogout() {}

    override fun onAvatarUpdate(url: String?) {
        Glide.with(this).load(url).apply(RequestOptions().centerCrop()).into(heanUserImg)
    }

    var list:MutableList<SettingBean> = mutableListOf()
    var sexArray:Array<String> = arrayOf("男","女","保密")
    lateinit var heanUserImg: ImageView
    lateinit var heanUserName: TextView
    lateinit var headView: View
    var nowdate=Calendar.getInstance()
    var mYear =nowdate.get(Calendar.YEAR)
    var mMonth = nowdate.get(Calendar.MONTH);
    var mDay = nowdate.get(Calendar.DAY_OF_MONTH);
    lateinit var adapter:MySettingAdapter
    override fun getLayoutId(): Int {
        return R.layout.activity_user_massage
    }

    override fun init() {
        headView = LayoutInflater.from(this).inflate(R.layout.user_item_head, null)
        heanUserImg=headView.findViewById<ImageView>(R.id.user_massage_item_img)
        heanUserName=headView.findViewById<TextView>(R.id.user_massage_item_title)
        AccountManager.getInstance().registerUserCallBack(this)

    }

    override fun initDate() {
        list.add(SettingBean("用户名",""))
        list.add(SettingBean("昵称",""))
        list.add(SettingBean("性别","保密"))
        list.add(SettingBean("出生年月",""))
//        判断是否登录
        if (AccountManager.getInstance().isLogin){
            list.get(0).massage=""+AccountManager.getInstance().user.name
            list.get(1).massage=""+AccountManager.getInstance().user.name
            if (AccountManager.getInstance().user.avatar!=null){
                Glide.with(this).load(AccountManager.getInstance().user.avatar).apply(RequestOptions().centerCrop()).into(heanUserImg)
            }
        }
        user_massage_rv.layoutManager= LinearLayoutManager(this)
        adapter= MySettingAdapter(R.layout.setting_item,list)
        adapter.addHeaderView(headView)
        user_massage_rv.adapter=adapter;

        headView.setOnClickListener {
            AvatarStudio.Builder(this)
                .needCrop(true)
                .dimEnabled(true)
                .setAspect(1,1)
                .setOutput(50,50)
                .setText("拍照","我的相册","取消")
                .setTextColor(Color.BLUE)
                .show { uri: String? ->
                    val file = Glide.with(this).load(uri).downloadOnly(50, 50).get()
                    upload(file)

                }
        }
        adapter.setOnItemChildClickListener { adapter, view, position ->
            when(position){
                0->{
                    val makeText = Toast.makeText(this, "用户名不支持修改哦~", Toast.LENGTH_LONG)
                    makeText.setGravity(Gravity.CENTER,0,0)
                    makeText.show()
                }
                1->{
                    val inflate = LayoutInflater.from(this).inflate(R.layout.user_item_set_name, null)
                    AlertDialog.Builder(this)
                        .setView(inflate)
                        .setCancelable(false)
                        .setPositiveButton("确定",object :DialogInterface.OnClickListener{
                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                val text = inflate.findViewById<EditText>(R.id.user_changename_edit)
                                println("sssss"+text)
                                list.get(position).massage=""+text.text.toString()
                                adapter.notifyDataSetChanged()
                            }
                        })
                        .setNegativeButton("取消",object :DialogInterface.OnClickListener{
                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                dialog!!.cancel()
                            }
                        })
                        .show()
                }
                2->{
                    showSexDialog(position)
                }
                3->{
                    val datePickerDialog =
                        DatePickerDialog(this,DatePickerDialog.THEME_HOLO_LIGHT, object : DatePickerDialog.OnDateSetListener {
                            override fun onDateSet(
                                view: DatePicker?,
                                year: Int,
                                month: Int,
                                dayOfMonth: Int
                            ) {
                                mDay = dayOfMonth
                                mMonth = month
                                mYear = year
                                val string = StringBuffer().append(mYear).append("年").append(mMonth)
                                    .append("月")
                                    .append(mDay).append("日").toString()
                                list.get(position).massage = "" + string
                                adapter.notifyDataSetChanged()
                            }
                        }, mYear, mMonth, mDay)
                    datePickerDialog.show()
                }
            }
            toast(this,list.get(position).title+"功能暂未开发完全,如有疑问请联系小柚")
        }
    }

    private fun showSexDialog(position: Int) {
        val sexDialog = AlertDialog.Builder(this)
        sexDialog.setSingleChoiceItems(sexArray,2,object :DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                list.get(position).massage=sexArray[which]
                adapter.notifyDataSetChanged()
                dialog!!.dismiss()
            }
        })
        sexDialog.show()

    }


    private fun upload(uploadFile: File) {
        if (!uploadFile.exists()) {
            toast(this,"上传文件不存在!")
            return
        }
        //创建上传文件的请求体.
        //创建上传文件的part参数.
        val uploadPart = MultipartBody.Part.createFormData("file", uploadFile.name)
        RetrofitCreator.getNetInterence().upload(AppNetConfig.UPLOAD,uploadPart)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseBody> {
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(t: ResponseBody) {
                    val s:UploadBean = t.string() as UploadBean
                    if (s.code.toString().toInt()==200){
                        AccountManager.getInstance().getUser().avatar=s.result
                        AccountManager.getInstance().notifyUserAvatarUpdate(s.result)
                    }

                }
                override fun onError(e: Throwable) {
                    ErrorDisposeManager.HandlerError(e)
                    throw RuntimeException(e.message)
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        AccountManager.getInstance().unRegisterUserCallBack(this)
    }

}
