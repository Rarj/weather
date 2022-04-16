package dev.arj.cuacanusantara.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import dev.arj.cuacanusantara.R
import kotlinx.android.synthetic.main.fragment_search.*
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

        viewModel.fetchCurrentWeatherByQuery("Brazil")

        text_navigate_up.setOnClickListener {
            val navController = activity?.let { it1 -> Navigation.findNavController(it1, R.id.nav_host_fragment) }
            navController?.navigateUp()
        }
    }

}