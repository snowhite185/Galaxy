package com.example.galaxy.ui.dashboard

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.galaxy.R
import com.example.galaxy.data.GalaxyDatabase
import com.example.galaxy.data.GalaxyRepository
import com.example.galaxy.databinding.FragmentDashboardBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val tvDate: TextView = binding.tvDate

        val mcurrentTime = Calendar.getInstance()
        val year = mcurrentTime.get(Calendar.YEAR)
        val month = mcurrentTime.get(Calendar.MONTH)
        val day = mcurrentTime.get(Calendar.DAY_OF_MONTH)
        tvDate.text =
            String.format("%d / %d / %d", day, month + 1, year)

        val datePicker = DatePickerDialog(
            requireActivity(),
            { view, year, month, dayOfMonth ->
                tvDate.text =
                    String.format("%d / %d / %d", dayOfMonth, month + 1, year)
            }, year, month, day
        );

        tvDate.setOnClickListener {
            datePicker.show()
        }

        val dao = GalaxyDatabase.getDatabase(requireContext()).membersDao()
        val attdao = GalaxyDatabase.getDatabase(requireContext()).attendanceDao()
        val repository = GalaxyRepository(attdao,dao)
        val viewModel = MemberListItemViewModelFactory(repository)
        val recyclerview :RecyclerView =binding.recyclerView


        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(requireActivity())

        // ArrayList of class ItemsViewModel
        val data = ArrayList<MembersListItemViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view

        // This will pass the ArrayList to our Adapter
        val adapter = MembersListAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}