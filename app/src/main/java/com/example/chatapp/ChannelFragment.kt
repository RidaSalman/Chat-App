package com.example.chatapp

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.chatapp.databinding.FragmentChannelBinding


class ChannelFragment : Fragment() {

    private lateinit var binding: FragmentChannelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_channel, container, false)

        /*val s = binding.searchView

        val hintText = "<font color='#B6B6B6'>${getString(R.string.hintSearchMess)}</font>"
        s = Html.fromHtml(hintText, Html.FROM_HTML_MODE_LEGACY)*/


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }


}