package com.polyakovworkbox.stringconcatcourse.fleetManagement.domain.aircraft

import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

class AircraftSeatMapTest {

    @Test
    fun `create seat map - success`() {
        val seatMap = correctSeatMapLayout()

        val result = AircraftSeatMap.from(seatMap)

        result shouldBeRight {
            it.seatMap shouldBe seatMap
        }
    }

    @ParameterizedTest
    @ArgumentsSource(WrongSeatMapsArgumentsProvider::class)
    fun `create seat map - empty seat map`(input: ArrayList<ArrayList<ArrayList<Char>>>) {
        val result = AircraftSeatMap.from(input)

        result shouldBeLeft WrongSeatMapLayout
    }
}

internal class WrongSeatMapsArgumentsProvider : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext): Stream<out Arguments> {
        return Stream.of(
            Arguments.of(arrayListOf<ArrayList<ArrayList<Char>>>()),
            Arguments.of(arrayListOf(arrayListOf<ArrayList<Char>>())),
            Arguments.of(arrayListOf(arrayListOf(arrayListOf<Char>()))),
            Arguments.of(arrayListOf(arrayListOf(arrayListOf('A'))))
        )
    }
}