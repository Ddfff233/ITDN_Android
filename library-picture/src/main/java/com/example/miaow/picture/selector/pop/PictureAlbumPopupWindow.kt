package com.example.miaow.picture.selector.pop

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import android.widget.RelativeLayout.LayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miaow.base.adapter.BaseAdapter
import com.example.miaow.picture.databinding.PictureAlbumPopupWindowBinding
import com.example.miaow.picture.selector.adapter.PictureAlbumAdapter
import com.example.miaow.picture.selector.bean.AlbumBean

class PictureAlbumPopupWindow(context: Context) : PopupWindow(context) {

    private var _binding: PictureAlbumPopupWindowBinding? = null
    private val binding get() = _binding!!
    private var albumAdapter = PictureAlbumAdapter()
    private var _listener: OnAlbumSelectedListener? = null

    init {
        _binding = PictureAlbumPopupWindowBinding.inflate(LayoutInflater.from(context))
        contentView = binding.root
        this.width = LayoutParams.MATCH_PARENT
        this.isFocusable = false
        this.isOutsideTouchable = false
        setBackgroundDrawable(ColorDrawable())
        initView()
    }

    fun onDestroy() {
        binding.list.adapter = null
        _listener = null
        _binding = null
    }

    private fun initView() {
        binding.list.layoutManager = LinearLayoutManager(binding.list.context)
        binding.list.adapter = albumAdapter
        albumAdapter.setOnItemClickListener(object : BaseAdapter.OnItemClickListener {
            override fun onItemClick(holder: BaseAdapter.ViewBindHolder, position: Int) {
                albumAdapter.setSelectedPosition(position)
                _listener?.onAlbumSelected(albumAdapter.getItem(position).name)
                dismiss()
            }
        })
    }

    fun setAlbumData(data: List<AlbumBean>, position: Int) {
        albumAdapter.setNewData(data)
        albumAdapter.setSelectedPosition(position)
    }

    fun show(parent: View) {
        showAsDropDown(parent)
    }

    fun setOnAlbumSelectedListener(listener: OnAlbumSelectedListener) {
        this._listener = listener
    }

    interface OnAlbumSelectedListener {
        fun onAlbumSelected(name: String)
    }

}