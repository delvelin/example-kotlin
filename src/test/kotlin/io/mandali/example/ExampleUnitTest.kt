package io.mandali.example

import io.github.hangga.delvelin.Delvelin
import io.github.hangga.delvelin.LogListener
import io.github.hangga.delvelin.properties.OutputFileFormat
import org.junit.jupiter.api.Test
import java.lang.Thread.sleep
import kotlin.concurrent.thread

class Account(val name: String, var balance: Int) {

    private fun deposit(amount: Int) {
        balance += amount
    }

    private fun withdraw(amount: Int) {
        balance -= amount
    }

    fun transfer(to: Account, amount: Int) {
        println("${this.name} tries to transfer $amount to ${to.name}.")
        synchronized(this) {
            sleep(10) // Simulate processing time
            if (balance >= amount) {
                withdraw(amount)
                synchronized(to) {
                    to.deposit(amount)
                }
            }
        }
    }
}

class MandaliExampleUnitTest {

    @Test
    fun `example of deadlock`() {
        val account1 = Account("Hangga", 1000)
        val account2 = Account("John", 1000)
        val account3 = Account("Alice", 2000)

        // Transfer from account1 to account2
        thread {
            account1.transfer(account2, 100)
        }.join(10) // as a simulation of the time required

        // Transfer from account2 to account1
        thread {
            account2.transfer(account1, 200)
        }.join(20)

        // Transfer from account3 to account1
        thread {
            account3.transfer(account1, 1000)
        }.join(500)

//        Mandali(this).start()
    }

    val list = ArrayList<Int>()

    @Test
    fun `example thread-unsafe using HashMap`() {
        val map = HashMap<Int, Int>()

        val threads = List(10) { index ->
            thread {
                for (i in 0 until 1000) {
                    map[i] = index
                }
            }
        }

        threads.forEach { it.join() }
    }

    @Test
    fun `vulnerability test`() {
        Delvelin()
            .setOutputFormat(OutputFileFormat.HTML)
            .setAllowedExtensions(".java")
            .setShowSaveDialog(true)
            .scan()
    }

    @Test
    fun `test with custom listener`() {
        Delvelin().setLogListener(object : LogListener {
            override fun onGetLog(s: String) {
            }

            override fun onGetLog(stringBuffer: StringBuffer) {
            }
        }).scan()
    }
}