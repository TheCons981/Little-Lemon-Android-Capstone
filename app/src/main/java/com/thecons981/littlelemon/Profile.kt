package com.thecons981.littlelemon

import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// or just
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun Profile(navController: NavController, sharedPreferences: SharedPreferences) {
    Column(

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoImage(
            modifier = Modifier
                .width(200.dp)
                .height(80.dp)
        )
        ProfileFormFields(navController = navController, sharedPreferences = sharedPreferences)
    }
}

@Composable
fun ProfileFormFields(navController: NavController, sharedPreferences: SharedPreferences) {

    val firstName = sharedPreferences.getString(Constants.firstNameKey, "")
    val lastName = sharedPreferences.getString(Constants.lastNameKey, "")
    val email = sharedPreferences.getString(Constants.emailKey, "")



    Text(
        stringResource(id = R.string.personal_info),
        style = MaterialTheme.typography.h5,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),

        )
    Text(
        stringResource(id = R.string.firstName),
        style = MaterialTheme.typography.body1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)

    )
    Text(
        firstName!!,
        style = MaterialTheme.typography.body1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .border(width = 1.dp, color = Color.Gray, RoundedCornerShape(4.dp))

            .background(color = Color.White)
            .padding(14.dp)
    )
    Text(
        stringResource(id = R.string.lastName),
        style = MaterialTheme.typography.body1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
    )
    Text(
        lastName!!,
        style = MaterialTheme.typography.body1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .border(width = 1.dp, color = Color.Gray, RoundedCornerShape(4.dp))

            .background(color = Color.White)
            .padding(14.dp)
    )
    Text(
        stringResource(id = R.string.email),
        style = MaterialTheme.typography.body1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
    )
    Text(
        email!!,
        style = MaterialTheme.typography.body1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .border(width = 1.dp, color = Color.Gray, RoundedCornerShape(4.dp))

            .background(color = Color.White)
            .padding(14.dp)
    )

    Button(
        onClick = {
            val edit = sharedPreferences.edit()
            edit.putString(Constants.firstNameKey, "")
            edit.putString(Constants.lastNameKey, "")
            edit.putString(Constants.emailKey, "")
            edit.putBoolean(Constants.isLogged, false)
            edit.apply()

            navController.navigate(Onboarding.route) {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),

        ) {
        Text(
            text = stringResource(id = R.string.logout),
        )
    }


}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {

}