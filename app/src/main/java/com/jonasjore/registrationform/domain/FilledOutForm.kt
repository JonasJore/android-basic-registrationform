package com.jonasjore.registrationform.domain

data class FilledOutForm(
    val name: String,
    val email: String,
    val password: String,
    val reenteredPassword: String,
    val gender: Gender,
    val country: String,
    val readLisenceAgreement: Boolean
)

private fun FilledOutForm.validateBlankInput(): Boolean =
    this.name.isNotBlank() ||
            this.email.isNotBlank() ||
            this.password.isNotBlank() ||
            this.reenteredPassword.isNotBlank()

private fun FilledOutForm.lisenceAgreementAccepted(): Boolean = this.readLisenceAgreement

fun FilledOutForm.passwordMatchesReenteredPassword(): Boolean =
    this.password == this.reenteredPassword

fun FilledOutForm.validateForm(): Boolean {
    return !(!this.validateBlankInput() ||
            !this.passwordMatchesReenteredPassword() ||
            !this.lisenceAgreementAccepted())
}
