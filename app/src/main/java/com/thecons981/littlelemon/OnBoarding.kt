package com.thecons981.littlelemon

import android.content.SharedPreferences
import android.widget.ScrollView
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


// or just
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle

@Composable
fun OnBoarding(navController: NavController, sharedPreferences: SharedPreferences) {

    Column(
        Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoImage(
            modifier = Modifier
                .width(200.dp)
                .height(80.dp)
        )
        Row(
            Modifier
                .fillMaxWidth()
                .background(Color(0xFF475D57)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center

        ) {
            Text(
                text = stringResource(id = R.string.register_prhase),

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, bottom = 30.dp, start = 20.dp, end = 20.dp),
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White,


                )

        }

        FormFields(navController = navController, sharedPreferences = sharedPreferences)

    }
}


@Composable
fun FormFields(navController: NavController, sharedPreferences: SharedPreferences) {

    val context = LocalContext.current
    var firstName by remember { mutableStateOf(TextFieldValue("")) }
    var lastName by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }


    Text(
        stringResource(id = R.string.personal_info),
        style = MaterialTheme.typography.h5,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 5.dp)
    )
    Text(
        stringResource(id = R.string.firstName),
        style = MaterialTheme.typography.body1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
    )
    TextField(
        value = firstName,
        onValueChange = {
            firstName = it
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Gray,
            disabledTextColor = Color.Black,
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
            .defaultMinSize(minHeight = 30.dp)
            .sizeIn(maxHeight = 50.dp),

        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
    Text(
        stringResource(id = R.string.lastName),
        style = MaterialTheme.typography.body1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
    )
    TextField(
        value = lastName,
        onValueChange = {
            lastName = it
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Gray,
            disabledTextColor = Color.Black,
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
            .defaultMinSize(minHeight = 30.dp)
            .sizeIn(maxHeight = 50.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )

    Text(
        stringResource(id = R.string.email),
        style = MaterialTheme.typography.body1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
    )
    TextField(
        value = email,
        onValueChange = {
            email = it
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Gray,
            disabledTextColor = Color.Black,
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
            .defaultMinSize(minHeight = 30.dp)
            .sizeIn(maxHeight = 50.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )

    Button(
        onClick = {
            if (firstName.text.isBlank() ||
                lastName.text.isBlank() ||
                email.text.isBlank()
            ) {
                Toast.makeText(context, R.string.register_error, Toast.LENGTH_SHORT).show()
            } else {
                val edit = sharedPreferences.edit()
                edit.putString(Constants.firstNameKey, firstName.text)
                edit.putString(Constants.lastNameKey, lastName.text)
                edit.putString(Constants.emailKey, email.text)
                edit.putBoolean(Constants.isLogged, true)
                edit.apply()

                Toast.makeText(context, R.string.register_ok, Toast.LENGTH_SHORT).show()
                navController.navigate(Home.route)
            }
        },
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Text(text = stringResource(id = R.string.register))
    }


}

@Preview(showBackground = true)
@Composable
fun OnBoardingPreview() {
    //OnBoarding()
}