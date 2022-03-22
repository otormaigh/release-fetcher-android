package ie.otormaigh.releasefetcher

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.work.*

internal object WorkScheduler {
  inline fun <reified W : CoroutineWorker> oneTimeRequest(context: Context, inputData: Data? = null): LiveData<WorkInfo> {
    val workRequest = OneTimeWorkRequestBuilder<W>()
      .apply { if (inputData != null) setInputData(inputData) }
      .build()

    WorkManager.getInstance(context)
      .enqueue(workRequest)

    return WorkManager.getInstance(context).getWorkInfoByIdLiveData(workRequest.id)
  }
}