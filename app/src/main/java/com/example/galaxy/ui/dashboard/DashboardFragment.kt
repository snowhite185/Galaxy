package com.example.galaxy.ui.dashboard

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.galaxy.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private val viewModel: MembersListItemViewModel by viewModels()
    private val dashViewModel: DashboardViewModel by viewModels()
    private lateinit var binding: FragmentDashboardBinding
    private val adapter = MembersListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        binding.viewModel = dashViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(
            requireActivity(), { _, _, _, _ ->
                dashViewModel.dateChanged(day, month + 1, year)
            }, year, month, day
        )

        binding.tvDate.setOnClickListener {
            datePicker.show()
        }

        binding.recyclerView.adapter = adapter
        viewModel.getMembers().observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
    }
}