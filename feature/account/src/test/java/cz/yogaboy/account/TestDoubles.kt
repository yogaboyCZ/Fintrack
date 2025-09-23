package cz.yogaboy.account

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

// Test doubles - Dummy, Fake, Stub, Spy, Mock is from Jov vid. Just added here my reference impl
class TestDoubles {
    interface EmailValidator {
        fun isValid(email: String): Boolean
    }

    class AuthorizationSystem(private val emailValidator: EmailValidator) {
        fun authorizedUserCount(): Int {
            return 0
        }

        fun authorize(email: String, password: String): Boolean {
            if (emailValidator.isValid(email)) {
                return email.isNotBlank() && password.isNotBlank()
            }
            return false
        }

    }

    @Test
    fun `no authorized users initially`() {
        val system = AuthorizationSystem(DummyEmailValidator())
        val authorizedUserCount = system.authorizedUserCount()
        assertEquals(0, authorizedUserCount)
    }

    @Test
    fun `successful authorization with valid email`() {
        val email = "an email"
        val password = "a password"
        val emailValidator = PresetEmailValidator(listOf(email))
        val system = AuthorizationSystem(emailValidator)
        val authorizaationResult = system.authorize(email, password)
        assertTrue(authorizaationResult)
//        emailValidator.verify(timeCalled = 1)
    }

    class PresetEmailValidator(
        private val validEmails: List<String>
    ) : EmailValidator {
        override fun isValid(email: String): Boolean {
            return validEmails.contains(email)
        }

    }

    class AcceptEmailValidatorMock : EmailValidator {
        private var validationCalleCount = 0
        var emailArgument: String? = null
        override fun isValid(email: String): Boolean {
            validationCalleCount++
            emailArgument = email
            return true
        }

        fun verify(timeCalled: Int) {
            if (timeCalled != validationCalleCount) {
                throw AssertionError("Incorrect count of calls. Expected: $timeCalled, but was: $validationCalleCount")
            }
        }
    }

    class AcceptEmailValidatorSpy : EmailValidator {
        var wasCalled = false
        var emailArgument: String? = null
        override fun isValid(email: String): Boolean {
            wasCalled = true
            return true
        }
    }

    class AcceptEmailValidator : EmailValidator {
        override fun isValid(email: String) = true

    }

    class DummyEmailValidator : EmailValidator {
        override fun isValid(email: String): Boolean {
            TODO()
        }
    }
}