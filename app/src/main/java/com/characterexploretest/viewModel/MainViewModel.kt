package com.characterexploretest.viewModel

import androidx.lifecycle.ViewModel
import com.characterexploretest.model.Actor
import com.characterexploretest.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainViewModel : ViewModel() {
    lateinit var actors: MutableList<Actor>
    var seasons: MutableList<String> = mutableListOf()
    fun init(onGet: (MutableList<Actor>) -> Unit,
             onError: (String) -> Unit) {
        callApi(onGet, onError)
    }

    private fun callApi(onGet: (MutableList<Actor>) -> Unit, onError: (String) -> Unit) {
        RetrofitClient.api.getAddresses()
            .enqueue(object : Callback<MutableList<Actor>> {
                override fun onResponse(
                    call: Call<MutableList<Actor>>,
                    response: Response<MutableList<Actor>>
                ) {
                    if (response.body() != null) {
                        response.body()?.let {
                            actors = it
                            it.map { actor ->
                                actor.appearance.map { i ->
                                    if (!seasons.contains(i.toString())) seasons.add(i.toString())
                                }
                            }
                            seasons.sort()
                            onGet.invoke(it)
                        }
                    }
                }

                override fun onFailure(call: Call<MutableList<Actor>>, t: Throwable) {
                    onError.invoke(t.message.orEmpty())
                }

            })
    }
}