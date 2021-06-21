package com.alextruck.cob_test_task.usecase.errors

import com.alextruck.cob_test_task.data.error.Error

interface ErrorUseCase {
    fun getError(errorCode: Int): Error
}