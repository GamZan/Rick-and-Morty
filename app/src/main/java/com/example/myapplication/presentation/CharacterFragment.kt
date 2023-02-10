package com.example.myapplication.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.myapplication.data.CharacterRepository
import com.example.myapplication.databinding.FragmentCharacterBinding
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CharacterFragment : Fragment() {

    private lateinit var binding: FragmentCharacterBinding

    private val throwable = MutableLiveData<Throwable?>(null)

    private val repository= CharacterRepository()

    private val viewModel = CharacterViewModel(repository, throwable)

    private val characterAdapter = CharacterAdapter()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycler.adapter = characterAdapter.withLoadStateFooter(CharacterLoadStateAdapter())

        viewModel.pageCharacter.onEach {
            characterAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.swipeRefresh.setOnRefreshListener {
            characterAdapter.refresh()
        }

        binding.refreshButton.setOnClickListener {
            characterAdapter.refresh()
            if (throwable.value?.cause == null) {
                binding.refreshButton.visibility = View.GONE
                binding.recycler.visibility = View.VISIBLE
            }
        }

        throwable.observe(viewLifecycleOwner) {
            val showError = it != null
            if (it != null) {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
            binding.recycler.isVisible = !showError
            binding.refreshButton.isVisible = showError
        }

        characterAdapter.loadStateFlow.onEach {
            binding.swipeRefresh.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }


}