package com.marina.vk2022.presentation.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.marina.vk2022.R
import com.marina.vk2022.databinding.FragmentMainBinding
import com.marina.vk2022.domain.VoiceRecorder
import com.marina.vk2022.presentation.Timer
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment(R.layout.fragment_main), Timer.OnTimerTickListener {

    private lateinit var binding: FragmentMainBinding
    private lateinit var voiceRecorder: VoiceRecorder
    private lateinit var recorder: MediaRecorder
    private var dirPath = ""
    private var filename = ""
    private var isRecording = false
    private var isPaused = false

    private var permissions = arrayOf(Manifest.permission.RECORD_AUDIO)
    private var permissionGranted = false

    private lateinit var timer: Timer
    private lateinit var vibrator: Vibrator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        permissionGranted = ActivityCompat.checkSelfPermission(
            requireContext(),
            permissions[0]
        ) == PackageManager.PERMISSION_GRANTED

        if (!permissionGranted) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                permissions,
                REQUEST_RECORD_AUDIO_PERMISSION
            )
        }
        timer = Timer(this)
        vibrator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        voiceRecorder = VoiceRecorder()
        setupButtonListeners()
    }

    private fun setupButtonListeners() {

        //начинаем запись
        binding.startRecordImageBtn.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            binding.startRecordImageBtn.visibility = View.GONE
            binding.stopRecordImageBtn.visibility = View.VISIBLE
            startRecord()
        }

        //заканчиваем запись
        binding.stopRecordImageBtn.setOnClickListener {
            binding.startRecordImageBtn.visibility = View.VISIBLE
            binding.stopRecordImageBtn.visibility = View.GONE
            stopRecord()
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

    private fun startRecord() {
        if (!permissionGranted) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                permissions,
                REQUEST_RECORD_AUDIO_PERMISSION
            )
            return
        }
        recorder = MediaRecorder()
        dirPath = "${requireActivity().externalCacheDir?.absolutePath}/"
        var simpleDateFormat = SimpleDateFormat("yyyy.MM.DD_hh.mm.ss")
        var date = simpleDateFormat.format(Date())
        filename = "audio_record_$date"
        recorder.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile("$dirPath$filename.mp3")

            try {
                prepare()
            } catch (e: IOException) {
            }

            start()
        }
        isRecording = true
        isPaused = false

        timer.start()
    }

    private fun stopRecord() {
//        recorder.stop()
        isPaused = false

        timer.stop()
    }

    companion object {
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 200
    }

    override fun onTimerTick(duration: String) {
        println(duration)
    }
}