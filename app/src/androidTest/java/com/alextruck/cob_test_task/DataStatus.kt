package com.alextruck.cob_test_task

/**
 * Created by AlextrucK
 */
sealed class DataStatus {
    object Success : DataStatus()
    object Fail : DataStatus()
    object EmptyResponse : DataStatus()
}