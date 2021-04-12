package com.characterexploretest.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.arlib.floatingsearchview.FloatingSearchView
import com.characterexploretest.R
import com.characterexploretest.adapter.ActorAdapter
import com.characterexploretest.model.Actor
import com.characterexploretest.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_explore.*


class ExploreFragment : Fragment() {

    companion object {
        fun newInstance() = ExploreFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var filteredSeasons = mutableListOf<Int>()
    private var tempList: MutableList<Actor> = mutableListOf()
        set(value) {
            actorAdapter.setData(value)
            field = value
        }
    private var actorAdapter: ActorAdapter = ActorAdapter(tempList, onClick = {
        findNavController().navigate(
            ExploreFragmentDirections.actionExploreFragmentToDetailFragment(
                it
            )
        )
    })
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    @ExperimentalStdlibApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.init(onGet = { mutableList ->
            tempList = mutableList
        }, onError = {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })
        recyclerView.layoutManager = GridLayoutManager(
            context,
            resources.getInteger(R.integer.spanCount)
        )
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = actorAdapter
        floating_search_view.setOnLeftMenuClickListener(object :
            FloatingSearchView.OnLeftMenuClickListener {
            override fun onMenuOpened() {

            }

            override fun onMenuClosed() {
                tempList = viewModel.actors
            }
        })
        floating_search_view.setOnQueryChangeListener { oldQuery, newQuery ->
            tempList = viewModel.actors.filter{
                it.name.lowercase().contains(newQuery.lowercase()) || it.nickname.lowercase().contains(
                    newQuery.lowercase()
                )
            } as MutableList<Actor>
        }
        floatingButton.setOnClickListener {
            Log.e("seasons", "${viewModel.seasons.size}")
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)

            // set the title for the alert dialog

            // set the title for the alert dialog
            builder.setTitle("Choose Seasons")


            val listItems: Array<String> = viewModel.seasons.toTypedArray()
            val checkedItems = BooleanArray(listItems.size)
            for (i in checkedItems.indices) {
                checkedItems[i] = filteredSeasons.contains(Integer.parseInt(listItems[i]))
            }
            // copy the items from the main list to the selected item list
            // for the preview if the item is checked then only the item
            // should be displayed for the user
            // now this is the function which sets the alert dialog for multiple item selection ready

            // now this is the function which sets the alert dialog for multiple item selection ready
            builder.setMultiChoiceItems(listItems, checkedItems) { _, _, _ ->
            }

            // alert dialog shouldn't be cancellable

            // alert dialog shouldn't be cancellable
            builder.setCancelable(false)

            // handle the positive button of the dialog

            // handle the positive button of the dialog
            builder.setPositiveButton("Done", DialogInterface.OnClickListener { dialog, which ->
                done(listItems,checkedItems, dialog)
            })

            // handle the negative button of the alert dialog

            // handle the negative button of the alert dialog
            builder.setNegativeButton("CANCEL",
                DialogInterface.OnClickListener { dialog, which -> dialog.cancel()})

            // handle the neutral button of the dialog to clear
            // the selected items boolean checkedItem

            // handle the neutral button of the dialog to clear
            // the selected items boolean checkedItem
            builder.setNeutralButton("CLEAR ALL"
            ) { dialog, _ ->
                for (i in checkedItems.indices) {
                    checkedItems[i] = false
                }
                done(listItems, checkedItems, dialog)
            }

            // create the alert dialog with the
            // alert dialog builder instance

            // create the alert dialog with the
            // alert dialog builder instance
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }
    }

    private fun done(listItems: Array<String>, checkedItems: BooleanArray, dialog: DialogInterface) {
        filteredSeasons = mutableListOf()
        for (i in checkedItems.indices) {
            if (checkedItems[i]) {
                filteredSeasons.add(Integer.parseInt(listItems[i]))
            }
        }
        if (filteredSeasons.size > 0) {
            tempList = viewModel.actors.filter{
                var isExists = false
                for (season in it.appearance) {
                    if (filteredSeasons.contains(season)) {
                        isExists = true
                        break
                    }
                }
                isExists
            } as MutableList<Actor>
        } else {
            tempList = viewModel.actors
        }
        dialog.dismiss()
    }

}