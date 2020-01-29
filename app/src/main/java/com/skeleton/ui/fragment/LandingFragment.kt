package com.skeleton.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.skeleton.R
import com.skeleton.databinding.ActivityLauncherBinding
import com.skeleton.presentation.callback.IDataBindingModel
import com.skeleton.presentation.callback.ItemClickCallback
import com.skeleton.presentation.callback.LandingFragmentClickHandler
import com.skeleton.presentation.callback.ProgressDialogCallback
import com.skeleton.presentation.ui.fragment.BaseFragment
import com.skeleton.search.viewmodel.LandingViewModel
import com.skeleton.util.extension.setTitle
import com.skeleton.util.extension.showShortToast
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import retrofit2.Response

class LandingFragment : BaseFragment(), ItemClickCallback, ProgressDialogCallback,
    LandingFragmentClickHandler {
    private lateinit var binding: ActivityLauncherBinding

    companion object {
        fun newInstance() = LandingFragment()

    }
    private val viewModel: LandingViewModel by sharedViewModel()

    private val providersObserver = Observer<List<String>> { list ->
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ActivityLauncherBinding.inflate(inflater)
        return binding.root
    }

    override fun onVisible() {
            setTitle(R.string.app_name)
    }

    private fun showSearchLoading() {
        binding.progressBarVisibility = VISIBLE
    }

    private fun hideSearchLoading() {
        binding.progressBarVisibility = GONE
    }

    override fun onSuccess(any: Any?) {
        hideSearchLoading()
    }

    override fun onFailure(any: Any?) {
        showShortToast((any as Response<*>).message())
        hideSearchLoading()
    }

    override fun onItemClick(
        iDataBindingModel: IDataBindingModel,
        itemViewBinding: ViewDataBinding
    ) {
    }

    override fun onButtonClick(view: View) {
    }
}