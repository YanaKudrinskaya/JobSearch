package ru.practicum.android.diploma.ui.industrychoice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentIndustryChoiceBinding
import ru.practicum.android.diploma.domain.industrychoice.models.Industry
import ru.practicum.android.diploma.presentation.industrychoice.IndustryChoiceScreenState
import ru.practicum.android.diploma.presentation.industrychoice.IndustryChoiceViewModel
import ru.practicum.android.diploma.ui.industrychoice.adapter.IndustryAdapter
import ru.practicum.android.diploma.ui.root.NavigationVisibilityController
import kotlin.getValue

class IndustryChoiceFragment : Fragment() {

    private var _binding: FragmentIndustryChoiceBinding? = null
    private val binding get() = _binding!!

    private val viewModel: IndustryChoiceViewModel by viewModel()

    private var selectedIndustryId: Int? = null

    private val adapter: IndustryAdapter by lazy {
        IndustryAdapter(
            onClick = { industry ->
                selectedIndustryId = industry.id
                adapter.updateSelection(industry.id)
                binding.applyButton.isVisible = true
            },
            selectedId = selectedIndustryId
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIndustryChoiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as? NavigationVisibilityController)?.setNavigationVisibility(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.visibility = View.VISIBLE

        setupListeners()
        setupObserves()

        binding.searchIndustry.doOnTextChanged { text, _, _, _ ->
            if (binding.searchIndustry.text.toString().isEmpty()) {
                binding.searchFieldIcon.setImageDrawable(requireContext().getDrawable(R.drawable.ic_search))
                adapter.filter(null)
            } else {
                binding.searchFieldIcon.setImageDrawable(requireContext().getDrawable(R.drawable.ic_close))
                adapter.filter(binding.searchIndustry.text.toString())
            }
            binding.applyButton.isVisible = false
            adapter.getItems().forEach {
                if (it.id == selectedIndustryId) {
                    binding.applyButton.isVisible = true
                }
            }
        }
    }

    private fun setupListeners() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.applyButton.setOnClickListener {
            selectedIndustryId?.let { viewModel.saveIndustry(it) }
            findNavController().navigateUp()
        }

        binding.searchFieldIcon.setOnClickListener {
            binding.searchIndustry.setText("")
        }
    }

    private fun setupObserves() {
        viewModel.getScreenState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is IndustryChoiceScreenState.Content -> showContent(state.list, state.SavedIndustryId)
                IndustryChoiceScreenState.Empty -> showError()
                IndustryChoiceScreenState.Error -> showError()
            }

        }
    }

    private fun showContent(list: List<Industry>, savedIndustryId: Int?) {
        binding.errorPlaceholder.isVisible = false
        binding.recyclerView.isVisible = true
        adapter.updateSelection(savedIndustryId)
        if (savedIndustryId != null) {
            binding.applyButton.isVisible = true
        }
        adapter.setItems(list.toMutableList())
    }

    private fun showError() {
        binding.recyclerView.visibility = View.GONE
        binding.errorPlaceholder.isVisible = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
