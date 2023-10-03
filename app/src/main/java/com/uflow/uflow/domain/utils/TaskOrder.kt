package com.uflow.uflow.domain.utils

sealed class TaskOrder{
    object Completes : TaskOrder()
    object Date : TaskOrder()
    object Category : TaskOrder()
    object Priority : TaskOrder()
    object NoCompletes: TaskOrder()
}
