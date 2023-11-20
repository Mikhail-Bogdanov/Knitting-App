package com.qwertyuiop.remotedata.repository

import android.content.Context
import android.content.IntentFilter
import android.os.Build.BRAND
import android.os.Build.DEVICE
import android.os.Build.MANUFACTURER
import android.os.Build.MODEL
import com.qwertyuiop.domaingray.repositories.remote.RemoteGrayRepository
import com.qwertyuiop.remotedata.dtos.AuthDto
import com.qwertyuiop.remotedata.network.ServiceApi
import com.qwertyuiop.remotedata.utils.GrayRemoteMapper.toAnswerEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteGrayRepositoryImpl(
    private val context: Context,
    private val serviceApi: ServiceApi
) : RemoteGrayRepository {

    override suspend fun getServiceResponse() = withContext(Dispatchers.IO) {
        serviceApi.getAnswer(
            AuthDto(
                //TODO CHANGE AUTH
                usbCharge = false,
                device = "Test device",
//                usbCharge = ucStatus(),
//                device = getDevice(),
                page = URL
            )
        ).toAnswerEntity()
    }

    private fun ucStatus() = context.registerReceiver(
        null,
        IntentFilter(ACTION)
    )?.extras?.getBoolean(RESULT) ?: false

    private fun getDevice(): String = "$MANUFACTURER $MODEL $BRAND $DEVICE"

    private companion object {
        //TODO PAGE
        const val URL = "tapps2"

        const val ACTION = "android.hardware.usb.action.USB_STATE"
        const val RESULT = "connected"
    }
}