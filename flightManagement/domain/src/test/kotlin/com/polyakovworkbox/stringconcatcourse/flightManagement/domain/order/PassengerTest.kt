package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.fullName
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.passenger
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.passportData
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class PassengerTest {

    @Test
    fun `is equal to other Passenger with the same value`() {
        val firstValue = passenger(fullName("Ivanov Ivan Ivanovich"), passportData("1234 123456"))
        val secondValue = passenger(fullName("Ivanov Ivan Ivanovich"), passportData("1234 123456"))

        (firstValue == secondValue) shouldBe true
    }

    @Test
    fun `create passenger - success`() {
        val fio = fullName()
        val passportData = passportData()

        val result = Passenger.from(fio, passportData)

        result.let {
            fio shouldBe fio
            passportData shouldBe passportData
        }
    }
}