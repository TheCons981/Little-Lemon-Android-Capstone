package com.thecons981.littlelemon

import android.graphics.BitmapFactory
import android.graphics.Paint.Align
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.toLowerCase
import com.thecons981.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun Home(navController: NavController, database: AppDatabase) {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    val onChangeSearch: (TextFieldValue) -> (Unit) = {
        searchText = it
    }

    var categoryFilterText by remember { mutableStateOf("") }
    val onChangeCategoryFilter: (String) -> (Unit) = {
        categoryFilterText = if (categoryFilterText == it) {
            ""
        } else {
            it
        }
    }

    Column {
        HomeNavbar(navController)
        Hero(searchText, onChangeSearch)
        MenuBreakdown(categoryFilterText, onChangeCategoryFilter)
        MenuItems(database, searchText, categoryFilterText)
    }
}

@Composable
fun HomeNavbar(navController: NavController) {
    val context = LocalContext.current
    /* var inputStream = context.assets.open("logo.png")
     val logo = BitmapFactory.decodeStream(inputStream).asImageBitmap()
     inputStream = context.assets.open("profile.png")
     val profile = BitmapFactory.decodeStream(inputStream).asImageBitmap()*/

    Box(
        modifier = Modifier
            .fillMaxWidth()
    )
    {
        LogoImage(
            modifier = Modifier
                .width(200.dp)
                .height(80.dp)
                .align(
                    Alignment.Center
                )
        )
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "profile",
            modifier = Modifier
                .width(70.dp)
                .height(70.dp)
                .padding(0.dp, 0.dp, 10.dp, 0.dp)
                .clickable {
                    navController.navigate(Profile.route)
                }
                .align(Alignment.CenterEnd),
        )
    }
}


@Composable
fun Hero(searchText: TextFieldValue, onChangeSearch: (TextFieldValue) -> (Unit)) {
    Box(
        modifier = Modifier
            .background(Color(0xFF495E57))
            .fillMaxWidth()
            .padding(10.dp, 10.dp, 10.dp, 10.dp)

    ) {
        Column {
            Text(
                stringResource(id = R.string.app_name),
                color = Color(0xFFF4CE14),
                style = MaterialTheme.typography.h4
            )


            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(top = 20.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        stringResource(id = R.string.chicago),
                        color = Color(0xFFFFFFFF),
                        style = MaterialTheme.typography.h5
                    )
                    Text(
                        text = stringResource(id = R.string.header_body),
                        style = MaterialTheme.typography.body1,
                        color = Color.White,
                        modifier = Modifier
                            .padding(bottom = 28.dp, end = 20.dp)
                            .fillMaxWidth(0.6f)
                            .padding(top = 7.dp),

                        )
                }

                Image(
                    painter = painterResource(id = R.drawable.hero),
                    contentDescription = "Upper Panel Image",
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .height(150.dp)


                )

            }

            OutlinedTextField(value = searchText,
                placeholder = { Text(stringResource(id = R.string.search)) },
                singleLine = true,
                onValueChange = { onChangeSearch(it) },

                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .defaultMinSize(minHeight = 30.dp)
                    .sizeIn(maxHeight = 50.dp),
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") }

            )
        }
    }
}

@Composable
fun MenuBreakdown(categoryFilterText: String, onChangeCategoryFilter: (String) -> (Unit)) {
    Box {
        Column {
            Text(
                stringResource(id = R.string.order_for_delivery),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(5.dp, 15.dp, 0.dp, 0.dp)
            )
            Row {
                FilterButton(
                    stringResource(id = R.string.starters),
                    categoryFilterText,
                    onChangeCategoryFilter
                )
                FilterButton(
                    stringResource(id = R.string.mains),
                    categoryFilterText,
                    onChangeCategoryFilter
                )
                FilterButton(
                    stringResource(id = R.string.desserts),
                    categoryFilterText,
                    onChangeCategoryFilter
                )
                FilterButton(
                    stringResource(id = R.string.drinks),
                    categoryFilterText,
                    onChangeCategoryFilter
                )
            }
        }
    }
}

@Composable
fun MenuItems(database: AppDatabase, searchText: TextFieldValue, categoryFilterText: String) {

    val databaseMenuItems by database.menuItemDao().getAll().observeAsState(emptyList())

    LazyColumn {
        itemsIndexed(databaseMenuItems.filter {
            it.title.contains(
                searchText.text,
                ignoreCase = true
            )
        }.filter {
            it.category.contains(
                categoryFilterText,
                ignoreCase = true
            )
        }.sortedBy {
            it.title
        }) { index, it ->
            Divider(
                color = Color.LightGray, thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            MenuItem(
                title = it.title,
                description = it.description,
                price = it.price,
                image = it.image
            )
        }
    }

}

@Composable
fun FilterButton(
    str: String,
    categoryFilterText: String,
    onChangeCategoryFilter: (String) -> (Unit)
) {
    Button(
        onClick = {
            onChangeCategoryFilter(str)
        },
        modifier = Modifier
            .padding(5.dp, 5.dp, 0.dp, 5.dp),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = if (str == categoryFilterText) {
                Color(0xFFF4CE14)
            } else {
                Color(0xFFAFAFAF)
            },
            contentColor = Color(0xFF495E57),
            disabledContentColor = Color(0xFFAFAFAF)
        ),
    ) {
        Text(str)
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(title: String, description: String, price: String, image: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = title,
            )
            Text(
                text = description,
                maxLines = 3,
                modifier = Modifier
                    .width(250.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "$ $price",
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        GlideImage(
            model = image,
            contentDescription = title,
            modifier = Modifier
                .size(80.dp),
            contentScale = ContentScale.Crop
        )
    }

}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    LittleLemonTheme {
        Greeting("Android")
    }
}