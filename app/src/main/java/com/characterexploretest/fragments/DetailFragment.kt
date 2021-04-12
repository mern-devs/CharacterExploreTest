package com.characterexploretest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.characterexploretest.R
import com.characterexploretest.fragments.DetailFragmentArgs.fromBundle
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment: Fragment() {
    private val actor by lazy {
        arguments?.let { fromBundle(it).actor }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        actor?.let {
            name.text = it.name + " (Nick name: " + it.nickname + ")"
            var appearance = ""
            it.appearance.map { i ->
                appearance += "$i, "
            }
            seasonAppearance.text = appearance.substring(0, appearance.length -2)
            status.text = it.status
            var occupy = ""
            it.occupation.map { str->
                occupy += "$str, "
            }
            occupation.text = occupy.substring(0, occupy.length -2)
            avatar.setImageURI(it.img)
        }
    }
}