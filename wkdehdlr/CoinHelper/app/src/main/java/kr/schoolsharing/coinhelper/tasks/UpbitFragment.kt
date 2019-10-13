package kr.schoolsharing.coinhelper.tasks

import android.os.Bundle
import androidx.databinding.library.baseAdapters.BR
import kr.schoolsharing.coinhelper.R
import kr.schoolsharing.coinhelper.base.BaseFragment
import kr.schoolsharing.coinhelper.base.BaseRecyclerViewAdapter
import kr.schoolsharing.coinhelper.data.Repository
import kr.schoolsharing.coinhelper.databinding.FragmentCoinrecyclerBinding
import kr.schoolsharing.coinhelper.databinding.MainRvItemBinding
import kr.schoolsharing.coinhelper.model.UpbitItem
import kr.schoolsharing.coinhelper.util.Market

class UpbitFragment : BaseFragment<FragmentCoinrecyclerBinding>(R.layout.fragment_coinrecycler) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(binding.rvCoin) {
            adapter = object : BaseRecyclerViewAdapter<UpbitItem, MainRvItemBinding>(
                R.layout.main_rv_item,
                BR.item
            ) {}

            setHasFixedSize(true)
        }

        val marketName = arguments?.get(Market.NAME.name).toString()
        binding.viewModel = UpbitViewModel(Repository).apply {
            loadUpbitMarket(marketName)
        }
    }

    companion object {
        fun newInstance(marketName: String): UpbitFragment {
            val fragment = UpbitFragment()
            val bundle = Bundle()

            bundle.putString(Market.NAME.name, marketName)
            fragment.arguments = bundle
            return fragment
        }
    }

}