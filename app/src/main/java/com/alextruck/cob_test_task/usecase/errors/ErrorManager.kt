package com.alextruck.cob_test_task.usecase.errors

import com.alextruck.cob_test_task.data.error.Error
import com.alextruck.cob_test_task.data.error.mapper.ErrorMapper
import javax.inject.Inject

/**
 * Created by AlextrucK
 */

class ErrorManager @Inject constructor(private val errorMapper: ErrorMapper) : ErrorUseCase {
    override fun getError(errorCode: Int): Error {
        return Error(code = errorCode, description = errorMapper.errorsMap.getValue(errorCode))
    }
}