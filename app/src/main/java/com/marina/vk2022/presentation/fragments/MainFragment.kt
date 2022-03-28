package com.marina.vk2022.presentation.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.*
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.marina.vk2022.R
import com.marina.vk2022.databinding.BottomSheetBinding
import com.marina.vk2022.databinding.FragmentMainBinding
import com.marina.vk2022.databinding.PlayerViewBinding
import com.marina.vk2022.presentation.MainFragmentViewModel
import com.marina.vk2022.presentation.records_list_recycler_view.RecordListAdapter
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding
    private lateinit var bindingBottomSheet: BottomSheetBinding


    private lateinit var viewModel: MainFragmentViewModel
    private lateinit var recordListAdapter: RecordListAdapter

    private var dirPath = ""
    private var filename = ""

    private var permissions = arrayOf(Manifest.permission.RECORD_AUDIO)
    private var permissionGranted = false
    private lateinit var vibrator: Vibrator
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)
        bindingBottomSheet = BottomSheetBinding.bind(view.findViewById(R.id.bottom_sheet))

        permissionGranted = ActivityCompat.checkSelfPermission(
            requireContext(),
            permissions[0]
        ) == PackageManager.PERMISSION_GRANTED

        viewModel = ViewModelProvider(this)[MainFragmentViewModel::class.java]
        if (!permissionGranted) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                permissions,
                REQUEST_RECORD_AUDIO_PERMISSION
            )
        }
        bottomSheetSetup()
        vibrator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        setupButtonListeners()
        setupRecyclerView()
        viewModel.recordsList.observe(viewLifecycleOwner) {
            recordListAdapter.submitList(it)
            println(it)
        }
    }

    private fun setupRecyclerView() {
        val rvRecordList = view?.findViewById<RecyclerView>(R.id.rv_records_list)

        with(rvRecordList) {
            recordListAdapter = RecordListAdapter()
            this?.adapter = recordListAdapter
        }

        setupClickListeners()
    }

    private fun setupClickListeners() {
        recordListAdapter.onRecordItemPlayClickListener = {
            viewModel.startPlayingRecord(it)
        }
        recordListAdapter.onRecordItemStopClickListener = {
            viewModel.stopPlayingRecord()
        }
    }


    private fun bottomSheetSetup() {
        bottomSheetBehavior = BottomSheetBehavior.from(bindingBottomSheet.root)
        bottomSheetBehavior.peekHeight = 0
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun setupButtonListeners() {

        //начинаем запись
        binding.startRecordImageBtn.setOnClickListener {
            setupMediaRecorder()
            start()
        }

        //заканчиваем запись
        binding.stopRecordImageBtn.setOnClickListener {
            stop()
        }

        bindingBottomSheet.cancelBtn.setOnClickListener {
            cancel()
        }

        bindingBottomSheet.okBtn.setOnClickListener {
            dismiss()
            save()
        }

        binding.bottomSheetBg.setOnClickListener {
            cancel()
            dismiss()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
    }

    private fun setupMediaRecorder() {
        val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd_hh.mm.ss")
        val date = simpleDateFormat.format(Date())
        dirPath = "${requireActivity().externalCacheDir?.absolutePath}/"
        Log.e("222222 mainfr", dirPath)
        filename = "audio_record_$date"
        viewModel.setupMediaRecorder("$dirPath$filename")
    }

    private fun start() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    50,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        }
        binding.startRecordImageBtn.visibility = View.GONE
        binding.stopRecordImageBtn.visibility = View.VISIBLE
        if (permissionGranted) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                permissions,
                REQUEST_RECORD_AUDIO_PERMISSION
            )
            viewModel.startRecord()
        }
    }

    private fun stop() {
        binding.startRecordImageBtn.visibility = View.VISIBLE
        binding.stopRecordImageBtn.visibility = View.GONE
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        binding.bottomSheetBg.visibility = View.VISIBLE
        bindingBottomSheet.filenameInput.setText(filename)
        viewModel.stopRecord()
    }

    private fun cancel() {
        viewModel.cancelRecord()
        dismiss()
    }

    private fun delete() {
        viewModel.deleteRecord()
        Toast.makeText(requireContext(), "Record deleted", Toast.LENGTH_SHORT).show()
    }

    private fun hideKeyboard(view: View) {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun dismiss() {
        binding.bottomSheetBg.visibility = View.GONE
        hideKeyboard(bindingBottomSheet.filenameInput)
        Handler(Looper.getMainLooper()).postDelayed({
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }, 100)
    }

    private fun save() {
        val newFileName = bindingBottomSheet.filenameInput.text.toString()
        viewModel.saveRecord(newFileName)
    }

    companion object {
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 200
    }
}