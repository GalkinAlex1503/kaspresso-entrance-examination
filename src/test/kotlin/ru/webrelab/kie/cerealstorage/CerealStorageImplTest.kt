package ru.webrelab.kie.cerealstorage

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class CerealStorageImplTest {

    //positive tests init storage
    @Test
    fun `should initialize with valid containerCapacity and storageCapacity`() {
        val storageTest = CerealStorageImpl(50f, 100f)
        assertEquals(50f, storageTest.containerCapacity, 0.01f)
        assertEquals(100f, storageTest.storageCapacity, 0.01f)
    }

    @Test
    fun `should initialize valid with zero for containerCapacity and valid storageCapacity`() {
        val storageTest = CerealStorageImpl(0f, 100f)
        assertEquals(0f, storageTest.containerCapacity, 0.01f)
        assertEquals(100f, storageTest.storageCapacity, 0.01f)
    }

    @Test
    fun `should initialize valid with zero for containerCapacity and zero storageCapacity`() {
        val storageTest = CerealStorageImpl(0f, 0f)
        assertEquals(0f, storageTest.containerCapacity, 0.01f)
        assertEquals(0f, storageTest.storageCapacity, 0.01f)
    }

    @Test
    fun `should be initialized with valid containerCapacity and equal to storageCapacity`() {
        val storageTest = CerealStorageImpl(100f, 100f)
        assertEquals(100f, storageTest.containerCapacity, 0.01f)
        assertEquals(100f, storageTest.storageCapacity, 0.01f)
    }

    //negative tests init storage
    @Test
    fun `should throw IllegalArgumentException when containerCapacity is negative`() {
        assertThrows(IllegalArgumentException::class.java) {
            CerealStorageImpl(-1f, 50f)
        }
    }

    @Test
    fun `should throw IllegalArgumentException when storageCapacity is negative`() {
        assertThrows(IllegalArgumentException::class.java) {
            CerealStorageImpl(10f, -10f)
        }
    }

    @Test
    fun `should throw if storageCapacity is lower than containerCapacity`() {
        assertThrows(IllegalArgumentException::class.java) {
            CerealStorageImpl(10f, 8f)
        }
    }

    //positive tests addCereal
    @Test
    fun `should add the amount of cereal to the container using the addCereal function`() {
        val storageTest = CerealStorageImpl(10f, 100f)
        assertEquals(0f, storageTest.addCereal(Cereal.RICE, 5f), 0.01f)
        assertEquals(5f, storageTest.getAmount(Cereal.RICE), 0.01f)
    }

    @Test
    fun `should add the amount of cereal to the container using the addCereal function then add more cereal`() {
        val storageTest = CerealStorageImpl(10f, 100f)
        assertEquals(0f, storageTest.addCereal(Cereal.RICE, 5f), 0.01f)
        assertEquals(0f, storageTest.addCereal(Cereal.RICE, 2f), 0.01f)
        assertEquals(7f, storageTest.getAmount(Cereal.RICE), 0.01f)
    }

    @Test
    fun `should add more cereal than the maximum capacity to the container using the addCereal function`() {
        val storageTest = CerealStorageImpl(10f, 100f)
        assertEquals(2f, storageTest.addCereal(Cereal.RICE, 12f), 0.01f)
        assertEquals(10f, storageTest.getAmount(Cereal.RICE), 0.01f)
    }

    @Test
    fun `should add a quantity of cereal equal to the maximum capacity to the container using the addCereal function`() {
        val storageTest = CerealStorageImpl(10f, 100f)
        assertEquals(0f, storageTest.addCereal(Cereal.RICE, 10f), 0.01f)
        assertEquals(10f, storageTest.getAmount(Cereal.RICE), 0.01f)
    }

    @Test
    fun `should add zero amount of cereal to container using addCereal function`() {
        val storageTest = CerealStorageImpl(10f, 100f)
        assertEquals(0f, storageTest.addCereal(Cereal.RICE, 0f), 0.01f)
        assertEquals(0f, storageTest.getAmount(Cereal.RICE), 0.01f)
    }

    //negative tests addCereal
    @Test
    fun `should throw an error when using the addCereal function with negative values`() {
        val storageTest = CerealStorageImpl(10f, 100f)
        assertThrows(IllegalArgumentException::class.java) {
            storageTest.addCereal(Cereal.RICE, -2f)
        }
    }

    @Test
    fun `should be thrown an exception when the storage is full of containers`() {
        val storageTest = CerealStorageImpl(10f, 20f)
        storageTest.addCereal(Cereal.RICE, 2f)
        storageTest.addCereal(Cereal.BULGUR, 3f)
        assertThrows(IllegalStateException::class.java) {
            storageTest.addCereal(Cereal.MILLET, 5f)
        }
    }

    //positive tests getCereal
    @Test
    fun `should return the amount of cereal received`() {
        val storageTest = CerealStorageImpl(10f, 100f)
        storageTest.addCereal(Cereal.RICE, 5f)
        assertEquals(2f, storageTest.getCereal(Cereal.RICE, 2f), 0.01f)
        assertEquals(3f, storageTest.getAmount(Cereal.RICE), 0.01f)
    }

    @Test
    fun `should return all amount of cereal received`() {
        val storageTest = CerealStorageImpl(10f, 100f)
        storageTest.addCereal(Cereal.RICE, 5f)
        assertEquals(5f, storageTest.getCereal(Cereal.RICE, 5f), 0.01f)
        assertEquals(0f, storageTest.getAmount(Cereal.RICE), 0.01f)
    }

    @Test
    fun `should return the amount the remainder of cereal`() {
        val storageTest = CerealStorageImpl(10f, 100f)
        storageTest.addCereal(Cereal.RICE, 5f)
        assertEquals(5f, storageTest.getCereal(Cereal.RICE, 7f), 0.01f)
        assertEquals(0f, storageTest.getAmount(Cereal.RICE), 0.01f)
    }

    //negative tests getCereal
    @Test
    fun `should throw an error when using the getCereal function with negative values`() {
        val storageTest = CerealStorageImpl(10f, 100f)
        storageTest.addCereal(Cereal.RICE, 2f)
        assertThrows(IllegalArgumentException::class.java) {
            storageTest.getCereal(Cereal.RICE, -2f)
        }
    }

    @Test
    fun `should throw an error when using the getCereal function with a cereal that is not in storage`() {
        val storageTest = CerealStorageImpl(10f, 100f)
        storageTest.addCereal(Cereal.RICE, 2f)
        assertThrows(IllegalArgumentException::class.java) {
            storageTest.getCereal(Cereal.BULGUR, 2f)
        }
    }

    //positive tests removeContainer
    @Test
    fun `should be destroyed the empty container`() {
        val storageTest = CerealStorageImpl(10f, 100f)
        storageTest.addCereal(Cereal.RICE, 0f)
        assertTrue(storageTest.removeContainer(Cereal.RICE))
    }

    //negative tests removeContainer
    @Test
    fun `should be return false then container is not empty`() {
        val storageTest = CerealStorageImpl(10f, 100f)
        storageTest.addCereal(Cereal.RICE, 5f)
        assertFalse(storageTest.removeContainer(Cereal.RICE))
    }

    @Test
    fun `should give an error if there is no such container in storage`() {
        val storageTest = CerealStorageImpl(10f, 100f)
        assertThrows(IllegalArgumentException::class.java) {
            storageTest.removeContainer(Cereal.RICE)
        }
    }

    //positive tests getAmount
    @Test
    fun `should return the amount of cereal in container`() {
        val storageTest = CerealStorageImpl(10f, 100f)
        storageTest.addCereal(Cereal.RICE, 5f)
        assertEquals(5.0f, storageTest.getAmount(Cereal.RICE))
    }

    //positive tests getAmount
    @Test
    fun `should give an error function getAmount if there is no such container in storage`() {
        val storageTest = CerealStorageImpl(10f, 100f)
        assertThrows(IllegalArgumentException::class.java) {
            storageTest.getAmount(Cereal.RICE)
        }
    }
    //negative tests getAmount
    @Test
    fun `should give an error function  getAmount if there is no such container in storage`() {
        val storageTest = CerealStorageImpl(10f, 100f)
        assertThrows(IllegalArgumentException::class.java) {
            storageTest.getAmount(Cereal.RICE)
        }
    }

    //positive tests getSpace
    @Test
    fun `should return the amount of cereal that the container can hold, taking into account its current fullness`() {
        val storageTest = CerealStorageImpl(10f, 100f)
        storageTest.addCereal(Cereal.RICE, 2f)
        assertEquals(8f, storageTest.getSpace(Cereal.RICE), 0.01f)
    }

    @Test
    fun `should return the amount of cereal that is not in stock that the container can hold, taking into account its current filling`() {
        val storageTest = CerealStorageImpl(10f, 100f)
        assertEquals(10f, storageTest.getSpace(Cereal.RICE), 0.01f)
    }

    //positive tests toString
    @Test
    fun `should return a string representation of a single container storage`() {
        val storageTest = CerealStorageImpl(10f, 100f)
        storageTest.addCereal(Cereal.RICE, 2.4f)
        assertEquals("Рис: 2.4", storageTest.toString())
    }

    @Test
    fun `should return a string representation of the storage`() {
        val storageTest = CerealStorageImpl(10f, 100f)
        storageTest.addCereal(Cereal.RICE, 2f)
        storageTest.addCereal(Cereal.BULGUR, 4.5f)
        assertEquals("Рис: 2.0\nБулгур: 4.5", storageTest.toString())
    }

}