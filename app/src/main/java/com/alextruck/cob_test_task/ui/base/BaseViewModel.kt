package com.alextruck.cob_test_task.ui.base

import androidx.lifecycle.ViewModel
import com.alextruck.cob_test_task.usecase.errors.ErrorManager
import javax.inject.Inject

/**
 * Created by AlextrucK
 */


abstract class BaseViewModel : ViewModel() {

    /**Inject Singleton ErrorManager
     * Use this errorManager to get the Errors
     */

    @Inject
    lateinit var errorManager: ErrorManager
}