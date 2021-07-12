package com.polyakovworkbox.stringconcatcourse.common.types.base

abstract class AggregateRoot<T>(id: T, version: Version) : DomainEntity<T>(id, version)