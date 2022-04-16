package dev.arj.cuacanusantara.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import dev.arj.cuacanusantara.R
import dev.arj.cuacanusantara.textChanges
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.flow.*
import org.koin.android.ext.android.inject

class SearchFragment : Fragment() {

    private val viewModel by inject<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        input_search.textChanges()
            .filterNot { query -> query.isNullOrBlank() }
            .debounce(3000)
            .distinctUntilChanged()
            .flatMapLatest { viewModel.fetchCurrentWeatherByQuery(it.toString()) }
            .onEach {
                Log.e("ALSDJLKAJSD ", it.toString())
            }
            .launchIn(lifecycleScope)
    }

}