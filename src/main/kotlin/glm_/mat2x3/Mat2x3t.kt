package glm_.mat2x3

import glm_.vec3.Vec3t


abstract class Mat2x3t<T : Number> {

    abstract var a0: T
    abstract var a1: T
    abstract var a2: T

    abstract var b0: T
    abstract var b1: T
    abstract var b2: T


    operator fun component1() = a0
    operator fun component2() = a1
    operator fun component3() = a2

    operator fun component4() = b0
    operator fun component5() = b1
    operator fun component6() = b2

    // -- Accesses --

    abstract operator fun get(index: Int): Vec3t<out Number>
    abstract operator fun get(column: Int, row: Int): T

    abstract operator fun set(index: Int, value: Vec3t<out Number>)
    abstract operator fun set(column: Int, row: Int, value: T)


    // component alias

    var v00
        @JvmName("v00") get() = a0
        @JvmName("v00") set(value) {
            a0 = value
        }
    var v01
        @JvmName("v01") get() = a1
        @JvmName("v01") set(value) {
            a1 = value
        }
    var v02
        @JvmName("v02") get() = a2
        @JvmName("v02") set(value) {
            a2 = value
        }

    var v10
        @JvmName("v10") get() = b0
        @JvmName("v10") set(value) {
            b0 = value
        }
    var v11
        @JvmName("v11") get() = b1
        @JvmName("v11") set(value) {
            b1 = value
        }
    var v12
        @JvmName("v12") get() = b2
        @JvmName("v12") set(value) {
            b2 = value
        }


    abstract fun size(): Int


    companion object {
        const val length = 2 * 3
    }

//    override fun toString() =
//            "| ${this[0][0]}][${this[1][0]} |" +
//                    "| ${this[0][1]}][${this[1][1]} |" +
//                    "| ${this[0][2]}][${this[1][2]} |"
}