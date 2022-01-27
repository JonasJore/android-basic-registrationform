package com.jonasjore.registrationform

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.jonasjore.registrationform.domain.FilledOutForm
import com.jonasjore.registrationform.domain.Gender
import com.jonasjore.registrationform.domain.passwordMatchesReenteredPassword
import com.jonasjore.registrationform.domain.validateForm

class MainActivity : AppCompatActivity() {

    lateinit var txtName: EditText
    lateinit var txtEmail: EditText
    lateinit var txtPassword: EditText
    lateinit var txtReenteredPassword: EditText
    lateinit var radioGroupGender: RadioGroup
    private lateinit var registerButton: Button
    lateinit var readLicenseAgreement: CheckBox
    lateinit var registrationCompleteSnackbar: Snackbar
    private lateinit var countryDropdown: Spinner

    lateinit var nameNotValid: TextView
    lateinit var emailNotValid: TextView
    lateinit var passwordNotValid: TextView
    private lateinit var passwordReenterNotValid: TextView

    var chosenGender: Gender = Gender.NOT_SELECTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        registerButton.setOnClickListener {
            initRegister()
        }
    }

    private fun initRegister() {
        val input = FilledOutForm(
            name = txtName.text.toString(),
            email = txtEmail.text.toString(),
            password = txtPassword.text.toString(),
            reenteredPassword = txtReenteredPassword.text.toString(),
            gender = chosenGender,
            country = countryDropdown.selectedItem.toString(),
            readLisenceAgreement = readLicenseAgreement.isChecked
        )

        if (input.validateForm()) {
            showSnackBar(input)
        } else {
            if (!readLicenseAgreement.isChecked) {
                Toast.makeText(
                    this,
                    "You need to agree to the license agreement",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (input.name.isBlank()) {
                nameNotValid.visibility = View.VISIBLE
            } else {
                nameNotValid.visibility = View.INVISIBLE
            }

            if (input.email.isBlank()) {
                emailNotValid.visibility = View.VISIBLE
            } else {
                emailNotValid.visibility = View.INVISIBLE
            }

            if (input.password.isBlank()) {
                passwordNotValid.visibility = View.VISIBLE
            } else {
                passwordNotValid.visibility = View.INVISIBLE
            }

            if (input.reenteredPassword.isBlank() || !input.passwordMatchesReenteredPassword()) {
                passwordReenterNotValid.visibility = View.VISIBLE
            } else {
                passwordReenterNotValid.visibility = View.INVISIBLE
            }
        }
    }

    private fun showSnackBar(input: FilledOutForm) {
        println(
            StringFormats.registeredFormSnackbarFormat.format(
                input.name,
                input.email,
                input.country,
                input.gender,
                input.password,
                input.readLisenceAgreement.toString()
            )
        )
    }

    private fun initViews() {
        txtName = findViewById(R.id.txtName)
        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)
        txtReenteredPassword = findViewById(R.id.txtPasswordReenter)

        nameNotValid = findViewById(R.id.txtNameNotValid)
        emailNotValid = findViewById(R.id.txtEmailNotValid)
        passwordNotValid = findViewById(R.id.txtPasswordNotValid)
        passwordReenterNotValid = findViewById(R.id.txtPasswordReenterNotValid)

        registerButton = findViewById(R.id.registerButton)

        readLicenseAgreement = findViewById(R.id.readLicenseAgreement)
        radioGroupGender = findViewById(R.id.radioGroupGender)
        radioGroupGender.setOnCheckedChangeListener { _, checkedId ->
            chosenGender = when (checkedId) {
                R.id.radioGroupMale -> Gender.MALE
                R.id.radioGroupFemale -> Gender.FEMALE
                R.id.radioGroupOther -> Gender.OTHER
                else -> Gender.NOT_SELECTED
            }
        }
        countryDropdown = findViewById(R.id.txtCountrySpinner)
    }
}
