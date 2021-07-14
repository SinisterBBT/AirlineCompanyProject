package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.fio
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.passportData
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class PassengerTest {

    @Test
    fun `create passenger - success`() {
        val fio = fio()
        val passportData = passportData()

        val result = Passenger.from(fio, passportData)

        result.let {
            fio shouldBe fio
            passportData shouldBe passportData
        }
    }
}