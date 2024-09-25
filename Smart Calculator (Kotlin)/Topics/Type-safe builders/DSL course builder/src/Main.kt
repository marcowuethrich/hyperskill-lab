data class Student(val name: String, val age: Int)

data class Course(val name: String, val students: MutableList<Student> = mutableListOf())

class CourseBuilder {
    var name: String = ""
    private val students = mutableListOf<Student>()

    fun name(value: String) {
        name = value
    }

    fun student(block: StudentBuilder.() -> Unit) {
        val studentBuilder = StudentBuilder()
        studentBuilder.block()
        students.add(studentBuilder.build())
    }

    fun build(): Course {
        return Course(name, students)
    }
}

class StudentBuilder {
    var name: String = ""
    var age: Int = 0

    fun name(value: String) {
        name = value
    }

    fun age(value: Int) {
        age = value
    }

    fun build(): Student {
        return Student(name, age)
    }
}


fun buildCourse(block: CourseBuilder.() -> Unit): Course {
    val courseBuilder = CourseBuilder()
    courseBuilder.block()
    return courseBuilder.build()
}
