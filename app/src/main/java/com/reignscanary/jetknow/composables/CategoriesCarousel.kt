package com.reignscanary.jetknow.composables


import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.database.FirebaseDatabase
import com.reignscanary.jetknow.Category
import com.reignscanary.jetknow.MainScreenViewModel
import java.util.*


@Composable
fun CategoriesCarousel(modifier: Modifier)

{


    val mainScreenViewModel : MainScreenViewModel = viewModel()
    val selectedCategory = mainScreenViewModel.selectedCategory.value
    val categoryList : List<Category> = listOf(Category("Electrician"), Category("Plumbers"),Category("Laundry"),Category("Barber"),Category("Dairy"))
    LazyRow(
        Modifier
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            .clip(MaterialTheme.shapes.large)

    ) {

        item {
            for (i in categoryList) {
                CategoryCard(
                    modifier
                        .padding(end = 4.dp)

                        .clip(MaterialTheme.shapes.large),
                    category = i.category,
                    isSelected = selectedCategory == i.category

                ) {
                    mainScreenViewModel.onSelectedCategoryChanged(it)
                }
            }
        }

    }



}



