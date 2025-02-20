package ru.webrelab.kie.cerealstorage

import kotlin.math.abs
import kotlin.math.min


class CerealStorageImpl(
    override val containerCapacity: Float,
    override val storageCapacity: Float
) : CerealStorage {


    // Блок инициализации класса. Выполняется сразу при создании объекта
    init {
        require(containerCapacity >= 0) {
            "Ёмкость контейнера не может быть отрицательной"
        }
        require(containerCapacity >= 0) {
            "Ёмкость хранилища не может быть отрицательной"
        }
        require(storageCapacity >= containerCapacity) {
            "Ёмкость хранилища не должна быть меньше ёмкости одного контейнера"
        }
    }

    private val storage = mutableMapOf<Cereal, Float>()

    // дальше будет переопределением методов интерфейса
    override fun addCereal(cereal: Cereal, amount: Float): Float {
        require(amount >= 0) {
            "Количество крупы не может быть отрицательным"
        }
        checkStorageCapacity(cereal)
        val currentAmount = storage.getOrDefault(cereal, 0f)
        val amountForAdding = min(getSpace(cereal), amount)
        storage[cereal] = currentAmount + amountForAdding
        return amount - amountForAdding
    }

    override fun getCereal(cereal: Cereal, amount: Float): Float {
        require(amount >= 0) {
            "Количество крупы не может быть отрицательным"
        }
        require(storage.contains(cereal)) {
            "Крупа этого типа отстутсвует в хранилище"
        }
        val current = getAmount(cereal)
        return if (current >= amount) {
            storage[cereal] = current - amount
            amount
        } else {
            storage[cereal] = 0f
            current
        }
    }

    override fun removeContainer(cereal: Cereal): Boolean {
        require(storage.contains(cereal)) {
            "Крупа этого типа отстутсвует в хранилище"
        }
        val epsilon = 0.01f // допустимая погрешность
        return if ( abs((storage[cereal] ?: 0f) - 0f) <= epsilon   ) {
            storage.remove(cereal)
            true
        } else {
            false
        }
    }

    override fun getAmount(cereal: Cereal): Float {
        require(storage.contains(cereal)) {
            "Крупа этого типа отстутсвует в хранилище"
        }
        return storage.getOrDefault(cereal, 0f)
    }

    override fun getSpace(cereal: Cereal): Float {
        return containerCapacity - storage.getOrDefault(cereal, 0f)
    }

    override fun toString(): String {
        return storage.map { "${it.key.local}: ${it.value}" }.joinToString("\n")
    }

    private fun checkStorageCapacity(cereal: Cereal) {
        if (storage.contains(cereal)) return
        check(storageCapacity >= (storage.size + 1) * containerCapacity) {
            "Недостаточно места в хранилище для нового контейнера"
        }
    }
}