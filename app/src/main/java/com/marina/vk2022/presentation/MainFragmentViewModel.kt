package com.marina.vk2022.presentation

import android.annotation.SuppressLint
import android.app.Application
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.marina.vk2022.data.network.VKRecordStorage
import com.marina.vk2022.data.repository.RecordListRepositoryImpl
import com.marina.vk2022.data.storage.room.RoomRecordStorage
import com.marina.vk2022.domain.Timer
import com.marina.vk2022.domain.entity.RecordItem
import com.marina.vk2022.domain.usecase.CreateRecordUseCase
import com.marina.vk2022.domain.usecase.DeleteRecordUseCase
import com.marina.vk2022.domain.usecase.GetAllRecordsUseCase
import kotlinx.coroutines.*
import java.io.File
import java.io.IOException
import java.util.*

@SuppressLint("NullSafeMutableLiveData")
class MainFragmentViewModel(application: Application) :
    AndroidViewModel(application),
    Timer.OnTimerTickListener {

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    private val vKRecordStorage = VKRecordStorage()
    private val roomRecordStorage = RoomRecordStorage(context)
    private val repository = RecordListRepositoryImpl(roomRecordStorage, vKRecordStorage)

    private val createRecordUseCase = CreateRecordUseCase(repository)
    private val deleteRecordUseCase = DeleteRecordUseCase(repository) // не успела
    private val getAllRecordsUseCase = GetAllRecordsUseCase(repository)

    private lateinit var recorder: MediaRecorder
    private lateinit var player:  MediaPlayer
    private var dirPath = ""
    private var filename = ""
    private var duration = ""
    private val timer: Timer = Timer(this)

    private var _recordsList: MutableLiveData<List<RecordItem>> =
        MutableLiveData<List<RecordItem>>()
    val recordsList: LiveData<List<RecordItem>>
        get() = _recordsList

    private fun updateRecordsList() {
        val newList = runBlocking {
            getAllRecordsUseCase.invoke()
        }
        _recordsList.value = newList.value
    }

    init {
        updateRecordsList()
    }

    fun startRecord() {
        recorder.start()
        timer.start()
    }

    fun cancelRecord() {
        File("$dirPath$filename.mp3").delete()
    }

    fun stopRecord() {
        timer.stop()
        recorder.apply {
            stop()
            release()
        }
    }

    @DelicateCoroutinesApi
    fun saveRecord(newFileName: String) {
        if (newFileName != filename) {
            val newFile = File("$dirPath$newFileName.mp3")
            File("$dirPath$filename.mp3").renameTo(newFile)
        }

        val filePath = "$dirPath$newFileName.mp3"
        val timestamp = Date().time

        val record = RecordItem(
            recordName = newFileName,
            recordPath = filePath,
            recordLength = duration,
            recordCreatingTime = timestamp
        )

        runBlocking {
            createRecordUseCase.invoke(record)
            delay(100L)
            updateRecordsList()
        }
    }

    fun startPlayingRecord(record: RecordItem) {
        setupMediaPlayer(record)
        player.start()
    }

    fun stopPlayingRecord() {
        player.stop()
        player.release()
    }

    fun deleteRecord() {
        File("$dirPath$filename.mp3").delete()
        updateRecordsList()
    }

    fun setupMediaRecorder(filename: String) {
        recorder = MediaRecorder()
        recorder.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile("$filename.mp3")

            try {
                prepare()
            } catch (e: IOException) {
            }
        }
    }

    private fun setupMediaPlayer(record: RecordItem) {
        dirPath = "${context.externalCacheDir?.absolutePath}/"
        player = MediaPlayer()
        player.apply {
            setDataSource("$dirPath${record.recordPath}")
            prepare()
        }
    }

    override fun onTimerTick(duration: String) {
        this.duration = duration
        println(duration)
    }
}